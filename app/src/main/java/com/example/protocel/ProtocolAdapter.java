package com.example.protocel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.protocel.ProtocolHolder;

public class ProtocolAdapter extends RecyclerView.Adapter {
    private String[] mDataset;
    public ProtocolAdapter(String[] myDataset){
        mDataset = myDataset;
    }

    @Override
    public ProtocolHolder onCreateViewHolder(ViewGroup parent, int viewtype){
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.protocol_row_layout, parent, false);
        ProtocolHolder ph = new ProtocolHolder(v);
        return ph;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
