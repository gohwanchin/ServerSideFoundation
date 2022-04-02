package sg.edu.nus.workshop16.model;

import java.io.ByteArrayInputStream;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Game {
    private Integer gid;
    private String name;
    private Integer year;
    private Integer ranking;
    private Integer usersRated;
    private String url;
    private String image;

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getUsersRated() {
        return usersRated;
    }

    public void setUsersRated(Integer usersRated) {
        this.usersRated = usersRated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("gid", gid)
                .add("name", name)
                .add("year", year)
                .add("ranking", ranking)
                .add("users_rated", usersRated)
                .add("url", url)
                .add("image", image)
                .build();
    }

    public static Game create(String rec) {
        JsonReader r = Json.createReader(new ByteArrayInputStream(rec.getBytes()));
        JsonObject o = r.readObject();
        Game g = new Game();
        g.setGid(o.getInt("gid"));
        g.setName(o.getString("name"));
        g.setYear(o.getInt("year"));
        g.setRanking(o.getInt("ranking"));
        g.setUsersRated(o.getInt("users_rated"));
        g.setUrl(o.getString("url"));
        g.setImage(o.getString("image"));
        return g;
    }
}
