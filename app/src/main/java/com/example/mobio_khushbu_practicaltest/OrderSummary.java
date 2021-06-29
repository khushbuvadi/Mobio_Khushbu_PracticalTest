package com.example.mobio_khushbu_practicaltest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobio_khushbu_practicaltest.adapter.ColorAdapter;
import com.example.mobio_khushbu_practicaltest.model.Color;
import com.example.mobio_khushbu_practicaltest.model.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderSummary extends AppCompatActivity {

    @BindView(R.id.rvOrderSummary) RecyclerView rvOrderSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersummary);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Product> finalList = (ArrayList<Product>) args.getSerializable("list");
        ProductAdapter adapter = new ProductAdapter(OrderSummary.this,finalList);
        rvOrderSummary.setAdapter(adapter);
    }

    public class ProductAdapter extends  RecyclerView.Adapter<ProductAdapter.ViewHolder> {
        private ArrayList<Product> list;
        Context context;

        public ProductAdapter(Context ctx, ArrayList<Product> addressList) {
            this.list = addressList;
            context = ctx;
            setHasStableIds(true);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordersummary, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setIsRecyclable(false);
            Product product = list.get(position);
            Glide.with(context).load(product.getImage()).thumbnail(Glide.with(context).load(R.drawable.place)).into(holder.ivImage);
            holder.tvPName.setText(product.getProductName());
            holder.tvPrice.setText(product.getPrice());
            holder.tvBrand.setText(product.getSelectedBrand());
            holder.tvColor.setText(product.getSelectedColor());
            holder.tvQty.setText(product.getQty());
            String price = "",f1="";
            price = product.getPrice().substring(1);
            f1  = price.replace(",","");
            Log.e("price","==> "+ price);
            float p = Float.parseFloat(f1);
            Log.e("p","==> "+ p);
            float pr = p * Float.parseFloat(product.getQty());
            Log.e("pr","==> "+ pr);
            holder.tvTotalPrice.setText("$"+ pr);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.ivImage) CircleImageView ivImage;
            @BindView(R.id.tvPName) TextView tvPName;
            @BindView(R.id.tvPrice) TextView tvPrice;
            @BindView(R.id.tvQty) TextView tvQty;
            @BindView(R.id.tvColor) TextView tvColor;
            @BindView(R.id.tvBrand) TextView tvBrand;
            @BindView(R.id.tvTotalPrice) TextView tvTotalPrice;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
