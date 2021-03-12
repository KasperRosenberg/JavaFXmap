//Kasper Rosenberg
//karo0568
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NewPlaceAlert extends Alert {
	private TextField nameField = new TextField();

	public NewPlaceAlert(AlertType confirmation) {
		super(AlertType.CONFIRMATION);
		GridPane newPlaceGrid = new GridPane();
		newPlaceGrid.setStyle("-fx-font-size: 14");
		newPlaceGrid.addRow(0, new Label("Name: "), nameField);
	}
}
