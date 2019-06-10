package draft.java.file;

import draft.java.file._file.CacheBytesOutputStream;
import draft.java.io._ioException;

import javax.tools.SimpleJavaFileObject;
import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.util.*;

/**
 * In memory container of a Java .class file (bytecodes) representing a Class
 *
 * Implementation of a {@link SimpleJavaFileObject} that maintains the Java
 * bytecode (binary) for a class "In-Memory" as a local
 * {@link CacheBytesOutputStream} (via a {@link ByteArrayOutputStream})
 *
 * @author M. Eric DeFazio
 */
public final class _classFile extends SimpleJavaFileObject  {

    /**
     * Create an EMPTY classFile which will house a class with a specific fully 
     * qualified type name
     * @param fullyQualifiedTypeName
     * @return 
     */
    public static final _classFile of(String fullyQualifiedTypeName){
        try{
            return new _classFile(fullyQualifiedTypeName);
        }catch(Exception e){
            throw new _ioException("Unable to create class file at \""+ fullyQualifiedTypeName+"\"", e);
        }
    }
    
    /**
     * 
     * @param fullyQualifiedTypeName
     * @param byteCodes
     * @return 
     */
    public static final _classFile of(String fullyQualifiedTypeName, byte[] byteCodes ){
        try{
            return new _classFile(fullyQualifiedTypeName, byteCodes);
        }catch(IOException | IllegalArgumentException | URISyntaxException e){
            throw new _ioException("Unable to create class file at \""+ fullyQualifiedTypeName+"\"", e);
        }
    }
    
    /**
     * Represents a classFile that is read in from the file system
     * 
     * @param basePath the base path from where the class file is read (i.e. "C:\temp\Myproj\target\classes")
     * @param fullyQualifiedTypeName the full class name (i.e. "aaaa.bbbb.C")
     * @param byteCodes
     * @return 
     */
    public static final _classFile of( Path basePath, String fullyQualifiedTypeName, byte[] byteCodes){
        try{
            return new _classFile(basePath, fullyQualifiedTypeName, byteCodes);
        }catch(Exception e){
            throw new _ioException("Unable to create class file at \""+ fullyQualifiedTypeName+"\"", e);
        }
    }
    
    /**
     * allows the classFile to be created and written ONCE, then cached as an
     * array of bytes to be read multiple times
     */
    private CacheBytesOutputStream cacheBytesOutputStream;

    /** the NAME of the package */
    private final String packageName;

    /** the name of the top level class */
    private final String className;

    /** 
     * Optional/ Nullable field used when the classFile is read in from the file 
     * system (for traceability) as opposed to generated in memory by the ad hoc 
     * javac compiler
     */
    public Path basePath;
    
     /**
     * Note: a classFile must retain "file"-path based syntax
     * i.e. "./com/path/ClassFile.class"
     * and also understand "java runtime" syntax
     * i.e. "com.path/ClassFile"
     *
     * @param _proto
     * @throws URISyntaxException
     */
    public _classFile( _classFile _proto ) throws URISyntaxException{
        super( _proto.toUri(), Kind.CLASS );
        
        this.packageName = _proto.packageName;
        this.className = _proto.className;
        this.basePath = _proto.basePath;

        //..call the copy constructor
        this.cacheBytesOutputStream = new CacheBytesOutputStream(
                _proto.cacheBytesOutputStream );
    }
    
    /**
     * Initialize an in memory Java Class for a given class Name
     *
     * @param className the full class NAME "io.varcode.MyValueObject"
     * @throws IllegalArgumentException
     * @throws URISyntaxException
     */
    private _classFile( String className )
            throws IllegalArgumentException, URISyntaxException {
        super( new URI( className.replace('.', '/') ), Kind.CLASS );
        int idx = className.lastIndexOf('.');
        if( idx > 0 ){
            this.packageName = className.substring(0, idx );
            this.className = className.substring(idx+1);
        } else{
            this.packageName = null;
            this.className = className;
        }
        this.cacheBytesOutputStream = new CacheBytesOutputStream( uri );
    }

