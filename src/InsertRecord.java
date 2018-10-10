

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InsertRecord {
	
	static String sql;

	
	public static void main(String[] args) {
		
		int rand =  (int) (Math.random()*1000);
		
		System.out.println(rand);
	}

}
