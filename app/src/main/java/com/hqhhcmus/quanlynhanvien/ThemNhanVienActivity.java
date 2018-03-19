package com.hqhhcmus.quanlynhanvien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.hqhhcmus.model.ChucVu;
import com.hqhhcmus.model.NhanVien;

public class ThemNhanVienActivity extends AppCompatActivity {
    EditText edtMaNV, edtTenNV;
    RadioButton rbtnNam;
    Button btnXoatrang, btnLuuNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);
        doAnhXa();
        addEvent();

    }

    private void addEvent() {
        btnXoatrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doXoaTrang();
            }
        });
        btnLuuNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doThemNhanVien();
            }
        });
    }

    private void doThemNhanVien() {
        if(edtMaNV.getText().toString().trim().isEmpty()||edtTenNV.getText().toString().trim().isEmpty()){
            if(edtMaNV.getText().toString().trim().isEmpty()){
                edtMaNV.requestFocus();
                Toast.makeText(this, "Không được bỏ trống!", Toast.LENGTH_SHORT).show();
            }
            if(edtTenNV.getText().toString().trim().isEmpty()){
                edtTenNV.requestFocus();
                Toast.makeText(this, "Không được bỏ trống!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            NhanVien nhanVien=new NhanVien();

            nhanVien.setMa(edtMaNV.getText()+"");
            nhanVien.setName(edtTenNV.getText()+"");
            nhanVien.setGioitinh(rbtnNam.isChecked());
            nhanVien.setChucvu(ChucVu.NHANVIEN);

            Intent intent=getIntent();
            Bundle bundle=new Bundle();
            bundle.putSerializable("NV",nhanVien);
            intent.putExtra("DATA", bundle);

            setResult(MainActivity.RESULT_THEM_NHANVIEN_THANH_CONG,intent);
            finish();
        }
    }

    private void doXoaTrang() {
        edtTenNV.setText("");
        edtMaNV.setText("");
        edtMaNV.requestFocus();
    }

    private void doAnhXa() {
        edtMaNV=findViewById(R.id.editMaNV);
        edtTenNV=findViewById(R.id.editTenNV);
        rbtnNam=findViewById(R.id.radNam);
        btnXoatrang=findViewById(R.id.buttonXoatrang);
        btnLuuNV=findViewById(R.id.buttonluunv);
    }
}
