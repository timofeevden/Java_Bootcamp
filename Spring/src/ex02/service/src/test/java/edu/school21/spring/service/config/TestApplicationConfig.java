package edu.school21.spring.service.config;

import edu.school21.spring.service.services.UsersService;
import edu.school21.spring.service.services.UsersServiceImpl;
import edu.school21.spring.service.repositories.UsersRepository;
import edu.school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@ComponentScan("edu.school21.spring.service")
public class TestApplicationConfig {
    @Bean("testDataSource")
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .build();
    }

    @Bean("usersTestRepository")
    UsersRepository usersRepositoryJdbcTemplate(EmbeddedDatabase testDataSource) {
        return new UsersRepositoryJdbcTemplateImpl(testDataSource);
    }
}