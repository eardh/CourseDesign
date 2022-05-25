package com.eardh.utils;

import java.sql.*;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class EarPool {

    private final static String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    private final static String DB_Url = "jdbc:mysql://localhost:3306/mobile_chat?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final static String username = "root";
    private final static String password = "123456";

    private int pool_size;
    private Connection[] connections;
    private AtomicIntegerArray states;
    private volatile static EarPool POOL;

    private EarPool() {
    }

    private EarPool(int pool_size) {
        this.pool_size = pool_size;
        connections = new Connection[pool_size];
        states = new AtomicIntegerArray(new int[pool_size]);
        for (int i = 0; i < pool_size; i++) {
            connections[i] = getConnection();
        }
    }

    public static EarPool getInstance(int pool_size) {
        if (POOL == null) {
            synchronized (EarPool.class) {
                if (POOL == null) {
                    POOL = new EarPool(pool_size);
                }
            }
        }
        return POOL;
    }

    public Connection get() {
        while(true) {
            for (int i = 0; i < pool_size; i++) {
                if(states.get(i) == 0) {
                    if (states.compareAndSet(i, 0, 1)) {
                        return connections[i];
                    }
                }
            }
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void free(Connection conn) {
        for (int i = 0; i < pool_size; i++) {
            if (connections[i] == conn) {
                states.set(i, 0);
                synchronized (this) {
                    this.notifyAll();
                }
                break;
            }
        }
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(JDBC_Driver);
            connection = DriverManager.getConnection(DB_Url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
