package rest.db;
import com.rnf.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import com.rnf.model.Session;
import com.rnf.model.Location;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.*;

public class DatabaseCrud {
	
	private MysqlDataSource ds;
	
	
	public DatabaseCrud(){
		// Initiate db connection
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("root");
		dataSource.setPassword("lorenzo31");
		dataSource.setServerName("localhost");
		dataSource.setDatabaseName("RFR");
		try{
			init(dataSource);
		} catch(SQLException e){
			System.out.println("Database error: "+ e.getErrorCode()+ ", "+ e.getMessage());
		}
	}
	
	
	private void init(MysqlDataSource dataSource) throws SQLException{
		ds = dataSource;
		//stmt.execute("INSERT INTO User(email,password) VALUES ('root@root.root', 'apassword')");
		//ResultSet rs = stmt.executeQuery("SELECT * FROM User");
		//stmt.close();
		
	}
	
	public User GetUser(String email, String password){
		try{
		java.sql.Statement stmt = this.openConnection();
		
		// Hash the password
		int nPassword = password.hashCode();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE email='"+ email +"' AND password='"+nPassword+"'");
		
		while(rs.next()){
			String username = rs.getString("email");
			
			return new User(username);
		}
		
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public boolean AddUser(String email, String password){
		try{
			java.sql.Statement stmt = this.openConnection();
			int nPassword = password.hashCode();
			boolean rs = stmt.execute("INSERT INTO User(email,password) VALUES('"+ email +"','"+ nPassword +"')");	
			return true;
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public boolean DeleteUser(String email){
		try{
			java.sql.Statement stmt = this.openConnection();
			boolean rs = stmt.execute("DELETE FROM User WHERE email='"+email+"'");	
			return true;
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
		return false;	
	}
	
	public boolean AddSession(String email){
		
		
		try{
			java.sql.Statement stmt = this.openConnection();
			
			
			boolean rs = stmt.execute("INSERT INTO Session(User_idUser,dateTime) VALUES( ("
					+ "SELECT idUser FROM User WHERE email="+email+") ,NOW())");
			
//			while(rs.next()){
//				String username = rs.getString("email");
//				TODO -> get the last id!
				return rs;
//			}
			} catch (SQLException e){
				System.out.println(e.getMessage());
		}
		
		
		return false;		
	}
	

	
	public ArrayList<Session> GetSessions(String email){
		
		try{
			java.sql.Statement stmt = this.openConnection();
			
			ArrayList<Session> sessions = new ArrayList<>();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Session WHERE User_idUser=(SELECT idUser FROM User WHERE email = '"+email+"')");
			
			while(rs.next()){
				
				
				int idSession= rs.getInt("idSession");
				String dateTime = rs.getString("dateTime");
				int userId = rs.getInt("user_idUser");
				
				// Add the session
				sessions.add(new Session(idSession, dateTime, userId));
			}
			return sessions;
			
			} catch (SQLException e){
				System.out.println(e.getMessage());
		}
		return null;
		
	}
	
	public boolean AddLocation(double latitude, double longitude, String email){
		// TODO -> Check that this works with the new select statement
		try{
			java.sql.Statement stmt = this.openConnection();
			boolean rs = stmt.execute("INSERT INTO Location(lat,long,Session_idSession) VALUES("+ latitude +","+ longitude +", (SELECT idSession FROM Session WHERE email="+email+" ORDER BY dateTime LIMIT 1))");	
			return true;
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	
	public ArrayList<Location> GetLocationsBySession(int sessionId){
		
		try{
			java.sql.Statement stmt = this.openConnection();
			
			ArrayList<Location> locations = new ArrayList<>();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Location WHERE Session_idSession= "+sessionId+";");
			
			while(rs.next()){
				
				int idSession= rs.getInt("idSession");
				int latitude = rs.getInt("latitude");
				int longitude = rs.getInt("longitude");
				// Add the session
				locations.add(new Location(idSession, latitude, longitude));
			}
			return locations;
			} catch (SQLException e){
				System.out.println(e.getMessage());
		}
		return null;
		
	}
	
	
	
	
	
	private java.sql.Statement openConnection(){
		java.sql.Connection conn;
		try {
			conn = ds.getConnection();
			java.sql.Statement stmt = conn.createStatement();
			stmt.executeQuery("USE RFR");
			return stmt;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}
