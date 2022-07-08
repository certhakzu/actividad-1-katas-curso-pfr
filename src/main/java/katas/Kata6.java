package katas;

import model.BoxArt;
import model.Movie;
import util.DataUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


/*
    Goal: Retrieve the url of the largest boxart using map() and reduce()
    DataSource: DataUtil.getMovieLists()
    Output: String
*/
public class Kata6 {
    public static String execute() {
        List<Movie> movies = DataUtil.getMovies();

        return movies.stream()
                .map(movie -> movie.getBoxarts())
                .reduce((boxArts1, boxArts2) -> {
                    BoxArt box1 = boxArts1.stream().max(Comparator.comparing(BoxArt::getWidth)).orElseThrow();
                    BoxArt box2 = boxArts2.stream().max(Comparator.comparing(BoxArt::getWidth)).orElseThrow();
                    return Arrays.asList(box1.getWidth() > box2.getWidth() ? box1 : box2);
                }).orElseThrow().stream().map(boxArt -> boxArt.getUrl()).findFirst().orElseThrow();
    }
}
