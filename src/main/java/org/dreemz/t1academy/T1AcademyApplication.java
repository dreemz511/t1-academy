package org.dreemz.t1academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class T1AcademyApplication {

    public static void main(String[] args) {
        SpringApplication.run(T1AcademyApplication.class, args);
    }

}
