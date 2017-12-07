package pl.mitusinski;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Łukasz on 18.05.2017.
 */
public class QuizUpdater extends Thread {

    private static final String QUERY_TITLE = "title";
    private static final String QUERY_DESCRIPTION = ".quizLead > p";
    private static final String QUERY_HAS_LINK = "form > input[type='submit']";

    private static final String QUIZ_URL = "http://wiadomosci.gazeta.pl/wiadomosci/13,129662,{id},x.html";
    private static final String ID_TAG = "{id}";
    private static final int MAX_FAILURES = 10;

    private BooleanProperty sync = new SimpleBooleanProperty(false);
    private int count = 0;

    QuizUpdater() {
    }

    public void update() {
        start();
    }

    @Override
    public void run() {
        sync.set(true);
        QuizList quizList = getQuizList();
        List<Quiz> list = quizList.getQuizList();
        List<Quiz> newList = new ArrayList<>();
        list.sort((q1, q2) -> new Integer(q1.getId()).compareTo(new Integer(q2.getId())));
        int next = Integer.parseInt(list.get(0).getId());

        for (Quiz q : list) {
            next++;
            while (Integer.parseInt(q.getId()) > next) {
                Quiz quizData = getQuizData(next);
                if (quizData.getCorrupted().equals("N")){
                    count++;
                }
                newList.add(quizData);
                next++;
            }
            newList.add(q);
        }

        setQuizList(new QuizList(newList));
        int failures = 0;
        while (failures < MAX_FAILURES) {
            next++;
            Quiz quizData = getQuizData(next);
            if (quizData == null || !"N".equals(quizData.getCorrupted())) {
                failures++;
            } else {
                failures = 0;
                count++;
                newList.add(quizData);
            }
        }
        setQuizList(new QuizList(newList));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sync.set(false);
    }

    public QuizList getQuizList() {
        File file = new File(Main.DATA_FILE);
        QuizList quizList = new QuizList();
        try {
            JAXBContext jaxb = JAXBContext.newInstance(QuizList.class);
            Unmarshaller unm = jaxb.createUnmarshaller();
            quizList = (QuizList) unm.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return quizList;
    }

    public void setQuizList(QuizList quizList) {
        File file = new File(Main.DATA_FILE);
        JAXBContext jaxb = null;
        try {
            jaxb = JAXBContext.newInstance(QuizList.class);
            Marshaller msh = jaxb.createMarshaller();
            msh.marshal(quizList, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private Quiz getQuizData(int id) {
        String url = getUrl(id);
        try {
            Document doc = Jsoup.connect(url).get();

            String title = doc.select(QUERY_TITLE).text();
            String desc = doc.select(QUERY_DESCRIPTION).text();
            boolean hasLink = doc.select(QUERY_HAS_LINK).size() > 0;
            Date added = new Date();
            String corrupted = !title.isEmpty() && hasLink ? "N" : "Y";
            Quiz quiz = new Quiz(Integer.toString(id), title, desc, added.toString(), corrupted);
            return quiz;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BooleanProperty getSync() {
        return sync;
    }

    public static String getUrl(int id) {
        String quizId = String.format("%4s", Integer.toString(id)).replace(" ", "0");
        String url = QUIZ_URL.replace(ID_TAG, quizId);
        return url;
    }

    public int getNewCount() {
        return count;
    }
}
