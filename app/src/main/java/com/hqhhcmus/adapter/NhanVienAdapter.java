package com.hqhhcmus.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hqhhcmus.model.ChucVu;
import com.hqhhcmus.model.NhanVien;
import com.hqhhcmus.quanlynhanvien.R;

import java.util.List;

/**
 * Created by HQH on 3/15/2018.
 */

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    @NonNull Context context;
    int resource;
    @NonNull List<NhanVien> objects;
    public NhanVienAdapter(@NonNull Context context, int resource, @NonNull List<NhanVien> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }
    public class ViewHolder{
        ImageView imgHinh;
        TextView txtTen, txtMota;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        if(convertView==null){
            LayoutInflater layoutInflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(this.resource,null);

            holder.imgHinh=convertView.findViewById(R.id.imageViewHinh);
            holder.txtTen=convertView.findViewById(R.id.textviewTenNV);
            holder.txtMota=convertView.findViewById(R.id.textviewMotaNV);

            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        NhanVien nhanVien=new NhanVien();
        nhanVien=this.objects.get(position);

        holder.imgHinh.setImageResource(nhanVien.isGioitinh()?(R.drawable.male_48x48):(R.drawable.female_48x48));
        holder.txtTen.setText("["+nhanVien.getMa().toUpperCase()+"] - "+ nhanVien.getName().toUpperCase());
        String cv="Nhân viên";
        if(nhanVien.getChucvu()== ChucVu.TRUONGPHONG){
            cv="Trưởng Phòng";
        }
        else
            if(nhanVien.getChucvu()==ChucVu.PPHOPHONG){
            cv="Phó Phòng";
            }
        holder.txtMota.setText(" Giới tính: "+ (nhanVien.isGioitinh()?"Nam":"Nữ") + "\tChức vụ: "+cv);
        return convertView;
    }
}
