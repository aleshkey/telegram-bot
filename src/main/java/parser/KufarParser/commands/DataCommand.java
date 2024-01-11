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
public class DataCommand implements Command {
    @Override
    public Class handler() {
        return CommandHandler.class;
    }

    @Override
    public Object getFindBy() {
        return "/data";
    }

    @SneakyThrows
    @Override
    public Answer getAnswer(ClassifiedUpdate update, User user) {

        StringBuilder message = new StringBuilder(
                "Name: " + user.getFirstName() + ";\n" +
                "Username: " + user.getUserName() + ";\n" +
                "Links:\n"
        );

        for (var link : user.getLinks()){
            message.append("<a href=\"").append(link.getUrl()).append("\">")
                    .append(link.getDescription()).append("</a>\n");
        }

        return new SendMessageBuilder().chatId(user.getChatId()).message(message.toString()).build();
    }
}
