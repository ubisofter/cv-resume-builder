package app.cvresume.android.fragments.profile.experience;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

import app.cvresume.android.R;
import app.cvresume.android.data.AppDatabase;
import app.cvresume.android.data.ExperienceEntity;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ViewHolder> {

    private List<ExperienceEntity> experienceList;
    private AppDatabase appDatabase;

    public ExperienceAdapter(List<ExperienceEntity> experienceList, AppDatabase appDatabase) {
        this.experienceList = experienceList;
        this.appDatabase = appDatabase;
    }

    @NonNull
    @Override
    public ExperienceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_experience, parent, false);

        return new ExperienceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ExperienceEntity experienceEntity = experienceList.get(position);
        holder.wPositionTV.setText(experienceEntity.experiencePosition);
        holder.wEmployerTV.setText(experienceEntity.experienceEmployer);
        holder.wDurationTV.setText(experienceEntity.experienceDuration);
        holder.wDeleteBtn.setOnClickListener(v -> {
            removeItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return experienceList.size();
    }

    public void updateData(List<ExperienceEntity> newData) {
        experienceList.clear();
        experienceList.addAll(newData);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < experienceList.size()) {
            final ExperienceEntity experienceEntity = experienceList.remove(position);
            notifyItemRemoved(position);

            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    appDatabase.experienceDao().deleteExperience(experienceEntity);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView wPositionTV;
        public TextView wEmployerTV;
        public TextView wDurationTV;
        public LinearLayout wDeleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wPositionTV = itemView.findViewById(R.id.wPositionTV);
            wEmployerTV = itemView.findViewById(R.id.wEmployerTV);
            wDurationTV = itemView.findViewById(R.id.wDurationTV);
            wDeleteBtn = itemView.findViewById(R.id.wDeleteBtn);
        }
    }
}