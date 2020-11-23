package xzy.com.Kering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({GlobalConfig.class})
public class KeringApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeringApplication.class, args);
    }

}
