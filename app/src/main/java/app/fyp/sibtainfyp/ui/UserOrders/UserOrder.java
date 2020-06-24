package app.fyp.sibtainfyp.ui.UserOrders;

public class UserOrder {
    private int id;

    private String title;
    private int image;

    public UserOrder(int id, String title, int image) {
        this.title = title;
        this.id = id;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }


}
