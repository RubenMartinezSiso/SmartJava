/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/


public enum TokenKind
{
	IDENTIFIER,
	INTEGERLITERAL,
	BOOLEANVALUE,
	ADDOPERATOR,
	MULOPERATOR,
	ASIGOPERATOR,
	
	DECL( "decl" ),
	INT("int"),
	BOOL("bool"),
	FUNC( "func" ),
	FOR ("for"),
	ENDFOR("endFor"),
	WHILE( "while" ),
	ENDWHILE("endWhile"),
	START( "start" ),
	END( "end" ),
	IF( "if" ),
	ENDIF( "endIf" ),
	ELSE( "else" ),
	PRINT( "print" ),
	RETURN( "return" ),
	READ("read" ),

	COMMA( "," ),
	DOT( "." ),
	LEFTPARAN( "(" ),
	RIGHTPARAN( ")" ),

	ARRAY("array" ),
	LEFTBR( "[" ),
	RIGHTBR( "]" ),

	EOT,
	
	ERROR;
	
	
	private String spelling = null;
	
	
	private TokenKind()
	{
	}
	
	
	private TokenKind( String spelling )
	{
		this.spelling = spelling;
	}
	
	
	public String getSpelling()
	{
		return spelling;
	}
}