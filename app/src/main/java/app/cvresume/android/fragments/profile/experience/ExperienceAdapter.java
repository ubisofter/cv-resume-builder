package app.cvresume.android.fragments.profile.experience;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.cvresume.android.R;
import app.cvresume.android.fragments.profile.education.EducationAdapter;
import app.cvresume.android.models.Experience;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ViewHolder> {

    private List<Experience> experienceList;

    public ExperienceAdapter(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    @NonNull
    @Override
    public ExperienceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_experience, parent, false);
        return new ExperienceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceAdapter.ViewHolder holder, int position) {
        Experience experience = experienceList.get(position);
        holder.positionTV.setText("Должность: " + experience.getPosition());
        holder.employerTV.setText("Работодатель: " + experience.getEmployer());
        holder.durationTV.setText("Продолжительность: " + experience.getDuration());

        // Установите обработчик для кнопки удаления
        holder.removeExperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Вызовите метод для удаления образования
                removeEducation(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return experienceList.size();
    }

    // Метод для удаления образования по позиции
    private void removeEducation(int position) {
        if (position >= 0 && position < experienceList.size()) {
            experienceList.remove(position);
            notifyDataSetChanged(); // Обновить адаптер
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView positionTV;
        public TextView employerTV;
        public TextView durationTV;
        public ImageView removeExperienceBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            positionTV = itemView.findViewById(R.id.positionTV);
            employerTV = itemView.findViewById(R.id.employerTV);
            durationTV = itemView.findViewById(R.id.durationTV);
            removeExperienceBtn = itemView.findViewById(R.id.removeExperienceBtn);
        }
    }
}