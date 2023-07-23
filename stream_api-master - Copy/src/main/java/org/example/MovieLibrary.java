package org.example;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
        List<String> moviesByYear = findMoviesByYear(2010);
        System.out.println("Movies by year " + moviesByYear);

        List<String> moviesByGenre = findMoviesByGenre("Action");
        System.out.println("Movies by genre " + moviesByGenre);

        List<String> DirectorsWithAtLeastNMovies = findDirectorsWithAtLeastNMovies(2);
        System.out.println("Directors With At Least N Movies " + DirectorsWithAtLeastNMovies);

        List<String> findActorsInGenre = findActorsInGenre("Comedy");
        System.out.println("find Actors In Genre " + findActorsInGenre);

        double average = findAverageReleaseYearForDirector("Christopher Nolan");
        System.out.println("find Average Release Year For Director " + average);

        List<String> findTopNActors = findTopNActors(10);
        System.out.println("find Top N Actors " + findTopNActors);

        List<String> MoviesByActorAndDirector = findMoviesByActorAndDirector("Leonardo DiCaprio", "Christopher Nolan");
        System.out.println("Movies By Actor And Director " + MoviesByActorAndDirector);

        Map<String, String> MostCommonGenrePerActor = findMostCommonGenrePerActor();
        System.out.println("Most Common Genre Per Actor " + MostCommonGenrePerActor);

        String DirectorWithHighestAverageRating = findDirectorWithHighestAverageRating();
        System.out.println("Director With Highest Average Rating " + DirectorWithHighestAverageRating);


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
    public double findAverageReleaseYearForDirector(String directorName) {
        List<Integer> releaseYearsForDirector = movies.stream()
                .filter(movie -> movie.getDirector() != null && movie.getDirector().getName().equals(directorName))
                .map(Movie::getReleaseYear).toList();
        if (releaseYearsForDirector.isEmpty()) {
            return 0.0;
        }
        return releaseYearsForDirector.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    //
//    // 6. Find the top N actors who have appeared in the most movies.
//    // Return a list of actor names.
    public List<String> findTopNActors(int n) {
        Map<String, Long> actorCountMap = movies.stream()
                .flatMap(movie -> movie.getActors().stream().map(Actor::getName))
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()));

        return movies.stream()
                .flatMap(movie -> movie.getActors().stream().map(Actor::getName))
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()).thenComparing(Map.Entry.comparingByKey()))
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    //
//    // 7. Find all movies where a specific actor and director have worked together.
//    // Return a list of movie titles.
    public List<String> findMoviesByActorAndDirector(String actorName, String directorName) {
        return movies.stream()
                .filter(movie -> movie.getActors().stream().anyMatch(actor -> actor.getName().equals(actorName)) &&
                        movie.getDirector().getName().equals(directorName))
                .map(Movie::getTitle)
                .collect(Collectors.toList());
    }

    //
//    // 8. Find the most common genre for each actor.
//    // Return a map with actor names as keys and the most common genre as values.
    public Map<String, String> findMostCommonGenrePerActor() {
        return movies.stream()
                .flatMap(movie -> movie.getActors().stream().map(actor -> Map.entry(actor.getName(), movie.getGenre())))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.groupingBy(Map.Entry::getValue, Collectors.counting())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse("Unknown")));
    }

    double getMovieRating(Movie movie) {
        return movie.getMovieRating(movie);
    }

    //
//    // 9. Find the director with the highest average movie rating (1-5).
//    // Assume there is a method: double getMovieRating(Movie movie), which returns the rating of a movie.
//    // Return the director name.
    public String findDirectorWithHighestAverageRating() {
        Map<Director, Double> directorAverageRatings = movies.stream()
                .filter(movie -> movie.getDirector() != null) // Filter out movies with null directors
                .collect(Collectors.groupingBy(Movie::getDirector,
                        Collectors.averagingDouble(movie -> getMovieRating(movie))));

        return directorAverageRatings.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey().getName())
                .orElse("Unknown");
    }
}



