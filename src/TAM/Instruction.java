/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package TAM;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

public class Instruction {

  public Instruction() {
    op = 0;
    r = 0;
    n = 0;
    d = 0;
  }

  // Java has no type synonyms, so the following representations are
  // assumed:
  //
  //  type
  //    OpCode = 0..15;  {4 bits unsigned}
  //    Length = 0..255;  {8 bits unsigned}
  //    Operand = -32767..+32767;  {16 bits signed}

  // Represents TAM instructions.
  public int op; // OpCode
  public int r;  // RegisterNumber
  public int n;  // Length
  public int d;  // Operand

  public void write(DataOutputStream output) throws IOException {
    output.writeInt (op);
    output.writeInt (r);
    output.writeInt (n);
    output.writeInt (d);
  }

  public static Instruction read(DataInputStream input) throws IOException {
    Instruction inst = new Instruction();
    try {
      inst.op = input.readInt();
      inst.r = input.readInt();
      inst.n = input.readInt();
      inst.d = input.readInt();
      return inst;
    } catch (EOFException s) {
      return null;
    }
  }
}
