package model;

import exception.InsufficientFundsException;
import service.AccountService;
import service.TransactionService;

//import javax.security.auth.login.AccountNotFoundException;
import exception.AccountNotFoundException;
import java.sql.SQLException;
import java.util.List;

public class Bank {
    private AccountService accountService;
    private TransactionService transactionService;

    public Bank(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }
    // Method to create a new account
    public void createAccount(int userId, double initialBalance) {
        Account account = new Account(userId, initialBalance);
        try {
            accountService.createAccount(account);
            System.out.println("Account created successfully for user ID: " + userId);
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
        }
    }
    // Method to get account balance
    public void getAccountBalance(int accountId) throws SQLException {
        try {
            double balance = accountService.getBalance(accountId);
            System.out.println("Account balance for account ID " + accountId + " is: " + balance);
        } catch (javax.security.auth.login.AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    // Method to transfer money between accounts
    public void transferMoney(int fromAccountId, int toAccountId, double amount) throws InsufficientFundsException, SQLException {
        try {
            accountService.transferMoney(fromAccountId, toAccountId, amount);
            System.out.println("Money transferred successfully from account " + fromAccountId + " to account " + toAccountId);
        } catch (javax.security.auth.login.AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    // Method to get transaction history for an account
    public List<Transaction> getTransactionHistory(int accountId) throws SQLException {
        try {
            List<Transaction> transactions = transactionService.getTransactionHistory(accountId);
            //System.out.println("Transaction history for account ID " + accountId + ":");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching transaction history: " + e.getMessage());
        }
        return transactionService.getTransactionHistory(accountId);
    }


   /*

    public List<Transaction> getTransactionHistory(int accountId) throws SQLException {
        return transactionService.getTransactionHistory(accountId);
    }

    */

}
