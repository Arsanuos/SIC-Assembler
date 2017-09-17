package statments.directive;

public interface DirectiveIF {

	public boolean isDirective(String line);

	public boolean isStart(String line);

	public boolean isEnd(String line);

	public int eval(String line);

	public void setRepeatedStart();

	public boolean wasLtorg();

	public void setLtorg();
	
	public boolean isEQU(String line);
}
