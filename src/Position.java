
public class Position {
	private double x;
	private double y;
	
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	@Override
	public int hashCode() {
		return (int)(x * 100000 + y);
	}
	@Override
	public boolean equals(Object o) {
		if(o instanceof Position) {
			Position p = (Position) o;
			return x == p.getX() && y == p.getY();
		}else {
			return false;
		}
	}
	@Override
	public String toString () {
		return"["+ x + "," + y + "]";
	}
	
	
}
