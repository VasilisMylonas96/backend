package eu.avaca.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class ConnectionManager {
    
    String jdbcUrl = "jdbc:postgresql://localhost:5432/DatabaseCustomer";
    String username = "postgres";
    String password = "mulonas1996";    

    private Connection connection ;
    private static ConnectionManager instance;
    public ConnectionManager()
    {

    }

    public Connection getConnection() throws SQLException
    {
        if (connection == null)
        {
            init();
        }

        return connection;
    }
    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }
    public void init() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(jdbcUrl, username, password);
    }

    public void closeConnection() throws SQLException
    {
        if (connection != null)
            connection.close();
    }



}
