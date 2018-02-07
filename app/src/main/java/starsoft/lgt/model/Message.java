package starsoft.lgt.model;

public class Message {

    public final String title;
    public final String date;
    public final String content;

    public Message(String title, String content, String date) {
        this.title = title;
        this.date = date;
        this.content = content;
    }

}