package com.hqhhcmus.quanlynhanvien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hqhhcmus.adapter.NhanVienAdapter;
import com.hqhhcmus.model.ChucVu;
import com.hqhhcmus.model.NhanVien;
import com.hqhhcmus.model.PhongBan;

import java.util.ArrayList;

public class ThietLapTruongPhoPhongActivity extends AppCompatActivity {
    ListView listvTruongPhong, listvPhoPhong;
    ArrayList<NhanVien> listTruongPhong=new ArrayList<>();
    ArrayList<NhanVien> listPhoPhong=new ArrayList<>();
    ArrayAdapter<NhanVien> adapterTruongphong, adapterPhophong;
    PhongBan phongBan;
    Button btnOK;
    int lastchecked=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_lap_truong_pho_phong);
        AnhXa();
        TabSetup();
    }

    private void AnhXa() {

        btnOK=findViewById(R.id.buttonOK);
        listvTruongPhong=findViewById(R.id.listviewTruongPhong);
        listvTruongPhong.setTextFilterEnabled(true);
        listvTruongPhong.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listvPhoPhong=findViewById(R.id.listviewPhoPhong);
        listvPhoPhong.setTextFilterEnabled(true);
        listvPhoPhong.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listPhoPhong=new ArrayList<>();
        adapterPhophong=new ArrayAdapter<NhanVien>(this,android.R.layout.simple_list_item_multiple_choice,listPhoPhong);
        listvPhoPhong.setAdapter(adapterPhophong);

        listTruongPhong=new ArrayList<>();
        adapterTruongphong=new ArrayAdapter<NhanVien>(this,android.R.layout.simple_list_item_single_choice,listTruongPhong);
        listvTruongPhong.setAdapter(adapterTruongphong);


        //lấy thông tin phòng ban đc gửi từ MainActivity
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        phongBan= (PhongBan) bundle.getSerializable("PHONGBAN");
        addNVtoListTPP(phongBan);
        adapterTruongphong.notifyDataSetChanged();
        adapterPhophong.notifyDataSetChanged();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Lấy ra vị trí nhân viên được checked
                //duyệt listTruongphong sau đó kiểm tra trùng với vitri checked thì set thành TRUONGPHONG
                //không trùng thì set thanh NHANVIEN
                for(int i=0;i<listTruongPhong.size();i++){
                    if(i!=listvTruongPhong.getCheckedItemPosition()){
                        listTruongPhong.get(i).setChucvu(ChucVu.NHANVIEN);
                        phongBan.getListNhanvien().get(i).setChucvu(ChucVu.NHANVIEN);
                    }
                    else{
                        listTruongPhong.get(i).setChucvu(ChucVu.TRUONGPHONG);
                        phongBan.getListNhanvien().get(i).setChucvu(ChucVu.TRUONGPHONG);
                    }
                }

                //Pho phong la mutilchoice nên đưa về các vitri được check dùng getcheckeditempositions
                //duyệt listphophong kiểm tra vitri trung với vitrichecked thì đổi thành PHOPHONG, nếu là TRUONGPHONG thì không đổi
                SparseBooleanArray checkedPhophong=listvPhoPhong.getCheckedItemPositions();
                for(int i=0;i<listvPhoPhong.getCount();i++){
                    if(checkedPhophong.get(i)&&listPhoPhong.get(i).getChucvu()==ChucVu.NHANVIEN){
                        listPhoPhong.get(i).setChucvu(ChucVu.PPHOPHONG);
                    }
                }

                //Xóa danh sách nhan vine phong ban sau đó cập nhật lại
                phongBan.getListNhanvien().clear();
                phongBan.getListNhanvien().addAll(listPhoPhong);

                //UPdate cho mainActivity
                doUpdateforMain();

            }
        });
    }

    private void doUpdateforMain() {
        Intent intent=getIntent();
        Bundle bundle=new Bundle();
        bundle.putSerializable("PHONGBAN",phongBan);
        intent.putExtra("DATA",bundle);
        setResult(MainActivity.RESULT_THIET_LAP_LANH_DAO_THANH_CONG,intent);
         finish();
    }

    private void addNVtoListTPP(PhongBan phongBan) {
        listTruongPhong.clear();
        listTruongPhong.addAll(phongBan.getListNhanvien());

        listPhoPhong.clear();
        listPhoPhong.addAll(listTruongPhong);
    }

    private void TabSetup() {
        TabHost tabHost=findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tabSpec;

        tabSpec=tabHost.newTabSpec("tab1");
        tabSpec.setContent(R.id.tabTruongPhong);
        tabSpec.setIndicator("Trưởng phòng");
        tabHost.addTab(tabSpec);

        tabSpec=tabHost.newTabSpec("tab2");
        tabSpec.setContent(R.id.tabPhoPhong);
        tabSpec.setIndicator("Phó Phòng");
        tabHost.addTab(tabSpec);


    }
}
