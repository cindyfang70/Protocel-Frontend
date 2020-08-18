package com.example.protocel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrganismAdapter extends RecyclerView.Adapter<OrganismAdapter.ViewHolder> {

    private ArrayList<Category> categories = new ArrayList<Category>();
    private Context mContext;
    public OrganismAdapter(Context context, ArrayList<Category> categories){
        this.mContext = context;
        this.categories.addAll(categories);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.protocol_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Category cat = categories.get(position);
        holder.protocolName.setText(cat.getName());
        holder.category = cat;
        holder.category = categories.get(position);
        holder.parentLayout.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("LISTITEM", "clicked");
                final ViewHolder v = holder;
                showCategories(v.category);
            }
        });
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView protocolName;
        RelativeLayout parentLayout;
        Category category;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            protocolName = itemView.findViewById(R.id.prokaryotes_text);
        }
    }
    public void showCategories(Category subcategory){
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("SUBCATEGORY", subcategory);
        mContext.startActivity(intent);
    }

}
