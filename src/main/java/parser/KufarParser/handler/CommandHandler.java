package parser.KufarParser.handler;

import org.springframework.stereotype.Component;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.handler.abstractions.AbstractHandler;
import parser.KufarParser.handler.abstractions.Command;
import parser.KufarParser.model.User;
import parser.KufarParser.util.ClassifiedUpdate;
import parser.KufarParser.util.enums.TelegramType;

import java.util.HashMap;

@Component
public class CommandHandler extends AbstractHandler {

    private HashMap<Object, Command> hashMap = new HashMap<>();

    @Override
    protected HashMap<Object, Command> createMap() {
        return hashMap;
    }

    @Override
    public TelegramType getHandleType() {
        return TelegramType.Command;
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean condition(User user, ClassifiedUpdate update) {
        return hashMap.containsKey(update.getCommandName());
    }

    @Override
    public Answer getAnswer(User user, ClassifiedUpdate update) {
        return hashMap.get(update.getCommandName()).getAnswer(update, user);
    }
}
