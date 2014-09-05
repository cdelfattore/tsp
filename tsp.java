/* Assignment: Project 1 - Brute Force Traveling Salesman Problem
** Name: Chris Del Fattore 
** Description: Use brute force to find the minimum cost solution to a Traveling Salesperson Problem
**
*/
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class tsp {
	public static HashMap<String,Point> pointsMap; //A hashmap used to retrieve point information easier
	public static Double shortPathLen = 0.0; //public variable used to keep track of the shoretest path length
	public static String shortPath = ""; //public variable to keep track of the shortest path string
	
	public static void main(String args[]) throws IOException{
		
		//Takes the filename as a parameter. File contains points and the x and y cooridnates.
		String filename = args[0];

		//The point class is defined at the bottom of this file.
		//The point class is a basic class to store information about a point.
		//The Below list is used to store the point information from the input file
		List<Point> points = new ArrayList<Point>();

		//BufferedReader used to read input from a file
		BufferedReader reader = new BufferedReader(new FileReader(filename));

		//pattern is the regular expression used to parse throught the input file and find the point number and the point's x and y value.
		//The pattern will find all of the points in the file
		String pattern = "(?m)^\\d+\\s\\d+\\.\\d+\\s\\d+\\.\\d+";
		Pattern r = Pattern.compile(pattern);

		String value = null;

		//the below while loop with go through the file line by line and see if a match has been made with the regular expression.
		//If a match is made, the line is parsed, retrieving the piont name, x and y coordinate values
		//the points are saved in the points list.
		while((value = reader.readLine()) != null){
			Matcher m = r.matcher(value);
			if(m.find()) {
				//add the point to the List of points
				points.add( new Point(value.split(" ")[0], Double.parseDouble(value.split(" ")[1]), Double.parseDouble(value.split(" ")[2])) );

			}
		}

		//Convert the numbers to letters on the point objects
		//see line 98 comments for more info
		//this function is located on line 107
		convertNumstoLetters(points);

		//create a map of the points to easily reference the values
		pointsMap = new HashMap<String,Point>();
		for (Point p : points) {
			pointsMap.put(p.getName(), p);
		}

		//The initString is all of the points in one single string.
		//The initString is needed for the permuation method.
		String initString = "";
		for(Point p : points){
			initString += p.name;
		}
		System.out.println("Finding shortest path...");

		//Call the permutation method.
		//See comment line 135 for more info.
		//Method is location on 142.
		permutation("",initString);

		//Convert the shortest path back into numbers, then output the shortest path to the console.
		//We are done!
		System.out.println("The shortest path is: " + lettersToNums(shortPath) + " with a length of " + shortPathLen);
	}

	//Method to compute distance
	//Takes to points as parameters and computes the distance between them.
	//Uses distance formula
	public static double computeDistance(Point a, Point b){
		return Math.sqrt( ((a.getX() - b.getX()) * (a.getX() - b.getX())) + ((a.getY() - b.getY()) * (a.getY() - b.getY()) ) );
	}

	//This method is used to calculate the distance of a path.
	//The first parameter is the map of the points were the x values are retrieved.
	//The second is the String perm, or the path we want to calculate distance for.
	public static Double clacPathDistance(Map<String,Point> map, String perm) {
		String[] tmpa = perm.split("");
		//tempDis stores the total calculated distance
		Double tempDis = 0.0;
		for(int i = 0;i<tmpa.length;i++){
			//This if checks if the final point in the path is reached.
			if(i+1==tmpa.length){
				//Calculate the last point with the first point, to finsh the hamiltonian path.
				tempDis += computeDistance(map.get(tmpa[i]), map.get(tmpa[0]));
			} 
			else{
				//Calculate the distance from one point to the next.
				tempDis += computeDistance(map.get(tmpa[i]), map.get(tmpa[i+1]));
			}
		}
		//The total distance of the path is returned.
		return tempDis;
	}

	//I found that using numbers was causing problems when generating the paths with the
	//permutation method, i.e. 10 (Ten) was being seen as 1 and 0 (One and Zero)
	//This was messing up the path permutations.
	//My solution was to use letters instead of numbers.
	//Not a very scalable soultion but will work for now.
	//Will need to implement a more scalable solition in the future.
	//Which will also require changes to the permutation method.
	//Will Change to use a Array List.
	//initally convert the numbers to letters, a=1,b=2,c=3.....
	public static void convertNumstoLetters(List<Point> points){
		String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p"};
		for(int i = 0;i<points.size();i++){
			points.get(i).setName(letters[i]);
		}
	}

	//convert the letters back to numbers
	//Prints the final path
	public static String lettersToNums(String s) {
		String path = "";
		Map<String,Integer> mapLettoNums = new HashMap<String,Integer>();
		String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p"};
		for(int i = 0;i<s.length();i++){
			mapLettoNums.put(letters[i],i+1);
		}
		String[] sArray = s.split("");
		for(String let : sArray){
			path += mapLettoNums.get(let)+ "-";
		}
		return path.substring(0,path.length()-1);

	}

	//permutation is a recursize method that does the following:
	//1. Generate a Permutation
	//2. Calculate the total distance of all the paths
	//3. Checks to see if this newly generated path is the shortest
	//4. If it is, the path length and prefix is saved in global variables
	//5. Then repeat until all of the permutations have been checked.
	public static void permutation(String prefix, String str){
		int n = str.length();
        if (n == 0) {
        	String[] preArray = prefix.split("");
        	//Prevents creating already generated paths
        	//By only generating paths that start with a (which means 1)
    		if(preArray[0].contains("a")) {
        		Double len = clacPathDistance(pointsMap, prefix);
        		//if the path is the first path calculated then it must be the shortest
        		if(shortPathLen == 0.0){
        			shortPathLen = len;
        			shortPath = prefix;
        		}
        		//chekc if the newly calculated path is the shortest
        		else if(len < shortPathLen){
	        		shortPathLen = len;
	        		shortPath = prefix;
	        		//System.out.println("Prefix added: " + prefix);
        		}
        	}
       	}
        else {
            for (int i = 0; i < n; i++) {
            	permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1));
            } 
		}	    
    }
}

//Object used to represent a single point
//Point Stores the Name, X and Y Value
//with methods to retrieve the name, x and y value
//and a method to set the name.
class Point {
	String name;
	double x, y;
	//constructor
	Point(String name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	//method to retrieve a points name
	String getName(){
		return name;
	}
	//get X value
	double getX(){
		return x;
	}
	//get y value
	double getY(){
		return y;
	}
	//needed when converting a number to a letter and vise versa
	void setName(String a) {
		this.name = a;
	}

}