package com.edveloso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoapiApplication {

	public static void main(String[] args) throws Exception {
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/livraria", "root", "123456");
		System.out.println("connected");
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery("select * from livro");
//		SpringApplication.run(TodoapiApplication.class, args);
		while(rs.next()) {
			System.out.print("id: " + rs.getInt("id"));
			System.out.print(" - titulo: " + rs.getString("titulo"));
			System.out.println(" - preco: " + rs.getDouble("preco"));
		}
		
		
	}

}
