package rest.example;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rnf.model.Location;

import rest.db.*;

@Path("res")
public class serviceDeployer {
	
	private DatabaseCrud dbCrud = new DatabaseCrud(); 

	@GET
	@Path("hello/{username}/{password}")
	@Produces(MediaType.TEXT_PLAIN)
	public String registerUser(@PathParam("username") String username , @PathParam("password")  String password)
	{
		String usernamepassword = username + " " + password;
		return usernamepassword;
	
	}
	
	@GET
	@Path("registerUser/{username}/{password}")
	@Produces(MediaType.TEXT_PLAIN)
	public String registerUserr(@PathParam("username") String username , @PathParam("password")  String password)
	{
		if(dbCrud.AddUser(username, password)){
			return "User "+ username + " added";
		}
		
		return "user not added";
	}
	
	@GET
	@Path("deleteUser/{username}/{password}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteUser(@PathParam("username") String username, @PathParam("password") String password)
	{
		if(dbCrud.DeleteUser(username)){
			return "User "+ username + " deleted";
		}
		return "User not deleted";
	}
	
	@GET
	@Path("loginUser/{username}/{password}")
	@Produces(MediaType.APPLICATION_XML)
	public boolean login(@PathParam("username") String username, @PathParam("password") String password){
		if(dbCrud.GetUser(username, password) != null){	
			return true;
		}
		return false;
	}
	
	@GET
	@Path("createSession/{username}")
	@Produces(MediaType.APPLICATION_XML)
	public boolean startSession(@PathParam("username") String username){
		if(dbCrud.AddSession(username)){
			return true;
		}
		return false;
	}
	
	@GET
	@Path("createStep&latitude/{latitude}/{longitude}/{email}")
	@Produces(MediaType.APPLICATION_XML)
	public boolean addStep(@PathParam("latitude") double latitude, @PathParam("longitude") double longitude, @PathParam("email") String email){
		if(dbCrud.AddLocation(latitude, longitude, email)){
			return true;
		}	
		return true;
	}
	
	@GET
	@Path("getSessions/{email}")
	@Produces(MediaType.APPLICATION_XML)
	public boolean getSessions(@PathParam("email") String email){
		if(dbCrud.GetSessions(email) != null){
			return true;
		}	
		return true;
	}
	
	@GET
	@Path("getSessionDistance/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public double getSessionDistance(@PathParam("id") int sessionId){
		
		ArrayList<Location> lcs = dbCrud.GetLocationsBySession(sessionId);
		
		double distance = 0;
		// Return all the locations
		if(lcs != null){
			
			// Calculate distance and -calories used
			if(lcs.size() > 1){
				for(int i = 0; i < lcs.size()-1; i++  ){
					
					double lat_a = lcs.get(i).getLatitude();
					double lat_b = lcs.get(i+1).getLatitude();
					double longi_a = lcs.get(i).getLongitude();
					double longi_b = lcs.get(i+1).getLongitude();
					distance += rest.db.DistanceCalculator.distance(lat_a, lat_b, longi_a, longi_b, "K");
					double speed = distance / lcs.size();
				}
			}
			
		}	
		return distance;
	}
		
		@GET
		@Path("getSessionCalories/{id}")
		@Produces(MediaType.APPLICATION_XML)
		public double getSessionCalories(@PathParam("id") int sessionId)
		{
			double distance = this.getSessionDistance(sessionId);
			int weight = 160; //Average weight of a human
			double kcal =  weight * 0.75 * distance; 
			return kcal;
		}	
}
