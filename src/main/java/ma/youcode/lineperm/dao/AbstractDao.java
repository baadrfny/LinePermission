package ma.youcode.lineperm.dao;

import ma.youcode.lineperm.database.DBConnection;

import java.sql.Connection;

public abstract class AbstractDao<T> implements Dao<T> {

    protected Connection connection;

    protected AbstractDao() {
        connection = DBConnection.getInstance().getConnection();
    }
}