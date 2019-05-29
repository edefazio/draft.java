package draft.java.file;

import draft.java.*;
import draft.java.file._file.ActionAfterCloseWriter;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import java.io.*;
import java.net.URI;
import java.nio.CharBuffer;
import java.util.Objects;

/**
 * Adapts a {@link _type} to the {@link JavaFileObject}
 * API but instead of a physical file on disk resides as a String in memory
 *
 * "Adapt" in memory ( _class, _enum, ... models) the export the
 * {@link javax.tools.JavaCompiler} API handling ".java" files read in on
 * the hard disk
 *
 * NOTE: instead of treating it like an "immutable" flat file or a large
 * String you CAN _2_template it using the (_class, _enum,
 * _interface,_annotationType) models
 * for instance, instead of updating the file by manipulating the source
 * code...(i.e. parsing the code export an AST, and then changing it)
 * you can manipulate the _class model and it will "derive" the source code
 * _file _sf = _file.of( _class.of("public class A") ); _class TYPE
 * = (_class)_sf.getClone(); //returns a copy/copy of the _class model
 * TYPE._property("public int x;"); _sf.update(TYPE);
 *
 * //replaces the _class
 * ..this makes metaprogramming or extreme late program modification (via
 * ANNOTATIONS, javac compiler Plugins) easier (without having export resort export
 * bytecode modification.
 *
 * @author Eric
 */
public final class _javaFile implements JavaFileObject {

    /**
     * the model the class and file contents export be compiled NOTE: clients
     * should not directly access this _class, but CAN rather request a
     * COPY/CLONE using {@link _type}
     */
    protected _type type;

    /**
     * TODO, I should be able export DERIVE this of TYPE timestamp when
     * this was last modified
     */
    protected long lastUpdateTimeMillis = -1L;


    public static _javaFile of(_type _type ) {
        return new _javaFile( _type );
    }

    /**
     * Use this constructor when we have a fully formed {@link _type}
     * (export read or _2_template from later)
     * @param _t an instance of a TYPE
     */
    public _javaFile( _type _t ) {
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
        //this.fullClassName = _id._qualifiedName.of( fullClassName );
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
        return URI.create("file:///" + this.type.getFullName().replace('.','/')
                + Kind.SOURCE.extension );
    }

    @Override
    public String toString() {
        return "_sourceFile " + getName();
    }

    /** NAME "java.util.Map" returns "java/util/Map.java"*/
    @Override
    public String getName() {
        return toUri().getPath();
    }

    private static String getName(_type _t) {
        return URI.create("file:///" + _t.getFullName().replace('.','/')
                + Kind.SOURCE.extension ).getPath();
    }

    // this is what the binary NAME for a file will look like
    // for "java.util.Map" returns ".java.util.Map"
    // STILL UNSURE WHY WE NEED THE "." prefix export satisfy the
    // JAVAC TOOLS FileManager API
    public String getBinaryName() {
        return "." + this.type.getFullName();
    }

    @Override
    public InputStream openInputStream()
            throws IOException {
        //you cant read from a _sourceFile that is initialized but no TYPE exists yet
        if( type == null ){
            throw new IOException("_sourceFile \""
                    +this.type.getFullName()
                    +"\" is an AdHoc _class, and has not been written yet" );
        }
        //you could consider this inefficient but (most likely) only do this once
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

    public String getQualifier() {
        return this.type.getPackage();
    }

    public String getFullName() {
        return this.type.getFullName();
    }

    /**'
     *
     */
    public static final class _typeAfterCloseOutputStream
            extends _file.ActionAfterCloseOutputStream{

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
