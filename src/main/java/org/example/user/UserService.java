package org.example.user;

import org.example.account.Account;
import org.example.account.AccountService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final Map<Integer, User> userMap;
    private int idCounter;
    private final Set<String> userLogins;
    private final AccountService accountService;

    public UserService(AccountService accountService) {
        this.userMap = new HashMap<>();
        this.idCounter = 0;
        this.userLogins = new HashSet<>();
        this.accountService = accountService;
    }

    public User createUser(String login) {
        if (userLogins.contains(login)) {
            throw new IllegalArgumentException("Login: %s already exists"
                    .formatted(login));
        }
        userLogins.add(login);
        idCounter++;
        User newUser = new User(idCounter, login, new ArrayList<>());

        Account newAccount = accountService.createAccount(newUser);
        newUser.getAccountList().add(newAccount);
        userMap.put(newUser.getId(), newUser);
        return newUser;
    }

    public Optional<User> findUserById(int id) {
        return Optional.ofNullable(userMap.get(id));
    }

    public List<User> getAllUsers() {
        return userMap.values().stream().toList();
    }
}
