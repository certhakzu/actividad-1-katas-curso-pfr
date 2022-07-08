package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.BoxArt;
import model.MovieList;
import util.DataUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Goal: Retrieve id, title, and a 150x200 box art url for every video
    DataSource: DataUtil.getMovieLists()
    Output: List of ImmutableMap.of("id", "5", "title", "Bad Boys", "boxart": BoxArt)
*/
public class Kata4 {
    public static List<Map> execute() {
        List<MovieList> movieLists = DataUtil.getMovieLists();

        List<Map> collect = movieLists.stream()
                .map(movieList -> movieList.getVideos().stream())
                .flatMap(movieStream -> movieStream.distinct())
                .map(movie ->
                        ImmutableMap.of(
                                "id", movie.getId(),
                                "title", movie.getTitle(),
                                "boxart",
                                movie.getBoxarts()
                                        .stream()
                                        .filter(boxArt -> boxArt.getWidth()==150 && boxArt.getHeight()==200)
                                        .findFirst())).collect(Collectors.toList());


        return collect;//ImmutableList.of(ImmutableMap.of("id", 5, "title", "Bad Boys", "boxart", new BoxArt(150, 200, "url")));
    }
}
