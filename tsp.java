/*
Author: Chris Del Fattore
Email:	crdelf01@cardmail.louisville.edu
Description:

*/
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class tsp {
	public static List<String> listOfPermutations; //List to store the permutations

	public static void main(String args[]) throws IOException{
		listOfPermutations = new ArrayList<String>();
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
		//create a map of the points to easily reference the values
		HashMap<Integer,Point> pointsMap = new HashMap<Integer,Point>();
		for (Point p : points) {
			//System.out.println("Name : " + p.getName() + " X : " + p.getX() + " Y: " + p.getY());
			pointsMap.put(p.getName(), p);
		}

		//Find the permutations of the set of points
		String initString = "";
		for(Point p : points){
			initString += p.name;
		}
		//System.out.println("Initial String " + initString);
		permutation("",initString);
		//System.out.println(listOfPermutations.size());
		//bruteForceSearch(listOfPermutations, points);
		//printPermutations();
		//System.out.println("Complete");

	}

	//find smallest path using brute force
	/*public static void bruteForceSearch(List<String> perms, List<Point> points) {
		HashMap<String, Double> pathLengths = new HashMap<String, Double>();
		for (String s : perms){
			double distance;
			String[] temp = s.split("");
			System.out.println(Arrays.toString(temp));
			for(int i = 0;i<temp.length;i++){
				//if(i < temp.length - 1){
				distance += computeDistance(points.get(i), points.get(i+1));	
				//}
				/*else {
					distance += computeDistance(points.get(i),points.get(0));
				}
			}
			distance += computeDistance(points.get(),points.get(0));
		}
		//return 
	}*/

	//Method to compute distance
	public static double computeDistance(Point a, Point b){
		return Math.sqrt( ((a.getX() - b.getX()) * (a.getX() - b.getX())) + ((a.getY() - b.getY()) * (a.getY() - b.getY()) ) );
	}

	//Method to generate the prefixs
	public static void permutation(String prefix, String str){
		int n = str.length();
        if (n == 0) {
        	listOfPermutations.add(prefix);
        	//System.out.println("Prefix added: " + prefix);
        }  
        else {
            for (int i = 0; i < n; i++) {
            	permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1));
            } 
        }
	}

	//Simple Method to pring the permutations.
	//Must run permutation method before printPermutations method
	public static void printPermutations(){
		for(String s : listOfPermutations) {
			System.out.println(s);	
		}
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