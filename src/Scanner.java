/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/

public class Scanner
{
	private SourceFile source;
	
	private char currentChar;
	private StringBuffer currentSpelling = new StringBuffer();
	
	
	public Scanner( SourceFile source )
	{
		this.source = source;
		
		currentChar = source.getSource();
	}
	
	
	private void takeIt()
	{
		currentSpelling.append( currentChar );
		currentChar = source.getSource();
	}
	
	
	private boolean isLetter( char c )
	{
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
	}
	
	
	private boolean isDigit( char c )
	{
		return c >= '0' && c <= '9';
	}
	
	
	private void scanSeparator()
	{
		switch( currentChar ) {
			case '#':
				takeIt();
				while( currentChar != SourceFile.EOL && currentChar != SourceFile.EOT )
					takeIt();
					
				if( currentChar == SourceFile.EOL )
					takeIt();
				break;
				
			case ' ': case '\n': case '\r': case '\t':
				takeIt();
				break;
		}
	}
	
	
	private TokenKind scanToken()
	{
		if( isLetter( currentChar ) ) {
			takeIt();
			while( isLetter( currentChar ) || isDigit( currentChar ) || (currentChar == '[') || (currentChar == ']') )
				takeIt();
			
			if ( ("true").contentEquals(currentSpelling) || ("false").contentEquals(currentSpelling)) {
				return TokenKind.BOOLEANVALUE;
			} else {
				return TokenKind.IDENTIFIER;
			}
			
		} else if( isDigit( currentChar ) ) {
			takeIt();
			while( isDigit( currentChar ) )
				takeIt();
				
			return TokenKind.INTEGERLITERAL;
			
		} switch( currentChar ) {
			case '+': case '-':
				takeIt();
				return TokenKind.ADDOPERATOR;
			case '*': case '/': case '%':
				takeIt();
				return TokenKind.MULOPERATOR;
				
			case ':':
				takeIt();
				if( currentChar == '=' ) {
					takeIt();
					return TokenKind.ASIGOPERATOR;
				} else
					return TokenKind.ERROR;
				
			case ',':
			takeIt();
			return TokenKind.COMMA;

			case '.':
				takeIt();
				return TokenKind.DOT;
				
			case '(':
				takeIt();
				return TokenKind.LEFTPARAN;
				
			case ')':
				takeIt();
				return TokenKind.RIGHTPARAN;

			case '[':
				takeIt();
				return TokenKind.LEFTBR;
			
			case ']':
				takeIt();
				return TokenKind.RIGHTBR;
				
			case SourceFile.EOT:
				return TokenKind.EOT;
				
			default:
				takeIt();
				return TokenKind.ERROR;
		}
	}
	
	
	public Token scan()
	{
		while( currentChar == '#' || currentChar == '\n' ||
		       currentChar == '\r' || currentChar == '\t' ||
		       currentChar == ' ' )
			scanSeparator();
			
		currentSpelling = new StringBuffer( "" );
		TokenKind kind = scanToken();
		
		return new Token( kind, new String( currentSpelling ) );
	}
}
