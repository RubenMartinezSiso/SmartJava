/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

package ast;

public class Address
{
	public int level;
	public int displacement;
	
	
	public Address()
	{
		level = 0;
		displacement = 0;
	}
	
	
	public Address( int level, int displacement )
	{
		this.level = level;
		this.displacement = displacement;
	}
	
	
	public Address( Address a, int increment )
	{
		this.level = a.level;
		this.displacement = a.displacement + increment;
	}
	
	
	public Address( Address a )
	{
		this.level = a.level + 1;
		this.displacement = 0;
	}
	
	
	public String toString()
	{
		return "level=" + level + " displacement=" + displacement;
	}
}