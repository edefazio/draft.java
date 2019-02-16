package draft.java.io;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.Pair;
import com.github.javaparser.utils.SourceRoot;
import com.github.javaparser.utils.SourceZip;
import junit.framework.TestCase;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class _bulkTest extends TestCase {


    public void testSourceZip() throws IOException {
        //4508ms, 3579ms, 3783
        /*
        System.out.println( _bulk.read("C:\\draft\\intellij\\drft\\drft.zip") );

        SourceZip sz = new SourceZip(Paths.get("C:\\draft\\intellij\\drft\\drft.zip") );
        long start = System.currentTimeMillis();
        List<Pair<Path, ParseResult<CompilationUnit>>> pp = sz.parse();
        long end = System.currentTimeMillis();
        //3290, 3245, 3278
        System.out.println( "TOOK "+(end - start)+"ms for "+ pp.size());
        */
    }


    public void testDir(){
        _bulk._load _l = _bulk.read("C:\\draft\\intellij\\drft");
        System.out.println( _l );

    }

    public void testB(){

        //485 524 500
        _bulk._load _l = _bulk.read(Paths.get("C:\\temp"));
        System.out.println( _l );
        System.out.println( "START"+ _l.startTimestamp );
        System.out.println( "READ"+ _l.readFilesTimestamp );
        System.out.println( "PROCESS"+ _l.processedTimestamp );


        System.out.println( "READ TIME  : "+ (_l.readFilesTimestamp - _l.startTimestamp ));
        System.out.println( "PARSE TIME : "+ (_l.processedTimestamp - _l.readFilesTimestamp));

        //ok lets try a faster parser

        //470 480, 528

        long start = System.currentTimeMillis();
        SourceRoot sr = new SourceRoot(Paths.get( "C:\\temp"));
        List<ParseResult<CompilationUnit>> pares = sr.tryToParseParallelized();
        long end = System.currentTimeMillis();
        System.out.println( "TOOK "+ (end - start));
    }


}
