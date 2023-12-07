/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package ast;

import java.util.*;


public class Declarations
	extends AST
{
	public Vector<Declaration> dec = new Vector<Declaration>();

	public Object visit( Visitor v, Object arg )
	{
		return v.visitDeclarations( this, arg );
	}
}