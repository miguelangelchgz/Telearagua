package ve.gob.tla.telearagua.telearagua;

public class Post {
    public Post(String title, String link, String image_link, String description) {
        this.title = title;
        this.link = link;
        this.image_link = image_link;
        this.description = description;

    }

    public String description, title, link, image_link;

}
