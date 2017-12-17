package pl.mitusinski;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Łukasz on 18.05.2017.
 * github.com/lmitu
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "quizlist")
class QuizList {

    @XmlElement(name = "quiz")
    private List<Quiz> quizList = new ArrayList<>();

    QuizList() {
    }

    QuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    List<Quiz> getQuizList() {
        return quizList;
    }

    Optional<Quiz> getElementById(String id) {
        return quizList.stream()
                .filter(q -> q.getId().equals(id))
                .findFirst();
    }
}
