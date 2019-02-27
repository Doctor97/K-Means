import java.util.ArrayList;

public class Cluster {
	
	ArrayList<Point> points;
	Point centroid;
	String name;
	
	public Cluster(ArrayList<Point> points, Point centroid, String name){
		this.points = points;
		this.centroid = centroid;
		this.name = name;
	}
	
	
	public Cluster(ArrayList<Point> points, String name){
		this(points, null, name);
	}
	
	public void assign(Point point){
		points.add(point);
	}
	
	public void setCentroid(Point centroid){
		this.centroid = centroid;
	}
	
	public void clear(){
		points.clear();
	}
	
	public double calPurity(){
		double setosa = 0;
		double virginica = 0;
		double versicolor = 0;
		String mainIris = "";
		double mainIrisFreq = 0;
		for(int i = 0; i < points.size(); i++){
			if(points.get(i).name.equals("Iris-setosa")) setosa++;
			if(points.get(i).name.equals("Iris-virginica")) virginica++;
			if(points.get(i).name.equals("Iris-versicolor")) versicolor++;
		}
		if(setosa >= virginica && setosa >= versicolor) {
			mainIris = "Iris-setosa"; 
			mainIrisFreq = setosa;
		}
		if(virginica >= setosa && virginica >= versicolor){
			 mainIris = "Iris-virginica"; 
			 mainIrisFreq = virginica;
		}
		if(versicolor >= virginica && versicolor >= setosa){
			mainIris = "Iris-versicolor"; 
			mainIrisFreq = versicolor;
		}
		//System.out.println(mainIris + " :::: " + mainIrisFreq);
		double result = 100 * mainIrisFreq / points.size();
		//System.out.println(mainIrisFreq + " / " + points.size() + " = " + result);
		return result;
	}
	
	@Override
	public String toString(){
		return name + ": " + points.toString() + " Centroid: " + centroid + " Number of Irises: " + points.size();
	}

}
