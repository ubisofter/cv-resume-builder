package app.cvresume.android.fragments.profile.skill;

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
import app.cvresume.android.data.SkillEntity;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.ViewHolder> {

    private List<SkillEntity> skillList;
    private AppDatabase appDatabase;

    public SkillAdapter(List<SkillEntity> skillList, AppDatabase appDatabase) {
        this.skillList = skillList;
        this.appDatabase = appDatabase;
    }

    @NonNull
    @Override
    public SkillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_skill, parent, false);

        return new SkillAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        SkillEntity skillEntity = skillList.get(position);
        holder.sSkillTV.setText(skillEntity.skillSkill);

        switch (skillEntity.skillLvl){
            case 0:
                holder.sLvlTV.setText("Низкий");
                break;
            case 1:
                holder.sLvlTV.setText("Базовый");
                break;
            case 2:
                holder.sLvlTV.setText("Средний");
                break;
            case 3:
                holder.sLvlTV.setText("Хороший");
                break;
            case 4:
                holder.sLvlTV.setText("Высокий");
                break;
        }

        holder.sDescTV.setText(skillEntity.skillDesc);

        holder.sDeleteBtn.setOnClickListener(v -> {
            removeItem(position);
        });

        Log.d("SkillAdapter", "Binding item at position " + position);
    }

    @Override
    public int getItemCount() {
        return skillList.size();
    }

    public void updateData(List<SkillEntity> newData) {
        skillList.clear();
        skillList.addAll(newData);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < skillList.size()) {
            final SkillEntity skillEntity = skillList.remove(position);
            notifyItemRemoved(position);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    appDatabase.skillDao().deleteSkill(skillEntity);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView sSkillTV;
        public TextView sLvlTV;
        public TextView sDescTV;
        public LinearLayout sDeleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sSkillTV = itemView.findViewById(R.id.sSkillTV);
            sLvlTV = itemView.findViewById(R.id.sLvlTV);
            sDescTV = itemView.findViewById(R.id.sDescTV);
            sDeleteBtn = itemView.findViewById(R.id.sDeleteBtn);
        }
    }
}
