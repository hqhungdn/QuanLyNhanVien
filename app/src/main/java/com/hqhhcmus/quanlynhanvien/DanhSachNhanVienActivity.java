package com.hqhhcmus.quanlynhanvien;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hqhhcmus.adapter.NhanVienAdapter;
import com.hqhhcmus.model.NhanVien;
import com.hqhhcmus.model.PhongBan;

import java.util.ArrayList;

public class DanhSachNhanVienActivity extends AppCompatActivity {
    ImageButton btnHome;
    ListView lvNhanvien;
    TextView txtTitle;
    ArrayList<NhanVien> listNhanvien=null;
    NhanVienAdapter adapterNhanvien;
    NhanVien nhanVienselected;
    PhongBan phongBan=null;
    int vitri=-1;  //vị trí nhân viên được chọn
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_nhan_vien);
        doAnhXa();
        getDataFromMainActivity();
        addEvents();
    }

    private void updateDataForMainActivity() {
        phongBan=new PhongBan();
        phongBan.getListNhanvien().addAll(listNhanvien);

        Intent intent=getIntent();
        Bundle bundle=new Bundle();
        bundle.putSerializable("PHONGBAN",phongBan);
        intent.putExtra("DATA",bundle);
        setResult(MainActivity.RESULT_CAPNHAT_DANHSACH_NHANVIEN_THANHCONG,intent);
    }

    private void addEvents() {
        lvNhanvien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                nhanVienselected=listNhanvien.get(position);
                vitri=position;
                return false;
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataForMainActivity();
               finish();
            }
        });
    }

    private void getDataFromMainActivity() {
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DATA");
        PhongBan phongBan= (PhongBan) bundle.getSerializable("PHONGBAN");
        listNhanvien=phongBan.getListNhanvien();
        adapterNhanvien=new NhanVienAdapter(this,R.layout.layout_nhanvien_custom,listNhanvien);
        lvNhanvien.setAdapter(adapterNhanvien);
        txtTitle.setText("Danh sách nhân viên [".toUpperCase()+phongBan.getName().toUpperCase()+"]");

        registerForContextMenu(lvNhanvien);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu_nhanvien,menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menusuanhanvien:
                doSuaNhanVien();
                break;
            case R.id.menuchuyenphongban:
               doChuyenPhongBan();
                break;
            case R.id.menuxoanhanvien:
                doXoaNhanVien();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void doChuyenPhongBan() {
        Intent intent=new Intent(this,ChuyenPhongBanActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("NHANVIEN",nhanVienselected);
        intent.putExtra("DATA",intent);
        startActivityForResult(intent,MainActivity.REQUEST_CHUYEN_PHONG_BAN);
    }

    private void doXoaNhanVien() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this,android.R.style.Theme_Holo_Dialog_NoActionBar);
        builder.setIcon(R.drawable.delete_20x20);
        builder.setTitle("Xóa nhân viên?");
        builder.setMessage("Bạn có thật sự muốn xóa nhân viên ["+nhanVienselected.getName()+"]");
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listNhanvien.remove(nhanVienselected);
                adapterNhanvien.notifyDataSetChanged();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MainActivity.REQUEST_SUA_NHANVIEN && resultCode==MainActivity.RESULT_SUA_NHANVIEN_THANHCONG){
            Bundle bundle=data.getBundleExtra("DATA");
            NhanVien nhanVien= (NhanVien) bundle.getSerializable("NHANVIEN");
            listNhanvien.set(vitri,nhanVien);
            adapterNhanvien.notifyDataSetChanged();
        }
    }

    private void doSuaNhanVien() {
        Intent intent =new Intent(this,SuaNhanVienActivity.class);
        Bundle bundle =new Bundle();
        bundle.putSerializable("NHANVIEN",nhanVienselected);
        intent.putExtra("DATA",bundle);

        startActivityForResult(intent,MainActivity.REQUEST_SUA_NHANVIEN);
    }

    private void doAnhXa() {
        lvNhanvien=findViewById(R.id.listviewNhanvien);
        btnHome=findViewById(R.id.imageViewHome);
        txtTitle=findViewById(R.id.textViewTitle);
    }
}
