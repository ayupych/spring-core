package org.example.account;

public class Account {
    private final int id;
    private final int userId;
    private int moneyAmount;

    public Account(int id, int userId, int moneyMount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyMount;

    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", moneyMount=" + moneyAmount +
                '}';
    }
}
