package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MovieLibrary {
    private List<Movie> movies;

    public MovieLibrary() {
        List<Director> directors = Utils.readFile("directors").stream().map(Director::new).toList();
        List<Actor> actors = Utils.readFile("actors").stream().map(Actor::new).toList();
        this.movies = Utils.readFile("movies")
                .stream()
                .map(item -> {
                    int id = Integer.parseInt(item.get(0));
                    String title = item.get(1);
                    Director director = directors.stream().filter(dir -> dir.getId() == Integer.parseInt(item.get(2))).findFirst().orElse(null);
                    int actor1IdFromFile = Integer.parseInt(item.get(3));
                    int actor2IdFromFile = Integer.parseInt(item.get(4));
                    List<Actor> actorsList = actors.stream().filter(act -> act.getId() == actor1IdFromFile || act.getId() == actor2IdFromFile).distinct().toList();
                    int year = Integer.parseInt(item.get(5));
                    String genre = item.get(6);
                    return new Movie(id, title, director, actorsList, year, genre);
                }).toList();
        List<String> names = findDirectorsWithAtLeastNMovies(5);
        System.out.println(names);
    }


    //    // 1. Find all movies released in a specific year.
//    // Return a list of movie titles.
    public List<String> findMoviesByYear(int year) {
        List<String> moviesByYear = movies.stream().filter(item -> item.getReleaseYear() == year).map(Movie::getTitle).toList();
        return moviesByYear;

    }

    //
//    // 2. Find all movies of a specific genre.
//    // Return a list of movie titles.
    public List<String> findMoviesByGenre(String genre) {
        List<String> moviesByGenre = movies.stream().filter(item -> item.getGenre().equals(genre)).map(Movie::getTitle).toList();
        return moviesByGenre;
    }

    //
//    // 3. Find all directors who have directed at least N movies.
//    // Return a list of director names.
    public List<String> findDirectorsWithAtLeastNMovies(int n) {
        Map<String, Long> directorsMoviesCount = movies.stream()
                .filter(item -> item.getDirector() != null)
                .collect(Collectors.groupingBy(item -> item.getDirector().getName(), Collectors.counting()));
        return directorsMoviesCount.entrySet().stream().filter(item -> item.getValue() >= n).map(Map.Entry::getKey).toList();
    }

    //
//    // 4. Find all actors who have appeared in movies of a specific genre.
//    // Return a list of actor names.
    public List<String> findActorsInGenre(String genre) {
        List<List<Actor>> actor = movies.stream()
                .filter(item -> item.getGenre().equals(genre)).map(Movie::getActors).toList();
        List<String> actorsInGenre = actor.stream().flatMap(List::stream).map(Actor::getName).toList();
        return actorsInGenre;
    }
//
//    // 5. Find the average release year of movies for a specific director.
//    // Return a double value.
//    public double findAverageReleaseYearForDirector(String directorName) {
//        // Implement using declarative programming
//    }
//
//    // 6. Find the top N actors who have appeared in the most movies.
//    // Return a list of actor names.
//    public List<String> findTopNActors(int n) {
//        // Implement using declarative programming
//    }
//
//    // 7. Find all movies where a specific actor and director have worked together.
//    // Return a list of movie titles.
//    public List<String> findMoviesByActorAndDirector(String actorName, String directorName) {
//        // Implement using declarative programming
//    }
//
//    // 8. Find the most common genre for each actor.
//    // Return a map with actor names as keys and the most common genre as values.
//    public Map<String, String> findMostCommonGenrePerActor() {
//        // Implement using declarative programming
//    }
//
//    // 9. Find the director with the highest average movie rating (1-5).
//    // Assume there is a method: double getMovieRating(Movie movie), which returns the rating of a movie.
//    // Return the director name.
//    public String findDirectorWithHighestAverageRating() {
//        // Implement using declarative programming
//    }


}
