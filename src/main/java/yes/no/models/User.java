package yes.no.models;

import java.util.List;

public record User(String name, int age, List<Movie> watchHistory) {
}
