package draft.java.io;

import java.io.IOException;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.nio.file.Paths;
import javax.tools.FileObject;

/**
 * Given a root directory, (and optional Predicate) will recursively read in a 
 * the files in this directory and all subdirectories) into memory so they can 
 * be operated on (and potentially written out)
 * 
 * @author Eric
 */
public class _batch {
    
    /** the path for where files are read from*/
    public Path rootPath;

    /**
     * the paths of the files read in //public List<Path> sourceFilePaths;
     * (i.e. .java file, .yml
     * file, .pom file)
     */     
    public List<FileObject> files;
    
    /** 
     * Collect the paths to all files that were skipped based on the 
     * {@link #skipFilesPathPredicate} 
     */
    public List<Path> filesSkipped;
    
    /** it's a private constructor, use one of the static of() */
    private _batch (){ }
    
    /**
     * 
     * @param rootPath
     * @return 
     */
    public static _batch of( String rootPath ){
        return of(Paths.get(rootPath));
    }
    
    /**
     * Builds a _batch of in-memory files starting at the rootPath (including all files)
     * @param rootPath the root Path where to read from
     * @return the batch containing all files
     */
    public static _batch of(Path rootPath) {
        /** the default exclude for walking the project*/
        Predicate<String> skipFiles = f-> false;                
        return of(rootPath, skipFiles);
    }

    /**
     * 
     * @param rootPath
     * @param skipFiles
     * @return 
     */
    public static _batch of( String rootPath, Predicate<String> skipFiles) {
        return of(Paths.get(rootPath), skipFiles);
    }
    
    /**
     * Reads in all files into (accept those who match the {@link skipFiles} 
     * predicate) into a _batch
     *
     * @param rootPath the root path
     * @param skipFiles a filter to decide which files to skip based on the 
     * String version of the path
     * @return _batch containing the files read, and the paths read from
     */
    public static _batch of(Path rootPath, Predicate<String> skipFiles) {
        _batch batch = new _batch();
        batch.rootPath = rootPath;

        if (rootPath.toFile().isDirectory()) { //bulk read in a directory
            _fileReader fileReader = new _fileReader(rootPath, skipFiles);
            try {
                Files.walkFileTree(batch.rootPath, fileReader);
                batch.filesSkipped = fileReader.filesSkipped;
            } catch (IOException ioe) {
                throw new _ioException("unable to list files in " + rootPath, ioe);
            }
            batch.files = fileReader.filesRead;
            return batch;
        }
        throw new _ioException("Failure: expected rootPath to be a directory");
    }
            
    /**
     * 
     * @param _fileActionFn
     * @return 
     */
    public _batch forFiles( Consumer<FileObject> _fileActionFn ){
        this.files.forEach(_fileActionFn);
        return this;
    }
    
    /**
     * 
     * @param _fileMatchFn
     * @param _fileActionFn
     * @return 
     */
    public _batch forFiles(Predicate<FileObject> _fileMatchFn, Consumer<FileObject> _fileActionFn){
        this.files.stream().filter(_fileMatchFn).forEach(_fileActionFn);
        return this;
    }
    
    /**
     * Reads and stores the Files read as bytes in memory as FileData (the Path
     * & contents of the data in bytes)
     *
     */
    public static class _fileReader extends SimpleFileVisitor<Path> {

        public Path basePath;
        public List<FileObject> filesRead = new ArrayList<>();
        public List<Path> filesSkipped = new ArrayList<>();
        
        /** Create the right implementation of a file 
         * (i.e. a .java file might be different from a .xml file, from a .jar file)
         * 
         * by default they are all simpleFiles
         */
        public _file._factory fileFactory = _file.SimpleFileFactory;

        public Predicate<String> skipFiles;

        public _fileReader(Path basePath) {
            this.basePath = basePath;
        }

        public _fileReader(Path basePath, Predicate<String> pathExclude) {
            this.basePath = basePath;
            this.skipFiles = pathExclude;
        }
        
        public _fileReader(Path basePath, Predicate<String> pathExclude, _file._factory fileFactory){
            this.basePath = basePath;
            this.skipFiles = pathExclude;
            this.fileFactory = fileFactory;
        }

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attr) {
            String pathString = path.toString();
            if (skipFiles.test(pathString)) {
                filesSkipped.add(path);
                return CONTINUE;
            }
            try {
                byte[] bytes = Files.readAllBytes(path);
                //filesRead.add( _file.of(path, "", bytes) );      
                
                filesRead.add( fileFactory.of(basePath.relativize(path), bytes));
                //filesRead.add( _file.of(basePath, path, bytes));
            } catch (IOException e) {
                throw new _ioException("Unable to read file : " + path, e);
            }
            return CONTINUE;
        }

        /**
         * Invoked for a file that could not be visited.
         *
         * <p>
         * Unless overridden, this method re-throws the I/O exception that
         * prevented the file from being visited.
         *
         * @param file
         * @param exc
         * @return
         */
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            Objects.requireNonNull(file);
            throw new _ioException("unable to visit file " + file, exc);
        }
    }
}
