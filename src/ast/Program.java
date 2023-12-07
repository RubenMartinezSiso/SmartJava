/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
 
package ast;


public class Program
	extends AST
{
	public Block block;
	
	
	public Program( Block block )
	{
		this.block = block;
	}

	public Object visit( Visitor v, Object arg )
	{
		return v.visitProgram( this, arg );
	}
}