package com.ministore.android.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Brand;
import com.ministore.android.model.Category;
import com.ministore.android.model.Product;
import com.ministore.android.model.ResponseObject;
import com.ministore.android.response.ResponseBody;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class _ProductInfoFragment extends Fragment {

    public static final String KEY_PRODUCT = "product";
    private static final String DEFAULT_IMAGE_NAME = "default.png";
    private Product mProduct;

    public static final int INSERT_ACTION = 1;
    public static final int UPDATE_ACTION = 2;
    public static final int DELETE_ACTION = 3;
    private String realPath;
    private String fileName;

    private View view;
    private EditText edtId, edtName, edtDescription, edtCaculationUnit, edtDiscount, edtPrice, edtQuantity, edtSoldQuantity, edtSpecification;
    private CheckBox cbStatus;
    private Spinner spCategory, spBrand;
    private ImageView imgProduct;
    private Button btnInsert, btnUpdate, btnDelete, btnClear;

    private List<Category> listCategory;
    private List<Brand> listBrand;

    ArrayAdapter adapterCategory, adapterBrand;

    public interface OnActionListener {
        void onActionCompleted(int action);
    }

    private OnActionListener listener;

    public _ProductInfoFragment setListener(OnActionListener listener) {
        this.listener = listener;
        return this;
    }

    public _ProductInfoFragment() {
        // Required empty public constructor
    }

    public static _ProductInfoFragment newInstance(Product product) {
        _ProductInfoFragment fragment = new _ProductInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(KEY_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout._fragment_product_info, container, false);
        setControl();
        setData();
        setEvent();
        if (mProduct != null) {
            setInfo(mProduct);
        }
        return view;
    }

    private void setData() {
        listCategory = new ArrayList<>();
        listBrand = new ArrayList<>();

        ApiService.apiService.getListCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                listCategory = response.body();
                adapterCategory = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listCategory) {
                    @Nullable
                    @Override
                    public Object getItem(int position) {
                        return listCategory.get(position).getName();
                    }
                };
                spCategory.setAdapter(adapterCategory);
                if(mProduct != null) {
                    int id = mProduct.getCategory().getCategoryId();
                    for(int i = 0; i < listCategory.size(); i++) {
                        if(listCategory.get(i).getCategoryId() == id) {
                            spCategory.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });

        ApiService.apiService.getListBrands().enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                listBrand = response.body();
                adapterBrand = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listBrand) {
                    @Nullable
                    @Override
                    public Object getItem(int position) {
                        return listBrand.get(position).getName();
                    }
                };
                spBrand.setAdapter(adapterBrand);
                if(mProduct != null) {
                    int id = mProduct.getBrand().getBrandId();
                    for(int i = 0; i < listBrand.size(); i++) {
                        if(listBrand.get(i).getBrandId() == id) {
                            spBrand.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {

            }
        });

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

            Product product = checkProduct();
            if(product == null) return;

            if (realPath == null || realPath.isEmpty()) {
                product.setImage(DEFAULT_IMAGE_NAME);
                addProduct(product);
            }
            else {
                MultipartBody.Part body = createBodyToUpload();
                if (body == null) return;
                ApiService.apiService.uploadProductImage(authorization, body).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        fileName = response.body();
                        product.setImage(fileName);
                        addProduct(product);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        showMessage(t.getMessage());
                    }
                });
            }
        });
        btnUpdate.setOnClickListener(view1 -> {
            String authorization = MyApplication.getAuthorization();
            if (authorization == null) {
                MyApplication.goToLoginActivity(getActivity());
                return;
            }

            Product product = checkProduct();
            if(product == null) return;

            int id = mProduct.getProductId();
            product.setProductId(id);
            if (realPath == null || realPath.isEmpty()) {
                product.setImage(mProduct.getImage());
                updateProduct(id, product);
            }
            else {
                MultipartBody.Part body = createBodyToUpload();
                if (body == null) return;
                ApiService.apiService.uploadProductImage(authorization, body).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        fileName = response.body();
                        product.setImage(fileName);
                        updateProduct(id, product);
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
                    .setMessage("Are you sure want to delete this category?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (mProduct == null) return;
                            int id = mProduct.getProductId();
                            ApiService.apiService.deleteProduct(id).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    ResponseBody responseObject;
                                    if (response.code() != 200) {
                                        Gson gson = new GsonBuilder().create();
                                        try {
                                            responseObject = gson.fromJson(response.errorBody().string(), ResponseBody.class);
                                            showMessage(responseObject.getMessage());
                                        }
                                        catch (IOException e) {
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
            edtName.setText("");
            edtDescription.setText("");
            imgProduct.setImageResource(R.drawable.img_cute_book_pencil_cartoon);
        });
        imgProduct.setOnClickListener(view1 -> {
            requestPermission();
        });

    }

    private void updateProduct(int id, Product product) {
        ApiService.apiService.updateProduct(id, product).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseObject;
                if (response.code() != 200) {
                    Gson gson = new GsonBuilder().create();
                    try {
                        responseObject = gson.fromJson(response.errorBody().string(), ResponseBody.class);
                        showMessage(responseObject.getMessage());
                    }
                    catch (IOException e) {
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

    private void addProduct(Product product) {
        ApiService.apiService.insertProduct(product).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseObject;
                if (response.code() != 200) {
                    Gson gson = new GsonBuilder().create();
                    try {
                        responseObject = gson.fromJson(response.errorBody().string(), ResponseBody.class);
                        showMessage(responseObject.getMessage());
                    }
                    catch (IOException e) {
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

    private Product checkProduct() {

        String name = edtName.getText().toString().trim();
        if (name.isEmpty()) {
            showMessage("Name must not be empty!");
            return null;
        }
        double price = Double.parseDouble(edtPrice.getText().toString().trim());
        if (price <= 0) {
            showMessage("Price should be greater than 0!");
            return null;
        }
        String caculationUnit = edtCaculationUnit.getText().toString().trim();
        if (caculationUnit.isEmpty()) {
            showMessage("Caculation unit must not be empty!");
            return null;
        }
        int quantity = Integer.parseInt(edtQuantity.getText().toString().trim());
        if (quantity <= 0) {
            showMessage("Quantity should be greater than 0!");
            return null;
        }
        int soldQuantity = Integer.parseInt(edtSoldQuantity.getText().toString().trim());
        boolean status = cbStatus.isChecked() ? true : false;
        String specification = edtSpecification.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        int discount = Integer.parseInt(edtDiscount.getText().toString().trim());
        Category c = listCategory.get(spCategory.getSelectedItemPosition());
        Brand b = listBrand.get(spBrand.getSelectedItemPosition());

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setSpecification(specification);
        product.setDiscount(discount);
        product.setPrice(price);
        product.setCalculationUnit(caculationUnit);
        product.setQuantity(quantity);
        product.setSoldQuantity(soldQuantity);
        product.setStatus(status);
        product.setCategory(c);
        product.setBrand(b);
        return product;
    }

    private MultipartBody.Part createBodyToUpload() {
        File file = new File(realPath);
        String file_path = file.getAbsolutePath();//folder/image/123.png
        String[] strings = file_path.split("/");
        fileName = strings[strings.length - 1];
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
                            imgProduct.setImageBitmap(bitmap);
                        } else {
                            imgProduct.setImageResource(R.drawable.ic_add_photo_alternate);
                        }
                    }
                });
    }

    public void setInfo(Product product) {
        mProduct = product;
        edtId.setText(String.valueOf(product.getProductId()));
        edtName.setText(product.getName());
        edtDescription.setText(product.getDescription());
        edtDiscount.setText(String.valueOf(product.getDiscount()));
        edtPrice.setText(String.valueOf(product.getPrice()));
        edtQuantity.setText(String.valueOf(product.getQuantity()));
        edtSoldQuantity.setText(String.valueOf(product.getSoldQuantity()));
        edtSpecification.setText(product.getSpecification());
        edtCaculationUnit.setText(product.getCalculationUnit());
        cbStatus.setChecked(product.isStatus());

        Picasso.get().load(ApiService.PRODUCT_IMAGE_URL + product.getImage())
                .placeholder(R.drawable.ic_add_photo_alternate)
                .error(R.drawable.ic_add_photo_alternate)
                .into(imgProduct);
    }

    private void setControl() {
        edtId = view.findViewById(R.id.edt_idP);
        edtName = view.findViewById(R.id.edt_nameP);
        edtDescription = view.findViewById(R.id.edt_descriptionP);
        edtDiscount = view.findViewById(R.id.edt_discountP);
        edtPrice = view.findViewById(R.id.edt_priceP);
        edtQuantity = view.findViewById(R.id.edt_quantityP);
        edtSoldQuantity = view.findViewById(R.id.edt_sold_quantityP);
        edtSpecification = view.findViewById(R.id.edt_specificationP);
        edtCaculationUnit = view.findViewById(R.id.edt_caculationUnitP);
        cbStatus = view.findViewById(R.id.cb_statusP);
        spBrand = view.findViewById(R.id.sp_brandP);
        spCategory = view.findViewById(R.id.sp_categoryP);

        imgProduct = view.findViewById(R.id.img_productP);
        btnInsert = view.findViewById(R.id.btn_insertP);
        btnUpdate = view.findViewById(R.id.btn_updateP);
        btnDelete = view.findViewById(R.id.btn_deleteP);
        btnClear = view.findViewById(R.id.btn_clearP);
    }
}