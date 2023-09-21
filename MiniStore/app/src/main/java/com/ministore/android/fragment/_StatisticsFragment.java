package com.ministore.android.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.MyItem;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _StatisticsFragment extends Fragment {

    private static final int CREATE_FILE = 1;
    View mView;
    BarChart chartReport;
    Button btnReport;

    List<MyItem> dataList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout._fragment_statistics, container, false);

        setControl();
        getData();
        setEvent();

        return mView;
    }

    private void setEvent() {
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
    }

    private void checkPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                createFile();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void createFile() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "report-order");
        startActivityForResult(intent, CREATE_FILE);
    }

    public void getData() {
        String authorization = MyApplication.getAuthorization();
        if (authorization == null) {
            MyApplication.goToLoginActivity(getActivity());
            return;
        }
        ApiService.apiService.getReport(authorization).enqueue(new Callback<List<MyItem>>() {
            @Override
            public void onResponse(Call<List<MyItem>> call, Response<List<MyItem>> response) {
                dataList = response.body();
                ArrayList<BarEntry> barEntries = new ArrayList<>();

                for (int i = 0; i < dataList.size(); i++) {
                    barEntries.add(new BarEntry(i, dataList.get(i).getValue()));
                }

                ArrayList<String> labels = new ArrayList<String>();
                for (int i = 0; i < dataList.size(); i++) {
                    labels.add(dataList.get(i).getLabel());
                }

                XAxis xAxis = chartReport.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setLabelCount(labels.size());
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

                BarDataSet barDataSet = new BarDataSet(barEntries, "Date");

                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                chartReport.setData(new BarData(barDataSet));
                chartReport.animateY(1000);
                chartReport.getDescription().setText("Revenue in recent 7 days");
                chartReport.getDescription().setTextColor(Color.BLUE);
            }

            @Override
            public void onFailure(Call<List<MyItem>> call, Throwable t) {

            }
        });
    }

    public Bitmap getBitmapFromView(View view) {
        // Định nghĩa một Bitmap với cùng kích cỡ với view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        // Gắn một canvas vào
        Canvas canvas = new Canvas(returnedBitmap);

        // Lấy background của view
        Drawable drawable = view.getBackground();
        if (drawable != null) {
            // nếu có bg thì vẽ lên canvas
            drawable.draw(canvas);
        }
        else {
            // nếu không có bg thì vẽ bg trắng lên canvas
            canvas.drawColor(Color.WHITE);
        }

        // vẽ view lên canvas
        view.draw(canvas);
        return returnedBitmap;
    }

    public Image getImageFromView(View view) {
        Bitmap bitmap = getBitmapFromView(view);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();

        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CREATE_FILE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if(data != null) {
                uri = data.getData();

                ParcelFileDescriptor pdf = null;
                try {
                    pdf = getActivity().getContentResolver().openFileDescriptor(uri, "w");
                    FileOutputStream fileOutputStream = new FileOutputStream(pdf.getFileDescriptor());

                    PdfWriter writer = new PdfWriter(fileOutputStream);
                    PdfDocument pdfDocument = new PdfDocument(writer);
                    Document document = new Document(pdfDocument);

                    Paragraph title = new Paragraph("ORDER STATUS STATISTICS");
                    title.setTextAlignment(TextAlignment.CENTER);
                    title.setBold();

                    // Creating a table
                    float [] pointColumnWidths = {200F, 150F};
                    Table table = new Table(pointColumnWidths);
                    table.setTextAlignment(TextAlignment.CENTER);
                    table.setHorizontalAlignment(HorizontalAlignment.CENTER);

                    // Adding cells to the table
                    table.addCell(new Cell().add(new Paragraph("Status").setTextAlignment(TextAlignment.CENTER)));
                    table.addCell(new Cell().add(new Paragraph("Quantity").setTextAlignment(TextAlignment.CENTER)));

                    for (int i = 0; i < dataList.size(); i++) {
                        table.addCell(new Cell().add(new Paragraph(dataList.get(i).getLabel()).setTextAlignment(TextAlignment.LEFT)));
                        table.addCell(new Cell().add(new Paragraph(dataList.get(i).getValue() + "").setTextAlignment(TextAlignment.RIGHT)));
                    }

                    View report = mView.findViewById(R.id.chartReport);
                    Image image = getImageFromView(report);

                    document.add(title);
                    document.add(table);
                    document.add(image);

                    document.close();

                    //CustomToast.makeCustomToast(getActivity(), R.drawable.ic_check, "PDF is saved successfully").show();
                    Toast.makeText(getContext(), "Pdf is saved successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex) {
                    try {
                        DocumentsContract.deleteDocument(getActivity().getContentResolver(), uri);
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    ex.printStackTrace();
                }
            }
        }
    }

    private void setControl() {
        chartReport = mView.findViewById(R.id.chartReport);
        btnReport = mView.findViewById(R.id.btnReport);
    }
}
