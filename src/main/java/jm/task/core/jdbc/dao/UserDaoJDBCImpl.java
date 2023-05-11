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
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(255),lastname VARCHAR(255),age INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pS = Util.getConnection().prepareStatement("INSERT INTO Users (name,lastname,age) " +
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
        try (PreparedStatement pS = Util.getConnection().prepareStatement("DELETE FROM Users WHERE id = ?")) {
            pS.setLong(1,id);
            pS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<User> getAllUsers() {
        List <User> Users = new ArrayList<>();
        try (ResultSet resultSet = Util.getConnection().createStatement().executeQuery("SELECT*FROM Users")) {
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
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
