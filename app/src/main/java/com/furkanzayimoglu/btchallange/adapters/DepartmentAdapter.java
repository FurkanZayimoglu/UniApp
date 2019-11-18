package com.furkanzayimoglu.btchallange.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkanzayimoglu.btchallange.R;
import com.furkanzayimoglu.btchallange.model.UniDepartmentDetail;

import java.util.ArrayList;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {


    private ArrayList<UniDepartmentDetail> arrayList;
    private LayoutInflater layoutInflater;



    public DepartmentAdapter(Context context , ArrayList<UniDepartmentDetail> arrayList){
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.departmentdetail,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.departmentNameText.setText(arrayList.get(position).getName());
        holder.departmentTypeText.setText(arrayList.get(position).getSchoolType());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView departmentNameText;
        TextView departmentTypeText;

        public ViewHolder(@NonNull View view) {
            super(view);

            departmentNameText = view.findViewById(R.id.departmenName);
            departmentTypeText = view.findViewById(R.id.departmenType);


        }
    }
}
