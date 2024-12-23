package com.example.workout.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workout.Domain.Lession;
import com.example.workout.databinding.ViewholderExerciseBinding;

import java.util.ArrayList;

public class LessionsAdapter extends RecyclerView.Adapter<LessionsAdapter.Viewholder> {

    private final ArrayList<Lession> list;
    private Context context;

    public LessionsAdapter(ArrayList<Lession> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public LessionsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderExerciseBinding binding = ViewholderExerciseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LessionsAdapter.Viewholder holder, int position) {
        holder.binding.titleTxt.setText(list.get(position).getTitle());
        holder.binding.durationTxt.setText(list.get(position).getDuration());

        int resId = context.getResources().getIdentifier(list.get(position).getPicPath(), "drawable", context.getPackageName());
        Glide.with(context)
                .load(resId)
                .into(holder.binding.pic);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition == RecyclerView.NO_POSITION) return;

                Lession lession = list.get(adapterPosition);
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + lession.getLink()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + lession.getLink()));

                try {
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(webIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ViewholderExerciseBinding binding;

        public Viewholder(ViewholderExerciseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
