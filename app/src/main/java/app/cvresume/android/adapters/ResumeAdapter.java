package app.cvresume.android.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.cvresume.android.R;

public class ResumeAdapter extends RecyclerView.Adapter<ResumeAdapter.ResumeViewHolder> {

    private String[] sections; // Массив с названиями разделов резюме
    private int[] sectionIcons; // Массив с иконками

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ResumeAdapter(String[] sections, int[] sectionIcons) {
        this.sections = sections;
        this.sectionIcons = sectionIcons;
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
        holder.sectionTitle.setText(sectionTitle);

        int iconResId = sectionIcons[position];
        holder.sectionIcon.setImageResource(iconResId);

        // Добавьте обработчик нажатия на элемент, чтобы открыть соответствующий фрагмент
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position); // Передаем позицию элемента
                }
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

        public ResumeViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionTitle = itemView.findViewById(R.id.sectionTitle);
            sectionIcon = itemView.findViewById(R.id.sectionIcon);
        }
    }
}