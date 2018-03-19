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
import com.hqhhcmus.model.PhongBan;
import com.hqhhcmus.quanlynhanvien.R;

import java.util.List;

/**
 * Created by HQH on 3/15/2018.
 */

public class PhongBanAdapter extends ArrayAdapter<PhongBan> {
    Context context;
    int resource;
    List<PhongBan> objects;
    public PhongBanAdapter(Context context, int resource, List<PhongBan> objects) {
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
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(this.resource, null);

            holder.txtTen=convertView.findViewById(R.id.textviewTen);
            holder.txtMota=convertView.findViewById(R.id.textviewMota);

            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();

        }

        PhongBan phongBan=this.objects.get(position);

        String strTen="";
        strTen+=phongBan.getMa().toUpperCase()+" - "+phongBan.getName().toUpperCase()+"  [có "+phongBan.getListNhanvien().size()+" nhân viên]";

        holder.txtTen.setText(strTen);

        String strmota="";
        String strtp="-Trưởng phòng: [Chưa có]";
        String strpp="-Phó phòng: ";
        int dem=0;
        for(int i=0;i<phongBan.getListNhanvien().size();i++)
        {
            if(phongBan.getListNhanvien().get(i).getChucvu()== ChucVu.TRUONGPHONG)
            {
                strtp="-Trưởng phòng: "+ phongBan.getListNhanvien().get(i).getName();
            }
            if(phongBan.getListNhanvien().get(i).getChucvu()==ChucVu.PPHOPHONG){
                strpp+= "\n\t\t\t+"+phongBan.getListNhanvien().get(i).getName();
                dem++;
            }
        }
        if(dem==0){
            strpp="- Phó phòng: [Chưa có]";
        }

        strmota+=strtp + "\n" +strpp ;
        holder.txtMota.setText(strmota);

        return convertView;
    }
}
