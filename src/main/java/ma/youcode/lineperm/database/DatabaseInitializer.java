package ma.youcode.lineperm.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {

        String usersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL
                )
                """;

        String fichiersTable = """
                CREATE TABLE IF NOT EXISTS fichiers (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nom TEXT NOT NULL,
                    proprietaire_id INTEGER NOT NULL,
                    permissions TEXT NOT NULL,
                    FOREIGN KEY (proprietaire_id) REFERENCES users(id)
                )
                """;

        String logsTable = """
                CREATE TABLE IF NOT EXISTS logs (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    fichier_id INTEGER NOT NULL,
                    action TEXT NOT NULL,
                    resultat TEXT NOT NULL,
                    date_action DATETIME DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES users(id),
                    FOREIGN KEY (fichier_id) REFERENCES fichiers(id)
                )
                """;

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();

            statement.executeUpdate(usersTable);
            statement.executeUpdate(fichiersTable);
            statement.executeUpdate(logsTable);

            System.out.println("Tables created successfully !");

            statement.close();

        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
}