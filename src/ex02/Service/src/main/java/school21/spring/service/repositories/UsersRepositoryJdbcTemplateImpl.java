package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.*;


@Component("usersRepositoryJdbcTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(@Qualifier("driverManagerDataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM userTable WHERE id = :id";
        Map<String, Long> namedParameters = Collections.singletonMap("id", id);

        try {
            return this.jdbcTemplate
                    .queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return this.jdbcTemplate.query("SELECT * FROM userTable",
                new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO userTable VALUES (:id, :email, :password);";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);
        this.jdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE userTable SET email = :email WHERE id = :id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);
        this.jdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM userTable WHERE id = :id";
        Map<String, Long> namedParameters = Collections.singletonMap("id", id);
        this.jdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM userTable WHERE email = :email";
        Map<String, String> namedParameters = Collections.singletonMap("email", email);
        return Optional.ofNullable(this.jdbcTemplate
                .queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(User.class)));
    }
}
