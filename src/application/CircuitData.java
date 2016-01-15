package application;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public  class CircuitData {
	public static double defaultVoltage = 36;
	public static double defaultResistance = 3;
	public static int i = 0;
	//ArrayList circuit stores the conponents of the circuit,assuming one circuit
	public static ArrayList<DraggableNode> circuit = new ArrayList<DraggableNode>();  
	//list store every pair of connected conponent in loop
	public static List <Pair<DraggableNode, DraggableNode> > connectedConponent = new ArrayList <Pair<DraggableNode, DraggableNode> > ();
	public static ArrayList<Integer> Resistance = new ArrayList<Integer>();  
	
	public static int indexStart;
	public static int indexEnd;
	public static void printCircuit(){
		System.out.print("--------");
		for(int i=0;i<circuit.size();i++)
		  {
		   System.out.print(circuit.get(i).getType()+"+");
		  }
		System.out.println("--------");
	}
	public static boolean formCircuit(){
		for (DraggableNode draggableNode : circuit) {
			if((findKeyIndex(draggableNode)!=-1)&&(findValueIndex(draggableNode)!=-1))
			{}
			else return false;
		}
	
	System.out.println("*********a circuit is formed*********");
	printCircuit();
	System.out.println("*********************************");
	if(numberOfAmmeter()>0){
		AmmeterWork();
	}
		
	return true;	
	}
	public static void addPair(DraggableNode a,DraggableNode b){
		connectedConponent.add(new Pair(a,b));
		formCircuit();
	}
	public static void removeCon(){
		
	}
	public static int findKeyIndex(DraggableNode a){
		for (Pair<DraggableNode, DraggableNode> pair : connectedConponent) {
			if(pair.getKey().equals(a))
				return connectedConponent.indexOf(new Pair(pair.getKey(),pair.getValue()));;
		}
		return -1;
	}
	public static int findValueIndex(DraggableNode a){
		for (Pair<DraggableNode, DraggableNode> pair : connectedConponent) {
			if(pair.getValue().equals(a))
				return connectedConponent.indexOf(new Pair(pair.getKey(),pair.getValue()));
		}
		return -1;
	}
	public static int numberOfResistance(){
		int number = 0;
		for (DraggableNode draggableNode : circuit) {
			if(draggableNode.getType().equals(DragIconType.resistor))
				number ++;
		
		}
		return number;
	}
	
	public static int numberOfAmmeter(){
		int number = 0;
		for (DraggableNode draggableNode : circuit) {
			if(draggableNode.getType().equals(DragIconType.wire))
				number ++;
		
		}
		return number;
	}
	static double v;
	static double a;
	public static void AmmeterWork(){
		a = CircuitData.defaultVoltage/getTotalResistance();
		System.out.println("current is "+CircuitData.defaultVoltage/getTotalResistance());
	}
	public static boolean isVoltmeter(DraggableNode a){
		return a.getType().equals(DragIconType.button_switch);
	}
	
	public static boolean isPoint(DraggableNode a){
		return a.getType().equals(DragIconType.leftpoint);
	}
	
	public static double getTotalResistance(){
		double sum = 0 ;
		for (DraggableNode draggableNode : circuit) {
			sum = sum + draggableNode.resistance;
		
		}
		return sum;
	}
	public static double getResistanceInVoltmeter(){
		double sum = 0 ;
		for (int i = 0; i < circuit.size(); i++) {
			if(i>getStartpoint())
				if(i<getEndpoint())
					sum = sum + circuit.get(i).resistance;
		}
		return sum;
	}
	public static int getStartpoint(){
		for (DraggableNode draggableNode : circuit) {
			if(draggableNode.getType().equals(DragIconType.leftpoint)){
				System.out.println("startpoint is "+circuit.indexOf(draggableNode));
				return circuit.indexOf(draggableNode);
			}
				
		
		}
		return -1;
	}
	public static int getEndpoint(){
		for (DraggableNode draggableNode : circuit) {
			if(draggableNode.getType().equals(DragIconType.leftpoint))
				if(circuit.indexOf(draggableNode)!=getStartpoint()){
					System.out.println("endpoint is "+circuit.indexOf(draggableNode));
					return circuit.indexOf(draggableNode);
				}
				
		
		}
		return -1;
	}
	
	public static void volMeterWork(){
		if((getEndpoint()==-1)||(getStartpoint()==-1))
		{}
		else {
			v = CircuitData.defaultVoltage*getResistanceInVoltmeter()/getTotalResistance();
			RootLayout.setMeters(v,a);
			System.out.println("voltage = "+CircuitData.defaultVoltage*getResistanceInVoltmeter()/getTotalResistance());
		}
	}
}
