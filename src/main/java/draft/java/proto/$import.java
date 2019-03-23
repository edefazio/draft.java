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
 * Template for an import declaration on a Jav top level type 
 *
 */
public final class $import
    implements Template<_import>, $query<_import> {

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
        return $import.of(_import.of(protoSource))
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
        return $import.of(_import.of(target)).listIn(_type);
    }
    
    /**
     * 
     * @param proto
     * @return 
     */
    public static $import of( String proto){
        _import _i = _import.of(proto );
        return new $import( _i.toString().trim() );
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $import of( String proto, Predicate<_import> constraint){
        _import _i = _import.of(proto );
        return new $import( _i.toString().trim() ).constraint(constraint);
    }
    
    /**
     * 
     * @param clazz
     * @return 
     */
    public static $import of( Class clazz ){
        _import _i = _import.of( clazz );
        return new $import( _i.toString().trim() );
    }
    
    /**
     * 
     * @param clazz
     * @param constraint
     * @return 
     */
    public static $import of( Class clazz, Predicate<_import> constraint){
        _import _i = _import.of( clazz );
        return new $import( _i.toString().trim() ).constraint(constraint);
    }
    
    /**
     * 
     * @param _proto
     * @return 
     */
    public static $import of( _import _proto){
        return new $import( _proto.toString().trim() );
    }

    /**
     * 
     * @param _proto
     * @param constraint
     * @return 
     */
    public static $import of( _import _proto, Predicate<_import> constraint){
        return new $import( _proto.toString().trim() ).constraint(constraint);
    }
    
    public Predicate<_import> constraint = t-> true;
        
    public Stencil importPattern;

    private $import( String stencil) {
        this.importPattern = Stencil.of(stencil.trim());
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
            return importPattern.deconstruct( _i.toString().trim() );
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
            return importPattern.deconstruct( astImport.toString().trim() );
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
        Optional<ImportDeclaration> f = _n.ast().findFirst(ImportDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _import.of(f.get());
        }
        return null;
    }

    /**
     * Returns the first _import that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _import that matches (or null if none found)
     */
    public _import firstIn( Node astNode ){
        Optional<ImportDeclaration> f = astNode.findFirst(ImportDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _import.of(f.get());
        }
        return null;
    }
    
    @Override
    public List<_import> listIn( _node _n ){
        if( _n instanceof _type) {
            return listIn(((_type) _n).findCompilationUnit());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<_import> listIn(Node astRootNode ){
        List<_import> typesList = new ArrayList<>();
        astRootNode.walk(ImportDeclaration.class, t->{
            if( this.matches(t) ){
                typesList.add(_import.of(t));
            }
        } );
        return typesList;
    }

    @Override
    public List<Select> listSelectedIn( Node astRootNode ){
        List<Select>sts = new ArrayList<>();
        astRootNode.walk(ImportDeclaration.class, e-> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    @Override
    public List<Select> listSelectedIn( _node _n ){
        if( _n instanceof _type ){
            return listSelectedIn(((_type)_n).findCompilationUnit() );
        }
        return Collections.EMPTY_LIST;
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
        if( _n instanceof _type){
            _type _t = (_type)_n;
            removeIn( _t.findCompilationUnit() );
            return _n;
        }
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
        return replaceIn(_n, _import.of(clazz));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param imp
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, String imp){
        return replaceIn(_n, _import.of(imp));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _i
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, _import _i){
        if( _n instanceof _type ){
            _type _t = (_type)_n;
            replaceIn( _t.findCompilationUnit(), $import.of( _i ) );
        }
        return _n;
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
        if( _n instanceof _type ){
             _type _t = (_type)_n;
            replaceIn( _t.findCompilationUnit(), $i );
        }
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
        astNode.walk(ImportDeclaration.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astImport.replace($i.construct(sel.tokens).ast() );
            }
        });
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
        if( _n instanceof _type ){
            _type _t = (_type)_n;
            forSelectedIn( _t.findCompilationUnit(), selectConsumer );
        }
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
        astNode.walk(ImportDeclaration.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
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
    public <N extends _node> N forIn(N _m, Consumer<_import> _importActionFn){
        if( _m instanceof _type ){
            forIn( ((_type) _m).findCompilationUnit(), _importActionFn);
        }
        return _m;
    }

    public static class Select implements $query.selected {
        public ImportDeclaration astImport;
        public Tokens tokens;

        public Select( ImportDeclaration astImport, Tokens tokens){
            this.astImport = astImport;
            this.tokens = tokens;
        }
        @Override
        public String toString(){
            return "$import.Select {"+ System.lineSeparator()+
                    Text.indent( astImport.toString() )+ System.lineSeparator()+
                    Text.indent( "TOKENS : " + tokens) + System.lineSeparator()+
                    "}";
        }
    }
}
