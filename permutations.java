import java.io.*;
import java.util.*;
import java.util.regex.*;
public class permutations {
	public static List<String> listOfPermutations; //List to store the permutations
	public static PrintWriter out;
	public static HashMap<String,Point> pointsMap;
	public static Double shortPathLen = 0.0;
	public static String shortPath = "";
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
				points.add( new Point(value.split(" ")[0], Double.parseDouble(value.split(" ")[1]), Double.parseDouble(value.split(" ")[2])) );

			}
		}
		/*for(Point p : points){
			System.out.println(p.getName());
		}*/
		List<Point> newPoints = convertNumstoLetters(points);
		/*for(Point p : newPoints){
			System.out.println(p.getName() + " " + p.getX() + " " + p.getY());
		}*/
		//create a map of the points to easily reference the values
		 pointsMap = new HashMap<String,Point>();
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
		/*System.out.println("Finding Paths");
		out = new PrintWriter(new FileWriter("perm.txt")); */
		permutation("",initString);
		//out.close();
		//printPermutations();
		//clacAllPaths(pointsMap, "abcd");
		/*System.out.println("Calculating paths...");

		Map<String, Double> pathLengths = clacAllPaths(pointsMap,listOfPermutations);
		System.out.println("Finding shortest path");
		String finalPath = findSmallestPath(pathLengths);
		System.out.println("The shortest path is: " + lettersToNums(finalPath) + " with a length of " + pathLengths.get(finalPath));*/
		System.out.println("The shortest path is: " + shortPath + " with a length of " + shortPathLen);


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
	/*public static Map<String,Double> clacAllPaths(Map<String,Point> map, List<String> perm) {
		Map<String,Double> pathLengths = new HashMap<String,Double>();
		for(String s : perm) {
			String[] tmpa = s.split("");
			Double tempDis = 0.0;
			for(int i = 0;i<s.length();i++){
				if(i+1==s.length()){
					//System.out.println(Integer.parseInt(tmpa[i]) + "," + Integer.parseInt(tmpa[0]));
					//System.out.println(map.get(Integer.parseInt(tmpa[i])) + " " + map.get(Integer.parseInt(tmpa[0])));
					tempDis += computeDistance(map.get(tmpa[i]), map.get(tmpa[0]));
				} 
				else{
					//System.out.println(Integer.parseInt(tmpa[i]) + "," + Integer.parseInt(tmpa[i+1]));
					//System.out.println(map.get(Integer.parseInt(tmpa[i])) + " " + map.get(Integer.parseInt(tmpa[i+1])));
					tempDis += computeDistance(map.get(tmpa[i]), map.get(tmpa[i+1]));
				}
			}
			//System.out.println(s + " " + tempDis);
			pathLengths.put(s,tempDis);
		}
		return pathLengths;
	}*/

	public static Double clacAllPaths(Map<String,Point> map, String perm) {
		String[] tmpa = perm.split("");
		Double tempDis = 0.0;
		for(int i = 0;i<tmpa.length;i++){
			if(i+1==tmpa.length){
				//System.out.println(Integer.parseInt(tmpa[i]) + "," + Integer.parseInt(tmpa[0]));
				//System.out.println(map.get(Integer.parseInt(tmpa[i])) + " " + map.get(Integer.parseInt(tmpa[0])));
				tempDis += computeDistance(map.get(tmpa[i]), map.get(tmpa[0]));
			} 
			else{
				//System.out.println(Integer.parseInt(tmpa[i]) + "," + Integer.parseInt(tmpa[i+1]));
				//System.out.println(map.get(Integer.parseInt(tmpa[i])) + " " + map.get(Integer.parseInt(tmpa[i+1])));
				tempDis += computeDistance(map.get(tmpa[i]), map.get(tmpa[i+1]));
			}
		}
		//System.out.println(perm + " " + tempDis);
		//pathLengths.put(s,tempDis);
		return tempDis;
	}

	//Method to compute distance
	public static double computeDistance(Point a, Point b){
		return Math.sqrt( ((a.getX() - b.getX()) * (a.getX() - b.getX())) + ((a.getY() - b.getY()) * (a.getY() - b.getY()) ) );
	}
	public static List<Point> convertNumstoLetters(List<Point> points){
		String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l"};
		for(int i = 0;i<points.size();i++){
			points.get(i).setName(letters[i]);
		}
		return points;
	}
	public static String lettersToNums(String s) {
		String path = "";
		Map<String,Integer> mapLettoNums = new HashMap<String,Integer>();
		String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l"};
		for(int i = 0;i<s.length();i++){
			mapLettoNums.put(letters[i],i+1);
		}
		String[] sArray = s.split("");
		for(String let : sArray){
			path += mapLettoNums.get(let)+ "-";
		}
		return path.substring(0,path.length()-1);

	}
	public static void printPermutations(){
		for(String s : listOfPermutations) {
			System.out.println(s);	
		}
	}
	public static void permutation(String prefix, String str){
		int n = str.length();
	        if (n == 0) {
	        	String[] preArray = prefix.split("");
        		if(preArray[0].contains("a")) {
	        		Double len = clacAllPaths(pointsMap, prefix);
	        		if(shortPathLen == 0.0){
	        			shortPathLen = len;
	        			shortPath = prefix;
	        		}
	        		else if(len < shortPathLen){
	        		//listOfPermutations.add(prefix);
	        		shortPathLen = len;
	        		shortPath = prefix;
	        		//out.println(prefix + "," + len);
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
class Point {
	String name;
	double x, y;
	
	Point(String name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	String getName(){
		return name;
	}
	double getX(){
		return x;
	}
	double getY(){
		return y;
	}
	void setName(String a) {
		this.name = a;
	}

}