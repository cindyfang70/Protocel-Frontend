package com.example.protocel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProtocolAdapter extends RecyclerView.Adapter<ProtocolAdapter.ProtocolViewHolder> {

    ArrayList<Protocols> protocols = new ArrayList<>();
    private Context mContext;
    public ProtocolAdapter(Context context, ArrayList<Protocols> protocols){
        this.mContext = context;
        this.protocols.addAll(protocols);
    }

    @NonNull
    @Override
    public ProtocolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.protocol_verificationimages, parent, false);
        ProtocolAdapter.ProtocolViewHolder viewHolder = new ProtocolAdapter.ProtocolViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProtocolViewHolder holder, int position) {
        Protocols cat = protocols.get(position);
        holder.protocolName.setText(cat.getName());
        holder.protocol = cat;
        if (!holder.protocol.getLabConfirmation()){
            holder.testtubeImage.setVisibility(View.INVISIBLE);
        }
        if (!holder.protocol.getLiteratureConfirmation()) {
            holder.bookImage.setVisibility(View.INVISIBLE);
        }
        holder.parentLayout.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("LISTITEM", "clicked");
                final ProtocolAdapter.ProtocolViewHolder v = holder;
                showCategories(v.protocol);
            }
        });
    }

    public void showCategories(Protocols protocol){
        Intent intent = new Intent(mContext, ProtocolActivity.class);
        intent.putExtra("PROTOCOL", protocol);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return this.protocols.size();
    }

    public class ProtocolViewHolder extends RecyclerView.ViewHolder {
        TextView protocolName;
        RelativeLayout parentLayout;
        Protocols protocol;
        ImageView testtubeImage;
        ImageView bookImage;
        public ProtocolViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            protocolName = itemView.findViewById(R.id.protocol_images);
            testtubeImage = itemView.findViewById(R.id.testtube_icon);
            bookImage = itemView.findViewById(R.id.book_icon);
        }
    }
}
