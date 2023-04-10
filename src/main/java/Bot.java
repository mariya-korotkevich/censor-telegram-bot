import abstracts.MessageFilter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    private static final String BOT_TOKEN = "";
    private static final String BOT_NAME = "";


    private final MessageFilter messageFilter;

    public Bot(MessageFilter messageFilter) {
        this.messageFilter = messageFilter;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            Message inMessage = update.getMessage();
            String chatId = inMessage.getChatId().toString();
            String response = parseMessage(inMessage.getText());
            SendMessage outMessage = new SendMessage();
            outMessage.setChatId(chatId);
            outMessage.setText(response);
            try {
                execute(outMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String parseMessage(String textMsg){
        if ("/start".equals(textMsg)){
            return "Hello, bro";
        } else {
            return messageFilter.filter(textMsg);
        }
    }
}