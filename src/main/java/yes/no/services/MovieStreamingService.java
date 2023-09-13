package yes.no.services;

import yes.no.models.Genre;
import yes.no.models.Movie;
import yes.no.models.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MovieStreamingService {
    private final List<Movie> movies;
    private final List<User> users;

    public MovieStreamingService(List<Movie> movies, List<User> users) {
        this.movies = movies;
        this.users = users;
    }

    public Optional<Genre> getMostWatchGenre() {
        var genreMap = users.stream()
                .flatMap(user -> user.watchHistory().stream())
                .collect(Collectors.groupingBy(movie -> movie.genre(), Collectors.counting()));

        return genreMap.entrySet().stream().max((a, b) -> (int) (a.getValue() - b.getValue())).map(e -> e.getKey());

    }

    private long getWatchedTime(User user) {
        return user.watchHistory().stream().collect(Collectors.summingLong(movie -> movie.duration().toMinutes()));
    }

    public Optional<User> getUserWithLongestWatchTime() {
        return users.stream().max((a, b) -> (int) (getWatchedTime(a) - getWatchedTime(b)));
    }

    public List<Movie> getMoviesOver(long minutes) {
        return movies.stream().filter(movie -> movie.duration().toMinutes() > minutes).toList();
    }
}
