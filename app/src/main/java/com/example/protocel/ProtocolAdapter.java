package com.example.protocel;

import android.content.Context;
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

    private ArrayList<String> protocols = new ArrayList<String>();
    private Context mContext;
    public ProtocolAdapter(Context context, ArrayList<String> protocols){
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
        holder.protocolName.setText(protocols.get(position));
        holder.parentLayout.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("LISTITEM", "clicked");
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            protocolName = itemView.findViewById(R.id.prokaryotes_text);
        }
    }
}
