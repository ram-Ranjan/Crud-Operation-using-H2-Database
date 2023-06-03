package com.jsp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcWithH2db {

	public static void main(String[] args) {

		try {

			Class.forName("org.h2.Driver");
			Connection connection = DriverManager
					.getConnection("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:users.sql'");
			
			
			System.out.println("is connection  checkup succesfull  - "+connection.isValid(0)+"\n");

			// crud
			
			System.out.println("Predefined  table data from users.sql file");
			
			Statement statement =  connection.createStatement();
			ResultSet rs= statement.executeQuery("Select * from users");
			while(rs.next()) {
				System.out.println(" "+rs.getInt(1)+" - "+rs.getString(2));
			}
			
			
		
			//INSERT OPERATION
			
			System.out.println("************* Performing insertion ************");
			
			PreparedStatement ps1 = connection.prepareStatement("Insert into USERS (name) values(?),(?)");
			ps1.setString(1, "Ranjan");
			ps1.setString(2, "Rahul");
			int res =ps1.executeUpdate();
			
			
			//Insertion using Statement Interface and execute will give false for non-select queries
			
			Statement st = connection.createStatement();
			int u=st.executeUpdate("Insert into USERS (name) values ('Ram'),('Shyam')");
			
			System.out.println(u+" rows inserted by statement object ");
			System.out.println("%%%%%%");
			
			if(res>0) {
				System.out.println(res+" rows Inserted by prepared statement  ");
			}
			
			// SELECT OPERATION
			
			System.out.println("************** Select Operation ***********");

			PreparedStatement ps = connection.prepareStatement("select * from USERS ");
			
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getInt("id") + " - " + resultSet.getString("name"));
			}
			
			
			
			System.out.println("*************** Update Operation **********");
		//	Update operation
			int result =st.executeUpdate("Update users set id=10 where name='Ranjan'");
			
			System.out.println(result+" row updated and id of 'Ranjan' changed");
			
			System.out.println("********** After Update **************");
			
			
			PreparedStatement ptst = connection.prepareStatement("Select * from users");
			ResultSet newResultSet = ptst.executeQuery();

			while (newResultSet.next()) {
				System.out.println(newResultSet.getInt("id") + " - " + newResultSet.getString("name"));
			}
			
			
			//Delete Operation
			System.out.println("************ Performing delete operation ***********");
			
			statement.execute("delete from users where id=6");
			
			System.out.println("1 row deleted with name 'Shyam' ");

			System.out.println("************ Final retrival after deletion ***********");

			ResultSet rs1= statement.executeQuery("Select * from users");
			while(rs1.next()) {
				System.out.println(" "+rs1.getInt(1)+" - "+rs1.getString(2));
			}
			
			
			// Statement stmt= connection.createStatement();
//			String Query = "create table h2Jdbc (id integer ,name varchar(40), age integer)";
//			stmt.execute(Query);

			// stmt.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
