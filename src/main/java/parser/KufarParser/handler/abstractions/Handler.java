package parser.KufarParser.handler.abstractions;


import jakarta.persistence.MappedSuperclass;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.model.User;
import parser.KufarParser.util.ClassifiedUpdate;
import parser.KufarParser.util.enums.TelegramType;

@MappedSuperclass
public interface Handler {

    // Какой тип сообщения будет обработан
    TelegramType getHandleType();

    // Приоритет обработчика
    int priority();

    // Условия, при которых мы воспользуемся этим обработчиком
    boolean condition(User user, ClassifiedUpdate update);

    // В этом методе, с помощью апдейта мы будем получать answer
    Answer getAnswer(User user, ClassifiedUpdate update);
}
