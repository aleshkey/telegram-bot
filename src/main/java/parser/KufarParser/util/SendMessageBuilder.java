package parser.KufarParser.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import parser.KufarParser.constatnts.Answer;

public class SendMessageBuilder {
    private SendMessage sendMessage;

    public SendMessageBuilder() {
        this.sendMessage = new SendMessage();
        this.sendMessage.enableHtml(true);
    }

    public SendMessageBuilder chatId(Long chatId) {
        this.sendMessage.setChatId(chatId);
        return this;
    }

    public SendMessageBuilder message(String message) {
        this.sendMessage.setText(message);
        return this;
    }



    public Answer build() throws Exception {
        sendMessage.getChatId();

        Answer answer = new Answer();
        answer.setBotApiMethod(sendMessage);

        return answer;
    }
}
