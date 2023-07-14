package internal.system.logdetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // logging처리 스케줄링!!
public class LogDetectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogDetectorApplication.class, args);
    }

}
