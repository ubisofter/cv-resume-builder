package app.cvresume.android.fragments.profile.education;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import app.cvresume.android.R;
import app.cvresume.android.models.Education;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ViewHolder> {

    private List<Education> educationList;

    public EducationAdapter(List<Education> educationList) {
        this.educationList = educationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_education, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Education education = educationList.get(position);
        holder.degreeTextView.setText("Образование: " + education.getDegree());
        holder.specializationTextView.setText("Учебное заведение: " + education.getUniversity());
        holder.yearsTextView.setText("Годы обучения: " + education.getYears());

        // Устанавливаем обработчик для кнопки удаления
        holder.deleteBtn.setOnClickListener(v -> {
            removeEducation(position);
        });
    }

    @Override
    public int getItemCount() {
        return educationList.size();
    }

    // Метод для удаления образования по позиции
    @SuppressLint("NotifyDataSetChanged")
    private void removeEducation(int position) {
        if (position >= 0 && position < educationList.size()) {
            educationList.remove(position);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView degreeTextView;
        public TextView specializationTextView;
        public TextView yearsTextView;
        public ImageView deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            degreeTextView = itemView.findViewById(R.id.degreeTextView);
            specializationTextView = itemView.findViewById(R.id.specializationTextView);
            yearsTextView = itemView.findViewById(R.id.yearsTextView);
            deleteBtn = itemView.findViewById(R.id.deleteEducationBtn);
        }
    }
}