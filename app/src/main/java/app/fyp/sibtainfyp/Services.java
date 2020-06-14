package app.fyp.sibtainfyp;

public class Services {
    private int id;
    private boolean isSelected;

    private String title;
    private int image;

    public Services(int id, String title,int image) {
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


    public void setSelected(boolean selection){
        this.isSelected = selection;
    }

    public boolean isSelected(){
        return isSelected;
    }
}