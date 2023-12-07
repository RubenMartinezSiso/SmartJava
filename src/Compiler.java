/*
* **************************************************************
* Compiler Construction
* SmartJava Assignment
* Dand Marba Sera | Lucía Cárdenas Palacios | Rubén Martínez Sisó
* **************************************************************
*/
  
import ast.*;
import javax.swing.*;

public class Compiler
{
	//private static final String EXAMPLES_DIR = "C:\\Users\\ruben\\OneDrive\\Escritorio\\SmartJava\\src\\SmartJavaTests";
	private static final String EXAMPLES_DIR = "C:\\Users\\Usuario\\Desktop\\Universidad VIA\\CMC Project\\SmartJava\\src\\SmartJavaTests\\Tests SMARTJAVA ULTIMATE";
	
	
	public static void main( String args[] )
	{
		JFileChooser fc = new JFileChooser( EXAMPLES_DIR );
		
		if( fc.showOpenDialog( null ) == JFileChooser.APPROVE_OPTION ) {
			String sourceName = fc.getSelectedFile().getAbsolutePath();
			
			SourceFile in = new SourceFile( sourceName );
			Scanner s = new Scanner( in );
			Parser p = new Parser( s );
			Checker c = new Checker();
			Encoder e = new Encoder();
		
			Program program = (Program) p.parseProgram();
			c.check( program );
			e.encode( program );
			
			String targetName;
			if( sourceName.endsWith( ".txt" ) )
				targetName = sourceName.substring( 0, sourceName.length() - 4 ) + ".tam";
			else
				targetName = sourceName + ".tam";
			
			e.saveTargetProgram( targetName );
		}
  	}
}