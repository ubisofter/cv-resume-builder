package app.cvresume.android.fragments.profile.skill;

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
import app.cvresume.android.models.Skill;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.ViewHolder> {

    private List<Skill> skillList;

    public SkillAdapter(List<Skill> skillList) {
        this.skillList = skillList;
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
        Skill skill = skillList.get(position);
        holder.skillTV.setText("Навык: " + skill.getSkill());

        switch (skill.getSkillLevel()){
            case 0:
                holder.skillLvl.setText("Уровень: Низкий");
                break;
            case 1:
                holder.skillLvl.setText("Уровень: Базовый");
                break;
            case 2:
                holder.skillLvl.setText("Уровень: Средний");
                break;
            case 3:
                holder.skillLvl.setText("Уровень: Хороший");
                break;
            case 4:
                holder.skillLvl.setText("Уровень: Высокий");
                break;
        }
        //holder.skillLvl.setText("Уровень: " + skill.getSkillLevel());

        holder.skillDesc.setText("Описание: " + skill.getSkillDesc());

        // Установите обработчик для кнопки удаления
        holder.removeSkillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Вызовите метод для удаления образования
                removeEducation(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return skillList.size();
    }

    // Метод для удаления образования по позиции
    private void removeEducation(int position) {
        if (position >= 0 && position < skillList.size()) {
            skillList.remove(position);
            notifyDataSetChanged(); // Обновить адаптер
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView skillTV, skillDesc, skillLvl;
        public ImageView removeSkillBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            skillTV = itemView.findViewById(R.id.skillTV);
            skillDesc = itemView.findViewById(R.id.skillDescTV);
            skillLvl = itemView.findViewById(R.id.skillLvlTV);
            removeSkillBtn = itemView.findViewById(R.id.removeSkillBtn);
        }
    }
}
