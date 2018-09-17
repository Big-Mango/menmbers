package com.haffee.menmbers;

import com.haffee.menmbers.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MenmbersApplication {

    public static void main(String[] args) {
//        SpringApplication.run(MenmbersApplication.class, args);
        ApplicationContext app = SpringApplication.run(MenmbersApplication.class, args);
        SpringUtil.setApplicationContext(app);
    }
}
