package draft.java.file;

import draft.DraftException;
import draft.java.io._ioException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.tools.FileObject;

/**
 * 
 * @author Eric
 * @param <T>
 */
public interface _memoryFile<T extends _memoryFile> 
    extends FileObject {

    /** @return How big is the file (in bytes) */
    public int sizeInBytes();
    
    /**
     * Adapts the getCharContent() method that throws a CheckedException
     * @return 
     */
    default String asString(){
        try{
            return this.getCharContent(true).toString();            
        }catch(Exception e){
            throw new _ioException("Unable to get String content of file: \""+ this.getName()+"\"");
        }
    }
    
    /**
     *
     * @return
     */
    default byte[] asByteArray(){
        try{
            InputStream is = this.openInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            } 
            buffer.flush();
            byte[] byteArray = buffer.toByteArray();
            return byteArray;
        }catch(IOException e){
            throw new _ioException("unable to read byte contents of "+ this.getName(), e);
        }        
    }
    
    @Override
    public long getLastModified();
    
    /**
     * Gets the file extension for the file if it has one
     * @return the file extension (i.e. .java, .yml, .html, etc.)
     */
    default String getFileExtension(){
        int idx = this.getName().lastIndexOf(".");
        if( idx > 0 ){
            return this.getName().substring(idx+1);
        }
        return "";
    }
    
    /**
     * is the file one of these file types
     * isFileOfType( ".java", ".html", ".yaml", ".xml", ".json" )
     * @param fileExtensions
     * @return 
     */
    default boolean isFileExtension(String...fileExtensions){
        String fileExtension = getFileExtension();
        
        boolean isOfType = 
            Arrays.stream(fileExtensions).filter(e-> e.equalsIgnoreCase(fileExtension) || e.equalsIgnoreCase("."+fileExtension) ).findFirst().isPresent();        
        return isOfType;
    }
    
    /**
     * NOTE: ASSUMPTION this file is a text based file
     * @param targetReplacementPairs a list of key-value pairs
     * @return true if ANY of the replacements happened (i.e. the file was updated)
     */
    default boolean replaceIn(String...targetReplacementPairs ){
        if( (targetReplacementPairs.length % 2) != 0){
            throw new _ioException("replacements must be passed in pairs (i.e. MUST be an even number), got ("+targetReplacementPairs.length+")");
        }
        boolean modified = false;
        try{            
            String fileContent = this.getCharContent(true).toString();
            
            for(int i=0;i<targetReplacementPairs.length; i+=2){
                if( fileContent.contains(targetReplacementPairs[i]) ){                    
                    fileContent = fileContent.replace(targetReplacementPairs[i], targetReplacementPairs[i+1]);
                    modified = true;
                }                
            }
            
            
            if( modified ){
                /*
                if( this instanceof _file ){
                   _file _f = (_file)this;
                    //System.out.println( "SETTING BYTES ");
                    _f.data = fileContent.getBytes();
                } else{
                */
                //only write if the content has changed,
                //NOTE: this will update the last modified timestamp
                    OutputStream os = this.openOutputStream();
                    os.write(fileContent.getBytes());
                    os.flush();
                    os.close();
                /* }*/
                
                //Writer osw = this.openWriter();
                //osw.write(fileContent);
                //osw.close();
                //System.out.println( "Updated "+ this.getName());
            }            
        }catch(Exception e){
            throw new _ioException("exception reading and replacing file content in : \""+this.getName()+"\"", e);
        }
        return modified;        
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
