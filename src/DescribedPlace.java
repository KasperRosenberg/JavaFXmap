//Kasper Rosenberg
//karo0568
class DescribedPlace extends Place {
	private String description;

	public DescribedPlace(String type, String category, double x, double y, String name, String description) {
		super(type, category, x, y, name);
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
