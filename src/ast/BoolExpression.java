/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;

public class BoolExpression
	extends Expression
{
	public Identifier name;
	public BoolDeclaration decl;
	
	public BoolExpression( Identifier name )
	{
		this.name = name;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitBoolExpression( this, arg );
	}
}