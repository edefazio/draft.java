package draft.java.file;

import draft.java.file._file.CacheBytesOutputStream;

import javax.tools.SimpleJavaFileObject;
import java.io.*;
import java.net.*;
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

    /** allows the classFile to be created and written ONCE, then cached as an
     * array of bytes to be read multiple times*/
    private CacheBytesOutputStream cacheBytesOutputStream;

    /** the NAME of the Class */
    private final String packageName;

    private final String className;

    @Override
    public String getName() {
        if( packageName != null){
            return packageName+"."+className;
        }
        return className;
    }

    public String getPackageName(){
        return this.packageName;
    }

    /**
     * Initialize an in memory Java Class for a given class Name
     *
     * @param className the full class NAME "io.varcode.MyValueObject"
     * @throws IllegalArgumentException
     * @throws URISyntaxException
     */
    public _classFile( String className )
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
     * Build and return a pre-_1_build _classFile
     *
     * @param className the NAME of the _class
     * @param byteCodes the bytecodes
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public _classFile( String className, byte[] byteCodes )
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
                this.cacheBytesOutputStream.lastModifiedMillis,
                this.cacheBytesOutputStream.bytes } );
        return hash;
    }

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
        super( new URI( _proto.getFullName().replace( '.', '/' ) ), Kind.CLASS );

        this.packageName = _proto.packageName;
        this.className = _proto.className;

        //..call the copy constructor
        this.cacheBytesOutputStream = new CacheBytesOutputStream(
                _proto.cacheBytesOutputStream );
    }

    @Override
    public String toString() {
        return "_classFile(" + getFullName()+"):" + Integer.toHexString( hashCode() );
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
        throw new IOException("_classFile "+getFullName()+" hasnt been written yet");
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

    public String getFullName() { return getName(); }

    public String getSimpleName(){ return this.className; }
}
