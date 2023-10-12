package app.cvresume.android.fragments.profile.education;

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
import app.cvresume.android.data.EducationEntity;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ViewHolder> {

    private List<EducationEntity> educationList;
    private AppDatabase appDatabase;

    public EducationAdapter(List<EducationEntity> educationList, AppDatabase appDatabase) {
        this.educationList = educationList;
        this.appDatabase = appDatabase;
    }

    @NonNull
    @Override
    public EducationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_education, parent, false);

        return new EducationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EducationAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        EducationEntity educationEntity = educationList.get(position);
        holder.eDegreeTV.setText(educationEntity.educationDegree);
        holder.eUniversityTV.setText(educationEntity.educationUniversity);
        holder.eYearsTV.setText(educationEntity.educationYears);
        holder.eDeleteBtn.setOnClickListener(v -> {
            removeItem(position);
        });

        Log.d("EducationAdapter", "Binding item at position " + position);
    }

    @Override
    public int getItemCount() {
        return educationList.size();
    }

    public void updateData(List<EducationEntity> newData) {
        educationList.clear();
        educationList.addAll(newData);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < educationList.size()) {
            final EducationEntity educationEntity = educationList.remove(position);
            notifyItemRemoved(position);
            Executors.newSingleThreadExecutor().execute(() -> appDatabase.educationDao().deleteEducation(educationEntity));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView eDegreeTV;
        public TextView eUniversityTV;
        public TextView eYearsTV;
        public LinearLayout eDeleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eDegreeTV = itemView.findViewById(R.id.eDegreeTV);
            eUniversityTV = itemView.findViewById(R.id.eUniversityTV);
            eYearsTV = itemView.findViewById(R.id.eYearsTV);
            eDeleteBtn = itemView.findViewById(R.id.eDeleteBtn);
        }
    }
}