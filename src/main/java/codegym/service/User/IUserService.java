package codegym.service.User;

import com.codegym.model.User.User;

import java.util.HashMap;
import java.util.List;

public interface IUserService extends GeneralService<User> {
    List<User> getAllUser();
    void addUser(User user);
    void updateUser(User user);
    boolean isUserExist(User user);
    boolean checkSignin(User user);
    void removeAccount(User user);

    HashMap<String, String> getUserMap();
    User findUserByName(String username);
}
