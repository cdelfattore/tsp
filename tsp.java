/*
Author: Chris Del Fattore
Email:	crdelf01@cardmail.louisville.edu
Description:

*/
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class tsp {
	public static void main(String args[]) throws IOException{
		String filename = args[0];
		List<Point> points = new ArrayList<Point>();
		BufferedReader reader = new BufferedReader(new FileReader(filename));

		String pattern = "(?m)^\\d+\\s\\d+\\.\\d+\\s\\d+\\.\\d+";
		Pattern r = Pattern.compile(pattern);

		String value = null;

		while((value = reader.readLine()) != null){
			Matcher m = r.matcher(value);
			if(m.find()) {
				points.add( new Point(Integer.parseInt(value.split(" ")[0]), Double.parseDouble(value.split(" ")[1]), Double.parseDouble(value.split(" ")[2])) );
			}
		}
		for (Point p : points) {
			System.out.println("Name : " + p.getName() + " X : " + p.getX() + " Y: " + p.getY());
		}
		double x = computeDistance(points.get(0), points.get(1));
		System.out.println(x);
	}


	public static double computeDistance(Point a, Point b){
		return Math.sqrt( ((a.getX() - b.getX()) * (a.getX() - b.getX())) + ((a.getY() - b.getY()) * (a.getY() - b.getY()) ) );
	}

}

//Object used to represent a single point
class Point {
	int name, shortestPathPoint;
	double x, y;
	
	Point(int name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	int getName(){
		return name;
	}
	double getX(){
		return x;
	}
	double getY(){
		return y;
	}
	void setShortestPathPoint(int pointNumber) {
		this.shortestPathPoint = pointNumber;
	}
	int getShoShortestPathPoint(){
		return shortestPathPoint;
	}
}