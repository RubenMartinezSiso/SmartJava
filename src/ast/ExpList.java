/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;


import java.util.*;


public class ExpList
	extends AST
{
	public Vector<Expression> exp = new Vector<Expression>();

	public Object visit( Visitor v, Object arg )
	{
		return v.visitExpList( this, arg );
	}
}