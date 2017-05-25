package es.esy.revolusipingwin.mychat;

/**
 * Created by Hari Nugroho on 24/05/2017.
 */

public class User {
    private String name;
    private String imageUrl;
    private String idMyChat;

    public User() {
    }

    public User(String name, String imageUrl, String idMyChat) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.idMyChat = idMyChat;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getIdMyChat() {
        return idMyChat;
    }
}
