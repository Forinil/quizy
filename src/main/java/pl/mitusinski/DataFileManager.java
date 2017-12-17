package pl.mitusinski;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

/**
 * Created by Łukasz on 11.12.2017.
 * github.com/lmitu
 */
class DataFileManager {

    QuizList getQuizList() {
        File file = new File(Main.DATA_FILE);
        QuizList quizList = new QuizList();
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(this.getClass().getResource(Main.DATA_FILE_XSD));

            JAXBContext jaxb = JAXBContext.newInstance(QuizList.class);
            Unmarshaller unm = jaxb.createUnmarshaller();
            unm.setSchema(schema);
            unm.setEventHandler(new QuizFileValidationEventHandler());
            quizList = (QuizList) unm.unmarshal(file);
        } catch (JAXBException | SAXException e) {
            e.printStackTrace();
        }
        return quizList;
    }

    void setQuizList(QuizList quizList) {
        File file = new File(Main.DATA_FILE);
        JAXBContext jaxb;
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(this.getClass().getResource(Main.DATA_FILE_XSD));

            jaxb = JAXBContext.newInstance(QuizList.class);
            Marshaller msh = jaxb.createMarshaller();
            msh.setSchema(schema);
            msh.setEventHandler(new QuizFileValidationEventHandler());
            msh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            msh.marshal(quizList, file);
        } catch (JAXBException | SAXException e) {
            e.printStackTrace();
        }
    }
}
