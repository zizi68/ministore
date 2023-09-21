package com.ministore.android;

import android.app.Activity;
import android.app.Application;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Button;

import com.ministore.android.activity.LoginActivity;
import com.ministore.android.activity.MainActivity;
import com.ministore.android.activity.ProfileActivity;
import com.ministore.android.activity.SettingsActivity;
import com.ministore.android.model.Address;
import com.ministore.android.model.District;
import com.ministore.android.model.JwtResponse;
import com.ministore.android.model.Product;
import com.ministore.android.model.Province;
import com.ministore.android.model.Ward;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.paperdb.Paper;

public class MyApplication extends Application {

    public static final String KEY_GET_STARTED = "get_started";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_PRODUCT = "product";
    public static final String KEY_JWT_RESPONSE = "jwt_response";

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_SHIPPER = "ROLE_SHIPPER";

    private static NumberFormat numberFormat = new DecimalFormat("###,###");
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aa");

    public interface OnCartActionListener {
        void onAuthenticationFailed();
        void onCartShowMessage(String message);
    }

    public interface OnOrderActionListener {
        void onAuthenticationFailed();
        void onActionCompleted(String message);
    }

    public static String fromAddressToString(Address address) {
        Ward ward = address.getWard();
        District district = ward.getDistrict();
        Province province = district.getProvince();
        String specificAddress = address.getSpecificAddress();
        return String.format("%s, %s %s - %s %s - %s", specificAddress,
                ward.getWardPrefix(), ward.getWardName(),
                district.getDistrictPrefix(), district.getDistrictName(),
                province.getProvinceName());
    }

    public static void goToLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finishAffinity();
    }

    public static void goToMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
    }

    public static int getUserId() {
        JwtResponse jwtResponse = Paper.book().read(MyApplication.KEY_JWT_RESPONSE);
        if (jwtResponse == null) return -1;
        return jwtResponse.getId();
    }

    public static String getAuthorization() {
        JwtResponse jwtResponse = Paper.book().read(MyApplication.KEY_JWT_RESPONSE);
        if (jwtResponse == null) return null;
        return String.format("%s %s", jwtResponse.getTokenType(), jwtResponse.getAccessToken());
    }

    public static boolean checkUserLogged() {
        JwtResponse jwtResponse = Paper.book().read(MyApplication.KEY_JWT_RESPONSE);
        if (jwtResponse == null) return false;
        return jwtResponse.getRoles().contains(ROLE_USER);
    }

    public static boolean checkAdminPermission() {
        JwtResponse jwtResponse = Paper.book().read(MyApplication.KEY_JWT_RESPONSE);
        if (jwtResponse == null) return false;
        return jwtResponse.getRoles().contains(ROLE_ADMIN);
    }
    public static boolean checkShipperPermission() {
        JwtResponse jwtResponse = Paper.book().read(MyApplication.KEY_JWT_RESPONSE);
        if (jwtResponse == null) return false;
        return jwtResponse.getRoles().contains(ROLE_SHIPPER);
    }

    private enum EActionButton {
        REQUEST_CANCELLATION("Request cancellation", R.color.dark_red, R.drawable.ic_playlist_remove_white),
        CANCEL_REQUEST("Cancel request", R.color.dark_gray, R.drawable.ic_cancel_white),
        RECEIVED("Received", R.color.light_blue, R.drawable.ic_check_box_white),
        REPURCHASE("Repurchase", R.color.light_green, R.drawable.ic_shopping_bag),
        REPURCHASE2("Repurchase", R.color.light_green, R.drawable.ic_shopping_bag);

        private final String text;
        private final int colorResourceId;
        private final int imageId;

        EActionButton(String text, int colorResourceId, int imageId) {
            this.text = text;
            this.colorResourceId = colorResourceId;
            this.imageId = imageId;
        }
    }

    public static void setActionButton(Button btnAction, int statusId) {
        EActionButton eActionButton = EActionButton.values()[statusId - 1];
        btnAction.setText(eActionButton.text);
        btnAction.setBackgroundTintList(btnAction.getResources().getColorStateList(eActionButton.colorResourceId));
        btnAction.setCompoundDrawablesWithIntrinsicBounds(eActionButton.imageId, 0, 0, 0);
    }

    public static String formatNumber(double number) {
        return numberFormat.format(number);
    }

    public static String formatDate(Date date) {
        return simpleDateFormat.format(date);
    }

    public static void signOut(Activity activity) {
        Paper.book().delete(MyApplication.KEY_JWT_RESPONSE);
        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
        activity.startActivity(intent);
        activity.finishAffinity();
    }

    public static void viewProfile(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }

    public static void viewSettings(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), SettingsActivity.class);
        activity.startActivity(intent);
    }


    public static File createFileFromBitmap(Context context, Bitmap bitmap, String fileName) {
        //create a file to write bitmap data
        File f = new File(context.getCacheDir(), fileName);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return f;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
