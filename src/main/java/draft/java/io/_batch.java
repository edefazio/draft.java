package draft.java.io;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceZip;
import draft.DraftException;
import draft.java._java._compilationUnitMember;
import draft.java._java._moduleInfo;
import draft.java._java._packageInfo;
import draft.java._type;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * ![what
 * happened](https://cdn-images-1.medium.com/max/1600/1*1ceZ3tCVJ-iRyS7B2THn7Q.png)
 *
 * @author Eric
 *
 */
public class _batch {

    public static class _load {

        /** the path for where things are read from */
        Path baseReadPath;
        /** when the _load first started */
        long startTimestamp;
        /** timestamp when all files have been fully read in */ 
        long readFilesTimestamp;
        long processedTimestamp;
        List<Path> paths;
        
        /** 
         * mapping between the path of the input file and the underlying 
         * java _compilationUnit (i.e. _type, 
         */
        Map<Path, _compilationUnitMember> loadedUnits;
    }

    /**
     * Depending on the name of the file, load the appropriate java 
     * _compilationUnit and return the object
     * @param fr
     * @return 
     */
    private static _compilationUnitMember loadJavaCompilationUnit(FileContents fr) {
        if (fr.filePath.endsWith("package-info.java")) {
            return _packageInfo.of(fr.fileContents); //1) parse the String to _type                
            //receipt.compilationUnits.put(fr.filePath, _pi); //store the _type in the receipt                     
        } else if (fr.filePath.endsWith("module-info.java")) {
            return _moduleInfo.of(fr.fileContents);
            //receipt.compilationUnits.put(fr.filePath, _mi); //store the _type in the receipt                                         
        } else {
            return _type.of(fr.fileContents); //1) parse the String to _type                
            //receipt.compilationUnits.put(fr.filePath, _t); //store the _type in the receipt                    
        }
    }

    /**
     * Reads in all .java files into {@link _compilationUnit}s that are 
     * within the rootPath and returns a _load containing
     *
     * @param rootPath
     * @return Receipt containing the files read in, and the paths read from
     */
    public static _load read(Path rootPath) {
        _load receipt = new _load();
        receipt.baseReadPath = rootPath;

        if (rootPath.toFile().isDirectory()) { //bulk read in a directory
            _bulkReadJavaFiles javaFileReader = new _bulkReadJavaFiles();
            try {
                receipt.startTimestamp = System.currentTimeMillis();
                Files.walkFileTree(rootPath, javaFileReader);
                receipt.readFilesTimestamp = System.currentTimeMillis();
                receipt.paths = javaFileReader.listPaths();
            } catch (IOException ioe) {
                throw new _ioException("unable to list files in " + rootPath, ioe);
            }

            /**
             * Use parallel worker threads for parsing and transforming
             */
            javaFileReader.filesRead.stream().forEach(fr -> {
                _compilationUnitMember _cu = loadJavaCompilationUnit( fr );
                receipt.loadedUnits.put(fr.filePath, _cu);
                /*
                //System.out.println("Trying to read "+ fr.filePath );
                if (fr.filePath.endsWith("package-info.java")) {
                    _packageInfo _pi = _packageInfo.of(fr.fileContents); //1) parse the String to _type                
                    receipt.compilationUnits.put(fr.filePath, _pi); //store the _type in the receipt                     
                } else if (fr.filePath.endsWith("module-info.java")) {
                    _moduleInfo _mi = _moduleInfo.of(fr.fileContents);
                    receipt.compilationUnits.put(fr.filePath, _mi); //store the _type in the receipt                                         
                } else {
                    _type _t = _type.of(fr.fileContents); //1) parse the String to _type                
                    receipt.compilationUnits.put(fr.filePath, _t); //store the _type in the receipt                    
                }
                */
            });
            receipt.processedTimestamp = System.currentTimeMillis();
            return receipt;
        }
        /* its a file ( a .zip or .jar file ) */
        SourceZip sz = new SourceZip(rootPath);
        receipt.startTimestamp = System.currentTimeMillis();
        receipt.readFilesTimestamp = System.currentTimeMillis();
        try {
            sz.parse((Path relativeZipEntryPath, ParseResult<CompilationUnit> result) -> {
                if (result.isSuccessful()) {
                    receipt.paths.add(relativeZipEntryPath);
                    _type _t = _type.of(result.getResult().get());
                } else {
                    throw new DraftException("Unable to parse File in zip/jar at: "
                            + relativeZipEntryPath + System.lineSeparator()
                            + "problems:" + result.getProblems());
                }
            });
            receipt.processedTimestamp = System.currentTimeMillis();
            return receipt;
        } catch (IOException ioe) {
            throw new DraftException("Unable to read from .jar or .zip file at " + rootPath);
        }
    }

    public static class FileContents {

        public final Path filePath;
        public final String fileContents;

        public FileContents(Path filePath, String fileContents) {
            this.filePath = filePath;
            this.fileContents = fileContents;
        }
    }

    /**
     * Reads and Stores all Java files found within a directory into a List of
     * Strings in memory.
     *
     * NOTE: this CAN be a memory hog if the directory contains MANY .java files
     * but it does this to allow parallel threads to parse the Strings (which is
     * more CPU intensive operation)
     *
     * Consider an alternative if you have 1000s of .java files to be read in
     */
    private static class _bulkReadJavaFiles extends SimpleFileVisitor<Path> {

        List<FileContents> filesRead = new ArrayList<>();

        public List<Path> listPaths() {
            List<Path> paths = new ArrayList<>();
            filesRead.forEach(fr -> paths.add(fr.filePath));
            return paths;
        }

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attr) {
            if (attr.isRegularFile()) {
                String pathString = path.toString();
                if (pathString.endsWith(".java")) {
                    filesRead.add(new FileContents(path, _inFilePath.readFile(path, Charset.defaultCharset())));
                }
            }
            return CONTINUE;
        }

        /**
         * Invoked for a file that could not be visited.
         *
         * <p>
         * Unless overridden, this method re-throws the I/O exception that
         * prevented the file from being visited.
         */
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            Objects.requireNonNull(file);
            throw new _ioException("unable to visit file " + file, exc);
        }
    }
}
