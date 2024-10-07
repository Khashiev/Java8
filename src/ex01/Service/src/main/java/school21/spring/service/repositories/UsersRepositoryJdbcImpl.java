package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;
    private Connection connection;

    UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        initializeConnection();
    }

    private void initializeConnection() {
        try {
            connection = dataSource.getConnection();
            if (connection == null) {
                throw new SQLException("Could not get connection");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(Long id) {
        try {
            ResultSet resultSet = connection.createStatement()
                    .executeQuery("SELECT * FROM userTable WHERE id = " + id);

            if (resultSet.next()) {
                return new User(resultSet.getLong("id"), resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try {
            ResultSet resultSet = connection.createStatement()
                    .executeQuery("SELECT * FROM userTable");

            while (resultSet.next()) {
                users.add(new User(resultSet.getLong("id"), resultSet.getString("email")));
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void save(User user) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO userTable (email) VALUES (?)");
            ps.setString(1, user.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try {
            connection.createStatement()
                    .executeUpdate("UPDATE userTable SET email = "
                            + user.getEmail() + " WHERE id = " + user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            connection.createStatement()
                    .executeUpdate("DELETE FROM userTable WHERE id = " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
             ResultSet resultSet = connection.createStatement()
                    .executeQuery("SELECT * FROM userTable WHERE email = '" + email + "'");

            if (resultSet.next()) {
                return Optional.of(new User(resultSet.getLong("id"),
                        resultSet.getString("email")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }
}
