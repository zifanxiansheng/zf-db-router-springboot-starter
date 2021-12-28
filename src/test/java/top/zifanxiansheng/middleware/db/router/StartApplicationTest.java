package top.zifanxiansheng.middleware.db.router;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;

/**
 * @Author 梓樊先生
 * @Date 2021/12/28 17:28
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class StartApplicationTest implements CommandLineRunner {


    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(StartApplicationTest.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

    }
}
