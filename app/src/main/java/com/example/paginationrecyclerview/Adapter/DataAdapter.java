package com.example.paginationrecyclerview.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.paginationrecyclerview.Model.DataResponseModel;
import com.example.paginationrecyclerview.R;

import java.util.ArrayList;

public class DataAdapter  extends RecyclerView.Adapter<DataAdapter.dataViewHolder> {

    private ArrayList<DataResponseModel> mylists;
    private Activity activity;
    public DataAdapter(ArrayList<DataResponseModel> myArrList, Activity activity){
        this.mylists=myArrList;
        this.activity=activity;
    }
    @NonNull
    @Override
    public dataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_layout,parent,false);
        return new dataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dataViewHolder holder, int position) {
        DataResponseModel dataResponseModel= mylists.get(position);
        holder.product_name.setText(dataResponseModel.getName());

        Glide.with(holder.product_image.getContext())
                .load(dataResponseModel.getImage())
                .into(holder.product_image);

        holder.btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.btn_save_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mylists.size();
    }

    class dataViewHolder extends RecyclerView.ViewHolder{

        private ImageView product_image;
        private TextView product_name;
        private Button btn_buy,btn_save_later;
        public dataViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image= (ImageView) itemView.findViewById(R.id.product_image);
            product_name= (TextView) itemView.findViewById(R.id.product_name);
            btn_buy= (Button) itemView.findViewById(R.id.btn_buy);
            btn_save_later= (Button) itemView.findViewById(R.id.btn_save_later);
        }
    }
}
