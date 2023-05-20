package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;

import ru.yandex.practicum.catsgram.model.User;

import java.util.*;
//Аннотация @Service дополнительно сообщает, что класс, который добавляется в контекст приложения,
// это именно класс с логикой, а не контроллер или что-то ещё. При этом Spring по отношению к классам
// с @Component и @Service ведёт себя одинаково.
@Service
public class UserService {

    private final Map<String, User> users = new HashMap<>();

    public Collection<User> findAll() {
        return users.values();
    }

    public User createUser(User user) {
        checkEmail(user);
        if (users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException(String.format(
                    "Пользователь с электронной почтой %s уже зарегистрирован.",
                    user.getEmail()
            ));
        }
        users.put(user.getEmail(), user);
        return user;
    }

    public User updateUser(User user) {
        checkEmail(user);
        users.put(user.getEmail(), user);

        return user;
    }

    public User findUserByEmail(String email) {
        if (email == null) {
            return null;
        }
        return users.get(email);
    }

    private void checkEmail(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }
    }
}