package assembler;

public class AssemblerFactory {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static ModesIF getAssembler(String fileName, String mode) {

		if (mode.equalsIgnoreCase("SIC")) {
			return new SicModeAssembler(fileName);
		} else if (mode.equalsIgnoreCase("SICXE")) {

		}
		return null;
	}

}
