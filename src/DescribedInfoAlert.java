//Kasper Rosenberg
//karo0568
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

class DescribedInfoAlert extends Alert {
	private TextField nameField = new TextField();
	private TextField descriptionField = new TextField();
	
	public DescribedInfoAlert(String name, Position pos, String description) {
		super(AlertType.INFORMATION);
		GridPane describedInfoGrid = new GridPane();
		describedInfoGrid.setStyle("-fx-font-size: 14");
		describedInfoGrid.addRow(0,new Label(description));
		getDialogPane().setContent(describedInfoGrid);
	}
}