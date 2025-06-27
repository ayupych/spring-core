package org.example.operations.processors;

import org.example.account.AccountService;
import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountTransferProcessor implements OperationCommandProcessor {

    private final Scanner scanner;
    private final AccountService accountService;

    public AccountTransferProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.println("Enter source account id: ");
        int fromAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter destination id: ");
        int toAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Ammount to transfer: ");
        int ammountToTransfer = Integer.parseInt(scanner.nextLine());
        accountService.transfer(fromAccountId, toAccountId, ammountToTransfer);
        System.out.println("Successfully transfer amount: %s, from account with id: %s, to account with id: %s."
                .formatted(ammountToTransfer, fromAccountId, toAccountId));





    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
