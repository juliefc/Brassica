public class Conversion {
	private String type;
	private Integer position;
	
	public Conversion() {
	}
	
	public Conversion(String type, Integer position) {
		setType(type);
		setPosition(position);
	}
	
	public Conversion(Conversion conversion) {
		setType(conversion.getType());
		setPosition(conversion.getPosition());
	}
	
	public String toString() {
		return "[" + type + "," + position + "]";
	}

	// getters - setters
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
}