package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.sun.media.jfxmedia.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RootLayout extends AnchorPane{

	@FXML SplitPane base_pane;
	@FXML AnchorPane right_pane;
	@FXML VBox left_pane;

	private DragIcon mDragOverIcon = null;

	private EventHandler<DragEvent> mIconDragOverRoot = null;
	private EventHandler<DragEvent> mIconDragDropped = null;
	private EventHandler<DragEvent> mIconDragOverRightPane = null;

	public RootLayout() {

		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/RootLayout.fxml")
				);

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();

		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}



	@FXML
	private void handleButtonAction() {
	     // Button was clicked, do something...
        System.out.println("That was easy, wasn't it?");
	 }
	@FXML
	private void initialize() {

		//Add one icon that will be used for the drag-drop process
		//This is added as a child to the root anchorpane so it can be visible
		//on both sides of the split pane.
		mDragOverIcon = new DragIcon();

		mDragOverIcon.setVisible(false);
		mDragOverIcon.setOpacity(0.65);
		getChildren().add(mDragOverIcon);

		//populate left pane with multiple coloured icons for testing
		for (int i = 1; i < 9; i++) {

			DragIcon icn = new DragIcon();

			addDragDetection(icn);

			icn.setType(DragIconType.values()[i]);
			left_pane.getChildren().add(icn);
		}

		buildDragHandlers();
	}

	private void addDragDetection(DragIcon dragIcon) {

		dragIcon.setOnDragDetected (new EventHandler <MouseEvent> () {

			@Override
			public void handle(MouseEvent event) {

				// set drag event handlers on their respective objects
				base_pane.setOnDragOver(mIconDragOverRoot);
				right_pane.setOnDragOver(mIconDragOverRightPane);
				right_pane.setOnDragDropped(mIconDragDropped);

				// get a reference to the clicked DragIcon object
				DragIcon icn = (DragIcon) event.getSource();

				//begin drag ops
				mDragOverIcon.setType(icn.getType());
				mDragOverIcon.relocateToPoint(new Point2D (event.getSceneX(), event.getSceneY()));

				ClipboardContent content = new ClipboardContent();
				DragContainer container = new DragContainer();

				container.addData ("type", mDragOverIcon.getType().toString());
				content.put(DragContainer.AddNode, container);

				mDragOverIcon.startDragAndDrop (TransferMode.ANY).setContent(content);
				mDragOverIcon.setVisible(true);
				mDragOverIcon.setMouseTransparent(true);
				event.consume();
			}
		});
	}
	@FXML
	private void closeProject(ActionEvent event){
		Platform.exit();
		System.exit(0);
	}

	@FXML
	private void savePro(ActionEvent event){
		System.out.print("ABout to save");
		
		/*
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(this.getScene().getWindow());
		if(file != null){
			SaveFile(current, file);
		}
		*/
	}

	private void SaveFile(Scene obj, File file){
		try {
			FileOutputStream fout = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(obj);
			fout.close();
		} catch (IOException ex) {

		}

	}
	@FXML
	private void openPro(ActionEvent event){
		try{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open File");
			File file = fileChooser.showOpenDialog(this.getScene().getWindow());
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Scene result = (Scene) ois.readObject();
			ois.close();
			Stage window = (Stage) this.getScene().getWindow();
			window.setScene(result);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void newPro(ActionEvent event){
		Stage primaryStage = new Stage();
		BorderPane root = new BorderPane();
		try {
			Scene scene = new Scene(root,800,480);
			scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("New Project");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		root.setCenter(new RootLayout());
	}

	private void buildDragHandlers() {

		//drag over transition to move widget form left pane to right pane
		mIconDragOverRoot = new EventHandler <DragEvent>() {

			@Override
			public void handle(DragEvent event) {

				Point2D p = right_pane.sceneToLocal(event.getSceneX(), event.getSceneY());

				//turn on transfer mode and track in the right-pane's context
				//if (and only if) the mouse cursor falls within the right pane's bounds.
				if (!right_pane.boundsInLocalProperty().get().contains(p)) {

					event.acceptTransferModes(TransferMode.ANY);
					mDragOverIcon.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
					return;
				}

				event.consume();
			}
		};

		mIconDragOverRightPane = new EventHandler <DragEvent> () {

			@Override
			public void handle(DragEvent event) {

				event.acceptTransferModes(TransferMode.ANY);

				//convert the mouse coordinates to scene coordinates,
				//then convert back to coordinates that are relative to
				//the parent of mDragIcon.  Since mDragIcon is a child of the root
				//pane, coodinates must be in the root pane's coordinate system to work
				//properly.
				mDragOverIcon.relocateToPoint(
						new Point2D(event.getSceneX(), event.getSceneY())
						);
				event.consume();
			}
		};

		mIconDragDropped = new EventHandler <DragEvent> () {

			@Override
			public void handle(DragEvent event) {

				DragContainer container =
						(DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

				container.addData("scene_coords",
						new Point2D(event.getSceneX(), event.getSceneY()));

				ClipboardContent content = new ClipboardContent();
				content.put(DragContainer.AddNode, container);

				event.getDragboard().setContent(content);
				event.setDropCompleted(true);
			}
		};

		this.setOnDragDone (new EventHandler <DragEvent> (){

			@Override
			public void handle (DragEvent event) {

				right_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRightPane);
				right_pane.removeEventHandler(DragEvent.DRAG_DROPPED, mIconDragDropped);
				base_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRoot);

				mDragOverIcon.setVisible(false);

				//Create node drag operation
				DragContainer container =
						(DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

				if (container != null) {
					if (container.getValue("scene_coords") != null) {
						DraggableNode node = new DraggableNode();
						node.setType(DragIconType.valueOf(container.getValue("type")));
						right_pane.getChildren().add(node);
						Point2D cursorPoint = container.getValue("scene_coords");
						System.out.println(cursorPoint.getX() + " " + cursorPoint.getY());
						node.relocateToPoint(
								new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32)
								);

					}
				}
				
				//Move node drag operation
				container =
						(DragContainer) event.getDragboard().getContent(DragContainer.DragNode);

				if (container != null) {
					if (container.getValue("type") != null)
						System.out.println ("Moved node " + container.getValue("type"));
				}
				 

				//AddLink drag operation
				container =
						(DragContainer) event.getDragboard().getContent(DragContainer.AddLink);

				if (container != null) {

					//bind the ends of our link to the nodes whose id's are stored in the drag container
					String sourceId = container.getValue("source");
					String targetId = container.getValue("target");

					if (sourceId != null && targetId != null) {

						//	System.out.println(container.getData());
						NodeLink link = new NodeLink();

						//add our link at the top of the rendering order so it's rendered first
						right_pane.getChildren().add(0,link);

						DraggableNode source = null;
						DraggableNode target = null;

						for (Node n: right_pane.getChildren()) {

							if (n.getId() == null)
								continue;

							if (n.getId().equals(sourceId))
								source = (DraggableNode) n;

							if (n.getId().equals(targetId))
								target = (DraggableNode) n;

						}

						if (source != null && target != null)
							link.bindEnds(source, target);
					}

				}

				event.consume();
			}
		});
	}
}
