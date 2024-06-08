package model;

public class Account {
    private int accountId;
    private int userId;
    private double balance;

    public Account(int userId, double balance) {
        //why we pass two element ? why we do not pass account Id??
        this.userId = userId;
        this.balance = balance;
    }

    public int getAccountId() {

        return accountId;
    }

    public void setAccountId(int accountId) {

        this.accountId = accountId;
    }

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {

        this.userId = userId;
    }

    public double getBalance() {

        return balance;
    }

    public void setBalance(double balance) {

        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", balance=" + balance +
                '}';
    }
}
