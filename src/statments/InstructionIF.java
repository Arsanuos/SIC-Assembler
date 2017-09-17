package statments;

public interface InstructionIF {

	public int eval(String line, String flag);
	
	public boolean wasLtorg();
	
	public boolean isEQU(String line);
}
