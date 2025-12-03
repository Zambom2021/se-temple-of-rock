package br.com.templeofrock.models;

import com.github.javafaker.Faker;

public class Disc {
    private String title;
    private int releaseYear;

    private static final Faker faker = new Faker();

    public Disc(){
        this.title = faker.book().title();
        this.releaseYear = faker.number().numberBetween(1970, 2025);
    }

    public Disc(String title, int releaseYear){
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public String getTitle() { return title; }
    public int getReleaseYear() { return releaseYear; }
}
