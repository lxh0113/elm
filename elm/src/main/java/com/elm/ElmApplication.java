package com.elm;

import com.elm.controller.WebSocket;
import javassist.tools.web.Webserver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
@EnableWebSocket
public class ElmApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(ElmApplication.class);
        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);

//        SpringApplication.run(ElmApplication.class, args);
        WebSocket.setApplicationContext(configurableApplicationContext);
    }

}
