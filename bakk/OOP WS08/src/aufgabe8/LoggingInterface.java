package aufgabe8;

public interface LoggingInterface {
	public boolean debugmode = false;
	public void write(boolean debug, String message);
	public void writeCompleteStatus();
}
