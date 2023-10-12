package app.cvresume.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.cvresume.android.R;

public class TemplatesAdapter extends RecyclerView.Adapter<TemplatesAdapter.TemplateViewHolder> {

    private Context context;
    private OnTemplateClickListener listener;

    int[] imageResources = { R.drawable.tmp_preview_1, R.drawable.tmp_preview_2, R.drawable.tmp_preview_3,
            R.drawable.tmp_preview_4, R.drawable.tmp_preview_5, R.drawable.tmp_preview_6 };

    public TemplatesAdapter(Context context, OnTemplateClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public interface OnTemplateClickListener {
        void onTemplateClick(int position);
    }

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_template, parent, false);
        return new TemplateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder holder, int position) {
        if (position >= 0 && position < imageResources.length) {
            holder.templateImage.setImageResource(imageResources[position]);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTemplateClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        { return imageResources.length; }
    }

    public class TemplateViewHolder extends RecyclerView.ViewHolder {

        ImageView templateImage;

        public TemplateViewHolder(@NonNull View itemView) {
            super(itemView);
            templateImage = itemView.findViewById(R.id.templateImage);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (listener != null) {
                    listener.onTemplateClick(position);
                }
            });
        }
    }
}