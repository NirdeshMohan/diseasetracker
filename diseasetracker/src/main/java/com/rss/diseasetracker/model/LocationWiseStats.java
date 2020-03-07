package com.rss.diseasetracker.model;

public class LocationWiseStats {
	private int currentTotalCases;
	private String state;
	private String country;
	private int differnceSinceYesterday;
	
	public int getDiffernceSinceYesterday() {
		return differnceSinceYesterday;
	}
	public void setDiffernceSinceYesterday(int differnceSinceYesterday) {
		this.differnceSinceYesterday = differnceSinceYesterday;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getCurrentTotalCases() {
		return currentTotalCases;
	}
	public void setCurrentTotalCases(int currentTotalCases) {
		this.currentTotalCases = currentTotalCases;
	}
	
	@Override
	public String toString() {
		return "LocationWiseStats [currentTotalCases=" + currentTotalCases + ", state=" + state + ", country=" + country
				+ "]";
	}
}
