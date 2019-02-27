import java.util.ArrayList;

public class Point {
	
	protected ArrayList<Double> values;
	String name;
	
	public Point(ArrayList<Double> values, String name){
		this.values = values;
		this.name = name;
	}
	
	public static boolean compare(Point p1, Point p2){
		//boolean equal;
		int equal = 0;
		if(p1 != null && p2 != null){
			if(p1.values.size() == p2.values.size()){
				for(int i = 0; i < p1.values.size(); i++){
					double p1Val = p1.values.get(i);
					double p2Val = p2.values.get(i);
					if(p1Val == p2Val) equal++;
					//else System.out.println("P1: " + p1.values.get(i) +  " P2: " + p2.values.get(i));
				}
				if(equal == p1.values.size()) return true;
				else return false;
			}
			else return false;
		}
		else return false;
		
	}

	
	@Override
	public String toString(){
		String result = "";
		for(int i = 0; i < 4; i++){
			if(i == 0) result = result + " " + values.get(i);
			else result = result + "," + values.get(i);
		}
		result = result + "," + name;
		return result;
	}
	
}
