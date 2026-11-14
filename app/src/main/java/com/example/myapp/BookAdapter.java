package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

        holder.tvItemCategoryBadge.setText(currentBook.getCategory());

        if (currentBook.getProgress() >= 100.0) {
            holder.tvItemCompletedBadge.setVisibility(View.VISIBLE);
        } else {
            holder.tvItemCompletedBadge.setVisibility(View.GONE);
        }

        holder.tvItemTitle.setText(currentBook.getTitle());
        holder.tvItemAuthor.setText("by " + currentBook.getAuthor());

        if (currentBook.getContent() != null && !currentBook.getContent().trim().isEmpty()) {
            holder.tvItemContentSnippet.setText(currentBook.getContent());
            holder.tvItemContentSnippet.setVisibility(View.VISIBLE);
        } else {
            holder.tvItemContentSnippet.setVisibility(View.GONE);
        }

        if (currentBook.getPdfUrl() != null && !currentBook.getPdfUrl().trim().isEmpty()) {
            holder.tvItemPdfUrl.setText("PDF Link: " + currentBook.getPdfUrl());
            holder.tvItemPdfUrl.setVisibility(View.VISIBLE);
        } else {
            holder.tvItemPdfUrl.setVisibility(View.GONE);
        }

        String formattedProgress = String.format("%.1f", currentBook.getProgress());
        holder.tvItemProgress.setText("Progress: " + formattedProgress + "% (" + currentBook.getReadPages() + " / " + currentBook.getTotalPages() + " pages)");

        int progressPercent = (int) Math.min(Math.max(currentBook.getProgress(), 0), 100);
        holder.pbItemProgress.setProgress(progressPercent);

        holder.tvItemRating.setText("Rating: " + currentBook.getRating() + "/5");
        holder.tvItemDate.setText("Added: " + currentBook.getAddedDate());

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
        TextView tvItemCategoryBadge, tvItemCompletedBadge, tvItemTitle, tvItemAuthor;
        TextView tvItemContentSnippet, tvItemPdfUrl, tvItemProgress, tvItemRating, tvItemDate;
        ProgressBar pbItemProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemCategoryBadge = itemView.findViewById(R.id.tvItemCategoryBadge);
            tvItemCompletedBadge = itemView.findViewById(R.id.tvItemCompletedBadge);
            tvItemTitle = itemView.findViewById(R.id.tvItemTitle);
            tvItemAuthor = itemView.findViewById(R.id.tvItemAuthor);
            tvItemContentSnippet = itemView.findViewById(R.id.tvItemContentSnippet);
            tvItemPdfUrl = itemView.findViewById(R.id.tvItemPdfUrl);
            tvItemProgress = itemView.findViewById(R.id.tvItemProgress);
            pbItemProgress = itemView.findViewById(R.id.pbItemProgress);
            tvItemRating = itemView.findViewById(R.id.tvItemRating);
            tvItemDate = itemView.findViewById(R.id.tvItemDate);
        }
    }
}