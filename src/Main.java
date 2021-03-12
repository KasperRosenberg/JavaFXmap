
//Kasper Rosenberg
//karo0568
//940404-6535
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

//Hur hantera coordinates?

public class Main extends Application {
	Stage primaryStage;
	ImageView imageView;
	ToggleGroup group;
	RadioButton namedButton;
	RadioButton describedButton;
	Pane pane;
	ListView<String> categoryList;
	Button newButton;
	boolean changed = false;
	HashMap<Position, Place> places = new HashMap();
	ArrayList<Place> markedPlaces = new ArrayList();
	Set<Place> setPlaces = new HashSet();
	HashMap<String, Set<Place>> namePlaces = new HashMap<>();
	HashMap<String, Set<Place>> categoryPlaces = new HashMap<>();
	private ClickHandler clickHandler = new ClickHandler();
	TextField searchField;
	private CordinatesHandler cordinateshandler = new CordinatesHandler();

	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		BorderPane root = new BorderPane();
		root.setStyle("fx-font-size: 14");
		Scene scene = new Scene(root, 1200, 700);
		primaryStage.setScene(scene);
		primaryStage.show();

		// Top BorderPane
		// New Radio: Name, Described Searchbar, Search, Hide, Remove, Cordinates
		FlowPane flowTop = new FlowPane();
		newButton = new Button("New");
		namedButton = new RadioButton("Named");
		describedButton = new RadioButton("Described");
		searchField = new TextField("Search");
		Button searchButton = new Button("Search");
		Button hideButton = new Button("Hide");
		Button removeButton = new Button("Remove");
		Button coordinatesButton = new Button("Coordinates");
		VBox radioVBox = new VBox();
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		VBox topBox = new VBox();
		MenuItem loadMap = new MenuItem("Load Map");
		MenuItem loadPlaces = new MenuItem("Load Places");
		MenuItem save = new MenuItem("Save");
		MenuItem exit = new MenuItem("Exit");
		fileMenu.getItems().add(loadMap);
		fileMenu.getItems().add(loadPlaces);
		fileMenu.getItems().add(save);
		fileMenu.getItems().add(exit);
		topBox.getChildren().add(menuBar);
		topBox.getChildren().add(flowTop);
		menuBar.getMenus().add(fileMenu);
		radioVBox.getChildren().add(namedButton);
		radioVBox.getChildren().add(describedButton);
		flowTop.getChildren().add(newButton);
		flowTop.getChildren().add(radioVBox);
		flowTop.getChildren().add(searchField);
		flowTop.getChildren().add(searchButton);
		flowTop.getChildren().add(hideButton);
		flowTop.getChildren().add(removeButton);
		flowTop.getChildren().add(coordinatesButton);
		flowTop.setHgap(5);
		flowTop.setVgap(5);
		flowTop.setPadding(new Insets(5));
		root.setTop(topBox);
		group = new ToggleGroup();
		namedButton.setToggleGroup(group);
		describedButton.setToggleGroup(group);
		namedButton.setSelected(true);
		searchField.setEditable(true);

		// Right BorderPane
		FlowPane flowRight = new FlowPane();
		flowRight.setOrientation(Orientation.VERTICAL);
		Label categories = new Label("Categories");
		categoryList = new ListView();
		categoryList.setPrefHeight(75);
		categoryList.setPrefWidth(175);
		categories.setAlignment(Pos.CENTER);
		categoryList.getItems().add("Bus");
		categoryList.getItems().add("Underground");
		categoryList.getItems().add("Train");
		Button hideCategoryButton = new Button("Hide Category");
		flowRight.getChildren().add(categories);
		flowRight.getChildren().add(categoryList);
		flowRight.getChildren().add(hideCategoryButton);
		flowRight.setPadding(new Insets(5));
		hideCategoryButton.setAlignment(Pos.CENTER);
		root.setRight(flowRight);

		// Center BorderPane
		pane = new Pane();
		root.setCenter(pane);

		// Ladda in karta
		loadMap.setOnAction(new LoadMapHandler());

		// Ladda in places
		loadPlaces.setOnAction(new LoadPlacesHandler());

