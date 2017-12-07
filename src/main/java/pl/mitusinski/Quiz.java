package pl.mitusinski;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Łukasz on 16.05.2017.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "quiz")
public class Quiz {

    public Quiz() {
    }

    public Quiz(String id, String title, String desc, String added, String corrupted) {
        setId(id);
        setTitle(title);
        setDesc(desc);
        setAdded(added);
        setCorrupted(corrupted);
    }

    private StringProperty id = new SimpleStringProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty desc = new SimpleStringProperty();
    private StringProperty added = new SimpleStringProperty();
    private StringProperty corrupted = new SimpleStringProperty();

    public StringProperty getIdProperty() {
        return id;
    }

    public StringProperty getTitleProperty() {
        return title;
    }

    public StringProperty getDescProperty() {
        return desc;
    }

    public StringProperty getAddedProperty() {
        return added;
    }

    public StringProperty getCorruptedProperty() {
        return corrupted;
    }

    public String getId() {
        return getIdProperty().get();
    }

    public void setId(String id) {
        getIdProperty().set(id);
    }

    public String getTitle() {
        return getTitleProperty().get();
    }

    public void setTitle(String title) {
        getTitleProperty().set(title);
    }

    public String getDesc() {
        return getDescProperty().get();
    }

    public void setDesc(String desc) {
        getDescProperty().set(desc);
    }

    public String getAdded() {
        return getAddedProperty().get();
    }

    public void setAdded(String Added) {
        getAddedProperty().set(Added);
    }

    public String getCorrupted() {
        return getCorruptedProperty().get();
    }

    public void setCorrupted(String Corrupted) {
        getCorruptedProperty().set(Corrupted);
    }

}
