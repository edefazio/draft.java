package draft.java.proto;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import draft.*;
import draft.java.*;
import draft.java._model._node;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Template for an import declaration on a Java top level _type 
 *
 */
public final class $import
    implements Template<_import>, $query<_import> {

    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */
    public static final <T extends _type> List<_import> list( T _type, _import _protoTarget){
        return $import.of(_protoTarget).listIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoTargetImport
     * @return 
     */
    public static final <T extends _type> List<_import> list( T _type, String protoTargetImport ){
        return $import.of(protoTargetImport).listIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param astProtoTarget
     * @return 
     */
    public static final <T extends _type> List<_import> list( T _type, ImportDeclaration astProtoTarget ){
        return $import.of(_import.of(astProtoTarget)).listIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @return 
     */
    public static final <T extends _type> List<_import> list( T _type, Class target ){
        return $import.of(target).listIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<_import> list( T _type, _import _protoTarget, Predicate<_import> constraint){
        return $import.of(_protoTarget).constraint(constraint).listIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoTargetImport
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<_import> list( T _type, String protoTargetImport, Predicate<_import> constraint){
        return $import.of(protoTargetImport).constraint(constraint).listIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param astProtoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<_import> list( T _type, ImportDeclaration astProtoTarget, Predicate<_import> constraint){
        return $import.of(_import.of(astProtoTarget)).constraint(constraint).listIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<_import> list( T _type, Class target, Predicate<_import> constraint){
        return $import.of(target).constraint(constraint).listIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */
    public static final <T extends _type> List<Select> selectList( T _type, _import _protoTarget){
        return $import.of(_protoTarget).selectListIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoTargetImport
     * @return 
     */
    public static final <T extends _type> List<Select> selectList( T _type, String protoTargetImport ){
        return $import.of(protoTargetImport).selectListIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param astProtoTarget
     * @return 
     */
    public static final <T extends _type> List<Select> selectList( T _type, ImportDeclaration astProtoTarget ){
        return $import.of(_import.of(astProtoTarget)).selectListIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @return 
     */
    public static final <T extends _type> List<Select> selectList( T _type, Class target ){
        return $import.of(target).selectListIn(_type);
    }
    
    
        /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<Select> selectList( T _type, _import _protoTarget,Predicate<_import> constraint){
        return $import.of(_protoTarget).constraint(constraint).selectListIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoTargetImport
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<Select> selectList( T _type, String protoTargetImport,Predicate<_import> constraint){
        return $import.of(protoTargetImport).constraint(constraint).selectListIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param astProtoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<Select> selectList( T _type, ImportDeclaration astProtoTarget, Predicate<_import> constraint){
        return $import.of(_import.of(astProtoTarget)).constraint(constraint).selectListIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<Select> selectList( T _type, Class target, Predicate<_import> constraint){
        return $import.of(target).constraint(constraint).selectListIn(_type);
    }
   
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */
    public static final <T extends _type> _import first( T _type, _import _protoTarget){
        return $import.of(_protoTarget).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoTargetImport
     * @return 
     */
    public static final <T extends _type> _import first( T _type, String protoTargetImport ){
        return $import.of(protoTargetImport).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param astProtoTarget
     * @return 
     */
    public static final <T extends _type> _import first( T _type, ImportDeclaration astProtoTarget ){
        return $import.of(_import.of(astProtoTarget)).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @return 
     */
    public static final <T extends _type> _import first( T _type, Class target ){
        return $import.of(target).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> _import first( T _type, _import _protoTarget, Predicate<_import> constraint){
        return $import.of(_protoTarget).constraint(constraint).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoTargetImport
     * @param constraint
     * @return 
     */
    public static final <T extends _type> _import first( T _type, String protoTargetImport, Predicate<_import> constraint){
        return $import.of(protoTargetImport).constraint(constraint).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param astProtoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> _import first( T _type, ImportDeclaration astProtoTarget, Predicate<_import> constraint){
        return $import.of(_import.of(astProtoTarget)).constraint(constraint).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param constraint
     * @return 
     */
    public static final <T extends _type> _import first( T _type, Class target, Predicate<_import> constraint){
        return $import.of(target).constraint(constraint).firstIn(_type);
    }
    
     /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, _import _protoTarget){
        return $import.of(_protoTarget).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoTargetImport
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, String protoTargetImport ){
        return $import.of(protoTargetImport).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param astProtoTarget
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, ImportDeclaration astProtoTarget ){
        return $import.of(_import.of(astProtoTarget)).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, Class target ){
        return $import.of(target).selectFirstIn(_type);
    }
               
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, _import _protoTarget, Predicate<_import> constraint){
        return $import.of(_protoTarget).constraint(constraint).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoTargetImport
     * @param constraint
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, String protoTargetImport, Predicate<_import> constraint){
        return $import.of(protoTargetImport).constraint(constraint).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param astProtoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, ImportDeclaration astProtoTarget, Predicate<_import> constraint){
        return $import.of(_import.of(astProtoTarget)).constraint(constraint).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param constraint
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, Class target, Predicate<_import> constraint){
        return $import.of(target).constraint(constraint).selectFirstIn(_type);
    }
    
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoSource
     * @param _protoTarget
     * @return 
     */
    public static final <T extends _type> T replace( T _type, _import _protoSource, _import _protoTarget ){
        return $import.of(_protoSource).replaceIn(_type, _protoTarget);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoSource
     * @param protoTarget
     * @return 
     */
    public static final <T extends _type> T replace( T _type, String protoSource, String protoTarget ){
        return $import.of(protoSource).replaceIn(_type, protoTarget);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param astProtoSource
     * @param astProtoTarget
     * @return 
     */
    public static final <T extends _type> T replace( T _type, ImportDeclaration astProtoSource, ImportDeclaration astProtoTarget ){
        return $import.of(_import.of(astProtoSource))
                .replaceIn(_type, _import.of(astProtoTarget));
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoSource
     * @param protoTarget
     * @return 
     */
    public static final <T extends _type> T replace( T _type, Class protoSource, Class protoTarget ){
        return $import.of(protoSource)
            .replaceIn(_type, _import.of(protoTarget));
    }

    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */
    public static final <T extends _type> T remove( T _type, _import _protoTarget){
        return $import.of(_protoTarget).removeIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoTarget
     * @return 
     */
    public static final <T extends _type> T remove( T _type, String protoTarget ){
        return $import.of(protoTarget).removeIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param astProtoTarget
     * @return 
     */
    public static final <T extends _type> T remove( T _type, ImportDeclaration astProtoTarget ){
        return $import.of(_import.of(astProtoTarget)).removeIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @return 
     */
    public static final <T extends _type> T remove( T _type, Class target ){
        return $import.of(_import.of(target)).removeIn(_type);
    }
    
    
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> T remove( T _type, _import _protoTarget, Predicate<_import> constraint){
        return $import.of(_protoTarget).constraint(constraint).removeIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> T remove( T _type, String protoTarget, Predicate<_import> constraint){
        return $import.of(protoTarget).constraint(constraint).removeIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param astProtoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> T remove( T _type, ImportDeclaration astProtoTarget, Predicate<_import> constraint){
        return $import.of(_import.of(astProtoTarget)).constraint(constraint).removeIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param constraint
     * @return 
     */
    public static final <T extends _type> T remove( T _type, Class target, Predicate<_import> constraint){
        return $import.of(_import.of(target)).constraint(constraint).removeIn(_type);
    }
    
    /**
     * 
     * @param proto
     * @return 
     */
    public static $import of( String proto){
        _import _i = _import.of(proto );
        return new $import( _i  );
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $import of( String proto, Predicate<_import> constraint){
        _import _i = _import.of(proto );
        return new $import( _i ).constraint(constraint);
    }
    
    /**
     * 
     * @param clazz
     * @return 
     */
    public static $import of( Class clazz ){
        _import _i = _import.of( clazz );
        return new $import( _i  );
    }
    
    /**
     * 
     * @param clazz
     * @param constraint
     * @return 
     */
    public static $import of( Class clazz, Predicate<_import> constraint){
        _import _i = _import.of( clazz );
        return new $import( _i  ).constraint(constraint);
    }
    
    /**
     * 
     * @param _proto
     * @return 
     */
    public static $import of( _import _proto){
        return new $import( _proto  );
    }

    /**
     * 
     * @param _proto
     * @param constraint
     * @return 
     */
    public static $import of( _import _proto, Predicate<_import> constraint){
        return new $import( _proto ).constraint(constraint);
    }
    
    public Predicate<_import> constraint = t-> true;
        
    public Stencil importPattern;
    
    private $import(_import proto ){
        this.importPattern = Stencil.of( proto.getName() );
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public $import constraint( Predicate<_import> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    /**
     * 
     * @param imports
     * @return 
     */
    public boolean matches( String imports ){
        return matches( _import.of(imports) );
    }

    /**
     * 
     * @param astImport
     * @return 
     */
    public boolean matches( ImportDeclaration astImport ){
        return deconstruct(astImport ) != null;
    }

    /**
     * 
     * @param _i
     * @return 
     */
    public boolean matches( _import _i){
        return deconstruct( _i ) != null;
    }

    /**
     * Deconstruct the expression into tokens, or return null if the statement doesnt match
     *
     * @param _i
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public Tokens deconstruct(_import _i ){
        if( this.constraint.test(_i)){
            return importPattern.deconstruct( _i.getName().trim() );
        }
        return null;
    }

    /**
     * Deconstruct the expression into tokens, or return null if the statement doesnt match
     *
     * @param astImport
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public Tokens deconstruct(ImportDeclaration astImport ){
        if(this.constraint.test(_import.of(astImport))){            
            return importPattern.deconstruct( astImport.getNameAsString().trim() );
        }
        return null;
    }

    @Override
    public String toString() {
        return "($import) : \"" + this.importPattern + "\"";
    }

    @Override
    public _import construct(Translator translator, Map<String, Object> keyValues) {
        return _import.of(importPattern.construct(translator, keyValues));
    }

    @Override
    public _import construct(Map<String, Object> keyValues) {
        return _import.of(importPattern.construct(Translator.DEFAULT_TRANSLATOR, keyValues));
    }

    @Override
    public _import construct(Object... keyValues) {
        return _import.of(importPattern.construct(Translator.DEFAULT_TRANSLATOR, keyValues));
    }

    @Override
    public _import construct(Translator translator, Object... keyValues) {
        return _import.of(importPattern.construct(translator, keyValues));
    }

    @Override
    public _import fill(Object... values) {
        return _import.of(importPattern.fill(Translator.DEFAULT_TRANSLATOR, values));
    }

    @Override
    public _import fill(Translator translator, Object... values) {
        return _import.of(importPattern.fill(translator, values));
    }

    @Override
    public $import $(String target, String $Name) {
        this.importPattern = this.importPattern.$(target, $Name);
        return this;
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $import assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $import assign$( Object... keyValues ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, Tokens.of( keyValues ) );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param translator translates values to be hardcoded into the Stencil
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $import assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    public $import assign$( Translator translator, Tokens kvs ) {
        this.importPattern = this.importPattern.assign$(translator,kvs);
        return this;
    }

    @Override
    public List<String> list$() {
        return this.importPattern.list$();
    }

    @Override
    public List<String> list$Normalized() {
        return this.importPattern.list$Normalized();
    }

    /**
     * 
     * @param _i
     * @return 
     */
    public Select select(_import _i){
        return select( _i.ast() );
    }

    /**
     * 
     * @param astImport
     * @return 
     */
    public Select select(ImportDeclaration astImport){
        Tokens ts = this.deconstruct(astImport);
        if( ts != null){
            return new Select( astImport, ts );
        }
        return null;
    }

    /**
     * Returns the first _import that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _import that matches (or null if none found)
     */
    public _import firstIn( _node _n ){
        if( _n.ast().findCompilationUnit().isPresent() ){
            Optional<ImportDeclaration> f = _n.ast().findCompilationUnit().get().findFirst(ImportDeclaration.class, s -> this.matches(s) );         
            if( f.isPresent()){
            return _import.of(f.get());
            }
        }        
        return null;
    }

    /**
     * Returns the first _import that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _import that matches (or null if none found)
     */
    public _import firstIn( Node astNode ){
        if( astNode.findCompilationUnit().isPresent() ){
            Optional<ImportDeclaration> f = astNode.findCompilationUnit().get()
                .findFirst(ImportDeclaration.class, s -> this.matches(s) );         
            if( f.isPresent()){
                return _import.of(f.get());
            }
        }
        return null;
    }
    
    /**
     * Returns the first _import that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _import that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n ){
        if( _n.ast().findCompilationUnit().isPresent() ){
            Optional<ImportDeclaration> f = _n.ast().findCompilationUnit().get()
                    .findFirst(ImportDeclaration.class, s -> this.matches(s) );         
            if( f.isPresent()){
                return select(f.get());
            }
        }
        return null;
    }

    /**
     * Returns the first _import that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _import that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode ){
        if( astNode.findCompilationUnit().isPresent() ){
            Optional<ImportDeclaration> f = astNode.findCompilationUnit().get()
                    .findFirst(ImportDeclaration.class, s -> this.matches(s) );         
            if( f.isPresent()){
                return select(f.get());
            }         
        }
        return null;
    }
    
    @Override
    public List<_import> listIn( _node _n ){
        return listIn( _n.ast() );
    }

    @Override
    public List<_import> listIn(Node astRootNode ){
        if( astRootNode.findCompilationUnit().isPresent()){
            List<_import> l = new ArrayList<>();
            astRootNode.findCompilationUnit().get().walk(ImportDeclaration.class, t->{
                if( this.matches(t) ){
                    l.add(_import.of(t));
                }
            } );
            return l;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Select> selectListIn( Node astRootNode ){
        List<Select>sts = new ArrayList<>();
        if(astRootNode.findCompilationUnit().isPresent() ){
            astRootNode.findCompilationUnit().get().walk(ImportDeclaration.class, e-> {
                Select s = select( e );
                if( s != null ){
                    sts.add( s);
                }
            });
        }
        return sts;
    }

    @Override
    public List<Select> selectListIn( _node _n ){
        return selectListIn( _n.ast() );        
    }

    /**
     * Remove all matching occurrences of the template in the node and return the
     * modified node
     * @param astNode the root node to start search
     * @param <N> the input node TYPE
     * @return the modified node
     */
    @Override
    public <N extends Node> N removeIn(N astNode ){
        astNode.walk( ImportDeclaration.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astImport.removeForced();
            }
        });
        return astNode;
    }

    /**
     *
     * @param _n the root model node to start from
     * @param <N> the TYPE of model node
     * @return the modified model node
     */
    @Override
    public <N extends _node> N removeIn(N _n ){
        removeIn( _n.ast() );
        return _n;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param clazz
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, Class clazz){
        return replaceIn(_n, $import.of(clazz));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param imp
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, String imp){
        return replaceIn(_n, $import.of(imp));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _i
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, _import _i){
        return replaceIn(_n, $import.of( _i ));        
    }
    
        
    /**
     * Replace all occurrences of the template in the code with the replacement
     * (composing the replacement from the constructed tokens in the source)
     *
     * @param _n the model to find replacements
     * @param $i the template to be constructed as the replacement
     * @param <N> the TYPE of model
     * @return
     */
    public <N extends _node> N replaceIn(N _n, $import $i ){
        replaceIn( _n.ast(), $i);        
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param $i
     * @return 
     */
    public <N extends Node> N replaceIn(N astNode, $import $i ){
        if( astNode.findCompilationUnit().isPresent() ){
            astNode.findCompilationUnit().get().walk(ImportDeclaration.class, e-> {
                Select sel = select( e );
                if( sel != null ){
                    sel.astImport.replace($i.construct(sel.args).ast() );
                }
            });
        }
        return astNode;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConsumer
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select> selectConsumer ){
        forSelectedIn(_n.ast(), selectConsumer);
        return _n;
    }

    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectConsumer
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Consumer<Select> selectConsumer ){
        if( astNode.findCompilationUnit().isPresent()){
            astNode.findCompilationUnit().get().walk(ImportDeclaration.class, e-> {
                Select sel = select( e );
                if( sel != null ){
                    selectConsumer.accept( sel );
                }
            });
        }
        return astNode;
    }

    @Override
    public <N extends Node> N forIn(N astNode, Consumer<_import> _importActionFn){
        astNode.walk(ImportDeclaration.class, e-> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                _importActionFn.accept( _import.of(e));
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forIn(N _n, Consumer<_import> _importActionFn){
        forIn(_n.ast(), _importActionFn);
        return _n;
    }

    /**
     * A Matched Selection result returned from matching a prototype $import
     * inside of some CompilationUnit
     */
    public static class Select implements $query.selected, 
        $query.selectedAstNode<ImportDeclaration>, 
        $query.selected_model<_import> {
    
        public final ImportDeclaration astImport;
        public final $args args;

        public Select( ImportDeclaration astImport, Tokens tokens){
            this.astImport = astImport;
            this.args = $args.of( tokens );
        }
        
        @Override
        public $args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$import.Select {"+ System.lineSeparator()+
                Text.indent( astImport.toString() )+ System.lineSeparator()+
                Text.indent("ARGS : " + args) + System.lineSeparator()+
                "}";
        }

        @Override
        public ImportDeclaration ast() {
            return astImport;
        }

        @Override
        public _import model() {
            return _import.of(astImport);
        }
    }
}
