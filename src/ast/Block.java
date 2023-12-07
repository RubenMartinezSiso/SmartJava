/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;


public class Block
	extends AST
{
	public Declarations decs;
	public Statements stats;
	
	
	public Block( Declarations decs, Statements stats )
	{
		this.decs = decs;
		this.stats = stats;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitBlock( this, arg );
	}
}