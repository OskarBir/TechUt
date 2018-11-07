package com.example.jdbcdemo.service;

import java.sql.*;

public class SockService {

	public static void main(String[] args) throws SQLException {

		String URL = "jdbc:hsqldb:hsql://localhost/workdb";
		Connection connection = DriverManager.getConnection(URL);
		Statement statement = connection.createStatement();
		String CREATE_TABLE_SQL = "CREATE TABLE Sock (id bigint, name varchar(30), brand varchar(50), size int)";

		ResultSet rs = connection.getMetaData().getTables(null,null,null,null);
		boolean tableExists = false;
		while(rs.next()){
			if ("Sock".equalsIgnoreCase(rs.getString("table_name"))){
				tableExists = true;
				break;
			}
		}
		if(!tableExists) {
			statement.executeUpdate(CREATE_TABLE_SQL);
		}
		String insert = "INSERT INTO Sock(id,name,brand,size) VALUES(1,'Ultra', 'Nike', 42)";
		statement.executeUpdate(insert);

		String select = "SELECT name, brand FROM Sock";
		rs = statement.executeQuery(select);

		while (rs.next()){
			System.out.println(rs.getString("name"));
		}


	}


}
