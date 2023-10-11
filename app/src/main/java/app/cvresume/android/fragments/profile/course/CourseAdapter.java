package app.cvresume.android.fragments.profile.course;

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
import app.cvresume.android.data.CourseEntity;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<CourseEntity> courseList;
    private AppDatabase appDatabase;

    public CourseAdapter(List<CourseEntity> courseList, AppDatabase appDatabase) {
        this.courseList = courseList;
        this.appDatabase = appDatabase;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);

        return new CourseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CourseEntity courseEntity = courseList.get(position);
        holder.cNameTV.setText(courseEntity.courseName);
        holder.cSchoolTV.setText(courseEntity.courseSchool);
        holder.cPeriodTV.setText(courseEntity.coursePeriod);

        holder.cDeleteBtn.setOnClickListener(v -> {
            removeItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public void updateData(List<CourseEntity> newData) {
        courseList.clear();
        courseList.addAll(newData);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < courseList.size()) {
            final CourseEntity courseEntity = courseList.remove(position);
            notifyItemRemoved(position);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    appDatabase.courseDao().deleteCourse(courseEntity);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cNameTV;
        public TextView cSchoolTV;
        public TextView cPeriodTV;
        public LinearLayout cDeleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cNameTV = itemView.findViewById(R.id.cNameTV);
            cSchoolTV = itemView.findViewById(R.id.cSchoolTV);
            cPeriodTV = itemView.findViewById(R.id.cPeriodTV);
            cDeleteBtn = itemView.findViewById(R.id.cDeleteBtn);
        }
    }
}