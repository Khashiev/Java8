package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        System.out.println("/////usersRepositoryJdbc/////");
        UsersRepository repository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        run(repository);

        System.out.println("/////usersRepositoryJdbcTemplate/////");
        repository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        run(repository);
    }

    private static void run(UsersRepository repository) {
        repository.findAll().forEach(System.out::println);
        System.out.println("-------------");

        User user = new User();
        user.setId(10L);
        user.setEmail("test@test.com");
        repository.save(user);
        System.out.println(repository.findById(user.getId()));
        System.out.println("-------------");

        user.setEmail("test2@test.com");
        repository.update(user);
        System.out.println(repository.findByEmail(user.getEmail()));
        System.out.println("-------------");

        repository.delete(user.getId());
        repository.findAll().forEach(System.out::println);
        System.out.println("-------------");
    }
}
