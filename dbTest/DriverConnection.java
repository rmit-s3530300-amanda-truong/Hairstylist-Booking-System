package dbTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverConnection{

	public static void connect()
	{
		Connection conn = null;
		try{
			String url = "jdbc:sqlite:C:/Users/truon/Downloads/sqlite-jdbc-3.16.1";
			conn = DriverManager.getConnection(url);
			
			System.out.println("Connectection to sql established");
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException ex)
			{
				System.out.println(ex.getMessage());
			}
		}
		
	}
	
	public static void main(String[] args) {
		connect();
	}
}
