package me.eastcause.duels.database.impl;

import me.eastcause.duels.DuelPlugin;
import me.eastcause.duels.database.Callback;
import me.eastcause.duels.database.MysqlTimer;
import me.eastcause.duels.database.SQL;

import java.sql.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MySQL implements SQL {

    private String host, database, user, password;
    private int port;

    private Connection connection;
    private Executor executor;

    public MySQL(String host, int port, String database, String user, String password){
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.executor = Executors.newSingleThreadExecutor();
        connect();
    }

    @Override
    public boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?tcpKeepAlive=true", this.user, this.password);
            createTables();
            new MysqlTimer().runTaskTimer(DuelPlugin.getDuelPlugin(), 600L, 600L);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void createTables() {
        update("CREATE TABLE IF NOT EXISTS `duel_users` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` VARCHAR(40) NOT NULL , `name` VARCHAR(16) NOT NULL , `points` INT NOT NULL , `kills` INT NOT NULL , `deaths` INT NOT NULL , PRIMARY KEY (`id`), UNIQUE (`uuid`))");
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
