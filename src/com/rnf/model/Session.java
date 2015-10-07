package com.rnf.model;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Session {
	private int sessionID; 
	private String timestamp;
	private int userId;

	public Session(){
		
	}
	

	public Session(int sessionID, String time, int userId){
		this.sessionID= sessionID;
		this.timestamp = time;
		this.userId = userId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getSessionID() {
		return sessionID;
	}
}
