package com.example.sustainhillreap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerAdapterDetails extends FirebaseRecyclerAdapter<Post,RecyclerAdapterDetails.ViewHolder> {

    public RecyclerAdapterDetails(@NonNull FirebaseRecyclerOptions<Post> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Post post) {
        holder.name.setText("Name: "+post.getName());
        holder.amount.setText("Rs. "+post.getAmount());
        holder.date.setText("Date: "+post.getDate());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list, parent, false);
        return new RecyclerAdapterDetails.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,amount,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amt);
            date = itemView.findViewById(R.id.date1);

        }
    }
}
