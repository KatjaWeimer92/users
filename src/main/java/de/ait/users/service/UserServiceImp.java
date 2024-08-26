package de.ait.users.service;

import de.ait.users.dto.UserRequestDto;
import de.ait.users.dto.UserResponseDto;
import de.ait.users.entity.User;
import de.ait.users.repository.UserRepository;
import de.ait.users.repository.UserRepositoryImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class UserServiceImp implements UserService{
    private final UserRepository repository;
    private final ModelMapper mapper;


    @Autowired
    public UserServiceImp(@Qualifier("getRepository") UserRepository repository, ModelMapper mapper  ) {
        this.repository = repository;
        this.mapper=mapper;
    }


    public List<UserResponseDto> findAll() {
        return UserResponseDto.of(repository.findAll());
    }




    //   value = (условие)? value_1 : value_2;
    @Override
    public List<UserResponseDto> getUsers(String name, String email) {

        Predicate<User> predicateByName = (name.equals(""))  ? u->true : u->u.getName().equalsIgnoreCase(name);
        Predicate<User> predicateByEmail = (email.equals("")) ? u->true: u->u.getEmail().equalsIgnoreCase(email);

        Predicate<User> allCondition =predicateByName.and(predicateByEmail);

        List<User> userList = repository.findAll()
                .stream()
                .filter(allCondition)
                .toList();

        return UserResponseDto.of(userList);
    }

    @Override
    public UserResponseDto createNewUser(UserRequestDto dto) {
        //User user = repository.save(UserRequestDto.toEntity(dto));
        User user = repository.save(mapper.map(dto, User.class));
        //return UserResponseDto.of(user);
        return mapper.map(user,UserResponseDto.class);
    }

    @Override
    public UserResponseDto findById(Long id) {
        return findAll()
                .stream()
                .filter(u->u.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User user = UserRequestDto.toEntity(dto);   // dto -> entity
        user.setId(id);
        return UserResponseDto.of(repository.save(user));
    }


    public List<UserResponseDto> findByName(String name) {
        return findAll()
                .stream()
                .filter(u->u.getName().equals(name))
                .toList();
    }
}