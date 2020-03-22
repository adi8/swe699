package edu.gmu.swe699;

import edu.gmu.swe699.dynamodb.config.DynamoDbConfig;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("edu.gmu.swe699")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class})
@EnableDynamoDBRepositories(basePackages = "edu.gmu.swe699.dynamodb.repo")
@EnableScheduling
@Configuration
@Import({DynamoDbConfig.class})
public class OrderServiceApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(OrderServiceApplication.class).profiles("rest").web(
            WebApplicationType.SERVLET).run(args);
    }
}
