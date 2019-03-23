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
 * Template for an {@link _anno}
 *
 */
public final class $import
    implements Template<_import>, $query<_import> {

    public static final <T extends _type> T replace( T _type, _import _protoSource, _import _protoTarget ){
        return $import.of(_protoSource).replaceIn(_type, _protoTarget);
    }
    
    public static final <T extends _type> T replace( T _type, String protoSource, String protoTarget ){
        return $import.of(protoSource).replaceIn(_type, protoTarget);
    }
    
    public static final <T extends _type> T replace( T _type, ImportDeclaration astProtoSource, ImportDeclaration astProtoTarget ){
        return $import.of(_import.of(astProtoSource))
                .replaceIn(_type, _import.of(astProtoTarget));
    }
    
    public static final <T extends _type> T replace( T _type, Class protoSource, Class protoTarget ){
        return $import.of(_import.of(protoSource))
            .replaceIn(_type, _import.of(protoTarget));
    }

    public static final <T extends _type> T remove( T _type, _import _protoTarget){
        return $import.of(_protoTarget).removeIn(_type);
    }
    
    public static final <T extends _type> T remove( T _type, String protoTarget ){
        return $import.of(protoTarget).removeIn(_type);
    }
    
    public static final <T extends _type> T remove( T _type, ImportDeclaration astProtoTarget ){
        return $import.of(_import.of(astProtoTarget)).removeIn(_type);
    }
    
    public static final <T extends _type> T remove( T _type, Class target ){
        return $import.of(_import.of(target)).removeIn(_type);
    }
    
    public static final <T extends _type> List<_import> list( T _type, _import _protoTarget){
        return $import.of(_protoTarget).listIn(_type);
    }
    
    public static final <T extends _type> List<_import> list( T _type, String protoTargetImport ){
        return $import.of(protoTargetImport).listIn(_type);
    }
    
    public static final <T extends _type> List<_import> list( T _type, ImportDeclaration astProtoTarget ){
        return $import.of(_import.of(astProtoTarget)).listIn(_type);
    }
    
    public static final <T extends _type> List<_import> list( T _type, Class target ){
        return $import.of(_import.of(target)).listIn(_type);
    }
    
    public static $import of( String proto){
        _import _i = _import.of(proto );
        return new $import( _i.toString().trim() );
    }
    
    public static $import of( String proto, Predicate<_import> constraint){
        _import _i = _import.of(proto );
        return new $import( _i.toString().trim() ).constraint(constraint);
    }
    
    public static $import of( Class clazz ){
        _import _i = _import.of( clazz );
        return new $import( _i.toString().trim() );
    }
    
    public static $import of( Class clazz, Predicate<_import> constraint){
        _import _i = _import.of( clazz );
        return new $import( _i.toString().trim() ).constraint(constraint);
    }
    
    public static $import of( _import _proto){
        return new $import( _proto.toString().trim() );
    }

    public static $import of( _import _proto, Predicate<_import> constraint){
        return new $import( _proto.toString().trim() ).constraint(constraint);
    }
    
    public Predicate<_import> constraint = t-> true;
    public Stencil importStencil;

    private $import( String stencil) {
        this.importStencil = Stencil.of(stencil.trim());
    }

    public $import constraint( Predicate<_import> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    public boolean matches( String imports ){
        return matches( _import.of(imports) );
    }

    public boolean matches( ImportDeclaration astImport ){
        return deconstruct(astImport ) != null;
    }

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
            return importStencil.deconstruct( _i.toString().trim() );
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
            return importStencil.deconstruct( astImport.toString().trim() );
        }
        return null;
    }

    @Override
    public String toString() {
        return "($import) : \"" + this.importStencil + "\"";
    }

    @Override
    public _import construct(Translator translator, Map<String, Object> keyValues) {
        return _import.of(importStencil.construct(translator, keyValues));
    }

    @Override
    public _import construct(Map<String, Object> keyValues) {
        return _import.of(importStencil.construct(Translator.DEFAULT_TRANSLATOR, keyValues));
    }

    @Override
    public _import construct(Object... keyValues) {
        return _import.of(importStencil.construct(Translator.DEFAULT_TRANSLATOR, keyValues));
    }

    @Override
    public _import construct(Translator translator, Object... keyValues) {
        return _import.of(importStencil.construct(translator, keyValues));
    }

    @Override
    public _import fill(Object... values) {
        return _import.of(importStencil.fill(Translator.DEFAULT_TRANSLATOR, values));
    }

    @Override
    public _import fill(Translator translator, Object... values) {
        return _import.of(importStencil.fill(translator, values));
    }

    @Override
    public $import $(String target, String $Name) {
        this.importStencil = this.importStencil.$(target, $Name);
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
        this.importStencil = this.importStencil.assign$(translator,kvs);
        return this;
    }

    @Override
    public List<String> list$() {
        return this.importStencil.list$();
    }

    @Override
    public List<String> list$Normalized() {
        return this.importStencil.list$Normalized();
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
    public <N extends Node> N forIn(N node, Consumer<_import> _importActionFn){
        node.walk(ImportDeclaration.class, e-> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                _importActionFn.accept( _import.of(e));
            }
        });
        return node;
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
