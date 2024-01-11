package parser.KufarParser.service;

import org.springframework.stereotype.Service;
import parser.KufarParser.model.Link;
import parser.KufarParser.model.State;
import parser.KufarParser.model.User;
import parser.KufarParser.repository.StateRepository;
import parser.KufarParser.repository.UserRepository;
import parser.KufarParser.util.ClassifiedUpdate;

import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final StateRepository stateRepository;

    public UserService(UserRepository userRepository, StateRepository stateRepository) {
        this.userRepository = userRepository;
        this.stateRepository = stateRepository;
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void addLink(User user, String url){
        user.getLinks().add(new Link(url, Collections.singletonList(user)));
        userRepository.save(user);
    }

    public User findUserByUpdate(ClassifiedUpdate classifiedUpdate) {

        // Проверим, существует ли этот пользователь.
        if(userRepository.findById(classifiedUpdate.getUserId()).isPresent()) {
            User user = userRepository.findById(classifiedUpdate.getUserId()).get();

            // Если мы не смогли до этого записать имя пользователя, то запишем его.
            if(user.getUserName() == null && classifiedUpdate.getUserName() != null)
                user.setUserName(classifiedUpdate.getUserName());

            // Проверим менял ли пользователя имя.
            if(user.getUserName() != null)
                if (!user.getUserName().equals(classifiedUpdate.getUserName()))
                    user.setUserName(classifiedUpdate.getUserName());

            if(!user.getFirstName().equals(classifiedUpdate.getName()))
                user.setFirstName(classifiedUpdate.getName());

            return user;
        }
        try {
            User user = new User();
            user.setFirstName(classifiedUpdate.getName());
            user.setChatId(classifiedUpdate.getUserId());
            user.setUserName(classifiedUpdate.getUserName());

            State state = new State();
            state.setStateValue(null);
            state.setUser(user);

            stateRepository.save(state);

            user.setState(state);
            userRepository.save(user);

            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
