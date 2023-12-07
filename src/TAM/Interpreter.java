/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package TAM;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Interpreter {

  static String objectName;


// DATA STORE

  static int[] data = new int[1024];


// DATA STORE REGISTERS AND OTHER REGISTERS

  final static int
    CB = 0,
    SB = 0,
    HB = 1024;  // = upper bound of data array + 1

  static int
    CT, CP, ST, HT, LB, status;

  // status values
  final static int
    running = 0, halted = 1, failedDataStoreFull = 2, failedInvalidCodeAddress = 3,
    failedInvalidInstruction = 4, failedOverflow = 5, failedZeroDivide = 6,
    failedIOError = 7;

  static long
    accumulator;

  static int content (int r) {
    // Returns the current content of register r,
    // even if r is one of the pseudo-registers L1..L6.

    switch (r) {
      case Machine.CBr:
        return CB;
      case Machine.CTr:
        return CT;
      case Machine.PBr:
        return Machine.PB;
      case Machine.PTr:
        return Machine.PT;
      case Machine.SBr:
        return SB;
      case Machine.STr:
        return ST;
      case Machine.HBr:
        return HB;
      case Machine.HTr:
        return HT;
      case Machine.LBr:
        return LB;
      case Machine.L1r:
        return data[LB];
      case Machine.L2r:
        return data[data[LB]];
      case Machine.L3r:
        return data[data[data[LB]]];
      case Machine.L4r:
        return data[data[data[data[LB]]]];
      case Machine.L5r:
        return data[data[data[data[data[LB]]]]];
      case Machine.L6r:
        return data[data[data[data[data[data[LB]]]]]];
      case Machine.CPr:
        return CP;
      default:
        return 0;
    }
  }


// PROGRAM STATUS

  static void dump() {
    // Writes a summary of the machine state.
    int
      addr, staticLink, dynamicLink,
      localRegNum;

    System.out.println ("");
    System.out.println ("State of data store and registers:");
    System.out.println ("");
    if (HT == HB)
      System.out.println("            |--------|          (heap is empty)");
    else {
      System.out.println("       HB-->");
      System.out.println("            |--------|");
      for (addr = HB - 1; addr >= HT; addr--) {
        System.out.print(addr + ":");
        if (addr == HT)
          System.out.print(" HT-->");
        else
          System.out.print("      ");
        System.out.println("|" + data[addr] + "|");
      }
      System.out.println("            |--------|");
    }
    System.out.println("            |////////|");
    System.out.println("            |////////|");
    if (ST == SB)
      System.out.println("            |--------|          (stack is empty)");
    else {
      dynamicLink = LB;
      staticLink = LB;
      localRegNum = Machine.LBr;
      System.out.println("      ST--> |////////|");
      System.out.println("            |--------|");
      for (addr = ST - 1; addr >= SB; addr--) {
        System.out.print(addr + ":");
        if (addr == SB)
          System.out.print(" SB-->");
        else if (addr == staticLink) {
          switch (localRegNum) {
            case Machine.LBr:
              System.out.print(" LB-->");
              break;
            case Machine.L1r:
              System.out.print(" L1-->");
              break;
            case Machine.L2r:
              System.out.print(" L2-->");
              break;
            case Machine.L3r:
              System.out.print(" L3-->");
              break;
            case Machine.L4r:
              System.out.print(" L4-->");
              break;
            case Machine.L5r:
              System.out.print(" L5-->");
              break;
            case Machine.L6r:
              System.out.print(" L6-->");
              break;
          }
          staticLink = data[addr];
          localRegNum = localRegNum + 1;
        } else
          System.out.print("      ");
        if ((addr == dynamicLink) && (dynamicLink != SB))
          System.out.print("|SL=" + data[addr] + "|");
        else if ((addr == dynamicLink + 1) && (dynamicLink != SB))
          System.out.print("|DL=" + data[addr] + "|");
        else if ((addr == dynamicLink + 2) && (dynamicLink != SB))
          System.out.print("|RA=" + data[addr] + "|");
        else
          System.out.print("|" + data[addr] + "|");
        System.out.println ("");
        if (addr == dynamicLink) {
          System.out.println("            |--------|");
          dynamicLink = data[addr + 1];
        }
      }
    }
    System.out.println ("");
  }

  static void showStatus () {
    // Writes an indication of whether and why the program has terminated.
    System.out.println ("");
    switch (status) {
      case running:
        System.out.println("Program is running.");
        break;
      case halted:
        System.out.println("Program has halted normally.");
        break;
      case failedDataStoreFull:
        System.out.println("Program has failed due to exhaustion of Data Store.");
        break;
      case failedInvalidCodeAddress:
        System.out.println("Program has failed due to an invalid code address.");
        break;
      case failedInvalidInstruction:
        System.out.println("Program has failed due to an invalid instruction.");
        break;
      case failedOverflow:
        System.out.println("Program has failed due to overflow.");
        break;
      case failedZeroDivide:
        System.out.println("Program has failed due to division by zero.");
        break;
      case failedIOError:
        System.out.println("Program has failed due to an IO error.");
        break;
    }
    if (status != halted)
      dump();
  }


// INTERPRETATION

  static void checkSpace (int spaceNeeded) {
    // Signals failure if there is not enough space to expand the stack or
    // heap by spaceNeeded.

    if (HT - ST < spaceNeeded)
      status = failedDataStoreFull;
  }

  static boolean isTrue (int datum) {
    // Tests whether the given datum represents true.
    return (datum == Machine.trueRep);
  }

  static boolean equal (int size, int addr1, int addr2) {
    // Tests whether two multi-word objects are equal, given their common
    // size and their base addresses.

    boolean eq;
    int index;

    eq = true;
    index = 0;
    while (eq && (index < size))
      if (data[addr1 + index] == data[addr2 + index])
        index = index + 1;
      else
        eq = false;
    return eq;
  }

  static int overflowChecked (long datum) {
    // Signals failure if the datum is too large to fit into a single word,
    // otherwise returns the datum as a single word.

    if ((-Machine.maxintRep <= datum) && (datum <= Machine.maxintRep))
      return (int) datum;
    else {
      status = failedOverflow;
      return 0;
    }
  }

  static int toInt(boolean b) {
    return b ? Machine.trueRep : Machine.falseRep;
  }

  static int currentChar;

  static int readInt() throws java.io.IOException {
    int temp = 0;
    int sign = 1;

    do {
      currentChar = System.in.read();
    } while (Character.isWhitespace((char) currentChar));

    if ((currentChar == '-') || (currentChar == '+'))
      do {
        sign = (currentChar == '-') ? -1 : 1;
        currentChar = System.in.read();
      } while ((currentChar == '-')  || currentChar == '+');

    if (Character.isDigit((char) currentChar))
      do {
        temp = temp * 10 + (currentChar - '0');
        currentChar = System.in.read();
      } while (Character.isDigit((char) currentChar));

    return sign * temp;
  }

  static void callPrimitive (int primitiveDisplacement) {
    // Invokes the given primitive routine.

    int addr, size;
    char ch;

    switch (primitiveDisplacement) {
      case Machine.idDisplacement:
        break;
      case Machine.notDisplacement:
        data[ST - 1] = toInt(!isTrue(data[ST - 1]));
        break;
      case Machine.andDisplacement:
        ST = ST - 1;
        data[ST - 1] = toInt(isTrue(data[ST - 1]) & isTrue(data[ST]));
        break;
      case Machine.orDisplacement:
        ST = ST - 1;
        data[ST - 1] = toInt(isTrue(data[ST - 1]) | isTrue(data[ST]));
        break;
      case Machine.succDisplacement:
        data[ST - 1] = overflowChecked(data[ST - 1] + 1);
        break;
      case Machine.predDisplacement:
        data[ST - 1] = overflowChecked(data[ST - 1] - 1);
        break;
      case Machine.negDisplacement:
        data[ST - 1] = -data[ST - 1];
        break;
      case Machine.addDisplacement:
        ST = ST - 1;
        accumulator = data[ST - 1];
        data[ST - 1] = overflowChecked(accumulator + data[ST]);
        break;
      case Machine.subDisplacement:
        ST = ST - 1;
        accumulator = data[ST - 1];
        data[ST - 1] = overflowChecked(accumulator - data[ST]);
        break;
      case Machine.multDisplacement:
        ST = ST - 1;
        accumulator = data[ST - 1];
        data[ST - 1] = overflowChecked(accumulator * data[ST]);
        break;
      case Machine.divDisplacement:
        ST = ST - 1;
        accumulator = data[ST - 1];
        if (data[ST] != 0)
          data[ST - 1] = (int) (accumulator / data[ST]);
        else
          status = failedZeroDivide;
        break;
      case Machine.modDisplacement:
        ST = ST - 1;
        accumulator = data[ST - 1];
        if (data[ST] != 0)
          data[ST - 1] = (int) (accumulator % data[ST]);
        else
          status = failedZeroDivide;
        break;
      case Machine.ltDisplacement:
        ST = ST - 1;
        data[ST - 1] = toInt(data[ST - 1] < data[ST]);
        break;
      case Machine.leDisplacement:
        ST = ST - 1;
        data[ST - 1] = toInt(data[ST - 1] <= data[ST]);
        break;
      case Machine.geDisplacement:
        ST = ST - 1;
        data[ST - 1] = toInt(data[ST - 1] >= data[ST]);
        break;
      case Machine.gtDisplacement:
        ST = ST - 1;
        data[ST - 1] = toInt(data[ST - 1] > data[ST]);
        break;
      case Machine.eqDisplacement:
        size = data[ST - 1];
        ST = ST - 2 * size;
        data[ST - 1] = toInt(equal(size, ST - 1, ST - 1 + size));
        break;
      case Machine.neDisplacement:
        size = data[ST - 1];
        ST = ST - 2 * size;
        data[ST - 1] = toInt(! equal(size, ST - 1, ST - 1 + size));
        break;
      case Machine.eolDisplacement:
        data[ST] = toInt(currentChar == '\n');
        ST = ST + 1;
        break;
      case Machine.eofDisplacement:
        data[ST] = toInt(currentChar == -1);
        ST = ST + 1;
        break;
      case Machine.getDisplacement:
        ST = ST - 1;
        addr = data[ST];
        try {
          currentChar = System.in.read();
        } catch (java.io.IOException s) {
          status = failedIOError;
        }
        data[addr] = (int) currentChar;
        break;
      case Machine.putDisplacement:
        ST = ST - 1;
        ch = (char) data[ST];
        System.out.print(ch);
        break;
      case Machine.geteolDisplacement:
        try {
          while ((currentChar = System.in.read()) != '\n');
        } catch (java.io.IOException s) {
          status = failedIOError;
        }
        break;
      case Machine.puteolDisplacement:
        System.out.println ("");
        break;
      case Machine.getintDisplacement:
        ST = ST - 1;
        addr = data[ST];
        try {
          accumulator = readInt();
        } catch (java.io.IOException s) {
          status = failedIOError;
        }
        data[addr] = (int) accumulator;
        break;
      case Machine.putintDisplacement:
        ST = ST - 1;
        accumulator = data[ST];
        System.out.print(accumulator);
        break;
      case Machine.newDisplacement:
        size = data[ST - 1];
        checkSpace(size);
        HT = HT - size;
        data[ST - 1] = HT;
        break;
      case Machine.disposeDisplacement:
        ST = ST - 1;
        break;
    }
  }

  static void interpretProgram() {
    Instruction currentInstr;
    int op, r, n, d, addr, index;
    
    String justInstructionName;

    // Initialize registers
    ST = SB;
    HT = HB;
    LB = SB;
    CP = CB;
    status = running;
    do {
      currentInstr = Machine.code[CP];
      
      op = currentInstr.op;
      r = currentInstr.r;
      n = currentInstr.n;
      d = currentInstr.d;

      switch (op) {
        
        case Machine.LOADop:
          justInstructionName = "****************LOADop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          addr = d + content(r);
          checkSpace(n);
          for (index = 0; index < n; index++)
            data[ST + index] = data[addr + index];
          ST = ST + n;
          CP = CP + 1;
          break;
        
        case Machine.LOADAop:
          justInstructionName = "****************LOADAop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          addr = d + content(r);
          checkSpace(1);
          data[ST] = addr;
          ST = ST + 1;
          CP = CP + 1;
          break;

        case Machine.LOADIop:
          justInstructionName = "****************LOADIop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          ST = ST - 1;
          addr = data[ST];
          checkSpace(n);
          for (index = 0; index < n; index++)
            data[ST + index] = data[addr + index];
          ST = ST + n;
          CP = CP + 1;
          break;

        case Machine.LOADLop:
          justInstructionName = "****************LOADLop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          checkSpace(1);
          data[ST] = d;
          ST = ST + 1;
          CP = CP + 1;
          break;

        case Machine.STOREop:
          justInstructionName = "****************STOREop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          addr = d + content(r);
          ST = ST - n;
          for (index = 0; index < n; index++)
            data[addr + index] = data[ST + index];
          CP = CP + 1;
          break;

        case Machine.STOREIop:
          justInstructionName = "****************STOREIop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          ST = ST - 1;
          addr = data[ST];
          ST = ST - n;
          for (index = 0; index < n; index++)
            data[addr + index] = data[ST + index];
          CP = CP + 1;
          break;

        case Machine.CALLop:
          justInstructionName = "****************CALLop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          addr = d + content(r);
          if (addr >= Machine.PB) {
            callPrimitive(addr - Machine.PB);
            CP = CP + 1;
          } else {
            checkSpace(3);
            if ((0 <= n) && (n <= 15))
              data[ST] = content(n);
            else
              status = failedInvalidInstruction;
            data[ST + 1] = LB;
            data[ST + 2] = CP + 1;
            LB = ST;
            ST = ST + 3;
            CP = addr;
          }
          break;
        
        case Machine.CALLIop:
          justInstructionName = "****************CALLIop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          ST = ST - 2;
          addr = data[ST + 1];
          if (addr >= Machine.PB) {
            callPrimitive(addr - Machine.PB);
            CP = CP + 1;
          } else {
            data[ST + 1] = LB;
            data[ST + 2] = CP + 1;
            LB = ST;
            ST = ST + 3;
            CP = addr;
          }
          break;

        case Machine.RETURNop:
          justInstructionName = "****************RETURNop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          addr = LB - d;
          CP = data[LB + 2];
          LB = data[LB + 1];
          ST = ST - n;
          for (index = 0; index < n; index++)
            data[addr + index] = data[ST + index];
          ST = addr + n;
          break;
        
        case Machine.PUSHop:
          justInstructionName = "****************PUSHop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          checkSpace(d);
          ST = ST + d;
          CP = CP + 1;
          break;
      
        case Machine.POPop:
          justInstructionName = "****************POPop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          addr = ST - n - d;
          ST = ST - n;
          for (index = 0; index < n; index++)
            data[addr + index] = data[ST + index];
          ST = addr + n;
          CP = CP + 1;
          break;
        
        case Machine.JUMPop:
          justInstructionName = "****************JUMPop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          CP = d + content(r);
          break;
        
        case Machine.JUMPIop:
          justInstructionName = "****************JUMPIop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          ST = ST - 1;
          CP = data[ST];
          break;
        
        case Machine.JUMPIFop:
          justInstructionName = "****************JUMPIFop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          ST = ST - 1;
          if (data[ST] == n)
            CP = d + content(r);
          else
            CP = CP + 1; 
          break;
        
        case Machine.HALTop:
          justInstructionName = "****************HALTop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          status = halted;
          break;

        // Instruction for read statement
        case Machine.LOADLREADop:
          justInstructionName = "****************LOADLREADop****************";
          printCurrentInstrucctions(op, r, n, d, justInstructionName);
          checkSpace(1);
          // Reads from the keyboard
		      Scanner entradaEscaner = new Scanner (System.in);
          d = entradaEscaner.nextInt();
		      
          data[ST] = d;
          ST = ST + 1;
          CP = CP + 1;
          break;

      }
      if ((CP < CB) || (CP >= CT))
        status = failedInvalidCodeAddress;

    } while (status == running);
  }

 static void printCurrentInstrucctions(int op,int r,int n,int d,String justInstructionName) {
    if(false){
      System.out.println(justInstructionName);
      System.out.println("**************************************");
      System.out.print("***             op: ");
      System.out.print(op);
      System.out.println("             ***");
      System.out.print("***             r:  ");
      System.out.print(r);
      System.out.println("             ***");
      System.out.print("***             n:  ");
      System.out.print(n);
      System.out.println("             ***");
      System.out.print("***             d:  ");
      System.out.print(d);
      System.out.println("             ***");
      System.out.println("**************************************");
      }
    }
  

// LOADING
  static void loadObjectProgram (String objectName) {
    // Loads the TAM object program into code store from the named file.

    FileInputStream objectFile = null;
    DataInputStream objectStream = null;

    int addr;
    boolean finished = false;

    try {
      objectFile = new FileInputStream (objectName);
      objectStream = new DataInputStream (objectFile);

      addr = Machine.CB;
      while (!finished) {
        Machine.code[addr] = Instruction.read(objectStream);
        if (Machine.code[addr] == null)
          finished = true;
        else
          addr = addr + 1;
      }
      CT = addr;
      objectFile.close();
    } catch (FileNotFoundException s) {
      CT = CB;
      System.err.println ("Error opening object file: " + s);
    } catch (IOException s) {
      CT = CB;
      System.err.println ("Error reading object file: " + s);
    }
  }

  public static void main(String[] args) {
    System.out.println("********** TAM Interpreter (Java Version 2.1) **********");

    //final String EXAMPLES_DIR = "C:\\Users\\ruben\\OneDrive\\Escritorio\\SmartJava\\src\\SmartJavaTests\\Tests SMARTJAVA ULTIMATE";
    final String EXAMPLES_DIR = "C:\\Users\\Usuario\\Desktop\\Universidad VIA\\CMC Project\\SmartJava\\src\\SmartJavaTests\\Tests SMARTJAVA ULTIMATE";
    JFileChooser fc = new JFileChooser( EXAMPLES_DIR );
    fc.showOpenDialog( null );
    String sourceName = fc.getSelectedFile().getAbsolutePath();
    loadObjectProgram(sourceName);
    if (CT != CB) {
      interpretProgram();
      showStatus();
    }
  }
}
