/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.io;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.CharBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.tools.FileObject;

/**
 *
 * @author Eric
 */
public interface _file extends FileObject {

    /**
     * A Non Null path relative to the base path where the file is located
     * <PRE>
     * i.e. if we read this _f from a file:
     * "file:///C:/dev/projects/MyProject/src/main/java/com/myproj/MyJavaFile.java"
     * with the basePath of:
     * "file:///C:/dev/projects/MyProject/src/main/java/"
     * this file will have the Path of:
     * "com/myproj/MyJavaFile.java"
     * </PRE>
     */
    default Path getPath() {
        return Paths.get(this.toUri().getPath());
    }

    /**
     * it COULD be binary... could be text... dunno, it's bytes public byte[]
     * getData(){ return this. }
     */
    /**
     * @return update timestamp in millis when the file was last changed public
     * long getLastUpdateTime();
     */
    /**
     * Build the appropriate File abstraction based on the Path & with the data
     */
    public interface _factory {

        public FileObject of(Path path, byte[] data);
    }

    /** Creates Simple Files*/
    public static _factory SimpleFileFactory = (path, data)->{
        return new SimpleFile(path,data);
    };
    
    public static class SimpleFile implements FileObject {

        public Path path;
        public ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        public SimpleFile(Path path, byte[] data){
            this.path = path;
            try{
                this.baos.write(data);
            }catch(IOException ioe){
                throw new _ioException("unabel to write bytes", ioe);
            }
        }
        
        @Override
        public URI toUri(){
            return URI.create("file:////" + this.path.toString().replace("\\", "/"));
        }        
        
        @Override
        public String getName() {
            return this.path.toString();
        }

        @Override
        public InputStream openInputStream() throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            return baos;
        }

        /**
         * Wraps the result of {@linkplain #getCharContent} in a Reader.
         * Subclasses can change this behavior as long as the contract of
         * {@link FileObject} is obeyed.
         *
         * @param ignoreEncodingErrors {@inheritDoc}
         * @return a Reader wrapping the result of getCharContent
         * @throws IllegalStateException {@inheritDoc}
         * @throws UnsupportedOperationException {@inheritDoc}
         * @throws IOException {@inheritDoc}
         */
        @Override
        public Reader openReader(boolean ignoreEncodingErrors) throws IOException {

            CharSequence charContent = getCharContent(ignoreEncodingErrors);
            if (charContent == null) {
                throw new UnsupportedOperationException();
            }
            if (charContent instanceof CharBuffer) {
                CharBuffer buffer = (CharBuffer) charContent;
                if (buffer.hasArray()) {
                    return new CharArrayReader(buffer.array());
                }
            }
            return new StringReader(charContent.toString());
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return new String(this.baos.toByteArray());
        }

        @Override
        public Writer openWriter() throws IOException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public long getLastModified() {
            return 0L;
        }

        @Override
        public boolean delete() {
            return false;
        }
    }
}
