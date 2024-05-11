package com.onevision.books.dto;

public class AuthorDTO {

    private String author;
    private int occurrences;

    public AuthorDTO() {
    }

    public AuthorDTO(String author) {
        this.author = author;
    }

    public AuthorDTO(String author, int occurrences) {
        this.author = author;
        this.occurrences = occurrences;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

}
