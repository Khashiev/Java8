package school21.spring.service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("school21.spring.service")
public class TestApplicationConfig {
    @Bean
    public DataSource driverManagerDataSource() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .build();

        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.execute(
                "CREATE TABLE userTable(" +
                        "   id BIGINT, " +
                        "   email varchar(255), " +
                        "   password varchar(255)" +
                        ");"
        );

        return dataSource;
    }
}