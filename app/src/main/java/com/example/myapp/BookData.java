package com.example.myapp;

public class BookData {
    private int id;
    private String title;
    private String author;
    private String category;
    private String content;
    private String pdfUrl;
    private int totalPages;
    private int readPages;
    private double progress;
    private int rating;
    private String addedDate;

    public BookData(int id, String title, String author, String category, String content, String pdfUrl, int totalPages, int readPages, double progress, int rating, String addedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.content = content;
        this.pdfUrl = pdfUrl;
        this.totalPages = totalPages;
        this.readPages = readPages;
        this.progress = progress;
        this.rating = rating;
        this.addedDate = addedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getReadPages() {
        return readPages;
    }

    public void setReadPages(int readPages) {
        this.readPages = readPages;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }
}
