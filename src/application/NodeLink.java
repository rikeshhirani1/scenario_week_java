package application;

import java.io.IOException;
import java.util.UUID;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurve;

public class NodeLink extends AnchorPane {
	@FXML RootLayout controller;
	@FXML CubicCurve node_link;
	private DraggableNode source;
	private DraggableNode target;

	private final DoubleProperty mControlOffsetX = new SimpleDoubleProperty();
	private final DoubleProperty mControlOffsetY = new SimpleDoubleProperty();
	private final DoubleProperty mControlDirectionX1 = new SimpleDoubleProperty();
	private final DoubleProperty mControlDirectionY1 = new SimpleDoubleProperty();
	private final DoubleProperty mControlDirectionX2 = new SimpleDoubleProperty();
	private final DoubleProperty mControlDirectionY2 = new SimpleDoubleProperty();

	public NodeLink() {

		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/NodeLink.fxml")
				);

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();

		} catch (IOException exception) {
		    throw new RuntimeException(exception);
		}

		//provide a universally unique identifier for this object
		setId(UUID.randomUUID().toString());
	}

	@FXML
	private void initialize() {

		mControlOffsetX.set(100.0);
		mControlOffsetY.set(50.0);


		mControlDirectionX1.bind(new When (
				node_link.startXProperty().greaterThan(node_link.endXProperty()))
				.then(-1.0).otherwise(1.0));

		mControlDirectionX2.bind(new When (
				node_link.startXProperty().greaterThan(node_link.endXProperty()))
				.then(1.0).otherwise(-1.0));


		node_link.controlX1Property().bind(
				Bindings.add(
						node_link.startXProperty(), mControlOffsetX.multiply(mControlDirectionX1)
						)
				);

		node_link.controlX2Property().bind(
				Bindings.add(
						node_link.endXProperty(), mControlOffsetX.multiply(mControlDirectionX2)
						)
				);

		node_link.controlY1Property().bind(
				Bindings.add(
						node_link.startYProperty(), mControlOffsetY.multiply(mControlDirectionY1)
						)
				);

		node_link.controlY2Property().bind(
				Bindings.add(
						node_link.endYProperty(), mControlOffsetY.multiply(mControlDirectionY2)
						)
				);
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent mouseEvent) {
		        double v;
		        double a = 0;
		        //CircuitData.printCircuit();
		       
		        CircuitData.printVolts();
		        
		        //source.calculateVoltages();
		        //v = source.vOutput;
		        //a = source.aOutput;
		        //target.vInput = v;
		        //target.aInput = a;
		        //RootLayout.setMeters(v, a);
		    }
		});
	}
	
public void setStart(Point2D startPoint) {

		node_link.setStartX(startPoint.getX());
		
		node_link.setStartY(startPoint.getY());
	}

	public void setEnd(Point2D endPoint) {

		node_link.setEndX(endPoint.getX());
		node_link.setEndY(endPoint.getY());
		
	}


	public void bindEnds (DraggableNode source, DraggableNode target) {
		node_link.startXProperty().bind(
				Bindings.add(source.layoutXProperty(), (source.getWidth() / 2.0)));

		node_link.startYProperty().bind(
				Bindings.add(source.layoutYProperty(), (source.getWidth() / 2.0)));

		node_link.endXProperty().bind(
				Bindings.add(target.layoutXProperty(), (target.getWidth() / 2.0)));

		node_link.endYProperty().bind(
				Bindings.add(target.layoutYProperty(), (target.getWidth() / 2.0)));

		source.registerLink (getId());
		target.registerLink (getId());
		//System.out.println("start from:"+source.getType());
		//System.out.println("start from:"+source.getId());
		//starting node
		if(CircuitData.circuit.isEmpty()){
			CircuitData.circuit.add(source);
			
			CircuitData.circuit.add(target);
			CircuitData.addPair(source,target);
			CircuitData.printCircuit();
		}
		//non starting node
		else {
				
				if((CircuitData.isVoltmeter(source))||(CircuitData.isVoltmeter(target))){
					System.out.println("connecting to a voltmeter");
					//whether voltmeter is placed porperly
					//voltmeterWork()
					
				}
				
				else {
					if(!target.equals(CircuitData.circuit.get(0)))
					CircuitData.circuit.add(target);
					
					CircuitData.addPair(source,target);
					CircuitData.printCircuit();	
					CircuitData.volMeterWork();

				}


		}
		

		
		
	}

}
