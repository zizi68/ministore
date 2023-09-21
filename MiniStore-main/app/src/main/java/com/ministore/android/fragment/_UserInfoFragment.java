package com.ministore.android.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.api.ApiService;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _UserInfoFragment extends Fragment {
    public static final String KEY_USER = "user";
    public static final int INSERT_ACTION = 1;
    public static final int UPDATE_ACTION = 2;
    public static final int DELETE_ACTION = 3;
    ArrayAdapter adapterCategory, adapterBrand;
    private User mUser;
    private String realPath;
    private String fileName;
    private View view;
    private EditText edtUsername, edtEmail, edtPhone, edtFirstname, edtLastname;
    private ImageView imgUser;
    private Button btnInsert, btnUpdate, btnDelete, btnClear;
    private _UserInfoFragment.OnActionListener listener;

    public _UserInfoFragment() {
        // Required empty public constructor
    }

    public static _UserInfoFragment newInstance(User user) {
        _UserInfoFragment fragment = new _UserInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    public _UserInfoFragment setListener(_UserInfoFragment.OnActionListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (User) getArguments().getSerializable(KEY_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout._fragment_user_info, container, false);
        setControl();
        setData();
        setEvent();
        if (mUser != null) {
            setInfo(mUser);
        }
        return view;
    }

    private void setData() {


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

            valid();
            String firstname = edtFirstname.getText().toString().trim();
            String lastname = edtFirstname.getText().toString().trim();
            if (firstname.isEmpty() || lastname.isEmpty()) {
                showMessage("Name must not be empty!");
                return;
            }


            String email = edtEmail.getText().toString().trim();
            if (email.isEmpty()) {
                showMessage("email must not be empty!");
                return;
            }

            String username = edtUsername.getText().toString().trim();
            if (username.isEmpty()) {
                showMessage("username must not be empty!");
                return;
            }

            String phone = edtPhone.getText().toString().trim();
            if (phone.isEmpty()) {
                showMessage("phone must not be empty!");
                return;
            }

            User user = new User();
            user.setEmail(email);
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setPhone(phone);
            user.setUsername(username);

            MultipartBody.Part body = createBodyToUpload();
            if (body == null) return;
            ApiService.apiService.uploadUserImage(authorization, body).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    fileName = response.body();

                    ApiService.apiService.saveUser(user).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            ResponseBody responseObject;
                            if (response.code() != 200) {
                                Gson gson = new GsonBuilder().create();
                                try {
                                    responseObject = gson.fromJson(response.errorBody().string(), ResponseBody.class);
                                    showMessage(responseObject.getMessage());
                                } catch (IOException e) {
                                    // handle failure to read error
                                    showMessage(e.getMessage());
                                }
                                return;
                            }
                            responseObject = response.body();
                            showMessage(responseObject.getMessage());
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
            int id = mUser.getId();
            String firstname = edtFirstname.getText().toString().trim();
            String lastname = edtLastname.getText().toString().trim();
            if (firstname.isEmpty() || lastname.isEmpty()) {
                showMessage("Name must not be empty!");
                return;
            }


            String email = edtEmail.getText().toString().trim();
            if (email.isEmpty()) {
                showMessage("email must not be empty!");
                return;
            }

            String username = edtUsername.getText().toString().trim();
            if (username.isEmpty()) {
                showMessage("username must not be empty!");
                return;
            }

            String phone = edtPhone.getText().toString().trim();
            if (phone.isEmpty()) {
                showMessage("phone must not be empty!");
                return;
            }


            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setPhone(phone);
            user.setUsername(username);


            if (realPath == null || realPath.isEmpty()) {
                user.setImage(mUser.getImage());
                user.setPassword(mUser.getPassword());
                ApiService.apiService.saveUser(user).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody responseObject;
                        if (response.code() != 200) {
                            Gson gson = new GsonBuilder().create();
                            try {
                                responseObject = gson.fromJson(response.errorBody().string(), ResponseBody.class);
                                showMessage(responseObject.getMessage());
                            } catch (IOException e) {
                                // handle failure to read error
                                showMessage(e.getMessage());
                            }
                            return;
                        }
                        responseObject = response.body();
                        showMessage(responseObject.getMessage());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            } else {
                MultipartBody.Part body = createBodyToUpload();
                ApiService.apiService.uploadUserImage(authorization, body).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        fileName = response.body();

                        UpdateProfileRequest user = new UpdateProfileRequest();
                        user.setId(id);
                        user.setUsername(username);
                        user.setFirstName(firstname);
                        user.setLastName(lastname);
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

        });
        btnDelete.setOnClickListener(view1 -> {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Confirm")
                    .setMessage("Are you sure want to delete this user?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (mUser == null) return;
                            int id = mUser.getId();
                            ApiService.apiService.deleteUser(id).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    ResponseBody responseObject;
                                    if (response.code() != 200) {
                                        Gson gson = new GsonBuilder().create();
                                        try {
                                            responseObject = gson.fromJson(response.errorBody().string(), ResponseBody.class);
                                            showMessage(responseObject.getMessage());
                                        } catch (IOException e) {
                                            // handle failure to read error
                                            showMessage(e.getMessage());
                                        }
                                        return;
                                    }
                                    responseObject = response.body();
                                    showMessage(responseObject.getMessage());
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
            edtUsername.setText("");
            edtFirstname.setText("");
            edtEmail.setText("");
            edtLastname.setText("");
            edtPhone.setText("");
            edtUsername.setEnabled(true);
            imgUser.setImageResource(R.drawable.img_default_avatar);
        });
        imgUser.setOnClickListener(view1 -> {
            requestPermission();
        });

    }

    private void valid() {
    }

    private MultipartBody.Part createBodyToUpload() {
        File file;
        if (realPath == null || realPath.isEmpty()) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgUser.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            fileName = String.format("image%s.jpg", Calendar.getInstance().getTimeInMillis());
            file = MyApplication.createFileFromBitmap(getContext(), bitmap, fileName);
        } else {
            file = new File(realPath);
            String file_path = file.getAbsolutePath();//folder/image/123.png
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
                            imgUser.setImageBitmap(bitmap);
                        } else {
                            imgUser.setImageResource(R.drawable.ic_add_photo_alternate);
                        }
                    }
                });
    }

    public void setInfo(User user) {
        mUser = user;
        edtUsername.setText(user.getUsername());
        edtEmail.setText(user.getEmail());
        edtFirstname.setText(user.getFirstName());
        edtLastname.setText(user.getLastName());
        edtPhone.setText(user.getPhone());

        Picasso.get().load(ApiService.USER_IMAGE_URL + user.getImage())
                .placeholder(R.drawable.img_default_avatar)
                .error(R.drawable.img_default_avatar)
                .into(imgUser);
    }

    private void setControl() {
        edtUsername = view.findViewById(R.id.edt_username);
        edtFirstname = view.findViewById(R.id.edt_firstName);
        edtLastname = view.findViewById(R.id.edt_lastname);
        edtPhone = view.findViewById(R.id.edt_phone);
        edtEmail = view.findViewById(R.id.edt_gmail);


        imgUser = view.findViewById(R.id.img_User);
        btnInsert = view.findViewById(R.id.btn_insertUser);
        btnUpdate = view.findViewById(R.id.btn_updateUser);
        btnDelete = view.findViewById(R.id.btn_deleteUser);
        btnClear = view.findViewById(R.id.btn_clearUser);
    }

    public interface OnActionListener {
        void onActionCompleted(int action);
    }
}
