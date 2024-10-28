package com.nhnacademy.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class BasicConnectionPool  {
    private final String jdbcUrl;
    private final String username;
    private final String password;
    private final int maximumPoolSize;
    private final Queue<Connection> connections;
    private final String driverClassName;

    public BasicConnectionPool(String driverClassName, String jdbcUrl, String username, String password, int maximumPoolSize)  {
        this.driverClassName = driverClassName;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.maximumPoolSize = maximumPoolSize;
        connections = new LinkedList<>();

        checkDriver();
        initialize();
    }

    private void checkDriver(){
        //todo#1 driverClassName에 해당하는 class가 존재하는지 check합니다.
        //존재하지 않는다면 RuntimeException 예외처리.
        Class clazz = null;
        try
        {
            clazz=Class.forName(driverClassName);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("class not found");
        }
    }

    private void initialize()
    {
        //todo#2 maximumPoolSize만큼 Connection 객체를 생성해서 Connection Pool에 등록합니다.
        for(int i=0;i<this.maximumPoolSize;i++)
        {
            try
            {
                Connection connection = DriverManager.getConnection(jdbcUrl,username,password);
                connections.offer(connection);
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConnection() throws InterruptedException {
        synchronized (this) {
            while (connections.isEmpty()) {
                wait();
            }
            return connections.poll();
        }
    }

    public void releaseConnection(Connection connection)
    {
        //todo#4 작업을 완료한 Connection 객체를 Connection Pool에 반납 합니다.
        synchronized (this)
        {
            connections.offer(connection);
            notifyAll();
        }
    }

    public int getUsedConnectionSize(){
        //todo#5 현재 사용중인 Connection 객체 수를 반환합니다.
        synchronized (this)
        {
            return this.maximumPoolSize- this.connections.size();
        }
    }

    public void distory() throws SQLException {
        //todo#6 Connection Pool에 등록된 Connection을 close 합니다.
        for(Connection c : connections)
        {
            if(!c.isClosed())
            {
                c.close();
            }
        }
    }
}

