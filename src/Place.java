//Kasper Rosenberg
//karo0568
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Place extends Polygon {
	// Superklassen
	private String name;
	private double x;
	private double y;
	private Position position;
	private String category;
	private String type;
	boolean marked = false;

	public Place(String type, String category, double x, double y, String name) {
		super(x, y, x - 15, y - 30, x + 15, y - 30);
		this.name = name;
		this.x = x;
		this.y = y;
		this.category = category;
		this.type = type;
		setColor(category);
		position = new Position(x, y);
	}

	public void setColor(String category) {
		switch (category) {
		case "Bus":
			setFill(Color.RED);
			break;
		case "Underground":
			setFill(Color.BLUE);
			break;
		case "Train":
			setFill(Color.GREEN);
			break;
		case "None":
			setFill(Color.BLACK);
			break;
		}

	}

	public Position getPosition() {
		return position;
	}

//	public boolean switchState() {
//		return marked = !marked;
//	}

	public void setMarked() {
		setFill(Color.YELLOW);
		marked = true;
	}

	public void setUnMarked() {
		if (marked) {
			setColor(category);
			marked = false;
		}
	}

	public String getName() {
		return name;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public String getCategory() {
		return category;
	}

	public String getType() {
		return type;
	}
	public String toString() {
		return String.format("%s,%s,%.0f,%.0f,%s", getType(),getCategory(),position.getX(), position.getY(), getName());
	}

}
