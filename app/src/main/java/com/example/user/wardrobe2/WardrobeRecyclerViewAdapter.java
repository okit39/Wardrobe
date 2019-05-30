package com.example.user.wardrobe2;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import java.util.List;

public class WardrobeRecyclerViewAdapter extends RecyclerView.Adapter<WardrobeRecyclerViewAdapter.MyViewHolder>{

    Context mContext;
    List<Clothes> mData;
    OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onStatusClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mListener = onItemClickListener;
    }

    public WardrobeRecyclerViewAdapter(Context mContext, List<Clothes> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.cardview_item_clothes, viewGroup,false);
        final MyViewHolder viewHolder = new MyViewHolder(v,mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private CardView item_clothes;
        private ImageView im_photo;
        private Button btn;


        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            item_clothes = itemView.findViewById(R.id.clothes_item);
            im_photo = itemView.findViewById(R.id.photo_img);

        }
    }





}
