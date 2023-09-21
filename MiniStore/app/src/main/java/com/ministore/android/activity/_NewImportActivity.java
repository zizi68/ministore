package com.ministore.android.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter._ImportDetailAdapter;
import com.ministore.android.adapter._ProductAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Brand;
import com.ministore.android.model.Category;
import com.ministore.android.model.ImportDetail;
import com.ministore.android.model.Product;
import com.ministore.android.model.ResponseObject;
import com.ministore.android.response.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _NewImportActivity extends AppCompatActivity {

    ListView lvImportDetail;
    ArrayList<ImportDetail> data = new ArrayList<>();
    _ImportDetailAdapter adapter;

    Button btnSave;
    public static TextView tvTotalPrice;
    TextView btnAdd;
    public static float totalPrice = 0;

    ArrayAdapter productAdapter;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_import);

        setControl();
        setEvent();
    }

    private void setEvent() {
        hienThi();
        adapter = new _ImportDetailAdapter(this, R.layout._item_new_import, data);
        lvImportDetail.setAdapter(adapter);

        lvImportDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openUpdateDialog(i, data.get(i));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsertDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.size() == 0) {
                    Toast.makeText(_NewImportActivity.this, "Product list is empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    String authorization = MyApplication.getAuthorization();
                    int userId = MyApplication.getUserId();
                    if (authorization == null) {
                        MyApplication.goToLoginActivity(getParent());
                        return;
                    }
                    ApiService.apiService.insertImportByUserId(authorization, userId, totalPrice, data).enqueue(new Callback<ResponseObject>() {
                        @Override
                        public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                            ResponseObject responseObject;
                            if (response.code() != 200) {
                                Gson gson = new GsonBuilder().create();
                                try {
                                    responseObject = gson.fromJson(response.errorBody().string(), ResponseObject.class);
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
                            totalPrice = 0;
                            hienThi();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseObject> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void openUpdateDialog(int index, ImportDetail importDetail) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout._dialog_new_import);

        Spinner spProduct = dialog.findViewById(R.id.spProduct);
        EditText edtPrice = dialog.findViewById(R.id.edtPrice);
        EditText edtQuantity = dialog.findViewById(R.id.edtQuantity);

        Button btnComfirm = dialog.findViewById(R.id.btnComfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        spProduct.setAdapter(productAdapter);

        edtPrice.setText(String.valueOf(importDetail.getPrice()));
        edtQuantity.setText(String.valueOf(importDetail.getQuantity()));

        ApiService.apiService.getListProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                productAdapter = new ArrayAdapter(_NewImportActivity.this, android.R.layout.simple_list_item_1, productList) {
                    @Nullable
                    @Override
                    public Object getItem(int position) {
                        return productList.get(position).getName();
                    }
                };
                spProduct.setAdapter(productAdapter);
                int id = importDetail.getProduct().getProductId();
                for(int i = 0; i < productList.size(); i++) {
                    if(productList.get(i).getProductId() == id) {
                        spProduct.setSelection(i);
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImportDetail newImportDetail = checkImportDetail(importDetail, edtPrice,edtQuantity, spProduct);
                if(newImportDetail == null) return;

                data.set(index, newImportDetail);
                showMessage("Update product successfully");
                dialog.dismiss();
                hienThi();
                adapter.notifyDataSetChanged();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void openInsertDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout._dialog_new_import);

        Spinner spProduct = dialog.findViewById(R.id.spProduct);
        EditText edtPrice = dialog.findViewById(R.id.edtPrice);
        EditText edtQuantity = dialog.findViewById(R.id.edtQuantity);

        Button btnComfirm = dialog.findViewById(R.id.btnComfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        spProduct.setAdapter(productAdapter);

        ApiService.apiService.getListProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                for(int i = 0; i < data.size(); i++) {
                    for(int j = 0; j < productList.size(); j++)
                        if(productList.get(j).getProductId() == data.get(i).getProduct().getProductId())
                            productList.remove(j);
                }
                productAdapter = new ArrayAdapter(_NewImportActivity.this, android.R.layout.simple_list_item_1, productList) {
                    @Nullable
                    @Override
                    public Object getItem(int position) {
                        return productList.get(position).getName();
                    }
                };
                spProduct.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImportDetail importDetail = checkImportDetail(null, edtPrice, edtQuantity, spProduct);
                if(importDetail == null) return;

                data.add(importDetail);
                showMessage("Add product successfully");
                dialog.dismiss();
                hienThi();
                adapter.notifyDataSetChanged();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private ImportDetail checkImportDetail(ImportDetail importDetail, EditText edtPrice, EditText edtQuantity, Spinner spProduct) {
        float price = Float.parseFloat(edtPrice.getText().toString().trim());
        if (price <= 0) {
            showMessage("Price should be greater than 0!");
            return null;
        }

        int quantity = Integer.parseInt(edtQuantity.getText().toString().trim());
        if (quantity <= 0) {
            showMessage("Quantity should be greater than 0!");
            return null;
        }

        Product p = productList.get(spProduct.getSelectedItemPosition());

        if(importDetail == null)
            importDetail = new ImportDetail();

        else
            totalPrice -= importDetail.getPrice()*importDetail.getQuantity();

        importDetail.setPrice(price);
        importDetail.setQuantity(quantity);
        importDetail.setProduct(p);
        totalPrice += price*quantity;
        return importDetail;
    }

    public static void hienThi() {
        tvTotalPrice.setText(String.format("%s đ", MyApplication.formatNumber(totalPrice)));
    }

    private void setControl() {
        lvImportDetail = findViewById(R.id.lvImportDetail);
        btnSave = findViewById(R.id.btnSave);
        btnAdd = findViewById(R.id.btnAdd);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
    }

}