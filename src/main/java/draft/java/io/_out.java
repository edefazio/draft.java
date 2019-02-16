package draft.java.io;

import draft.java._type;
import draft.java.file.*;
import draft.java.runtime._project;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Exports in-memory files({@link _javaFile}s, {@link_classFile}s, {@link _file}s)
 * to some destination (i.e the file system) and returns a {@link _receipt}
 * containing information about the exported files
 * (i.e. where they were written to)
 *
 * _out.to("C:\\temp).export(_type);
 * _out.toRelativeDir("generated", _types);
 *
 * @author Eric
 */
public interface _out {

    static _filesReceipt of(_type... _t ){
        return _out.to(_io.getOutDir()).export(_t);
    }

    static _filesReceipt of(_classFiles _cfs ){
        return _out.to(_io.getOutClassDir()).export(_cfs);
    }

    static _filesReceipt of(_io._outConfig oc, _type..._types){
        return _out.to(oc.outJavaDir).export(_types);
    }

    static _filesReceipt of( _io._outConfig oc, _project _p){
        _filesReceipt _tr = _out.to(oc.outJavaDir).export(_p.list_types().toArray(new _type[0]));
        _filesReceipt _cr = _out.to(oc.outClassesDir).export(_p.getClassLoader().classFiles() );
        _filesReceipt _rf = _out.to(oc.outResourcesDir).export( _p.getClassLoader()._fileMan.resourceFiles );

        _tr.fileNames.addAll(_cr.fileNames);
        _tr.fileNames.addAll( _rf.fileNames);
        return _tr;
    }

    /**
     * Write the projects (.java source and .class files)
     *
     * @param _p
     * @return
     */
    static _filesReceipt of( _project _p ){
        _filesReceipt _tr = _out.to(_io.getOutJavaDir()).export(_p.list_types().toArray(new _type[0]));
        _filesReceipt _cr = _out.to(_io.getOutClassDir()).export(_p.getClassLoader().classFiles() );
        _filesReceipt _rf = _out.to(_io.getOutResourcesDir()).export( _p.getClassLoader()._fileMan.resourceFiles );

        _tr.fileNames.addAll(_cr.fileNames);
        _tr.fileNames.addAll( _rf.fileNames);
        return _tr;

    }

    static _filesReceipt to( String directoryPath, _project _p ){
        _filesReceipt _tr = _out.to(directoryPath).export(_p.list_types().toArray(new _type[0]));
        _filesReceipt _cr = _out.to(directoryPath).export(_p.getClassLoader().classFiles() );
        _filesReceipt _rf = _out.to(directoryPath).export( _p.getClassLoader()._fileMan.resourceFiles );

        _tr.fileNames.addAll(_cr.fileNames);
        _tr.fileNames.addAll( _rf.fileNames);

        return _tr;
    }

    /**
     * use the _out instance to export the {@link _javaFile}s and
     * @param _jfs
     * @return a {@link _receipt}
     */
    _receipt export(_javaFile..._jfs);

    /**
     * use the _out instance to export the {@link _javaFiles} and
     * @param _jfs
     * @return a {@link _receipt}
     */
    _receipt export(_javaFiles _jfs);

    /**
     * use the _out instance to export the {@link _file}s and
     * @param files
     * @return a {@link _receipt}
     */
    _receipt export(_file...files);

    /**
     * use the _out instance to export the {@link _files} and
     * @param _fs
     * @return a {@link _receipt}
     */
    _receipt export(_files _fs);

    /**
     * use the _out instance to export the {@link _type}s and
     * @param types
     * @return a {@link _receipt}
     */
    _receipt export(_type...types);

    /**
     * use the _out instance to export the {@link _classFile}s and
     * @param _cfs
     * @return a {@link _receipt}
     */
    _receipt export(_classFile... _cfs);

    /**
     * use the _out instance to export the {@link _classFiles} and
     * @param _cfs
     * @return a {@link _receipt}
     */
    _receipt export(_classFiles _cfs);


    /**
     * Returns a Description of the Files written and perhaps how to access them)
     * i.e. if written to the local file System, will return all of the FileNames
     * i.e. if written to github, the url of each file written, etc.
     */
    interface _receipt{

        /** @return the timestamp for when the export was completed*/
        long getTimestamp();

        /**
         * @return Describe what files have been exported,
         * i.e.contain a list of files that were written
         */
        String describe();

        /**
         * A List of the files/ entities that were exported
         * @return A list of String
         */
        List<String> list();
    }

    static _filesReceipt to( String directoryPath, _type..._types ){
        _to _t = to( directoryPath );
        return _t.export( _types );
    }

    static _filesReceipt to( String directoryPath, _classFile..._classFiles ){
        _to _t = to( directoryPath );
        return _t.export( _classFiles );
    }

    static _filesReceipt to( String directoryPath, _classFiles _classFiles ){
        _to _t = to( directoryPath );
        return _t.export( _classFiles );
    }

    /** prepare to write in memory files to the File System
     * at a certain directoryPath
     * @param directoryPath
     * @return
     */
    static _to to(String directoryPath){
        return new _to(directoryPath);
    }

    /**
     * prepare to write files (.java, .class) to a relative directory
     * based on the System.getProperty.("user.dir"); property
     *
     * @param relativeDir sub directory under "user.dir" System property
     * @return the _to instance
     */
    static _to toRelativeDir(String relativeDir){
        String op = System.getProperty(_io.OUT_DIR);
        if( op == null ){
            return new _to(System.getProperty("user.dir")+"/"+relativeDir);
        }
        return new _to(op+"/"+relativeDir);
    }

    static _filesReceipt toRelativeDir(String relativeDir, _type..._ts){
        return toRelativeDir(relativeDir).export(_ts);
    }

    static _filesReceipt toRelativeDir(String relativeDir, _classFile..._cfs){
        return toRelativeDir(relativeDir).export(_cfs);
    }

    static _filesReceipt toRelativeDir(String relativeDir, _classFiles _cfs){
        return toRelativeDir(relativeDir).export(_cfs);
    }

    /**
     * Export in memory files (_classFile, _file, ...)
     * to the local file system
     */
    class _to implements _out{

        /** base dir where files will be written*/
        public String baseDir;

        public _to( String dirPath){
            this.baseDir = dirPath.replace('\\', '/');
            if( !this.baseDir.endsWith( "/" ) ){
                this.baseDir = this.baseDir + "/";
            }
        }

        @Override
        public _filesReceipt export(_type... types){
            _filesReceipt _fr = new _filesReceipt();
            Arrays.stream(types).forEach(t -> _fr.fileNames.add( export_type(t) ));
            return _fr;
        }

        /**
         * Export a {@link _type} to a new .java file in the appropriate
         * directory under the rootDir
         *
         * @param _t the _type (_class, _enum, _interface, _annotation)
         * @return the file NAME of the *.java file exported
         */
        public String export_type(_type _t){
            //String fileName = this.baseDir + "\\" + javaSrcFilePath(_t);
            //Paths.get( this.baseDir, javaSrcFilePath(_t ) );
            Path p = pathToSource(this.baseDir, _t);
            //File outFile = new File( fileName );
            File outFile = p.toFile();
            //make sure the parent directory is available
            outFile.getParentFile().mkdirs();
            try{
                FileOutputStream fos = new FileOutputStream( outFile );
                fos.write( _t.toString().getBytes() );
                fos.flush();
                fos.close();
                return outFile.getAbsolutePath(); //fileName;
            } catch( IOException ioe ){
                throw new _ioException("cannot write file "+ outFile.getAbsolutePath(), ioe );
            }
        }


        private static Path pathToSource( String baseDir, _type _t ){
            return Paths.get(baseDir, _t.getFullName().replace('.', '\\')+".java");
        }

        private static Path pathToClass( String baseDir, _classFile _cf ){
            return Paths.get(baseDir, _cf.getFullName().replace('.', '\\')+".class");
        }

        @Override
        public _filesReceipt export(_classFile... _cfs) {
            _filesReceipt _fr = new _filesReceipt();
            Arrays.stream(_cfs).forEach( cf -> _fr.fileNames.add( exportClassFile(cf)));
            return _fr;
        }

        @Override
        public _filesReceipt export(_classFiles _cfs){
            _filesReceipt _fr = new _filesReceipt();
            _cfs.list().forEach( cf -> _fr.fileNames.add(exportClassFile(cf)));
            return _fr;
        }

        @Override
        public _filesReceipt export(_javaFile...javaFiles){
            _filesReceipt _fr = new _filesReceipt();
            Arrays.stream(javaFiles).forEach(jf -> _fr.fileNames.add(export_type(jf.get_type())));
            return _fr;
        }

        @Override
        public _filesReceipt export(_javaFiles javaFiles){
            _filesReceipt _fr = new _filesReceipt();
            javaFiles.forEach( jf -> _fr.fileNames.add( export_type(jf.get_type())));
            return _fr;
        }

        public String exportClassFile(_classFile _cf){

            Path pathToClass = pathToClass(this.baseDir, _cf);

            /*
            String fileName = this.baseDir
                    + _cf.getName().replaceIn('.', '/')+".class";
            File outFile = new File( fileName );
            */
            File outFile = pathToClass.toFile();
            outFile.getParentFile().mkdirs();
            try{
                FileOutputStream fos = new FileOutputStream(outFile);
                fos.write(_cf.getBytes());
                fos.flush();
                fos.close();
                return outFile.getAbsolutePath();
            }catch(IOException ioe){
                throw new _ioException("failure writing to "
                        + this.baseDir +" class file "+ outFile.getAbsolutePath(), ioe);
            }
        }

        @Override
        public _filesReceipt export(_file... files){
            _filesReceipt _fr = new _filesReceipt();
            Arrays.stream(files).forEach(f -> _fr.fileNames.add(export_file(f)));
            return _fr;
        }

        @Override
        public _filesReceipt export(_files _fs) {
            _filesReceipt _fr = new _filesReceipt();
            _fs.list().forEach(f -> _fr.fileNames.add( export_file(f)));
            return _fr;
        }

        public String export_file(_file _f){
            String fileName = this.baseDir + _f.getName();
            File outFile = new File(fileName);

            outFile.getParentFile().mkdirs();
            try{
                FileOutputStream fos = new FileOutputStream(outFile);
                fos.write(_f.data);
                fos.flush();
                fos.close();
                return fileName;
            } catch(IOException ioe){
                throw new _ioException(
                        "failure writing file \""+ fileName+"\"", ioe);
            }
        }
    }

    /**
     * _load for one (or more) in memory files written to the File System
     */
    class _filesReceipt implements _receipt{

        /** the fully qualified names of the files written */
        List<String> fileNames = new ArrayList<String>();

        /** timestamp, in millis when the export completed */
        public long timestamp;

        public static _filesReceipt of(String...fns){
            _filesReceipt _fr = new _filesReceipt();
            Arrays.stream(fns).forEach(fn -> _fr.fileNames.add(fn));
            _fr.timestamp = System.currentTimeMillis();
            return _fr;
        }

        public int count(){
            return fileNames.size();
        }

        @Override
        public long getTimestamp(){
            return this.timestamp;
        }

        public List<String> list(){
            return fileNames;
        }

        public boolean contains( String fileName ){
            try{
                Path p = Paths.get(fileName);
                return fileNames.contains(p.toString());
            } catch(Exception e){

            }
            return false;
        }

        public boolean containsAll( String... fns ){
            List<String> tf = new ArrayList<>();
            Arrays.stream(fns).forEach(e -> tf.add( Paths.get(e).toString()));
            return fileNames.containsAll( tf );
        }

        @Override
        public String describe(){
            return "files written " + fileNames.toString();
        }

        @Override
        public String toString(){
            return describe();
        }
    }
}
