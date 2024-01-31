package com.research.chat.start;

import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tritone
 */
@SpringBootApplication(scanBasePackages = {"com.research.chat"})
public class Application {

    public static void main(String[] args) {

        ResourceInit.init();

        SpringApplication.run(Application.class, args);
    }

}
