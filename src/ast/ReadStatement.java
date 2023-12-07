/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package ast;


public class ReadStatement
	extends Statement
{
	public Identifier readId;

	public IntDeclaration decl;

	public Integer value;
	
	public ReadStatement( Identifier readId )
	{
		this.readId = readId;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitReadStatement( this, arg );
	}
}
