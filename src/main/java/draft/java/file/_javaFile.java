package draft.java.file;

import draft.java.*;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import java.io.*;
import java.net.URI;
import java.nio.CharBuffer;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Adapts a {@link _type} to the {@link JavaFileObject}
 * API but instead of a physical file on disk resides as a String in memory
 *
 * "Adapt" in memory ( _class, _enum, ... models) the export the
 * {@link javax.tools.JavaCompiler} API handling ".java" files read in on
 * the hard disk
 *
 * ..this makes metaprogramming or extreme late program modification (via
 * ANNOTATIONS, javac compiler Plugins) easier (without having export resort export
 * bytecode modification.
 *
 * @author Eric
 */
public final class _javaFile implements JavaFileObject, _memoryFile<_javaFile> {

    /**
     * If the _javaFile is associated/ read in from the file system, it can have 
     * a filePath... 
     * i.e. "file:///C:/dev/projects/MyProject/src/main/java/com/myproj/MyJavaFile.java"
     * 
     * Note: we have a root path of:
     * "file:///C:/dev/projects/MyProject/src/main/java/"
     * on initialization because it is possible that the package/path "com.myproj"
     * or the type name "MyJavaFile.java" could change
     */
    public Path basePath;
    
    /**
     * the model the class and file contents export be compiled
     * NOTE: the package/path is derived from the _types package
     */
    public _type type;

    /**
     * TODO, I should be able export DERIVE this of TYPE timestamp when
     * this was last modified
     */
    protected long lastUpdateTimeMillis = -1L;
    
    public static _javaFile of( Path basePath, _type _t ){
        return new _javaFile( basePath, _t);
    }
    
    /**
     * 
     * @param _type
     * @return 
     */
    public static _javaFile of(_type _type ) {
        return new _javaFile( _type );
    }
    
    private _javaFile(Path basePath, _type _t){
        this.basePath = basePath;
        this.type = _t;
    }
    
    @Override
    public int sizeInBytes(){
        return this.toString().getBytes().length;
    }
    
    /**
     * Use this constructor when we have a fully formed {@link _type}
     * (export read or _2_template from later)
     * @param _t an instance of a TYPE
     */
    private _javaFile( _type _t ) {
        this.type = _t;
        this.lastUpdateTimeMillis = System.currentTimeMillis();
    }

    /**
     * This constructor is used when we derive _project
     * (when classes are GENERATED at COMPILE-TIME (i.e.via annotation processors))
     *
     * @param fullClassName
     */
    public _javaFile( String fullClassName ){
        this.lastUpdateTimeMillis = System.currentTimeMillis();
        this.type = _class.of(fullClassName);
        //no TYPE as of yet (we are probably reserving the file)
    }

    /**
     * Copy constructor, build a new _javaFile from this prototype file
     * and return
     * @param prototype  the prototype _javaFile
     */
    public _javaFile( _javaFile prototype ){
        this.lastUpdateTimeMillis = prototype.lastUpdateTimeMillis;
        this.type = (_type)_java.of( prototype.type.astCompilationUnit().clone());
        this.basePath = prototype.basePath;
    }

    /**
     * Returns the mutable TYPE instance or null if the TYPE has not been fully created
     * @return the TYPE
     */
    public _type get_type() {
        return this.type;
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
        final _javaFile other = (_javaFile)obj;
        if( !Objects.equals(this.type, other.type ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hash( this.type );
        return hash;
    }

    @Override
    public Kind getKind() {
        return Kind.SOURCE;
    }

    /**
     * Verify the simpleName 
     * (i.e. "Map", as apposed to the fully qualified name "java.util.Map") 
     * And type is compatible, i.e.
     * 
     * @param simpleName
     * @param kind
     * @return 
     */
    @Override
    public boolean isNameCompatible( String simpleName, Kind kind ) {
        String baseName = simpleName + kind.extension;
        return kind.equals( getKind() )
                && (baseName.equals( toUri().getPath() )
                || toUri().getPath().endsWith( "/" + baseName ));
    }

    @Override
    public NestingKind getNestingKind() {
        return NestingKind.TOP_LEVEL;
    }

    @Override
    public Modifier getAccessLevel() {
        return null;
    }

    @Override
    public URI toUri() {
        if( this.basePath == null ){
            return URI.create("file:///" + this.type.getFullName().replace('.','/')
                + Kind.SOURCE.extension );
        }
        return URI.create(this.basePath.toUri().toString() 
                + this.type.getFullName().replace('.','/')+ Kind.SOURCE.extension);
    }

    @Override
    public String toString() {
        return "_javaFile: " + toUri();
    }

    /**
     * returns the file name for the type (packages separated by / instead of .)
     * and prefixed by the base path if present with the ".java" file extension
     * <PRE>
     * i.e. "java.util.Map" returns "java/util/Map.java"
     * i.e. "aaaa.bbbb.C" might be "C:/Users/Eric/Tmp/aaaa/bbbb/C.java" if the
     * basePath is "C:/Users/Eric/Tmp"
     * </PRE>
     */
    @Override
    public String getName() {
        return toUri().getPath();
    }

    @Override
    public InputStream openInputStream()
            throws IOException {
        //you cant read from a _javaFile that is initialized but no TYPE exists yet
        if( type == null ){
            throw new IOException("_javaFile \""
                +this.type.getFullName()
                +"\" _type and has not been written" );
        }
        return new ByteArrayInputStream( this.type.toString().getBytes() );        
    }

    @Override
    public OutputStream openOutputStream() {
        return new _typeAfterCloseOutputStream( this );
    }

    @Override
    public Reader openReader( boolean ignoreEncodingErrors )
            throws IOException {
        CharSequence charContent = getCharContent( ignoreEncodingErrors );
        if( charContent == null ) {
            throw new UnsupportedOperationException();
        }
        if( charContent instanceof CharBuffer ) {
            CharBuffer buffer = (CharBuffer)charContent;
            if( buffer.hasArray() ) {
                return new CharArrayReader( buffer.array() );
            }
        }
        return new StringReader( charContent.toString() );
    }

    @Override
    public CharSequence getCharContent( boolean ignoreEncodingErrors )
            throws IOException {
        if( this.type == null ){
            throw new IOException("TYPE \""+this.type.getFullName()+"\" has not been  written");
        }
        return type.toString();        
    }

    public _javaFile copy(){
        return new _javaFile( this );
    }

    @Override
    public Writer openWriter() {
        return new _typeAfterCloseWriter( this );
    }

    @Override
    public long getLastModified() {
        return this.lastUpdateTimeMillis;
    }

    @Override
    public boolean delete() {
        return false; //lets say you cant delete it...?
    }

    /**'
     *
     */
    public static final class _typeAfterCloseOutputStream
            extends ActionAfterCloseOutputStream{

        private draft.java.file._javaFile _javaFile;

        public _typeAfterCloseOutputStream( draft.java.file._javaFile _javaFile ){
            this._javaFile = _javaFile;
        }

        @Override
        public void afterClose(){
            _javaFile.type = _type.of( new String(os.toByteArray()));
            _javaFile.lastUpdateTimeMillis = System.currentTimeMillis();
        }
    }

    public static final class _typeAfterCloseWriter
            extends ActionAfterCloseWriter {

        private final draft.java.file._javaFile _javaFile;

        public _typeAfterCloseWriter( draft.java.file._javaFile _javaFile ){
            this._javaFile = _javaFile;
        }

        @Override
        public void afterClose(){
            _javaFile.type = _type.of( this.writer.toString() );
            _javaFile.lastUpdateTimeMillis = System.currentTimeMillis();
        }
    }
}
