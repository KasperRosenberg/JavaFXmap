import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

class DescribedPlaceAlert extends NewPlaceAlert {
	private TextField nameField = new TextField();
	private TextField descriptionField = new TextField();
	
	public DescribedPlaceAlert() {
		super(AlertType.CONFIRMATION);
		GridPane describedPlaceGrid = new GridPane();
		describedPlaceGrid.setStyle("-fx-font-size: 14");
		describedPlaceGrid.addRow(0, new Label("Name: "), nameField);
		describedPlaceGrid.addRow(1, new Label ("Description: "), descriptionField);
		getDialogPane().setContent(describedPlaceGrid);	
	}
	
	public String getName() {
		return nameField.getText();
	}
	
	public String getDescription() {
		return descriptionField.getText();
	}

}
