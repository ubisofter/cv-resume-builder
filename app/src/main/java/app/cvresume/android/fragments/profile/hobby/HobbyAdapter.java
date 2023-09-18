package app.cvresume.android.fragments.profile.hobby;

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
import app.cvresume.android.models.Hobby;

public class HobbyAdapter extends RecyclerView.Adapter<HobbyAdapter.ViewHolder> {

    private List<Hobby> hobbyList;

    public HobbyAdapter(List<Hobby> hobbyList) {
        this.hobbyList = hobbyList;
    }

    @NonNull
    @Override
    public HobbyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hobby, parent, false);
        return new HobbyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HobbyAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Hobby hobby = hobbyList.get(position);
        holder.hobbyTV.setText("Хобби: " + hobby.getHobby());

        // Установите обработчик для кнопки удаления
        holder.removeHobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Вызовите метод для удаления образования
                removeEducation(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hobbyList.size();
    }

    // Метод для удаления образования по позиции
    private void removeEducation(int position) {
        if (position >= 0 && position < hobbyList.size()) {
            hobbyList.remove(position);
            notifyDataSetChanged(); // Обновить адаптер
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView hobbyTV;
        public ImageView removeHobbyBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hobbyTV = itemView.findViewById(R.id.hobbyTV);
            removeHobbyBtn = itemView.findViewById(R.id.removeHobbyBtn);
        }
    }
}