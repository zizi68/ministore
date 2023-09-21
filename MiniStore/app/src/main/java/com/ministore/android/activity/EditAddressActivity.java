package com.ministore.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.AddressAdapter;
import com.ministore.android.adapter.PdwAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.fragment.PdwBottomSheetFragment;
import com.ministore.android.fragment.RemoveCartBottomSheetFragment;
import com.ministore.android.model.Address;
import com.ministore.android.model.Cart;
import com.ministore.android.model.District;
import com.ministore.android.model.Province;
import com.ministore.android.model.ResponseObject;
import com.ministore.android.model.Ward;
import com.ministore.android.response.ResponseBody;

import java.io.IOException;
import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAddressActivity extends AppCompatActivity {

    public static final String KEY_ADDRESS = "address";

    private Toolbar toolbar;
    private Address address;
    private TextView tvProvince, tvDistrict, tvWard;
    private EditText edtSpecificAddress;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        getAddressInfo();
        setControl();
        setEvent();
        loadAddressInfo();
    }

    private void loadAddressInfo() {
        if (address != null) {
            Ward ward = address.getWard();
            District district = ward.getDistrict();
            Province province = district.getProvince();

            tvProvince.setTag(province);
            tvDistrict.setTag(district);
            tvWard.setTag(ward);

            tvProvince.setText(String.format("%s", province.getProvinceName()));
            tvDistrict.setText(String.format("%s %s", district.getDistrictPrefix(), district.getDistrictName()));
            tvWard.setText(String.format("%s %s", ward.getWardPrefix(), ward.getWardName()));
            edtSpecificAddress.setText(address.getSpecificAddress());
        }
    }

    private void getAddressInfo() {
        Intent intent = getIntent();
        address = (Address) intent.getSerializableExtra(KEY_ADDRESS);
    }

    private void showProvinceBottomSheet() {
        PdwBottomSheetFragment fragment = PdwBottomSheetFragment.newInstance(null);
        fragment.setOnClickListener(object -> {
            if (object == null) return;
            if (object instanceof Province) {
                Province province = (Province) object;
                tvProvince.setTag(province);
                tvProvince.setText(String.format("%s", province.getProvinceName()));
                tvDistrict.setTag(null);
                tvDistrict.setText("");
                tvWard.setTag(null);
                tvWard.setText("");
                showDistrictBottomSheet();
            }
        });
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }

    private void showDistrictBottomSheet() {
        if (tvProvince.getTag() == null) {
            showProvinceBottomSheet();
            return;
        }
        PdwBottomSheetFragment fragment = PdwBottomSheetFragment.newInstance(tvProvince.getTag());
        fragment.setOnClickListener(object -> {
            if (object == null) return;
            if (object instanceof District) {
                District district = (District) object;
                tvDistrict.setTag(district);
                tvDistrict.setText(String.format("%s %s", district.getDistrictPrefix(), district.getDistrictName()));
                tvWard.setTag(null);
                tvWard.setText("");
                showWardBottomSheet();
            }
        });
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }

    private void showWardBottomSheet() {
        if (tvDistrict.getTag() == null) {
            showDistrictBottomSheet();
            return;
        }
        PdwBottomSheetFragment fragment = PdwBottomSheetFragment.newInstance(tvDistrict.getTag());
        fragment.setOnClickListener(object -> {
            if (object == null) return;
            if (object instanceof Ward) {
                Ward ward = (Ward) object;
                tvWard.setTag(ward);
                tvWard.setText(String.format("%s %s", ward.getWardPrefix(), ward.getWardName()));
            }
        });
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }

    private void setEvent() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        tvProvince.setOnClickListener(view -> {
            showProvinceBottomSheet();
        });
        tvDistrict.setOnClickListener(view -> {
            showDistrictBottomSheet();
        });
        tvWard.setOnClickListener(view -> {
            showWardBottomSheet();
        });
        btnConfirm.setOnClickListener(view -> {
            if (tvProvince.getTag() == null) {
                showMessage("Please select province!");
                return;
            }
            if (tvDistrict.getTag() == null) {
                showMessage("Please select district!");
                return;
            }
            if (tvWard.getTag() == null) {
                showMessage("Please select ward!");
                return;
            }
            String specificAddress = edtSpecificAddress.getText().toString().trim();
            if (specificAddress.isEmpty()) {
                showMessage("Please enter specific address!");
                return;
            }
            Ward ward = (Ward) tvWard.getTag();
            int userId = MyApplication.getUserId();
            String authorization = MyApplication.getAuthorization();
            if (address == null) {
                address = new Address();
            }
            address.setWard(ward);
            address.setSpecificAddress(specificAddress);
            ApiService.apiService.saveAddressByUserId(authorization, userId, address)
                    .enqueue(new Callback<ResponseObject>() {
                        @Override
                        public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                            ResponseObject responseObject;
                            if (response.code() == 200) {
                                responseObject = response.body();
                                showMessage(responseObject.getMessage());
                                finish();
                            }
                            else {
                                Gson gson = new GsonBuilder().create();
                                try {
                                    if (response.errorBody() == null) {
                                        showMessage(response.message());
                                        return;
                                    }
                                    responseObject = gson.fromJson(response.errorBody().string(), ResponseObject.class);
                                    showMessage(responseObject.getMessage());
                                }
                                catch (IOException e) {
                                    showMessage(e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseObject> call, Throwable t) {
                            showMessage(t.getMessage());
                        }
                    });
        });
    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        tvProvince = findViewById(R.id.tv_province);
        tvDistrict = findViewById(R.id.tv_district);
        tvWard = findViewById(R.id.tv_ward);
        edtSpecificAddress = findViewById(R.id.edt_specific_address);
        btnConfirm = findViewById(R.id.btn_confirm);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
    }

}