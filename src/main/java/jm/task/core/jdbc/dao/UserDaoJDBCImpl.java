package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }
    @Override
    public void createUsersTable() {
        Connection connection = Util.getConnection();
        try (Statement statement =connection.createStatement()) {
            statement.executeUpdate("create table if not exists Users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT,name varchar(255),lastname varchar(255),age int)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop table if exists Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        try (PreparedStatement pS = connection.prepareStatement("insert into Users (name,lastname,age) " +
                "values (?,?,?)")) {
            pS.setString(1,name);
            pS.setString(2,lastName);
            pS.setByte(3,age);
            pS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        try (PreparedStatement pS = connection.prepareStatement(" delete from Users where id = ?")) {
            pS.setLong(1,id);
            pS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<User> getAllUsers() {
        List <User> Users = new ArrayList<>();
        Connection connection = Util.getConnection();
        try (ResultSet resultSet = connection.createStatement().executeQuery("select * from Users")) {
            while (resultSet.next()) {
                User dataUser = new User(resultSet.getString("name"),
                        resultSet.getString("lastname"),resultSet.getByte("age"));
                dataUser.setId(resultSet.getLong("id"));
                Users.add(dataUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  Users;
    }
    @Override
    public void cleanUsersTable() {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("truncate table Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
