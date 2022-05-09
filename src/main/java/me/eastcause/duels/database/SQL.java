package me.eastcause.duels.database;

import java.sql.Connection;
import java.sql.ResultSet;

public interface SQL {

    boolean connect();
    void createTables();
    void update(String update);
    ResultSet updateWithResult(String update);
    void disconnect();
    void reconnect();
    boolean isConnected();
    ResultSet query(String query);
    void query(String query, Callback<ResultSet> resultSetCallback);
    Connection getConnection();


}
