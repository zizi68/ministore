package com.ministore.android.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter._ImportDetailAdapter;
import com.ministore.android.adapter._OrderDetailAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.fragment._OrderFragment;
import com.ministore.android.model.Import;
import com.ministore.android.model.ImportDetail;
import com.ministore.android.model.Order;
import com.ministore.android.model.OrderDetail;
import com.ministore.android.model.ResponseObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _ImportDetailActivity extends AppCompatActivity {

    TextView tvUsername, tvAddress, tvDate, tvTotalPrice;

    ListView lvImportDetail;
    List<ImportDetail> list;
    _ImportDetailAdapter adapter;

    Import imports;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_import_detail);

        setControl();
        Intent intent = getIntent();
        imports = (Import) intent.getSerializableExtra("imports");

        setEvent();
    }

    private void setEvent() {
        String authorization = MyApplication.getAuthorization();
        if (authorization == null) {
            MyApplication.goToLoginActivity(this);
            return;
        }

        tvUsername.setText(imports.getUser().getUsername());

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String day = formatter.format(imports.getDate());
        tvDate.setText(day);


        tvTotalPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(imports.getTotalPrice()) + "đ");

        ApiService.apiService.getImportDetailByImportId(authorization, imports.getId()).enqueue(new Callback<List<ImportDetail>>() {
            @Override
            public void onResponse(Call<List<ImportDetail>> call, Response<List<ImportDetail>> response) {
                list = response.body();
                adapter = new _ImportDetailAdapter(getApplication(), R.layout.item_order_detail, list);
                lvImportDetail.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ImportDetail>> call, Throwable t) {

            }
        });

    }

    private void setControl() {
        tvUsername = findViewById(R.id.tvUsernameO);
        tvDate = findViewById(R.id.tvDateO);
        tvTotalPrice = findViewById(R.id.tvTotalPriceO);
        lvImportDetail = findViewById(R.id.lvImportDetail);
    }
}
