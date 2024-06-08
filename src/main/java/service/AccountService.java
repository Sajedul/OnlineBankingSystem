package service;

import dao.AccountDao;
import exception.InsufficientFundsException;
import model.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.sql.SQLException;

public class AccountService {
    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {

        this.accountDao = accountDao;
    }
    // Method to create a new account
    public void createAccount(Account account) throws SQLException {
        accountDao.createAccount(account);
    }
    // Method to get account balance
    public double getBalance(int accountId) throws AccountNotFoundException, SQLException {
        Account account = accountDao.getAccountById(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountId);
        }
        return account.getBalance();
    }
    // Method to transfer money between accounts
    public void transferMoney(int sourceAccountId, int destinationAccountId, double amount)
            throws SQLException, AccountNotFoundException, InsufficientFundsException {

        Account sourceAccount = accountDao.getAccountById(sourceAccountId);
        if (sourceAccount == null) {
            throw new AccountNotFoundException("Source account not found: " + sourceAccountId);
        }

        Account destinationAccount = accountDao.getAccountById(destinationAccountId);
        if (destinationAccount == null) {
            throw new AccountNotFoundException("Destination account not found: " + destinationAccountId);
        }

        if (sourceAccount.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds in account: " + sourceAccountId);
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        accountDao.updateAccount(sourceAccount);
        accountDao.updateAccount(destinationAccount);
    }
}
