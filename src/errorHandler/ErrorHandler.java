package errorHandler;

import java.util.ArrayList;

public class ErrorHandler {

	private ArrayList<String> errorInPassOne;
	private ArrayList<String> errorInPassTwo;
	private boolean FlagErrorInPassOne;
	private boolean FlagErrorInPassTwo;

	public ErrorHandler() {
		// TODO Auto-generated constructor stub
		FlagErrorInPassOne = false;
		FlagErrorInPassTwo = false;
		errorInPassOne = new ArrayList<String>();
		errorInPassTwo = new ArrayList<String>();
	}

	public ArrayList<String> getErrorInPassOne() {
		return errorInPassOne;
	}

	public void addErrorInPassOne(String line, String message) {
		this.errorInPassOne.add(message);
	}

	public ArrayList<String> getErrorInPassTwo() {
		return errorInPassTwo;
	}

	public void addErrorInPassTwo(String line, String message) {
		this.errorInPassTwo.add(message);
	}

	public boolean isFlagErrorInPassOne() {
		return FlagErrorInPassOne;
	}

	public void setFlagErrorInPassOne(boolean flagErrorInPassOne) {
		FlagErrorInPassOne = flagErrorInPassOne;
	}

	public boolean isFlagErrorInPassTwo() {
		return FlagErrorInPassTwo;
	}

	public void setFlagErrorInPassTwo(boolean flagErrorInPassTwo) {
		FlagErrorInPassTwo = flagErrorInPassTwo;
	}

	public boolean getErrorInPassTwoFlag() {
		return FlagErrorInPassTwo;
	}

	public void setErrorInPassOne(ArrayList<String> errorInPassOne) {
		this.errorInPassOne = errorInPassOne;
	}
}
