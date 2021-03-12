//Kasper Rosenberg
//karo0568
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CoordinatesAlert extends Alert {
	private TextField xField = new TextField();
	private TextField yField = new TextField();

	public CoordinatesAlert(AlertType confirmation) {
		super(AlertType.CONFIRMATION);
		setTitle("Input coordinates:");
		setHeaderText(null);
		GridPane coordinatesGrid = new GridPane();
		coordinatesGrid.setStyle("-fx-font-size: 14");
		coordinatesGrid.addRow(0, new Label("X:"), xField);
		coordinatesGrid.addRow(1, new Label("Y:"), yField);
		getDialogPane().setContent(coordinatesGrid);
		coordinatesGrid.setVgap(5);
		coordinatesGrid.setHgap(5);
	}

	public double getXCordinate() {
		return Double.parseDouble(xField.getText());
	}

	public double getYCordinate() {
		return Double.parseDouble(yField.getText());
	}

}
