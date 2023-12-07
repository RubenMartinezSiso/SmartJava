/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/


public class Token
{
	public TokenKind kind;
	public String spelling;
	
	
	public Token( TokenKind kind, String spelling )
	{
		this.kind = kind;
		this.spelling = spelling;
		
		if( kind == TokenKind.IDENTIFIER )
			for( TokenKind tk: KEYWORDS )
				if( spelling.equals( tk.getSpelling() ) ) {
					this.kind = tk;
					break;
				}
	}
	
	
	public boolean isAssignOperator()
	{
		if( kind == TokenKind.ASIGOPERATOR )
			return containsOperator( spelling, ASSIGNOPS );
		else
			return false;
	}
	
	public boolean isAddOperator()
	{
		if( kind == TokenKind.ADDOPERATOR )
			return containsOperator( spelling, ADDOPS );
		else
			return false;
	}
	
	public boolean isMulOperator()
	{
		if( kind == TokenKind.MULOPERATOR )
			return containsOperator( spelling, MULOPS );
		else
			return false;
	}
	
	private boolean containsOperator( String spelling, String OPS[] )
	{
		for( int i = 0; i < OPS.length; ++i )
			if( spelling.equals( OPS[i] ) )
				return true;
				
		return false;
	}
	
	private static final TokenKind[] KEYWORDS = { TokenKind.DECL, TokenKind.ENDFOR, TokenKind.ENDWHILE, TokenKind.ELSE, TokenKind.ENDIF, TokenKind.FUNC, TokenKind.IF,
		TokenKind.START, TokenKind.END, TokenKind.RETURN, TokenKind.PRINT, TokenKind.INT, TokenKind.BOOL, TokenKind.WHILE, TokenKind.FOR, TokenKind.READ, TokenKind.ARRAY };
	
	
	private static final String ASSIGNOPS[] =
	{
		":=",
	};

	
	private static final String ADDOPS[] =
	{
		"+",
		"-",
	};

	
	private static final String MULOPS[] =
	{
		"*",
		"/",
		"%",
	};
}