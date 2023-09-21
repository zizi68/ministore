package com.ministore.android.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Category;
import com.ministore.android.model.JwtResponse;
import com.ministore.android.model.ResponseObject;
import com.ministore.android.model.SignupRequest;
import com.ministore.android.model.UpdateProfileRequest;
import com.ministore.android.model.User;
import com.ministore.android.response.ResponseBody;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import io.paperdb.Paper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private NestedScrollView nestedScrollView;
    private View layoutImage;
    private ImageView imgUserAvatar;
    private EditText edtUsername, edtFirstName, edtLastName, edtEmail, edtPhone;
    private Button btnSave, btnCancel;

    private int userId;
    private String realPath;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setControl();
        setEvent();
        loadUserInformation();
    }

    private void showMessage(String message) {
//        Snackbar snackbar = Snackbar.make(nestedScrollView, message, Snackbar.LENGTH_LONG);
//        snackbar.setAction("OK", null);
//        snackbar.setActionTextColor(Color.YELLOW);
//        snackbar.show();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void saveChanges() {
        String username = edtUsername.getText().toString().trim();
        String firstName = edtFirstName.getText().toString().trim();
        String lastName = edtLastName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showMessage("Please enter all field!");
            return;
        }

        String authorization = MyApplication.getAuthorization();
        if (authorization == null) {
            MyApplication.goToLoginActivity(ProfileActivity.this);
            return;
        }
        MultipartBody.Part body = createBodyToUpload();
        ApiService.apiService.uploadUserImage(authorization, body).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                fileName = response.body();

                UpdateProfileRequest user = new UpdateProfileRequest();
                user.setId(userId);
                user.setUsername(username);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhone(phone);
                user.setImage(fileName);

                ApiService.apiService.updateProfile(user).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code() == 400) {
                            try {
                                showMessage(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        if (!response.isSuccessful()) {
                            showMessage("Update failed!\n" + response.message());
                            return;
                        }
                        showMessage(response.body());
                        finish();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        showMessage(t.getMessage());
                    }
                });
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showMessage(t.getMessage());
            }
        });
    }

    private void setEvent() {
        layoutImage.setOnClickListener(view -> {
            requestPermission();
        });
        btnSave.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm")
                    .setMessage("Are you sure want to save changes?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            saveChanges();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadUserInformation() {
        JwtResponse jwtResponse = Paper.book().read(MyApplication.KEY_JWT_RESPONSE);
        if (jwtResponse != null) {
            userId = jwtResponse.getId();
            ApiService.apiService.getUserById(userId)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User user = response.body();
                            Picasso.get().load(ApiService.USER_IMAGE_URL + user.getImage())
                                    .error(R.drawable.img_default_avatar)
                                    .placeholder(R.drawable.img_default_avatar)
                                    .into(imgUserAvatar);
                            edtUsername.setText(user.getUsername());
                            edtFirstName.setText(user.getFirstName());
                            edtLastName.setText(user.getLastName());
                            edtEmail.setText(user.getEmail());
                            edtPhone.setText(user.getPhone());
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
        }
    }

    private void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                showMessage("Permission Denied\n" + deniedPermissions.toString());
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void openImagePicker() {
        TedBottomPicker.with(this)
                .setPeekHeight(1600)
                .showTitle(true)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected image uri
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            realPath = MyApplication.getPathFromUri(ProfileActivity.this, uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (bitmap != null) {
                            imgUserAvatar.setImageBitmap(bitmap);
                        } else {
                            imgUserAvatar.setImageResource(R.drawable.img_default_avatar);
                        }
                    }
                });
    }

    private MultipartBody.Part createBodyToUpload() {
        File file;
        if (realPath == null || realPath.isEmpty()) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable)imgUserAvatar.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            fileName = String.format("image%s.jpg", Calendar.getInstance().getTimeInMillis());
            file = MyApplication.createFileFromBitmap(this, bitmap, fileName);
        }
        else {
            file = new File(realPath);
            String file_path = file.getAbsolutePath();
            String[] strings = file_path.split("/");
            fileName = strings[strings.length - 1];
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName, requestBody);
        return body;
    }

    private void setControl() {
        nestedScrollView = findViewById(R.id.nested_scroll_view);
        layoutImage = findViewById(R.id.layout_image);
        imgUserAvatar = findViewById(R.id.img_user_avatar);
        edtUsername = findViewById(R.id.edt_signup_username);
        edtFirstName = findViewById(R.id.edt_signup_firstname);
        edtLastName = findViewById(R.id.edt_signup_lastname);
        edtEmail = findViewById(R.id.edt_signup_email);
        edtPhone = findViewById(R.id.edt_signup_phone);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
    }
}