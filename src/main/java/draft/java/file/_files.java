package draft.java.file;

import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A "virtual" root directory storing in memory {@link _file}s
 *
 * An implementation of a {@link JavaFileManager.Location}
 * for any Java tool (i.e. the javac compiler) to "write" to a
 * virtual Directory
 * <UL>
 *  <LI>HTML files
 *  <LI>XML files
 *  <LI>META-INF files
 * </UL>
 * and not writing to disk
 *
 * @author Eric DeFazio
 */
public final class _files implements JavaFileManager.Location {

    public static final JavaFileManager.Location LOCATION =
            new JavaFileManager.Location() {
                public String getName() {
                    return "Files";
                }
                public boolean isOutputLocation() {
                    return true;
                }
            };

    public static _files of( _f..._fs ){
        _files _ffs = new _files();
        _ffs.add(_fs );
        return _ffs;
    }

    @Override
    public String getName() {
        return "Files";
    }

    @Override
    public boolean isOutputLocation() {
        return true;
    }

    public List<_f> list() {
        return this.files;
    }

    public List<_f> list(Predicate<_f>_fileMatchFn ){
        return this.files.stream()
                .filter( _fileMatchFn ).collect( Collectors.toList() );
    }

    public List<String> listNames(){
        List<String> names = new ArrayList<>();
        this.files.forEach( f -> names.add( f.getName() ) );
        return names;
    }

    public boolean isEmpty() {
        return files.isEmpty();
    }

    public int size() {
        return files.size();
    }

    public _files forEach(Consumer<_f> _fileConsumer ){
        this.files.forEach(_fileConsumer);
        return this;
    }

    public _f get(String fileName ) {
        for( int i = 0; i < files.size(); i++ ) {
            if( this.files.get( i ).is( fileName ) ) {
                return this.files.get( i );
            }
        }
        return null;
    }

    public _f getFile(String filePath, String relativeName ){
        Path p = Paths.get(filePath, relativeName);
        String fname = p.toString();
        if( filePath == null ){
            filePath ="";
        }
        if( filePath.length() > 0) {
            if( !filePath.endsWith( "/" ) ){
                filePath = filePath + "/";
            }
        }
        for( int i = 0; i < files.size(); i++ ) {
            _f _f = this.files.get( i );
            if( _f.getName().equals(fname) ){
                return this.files.get( i );
            }
            /*
            if( _f.filePath.equals(filePath) && _f.relativeName.equals(relativeName)) {
                return this.files.get( i );
            }
            */
        }
        return null;
    }

    public List<_f> files = new ArrayList<>();

    public _files() {
    }

    public _files add(Path filePath, byte[] data ) {
        add( _f.of( filePath, data ) );
        return this;
    }
    
    /**
     * Adds a binary file based on the path( could be null or blank for the base path)
     *
     * i.e.
     * _files _fs = new _files();
     * _fs.accept( "", "RootFile.txt", "this is some text);
     *
     * @param filePath the path to the file (i.e. "" or null or "/temp/"
     * @param relativeName relative NAME of the file
     * @param data the bytes of data
     * @return
     */
    public _files add(String filePath, String relativeName, byte[] data ) {

        add( _f.of( Paths.get(filePath), Paths.get(relativeName), data ) );
        return this;
    }

    /**
     * Adds a text file based on the path( could be null or blank for the base path)
     * <PRE>
     * i.e.
     * _files _fs = new _files();
     * _fs.accept( "", "RootFile.txt", "first line of text", "second line of text");
     *
     * //creates a file on the root path named "rootFile.txt" with contents:
     * this is the first line of text
     * this is the second line of text
     *
     *
     * _files _fs = new _files();
     * _fs.accept( "/path/to/", "NestedFile.txt", "first line", "second line");
     *
     * //creates a file at "path/to/NestedFile.text with contents:
     * first line
     * second line
     * </PRE>
     *
     * @param filePath
     * @param relativeName
     * @param linesOfText
     * @return
     */
    public _files add(String filePath, String relativeName, String... linesOfText ) {
        //add( _file.of( filePath, relativeName, linesOfText ) );
        
        add( _f.of( Paths.get( filePath), Paths.get( relativeName), linesOfText ) );
        return this;
    }

    public _files add(_f... _fs ) {
        this.files.addAll( Arrays.asList( _fs ) );
        return this;
    }

    @Override
    public String toString(){
        return "_files ("+listNames()+")" + hashCode();
    }

    /**
     * reserves and returns an "AdHoc" file that can be written to
     * (for use during Java tool processing, i.e. annotation processing)
     *
     * @param filePath the fully qualified path NAME to the file (i.e.
     * "META-INF\")
     * @param relativeName the relative NAME ("services.xml")
     * @return a FileObject to use for writing to
     
    public FileObject reserveFile( String filePath, String relativeName ) {

        //_file _f = new _file( filePath, relativeName, new byte[0] );
        //_file _f = new _file( Paths.get(filePath), relativeName, new byte[0] );
        _f _f = new _f(Paths.get(filePath), Paths.get(relativeName), new byte[0]);
        this.add( _f  );
        return _f;
    }
    */ 
    
    public FileObject reserveFile( String relativePath ){
        return reserveFile( Paths.get(relativePath));
    }
    
    public FileObject reserveFile( Path relativePath ){
        _f _fi = _f.of(relativePath, new byte[0]);
        this.add( _fi  );
        return _fi;
    }
}