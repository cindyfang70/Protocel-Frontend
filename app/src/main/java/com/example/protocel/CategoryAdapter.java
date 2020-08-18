package com.example.protocel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    ArrayList<Category> categories = new ArrayList<>();
    private Context mContext;
    public CategoryAdapter(Context context, ArrayList<Category> categories){
        this.mContext = context;
        this.categories.addAll(categories);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.protocol_listitem, parent, false);
        CategoryAdapter.CategoryViewHolder viewHolder = new CategoryAdapter.CategoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position) {
        Category cat = categories.get(position);
        holder.protocolName.setText(cat.getName());
        holder.category = cat;
        holder.category = categories.get(position);
        holder.parentLayout.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("LISTITEM", "clicked");
                final CategoryAdapter.CategoryViewHolder v = holder;
                showCategories(v.category);
            }
        });
    }

    public void showCategories(Category subcategory){
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("SUBCATEGORY", subcategory);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView protocolName;
        RelativeLayout parentLayout;
        Category category;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            protocolName = itemView.findViewById(R.id.prokaryotes_text);
        }
    }
}
