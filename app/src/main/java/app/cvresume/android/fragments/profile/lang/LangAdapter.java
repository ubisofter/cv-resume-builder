package app.cvresume.android.fragments.profile.lang;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

import app.cvresume.android.R;
import app.cvresume.android.data.AppDatabase;
import app.cvresume.android.data.ExperienceEntity;
import app.cvresume.android.data.LangEntity;
import app.cvresume.android.models.Lang;

public class LangAdapter extends RecyclerView.Adapter<LangAdapter.ViewHolder> {

    private List<LangEntity> langList;
    private AppDatabase appDatabase;

    public LangAdapter(List<LangEntity> langList, AppDatabase appDatabase) {
        this.langList = langList;
        this.appDatabase = appDatabase;
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

        LangEntity langEntity = langList.get(position);
        holder.lLangTV.setText(langEntity.langLang);

        switch (langEntity.langLvl){
            case 0:
                holder.lLvlTV.setText("Новичок");
                break;
            case 1:
                holder.lLvlTV.setText("Чтение");
                break;
            case 2:
                holder.lLvlTV.setText("Разговор");
                break;
            case 3:
                holder.lLvlTV.setText("Высокий");
                break;
            case 4:
                holder.lLvlTV.setText("Носитель");
                break;
        }

        holder.lDeleteBtn.setOnClickListener(v -> {
            removeItem(position);
        });

        Log.d("LangAdapter", "Binding item at position " + position);
    }

    @Override
    public int getItemCount() {
        return langList.size();
    }

    public void updateData(List<LangEntity> newData) {
        langList.clear();
        langList.addAll(newData);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < langList.size()) {
            final LangEntity langEntity = langList.remove(position);
            notifyItemRemoved(position);
            Executors.newSingleThreadExecutor().execute(() -> appDatabase.langDao().deleteLang(langEntity));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lLangTV;
        public TextView lLvlTV;
        public LinearLayout lDeleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lLangTV = itemView.findViewById(R.id.lLangTV);
            lLvlTV = itemView.findViewById(R.id.lLvlTV);
            lDeleteBtn = itemView.findViewById(R.id.lDeleteBtn);
        }
    }
}

