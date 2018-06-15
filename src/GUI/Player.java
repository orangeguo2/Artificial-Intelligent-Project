package GUI;


public enum Player {
	LIGHT,
	DARK;
	public Player opposite() {
		return this==LIGHT ? DARK : LIGHT;
	}
}
