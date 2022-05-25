package com.eardh.mapper;

import com.eardh.utils.Converter;
import com.eardh.utils.EarPool;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseMapper {

    protected final EarPool pool = EarPool.getInstance(5);

    List<?> query(Class<?> clazz, String sql, Object ... objects) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection connection = pool.get();
        List<Object> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            statement.setString(i + 1, objects[i].toString());
        }
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Object convert;
            if (clazz == String.class) {
                convert = resultSet.getString(1);
            } else {
                convert = Converter.convert(clazz, resultSet);
            }
            result.add(convert);
        }
        pool.free(connection);
        return result;
    }

    public boolean execute(String sql, Object ... objects) throws SQLException {
        Connection connection = pool.get();
        boolean succ = false;
        Savepoint savepoint = connection.setSavepoint();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < objects.length; i++) {
                statement.setString(i+1, objects[i] != null ? objects[i].toString() : null);
            }
            statement.execute();
            connection.commit();
            succ = true;
        } catch (SQLException throwables) {
            connection.rollback(savepoint);
        }
        pool.free(connection);
        return succ;
    }
}
