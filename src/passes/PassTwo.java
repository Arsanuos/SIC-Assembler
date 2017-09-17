package passes;
 
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import InstructionMode.Sic;
import errorHandler.ErrorHandler;
import resources.Resources;
import tables.SymbolTable;
import utility.InstructionHandler;
 
public class PassTwo {
 
    private String progName;
    private String startingAddress;
    private int progLength;
    private ArrayList<String> locationCounters;
    private PrintWriter writer;
    private boolean pass2Flag;
    private String lastLine;
    private SymbolTable symTable;
    private ErrorHandler errorHandler;
    private Resources resources;
 
    public PassTwo(Resources resources) {
        this.progName = resources.getProgName();
        this.startingAddress = resources.getStartingAddress();
        this.progLength = resources.getProgramLen();
        this.locationCounters = resources.getAddresses();
        this.pass2Flag = resources.getErrorHandler().getErrorInPassTwoFlag();
        symTable = resources.getSymTable();
        errorHandler = resources.getErrorHandler();
        this.resources = resources;
    }
 
    public PassTwo() {
    }
 
    public void opCodeGeneration() {
        if (pass2Flag) {
            return;
        }
        try {
            
            int counter = 0;
            writer = new PrintWriter("opCode.txt");
            writer.print("H");
            if (progName == null) {
                writer.print("      ");
            } else {
                writer.print("^");
                writer.print(completingSpaces(progName, 6));
                writer.print("^");
            }
            writer.print(completingZeros(startingAddress, 6));
            writer.print("^");
            writer.println(completingZeros(Integer.toHexString(progLength)
                    .toUpperCase(), 6));
            String toBePrinted = "";
            for (int i = 1; i < Sic.getInstance().getOpCode().size(); i++) {
                if (toBePrinted.equals("")) {
                    if (Sic.getInstance().getOpCode().get(i) == " ") {
                        continue;
                    }
                    writer.print("T");
                    writer.print("^");
                    writer.print(completingZeros(locationCounters.get(i), 6)
                            .toUpperCase());
                    writer.print("^");
                    toBePrinted = Sic.getInstance().getOpCode().get(i)
                            .toUpperCase();
                    counter = Sic.getInstance().getOpCode().get(i).length();
                } else {
                    if (Sic.getInstance().getOpCode().get(i) == " "
                            && toBePrinted.length() > 1) {
                        writer.print(calculateLength(counter) + "^");
                        writer.println(toBePrinted.toUpperCase());
                        toBePrinted = "";
                        counter = 0;
                        continue;
                    } else if (Sic.getInstance().getOpCode().get(i) == " ") {
                        toBePrinted = "";
                        counter = 0;
                        continue;
                    } else if (Sic.getInstance().getOpCode().get(i) == "/") {
                        continue;
                    }  else if (Sic.getInstance().getOpCode().get(i) == "-") {
                        continue;
                    }
                    
                    toBePrinted += "^" + Sic.getInstance().getOpCode().get(i)
                            .toUpperCase();
                    counter += Sic.getInstance().getOpCode().get(i)
                            .length();
                    if (counter > 60) {
                        writer.print(calculateLength(counter - Sic
                                .getInstance().getOpCode().get(i).length())
                                + "^");
                        writer.println(toBePrinted.substring(0, toBePrinted
                                .length() - Sic.getInstance().getOpCode().get(i)
                                        .length() - 1));
                        toBePrinted = "";
                        counter = 0;
                        i--;
                    } else if (i == Sic.getInstance().getOpCode().size()-1) {
                        writer.print(calculateLength(counter) + "^");
                        writer.println(toBePrinted.toUpperCase());
                    }
 
                }
            }
 
            for(int j =0; j<Sic.getInstance().getOpCode().size(); j++){
                if(Sic.getInstance().getOpCode().get(j).equals("-")){
                    lastLine = resources.getAssemblyFileInstruction().get(j);
                }
                    
            }
            
            String sA = InstructionHandler.getOperand(lastLine);
            
            if (sA != null) {
                if (symTable.getAddress(sA) != null) {
                    startingAddress = symTable.getAddress(sA);
                    int temp = Integer.parseInt(startingAddress, 2);
                    startingAddress = Integer.toHexString(temp);
                } 
            }
           
            writer.print("E");
            writer.print("^");
            writer.print(completingZeros(startingAddress, 6));
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    private String completingSpaces(String s, int length) {
        while (s.length() < length) {
            s += ' ';
        }
        return s;
    }
 
    private String completingZeros(String s, int length) {
        while (s.length() < length) {
            s = "0" + s;
        }
        return s;
    }
 
    private String calculateLength(int l) {
        int num = l / 2;
        return Integer.toHexString(num).toUpperCase();
    }
 
}