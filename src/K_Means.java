import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JOptionPane;

public class K_Means {
	
	private static File iris = new File("iris.txt");
	protected static ArrayList<Point> irises = new ArrayList<>();
	protected static int K;
	protected static Cluster[] clusters;
	protected static Point[] centroids ;
	
	public static void start(){
		try{
		String enterK = JOptionPane.showInputDialog(null, 
				"Please, enter k value");
		K = Integer.parseInt(enterK);
		}catch(Exception e){
			
		}
		clusters = new Cluster[K];
		centroids = new Point[K];
	}
	
	public static void extract() throws IOException{
		
		BufferedReader brIris = null;
	
		try {
			brIris = new BufferedReader(new FileReader(iris));
			
			String currentLine;
			
			while ((currentLine = brIris.readLine()) != null) {
				String[] iris = currentLine.split(",");
				Point irisPoint = createPoint(iris);
				irises.add(irisPoint);
			}
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
	}
	
	
	public static Point createPoint(String[] iris){
		ArrayList<Double> values = new ArrayList<>();
		String name = iris[4];
		for(int i = 0; i < 4; i++){
			try{
				double x = Double.parseDouble(iris[i]);
				values.add(x);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		Point point = new Point(values, name);
		return point;
	}
	
	public static void createClusters(){
		for(int i = 0; i < clusters.length; i++){
			clusters[i] = new Cluster(new ArrayList<Point>(), "Cluster-" + (i+1));
		}
	}
	
	public static void randomAssign(){
		Random rand = new Random();
		
		for(int i = 0; i < irises.size(); i++){
			int randCluster = rand.nextInt((K-1) - 0 + 1) + 0;
			clusters[randCluster].assign(irises.get(i));
		}
			
	}
	
	public static void calCentroid(Cluster cluster){
		double x = 0;
		double y = 0;
		double z = 0;
		double c = 0;
		
		for(int i = 0; i < cluster.points.size(); i++){
			 x = x + cluster.points.get(i).values.get(0); 
			 y = y + cluster.points.get(i).values.get(1); 
			 z = z + cluster.points.get(i).values.get(2);
			 c = c + cluster.points.get(i).values.get(3); 
		}
		
		x = x / cluster.points.size();
		y = y / cluster.points.size();
		z = z / cluster.points.size();
		c = c / cluster.points.size();
		
		ArrayList<Double> centroidValues = new ArrayList<>();
		
		centroidValues.add(x);
		centroidValues.add(y);
		centroidValues.add(z);
		centroidValues.add(c);
		
		Point centroid = new Point(centroidValues,"Centroid");
		
		cluster.setCentroid(centroid);
		//if(cluster.centroid.values == null) System.out.println("ALERT");
	}
	
	public static void setFirstCentroids(){
		for(int i = 0; i <clusters.length; i++){
			calCentroid(clusters[i]);
			centroids[i] = clusters[i].centroid;
		}
		System.out.println("START");
		for(int i = 0; i < clusters.length; i++){
			//if(clusters[i].points.size() == 0) System.out.println("ALERT");
			System.out.println(clusters[i]);
		}
	}
	
	public static void reassignPoints(){
		for(int i = 0; i < clusters.length; i++){
			clusters[i].clear();
		}
		for(int i = 0; i < irises.size(); i++){
			double minDistance = Double.MAX_VALUE;
			int clusterIndex = Integer.MAX_VALUE;
			String result = "";
			for(int j = 0; j < clusters.length; j++){
				double distance = calDistances(irises.get(i), clusters[j]);
				if(distance < minDistance){
					minDistance = distance;
					clusterIndex = j;
				}
			}
			clusters[clusterIndex].assign(irises.get(i));
		}
		for(int i = 0; i <clusters.length; i++){
			calCentroid(clusters[i]);
			if(clusters[i].points.size() == 0){
				for(int j = 0; j <clusters.length; j++){
					clusters[j].clear();
				}
				System.out.println("Random Generator Error. We restarted process");
				randomAssign();
				setFirstCentroids();
			}
			else centroids[i] = clusters[i].centroid;
		}
		
	}
	
	public static double calDistances(Point iris, Cluster cluster){
		double distance = 0;
		for(int i = 0; i < 4; i++){
			distance += Math.pow(cluster.centroid.values.get(i) - iris.values.get(i), 2);
		}
		distance = Math.sqrt(distance);
		//System.out.println(distance);
		return distance;
	}

	public static void main(String[] args) throws IOException {
		start();
		extract();
		createClusters();
		randomAssign();
		setFirstCentroids();
		
		int x = 0;
		while(true){
			Point[] localCentroids = new Point[K];
			for(int i = 0; i<clusters.length; i++){
				localCentroids[i] = centroids[i];
			}
			
			reassignPoints();
			
			int stop = 0;
			for(int i = 0; i<clusters.length; i++){
				boolean equal = Point.compare(clusters[i].centroid, localCentroids[i]);
				if(equal == true) stop++;
			}
			
			x++;
			System.out.println(x + ":");
			for(int i = 0; i < clusters.length; i++){
				System.out.println(clusters[i]);
			}
			if(stop == clusters.length) break;
			//if(x > 10) break;
		}
		
		System.out.println("");
		System.out.println("FINAL: ");
		for(int i = 0; i < clusters.length; i++){
			System.out.println(clusters[i]);
		}

		for(int i = 0; i < clusters.length; i++){
			double purity = clusters[i].calPurity();
			System.out.println("Purity of " + clusters[i].name + " = " + purity);
		}
		
		
	}

}
