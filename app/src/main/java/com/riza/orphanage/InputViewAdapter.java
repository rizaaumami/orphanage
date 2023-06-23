package com.riza.orphanage;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.riza.orphanage.databinding.ItemPantiBinding;

import java.util.ArrayList;
import java.util.List;

public class InputViewAdapter extends RecyclerView.Adapter<InputViewAdapter.ViewHolder> {
    private List<ScriptGroup.Input> data = new ArrayList<>();
    private PantiViewAdapter.OnItemLongClickListener onItemLongClickListener;

    public void setData(List<ScriptGroup.Input> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemLongClickListener(PantiViewAdapter.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @NonNull
    @Override
    public InputViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPantiBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InputViewAdapter.ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        ScriptGroup.Input input = data.get(pos);
        holder.itemPantiBinding.tvJudul.setText(input.getNama_panti());
        holder.itemPantiBinding.tvTanggal.setText(input.getTanggal_konser());
        holder.itemPantiBinding.tvTempat.setText(input.getLokasi_konser());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickListener.onItemLongClick(v,input,pos);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull ItemPantiBinding itemView) {
            super(itemView.getRoot());
            ItemPantiBinding = itemView;
        }
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(View v, ScriptGroup.Input input, int position);
    }
}
