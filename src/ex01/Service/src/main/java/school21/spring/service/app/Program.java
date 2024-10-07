package school21.spring.service.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.repositories.UsersRepository;

public class Program {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository repository = context.getBean("usersRepositoryJdbc", UsersRepository.class);

        repository.findAll().forEach(System.out::println);
        System.out.println("-----------------");

        repository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        repository.findAll().forEach(System.out::println);
    }
}
