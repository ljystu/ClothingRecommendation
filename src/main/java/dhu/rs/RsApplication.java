package dhu.rs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("dhu.rs.dao")
@SpringBootApplication
@EnableScheduling
public class RsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsApplication.class, args);
    }

}