		// Koordinatknappen
		coordinatesButton.setOnAction(new CordinatesHandler());

		// NewKnappen
		newButton.setOnAction(new NewHandler());

		// HideKnappen
		hideButton.setOnAction(new HideHandler());

		// Removeknappen
		removeButton.setOnAction(new RemoveHandler());

		// HideCategoryKnappen
		hideCategoryButton.setOnAction(new HideCategoryHandler());

		// SaveKnappen
		save.setOnAction(new SaveHandler());

		// ListViewLyssnare
		categoryList.setOnMouseClicked(new ListViewHandler());

		// Exitknappen
		exit.setOnAction(new ExitHandler());

		// KyssaRutan
		primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new KryssHandler());

		// SearchKnappen
		searchButton.setOnAction(new SearchHandler());
	}

	class ClickHandler implements EventHandler<MouseEvent> {
		public void handle(MouseEvent event) {
			double x = event.getX();
			double y = event.getY();
			Position position = new Position(x, y);
			if (places.containsKey(position)) {
				new Alert(AlertType.ERROR, "Position already occupied!").showAndWait();
				return;
			}
//			for (Position p : places.keySet()) {
//				if (p.getX() == x && p.getY() == y) {
//					Alert msg = new Alert(AlertType.ERROR, "Poistion already occupied!");
//					msg.showAndWait();
//					return;
//				}
//			}
			String listViewValue = categoryList.getSelectionModel().getSelectedItem();
			if (listViewValue == null) {
				listViewValue = "None";
			}
			RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
			String toggleGroupValue = selectedRadioButton.getText();
			if (toggleGroupValue.equalsIgnoreCase("Named")) {
				NamedPlaceAlert dialog = new NamedPlaceAlert();
				Optional<ButtonType> result = dialog.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					String name = dialog.getName();
					if (name.trim().isEmpty()) {
						Alert msg = new Alert(AlertType.ERROR, "Empty Field!");
						msg.showAndWait();
						newButton.setDisable(false);
						return;
					}

					else {
						NamedPlace namedPlace = new NamedPlace("Named", listViewValue, x, y, name);
						namedPlace.setOnMouseClicked(new MouseHandler());
						places.put(namedPlace.getPosition(), namedPlace);
						Set<Place> set = namePlaces.get(name);
						if (set == null) {
							set = new HashSet<>();
							namePlaces.put(name, set);
						}
						set.add(namedPlace);
						Set<Place> cSet = categoryPlaces.get(listViewValue);
						if (cSet == null) {
							cSet = new HashSet<>();
							categoryPlaces.put(listViewValue, cSet);
						}
						cSet.add(namedPlace);
						pane.getChildren().add(namedPlace);
						changed = true;
					}
				}
			} else if (toggleGroupValue.equalsIgnoreCase("Described")) {
				DescribedPlaceAlert dialog = new DescribedPlaceAlert();
				Optional<ButtonType> result = dialog.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					String name = dialog.getName();
					String description = dialog.getDescription();
					if (name.trim().isEmpty() || description.trim().isEmpty()) {
						Alert msg = new Alert(AlertType.ERROR, "Empty Field!");
						msg.showAndWait();
						newButton.setDisable(false);
						return;
					} else {
						DescribedPlace describedPlace = new DescribedPlace("Described", listViewValue, x, y, name,
								description);
						describedPlace.setOnMouseClicked(new MouseHandler());
						places.put(describedPlace.getPosition(), describedPlace);
						Set<Place> set = namePlaces.get(name);
						if (set == null) {
							set = new HashSet<>();
							namePlaces.put(name, set);
						}
						set.add(describedPlace);
						Set<Place> cSet = categoryPlaces.get(listViewValue);
						if (cSet == null) {
							cSet = new HashSet<>();
							categoryPlaces.put(listViewValue, cSet);
						}
						pane.getChildren().add(describedPlace);
						changed = true;
					}
				}
			}
			pane.removeEventHandler(MouseEvent.MOUSE_CLICKED, clickHandler);
			pane.setCursor(Cursor.DEFAULT);
			newButton.setDisable(false);
			categoryList.getSelectionModel().clearSelection();
		}
	}

	class LoadMapHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (changed) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Osparat. Avsluta ändå?");
				Optional<ButtonType> resultat = alert.showAndWait();
				if (resultat.isPresent() && resultat.get() == ButtonType.CANCEL) {
					event.consume();
					return;
				}
