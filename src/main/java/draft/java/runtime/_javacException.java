package draft.java.runtime;

import draft.DraftException;
import draft.java.file._javaFile;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.io.BufferedReader;
import java.util.Collection;
import java.util.List;

/**
 * Exception from running the Javac compiler on Java source to Class
 * at runtime. This class attempts to read the Diagnostics returned from
 * the Javac process to return an appropriate stack trace.
 *
 * @author M. Eric DeFazio
 */
public class _javacException extends DraftException {

    private static final String N = "\r\n";

    private static final long serialVersionUID = 1L;

    public final List<Diagnostic<? extends JavaFileObject>> diagnostics;

    public static String composeStackTrace( DiagnosticCollector<JavaFileObject> diagnosticsCollector ) {
        List<Diagnostic<? extends JavaFileObject>> diagnostics
                = diagnosticsCollector.getDiagnostics();

        StringBuilder sb = new StringBuilder();

        for( int i = 0; i < diagnostics.size(); i++ ) {
            Diagnostic<? extends JavaFileObject> d = diagnostics.get( i );

            if( d.getKind() == Diagnostic.Kind.NOTE || d.getKind() == Diagnostic.Kind.WARNING ) {
                continue;
            }
            sb.append( d.getMessage( null ) );

            if( !(d.getSource() instanceof _javaFile) ) {   //the source that originated the error is not available
                return sb.toString();
            }
            String className = ((_javaFile)d.getSource()).type.getFullName();
            sb.append( N );
            sb.append( className ); //javaCode.className );
            sb.append( ".class" );
            if( d.getLineNumber() >= 0 ) {
                sb.append( " at line" );
                sb.append( "[ " );
                sb.append( d.getLineNumber() );
                sb.append( " ]: " );
                try {
                    BufferedReader br = new BufferedReader(d.getSource().openReader(true));

                    for( int l = 0; l < d.getLineNumber() - 1; l++ ) {
                        br.readLine();
                    }
                    String theLine = br.readLine();

                    sb.append( theLine );
                    sb.append( "\r\n" );
                }catch( Exception e ) {
                    //swallow this one
                }
            }
        }
        return sb.toString();
    }

    public _javacException(DiagnosticCollector<JavaFileObject> diagnostics ) {
        super( "Failed Compilation of workspace" + N
                + composeStackTrace( //(Collection)javaCode,
                diagnostics ) );

        this.diagnostics = diagnostics.getDiagnostics();
    }

    public _javacException(Collection<_javaFile> javaCode, DiagnosticCollector<JavaFileObject> diagnostics ) {
        super( "Failed Compilation of workspace" + N
                + composeStackTrace(
                diagnostics ) );

        this.diagnostics = diagnostics.getDiagnostics();
    }

    public _javacException(String message ) {
        super( message );
        this.diagnostics = null;
    }

    public _javacException(String message, Throwable e ) {
        super( message, e );
        this.diagnostics = null;
    }
}
