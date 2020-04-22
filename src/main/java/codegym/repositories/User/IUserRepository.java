package codegym.repositories.User;

import com.codegym.model.User.User;

import java.util.List;

public interface IUserRepository extends GeneralRepo<User> {
    List<User> getAllUser();
    void addUser(User user);
    void updateUser(User user);
    User findByUsername(String username);
    void removeAccount(User user);
}
