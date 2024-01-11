package parser.KufarParser.handler.abstractions;

import jakarta.persistence.MappedSuperclass;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.model.User;
import parser.KufarParser.util.ClassifiedUpdate;

@MappedSuperclass
public interface Command {
    // Каким обработчиком будет пользоваться команда
    Class handler();
    // С помощью чего мы найдём эту команду
    Object getFindBy();
    // Ну и тут мы уже получим ответ на самом деле
    Answer getAnswer(ClassifiedUpdate update, User user);
}
