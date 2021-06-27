package com.vikas.secret.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vikas.lib.GlideImageLoader;
import com.vikas.secret.R;
import com.vikas.secret.data.models.HomeItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ItemViewHolder>{

    public interface HomeItemClickListener {
        void onItemClick(int adapterPosition, HomeItem homeItem, ImageView imageView);
    }

    private final HomeItemClickListener homeItemClickListener;
    private final List<HomeItem> homeItems;
    private final Context context;

    public HomeAdapter(List<HomeItem> homeItems, HomeItemClickListener homeItemClickListener, Context context) {
        this.homeItems = homeItems;
        this.homeItemClickListener = homeItemClickListener;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final HomeItem homeItem = homeItems.get(position);

        holder.imageView.setImageResource(homeItem.isOpen()? R.drawable.ic_baseline_lock_open_24: R.drawable.ic_baseline_lock_24);
        ViewCompat.setTransitionName(holder.imageView, homeItem.getName());

        holder.bind(holder.getAdapterPosition(), homeItem, homeItemClickListener, holder.imageView);
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
        return homeItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_home_square_image);
        }

        public void bind(int adapterPosition, HomeItem homeItem, HomeItemClickListener homeItemClickListener, ImageView imageView) {
            itemView.setOnClickListener(v -> homeItemClickListener.onItemClick(adapterPosition, homeItem, imageView));
        }
    }
}