//				if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
//					places.clear();
//					markedPlaces.clear();
//					namePlaces.clear();
//					categoryPlaces.clear();
//				}
			}
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select image file");
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"),
					new FileChooser.ExtensionFilter("All files", "*.*"));
			File file = fileChooser.showOpenDialog(primaryStage);
			if (file == null) {
				return;
			}
			imageView = new ImageView();
			String name = file.getAbsolutePath();
			Image image = new Image("File:" + name);
			imageView.setImage(image);
			pane.getChildren().add(imageView);
			pane.getChildren().removeAll(places.values());
			places.clear();
			namePlaces.clear();
			categoryPlaces.clear();
			markedPlaces.clear();
			changed = false;

		}
	}

	class LoadPlacesHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (changed) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("You will lose your unsaved locations, continue anyway?");
				Optional<ButtonType> resultat = alert.showAndWait();
				if (resultat.isPresent() && resultat.get() == ButtonType.CANCEL) {
					event.consume();
				}
//				if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
//					places.clear();
//					markedPlaces.clear();
//					namePlaces.clear();
//					categoryPlaces.clear();
//					System.out.println(places);
//					System.out.println(markedPlaces);
//					System.out.println(categoryPlaces);
//					System.out.println(namePlaces);
//					changed = false;
//				}
			}
