package yes.no.services;

import org.junit.jupiter.api.Test;
import yes.no.models.Genre;
import yes.no.models.Movie;
import yes.no.models.User;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MovieStreamingServiceTest {

    @Test
    void getMostWatchGenre() {
        var movies = List.of(
                new Movie("a", Genre.ACTION, Duration.ofMinutes(10)),
                new Movie("b", Genre.ACTION, Duration.ofMinutes(10)),
                new Movie("c", Genre.COMEDY, Duration.ofMinutes(10)),
                new Movie("d", Genre.DRAMA, Duration.ofMinutes(10)),
                new Movie("e", Genre.FANTASY, Duration.ofMinutes(10)),
                new Movie("f", Genre.SCI_FI, Duration.ofMinutes(10))
        );
        var users = List.of(
                new User("a", 1, List.of(movies.get(0), movies.get(1))),
                new User("b", 1, List.of(movies.get(2), movies.get(4))),
                new User("c", 1, List.of(movies.get(1), movies.get(5))),
                new User("d", 1, List.of(movies.get(1), movies.get(0)))

        );
        var movieService = new MovieStreamingService(movies, users);
        var expected = Optional.of(Genre.ACTION);

        var mostWatchGenre = movieService.getMostWatchGenre();

        assertEquals(expected, mostWatchGenre);

    }

    @Test
    void getUserWithLongestWatchTime() {
        var movies = List.of(
                new Movie("a", Genre.ACTION, Duration.ofMinutes(10)),
                new Movie("b", Genre.ACTION, Duration.ofMinutes(10)),
                new Movie("c", Genre.COMEDY, Duration.ofMinutes(10)),
                new Movie("d", Genre.DRAMA, Duration.ofMinutes(10)),
                new Movie("e", Genre.FANTASY, Duration.ofMinutes(10)),
                new Movie("f", Genre.SCI_FI, Duration.ofMinutes(10))
        );
        var users = List.of(
                new User("a", 1, List.of(movies.get(0), movies.get(1), movies.get(2))),
                new User("b", 1, List.of(movies.get(2), movies.get(4))),
                new User("c", 1, List.of(movies.get(1), movies.get(5))),
                new User("d", 1, List.of(movies.get(1), movies.get(0)))

        );
        var movieService = new MovieStreamingService(movies, users);
        var expected = Optional.of(users.get(0));

        var userWithLongestWatchTime = movieService.getUserWithLongestWatchTime();

        assertEquals(expected, userWithLongestWatchTime);
    }

    @Test
    void getMoviesOver() {
        var movies = List.of(
                new Movie("a", Genre.ACTION, Duration.ofMinutes(101)),
                new Movie("b", Genre.ACTION, Duration.ofMinutes(10)),
                new Movie("c", Genre.COMEDY, Duration.ofMinutes(10)),
                new Movie("d", Genre.DRAMA, Duration.ofMinutes(10)),
                new Movie("e", Genre.FANTASY, Duration.ofMinutes(10)),
                new Movie("f", Genre.SCI_FI, Duration.ofMinutes(10))
        );

        var movieService = new MovieStreamingService(movies, null);
        var expected = List.of(movies.get(0));

        var moviesOver100 = movieService.getMoviesOver(100);

        assertEquals(expected, moviesOver100);
    }
}