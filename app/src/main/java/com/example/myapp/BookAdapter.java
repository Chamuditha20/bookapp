package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {


    Context context;
    ArrayList<BookData> bookList;


    public BookAdapter(Context context, ArrayList<BookData> bookList) {
        this.context = context;
        this.bookList = bookList;
    }


    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_book_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        BookData currentBook = bookList.get(position);

        holder.tvItemTitle.setText(currentBook.getTitle());
        holder.tvItemAuthorCategory.setText(currentBook.getAuthor() + "  |  " + currentBook.getCategory());

        String formattedProgress = String.format("%.1f", currentBook.getProgress());
        holder.tvItemProgress.setText("Progress: " + currentBook.getReadPages() + " / " + currentBook.getTotalPages() + " pages (" + formattedProgress + "%)");

        holder.tvItemRatingDate.setText("Rating: " + currentBook.getRating() + "/5   |   Added: " + currentBook.getAddedDate());
    }


    @Override
    public int getItemCount() {
        return bookList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemTitle, tvItemAuthorCategory, tvItemProgress, tvItemRatingDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemTitle = itemView.findViewById(R.id.tvItemTitle);
            tvItemAuthorCategory = itemView.findViewById(R.id.tvItemAuthorCategory);
            tvItemProgress = itemView.findViewById(R.id.tvItemProgress);
            tvItemRatingDate = itemView.findViewById(R.id.tvItemRatingDate);
        }
    }
}