//			for (Place p : places.values()) {
//				pane.getChildren().remove(p);
//			}
			try {
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file == null) {
					return;
				}
				pane.getChildren().removeAll(places.values());
				places.clear();
				markedPlaces.clear();
				namePlaces.clear();
				categoryPlaces.clear();
				changed = false;
				String name = file.getAbsolutePath();
				FileReader infil = new FileReader(name);
				BufferedReader in = new BufferedReader(infil);
				String line;
				while ((line = in.readLine()) != null) {
					String[] tokens = line.split(",");
					String type = tokens[0];
					String category = tokens[1];
					Double x = Double.parseDouble(tokens[2]);
					Double y = Double.parseDouble(tokens[3]);
					String namn = tokens[4];
					// Om type == named gör named
					if (type.equalsIgnoreCase("named")) {
						NamedPlace p = new NamedPlace(type, category, x, y, namn);
						Position pos = p.getPosition();
						p.setOnMouseClicked(new MouseHandler());
						places.put(pos, p);
						Set<Place> set = namePlaces.get(namn);
						if (set == null) {
							set = new HashSet<>();
							namePlaces.put(namn, set);
						}
						set.add(p);
						Set<Place> cSet = categoryPlaces.get(category);
						if (cSet == null) {
							cSet = new HashSet<>();
							categoryPlaces.put(category, cSet);
						}
						cSet.add(p);
						pane.getChildren().add(p);

					}
					if (type.equalsIgnoreCase("described")) {
						String description = tokens[5];
						DescribedPlace p = new DescribedPlace(type, category, x, y, namn, description);
						Position pos = p.getPosition();
						places.put(pos, p);
						p.setOnMouseClicked(new MouseHandler());
						Set<Place> set = namePlaces.get(namn);
						if (set == null) {
							set = new HashSet<>();
							namePlaces.put(namn, set);
						}
						set.add(p);
						Set<Place> cSet = categoryPlaces.get(category);
						if (cSet == null) {
							cSet = new HashSet<>();
							categoryPlaces.put(category, cSet);
						}
						cSet.add(p);
						pane.getChildren().add(p);
					}
				}
				infil.close();
				in.close();

			} catch (FileNotFoundException ek) {
				System.out.println("File not found exception");
				new Alert(AlertType.ERROR, ek.getMessage()).showAndWait();
			} catch (IOException e) {
				System.out.println("IO Exception fel");
				new Alert(AlertType.ERROR, e.getMessage()).showAndWait();

			}
		}
	}

	class SaveHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Select where to save");
				File file = fileChooser.showSaveDialog(primaryStage);
				if (file == null) {
					return;
				}
				String name = file.getAbsolutePath();
				FileWriter outfile = new FileWriter(name);
				PrintWriter out = new PrintWriter(outfile);
				for (Place p : places.values()) {
					if (p.getType().equalsIgnoreCase("named")) {
						out.println(p.getType() + "," + p.getCategory() + "," + p.getX() + "," + p.getY() + ","
								+ p.getName());
					}
					if (p.getType().equalsIgnoreCase("described")) {
						DescribedPlace dp = (DescribedPlace) p;
						out.println(p.getType() + "," + p.getCategory() + "," + p.getX() + "," + p.getY() + ","
								+ p.getName() + "," + dp.getDescription());
					}
				}
				out.close();
				outfile.close();
				changed = false;
			} catch (FileNotFoundException e) {
				new Alert(AlertType.ERROR, "File not found!").showAndWait();
			} catch (IOException e) {
				new Alert(AlertType.ERROR, "" + e.getMessage()).showAndWait();

			}
		}
	}

	class NewHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			pane.addEventHandler(MouseEvent.MOUSE_CLICKED, clickHandler);
			pane.setCursor(Cursor.CROSSHAIR);
			newButton.setDisable(true);

		}
	}

	class MouseHandler implements EventHandler<MouseEvent> {
		public void handle(MouseEvent event) {
			MouseButton button = event.getButton();
			Place p1 = (Place) event.getSource();
			Position pos = p1.getPosition();
			String name = p1.getName();
			if (button == MouseButton.SECONDARY) {
				if (p1.getType().equalsIgnoreCase("named")) {
					System.out.println(p1.getType());
					NamedInfoAlert alert = new NamedInfoAlert(name, pos);
					alert.setHeaderText(name + pos);
					alert.showAndWait();
				}
				if (p1.getType().equalsIgnoreCase("described")) {
					DescribedPlace dp = (DescribedPlace) p1;
					String description = dp.getDescription();
					DescribedInfoAlert alert = new DescribedInfoAlert(name, pos, description);
					alert.setHeaderText(name + pos);
					alert.showAndWait();
				}
			}
			if (button == MouseButton.PRIMARY) {
				if (markedPlaces.contains(p1)) {
					markedPlaces.remove(p1);
					p1.setUnMarked();
				} else {
					markedPlaces.add(p1);
					p1.setMarked();
				}
			}
		}
	}

	class SearchHandler implements EventHandler<ActionEvent> {
		//String name;

		public void handle(ActionEvent event) {
//			for (Place p : markedPlaces) {
//				p.setUnMarked();
//			}
			Iterator<Place> iter1 = markedPlaces.iterator();
			while (iter1.hasNext()) {
				Place p = iter1.next();
				p.setUnMarked();
				iter1.remove();
			}
			//markedPlaces.clear();
			String name = searchField.getText();
			Set<Place> set = namePlaces.get(name);
			if (set != null) {
				for (Place p : set) {
					p.setMarked();
					markedPlaces.add(p);
					p.setVisible(true);
				}
			}
		}
	}

	class HideHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (!markedPlaces.isEmpty()) {
				Iterator<Place> iter = markedPlaces.iterator();
				while (iter.hasNext()) {
					Place p = iter.next();
					Set<Place> set = namePlaces.get(p.getName());
					p.setUnMarked();
					p.setVisible(false);
					if (set.isEmpty()) {
						namePlaces.remove(p.getName());
					}
					Set<Place> cSet = categoryPlaces.get(p.getCategory());
					if (cSet.isEmpty()) {
						categoryPlaces.remove(p.getName());
					}

					iter.remove();
				}
			}
		}
	}

	class RemoveHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (!markedPlaces.isEmpty()) {
				Iterator<Place> iter = markedPlaces.iterator();
				while (iter.hasNext()) {
					Place p = iter.next();
					places.remove(p.getPosition(), p);
					Set<Place> set = namePlaces.get(p.getName());
					set.remove(p);

					if (set.isEmpty()) {
						namePlaces.remove(p.getName());
					}
					Set<Place> cSet = categoryPlaces.get(p.getCategory());
					cSet.remove(p);
					if (set.isEmpty()) {
						categoryPlaces.remove(p.getName());
					}
					pane.getChildren().remove(p);
					iter.remove();
				}
				changed = true;
			}
		}
	}

	class CordinatesHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				CoordinatesAlert dialog = new CoordinatesAlert(AlertType.CONFIRMATION);
				Optional<ButtonType> result = dialog.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					double xCordinate = dialog.getXCordinate();
					double yCordinate = dialog.getYCordinate();
					Position position = new Position(xCordinate, yCordinate);
					Place found = places.get(position);
					if (xCordinate < 0 || yCordinate < 0) {
						Alert msg = new Alert(AlertType.ERROR, "Invalid coordinates!");
						msg.showAndWait();
						return;
					} else if (found == null) {
						Alert msg = new Alert(AlertType.ERROR, "No Place at this location!");
						msg.showAndWait();
						return;
					} else {
						Iterator<Place> iter = markedPlaces.iterator();
						while (iter.hasNext()) {
							Place p = iter.next();
							p.setUnMarked();
							iter.remove();
						}
						found.setVisible(true);
						found.setMarked();
						markedPlaces.add(found);
						pane.removeEventFilter(ActionEvent.ACTION, cordinateshandler);
						return;
					}
