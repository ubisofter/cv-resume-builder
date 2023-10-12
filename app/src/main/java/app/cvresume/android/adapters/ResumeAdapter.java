package app.cvresume.android.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import app.cvresume.android.R;

public class ResumeAdapter extends RecyclerView.Adapter<ResumeAdapter.ResumeViewHolder> {

    private String[] sections;
    private int[] sectionIcons;
    private int[] sectionProgress;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ResumeAdapter(String[] sections, int[] sectionIcons, int[] progress) {
        this.sections = sections;
        this.sectionIcons = sectionIcons;
        this.sectionProgress = progress;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ResumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resume, parent, false);
        return new ResumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResumeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String sectionTitle = sections[position];
        int progress = sectionProgress[position];

        Log.d("BindViewHolder progress", sectionTitle+" PROGRESS "+progress);

        holder.sectionTitle.setText(sectionTitle);

        if(progress>99){
            holder.progressFilled.setVisibility(View.GONE);
            holder.doneImg.setVisibility(View.VISIBLE);
        } else {
            holder.progressFilled.setProgress(progress);
            holder.doneImg.setVisibility(View.GONE);
            holder.progressFilled.setVisibility(View.VISIBLE);
        }

        int iconResId = sectionIcons[position];
        holder.sectionIcon.setImageResource(iconResId);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sections.length;
    }

    public static class ResumeViewHolder extends RecyclerView.ViewHolder {
        TextView sectionTitle;
        ImageView sectionIcon;
        CircularProgressIndicator progressFilled;
        ImageView doneImg;

        public ResumeViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionTitle = itemView.findViewById(R.id.sectionTitle);
            sectionIcon = itemView.findViewById(R.id.sectionIcon);
            progressFilled = itemView.findViewById(R.id.progressFilled);
            doneImg = itemView.findViewById(R.id.progressImg);
        }
    }
}