package com.example.protocel;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ProtocolHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public ProtocolHolder(TextView v){
        super(v);
        textView = v;
    }


}
