package service;

import dao.TransactionDao;
import model.Transaction;

import java.sql.SQLException;
import java.util.List;

public class TransactionService {
    private final TransactionDao transactionDao;

    public TransactionService(TransactionDao transactionDao) {

        this.transactionDao = transactionDao;
    }
    // Method to record a transaction
    public void recordTransaction(Transaction transaction) throws SQLException {
        transactionDao.createTransaction(transaction);
    }

    // Method to get transaction history for an account
    public List<Transaction> getTransactionHistory(int accountId) throws SQLException {
        return transactionDao.getTransactionsByAccountId(accountId);
    }
}
