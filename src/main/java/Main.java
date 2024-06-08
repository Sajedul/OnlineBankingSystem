import dao.AccountDao;
import dao.TransactionDao;
import dao.UserDao;
import exception.AccountNotFoundException;
import exception.InsufficientFundsException;
import jdk.jfr.Timestamp;
import model.Bank;
import model.Transaction;
import model.User;
import service.AccountService;
import service.TransactionService;
import service.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, InsufficientFundsException, AccountNotFoundException, ClassNotFoundException {
        String jdbcURL = "jdbc:mysql://localhost:3306/online_banking";
        String jdbcUsername = "root";
        String jdbcPassword = "mysql";
        UserDao userDao = new UserDao(jdbcURL, jdbcUsername, jdbcPassword);
        UserService userService = new UserService(userDao);
        // Example usage
        // Register a new user
        User newUser = new User();
        newUser.setName("John Doe");
        newUser.setEmail("john@example.com");
        newUser.setPassword("password");
        boolean isRegistered = userService.registerUser(newUser);
        if (isRegistered) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("User registration failed.");
        }
        AccountDao accountDao = new AccountDao(jdbcURL, jdbcUsername, jdbcPassword);
        TransactionDao transactionDao = new TransactionDao(jdbcURL, jdbcUsername, jdbcPassword);

        AccountService accountService = new AccountService(accountDao);
        TransactionService transactionService = new TransactionService(transactionDao);

        Bank bank = new Bank(accountService, transactionService);

        // Test operations
        bank.createAccount(2, 1000.00);
        bank.getAccountBalance(1);
        bank.transferMoney(1, 2, 20.00);
        bank.getTransactionHistory(1);
    }
}