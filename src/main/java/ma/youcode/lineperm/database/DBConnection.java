package ma.youcode.lineperm.database;
import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection{



    private static final String URL = "jdbc:sqlite:audit.db";


    private static DBConnection instance;


    private Connection connection;

    private DBConnection(){
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("Connection successfuly");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static DBConnection getInstance(){
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }


    
}

