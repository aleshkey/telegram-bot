package parser.KufarParser.util;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import parser.KufarParser.util.enums.TelegramType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Getter
public class ClassifiedUpdate {
    private final TelegramType telegramType; // enum, чтобы всё выглядило красиво

    private final Long userId; // тот же chat-id, но выглядит красивее и получить его легче

    private String name; // получим имя пользователя. Именно имя, не @username

    private String commandName; // если это команда, то запишем её

    private final Update update; // сохраним сам update, чтобы в случае чего, его можно было достать

    private final List<String> args; // просто поделим текст сообщения, в будущем это поможет

    private String userName; // @username

    public ClassifiedUpdate(Update update) {
        this.update = update;
        this.telegramType = handleTelegramType();
        this.userId = handleUserId();
        this.args = handleArgs();
        this.commandName = handleCommandName();
    }

    public String handleCommandName() {
        if(update.hasMessage()) {
            if(update.getMessage().hasText()) {
                if(update.getMessage().getText().startsWith("/")) {
                    return update.getMessage().getText().split(" ")[0];
                } else return update.getMessage().getText();
            }
        } if(update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData().split(" ")[0];
        }
        return "";
    }

    private TelegramType handleTelegramType() {

        if(update.hasCallbackQuery())
            return TelegramType.CallBack;

        if(update.hasMessage()) {
            if(update.getMessage().hasText()) {
                if(update.getMessage().getText().startsWith("/"))
                    return TelegramType.Command;
                else
                    return TelegramType.Text;
            } else if(update.getMessage().hasSuccessfulPayment()) {
                return TelegramType.SuccessPayment;
            } else if(update.getMessage().hasPhoto())
                return TelegramType.Photo;
        } else if(update.hasPreCheckoutQuery()) {
            return TelegramType.PreCheckoutQuery;
        } else if(update.hasChatJoinRequest()) {
            return TelegramType.ChatJoinRequest;
        } else if(update.hasChannelPost()) {
            return TelegramType.ChannelPost;
        }
        else if(update.hasMyChatMember()) {
            return TelegramType.MyChatMember;
        }
        if(update.getMessage().hasDocument()) {
            return TelegramType.Text;
        }
        return TelegramType.Unknown;
    }

    private Long handleUserId() {
        if (telegramType == TelegramType.PreCheckoutQuery) {
            name = getNameByUser(update.getPreCheckoutQuery().getFrom());
            userName = update.getPreCheckoutQuery().getFrom().getUserName();
            return update.getPreCheckoutQuery().getFrom().getId();
        } else if(telegramType == TelegramType.ChatJoinRequest) {
            name = getNameByUser(update.getChatJoinRequest().getUser());
            userName = update.getChatJoinRequest().getUser().getUserName();
            return update.getChatJoinRequest().getUser().getId();
        } else if (telegramType == TelegramType.CallBack) {
            name = getNameByUser(update.getCallbackQuery().getFrom());
            userName = update.getCallbackQuery().getFrom().getUserName();
            return update.getCallbackQuery().getFrom().getId();
        } else if(telegramType == TelegramType.MyChatMember){
            name = update.getMyChatMember().getChat().getTitle();
            userName = update.getMyChatMember().getChat().getUserName();
            return update.getMyChatMember().getFrom().getId();
        } else {
            name = getNameByUser(update.getMessage().getFrom());
            userName = update.getMessage().getFrom().getUserName();
            return update.getMessage().getFrom().getId();
        }
    }

    //Разделим сообщение на аргументы
    private List<String> handleArgs() {
        List<String> list = new LinkedList<>();

        if(telegramType == TelegramType.Command) {
            String[] args = getUpdate().getMessage().getText().split(" ");
            Collections.addAll(list, args);
            list.remove(0);

            return list;
        } else if (telegramType == TelegramType.Text) {
            list.add(getUpdate().getMessage().getText());

            return list;
        } else if (telegramType == TelegramType.CallBack) {
            String[] args = getUpdate().getCallbackQuery().getData().split(" ");
            Collections.addAll(list, args);
            list.remove(0);

            return list;
        }
        return new ArrayList<>();
    }

    //Вынесли имя в другой метод
    private String getNameByUser(User user) {
        if(user.getIsBot())
            return "BOT";

        if(!user.getFirstName().isBlank() || !user.getFirstName().isEmpty())
            return user.getFirstName();

        if(!user.getUserName().isBlank() || !user.getUserName().isEmpty())
            return user.getUserName();

        return "noname";
    }

    //Лог
    public String getLog() {
        return "USER_ID : " + getUserId() +
                "\nUSER_NAME : " + getName() +
                "\nTYPE : " + getTelegramType() +
                "\nARGS : " + getArgs().toString() +
                "\nCOMMAND_NAME : " + getCommandName();
    }
}
