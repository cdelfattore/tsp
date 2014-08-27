/*
Author: Chris Del Fattore
Email:	crdelf01@cardmail.louisville.edu
Description:
	First Verson can calculate up to 9 points quickly, 10 is too much.
	Brute force is too inefficent for 10 points
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
		System.out.println(listOfPermutations.size());
		System.out.println("Finding Lengths");
		Map<String, Double> pathLengths = clacAllPaths(pointsMap,listOfPermutations);
		String finalPath = findSmallestPath(pathLengths);
		System.out.println("The shortest path is: " + finalPath + " with a length of " + pathLengths.get(finalPath));
		//System.out.println("Complete");

	}

	//find the smallest path using a simple min number algorithm
	//embarrassing enought this is the most efficent algorithm at O(n) in the entire program
	public static String findSmallestPath(Map<String, Double> paths){
		String shortest = "";
		for(String s : paths.keySet()){
			//System.out.println("Path " + s + " has a length of " + paths.get(s));
			if(shortest == "") {
				shortest = s;
			}
			else if(paths.get(s) < paths.get(shortest)){
				shortest = s;
			}
		}
		return shortest;
	}

	//claculate all of the path lengths
	//uses brute force, starts to have trouble after a length of 9 points
	public static Map<String,Double> clacAllPaths(Map<Integer,Point> map, List<String> perm) {
		Map<String,Double> pathLengths = new HashMap<String,Double>();
		for(String s : perm) {
			String[] tmpa = s.split("");
			Double tempDis = 0.0;
			for(int i = 0;i<s.length();i++){
				if(i+1==s.length()){
					//System.out.println(Integer.parseInt(tmpa[i]) + "," + Integer.parseInt(tmpa[0]));
					//System.out.println(map.get(Integer.parseInt(tmpa[i])) + " " + map.get(Integer.parseInt(tmpa[0])));
					tempDis += computeDistance(map.get(Integer.parseInt(tmpa[i])), map.get(Integer.parseInt(tmpa[0])));
				} 
				else{
					//System.out.println(Integer.parseInt(tmpa[i]) + "," + Integer.parseInt(tmpa[i+1]));
					//System.out.println(map.get(Integer.parseInt(tmpa[i])) + " " + map.get(Integer.parseInt(tmpa[i+1])));
					tempDis += computeDistance(map.get(Integer.parseInt(tmpa[i])), map.get(Integer.parseInt(tmpa[i+1])));
				}
			}
			//System.out.println(s + " " + tempDis);
			pathLengths.put(s,tempDis);
		}
		return pathLengths;
	}

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

	//Simple Method to print the permutations.
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