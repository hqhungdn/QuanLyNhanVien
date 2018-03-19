package com.hqhhcmus.quanlynhanvien;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.hqhhcmus.model.ChucVu;
import com.hqhhcmus.model.NhanVien;

public class SuaNhanVienActivity extends AppCompatActivity {
    EditText edtmaNV, edttenNV;
    RadioButton rdbtnNam;
    Button btnXoaTrang, btnLuuNV;
    NhanVien nhanVien=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);
        AnhXa();

        //Lấy thông tin nhân viên được gửi qua từ activity cha sau đó đưa vào form
        getDataFromFatherActivity();

        addEvents();
    }

    private void addEvents() {
        btnXoaTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtmaNV.setText("");
                edttenNV.setText("");
                edtmaNV.requestFocus();
            }
        });
        btnLuuNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lâys thông tin đã sửa từ form sau đó gửi lại về cho activity cha (DanhSachNhanVienActivity)
                updateIntoFatherActivity();
            }
        });
    }

    private void updateIntoFatherActivity() {
        if(edtmaNV.getText().toString().trim().isEmpty()||edttenNV.getText().toString().trim().isEmpty())
        {
            if(edtmaNV.getText().toString().trim().isEmpty())
            {
                edtmaNV.requestFocus();
                edtmaNV.setError("Không được bỏ trống!");
            }
            else
            {
                edttenNV.requestFocus();
                edttenNV.setError("Không được bỏ trống!");
            }
        }
        else{
            nhanVien.setMa(edtmaNV.getText().toString());
            nhanVien.setName(edttenNV.getText().toString());
            nhanVien.setGioitinh(rdbtnNam.isChecked());
            nhanVien.setChucvu(ChucVu.NHANVIEN);

            Intent intent=getIntent();
            Bundle bundle=new Bundle();
            bundle.putSerializable("NHANVIEN",nhanVien);
            intent.putExtra("DATA",bundle);
            setResult(MainActivity.RESULT_SUA_NHANVIEN_THANHCONG,intent);
            Toast.makeText(this, "Sửa Nhân Viên Thành công!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void getDataFromFatherActivity(){

        //Lấy data từ activity cha
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        nhanVien= (NhanVien) bundle.getSerializable("NHANVIEN");

        //đưa thông tin vừa lấy được vào form điền
        edtmaNV.setText(nhanVien.getMa());
        edttenNV.setText(nhanVien.getName());
        rdbtnNam.setChecked(nhanVien.isGioitinh());
    }
    private void AnhXa() {
        edtmaNV=findViewById(R.id.editMaNV);
        edttenNV=findViewById(R.id.editTenNV);
        rdbtnNam=findViewById(R.id.radNam);
        btnXoaTrang=findViewById(R.id.buttonXoatrang);
        btnLuuNV=findViewById(R.id.buttonluunv);
    }
}
