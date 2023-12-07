/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;

public class IntExpression
	extends Expression
{
	public Identifier name;
	public IntDeclaration decl;
	
	public IntExpression( Identifier name )
	{
		this.name = name;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitIntExpression( this, arg );
	}
}