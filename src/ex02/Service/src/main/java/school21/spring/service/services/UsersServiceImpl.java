package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.UUID;

@Component("usersService")
public class UsersServiceImpl implements UsersService {
    private final UsersRepository repository;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplate") UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public String signUp(String email) {
        String password = UUID.randomUUID().toString();
        Long id = (long) repository.findAll().size() + 1;
        repository.save(new User(id, email, password));

        return password;
    }
}
