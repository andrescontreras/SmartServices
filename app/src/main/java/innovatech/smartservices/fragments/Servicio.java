package innovatech.smartservices.fragments;

public class Servicio {

    private String title;
    private String Category;
    private String Decription;
    private int Thumbnail;

    public Servicio (){

    }

    public Servicio(String title , String category , String decription , int thumbnail) {
        this.title = title;
        Category = category;
        Decription = decription;
        Thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDecription() {
        return Decription;
    }

    public void setDecription(String decription) {
        Decription = decription;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
