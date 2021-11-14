package com.example.zwiggy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zwiggy.R;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerAdapterViewHolder> {

    ArrayList<String> mResturants;
    Context mContext;

    public CustomerAdapter(Context context, ArrayList<String> resturantsList){
        mContext = context;
        mResturants = resturantsList;
    }
    @NonNull
    @Override
    public CustomerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.activity_customer_restaurant_list_iems, parent, false);
        CustomerAdapterViewHolder viewHolder = new CustomerAdapterViewHolder(contactView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull CustomerAdapterViewHolder holder, int position) {
        String restaurant_list =mResturants.get(position);
        TextView textView = holder.restaurant;
        textView.setText(restaurant_list);
    }

    @Override
    public int getItemCount() {
        return mResturants.size();
    }

    public class CustomerAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView restaurant;

        public CustomerAdapterViewHolder(View itemView) {
            super(itemView);
            restaurant = (TextView) itemView.findViewById(R.id.list_reataurant_items);
        }
    }
}