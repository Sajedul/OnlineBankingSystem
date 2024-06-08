package dao;

import model.Account;

import java.sql.*;

public class AccountDao {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public AccountDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }
    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

   /* public Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    */
    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }
    public void createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (user_id, balance) VALUES (?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, account.getUserId());
        statement.setDouble(2, account.getBalance());

        statement.executeUpdate();
        statement.close();
        disconnect();
    }
    public Account getAccountById(int accountId) throws SQLException {
        Account account = null;
        String sql = "SELECT * FROM accounts WHERE account_id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, accountId);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int userId = resultSet.getInt("user_id");
            double balance = resultSet.getDouble("balance");

            account = new Account( userId, balance);
        }

        resultSet.close();
        statement.close();
        disconnect();

        return account;
    }
    public void updateAccount(Account account) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setDouble(1, account.getBalance());
        statement.setInt(2, account.getAccountId());

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

}
