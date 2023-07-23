package org.example;

import java.util.List;
import java.util.Random;

public class Movie {
    private final int id;
    private final String title;
    private final Director director;
    private final List<Actor> actors;
    private final int releaseYear;
    private final String genre;
    private final int rating;

    Random random=new Random();

    public Movie(int id, String title, Director director, List<Actor> actors, int releaseYear, String genre) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.actors = actors;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.rating= random.nextInt(0,6);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Director getDirector() {
        return director;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public double getMovieRating(Movie movie) {
        return rating;
    }
}
