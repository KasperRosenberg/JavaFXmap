//Kasper Rosenberg
//karo0568
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

class NamedInfoAlert extends Alert {
	private TextField nameField = new TextField();
	private TextField descriptionField = new TextField();
	
	public NamedInfoAlert(String name, Position pos) {
		super(AlertType.INFORMATION);
		GridPane namedInfoGrid = new GridPane();
		namedInfoGrid.setStyle("-fx-font-size: 14");
		//namedInfoGrid.addRow(0, new Label(name + pos.toString()));
		getDialogPane().setContent(namedInfoGrid);
	}
}