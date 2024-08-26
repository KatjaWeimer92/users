package de.ait.users.repository;

import de.ait.users.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//@Component
@Repository
public class UserRepositoryImp implements UserRepository{

    private List<User> database = new ArrayList<>(List.of(
            new User(1L, "Jack", "jack@mail.com","qwert1"),
            new User(2L, "Ann", "ann@mail.com","qkjda"),
            new User(3L, "Jack", "jack1977@mail.com","qwhhhd"),
            new User(4L, "Lena", "lena@mail.com","qljsda")
    ));



    @Override
    public List<User> findAll() {
        return new ArrayList<>(database);
    }

    @Override
    public User save(User user) {
        if(user.getId()==null){
            return createUser(user);  //Ctrl+Alt+M
        } else {
            return updateUser(user);
        }

    }

    private  User updateUser(User user){
        Optional<User> userFroDB = findById(user.getId());
        if(userFroDB.isEmpty()){
            return null;
        } else {
            User u = userFroDB.get();
            u.setName(user.getName());
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
        }
        return user;
    }
    private User createUser(User user) {
        Long newId = database.get(database.size() - 1).getId() + 1;
        user.setId(newId);
        database.add(user);
        return user;
    }

    private Optional<User> findById(Long id){
        return database.stream()
                .filter(u -> u.getId().equals(id))
                .findAny();
    }

}