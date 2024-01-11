package parser.KufarParser.text_answer;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.constatnts.States;
import parser.KufarParser.handler.TextHandler;
import parser.KufarParser.handler.abstractions.TextAnswer;
import parser.KufarParser.model.User;
import parser.KufarParser.service.UserService;
import parser.KufarParser.util.ClassifiedUpdate;
import parser.KufarParser.util.SendMessageBuilder;

@Component
public class AddURLDescription implements TextAnswer {

    @Autowired
    private UserService userService;

    @Override
    public Class handler() {
        return TextHandler.class;
    }

    @Override
    public Object getFindBy() {
        return States.ADD_URL_DESCRIPTION.toString();
    }

    @SneakyThrows
    @Override
    public Answer getAnswer(ClassifiedUpdate update, User user) {
        user.getState().setStateValue(null);
        user.getLinks().get(user.getLinks().size()-1).setDescription(update.getUpdate().getMessage().getText());
        userService.save(user);
        return new SendMessageBuilder().chatId(user.getChatId()).message("Successfully added link").build();
    }
}
