package draft.java.file;

import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import java.net.URL;
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


    public static _files of( _file..._fs ){
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

    public List<_file> list() {
        return this.files;
    }

    public List<_file> list(Predicate<? super _file>_fileMatchFn ){
        return this.files.stream()
                .filter( _fileMatchFn ).collect( Collectors.toList() );
    }

    public List<String> listNames(){
        List<String> names = new ArrayList<>();
        this.files.forEach( f -> names.add( f.getName() ) );
        return names;
    }

    public List<URL> listUrls(){
        List<URL> urls = new ArrayList<>();
        this.files.forEach( f -> urls.add( f.url ) );
        return urls;
    }

    public boolean isEmpty() {
        return files.isEmpty();
    }

    public int size() {
        return files.size();
    }

    public _files forEach(Consumer<_file> _fileConsumer ){
        this.files.forEach(_fileConsumer);
        return this;
    }

    public _file get(String fileName ) {
        for( int i = 0; i < files.size(); i++ ) {
            if( this.files.get( i ).is( fileName ) ) {
                return this.files.get( i );
            }
        }
        return null;
    }

    public _file getFile(String filePath, String relativeName ){
        if( filePath == null ){
            filePath ="";
        }
        if( filePath.length() > 0) {
            if( !filePath.endsWith( "/" ) ){
                filePath = filePath + "/";
            }
        }
        for( int i = 0; i < files.size(); i++ ) {
            _file _f = this.files.get( i );
            if( _f.filePath.equals(filePath)
                    && _f.relativeName.equals(relativeName ) ) {
                return this.files.get( i );
            }
        }
        return null;
    }

    public List<_file> files = new ArrayList<>();

    public _files() {
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

        add( _file.of( filePath, relativeName, data ) );
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
        add( _file.of( filePath, relativeName, linesOfText ) );
        return this;
    }

    public _files add(_file... _fs ) {
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
     */
    public FileObject reserveFile( String filePath, String relativeName ) {

        _file _f = new _file( filePath, relativeName, new byte[0] );
        this.add( _f  );
        return _f;
    }
}