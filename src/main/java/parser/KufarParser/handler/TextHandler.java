package parser.KufarParser.handler;

import org.springframework.stereotype.Component;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.handler.abstractions.AbstractHandler;
import parser.KufarParser.handler.abstractions.AbstractTextHandler;
import parser.KufarParser.handler.abstractions.Command;
import parser.KufarParser.handler.abstractions.TextAnswer;
import parser.KufarParser.model.User;
import parser.KufarParser.util.ClassifiedUpdate;
import parser.KufarParser.util.enums.TelegramType;

import java.util.HashMap;

@Component
public class TextHandler extends AbstractTextHandler {

    private HashMap<Object, TextAnswer> hashMap = new HashMap<>();

    @Override
    protected HashMap<Object, TextAnswer> createMap() {
        return hashMap;
    }

    @Override
    public TelegramType getHandleType() {
        return TelegramType.Text;
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean condition(User user, ClassifiedUpdate update) {
        return update.getTelegramType() == TelegramType.Text;
    }

    @Override
    public Answer getAnswer(User user, ClassifiedUpdate update) {
        return hashMap.get(user.getState().getStateValue()).getAnswer(update, user);
    }
}