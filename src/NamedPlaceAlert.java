//Kasper Rosenberg
//karo0568
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NamedPlaceAlert extends NewPlaceAlert {
	private TextField nameField = new TextField();

	public NamedPlaceAlert() {
		super(AlertType.CONFIRMATION);
		GridPane namedPlaceGrid = new GridPane();
		namedPlaceGrid.setStyle("-fx-font-size: 14");
		namedPlaceGrid.addRow(0, new Label("Name: "), nameField);
		getDialogPane().setContent(namedPlaceGrid);
	}

	public String getName() {
		return nameField.getText();
	}
}
