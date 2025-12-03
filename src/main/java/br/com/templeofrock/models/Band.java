package br.com.templeofrock.models;

public class Band {
    public String name;
    public String genre;
    public String members;
    public int formationYear;
    public String country;

    public Band(){}

    public Band(String name, String genre, String members, int formationYear, String country){
        this.name = name;
        this.genre = genre;
        this.members = members;
        this.formationYear = formationYear;
        this.country = country;
    }
}
