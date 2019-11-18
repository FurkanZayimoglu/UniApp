package com.furkanzayimoglu.btchallange.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkanzayimoglu.btchallange.R;
import com.furkanzayimoglu.btchallange.model.UniModel;
import com.furkanzayimoglu.btchallange.network.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UniAdapter extends RecyclerView.Adapter<UniAdapter.MyViewHolder>{

    public  List<UniModel> arrayList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener listener;

    public UniAdapter(Context context, List<UniModel> arrayList){
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_design,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(arrayList.get(position).getName());
        holder.shortDetail.setText(arrayList.get(position).getShortDetail());
        Picasso.get().load(arrayList.get(position).getUrl()).into(holder.imageUrl);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        TextView shortDetail;
        ImageView imageUrl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            shortDetail = itemView.findViewById(R.id.shortDetail);
            imageUrl = itemView.findViewById(R.id.imageUrl);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());

        }
    }
}
