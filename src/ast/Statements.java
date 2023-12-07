/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package ast;


import java.util.*;


public class Statements
	extends AST
{
	public Vector<Statement> stat = new Vector<Statement>();

	public Object visit( Visitor v, Object arg )
	{
		return v.visitStatements( this, arg );
	}
}