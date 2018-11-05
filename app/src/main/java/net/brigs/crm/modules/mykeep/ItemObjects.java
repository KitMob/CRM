package net.brigs.crm.modules.mykeep;

public class ItemObjects {

    private String  _title;
    private String  _content;
    private String     _image;
    private String  _color;
    private String  _lastUpdateDate;
    private String  _creationDate;

    public ItemObjects(String title, String content, String color) {

        _title = title;
        _content = content;
        _image = null;
        _color = color;
    }

    public ItemObjects(String title, String content, String image, String color) {

        _title = title;
        _content = content;
        _image = image;
        _color = color;
    }

    public ItemObjects(String title, String content, String color, String lastUpdateDate, String creationDate) {

        _title = title;
        _content = content;
        _color = color;
        _lastUpdateDate = lastUpdateDate;
        _creationDate = creationDate;
    }

    public ItemObjects(String title, String content, String image, String color, String lastUpdateDate, String creationDate) {

        _title = title;
        _content = content;
        _image = image;
        _color = color;
        _lastUpdateDate = lastUpdateDate;
        _creationDate = creationDate;
    }

    // Getters
    public String   getTitle() {
        return _title;
    }

    String          getContent() { return _content; }

    String          getColor() {
        return _color;
    }

    String             getImage() {
        return _image;
    }

    String          getLastUpdateDate() { return _lastUpdateDate; }

    String          getCreationDate() { return  _creationDate; }

    // Setters
    public void     setTitle(String title) {
        _title = title;
    }

    public void     setContent(String content) {
        _content = content;
    }

    public void     setColor(String color) {
        _color = color;
    }

    public void     setImage(String image) {
        _image = image;
    }

    public void     setLastUpdateDate(String lastUpdateDate) { _lastUpdateDate = lastUpdateDate; }

    public void     setCreationDate(String creationDate) { _creationDate = creationDate; }
}
