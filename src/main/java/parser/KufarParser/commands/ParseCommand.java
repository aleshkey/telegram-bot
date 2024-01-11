package parser.KufarParser.commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import parser.KufarParser.BotComponent;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.constatnts.States;
import parser.KufarParser.handler.CommandHandler;
import parser.KufarParser.handler.abstractions.Command;
import parser.KufarParser.model.Link;
import parser.KufarParser.model.Product;
import parser.KufarParser.model.User;
import parser.KufarParser.service.ProductService;
import parser.KufarParser.service.UserService;
import parser.KufarParser.util.ClassifiedUpdate;
import parser.KufarParser.util.Parser;
import parser.KufarParser.util.SendMessageBuilder;

import java.lang.reflect.Type;
import java.util.*;

@Component
public class ParseCommand extends TimerTask implements Command {

    @Autowired
    BotComponent botComponent;

    private Timer timer;
    private List<String> parsedData = new ArrayList<>();
    private ClassifiedUpdate savedUpdate;
    private User savedUser;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        timer = new Timer();
        timer.scheduleAtFixedRate(this, 0, 2 * 60 * 1000); // Запуск каждые 2 минуты
    }

    @PreDestroy
    public void destroy(){
        parsedData.clear();
        savedUpdate=null;
        savedUser=null;
    }


    @Override
    public Class handler() {
        return CommandHandler.class;
    }

    @Override
    public Object getFindBy() {
        return "/parse";
    }

    @SneakyThrows
    @Override
    public Answer getAnswer(ClassifiedUpdate update, User user) {
        user.getState().setStateValue(States.PARSING.toString());
        savedUpdate = update;
        savedUser = user;
        userService.save(user);
        return new SendMessageBuilder().chatId(user.getChatId()).message("parsing starts").build();
    }


    private List<String> parse(User user){
        List<Link> links = user.getLinks();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Product>>() {}.getType();
        List<Product> allProducts = new ArrayList<>();

        for (var link : links){
            List<Product> products = gson.fromJson(Parser.parse(link.getUrl()).toString(), listType);
            allProducts.addAll(productService.check(products));
        }

        return convertListToStr(allProducts);

    }

    private List<String> convertListToStr(List<Product> products){
        List<String> res = new ArrayList<>();
        for(var product : products){
            res.add(product.toString());
        }
        return res;
    }


    @SneakyThrows
    @Override
    public void run() {
        StringBuilder comparedMessage = new StringBuilder();
        parsedData = parse(savedUser);
        if (!parsedData.isEmpty()){
            for (var data : parsedData){
                comparedMessage.append("<pre>").append(data).append("</pre>\n");
            }
            parsedData.clear();
        }
    }

}
