package es.upm.etsisi.fis.fisfleet;

import es.upm.etsisi.fis.controller.ControladorPartida;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class FisfleetApplication {

	public static void main(String[] args) {
		ControladorPartida.getInstance(new Scanner(""));

		SpringApplication.run(FisfleetApplication.class, args);
	}

}
