package parser.KufarParser.handler.abstractions;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.MappedSuperclass;
import org.springframework.beans.factory.annotation.Autowired;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.model.User;
import parser.KufarParser.util.ClassifiedUpdate;
import parser.KufarParser.util.enums.TelegramType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractTextHandler implements Handler{
    protected final Map<Object, TextAnswer> allAnswers = new HashMap<>();
    // Найдём все команды для обработчика
    @Autowired
    private List<TextAnswer> answers;

    protected abstract HashMap<Object, TextAnswer> createMap();

    @PostConstruct
    private void init() {
        answers.forEach(a -> {
            allAnswers.put(a.getFindBy(), a);
            if(Objects.equals(a.handler().getName(), this.getClass().getName())) {
                createMap().put(a.getFindBy(), a);

                System.out.println(a.getClass().getSimpleName() + " was added for " + this.getClass().getSimpleName());
            }
        });
    }
}
