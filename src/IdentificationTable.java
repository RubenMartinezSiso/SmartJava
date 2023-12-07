/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/


import ast.*;

import java.util.*;


public class IdentificationTable
{
	private Vector<IdEntry> table = new Vector<IdEntry>();
	private int level = 0;
	
	
	public IdentificationTable()
	{
	}
	
	
	public void enter( String id, Declaration attr )
	{
		IdEntry entry = find( id );
		
		if( entry != null && entry.level == level )
			System.out.println( id + " declared twice" );
		else
			table.add( new IdEntry( level, id, attr ) );
	}
	
	
	public Declaration retrieve( String id )
	{
		IdEntry entry = find( id );
		
		if( entry != null )
			return entry.attr;
		else
			return null;
	}
	
	
	public void openScope()
	{
		++level;
	}
	
	
	public void closeScope()
	{
		int pos = table.size() - 1;
		while( pos >= 0 && table.get(pos).level == level ) {
			table.remove( pos );
			pos--;
		}
		
		level--;
	}
	
	
	private IdEntry find( String id )
	{
		for( int i = table.size() - 1; i >= 0; i-- )
			if( table.get(i).id.equals( id ) )
				return table.get(i);
				
		return null;
	}
}