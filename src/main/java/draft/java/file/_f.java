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
public final class _f implements _memoryFile<_f>, JavaFileObject {

    /**
     * If the _f  is associated/ read in from the file system, it can have 
     * a basePath... 
     * <PRE>
     * i.e. if we read this _f from a file:
     * "file:///C:/dev/projects/MyProject/src/main/java/com/myproj/MyJavaFile.java"
     * 
     * we have a base path of:
     * "file:///C:/dev/projects/MyProject/src/main/java/"
     * 
     * and a relative path of:
     * "com/myproj/MyJavaFile.java"
     * </PRE>
     * 
     * NOTE: basePath CAN BE NULL
     * if the _f is created in memory as apposed to
     * read in from a local file, we may only have a relativePath:
     * "com/myproj/MyJavaFile.java"
     * </PRE>
     */
    public Path basePath;
    
    /**
     * A Non Null path relative to the base path where the file is located
     *  <PRE>
     * i.e. if we read this _f from a file:
     * "file:///C:/dev/projects/MyProject/src/main/java/com/myproj/MyJavaFile.java"
     * 
     * we have a base path of:
     * "file:///C:/dev/projects/MyProject/src/main/java/"
     * 
     * and a relative path of:
     * "com/myproj/MyJavaFile.java"
     * </PRE>
     */
    public Path relativePath;

    /** it COULD be binary... could be text... dunno, it's bytes **/
    public byte[] data;

    /** update timestamp in millis when the file was last changed*/
    public long lastUpdateTimeMills;

    /**
     * Create and return a "textual" _file at the filePath with the data provided
     * @param basePath
     * @param relativePath
     * @param data
     * @return
     */
    public static _f of(Path basePath, Path relativePath, String... data ){    
        StringBuilder fileData = new StringBuilder();
        for( int i=0; i<data.length; i++ ){
            if( i > 0 ){
                fileData.append( System.lineSeparator());
            }
            fileData.append( data[i]);
        }
        return new _f( basePath, relativePath, fileData.toString().getBytes() );
    }

    public static _f of(Path relativePath, byte[] data ){    
        return new _f( null, relativePath, data );
    }
    
    /** Create and return a path containing the bytes
     * @param filePath
     * @param relativeName
     * @param data
     * @return
     */
    public static _f of(Path basePath, Path relativePath, byte[] data ){    
        return new _f( basePath, relativePath, data );
    }

    public URL getURL(){
        if( this.basePath == null ){
            try{
                return new URL( "file:\\\\" + this.relativePath);
            }catch(Exception e){
                throw new _ioException( "Invalid relative path "+ relativePath);
            }    
        }        
        try {
            return new URL("file:\\\\" + Paths.get(basePath.toString(), this.relativePath.toString()) );
        }catch(Exception e){
            throw new _ioException( "Invalid relative path "+ relativePath);
        }
    }

    @Override
    public String getName(){
        if( basePath == null ){
            return this.relativePath.toString();
        }
        return Paths.get(basePath.toString(), this.relativePath.toString()).toString();
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
     * @param basePath the base directory path where the file was read (NULLABLE) i.e. 
     * "C://temp//Myproj//src//main//java"
     * @param relativePath relative path to the file (NOT NULL) i.e. "/java/util/Map.java")
     * @param data the bytes of data (could be textual or binary encoded)
     */
    public _f(Path basePath, Path relativePath, byte[] data ){
        
        this.basePath = basePath;    
        if( basePath != null ){
            try{
                this.relativePath = basePath.relativize(relativePath);
            } catch(Exception e){
                this.relativePath = relativePath;
            }
        } else{
            this.relativePath = relativePath;
        }      
        this.data = data;        
    }
    
    /** 
     * is this file name the same as the file name provided
     * @param fileName
     * @return 
     */
    public boolean is( String fileName ){
        /** TODO CHERCK THIS   **/
        return Paths.get(fileName).equals( this.relativePath)
                || Paths.get( getName() ).equals( Paths.get(fileName));
        //return (filePath + relativeName).equals( fileName );
    }

    @Override
    public String toString(){
        return "_file \"" + toUri() + "\" ("+hashCode()+")";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode( this.basePath );
        hash = 83 * hash + Objects.hashCode( this.relativePath );
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
        final _f other = (_f)obj;
        if( !Objects.equals( this.basePath, other.basePath ) ) {
            return false;
        }
        if( !Objects.equals( this.relativePath, other.relativePath ) ) {
            return false;
        }
        if( !Arrays.equals( this.data, other.data ) ) {
            return false;
        }
        return true;
    }

    @Override
    public URI toUri() {
        return Paths.get( this.getName()).toUri();
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

        private final _f _targetFile;

        public _fileAfterCloseOutputStream(_f _targetFile ){
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

        private final _f _targetFile;

        public _fileAfterCloseWriter( _f _targetFile ){
            this._targetFile = _targetFile;
        }

        @Override
        public void afterClose() {

            this._targetFile.data = this.writer.toString().getBytes();
            this._targetFile.lastUpdateTimeMills = System.currentTimeMillis();
        }
    }
}
