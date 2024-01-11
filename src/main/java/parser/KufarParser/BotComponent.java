package parser.KufarParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import parser.KufarParser.config.BotConfig;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.handler.ClassifiedUpdateHandler;
import parser.KufarParser.util.ClassifiedUpdate;

@Component
public class BotComponent extends TelegramLongPollingBot {

    @Autowired
    private BotConfig botConfig;

    @Autowired
    private ClassifiedUpdateHandler classifiedUpdateHandler;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            this.sendMessage(classifiedUpdateHandler.request(new ClassifiedUpdate(update)));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(Answer answer) throws TelegramApiException {
        if (answer.getSendAudio()!=null){
            this.execute(answer.getSendAudio());
        }

        if (answer.getSendAnimation()!=null){
            this.execute(answer.getSendAnimation());
        }

        if (answer.getSendDocument()!=null){
            this.execute(answer.getSendDocument());
        }

        if (answer.getSendSticker()!=null){
            this.execute(answer.getSendSticker());
        }

        if (answer.getBotApiMethod()!=null){
            this.execute(answer.getBotApiMethod());
        }

        if (answer.getSendPhoto()!=null){
            this.execute(answer.getSendPhoto());
        }

        if (answer.getSendVideo()!=null){
            this.execute(answer.getSendVideo());
        }
    }
}
