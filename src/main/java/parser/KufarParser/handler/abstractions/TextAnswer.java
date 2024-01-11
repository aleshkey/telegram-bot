package parser.KufarParser.handler.abstractions;


import jakarta.persistence.MappedSuperclass;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.model.User;
import parser.KufarParser.util.ClassifiedUpdate;

@MappedSuperclass
public interface TextAnswer{

    Class handler();

    Object getFindBy();

    Answer getAnswer(ClassifiedUpdate update, User user);

}
