package codegym.repositories.User.Impl;

import com.codegym.model.User.User;
import com.codegym.repositories.User.IUserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class UserRepositoryImpl implements IUserRepository {
    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<User> getAllUser() {
        TypedQuery<User> query = this.entityManager.createQuery("select s from User s", User.class);
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public void addUser(User user) {
        this.entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        this.entityManager.merge(user);
    }

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = this.entityManager.createQuery("select u from User u where u.username=:username", User.class);
        query.setParameter("username", username);
        List<User> user = query.getResultList();
        return user.get(0);
    }

    @Override
//    @Transactional
    public void removeAccount(User user) {
//        Entity userEntity

//        System.out.println(user.getId());
//        User userEntity = this.entityManager.find(User.class, user.getId());
//        System.out.println(userEntity.getId());
//
//            this.entityManager.getTransaction().begin();
//            this.entityManager.remove(userEntity);
//            this.entityManager.getTransaction().commit();

        javax.persistence.Query query = this.entityManager.createQuery("delete from User  where username=:username");
        query.setParameter("username", user.getUsername());
        int result=query.executeUpdate();
    }
}
