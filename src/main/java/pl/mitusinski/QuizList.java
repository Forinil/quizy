package pl.mitusinski;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Łukasz on 18.05.2017.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "quizlist")
public class QuizList {

    public QuizList() {
    }

    public QuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    @XmlElement(name = "quiz")
    private List<Quiz> quizList = new ArrayList<>();

    public List<Quiz> getQuizList() {
        return quizList;
    }
}
