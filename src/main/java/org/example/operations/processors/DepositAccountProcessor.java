package org.example.operations.processors;

import org.example.account.AccountService;
import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DepositAccountProcessor implements OperationCommandProcessor {

    private final Scanner scanner;
    private final AccountService accountService;

    public DepositAccountProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter account id:  ");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter ammount to deposit: ");
        int ammountToDeposit = Integer.parseInt(scanner.nextLine());
        accountService.depositAccount(accountId, ammountToDeposit);
        System.out.println("Successfully deposit amount %s to id %s".formatted(ammountToDeposit, accountId));
        System.out.println();
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
