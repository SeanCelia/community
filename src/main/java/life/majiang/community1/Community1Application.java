package life.majiang.community1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication

@MapperScan({"life.majiang.community1.mapper"})
@EnableCaching
public class Community1Application {

    public static void main(String[] args) {
        SpringApplication.run(Community1Application.class, args);
    }

}
