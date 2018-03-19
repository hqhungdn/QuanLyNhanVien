package com.hqhhcmus.quanlynhanvien;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hqhhcmus.adapter.PhongBanAdapter;
import com.hqhhcmus.model.ChucVu;
import com.hqhhcmus.model.NhanVien;
import com.hqhhcmus.model.PhongBan;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ACTIVITY_THEM_NHANVIEN = 1;
    public static final int RESULT_THEM_NHANVIEN_THANH_CONG = 2;
    private static final int REQUEST_DANH_SACH_NHANVIEN = 3;
    private static final int REQUEST_THIET_LAP_TRUONG_PHO_PHONG = 4;
    public static final int RESULT_THIET_LAP_LANH_DAO_THANH_CONG =5 ;
    public static final int REQUEST_SUA_NHANVIEN = 6;
    public static final int RESULT_SUA_NHANVIEN_THANHCONG = 7;
    public static final int RESULT_CAPNHAT_DANHSACH_NHANVIEN_THANHCONG = 8;
    public static final int REQUEST_CHUYEN_PHONG_BAN = 9;
    EditText edtMaPB, edtTenPB;
    Button btnLuuPB;
    ListView listViewPB;
    public static ArrayList<PhongBan> listPhongban=new ArrayList<>();
    PhongBanAdapter adapterPhongban;
    PhongBan phongBanseleted;
    int vitri=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doAnhxa();
        fakeData();
        addEvent();
    }

    private void addEvent() {
        btnLuuPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doThemPhongBan();

            }
        });
        listViewPB.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                phongBanseleted=listPhongban.get(position);
                vitri=position;
                return false;
            }
        });
    }

    private void doThemPhongBan() {
        PhongBan phongBan=new PhongBan();
        String mapb=edtMaPB.getText().toString();
        String tenpb=edtTenPB.getText().toString();
        boolean checkma=false,checkten=false;
        if(edtTenPB.getText().toString().trim().isEmpty()||edtMaPB.getText().toString().trim().isEmpty()){
            if(edtMaPB.getText().toString().trim().isEmpty()){
                edtMaPB.requestFocus();
                Toast.makeText(this, "Không được để trống!", Toast.LENGTH_SHORT).show();
            }
            else{
                edtTenPB.requestFocus();
                Toast.makeText(this, "Không được để trống!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            for(int i=0;i<listPhongban.size();i++){
                if(mapb.trim().equalsIgnoreCase(listPhongban.get(i).getMa())){
                    checkma=true;
                    break;
                }
                if(tenpb.trim().equalsIgnoreCase(listPhongban.get(i).getName())){
                    checkten=true;
                    break;
                }
            }
            if(checkma||checkten){
                if(checkma){
                    Toast.makeText(this, "Mã phòng ban đã tồn tại!", Toast.LENGTH_SHORT).show();
                    edtMaPB.requestFocus();
                    edtMaPB.selectAll();
                }
                if(checkten){
                    Toast.makeText(this, "Tên phòng ban đã tồn tại!", Toast.LENGTH_SHORT).show();
                    edtTenPB.requestFocus();
                    edtTenPB.selectAll();

                }
            }
            else{
                phongBan=new PhongBan(mapb,tenpb);
                listPhongban.add(phongBan);
                Toast.makeText(this, "Đã thêm "+ tenpb+".", Toast.LENGTH_LONG).show();
                edtMaPB.setText("");
                edtTenPB.setText("");
                edtMaPB.requestFocus();
                adapterPhongban.notifyDataSetChanged();
            }
        }
    }

    private void fakeData() {
        PhongBan phongBan=new PhongBan("PB1","Phòng kỹ thuật");
        listPhongban.add(phongBan);
        phongBan.themNhanvien(new NhanVien("NV1","HOàng QUốc Hưng",true));
        phongBan.themNhanvien(new NhanVien("NV1","Phan quốc tèo",true));
        phongBan.themNhanvien(new NhanVien("NV1","Nguyễn văn bằng",true));
        phongBan.themNhanvien(new NhanVien("NV1","phùng xuân hòa",true));
        listPhongban.add(new PhongBan("PB2", "Phòng dịch vụ"));
        listPhongban.add(new PhongBan("PB3", "Tài Chính"));
        listPhongban.add(new PhongBan("PB4", "Phòng kế hoạch"));

    }

    private void doAnhxa() {
        edtMaPB=findViewById(R.id.editTextMaphongban);
        edtTenPB=findViewById(R.id.editTextTenphongban);
        listViewPB=findViewById(R.id.listViewPhongban);
        btnLuuPB=findViewById(R.id.buttonLuuPB);

        adapterPhongban=new PhongBanAdapter(MainActivity.this,R.layout.layout_item_custom,listPhongban);
        listViewPB.setAdapter(adapterPhongban);
        registerForContextMenu(listViewPB);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu_phongban,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuthemnv:
                doThemnhanvien();
                break;
            case R.id.menudsnhanvien:
                doDanhsachnhanvien();
                break;
            case R.id.menulaptruongphophong:
                doLapTruongPhoPhong();
                break;
            case R.id.menuxoaphongban:
                doXoaPhongBan();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void doXoaPhongBan() {
        final AlertDialog.Builder builder =new AlertDialog.Builder(this, android.R.style.Theme_Holo_Dialog_NoActionBar);
        builder.setIcon(R.drawable.delete_20x20);
        builder.setMessage("Bạn có chắc chắn muốn xóa ["+phongBanseleted.getName()+"]");
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listPhongban.remove(phongBanseleted);
                adapterPhongban.notifyDataSetChanged();
            }
        });
        builder.show();

    }

    private void doLapTruongPhoPhong() {
        Intent intent = new Intent(MainActivity.this,ThietLapTruongPhoPhongActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("PHONGBAN",phongBanseleted);
        intent.putExtra("DATA",bundle);
        startActivityForResult(intent,REQUEST_THIET_LAP_TRUONG_PHO_PHONG);
    }

    private void doDanhsachnhanvien() {
        Intent intent=new Intent(this,DanhSachNhanVienActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("PHONGBAN",phongBanseleted);
        intent.putExtra("DATA",bundle);
        startActivityForResult(intent,REQUEST_DANH_SACH_NHANVIEN);
    }

    private void doThemnhanvien() {
        Intent intent=new Intent(MainActivity.this, ThemNhanVienActivity.class);
        startActivityForResult(intent,REQUEST_CODE_ACTIVITY_THEM_NHANVIEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ACTIVITY_THEM_NHANVIEN && resultCode==MainActivity.RESULT_THEM_NHANVIEN_THANH_CONG){
            Bundle bundle=data.getBundleExtra("DATA");

            NhanVien nhanVien= (NhanVien) bundle.getSerializable("NV");
            phongBanseleted.themNhanvien(nhanVien);
            adapterPhongban.notifyDataSetChanged();
        }
        if(requestCode==REQUEST_THIET_LAP_TRUONG_PHO_PHONG && resultCode==MainActivity.RESULT_THIET_LAP_LANH_DAO_THANH_CONG){
            Bundle bundle=data.getBundleExtra("DATA");
            PhongBan phongBan= (PhongBan) bundle.getSerializable("PHONGBAN");

            //Cập nhật lại phòng ban ( phongbanselected có vị trí là vitri)
            listPhongban.set(vitri,phongBan);
            adapterPhongban.notifyDataSetChanged();

        }
        if(requestCode==MainActivity.REQUEST_DANH_SACH_NHANVIEN && resultCode==MainActivity.RESULT_CAPNHAT_DANHSACH_NHANVIEN_THANHCONG){
            Bundle bundle=data.getBundleExtra("DATA");
            PhongBan phongBan= (PhongBan) bundle.getSerializable("PHONGBAN");
            phongBanseleted.getListNhanvien().clear();
            phongBanseleted.getListNhanvien().addAll(phongBan.getListNhanvien());
            adapterPhongban.notifyDataSetChanged();
        }
    }

    //Tạo hàm này để tất cả các activity khác đều truy xuất được listPhongban
    public static ArrayList<PhongBan> getListPhongban(){
        return listPhongban;
    }
}
