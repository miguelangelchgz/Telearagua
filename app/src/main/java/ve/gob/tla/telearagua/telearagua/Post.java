package ve.gob.tla.telearagua.telearagua;

public class Post {
    public Post(String title,String description,String image_link,  String date, String category) {
        this.title = title;
        this.image_link = "http://tla.gob.ve/archivos/"+image_link;
        this.description = description;
        this.date = date;
        this.category = category;

    }

    public String description, title, image_link,date,category;

}