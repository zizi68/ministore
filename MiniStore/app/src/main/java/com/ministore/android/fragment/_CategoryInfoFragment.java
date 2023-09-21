package com.ministore.android.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Category;
import com.ministore.android.response.ResponseBody;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _CategoryInfoFragment extends Fragment {

    public static final String KEY_CATEGORY = "category";
    private Category mCategory;

    public static final int INSERT_ACTION = 1;
    public static final int UPDATE_ACTION = 2;
    public static final int DELETE_ACTION = 3;
    private String realPath;
    private String fileName;

    private View view;
    private EditText edtId, edtName, edtNote;
    private ImageView imgCategory;
    private Button btnInsert, btnUpdate, btnDelete, btnClear;

    public interface OnActionListener {
        void onActionCompleted(int action);
    }

    private OnActionListener listener;

    public _CategoryInfoFragment setListener(OnActionListener listener) {
        this.listener = listener;
        return this;
    }

    public _CategoryInfoFragment() {
        // Required empty public constructor
    }

    public static _CategoryInfoFragment newInstance(Category category) {
        _CategoryInfoFragment fragment = new _CategoryInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = (Category) getArguments().getSerializable(KEY_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout._fragment_category_info, container, false);
        setControl();
        setEvent();
        if (mCategory != null) {
            setInfo(mCategory);
        }
        return view;
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setEvent() {
        btnInsert.setOnClickListener(view1 -> {
            String authorization = MyApplication.getAuthorization();
            if (authorization == null) {
                MyApplication.goToLoginActivity(getActivity());
                return;
            }
            String name = edtName.getText().toString().trim();
            if (name.isEmpty()) {
                showMessage("Name must not be empty!");
                return;
            }
            String note = edtNote.getText().toString().trim();

            MultipartBody.Part body = createBodyToUpload();
            if (body == null) return;
            ApiService.apiService.uploadCategoryImage(authorization, body).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    fileName = response.body();
                    Category category = new Category(name, fileName, note);
                    ApiService.apiService.insertCategory(category).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            ResponseBody responseBody = response.body();
                            switch (responseBody.getCode()) {
                                case 200:
                                    if (listener != null) {
                                        listener.onActionCompleted(INSERT_ACTION);
                                    }
                                    getActivity().finish();
                                case 400:
                                case 500:
                                    showMessage(responseBody.getMessage());
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showMessage(t.getMessage());
                }
            });
        });
        btnUpdate.setOnClickListener(view1 -> {
            String authorization = MyApplication.getAuthorization();
            if (authorization == null) {
                MyApplication.goToLoginActivity(getActivity());
                return;
            }
            int id = mCategory.getCategoryId();
            String name = edtName.getText().toString().trim();
            if (name.isEmpty()) {
                showMessage("Name must not be empty!");
                return;
            }
            String note = edtNote.getText().toString().trim();
            MultipartBody.Part body = createBodyToUpload();
            if (body == null) return;
            ApiService.apiService.uploadCategoryImage(authorization, body).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    fileName = response.body();
                    Category category = new Category(id, name, fileName, note);
                    ApiService.apiService.updateCategory(id, category).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            ResponseBody responseBody = response.body();
                            switch (responseBody.getCode()) {
                                case 200:
                                    if (listener != null) {
                                        listener.onActionCompleted(INSERT_ACTION);
                                    }
                                    getActivity().finish();
                                case 400:
                                case 500:
                                    showMessage(responseBody.getMessage());
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showMessage(t.getMessage());
                }
            });
        });
        btnDelete.setOnClickListener(view1 -> {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Confirm")
                    .setMessage("Are you sure want to delete this category?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (mCategory == null) return;
                            int id = mCategory.getCategoryId();
                            ApiService.apiService.deleteCategory(id).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    ResponseBody responseBody = response.body();
                                    switch (responseBody.getCode()) {
                                        case 200:
                                            if (listener != null) {
                                                listener.onActionCompleted(DELETE_ACTION);
                                            }
                                            getActivity().finish();
                                        case 400:
                                        case 500:
                                            showMessage(responseBody.getMessage());
                                            break;
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

        });
        btnClear.setOnClickListener(view1 -> {
            edtName.setText("");
            edtNote.setText("");
            imgCategory.setImageResource(R.drawable.img_cute_book_pencil_cartoon);
        });
        imgCategory.setOnClickListener(view1 -> {
            requestPermission();
        });
    }

    private MultipartBody.Part createBodyToUpload() {
        File file;
        if (realPath == null || realPath.isEmpty()) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable)imgCategory.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            fileName = String.format("image%s.jpg", Calendar.getInstance().getTimeInMillis());
            file = MyApplication.createFileFromBitmap(getContext(), bitmap, fileName);
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
        TedBottomPicker.with(getActivity())
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
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            realPath = MyApplication.getPathFromUri(getContext(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (bitmap != null) {
                            imgCategory.setImageBitmap(bitmap);
                        } else {
                            imgCategory.setImageResource(R.drawable.ic_add_photo_alternate);
                        }
                    }
                });
    }

    public void setInfo(Category category) {
        mCategory = category;
        edtId.setText(String.valueOf(category.getCategoryId()));
        edtName.setText(category.getName());
        edtNote.setText(category.getNote());
        Picasso.get().load(ApiService.CATEGORY_IMAGE_URL + category.getImage())
                .placeholder(R.drawable.ic_add_photo_alternate)
                .error(R.drawable.ic_add_photo_alternate)
                .into(imgCategory);
    }

    private void setControl() {
        edtId = view.findViewById(R.id.edt_id);
        edtName = view.findViewById(R.id.edt_name);
        edtNote = view.findViewById(R.id.edt_note);
        imgCategory = view.findViewById(R.id.img_category);
        btnInsert = view.findViewById(R.id.btn_insert);
        btnUpdate = view.findViewById(R.id.btn_update);
        btnDelete = view.findViewById(R.id.btn_delete);
        btnClear = view.findViewById(R.id.btn_clear);
    }
}