package me.eastcause.duels.database.impl;

import me.eastcause.duels.database.Callback;
import me.eastcause.duels.database.SQL;

import java.sql.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SQLite implements SQL {

    private String database;

    private Connection connection;
    private Executor executor;

    public SQLite(String database){
        this.database = database;
        this.executor = Executors.newSingleThreadExecutor();
        connect();
    }

    @Override
    public boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:"+database+".db");
            createTables();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void createTables() {
        update("CREATE TABLE IF NOT EXISTS `duel_users` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , `uuid` VARCHAR(40) NOT NULL , `name` VARCHAR(16) NOT NULL , `points` INT NOT NULL , `kills` INT NOT NULL , `deaths` INT NOT NULL, UNIQUE (`uuid`))");
    }

    @Override
    public void update(String update) {
        if(!isConnected()){
            reconnect();
            update(update);
            return;
        }
        Runnable runnable = () -> {
            try {
                getConnection().createStatement().executeUpdate(update);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        };
        this.executor.execute(runnable);
    }

    @Override
    public ResultSet updateWithResult(String update) {
        if(!isConnected()){
            reconnect();
            return updateWithResult(update);
        }
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(update, 1);
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                return resultSet;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void disconnect() {
        if(getConnection() != null){
            try {
                getConnection().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void reconnect() {
        connect();
    }

    @Override
    public boolean isConnected() {
        try {
            return (getConnection() != null || !getConnection().isClosed());
        }catch (SQLException throwables){
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public ResultSet query(String query) {
        if(!isConnected()){
            reconnect();
            return query(query);
        }
        try {
            return getConnection().createStatement().executeQuery(query);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void query(String query, Callback<ResultSet> resultSetCallback) {
        if(!isConnected()){
            reconnect();
            query(query, resultSetCallback);
            return;
        }
        new Thread(() -> {
            try {
                ResultSet resultSet = getConnection().createStatement().executeQuery(query);
                resultSetCallback.done(resultSet);
            } catch (SQLException throwables) {
                resultSetCallback.error(throwables);
                throwables.printStackTrace();
            }
        });
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
