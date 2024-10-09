package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("usersRepositoryJdbc")
public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;
    private Connection connection;

    @Autowired
    UsersRepositoryJdbcImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
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
                return new User(resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
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
                users.add(new User(resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password")));
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
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO userTable VALUES (?, ?, ?)"
            );
            ps.setLong(1, user.getId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE userTable SET email = ? WHERE id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setLong(2, user.getId());
            ps.executeUpdate();
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
        String sql = "SELECT * FROM userTable WHERE email = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet set = ps.executeQuery();

            if (set.next()) {
                return Optional.of(new User(set.getLong(1), set.getString(2), set.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
