package draft.java.proto;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import draft.*;
import draft.java.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Template for an {@link _anno}
 *
 */
public final class $import
    implements Template<_import>, $query<_import> {

    public static final <T extends _type> T replace( T _type, _import _source, _import _target ){
        return $import.of(_source).replaceIn(_type, _target);
    }
    
    public static final <T extends _type> T replace( T _type, String sourceImport, String targetImport ){
        return $import.of(sourceImport).replaceIn(_type, targetImport);
    }
    
    public static final <T extends _type> T replace( T _type, ImportDeclaration source, ImportDeclaration target ){
        return $import.of(_import.of(source))
                .replaceIn(_type, _import.of(target));
    }
    
    public static final <T extends _type> T replace( T _type, Class source, Class target ){
        return $import.of(_import.of(source))
            .replaceIn(_type, _import.of(target));
    }

    public static final <T extends _type> T remove( T _type, _import _target){
        return $import.of(_target).removeIn(_type);
    }
    
    public static final <T extends _type> T remove( T _type, String targetImport ){
        return $import.of(targetImport).removeIn(_type);
    }
    
    public static final <T extends _type> T remove( T _type, ImportDeclaration target ){
        return $import.of(_import.of(target)).removeIn(_type);
    }
    
    public static final <T extends _type> T remove( T _type, Class target ){
        return $import.of(_import.of(target)).removeIn(_type);
    }
    
    public static final <T extends _type> List<_import> list( T _type, _import _target){
        return $import.of(_target).listIn(_type);
    }
    
    public static final <T extends _type> List<_import> list( T _type, String targetImport ){
        return $import.of(targetImport).listIn(_type);
    }
    
    public static final <T extends _type> List<_import> list( T _type, ImportDeclaration target ){
        return $import.of(_import.of(target)).listIn(_type);
    }
    
    public static final <T extends _type> List<_import> list( T _type, Class target ){
        return $import.of(_import.of(target)).listIn(_type);
    }
    
    public static $import of( String code){
        _import _i = _import.of( code );
        return new $import( _i.toString().trim() );
    }
    
    public static $import of( String code, Predicate<_import> constraint){
        _import _i = _import.of( code );
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
    
    public static $import of( _import _i){
        return new $import( _i.toString().trim() );
    }

    public static $import of( _import _i, Predicate<_import> constraint){
        return new $import( _i.toString().trim() ).constraint(constraint);
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

    public boolean matches( ImportDeclaration importDeclaration ){
        return deconstruct( importDeclaration ) != null;
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

    public Select select(_import _i){
        return select( _i.ast() );
    }

    public Select select(ImportDeclaration i){
        Tokens ts = this.deconstruct(i);
        if( ts != null){
            return new Select( i, ts );
        }
        return null;
    }

    @Override
    public List<_import> listIn(_model._node _t ){
        if( _t instanceof _type) {
            return listIn( ((_type) _t).findCompilationUnit());
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
    public List<Select> listSelectedIn(_model._node _m ){
        if( _m instanceof _type ){
            return listSelectedIn( ((_type)_m).findCompilationUnit() );
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Remove all matching occurrences of the template in the node and return the
     * modified node
     * @param node the root node to start search
     * @param <N> the input node TYPE
     * @return the modified node
     */
    @Override
    public <N extends Node> N removeIn(N node ){
        node.walk( ImportDeclaration.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astImport.removeForced();
            }
        });
        return node;
    }

    /**
     *
     * @param _m the root model node to start from
     * @param <M> the TYPE of model node
     * @return the modified model node
     */
    @Override
    public <M extends _model._node> M removeIn(M _m ){
        if( _m instanceof _type){
            _type _t = (_type)_m;
            removeIn( _t.findCompilationUnit() );
            return _m;
        }
        return _m;
    }

    public <M extends _model._node> M replaceIn(M _m, Class clazz){
        return replaceIn(_m, _import.of(clazz));
    }
    
    public <M extends _model._node> M replaceIn(M _m, String imp){
        return replaceIn(_m, _import.of(imp));
    }
    
    public <M extends _model._node> M replaceIn(M _m, _import _i){
        if( _m instanceof _type ){
            _type _t = (_type)_m;
            replaceIn( _t.findCompilationUnit(), $import.of( _i ) );
        }
        return _m;
    }
        
    /**
     * Replace all occurrences of the template in the code with the replacement
     * (composing the replacement from the constructed tokens in the source)
     *
     * @param _m the model to find replacements
     * @param $i the template to be constructed as the replacement
     * @param <M> the TYPE of model
     * @return
     */
    public <M extends _model._node> M replaceIn(M _m, $import $i ){
        if( _m instanceof _type ){
             _type _t = (_type)_m;
            replaceIn( _t.findCompilationUnit(), $i );
        }
        return _m;
    }
    
    public <N extends Node> N replaceIn(N node, $import $i ){
        node.walk(ImportDeclaration.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astImport.replace($i.construct(sel.tokens).ast() );
            }
        });
        return node;
    }

    public <M extends _model._node> M forSelectedIn(M _m, Consumer<Select> selectConsumer ){
        if( _m instanceof _type ){
            _type _t = (_type)_m;
            forSelectedIn( _t.findCompilationUnit(), selectConsumer );
        }
        return _m;
    }

    public <N extends Node> N forSelectedIn(N node, Consumer<Select> selectConsumer ){
        node.walk(ImportDeclaration.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return node;
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
    public <M extends _model._node> M forIn(M _m, Consumer<_import> _importActionFn){
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
