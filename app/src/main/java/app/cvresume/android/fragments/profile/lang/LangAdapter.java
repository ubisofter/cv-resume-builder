package app.cvresume.android.fragments.profile.lang;

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
import app.cvresume.android.models.Lang;

public class LangAdapter extends RecyclerView.Adapter<LangAdapter.ViewHolder> {

    private List<Lang> langList;

    public LangAdapter(List<Lang> langList) {
        this.langList = langList;
    }

    @NonNull
    @Override
    public LangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lang, parent, false);
        return new LangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LangAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Lang lang = langList.get(position);
        holder.langTV.setText("Язык: " + lang.getLang());

        switch (lang.getLangLvl()){
            case 0:
                holder.langLvl.setText("Уровень: Новичок");
                break;
            case 1:
                holder.langLvl.setText("Уровень: Чтение");
                break;
            case 2:
                holder.langLvl.setText("Уровень: Разговор");
                break;
            case 3:
                holder.langLvl.setText("Уровень: Высокий");
                break;
            case 4:
                holder.langLvl.setText("Уровень: Носитель");
                break;
        }
        //holder.langLvl.setText("Уровень: " + lang.getLangLevel());

        holder.langDesc.setText("Описание: " + lang.getLangDesc());

        // Установите обработчик для кнопки удаления
        holder.removeLangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Вызовите метод для удаления образования
                removeEducation(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return langList.size();
    }

    // Метод для удаления образования по позиции
    private void removeEducation(int position) {
        if (position >= 0 && position < langList.size()) {
            langList.remove(position);
            notifyDataSetChanged(); // Обновить адаптер
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView langTV, langDesc, langLvl;
        public ImageView removeLangBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            langTV = itemView.findViewById(R.id.langTV);
            langDesc = itemView.findViewById(R.id.langDescTV);
            langLvl = itemView.findViewById(R.id.langLvlTV);
            removeLangBtn = itemView.findViewById(R.id.removeLangBtn);
        }
    }
}