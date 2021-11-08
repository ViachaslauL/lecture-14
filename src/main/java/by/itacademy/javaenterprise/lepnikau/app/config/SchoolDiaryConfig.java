package by.itacademy.javaenterprise.lepnikau.app.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("by.itacademy.javaenterprise.lepnikau.app")
@PropertySource("classpath:db.properties")
public class SchoolDiaryConfig {

    @Bean
    public BasicDataSource basicDataSource(@Value("${DRIVER_CLASS}") String driver,
                                           @Value("${DB_URL}") String url,
                                           @Value("${DB_USER}") String user,
                                           @Value("${DB_PASSWORD}") String password,
                                           @Value("${INITIAL_POOL_SIZE}") int initPoolSize,
                                           @Value("${MAX_POOL_SIZE}") int maxPoolSize) {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initPoolSize);
        dataSource.setMaxTotal(maxPoolSize);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource)
    {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
