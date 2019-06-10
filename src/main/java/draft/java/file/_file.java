package draft.java.file;

import draft.DraftException;
import draft.java.io._ioException;

import javax.tools.FileObject;
import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * In memory version of a File
 * (i.e. for "resource files" that may be generated at runtime)
 *
 * NOTE: there are Specific {@_javaFile}
 *
 * @author M. Eric DeFazio
 */
public final class _file implements FileObject {

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

    /**
     *
     */
    public static abstract class ActionAfterCloseOutputStream
            extends OutputStream{

        protected ByteArrayOutputStream os;

        public ActionAfterCloseOutputStream(  ){
            this.os = new ByteArrayOutputStream();
        }

        public ActionAfterCloseOutputStream( ByteArrayOutputStream os ){
            this.os = os;
        }

        @Override
        public void write( int b ) {
            os.write( b );
        }

        @Override
        public void close() throws IOException {

            this.os.close();
            afterClose();
        }

        public abstract void afterClose();
    }

    /** Wraps a Writer */
    public static abstract class ActionAfterCloseWriter extends Writer {

        protected StringWriter writer;

        public ActionAfterCloseWriter(){
            this.writer = new StringWriter();
        }

        public ActionAfterCloseWriter( StringWriter writer ){
            this.writer = writer;
        }

        @Override
        public void write( char[] cbuf, int off, int len ){
            this.writer.write( cbuf, off, len);
        }

        @Override
        public void close() throws IOException {

            this.writer.close();
            afterClose();
        }

        @Override
        public void flush(){
            this.writer.flush();
        }

        public abstract void afterClose() throws IOException;
    }

    /**
     * This class exists to delay the registering of a Class in the _classLoader
     * until AFTER the compiler has compiled it and written the compiled
     * bytecodes into the _classLoader and closed the stream.
     *
     * i.e. it will accept the runtime javac compiler to create, write, and close
     * the stream (containing Java bytecodes), and (upon closing) will then
     */
    public static class CacheBytesOutputStream extends OutputStream{

        /**
         * Binary in-memory representation of the Class' bytecodes This Stream
         * is used the First as the Stream to write the bytecode to,
         * (AFTER it is fully written, the data for the class File is stored
         * in bytes[])
         */
        private final ByteArrayOutputStream initialOutputStream;

        /**
         * Has the initialOutputStream been completely written to and the bytes[]
         * been initialized (is the classFile completed the compilation?)
         */
        protected final AtomicBoolean isWritten;

        /**
         * The Cached bytes that are stored locally
         * (representing the Class bytecodes)
         */
        protected byte[] bytes;

        /** the time in millis when the class was last modified*/
        protected long lastModifiedMillis;

        /** the URI for the file to be written*/
        public final URI uri;

        public CacheBytesOutputStream( CacheBytesOutputStream proto ){
            this.uri = proto.uri;
            this.lastModifiedMillis = proto.lastModifiedMillis;
            if( proto.isWritten.get() ){
                this.isWritten = new AtomicBoolean(true);
                this.bytes = new byte[proto.bytes.length];
                this.initialOutputStream = null;
                System.arraycopy( proto.bytes, 0, this.bytes, 0, proto.bytes.length );
            }else{
                this.isWritten = new AtomicBoolean(false);
                this.initialOutputStream = new ByteArrayOutputStream();
            }
        }

        public CacheBytesOutputStream( URI uri ) {
            //this is the underlying OutputStream
            this.uri = uri;
            this.initialOutputStream = new ByteArrayOutputStream();
            this.isWritten = new AtomicBoolean( false );
            this.lastModifiedMillis = System.currentTimeMillis();
        }

        /** A Cache Bytes OutputStream (preloaded with data)*/
        public CacheBytesOutputStream( URI uri, byte[] data ){
            this.uri = uri;
            this.initialOutputStream = null; //no writing necessary
            this.lastModifiedMillis = System.currentTimeMillis();
            this.isWritten = new AtomicBoolean(true);
            this.bytes = data;
        }

        @Override
        public void write( int b ){
            initialOutputStream.write( b );
            this.lastModifiedMillis = System.currentTimeMillis();
        }

        /**
         * Writes <code>b.length</code> bytes from the specified byte array to
         * this output stream. The general contract for <code>write(b)</code> is
         * that it should have exactly the same effect as the call
         * <code>write(b, 0, b.length)</code>.
         *
         * @param b the data.
         * @exception IOException if an I/O error occurs.
         * @see OutputStream#write(byte[], int, int)
         */
        @Override
        public void write( byte b[] ) {
            initialOutputStream.write( b, 0, b.length );
            this.lastModifiedMillis = System.currentTimeMillis();
        }

        /**
         * Writes <code>len</code> bytes from the specified byte array starting
         * at offset <code>off</code> to this output stream. The general
         * contract for <code>write(b, off, len)</code> is that some of the
         * bytes in the array <code>b</code> are written to the output stream in
         * order; element <code>b[off]</code> is the first byte written and
         * <code>b[off+len-1]</code> is the last byte written by this operation.
         * <p>
         * The <code>write</code> method of <code>OutputStream</code> calls the
         * write method of one argument on each of the bytes to be written out.
         * Subclasses are encouraged to override this method and provide a more
         * efficient implementation.
         * <p>
         * If <code>b</code> is <code>null</code>, a
         * <code>NullPointerException</code> is THROWS.
         * <p>
         * If <code>off</code> is negative, or <code>len</code> is negative, or
         * <code>off+len</code> is greater than the length of the array
         * <code>b</code>, then an <tt>IndexOutOfBoundsException</tt> is THROWS.
         *
         * @param b the data.
         * @param off the start offset in the data.
         * @param len the number of bytes to write.
         * @throws IOException if an I/O error occurs. In particular, an
         * <code>IOException</code> is THROWS if the output stream is closed.
         */
        @Override
        public void write( byte b[], int off, int len ) {
            initialOutputStream.write( b, off, len );
            this.lastModifiedMillis = System.currentTimeMillis();
        }

        /**
         * Always return the local byte array that is written to once
         * @return the byte array contents of the in memory file
         */
        public byte[] toByteArray() {
            if( this.isWritten.get() ) {
                return bytes;
            }
            throw new DraftException("file "+this.uri+" has not been written yet" );
        }

        /**
         * Closes the stream and registers the Class with the ClassLoader
         *
         * @throws IOException
         */
        @Override
        public void close() throws IOException {
            super.flush();
            //now it's just bytes
            this.bytes = this.initialOutputStream.toByteArray();
            super.close();
            this.isWritten.set( true );
            this.lastModifiedMillis = System.currentTimeMillis();
        }
    }
}
