package parser.KufarParser.commands;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.handler.CommandHandler;
import parser.KufarParser.handler.abstractions.Command;
import parser.KufarParser.model.User;
import parser.KufarParser.util.ClassifiedUpdate;
import parser.KufarParser.util.SendMessageBuilder;

@Component
public class StartCommand implements Command {
    @Override
    public Class handler() {
        return CommandHandler.class;
    }

    @Override
    public Object getFindBy() {
        return "/start";
    }

    @SneakyThrows
    @Override
    public Answer getAnswer(ClassifiedUpdate update, User user) {
        return new SendMessageBuilder().chatId(user.getChatId()).message("Hello, "+user.getFirstName()+"!").build();
    }
}