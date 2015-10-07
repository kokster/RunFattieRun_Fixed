package com.rnf.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Result {
	
	double kcal = 0;
	double distance = 0;
	double averageSpeed = 0;
	
	
	
	public Result(){
		
	}
	
	private double getMinutesDistance(int numberOfSteps){
		return Math.round( numberOfSteps / 6); 
	}
	
	public Result(double distance, int numberOfSteps){
		this.distance = distance;
		this.averageSpeed = getMinutesDistance(numberOfSteps);
		this.kcal = 0.75 * distance;
	}
	
	
	
}



