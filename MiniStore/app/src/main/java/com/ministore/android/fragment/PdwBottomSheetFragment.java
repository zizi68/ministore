package com.ministore.android.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.PdwAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.District;
import com.ministore.android.model.Province;
import com.ministore.android.model.Ward;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PdwBottomSheetFragment extends BottomSheetDialogFragment implements PdwAdapter.OnItemClickListener {

    private static final String KEY_OBJECT = "object";

    private static final int STATE_PROVINCE = 0;
    private static final int STATE_DISTRICT = 1;
    private static final int STATE_WARD = 2;
    private static int currentState = STATE_PROVINCE;

    private Object mObject;

    private View view;
    private RecyclerView rcvPdw;
    private PdwAdapter pdwAdapter;

    public interface onClickListener {
        void onItemClick(Object object);
    }

    private onClickListener onClickListener;

    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private PdwBottomSheetFragment() {

    }

    public static PdwBottomSheetFragment newInstance(Object object) {
        PdwBottomSheetFragment fragment = new PdwBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_OBJECT, (Serializable) object);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mObject = bundle.get(KEY_OBJECT);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_pdw, null);
        bottomSheetDialog.setContentView(view);

        setControl();
        loadData();
        setEvent();

        BottomSheetBehavior behavior = BottomSheetBehavior.from((View) view.getParent());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        return bottomSheetDialog;
    }

    private void setEvent() {

    }

    private void loadData() {
        String authorization = MyApplication.getAuthorization();
        if (mObject == null) {
            ApiService.apiService.getAllProvince(authorization)
                    .enqueue(new Callback<List<Province>>() {
                        @Override
                        public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                            List<Province> provinceList = response.body();
                            if (provinceList == null || provinceList.isEmpty()) return;
                            List<Object> objectList = new ArrayList<>(provinceList);
                            pdwAdapter = new PdwAdapter(objectList);
                            pdwAdapter.setOnItemClickListener(PdwBottomSheetFragment.this);
                            rcvPdw.setAdapter(pdwAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<Province>> call, Throwable t) {

                        }
                    });
        }
        else if (mObject instanceof Province) {
            Province province = (Province) mObject;
            int id = province.getProvinceId();
            ApiService.apiService.getAllDistrictByProvinceId(authorization, id)
                    .enqueue(new Callback<List<District>>() {
                        @Override
                        public void onResponse(Call<List<District>> call, Response<List<District>> response) {
                            List<District> districtList = response.body();
                            if (districtList == null || districtList.isEmpty()) return;
                            List<Object> objectList = new ArrayList<>(districtList);
                            pdwAdapter = new PdwAdapter(objectList);
                            pdwAdapter.setOnItemClickListener(PdwBottomSheetFragment.this);
                            rcvPdw.setAdapter(pdwAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<District>> call, Throwable t) {

                        }
                    });
        }
        else if (mObject instanceof District) {
            District district = (District) mObject;
            int id = district.getDistrictId();
            ApiService.apiService.getAllWardByDistrictId(authorization, id)
                    .enqueue(new Callback<List<Ward>>() {
                        @Override
                        public void onResponse(Call<List<Ward>> call, Response<List<Ward>> response) {
                            List<Ward> wardList = response.body();
                            if (wardList == null || wardList.isEmpty()) return;
                            List<Object> objectList = new ArrayList<>(wardList);
                            pdwAdapter = new PdwAdapter(objectList);
                            pdwAdapter.setOnItemClickListener(PdwBottomSheetFragment.this);
                            rcvPdw.setAdapter(pdwAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<Ward>> call, Throwable t) {

                        }
                    });
        }
    }

    private void setControl() {
        rcvPdw = view.findViewById(R.id.rcv_pdw);

        rcvPdw.setHasFixedSize(true);
        rcvPdw.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        rcvSelectedItems.setFocusable(false);
//        rcvSelectedItems.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvPdw.addItemDecoration(itemDecoration);
    }

    @Override
    public void onClick(Object object) {
        if (onClickListener != null) {
            onClickListener.onItemClick(object);
        }
        this.dismiss();
    }
}
