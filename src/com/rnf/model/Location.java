package com.rnf.model;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Location {
	private int sessionId;
	private double latitude;
	private double longitude;
	
	public Location(){
		
	}
	
	public Location(int idSession, double latitude, double longitude) {
		this.sessionId = idSession;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
