package InstructionMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import errorHandler.ErrorHandler;
import passes.Literal;
import tables.OperationTable;
import tables.SymbolTable;
import utility.InstructionHandler;

public class Sic {

    private OperationTable opTable;
    private ArrayList<String> AssemblyFileInstruction;
    private ArrayList<String> fileAsItIs;
    private SymbolTable symTable;
    private ArrayList<String> operationCode;
    private ArrayList<String> AddressesList;
    private ErrorHandler errorHandler;
    private String progName;
    private HashMap<String, String> literalMap;
    private Literal literalCheck;
    private static Sic sic = new Sic();

    public static Sic getInstance() {
        // if (sic == null)
        // return new Sic();
        return sic;
    }

    private Sic() {
        operationCode = new ArrayList<>();
        literalCheck = new Literal();
    }

    public void setOpTable(OperationTable opTable) {
        this.opTable = opTable;
    }
    public void setAddressesList (ArrayList<String> a) {
        this.AddressesList = a;
    }
    public void setAssemblyFile(ArrayList<String> AssemblyFileInstruction) {
        this.AssemblyFileInstruction = AssemblyFileInstruction;
    }

    public void setAssemblyFileAsItIs(ArrayList<String> fileAsItIs) {
        this.fileAsItIs = fileAsItIs;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symTable = symbolTable;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setProgName(String progName) {
        this.progName = progName;
    }

    public void setLiteralMap(HashMap<String, String> literalMap) {
        this.literalMap = literalMap;
    }

    public void generateObjectCode() {
        int i;

        for (i = 0; i < AssemblyFileInstruction.size(); i++) {
            String line = AssemblyFileInstruction.get(i);
            String line2 = fileAsItIs.get(i);
            String operation = InstructionHandler.getOperation(line);
            String operand = InstructionHandler.getOperand(line);
            String Label = InstructionHandler.getLabel(line);
            String operand2 = InstructionHandler.getOperand(line2);

        //    System.out.println(operation);
            if (operation.equals("resb") || operation.equals("resw")
                    || operation.equals("start") || operation.equals("end")
                    || operation.equals("ltorg") || operation.equals("equ")
                    || operation.equals("org")) {

                if (operation.equals("end")) {
                    String sA = InstructionHandler.getOperand(
                            AssemblyFileInstruction.get(i));
                    if (sA != null) {
                        if (symTable.getAddress(sA) == null && !sA
                                .equalsIgnoreCase(
                                        progName)) {
                            operationCode.add(" ");
                            errorHandler.addErrorInPassTwo(
                                    AssemblyFileInstruction.get(i),
                                    ".Address not found");
                            errorHandler.setFlagErrorInPassTwo(true);
                            continue;
                        }
                    }
                }

                if(operation.equals("equ")|| operation.equals("ltorg"))
                    operationCode.add("/");
                else if (operation.equals("end"))
                    operationCode.add("-");
                else
                    operationCode.add(" ");
                errorHandler.addErrorInPassTwo(AssemblyFileInstruction.get(i),
                        " ");
                continue;
            } else if (operation.equals("word")) {
                String hexaCode = toHexa(Integer.parseInt(operand));
                hexaCode = completingZeros(hexaCode, 6);
                operationCode.add(hexaCode.toUpperCase());
                errorHandler.addErrorInPassTwo(AssemblyFileInstruction.get(i),
                        " ");
                continue;
            } else if (operation.equals("rsub")) {
                String hexaCode = opTable.getCode(operation);
                hexaCode = completingZeros(hexaCode);
                operationCode.add(hexaCode);
                errorHandler.addErrorInPassTwo(AssemblyFileInstruction.get(i),
                        " ");
                continue;

            } else if (operation.equals("byte")) {
                if (operand.charAt(0) == 'x') {
                    operationCode.add(scanningString(operand).toUpperCase());
                } else {
                    String s = scanningString(operand2);
                    String oCode = "";
                    for (int j = 0; j < s.length(); j++) {
                        oCode += toHexa(s.charAt(j));
                    }
                    operationCode.add(oCode.toUpperCase());
                }
                errorHandler.addErrorInPassTwo(AssemblyFileInstruction.get(i),
                        " ");
                continue;
            } else if (Literal.isLiteral(line,operation)) {
                String l = "";
                if (operation.contains("*")) {
                    l = operation.substring(1, operation.length());
                    int r = Integer.parseInt(l)-1;
              
                    String lineOfMinimizedFile = AssemblyFileInstruction.get(r);
                    String operationOFTheLine = InstructionHandler.getOperation(
                            lineOfMinimizedFile);
                    String operationCodeOfLine = opTable.getCode(operationOFTheLine);
                    String currentAddress = AddressesList.get(i);
                    currentAddress = completingZeros(currentAddress,4);
                    operationCodeOfLine += currentAddress;
                    operationCode.set(r, operationCodeOfLine.toUpperCase());
                    errorHandler.addErrorInPassTwo(AssemblyFileInstruction.get(r),
                            " ");
                    l = AddressesList.get(r);
                    l = completingZeros(l, 6);
                    operationCode.add(l.toUpperCase());
                    errorHandler.addErrorInPassTwo(AssemblyFileInstruction.get(i),
                            " ");
                    continue;  

                } else {
                  //  System.out.println(line);
                    l = Literal.getLiteral(line,operation, i);
                    if (l.charAt(0) == 'x') {
                        l = operation.substring(2, operation.length()-1);
                        operationCode.add(l.toUpperCase());
                    } else if (l.charAt(0) == 'c') {
                        l = operation.substring(2, operation.length()-1);
                        String oCode = "";
                        for (int j = 0; j < l.length(); j++) {
                            oCode += toHexa(l.charAt(j));
                        }
                        operationCode.add(oCode.toUpperCase());
                       
                    } else {
                        l = Integer.toHexString(Integer.parseInt(l));
                        operationCode.add(l.toUpperCase());
                    }
                    errorHandler.addErrorInPassTwo(AssemblyFileInstruction.get(i),
                            " ");
                    continue;    
                }
            }

            if ((operand.equals("0") || operand == null) && (!(operation
                    .toLowerCase()).equals("rsub") || !(operation.toLowerCase())
                            .equals("ltorg") || !Literal.isLiteral(
                                    line,operation))) {
                operationCode.add(" ");
                System.out.println(operation);
                errorHandler.addErrorInPassTwo(AssemblyFileInstruction.get(i),
                        ".NO OPERAND FOUND");
                errorHandler.setFlagErrorInPassTwo(true);
                continue;
            }

            String hexaCode = opTable.getCode(operation);

            if (operand.equals("*")) {
                String address = completingZeros(AddressesList.get(i), 4)
                        .toUpperCase();
                operationCode.add(hexaCode + address);
                errorHandler.addErrorInPassTwo(AssemblyFileInstruction.get(i),
                        " ");
                continue;
            }
            boolean containX = false;
            if (operand.contains(",x")) {
                operand = operand.substring(0, operand.length() - 2);
                containX = true;
            }

            String address = "";
            if (Literal.isLiteral(line,operand)) {
                String h = Literal.getLiteral(line,operand2, i);
                h = Literal.getBinaryRepresentationOfLiteral(line,h);
                address = literalMap.get(h);
                if (address == null) {
                    operationCode.add("");
                    errorHandler.addErrorInPassTwo(AssemblyFileInstruction.get(i),
                            " ");
                    continue;
                }
                address = completingZeros(address, 4);
            } else {
                address = symTable.getAddress(operand);
            }

            try {
                if (address == null) {
                    int intOperand = Integer.parseInt(operand, 16);
                    if (intOperand <= 32768) {
                        address = Integer.toBinaryString(intOperand);
                    } else {
                        operationCode.add(" ");
                        errorHandler.addErrorInPassTwo(AssemblyFileInstruction
                                .get(i),
                                ".OUT OF RANGE");
                        errorHandler.setFlagErrorInPassTwo(true);
                        continue;
                    }
                }
            } catch (Exception e) {
                operationCode.add(" ");
                errorHandler.addErrorInPassTwo(AssemblyFileInstruction.get(i),
                        ".LABEL NOT INITIALIZED");
                errorHandler.setFlagErrorInPassTwo(true);
                continue;
            }

            if (!Literal.isLiteral(line,operand)) {
                address = toHexa(Integer.parseInt(address, 2)).toUpperCase();
                address = completingZeros(address, 4);
            }
            if (containX) {
                int s = Integer.parseInt(address, 16);
                int t = Integer.parseInt("8000", 16);
                address = Integer.toHexString(s + t).toUpperCase();
            }

            hexaCode += address;
            operationCode.add(hexaCode);
            errorHandler.addErrorInPassTwo(AssemblyFileInstruction.get(i), " ");
        }
        System.out.println(errorHandler);

    }

    private String toHexa(int x) {
        String hexa = Integer.toHexString(x);
        return hexa;
    }

    private String scanningString(String s) {
        Scanner sw = new Scanner(s);
        String st = sw.next();
        return st.substring(2, st.length() - 1);
    }

    private String completingZeros(String s, int length) {
        while (s.length() < length) {
            s = '0' + s;
        }
        return s;
    }

    private String completingZeros(String s) {
        while (s.length() < 6) {
            s = s + '0';
        }
        return s;
    }

    public ArrayList<String> getOpCode() {
        return operationCode;
    }

    public boolean getErrorFlag() {
        return errorHandler.getErrorInPassTwoFlag();
    }
}
