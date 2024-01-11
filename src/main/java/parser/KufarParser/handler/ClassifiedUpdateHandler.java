package parser.KufarParser.handler;

import org.springframework.stereotype.Service;
import parser.KufarParser.constatnts.Answer;
import parser.KufarParser.service.UserService;
import parser.KufarParser.util.ClassifiedUpdate;

@Service
public class ClassifiedUpdateHandler {

    private final UserService userService;

    private final HandlersMap commandMap;

    public ClassifiedUpdateHandler(UserService userService, HandlersMap commandMap) {
        this.userService = userService;
        this.commandMap = commandMap;
    }

    public Answer request(ClassifiedUpdate classifiedUpdate) {
        return commandMap.execute(classifiedUpdate,
                userService.findUserByUpdate(classifiedUpdate));
    }
}