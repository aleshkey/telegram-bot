package parser.KufarParser.commands;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.constatnts.States;
import parser.KufarParser.handler.CommandHandler;
import parser.KufarParser.handler.abstractions.Command;
import parser.KufarParser.model.User;
import parser.KufarParser.service.UserService;
import parser.KufarParser.util.ClassifiedUpdate;
import parser.KufarParser.util.SendMessageBuilder;

@Component
public class AddLinkCommand implements Command {

    @Autowired
    private UserService userService;


    @Override
    public Class handler() {
        return CommandHandler.class;
    }

    @Override
    public Object getFindBy() {
        return "/add_link";
    }

    @SneakyThrows
    @Override
    public Answer getAnswer(ClassifiedUpdate update, User user) {
        user.getState().setStateValue(States.ADD_URL.toString());
        userService.save(user);
        return new SendMessageBuilder().chatId(user.getChatId()).message("Enter URL").build();
    }
}