    /**
     * Build and return a _classFile that represents a class loaded from the file system
     *
     * @param basePath the base directory that contains the file (i.e. "C:\temp\MyProj\src\main\java") 
     * @param className the NAME of the _class (i.e. "java.util.Map")
     * @param byteCodes the bytecodes
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    private _classFile( Path basePath, String className, byte[] byteCodes )
            throws IllegalArgumentException, URISyntaxException, IOException {

        super( new URI( "file:///" + basePath.toString().replace("\\", "/")+"/"+className.replace('.', '/')+".class" ), Kind.CLASS );
        this.basePath = basePath;
        int idx = className.lastIndexOf('.');
        if( idx > 0 ){
            this.packageName = className.substring(0, idx );
            this.className = className.substring(idx+1);
        } else{
            this.packageName = null;
            this.className = className;
        }
        this.cacheBytesOutputStream = new CacheBytesOutputStream( uri );
        this.cacheBytesOutputStream.write( byteCodes );
        this.cacheBytesOutputStream.flush();
        this.cacheBytesOutputStream.close();
    }
    
    /**
     * Build and return a pre-_1_build _classFile
     *
     * @param className the NAME of the _class
     * @param byteCodes the bytecodes
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    private _classFile( String className, byte[] byteCodes )
            throws IllegalArgumentException, URISyntaxException, IOException {

        super( new URI( className.replace('.', '/') ), Kind.CLASS );
        int idx = className.lastIndexOf('.');
        if( idx > 0 ){
            this.packageName = className.substring(0, idx );
            this.className = className.substring(idx+1);
        } else{
            this.packageName = null;
            this.className = className;
        }
        this.cacheBytesOutputStream = new CacheBytesOutputStream( uri );
        this.cacheBytesOutputStream.write( byteCodes );
        this.cacheBytesOutputStream.flush();
        this.cacheBytesOutputStream.close();
    }
    
    /** 
     * @return gets the package name, (i.e. "java.util") for the _type) 
     */
    public String getPackageName(){
        return this.packageName;
    }

    @Override
    public boolean equals( Object o ){
        if( !( o instanceof _classFile)){
            return false;
        }
        _classFile _cf = (_classFile)o;

        if( !Objects.equals( _cf.packageName, this.packageName )){
            return false;
        }
        if( !Objects.equals( _cf.className, this.className )){
            return false;
        }
        if( ! Objects.equals(_cf.basePath, this.basePath)){
            return false;
        }
        if( _cf.cacheBytesOutputStream.lastModifiedMillis
                != this.cacheBytesOutputStream.lastModifiedMillis){
            return false;
        }
        if( !Arrays.equals( _cf.cacheBytesOutputStream.bytes,
                this.cacheBytesOutputStream.bytes )){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode( new Object[]{
                this.packageName,
                this.className,
                this.basePath,
                this.cacheBytesOutputStream.lastModifiedMillis,
                this.cacheBytesOutputStream.bytes } );
        return hash;
    }

    @Override
    public String toString() {
        return "_classFile[" + toUri()+"]:" + Integer.toHexString( hashCode() );
    }

    /**
     * The"FileManager"/"ClassLoader" writes the class' bytecodes to the local
     * {@code ByteArrayOutputStream})
     *
     * This variant allows overwriting multiple times (whenever openOutputStream
     * is called
     * @return OutputStream
     */
    @Override
    public OutputStream openOutputStream(){

        //I'm trying to overwrite the outputstream
        if( this.cacheBytesOutputStream.isWritten.get() ){
            //create a new outputStream to write to
            this.cacheBytesOutputStream =
                new CacheBytesOutputStream( this.cacheBytesOutputStream.uri );
        }
        return cacheBytesOutputStream;
    }

    @Override
    public long getLastModified(){
        return this.cacheBytesOutputStream.lastModifiedMillis;
    }

    @Override
    public InputStream openInputStream()
            throws IOException{
        if( this.cacheBytesOutputStream.isWritten.get() ){
            return new ByteArrayInputStream(
                this.cacheBytesOutputStream.toByteArray() );
        }
        //if the stream is still open, or hasnt been created yet by the compiler
        // then throw an exception
        throw new IOException("_classFile ["+toUri()+"] hasnt been written yet");
    }

    /**
     * gets the Class bytecodes as an array of bytes
     *
     * @return the byte array
     */
    public byte[] getBytes() {
        return this.cacheBytesOutputStream.toByteArray();
    }

    /**
     * gets the full path to the file (in a directory or within a jar file)
     * the path contains all directories according to the
     * @return
     */
    public String getFilePath(){
        return getName() + ".class";
    }

    /**
     * 
     * @return 
     */
    public String getFullyQualifiedTypeName() { 
        if( packageName != null){
            return packageName+"."+className;
        }
        return className;
    }

    /**
     * 
     * @return 
     */
    public String getSimpleName(){ 
        return this.className; 
    }
}
