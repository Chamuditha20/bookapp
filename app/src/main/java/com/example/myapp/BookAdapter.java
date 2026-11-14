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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_book_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookData currentBook = bookList.get(position);

        holder.tvItemTitle.setText(currentBook.getTitle());
        holder.tvItemAuthorCategory.setText(currentBook.getAuthor() + "  |  " + currentBook.getCategory());

        String formattedProgress = String.format("%.1f", currentBook.getProgress());
        holder.tvItemProgress.setText("Progress: " + currentBook.getReadPages() + " / " + currentBook.getTotalPages() + " pages (" + formattedProgress + "%)");

        holder.tvItemRatingDate.setText("Rating: " + currentBook.getRating() + "/5   |   Added: " + currentBook.getAddedDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateDeleteActivity.class);
                intent.putExtra("id", currentBook.getId());
                intent.putExtra("title", currentBook.getTitle());
                intent.putExtra("author", currentBook.getAuthor());
                intent.putExtra("category", currentBook.getCategory());
                intent.putExtra("content", currentBook.getContent());
                intent.putExtra("pdfUrl", currentBook.getPdfUrl());
                intent.putExtra("totalPages", currentBook.getTotalPages());
                intent.putExtra("readPages", currentBook.getReadPages());
                intent.putExtra("rating", currentBook.getRating());
                intent.putExtra("addedDate", currentBook.getAddedDate());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return bookList.size();
    }


    public void filterList(ArrayList<BookData> filteredList) {
        this.bookList = filteredList;
        notifyDataSetChanged();
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