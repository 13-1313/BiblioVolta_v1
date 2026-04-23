package com.biblioteca.gestao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestaoApplication {

	public static void main(String[] args) {
		System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
		System.setProperty("https.protocols", "TLSv1.2");
		SpringApplication.run(GestaoApplication.class, args);
	}

}
