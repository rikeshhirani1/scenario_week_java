package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurve;

public class DragIcon extends AnchorPane{

	@FXML AnchorPane root_pane;

	private DragIconType mType = null;

	public DragIcon() {

		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/DragIcon.fxml")
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
	private void initialize() {}

	public void relocateToPoint (Point2D p) {

		//relocates the object to a point that has been converted to
		//scene coordinates
		Point2D localCoords = getParent().sceneToLocal(p);

		relocate (
				(int) (localCoords.getX() - (getBoundsInLocal().getWidth() / 2)),
				(int) (localCoords.getY() - (getBoundsInLocal().getHeight() / 2))
			);
	}

	public DragIconType getType () { return mType; }

	public void setType (DragIconType type) {

		mType = type;

		getStyleClass().clear();
		getStyleClass().add("dragicon");

		//added because the cubic curve will persist into other icons
		if (this.getChildren().size() > 0)
			getChildren().clear();

		switch (mType) {

		case closed_switch:
			getStyleClass().add("icon_closed_switch");
		break;

		case LED:
			getStyleClass().add("icon_LED");
		break;

		case open_switch:
			getStyleClass().add("icon_open_switch");
		break;

		case cell:
			getStyleClass().add("icon_cell");
		break;

		case resistor:
			getStyleClass().add("icon_resistor");
		break;

		case button_switch:
			getStyleClass().add("icon_button_switch");
		break;

		case lamp:
			getStyleClass().add("icon_lamp");
		break;

		case wire:
			getStyleClass().add("wire");
		break;


		default:
		break;
		}
	}
}
