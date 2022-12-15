package com.example.asadquran;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder>{

    @NonNull

    Context context;
    int[] images;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView rowimage;
        public ViewHolder(View itemView) {

            super(itemView);
            rowimage = itemView.findViewById(R.id.imageView);
        }
    }

    public ProgramAdapter(Context context, int[] images){
        this.context = context;
        this.images = images;
    }

    public ProgramAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.testingarea,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ProgramAdapter.ViewHolder holder, int position) {

        holder.rowimage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }
}
