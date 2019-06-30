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
 * prototype for an _import declaration on a Java top level _type 
 *
 */
public final class $import
    implements Template<_import>, $proto<_import> {
    
    /**
     * $proto representing any import
     * @return 
     */
    public static $import any(){
        return of();
    }
    
    /**
     * Match ANY import
     * @return 
     */
    public static $import of(){
        return new $import( t-> true );        
    }
    
    /**
     * 
     * @param pattern
     * @return 
     */
    public static $import of( String pattern){
        _import _i = _import.of(pattern );
        return new $import( _i  );
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public static $import of( Predicate<_import> constraint ){
        return new $import( constraint );
    }
    
    /**
     * 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $import of( String pattern, Predicate<_import> constraint){
        _import _i = _import.of(pattern );
        return new $import( _i ).addConstraint(constraint);
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
        return new $import( _i  ).addConstraint(constraint);
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
        return new $import( _proto ).addConstraint(constraint);
    }
    
    public Predicate<_import> constraint = t-> true;
        
    public Stencil importPattern;
    
    /** 
     * Only match on static imports & compose a static import 
     * NOTE: if isStatic is False, this will still match static imports
     */
    public Boolean isStatic = false;
    
    /** 
     * Only match on wildcard (.*) imports & compose a wildcard (.*) import 
     * NOTE: if isWildcard is false, this will still match wildcard imports
     */
    public Boolean isWildcard = false;
    
    private $import(_import proto ){
        this.importPattern = Stencil.of( proto.getName() );
        this.isStatic = proto.isStatic();
        this.isWildcard = proto.isWildcard();        
    }

    private $import( Predicate<_import> constraint ){
        this.importPattern = Stencil.of("$any$");
        this.constraint = constraint;
    }
    
    /**
     * ADDS an additional matching constraint to the prototype
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public $import addConstraint( Predicate<_import>constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    public $import setStatic(){
        return setStatic(true);        
    }
    
    public $import setStatic(boolean toSet){
        this.isStatic = toSet;
        return this;
    }
    
    public $import setWildcard(){
        return setWildcard(true);        
    }
    
    public $import setWildcard(boolean toSet){
        this.isWildcard = toSet;
        return this;
    }
    
    
    /**
     * 
     * @param imprt
     * @return 
     */
    public boolean matches( String imprt ){
        return matches(_import.of(imprt) );
    }

    /**
     * 
     * @param astImport
     * @return 
     */
    public boolean matches( ImportDeclaration astImport ){
        return select(astImport ) != null;
    }

    /**
     * 
     * @param _i
     * @return 
     */
    public boolean matches( _import _i){
        return select( _i ) != null;
    }

    /**
     * 
     * @param _i
     * @return 
     */
    public Select select(_import _i){
        if( this.constraint.test(_i)){
            if( this.isStatic && !_i.isStatic() || this.isWildcard && ! _i.isWildcard() ){
                return null;
            }             
            Tokens ts = importPattern.deconstruct( _i.getName().replace(".*", "").trim() );
            if( ts != null ){
                return new Select(_i, $args.of(ts) );
            }
        }
        return null;
    }

    /**
     * 
     * @param astImport
     * @return 
     */
    public Select select(ImportDeclaration astImport){
        return select(_import.of(astImport) );        
    }
    
    @Override
    public String toString() {
        return "($import) : \"" +this.importPattern + "\"";
    }

    @Override
    public _import construct(Translator translator, Map<String, Object> keyValues) {        
        _import _ii = _import.of(importPattern.construct(translator, keyValues));
        _ii.setStatic(this.isStatic);
        _ii.setWildcard(this.isWildcard);
        return _ii;
    }
    
    @Override
    public $import $(String target, String $Name) {
        this.importPattern = this.importPattern.$(target, $Name);
        return this;
    }

    /**
     * Hardcode (one or more) parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param hardcodedKeyValues the key parameter NAME and VALUE to hardcode
     * @return the modified Stencil
     */
    public $import hardcode$( Tokens hardcodedKeyValues ) {
        return hardcode$(Translator.DEFAULT_TRANSLATOR, hardcodedKeyValues );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $import hardcode$( Object... keyValues ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, Tokens.of( keyValues ) );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param translator translates values to be hardcoded into the Stencil
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $import hardcode$( Translator translator, Object... keyValues ) {
        return hardcode$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $import hardcode$( Translator translator, Tokens kvs ) {
        this.importPattern = this.importPattern.hardcode$(translator,kvs);
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
     * Returns the first _import that matches the pattern and constraint
     * @param astNode the node to look through
     * @param _importMatchFn
     * @return  the first _import that matches (or null if none found)
     */
    @Override
    public _import firstIn( Node astNode, Predicate<_import> _importMatchFn){
        if( astNode.findCompilationUnit().isPresent() ){
            Optional<ImportDeclaration> f = astNode.findCompilationUnit().get()
                .findFirst(ImportDeclaration.class, s ->{
                    Select sel = select(s);
                    return sel != null && _importMatchFn.test(sel._i);
                });         
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
    @Override
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
    
    /**
     * Returns the first _import that matches the pattern and constraint
     * @param _n the _java node
     * @param selectConstraint
     * @return  the first _import that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n, Predicate<Select> selectConstraint ){
        if( _n.ast().findCompilationUnit().isPresent() ){
            Optional<ImportDeclaration> f = _n.ast().findCompilationUnit().get()
                    .findFirst(ImportDeclaration.class, s -> {
                        Select sel = this.select(s); 
                        return sel != null && selectConstraint.test(sel);
                    });                
            if( f.isPresent()){
                return select(f.get());
            }
        }
        return null;
    }

    /**
     * Returns the first _import that matches the pattern and constraint
     * @param astNode the node to look through
     * @param selectConstraint
     * @return  the first _import that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode, Predicate<Select> selectConstraint ){
        if( astNode.findCompilationUnit().isPresent() ){
            Optional<ImportDeclaration> f = astNode.findCompilationUnit().get()
                    .findFirst(ImportDeclaration.class, s -> {
                        Select sel = this.select(s); 
                        return sel != null && selectConstraint.test(sel);
                    });         
            if( f.isPresent()){
                return select(f.get());
            }         
        }
        return null;
    }
    
    @Override
    public List<Select> listSelectedIn( Node astNode ){
        List<Select>sts = new ArrayList<>();
        if(astNode.findCompilationUnit().isPresent() ){
            astNode.findCompilationUnit().get().walk(ImportDeclaration.class, e-> {
                Select s = select( e );
                if( s != null ){
                    sts.add( s);
                }
            });
        }
        return sts;
    }

    /**
     * 
     * @param clazz
     * @return 
     
    @Override
    public List<Select> listSelectedIn(Class clazz){
        return (List<Select>)listSelectedIn(_type.of(clazz));
    }
    */ 
    
    /*
    @Override
    public List<Select> listSelectedIn( _node _n ){
        return $import.this.listSelectedIn( _n.ast() );        
    }
    */
    
    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn( Class clazz, Predicate<Select> selectConstraint ){
        return listSelectedIn(_type.of(clazz), selectConstraint);
    }
    
    /**
     * 
     * @param astNode
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn( Node astNode, Predicate<Select> selectConstraint ){
        List<Select>sts = new ArrayList<>();
        if(astNode.findCompilationUnit().isPresent() ){
            astNode.findCompilationUnit().get().walk(ImportDeclaration.class, e-> {
                Select s = select( e );
                if( s != null && selectConstraint.test(s)){
                    sts.add( s);
                }
            });
        }
        return sts;
    }

    /**
     * 
     * @param _n
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn( _node _n, Predicate<Select> selectConstraint ){
        return $import.this.listSelectedIn( _n.ast() );        
    }
    
    /**
     * 
     * @param clazz
     * @param importClass
     * @return 
     */
    public _type replaceIn(Class clazz, Class importClass){
        return replaceIn( _type.of(clazz), importClass);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param importClass
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, Class importClass){
        return replaceIn(_n, $import.of(importClass));
    }
    
    /**
     * 
     * @param clazz
     * @param importDecl
     * @return 
     */
    public _type replaceIn(Class clazz, String importDecl){
        return replaceIn( _type.of(clazz), importDecl);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param importDecl
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, String importDecl){
        return replaceIn(_n, $import.of(importDecl));
    }
    
    /**
     * 
     * @param clazz
     * @param _i
     * @return 
     */
    public _type replaceIn(Class clazz, _import _i){
        return replaceIn( _type.of(clazz), _i);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _i
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, _import _i){
        Node astNode = _n.ast();
        if( astNode.findCompilationUnit().isPresent() ){
            astNode.findCompilationUnit().get().walk(ImportDeclaration.class, e-> {
                Select sel = select( e );
                if( sel != null ){
                    sel.ast().replace(_i.ast() );
                }
            });
        }
        return _n;
    }
    
    /**
     * 
     * @param clazz
     * @param $i
     * @return 
     */
    public _type replaceIn(Class clazz, $import $i ){
        return replaceIn(_type.of(clazz), $i);
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
     * @param clazz
     * @return 
     */
    public <N extends Node> N replaceIn(N astNode, Class clazz){
        return replaceIn(astNode, $import.of(clazz));
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
                    sel.ast().replace($i.construct(sel.args).ast() );
                }
            });
        }
        return astNode;
    }

    /**
     * 
     * @param clazz
     * @param selectConsumer
     * @return 
     */
    public _type forSelectedIn( Class clazz, Consumer<Select> selectConsumer){
        return forSelectedIn(_type.of(clazz), selectConsumer);
    }
    
    /**
     * 
     * @param <T>
     * @param _t
     * @param selectConsumer
     * @return 
     */
    public <T extends _type> T forSelectedIn( T _t, Consumer<Select> selectConsumer ){
        forSelectedIn(_t.astCompilationUnit(), selectConsumer);
        return _t;
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

    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public _type forSelectedIn(Class clazz, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer ){
        return forSelectedIn(_type.of(clazz), selectConstraint, selectConsumer );
    }
    
    /**
     * 
     * @param <T>
     * @param _t
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public <T extends _type> T forSelectedIn(T _t, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer ){
        forSelectedIn(_t.astCompilationUnit(), selectConstraint, selectConsumer);
        return _t;
    }

    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer ){
        if( astNode.findCompilationUnit().isPresent()){
            astNode.findCompilationUnit().get().walk(ImportDeclaration.class, e-> {
                Select sel = select( e );
                if( sel != null && selectConstraint.test(sel) ){
                    selectConsumer.accept( sel );
                }
            });
        }
        return astNode;
    }
    
    @Override
    public <N extends Node> N forEachIn(N astNode, Predicate<_import> _importMatchFn, Consumer<_import> _importActionFn){
        astNode.walk(ImportDeclaration.class, e-> {
            //$args tokens = deconstruct( e );
            Select sel = select(e);
            if( sel != null && _importMatchFn.test(sel._i) ) {
                _importActionFn.accept( sel._i );
            }
        });
        return astNode;
    }

    /**
     * A Matched Selection result returned from matching a prototype $import
     * inside of some CompilationUnit
     */
    public static class Select implements $proto.selected, 
        $proto.selectedAstNode<ImportDeclaration>, 
        $proto.selected_model<_import> {
    
        public final _import _i;
        public final $args args;

        public Select(_import _i, $args tokens){
            this._i = _i;  
            this.args = tokens;
        }
        
        public Select( ImportDeclaration astImport, $args tokens){
            this._i = _import.of(astImport );
            this.args = tokens;
        }
        
        @Override
        public $args args(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$import.Select {"+ System.lineSeparator()+
                Text.indent( _i.toString() )+ System.lineSeparator()+
                Text.indent("$args : " + args) + System.lineSeparator()+
                "}";
        }

        @Override
        public ImportDeclaration ast() {
            return _i.ast();
        }
        
        public boolean isStatic(){
            return _i.isStatic();
        }
        
        public boolean isWildcard(){
            return _i.isWildcard();
        }

        public boolean is(String importDecl){
            return _i.is(importDecl);
        }
        
        public boolean is(Class clazz){
            return _i.is(clazz);
        }
        
        @Override
        public _import model() {
            return _i;
        }
    }
}
