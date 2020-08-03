package com.example.protocel;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.protocel.ProtocolHolder;

import java.util.ArrayList;

public class ProtocolAdapter extends RecyclerView.Adapter<ProtocolAdapter.ViewHolder> {

    private ArrayList<Category> protocols = new ArrayList<Category>();
    private Context mContext;
    public ProtocolAdapter(Context context, ArrayList<Category> protocols){
        this.mContext = context;
        this.protocols = protocols;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.protocol_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.protocolName.setText(protocols.get(position));
        holder.category = protocols.get(position);
        holder.parentLayout.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("LISTITEM", "clicked");
                showCategories(holder.category);
            }
        });
    }


    @Override
    public int getItemCount() {
        return protocols.size();
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
    public void showCategories(ArrayList<Category> protocols){
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("PROTOCOLS", protocols);
        mContext.startActivity(intent);
    }

}
