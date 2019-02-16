package draft;

import java.io.*;
import java.util.*;

/**
 * Utility functions for handling Textual data
 *
 * @author Eric
 */
public final class Text {

    /**
     * Create a single String (with LineSeparators between each entry) based on an Array of Strings
     * @param code the strings (each entry is on it's own line)
     * @return a Single String representing the Strings
     */
    public static String combine( String... code ) {
        StringBuilder sb = new StringBuilder();
        for( String code1 : code ) {
            sb.append( code1 );
            sb.append( System.lineSeparator() );
        }
        return sb.toString().trim();
    }

    /**
     *
     * @param string an un-indented String with multiple lines
     * @return a Single string where each line is indented 4 spaces
     */
    public static String indent( String string ) {
        List<String> ls = lines( string );

        StringBuilder sb = new StringBuilder();

        for (String l : ls) {
            sb.append("    ");
            sb.append(l);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Breaks the single String into an array of Lines
     * @param string a single String
     * @return
     */
    public static List<String> lines( String string ) {
        if( string == null ) {
            return Collections.EMPTY_LIST;
        }
        BufferedReader br = new BufferedReader(
                new StringReader( string ) );

        List<String> strLine = new ArrayList<>();

        try {
            String line = br.readLine();
            while( line != null ) {
                strLine.add( line );
                line = br.readLine();
            }
            return strLine;
        }
        catch( IOException e ) {
            throw new DraftException( "Error formatting Lines" );
        }
    }
}
