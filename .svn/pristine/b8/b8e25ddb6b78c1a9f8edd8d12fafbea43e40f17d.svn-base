package com.openmaps.geometry.basetypes;

public class Location{
	public double  x;
	public double  y;
	
	public Location(){
		
	}
	public Location(double x,double y){
		this.x = x;
		this.y = y;
	}
	
	
	public void add(double deltaX,double deltaY){
		this.x += deltaX;
		this.y += deltaY;
	}
	
	
	public boolean equals(Location loc) {
		return (loc.x==this.x && loc.y==this.y);
	}
	
	@Override
	public Location clone() {
		Location loc = new Location();
		loc.x = this.x;
		loc.y = this.y;
		return loc;
	}
}
