package draft.java.file;

import draft.java.io._ioException;

import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

/**
 * In memory version of a File
 * (i.e. for any type of ".java", ".class", ."txt", files that may be 
 * generated at runtime or represent a file on the filesystem)
 *
 * @author M. Eric DeFazio
 */
public final class _file implements _memoryFile<_file>, JavaFileObject {

    /** "virtual" path to the file i.e. "META-INF/" empty string "" is root path */
    public final Path filePath;

    /** relative NAME of the file after the path i.e. "index.html", "data.json"*/
    public final String relativeName;

    /** it COULD be binary... could be text... dunno, it's bytes **/
    public byte[] data;

    /** update timestamp in millis when the file was last changed*/
    public long lastUpdateTimeMills;

    /**
     * Create and return a "textual" _file at the filePath with the data provided
     * @param filePath
     * @param relativeName
     * @param data
     * @return
     */
    public static _file of(Path filePath, String relativeName, String... data ){    
        StringBuilder fileData = new StringBuilder();
        for( int i=0; i<data.length; i++ ){
            if( i > 0 ){
                fileData.append( System.lineSeparator());
            }
            fileData.append( data[i]);
        }
        return new _file( filePath, relativeName, fileData.toString().getBytes() );
    }

    /** Create and return a path containing the bytes
     * @param filePath
     * @param relativeName
     * @param data
     * @return
     */
    public static _file of(Path filePath, String relativeName, byte[] data ){    
        return new _file( filePath, relativeName, data );
    }

    public URL getURL(){
        try{
            return new URL( "file:\\\\" + this.filePath);
        }catch(Exception e){
            throw new _ioException("Invalid file path "+ filePath);
        }
    }

    @Override
    public String getName(){
        return filePath + relativeName;
    }

    @Override
    public int sizeInBytes(){
        return this.data.length;        
    }
    
    /**
     *
     * @param filePath "" (empty string) is root path, otherwise end with "/" like "user/" "tmp/data/"
     * NOTE: we Add the appropriate separator if one is not provided (to separate
     * the FilePath from the relativeName)... i.e.
     * _file.of( "temp", "data.txt", new byte[0]);
     *
     * will change the filePath "temp" to "temp\"
     *
     * @param filePath relative file NAME (i.e. "data.txt")
     * @param data the bytes of data (could be textual or binary encoded)
     */
    public _file(Path filePath, byte[] data ){
        if( filePath == null ){
            filePath = Paths.get("/");
        }
        this.filePath = filePath;

        this.relativeName = "";        
        this.data = data;        
    }
    
    public _file( Path filePath, String relativeName, byte[] data ){    
        if( filePath == null ){
            filePath = Paths.get("/");
        }
        this.filePath = filePath;

        this.relativeName = relativeName;
        this.data = data;       
    }

    /** 
     * is this file name the same as the file name provided
     * @param fileName
     * @return 
     */
    public boolean is( String fileName ){
        return (filePath + relativeName).equals( fileName );
    }

    @Override
    public String toString(){
        return "_file \"" + filePath + relativeName + "\" ("+hashCode()+")";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode( this.filePath );
        hash = 83 * hash + Objects.hashCode( this.relativeName );
        hash = 83 * hash + Arrays.hashCode( this.data );
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        final _file other = (_file)obj;
        if( !Objects.equals( this.filePath, other.filePath ) ) {
            return false;
        }
        if( !Objects.equals( this.relativeName, other.relativeName ) ) {
            return false;
        }
        if( !Arrays.equals( this.data, other.data ) ) {
            return false;
        }
        return true;
    }

    @Override
    public URI toUri() {
        return this.filePath.toUri();
    }

    @Override
    public InputStream openInputStream() {
        return new ByteArrayInputStream( this.data );
    }

    @Override
    public Reader openReader( boolean ignoreEncodingErrors ) {
        return new InputStreamReader( new ByteArrayInputStream( this.data ) );
    }

    @Override
    public CharSequence getCharContent( boolean ignoreEncodingErrors ) {
        return new String(this.data);
    }

    @Override
    public long getLastModified() {
        return this.lastUpdateTimeMills;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public OutputStream openOutputStream(){
        this.lastUpdateTimeMills = System.currentTimeMillis();
        return new _fileAfterCloseOutputStream( this );
    }

    @Override
    public Writer openWriter(){
        this.lastUpdateTimeMills = System.currentTimeMillis();
        return new _fileAfterCloseWriter( this );
    }

    @Override
    public Kind getKind() {
        if( this.isFileExtension(".java")){
            return Kind.SOURCE;
        }
        if( this.isFileExtension(".class")){
            return Kind.CLASS;
        }
        if( this.isFileExtension(".html") || this.isFileExtension(".htm")){
            return Kind.HTML;
        }
        return Kind.OTHER;
    }

    @Override
    public boolean isNameCompatible(String simpleName, Kind kind) {
        Kind thisKind = getKind();
        if( kind != Kind.OTHER && thisKind.equals(kind) ){
            String baseName = simpleName + kind.extension;
            return (baseName.equals(toUri().getPath())
                || toUri().getPath().endsWith("/" + baseName));
        }
        return false;
    }

    @Override
    public NestingKind getNestingKind() {
        if( getKind() == Kind.SOURCE ){
            return NestingKind.TOP_LEVEL;
        }
        return null;
    }

    @Override
    public Modifier getAccessLevel() {
        return null;
    }

    public static final class _fileAfterCloseOutputStream
            extends ActionAfterCloseOutputStream{

        private final _file _targetFile;

        public _fileAfterCloseOutputStream(_file _targetFile ){
            this._targetFile = _targetFile;
        }

        @Override
        public void afterClose(){
            this._targetFile.data = os.toByteArray();
            this._targetFile.lastUpdateTimeMills = System.currentTimeMillis();
        }
    }

    public static final class _fileAfterCloseWriter
            extends ActionAfterCloseWriter{

        private final _file _targetFile;

        public _fileAfterCloseWriter( _file _targetFile ){
            this._targetFile = _targetFile;
        }

        @Override
        public void afterClose() {

            this._targetFile.data = this.writer.toString().getBytes();
            this._targetFile.lastUpdateTimeMills = System.currentTimeMillis();
        }
    }
}
