package org.example.operations.processors;

import org.example.account.Account;
import org.example.account.AccountService;
import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import org.example.user.User;
import org.example.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAccountProcessor implements OperationCommandProcessor {
    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public CreateAccountProcessor(Scanner scanner, UserService userService, AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter user id for which to create an account: ");
        int userId = Integer.parseInt(scanner.nextLine());
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No such user with id= %s"
                        .formatted(userId)));
        Account account = accountService.createAccount(user);
        user.getAccountList().add(account);
        System.out.println("Account created with id: %s for user: %s"
                .formatted(account.getId(), user.getName()));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
