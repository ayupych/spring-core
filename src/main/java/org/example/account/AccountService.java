package org.example.account;

import org.example.user.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final Map<Integer, Account> accountMap;

    private int idCounter;

    private final AccountProperties accountProperties;


    public AccountService(AccountProperties accountProperties) {
        this.accountMap = new HashMap<>();
        this.idCounter = 0;
        this.accountProperties = accountProperties;
    }

    public Account createAccount(User user) {
        idCounter++;
        Account account = new Account(idCounter, user.getId(), accountProperties.getDefaultAccountAmount());
        accountMap.put(account.getId(), account);
        return account;
    }

    public Optional<Account> findAccountById(int id) {
        return Optional.ofNullable(accountMap.get(id));
    }

    public List<Account> getAllUserAccounts(int userId) {
        return accountMap.values().stream()
                .filter(account -> account.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public void depositAccount(int accountId, int moneyToDeposit) {
        var account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id= %s".formatted(accountId)));

        if (moneyToDeposit <= 0) {
            throw new IllegalArgumentException("Money to deposit must be positive, your deposit: %s"
                    .formatted(moneyToDeposit));
        }
        account.setMoneyAmount(account.getMoneyAmount() + moneyToDeposit);
        System.out.println("дошел до метода после сеттера");

    }

    public void withDrawFromAccount(int accountId, int ammountToWithDraw) {
        var account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id= %s".formatted(accountId)));

        if (ammountToWithDraw <= 0) {
            throw new IllegalArgumentException("Cannot withdraw not positive amount: amount=%s"
                    .formatted(ammountToWithDraw));
        }

        if (account.getMoneyAmount() < ammountToWithDraw) {
            throw new IllegalArgumentException("Cannot withdraw from account: id=%s, moneyAmmount=%s, attemptedWtihDraw = %s"
                    .formatted(accountId, account.getMoneyAmount(), ammountToWithDraw));
        }
        account.setMoneyAmount(account.getMoneyAmount() - ammountToWithDraw);
    }

    public Account closeAccount(int accountId) {
        var accountToRemove = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id= %s".formatted(accountId)));
        List<Account> accountList = getAllUserAccounts(accountToRemove.getUserId());
        if (accountList.size() == 1) {
            throw new IllegalArgumentException("Cannot close the only one account");
        }

        Account accountToDeposit =
                accountList.stream().filter(it -> it.getId() != accountId).findFirst().orElseThrow();
        accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + accountToRemove.getMoneyAmount());
        accountMap.remove(accountId);
        return accountToRemove;
    }


    public void transfer(int fromAccountId, int toAccountId, int amountToTransfer) {
        var accountFrom = findAccountById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such accountTo: id= %s".formatted(fromAccountId)));

        var accountTo = findAccountById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such accountTo: id= %s".formatted(toAccountId)));

        if (amountToTransfer <= 0) {
            throw new IllegalArgumentException("Cannot transfer not positive amount: amount=%s"
                    .formatted(amountToTransfer));
        }
        if (accountFrom.getMoneyAmount() < amountToTransfer) {
            throw new IllegalArgumentException("Cannot transfer from accountTo: id=%s, because balance Account less then transfer amount = %s"
                    .formatted(fromAccountId, toAccountId));
        }
        int totalAmountToDeposit = accountTo.getUserId() != accountFrom.getUserId()
                ? (int) (amountToTransfer * (1 - accountProperties.getTransferCommision()))
                : amountToTransfer;
        accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - amountToTransfer);
        accountTo.setMoneyAmount(accountTo.getMoneyAmount() + totalAmountToDeposit);
    }
}


