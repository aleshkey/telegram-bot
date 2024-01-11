package parser.KufarParser.handler;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.handler.abstractions.Handler;
import parser.KufarParser.model.User;
import parser.KufarParser.util.ClassifiedUpdate;
import parser.KufarParser.util.enums.TelegramType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Component
public class HandlersMap {
    private HashMap<TelegramType, List<Handler>> hashMap = new HashMap<>();
    private final List<Handler> handlers;

    // Тут точно также находим все обработчики, просто в первом случае я использовал
    // @Autowired. Это немного лучше.
    public HandlersMap(List<Handler> handlers) {
        this.handlers = handlers;
    }

    @PostConstruct
    private void init() {
        for(Handler handler : handlers) {
            if(!hashMap.containsKey(handler.getHandleType()))
                hashMap.put(handler.getHandleType(), new ArrayList<>());

            hashMap.get(handler.getHandleType()).add(handler);
        }

        hashMap.values().forEach(h -> h.sort(new Comparator<Handler>() {
            @Override
            public int compare(Handler o1, Handler o2) {
                return o2.priority() - o1.priority();
            }
        }));
    }

    public Answer execute(ClassifiedUpdate classifiedUpdate, User user) {
        if(!hashMap.containsKey(classifiedUpdate.getTelegramType()))
            return new Answer();

        for (Handler handler : hashMap.get(classifiedUpdate.getTelegramType())) {
            if(handler.condition(user, classifiedUpdate))
                return handler.getAnswer(user, classifiedUpdate);
        }
        return null;
    }
}
