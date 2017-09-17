package statments.operation;

public interface OperationIF {

	public boolean isValidOperation(String line);

	public int eval(String line);
}
