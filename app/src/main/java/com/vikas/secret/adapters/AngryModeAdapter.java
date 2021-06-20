package com.vikas.secret.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vikas.secret.R;
import com.vikas.secret.data.models.AngerModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AngryModeAdapter extends RecyclerView.Adapter<AngryModeAdapter.ItemViewHolder> {

    public interface AngerItemClickListener {
        void onItemClick(int adapterPosition, AngerModel angerModel, LinearLayout listItem);
    }

    private final AngerItemClickListener itemClickListener;
    private final List<AngerModel> angerModels;
    private final Context context;

    public AngryModeAdapter(AngerItemClickListener itemClickListener, List<AngerModel> angerModels, Context context) {
        this.itemClickListener = itemClickListener;
        this.angerModels = angerModels;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_angry, parent, false);
        return new AngryModeAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder, int position) {
        final AngerModel angerModel = angerModels.get(position);

        holder.title.setText(angerModel.getReason());
        holder.dateRange.setText(angerModel.getFormattedDateRange());
        holder.description.setText(angerModel.getDescription());

        holder.bind(holder.getAdapterPosition(), angerModel, itemClickListener, holder.listItem);
    }

    public void update(List<AngerModel> data) {
        angerModels.clear();
        angerModels.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return angerModels.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
    LinearLayout listItem;
    TextView title;
    TextView dateRange;
    TextView description;

    public ItemViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        listItem = itemView.findViewById(R.id.list_item);
        title = itemView.findViewById(R.id.title);
        dateRange = itemView.findViewById(R.id.dateRange);
        description = itemView.findViewById(R.id.description);
    }

    public void bind(int adapterPosition, AngerModel homeItem, AngerItemClickListener angerItemClickListener, LinearLayout listItem) {
        itemView.setOnClickListener(v -> angerItemClickListener.onItemClick(adapterPosition, homeItem, listItem));
    }
}

}