//					{
//						for (Position p : places.keySet()) {
//							if (p.getX() == xCordinate && p.getY() == yCordinate) {
//								Place found = places.get(p);
//								found.setMarked();
//								found.setVisible(true);
//								markedPlaces.add(found);
//								pane.removeEventHandler(ActionEvent.ACTION, cordinateshandler);
//								return;
//							}
//						}
//						Alert msg = new Alert(AlertType.ERROR, "No Place at this location!");
//						msg.showAndWait();
//					}
				}
			} catch (NumberFormatException e) {
				Alert msg = new Alert(AlertType.ERROR);
				msg.setContentText("x & y must be numbers");
				msg.showAndWait();
			}
			pane.removeEventHandler(ActionEvent.ACTION, cordinateshandler);
		}
	}

	class ListViewHandler implements EventHandler<MouseEvent> {
		public void handle(MouseEvent event) {
			String category = categoryList.getSelectionModel().getSelectedItem();
			Set<Place> sameCategory = categoryPlaces.get(category);
			if (sameCategory != null) {
				for (Place p : sameCategory) {
					p.setVisible(true);
				}
			}
		}
	}

	class HideCategoryHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			String listViewValue = categoryList.getSelectionModel().getSelectedItem();
			if (listViewValue == null) {
				listViewValue = "None";
			}

			Set<Place> cSet = categoryPlaces.get(listViewValue);
			if (cSet == null) {
				return;
			} else {
				for (Place p : cSet) {
					p.setVisible(false);
				}
			}
		}
	}

	class KryssHandler implements EventHandler<WindowEvent> {
		public void handle(WindowEvent event) {
			if (changed) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Osparat. Avsluta ändå?");
				Optional<ButtonType> resultat = alert.showAndWait();
				if (resultat.isPresent() && resultat.get() == ButtonType.CANCEL) {
					event.consume();
				}

			}
		}
	}

	class ExitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (changed) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Osparat. Avsluta ändå?");
				Optional<ButtonType> resultat = alert.showAndWait();
				if (resultat.isPresent() && resultat.get() == ButtonType.CANCEL) {
					event.consume();
				}
				if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
					primaryStage.close();
				}

			} else {
				primaryStage.close();
			}
		}
	}

//METODER
//	public void addToSet(String s, Place p) {
//		Set<Place> set = namePlaces.get(s);
//		if(set == null) {
//			set = new HashSet<>();
//			namePlaces.put(s, set);
//		}
//		set.add(p);
//	}
	public static void main(String[] args) {
		Application.launch(args);
	}

}
