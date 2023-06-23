package com.riza.orphanage;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PantiViewAdapter extends RecyclerView.Adapter<PantiViewAdapter.ViewHolder> {
    private OnItemClickCallback onItemClickCallback;

    private List<DataPanti> data = new ArrayList<DataPanti>();
    private OnItemLongClickListener onItemLongClickListener;

    public void setData(List<DataPanti> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }
    @NonNull
    @Override
    public PantiViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PantiViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(View v, DataPanti item, int position);
    }

    public interface OnItemClickCallback {
        void onItemClicked(DataPanti item);
    }
}
