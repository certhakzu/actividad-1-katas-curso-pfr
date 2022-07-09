package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import util.DataUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
    Goal: Create a datastructure from the given data:

    This time we have 4 seperate arrays each containing lists, videos, boxarts, and bookmarks respectively.
    Each object has a parent id, indicating its parent.
    We want to build an array of list objects, each with a name and a videos array.
    The videos array will contain the video's id, title, bookmark time, and smallest boxart url.
    In other words we want to build the following structure:

    [
        {
            "name": "New Releases",
            "videos": [
                {
                    "id": 65432445,
                    "title": "The Chamber",
                    "time": 32432,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg"
                },
                {
                    "id": 675465,
                    "title": "Fracture",
                    "time": 3534543,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/Fracture120.jpg"
                }
            ]
        },
        {
            "name": "Thrillers",
            "videos": [
                {
                    "id": 70111470,
                    "title": "Die Hard",
                    "time": 645243,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"
                },
                {
                    "id": 654356453,
                    "title": "Bad Boys",
                    "time": 984934,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg"
                }
            ]
        }
    ]

    DataSource: DataUtil.getLists(), DataUtil.getVideos(), DataUtil.getBoxArts(), DataUtil.getBookmarkList()
    Output: the given datastructure
*/
public class Kata11 {
    public static List<Map> execute() {
        List<Map> lists = DataUtil.getLists();
        /*
        [
            {
                "id": 5434364,
                "name": "New Releases" ***
            }
         ]
        */
        List<Map> videos = DataUtil.getVideos();
        /*
        [
            {
                "listId": 5434364,
                "id": 65432445, ***
                "title": "The Chamber"
            }
        ]
         */
        List<Map> boxArts = DataUtil.getBoxArts();
        /*
        [
            {
                videoId: 65432445,
                width: 130, *
                height:200, *
                url:"http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg" ********
            }
        ]
        */
        List<Map> bookmarkList = DataUtil.getBookmarkList();
        /*
        [
            {
                videoId: 65432445,
                time: 32432 ***
            }
        ]
         */

        Stream<Map> listasStream = lists.stream();
        Stream<Map> videosStream = videos.stream();
        Stream<Map> boxArtsStream = boxArts.stream();
        Stream<Map> bookmarkListStream = bookmarkList.stream();

        List<Map> collect = lists.stream()
                .map(element -> ImmutableMap.of(
                        "name", element.get("name"),
                        "videos", videos.stream()
                                .filter(video -> video.get("listId").equals(element.get("id")))
                                .map(data -> ImmutableMap.of(
                                        "id", data.get("id"),
                                        "title", data.get("title"),
                                        "time", bookmarkList.stream()
                                                .filter(bookmark -> bookmark.get("videoId").equals(data.get("id")))
                                                .map(time -> time.get("time"))
                                                .findFirst()
                                                .get(),
                                        "boxart", boxArts.stream()
                                                .filter(boxart -> boxart.get("videoId").equals(data.get("id")))
                                                .map(boxart-> boxart.get("url"))
                                                .sorted()
                                                .findFirst()
                                                .get()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
        
        return collect;
    }
}
