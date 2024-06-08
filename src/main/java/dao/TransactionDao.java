package dao;

import model.Transaction;

import java.sql.*;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public TransactionDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }
    protected Connection connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found", e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
        return jdbcConnection;
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }
    public void createTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, amount, type, timestamp) VALUES (?, ?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, transaction.getAccountId());
        statement.setDouble(2, transaction.getAmount());
        statement.setString(3, transaction.getType());
        statement.setTimestamp(4, transaction.getTimestamp());
        statement.executeUpdate();
        statement.close();
        disconnect();
    }
    public List<Transaction> getTransactionsByAccountId(int accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ?";

        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, accountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int accId = resultSet.getInt("account_id");
                    String type = resultSet.getString("type");
                    double amount = resultSet.getDouble("amount");
                    Timestamp timestamp = resultSet.getTimestamp("timestamp");

                    Transaction transaction = new Transaction(id, accId, type, amount, timestamp);
                    transactions.add(transaction);
                }
            }
        } finally {
            disconnect();
        }

        return transactions;
    }

}
