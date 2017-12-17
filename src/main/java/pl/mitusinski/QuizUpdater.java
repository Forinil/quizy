package pl.mitusinski;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.mitusinski.language.LanguageCodes;
import pl.mitusinski.language.LanguageRecognizer;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Łukasz on 18.05.2017.
 * github.com/lmitu
 */
class QuizUpdater extends Thread {

    private static final String QUERY_TITLE = "title";
    private static final String QUERY_DESCRIPTION = ".quizLead > p";
    private static final String QUERY_HAS_LINK = "form > input[type='submit']";

    private static final String QUIZ_URL = "http://wiadomosci.gazeta.pl/wiadomosci/13,129662,{id},x.html";
    private static final String ID_TAG = "{id}";
    private static final int MAX_FAILURES = 10;
    private final PropertiesManager propertiesManager = new PropertiesManager();
    private final LanguageRecognizer languageRecognizer = new LanguageRecognizer();
    private final DataFileManager dataFileManager = new DataFileManager();
    private BooleanProperty sync = new SimpleBooleanProperty(false);
    private int count = 0;

    QuizUpdater() {
    }

    static String getUrl(int id) {
        String quizId = String.format("%4s", Integer.toString(id)).replace(" ", "0");
        return QUIZ_URL.replace(ID_TAG, quizId);
    }

    void update() {
        start();
    }

    @Override
    public void run() {
        sync.set(true);
        QuizList quizList = dataFileManager.getQuizList();
        List<Quiz> list = quizList.getQuizList();
        List<Quiz> newList = new ArrayList<>();
        list.sort(Comparator.comparing(q2 -> new Integer(q2.getId())));
        int next = Integer.parseInt(list.get(0).getId());

        for (Quiz q : list) {
            next++;
            while (Integer.parseInt(q.getId()) > next) {
                Optional<Quiz> quizData = getQuizData(next);
                quizData.ifPresent(quiz -> {
                    if (quiz.getCorrupted().equals("N")) {
                        count++;
                    }
                    newList.add(quiz);
                });
                next++;
            }

            if (tryDetectingLanguage(q)) {
                String language = languageRecognizer.getLanguage(q.getCorrupted(), q.getTitle());
                q.setLanguage(language);
            }

            newList.add(q);
        }

        dataFileManager.setQuizList(new QuizList(newList));
        AtomicInteger failures = new AtomicInteger();
        while (failures.get() < MAX_FAILURES) {
            next++;
            Optional<Quiz> quizData = getQuizData(next);

            quizData.ifPresent(quiz -> {
                if (!"N".equals(quiz.getCorrupted())) {
                    failures.getAndIncrement();
                } else {
                    failures.set(0);
                    count++;
                    newList.add(quiz);
                }
            });
        }
        dataFileManager.setQuizList(new QuizList(newList));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sync.set(false);
    }

    private boolean tryDetectingLanguage(Quiz q) {
        if (propertiesManager.getResolveUnknownsFromSystemProperty()) {
            return q.getLanguage() == null || q.getLanguage().isEmpty() || q.getLanguage().equals(LanguageCodes.UNKNOWN);
        } else {
            return q.getLanguage() == null || q.getLanguage().isEmpty();
        }

    }

    private Optional<Quiz> getQuizData(int id) {
        String url = getUrl(id);
        try {
            Document doc = Jsoup.connect(url).get();

            String title = doc.select(QUERY_TITLE).text();
            String desc = doc.select(QUERY_DESCRIPTION).text();
            boolean hasLink = doc.select(QUERY_HAS_LINK).size() > 0;
            Date added = new Date();
            String corrupted = !title.isEmpty() && hasLink ? "N" : "Y";
            String language = languageRecognizer.getLanguage(corrupted, title);
            return Optional.of(new Quiz(Integer.toString(id), title, desc, added.toString(), corrupted, language));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    BooleanProperty getSync() {
        return sync;
    }

    int getNewCount() {
        return count;
    }
}
