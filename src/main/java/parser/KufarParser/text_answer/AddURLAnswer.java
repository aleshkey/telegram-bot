package parser.KufarParser.text_answer;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.constatnts.States;
import parser.KufarParser.handler.TextHandler;
import parser.KufarParser.handler.abstractions.TextAnswer;
import parser.KufarParser.model.State;
import parser.KufarParser.model.User;
import parser.KufarParser.service.UserService;
import parser.KufarParser.util.ClassifiedUpdate;
import parser.KufarParser.util.SendMessageBuilder;

@Component
public class AddURLAnswer implements TextAnswer {

    @Autowired
    private UserService userService;

    @Override
    public Class handler() {
        return TextHandler.class;
    }

    @Override
    public Object getFindBy() {
        return States.ADD_URL.toString();
    }

    @SneakyThrows
    @Override
    public Answer getAnswer(ClassifiedUpdate update, User user) {
        user.getState().setStateValue(States.ADD_URL_DESCRIPTION.toString());
        userService.addLink(user, update.getUpdate().getMessage().getText());
        return new SendMessageBuilder().chatId(user.getChatId()).message("Enter description").build();
    }

}
