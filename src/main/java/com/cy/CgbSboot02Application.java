package com.cy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class CgbSboot02Application {
	static public void main(String[] args) {
		SpringApplication.run(CgbSboot02Application.class, args);
	}

}
