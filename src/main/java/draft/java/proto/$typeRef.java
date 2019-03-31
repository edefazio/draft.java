package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import draft.*;
import draft.java.Ast;
import draft.java.Walk;
import draft.java._model._node;
import draft.java._typeRef;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Template for a Java Type Reference
 *
 * @param <T> the underlying Expression TYPE
 */
public final class $typeRef<T extends Type>
        implements Template<T>, $query<T>{

    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> _typeRef first( N _n, String proto ){
        return $typeRef.of(proto).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> _typeRef first( N _n, Class proto ){
        return $typeRef.of(proto).firstIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> _typeRef first( N _n, _typeRef proto ){
        return $typeRef.of(proto).firstIn(_n);
    }    
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _typeRef first( N _n, String proto, Predicate<_typeRef> constraint){
        return $typeRef.of(proto).constraint(constraint).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _typeRef first( N _n, Class proto, Predicate<_typeRef> constraint ){
        return $typeRef.of(proto).constraint(constraint).firstIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _typeRef first( N _n, _typeRef proto,Predicate<_typeRef> constraint){
        return $typeRef.of(proto).constraint(constraint).firstIn(_n);
    }
    
    
     /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String proto ){
        return $typeRef.of(proto).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, Class proto ){
        return $typeRef.of(proto).selectFirstIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, _typeRef proto ){
        return $typeRef.of(proto).selectFirstIn(_n);
    }    
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String proto, Predicate<_typeRef> constraint){
        return $typeRef.of(proto).constraint(constraint).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, Class proto, Predicate<_typeRef> constraint ){
        return $typeRef.of(proto).constraint(constraint).selectFirstIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, _typeRef proto,Predicate<_typeRef> constraint){
        return $typeRef.of(proto).constraint(constraint).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<_typeRef> list( N _n, String proto ){
        return $typeRef.of(proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<_typeRef> list( N _n, Class proto ){
        return $typeRef.of(proto).listIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<_typeRef> list( N _n, _typeRef proto ){
        return $typeRef.of(proto).listIn(_n);
    }    
    
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_typeRef> list( N _n, String proto, Predicate<_typeRef> constraint){
        return $typeRef.of(proto).constraint(constraint).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_typeRef> list( N _n, Class proto, Predicate<_typeRef> constraint ){
        return $typeRef.of(proto).constraint(constraint).listIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_typeRef> list( N _n, _typeRef proto,Predicate<_typeRef> constraint){
        return $typeRef.of(proto).constraint(constraint).listIn(_n);
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String proto ){
        return $typeRef.of(proto).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, Class proto ){
        return $typeRef.of(proto).selectListIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, _typeRef proto ){
        return $typeRef.of(proto).selectListIn(_n);
    }    
    
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String proto, Predicate<_typeRef> constraint){
        return $typeRef.of(proto).constraint(constraint).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, Class proto, Predicate<_typeRef> constraint ){
        return $typeRef.of(proto).constraint(constraint).selectListIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, _typeRef proto,Predicate<_typeRef> constraint){
        return $typeRef.of(proto).constraint(constraint).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param protoSource
     * @param protoTarget
     * @return 
     */
    public static final <N extends _node> N replace(N _n, _typeRef protoSource, _typeRef protoTarget){
        return (N)$typeRef.of(protoSource)
            .replaceIn(_n, $typeRef.of(protoTarget));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param protoSource
     * @param protoTarget
     * @return 
     */
    public static final <N extends _node> N replace(N _n, Type protoSource, Type protoTarget){
        return (N)$typeRef.of(protoSource)
            .replaceIn(_n, $typeRef.of(protoTarget));
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param protoSource
     * @param protoTarget
     * @return 
     */
    public static final <N extends _node> N replace(N _n, String protoSource, String protoTarget){
        return (N)$typeRef.of(protoSource)
            .replaceIn(_n, $typeRef.of(protoTarget));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param sourceType
     * @param targetType
     * @return 
     */
    public static final <N extends _node> N replace( N _n, Class sourceType, Class targetType){        
        return (N)$typeRef.of(sourceType)
            .replaceIn(_n, $typeRef.of(targetType));
    }
    
    /**
     * 
     * @param proto
     * @return 
     */
    public static $typeRef of(String proto ){
        return new $typeRef(Ast.typeRef(proto));
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $typeRef of(String proto, Predicate<_typeRef> constraint){
        return new $typeRef(Ast.typeRef(proto)).constraint(constraint);
    }
    
    /**
     * 
     * @param typeClass
     * @return 
     */
    public static $typeRef of( Class typeClass ){
        return of( Ast.typeRef(typeClass) );
    }

    /**
     * 
     * @param astType
     * @return 
     */
    public static $typeRef of(Type astType ){
        return new $typeRef( astType );
    }

    /**
     * 
     * @param astType
     * @param constraint
     * @return 
     */
    public static $typeRef of(Type astType, Predicate<_typeRef> constraint){
        return new $typeRef( astType ).constraint(constraint);
    }
    
    /**
     * 
     * @param _protoType
     * @return 
     */
    public static $typeRef of( _typeRef _protoType){
        return new $typeRef(_protoType.ast());
    }
    
    /**
     * 
     * @param t
     * @param constraint
     * @return 
     */
    public static $typeRef of( _typeRef t, Predicate<_typeRef> constraint){
        return new $typeRef(t.ast()).constraint(constraint);
    }

    public static final $typeRef booleanType = of(PrimitiveType.booleanType());
    public static final $typeRef charType = of(PrimitiveType.charType());
    public static final $typeRef byteType = of(PrimitiveType.byteType());
    public static final $typeRef shortType = of(PrimitiveType.shortType());
    public static final $typeRef intType = of(PrimitiveType.intType());
    public static final $typeRef longType = of(PrimitiveType.longType());
    public static final $typeRef floatType = of(PrimitiveType.floatType());
    public static final $typeRef doubleType = of(PrimitiveType.doubleType());

    public Predicate<_typeRef> constraint = (t)-> true;
    public Class<T> typeClass;
    public Stencil typeStencil;

    /**
     * 
     * @param astProtoType 
     */
    public $typeRef(T astProtoType){
        this.typeClass = (Class<T>)astProtoType.getClass();
        this.typeStencil = Stencil.of(astProtoType.toString() );
        System.out.println("TYPE STENCIL " +typeStencil);
    }

    /**
     * 
     * @param typeClass
     * @param stencil 
     */
    public $typeRef(Class<T>typeClass, String stencil ){
        this.typeClass = typeClass;
        this.typeStencil = Stencil.of(stencil);
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public $typeRef constraint(Predicate<_typeRef> constraint){
        this.constraint = constraint;
        return this;
    }
    
    @Override
    public T fill(Object...values){
        String str = typeStencil.fill(Translator.DEFAULT_TRANSLATOR, values);
        return (T)Ast.typeRef( str );
    }

    @Override
    public $typeRef $(String target, String $name ) {
        this.typeStencil = this.typeStencil.$(target, $name);
        return this;
    }

    /**
     * 
     * @param astExpr
     * @param $name
     * @return 
     */
    public $typeRef $(Expression astExpr, String $name){
        this.typeStencil = this.typeStencil.$(astExpr.toString(), $name);
        return this;
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $typeRef assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $typeRef assign$( Object... keyValues ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, Tokens.of( keyValues ) );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param translator translates values to be hardcoded into the Stencil
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified $field
     */
    public $typeRef assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $typeRef assign$( Translator translator, Tokens kvs ) {
        this.typeStencil.assign$(translator,kvs);
        return this;
    }

    @Override
    public T fill(Translator t, Object...values){
        return (T)Ast.typeRef(typeStencil.fill(t, values));
    }

    @Override
    public T construct( Object...keyValues ){
        return (T)Ast.typeRef(typeStencil.construct( Tokens.of(keyValues)));
    }

    /**
     * 
     * @param _n
     * @return 
     */
    public T construct( _node _n ){
        return (T)$typeRef.this.construct(_n.componentize());
    }

    @Override
    public T construct( Translator t, Object...keyValues ){
        return (T)Ast.typeRef(typeStencil.construct( t, Tokens.of(keyValues) ));
    }

    @Override
    public T construct( Map<String,Object> tokens ){
        return (T)Ast.typeRef(typeStencil.construct( Translator.DEFAULT_TRANSLATOR, tokens ));
    }

    @Override
    public T construct( Translator t, Map<String,Object> tokens ){
        return (T)Ast.typeRef(typeStencil.construct( t, tokens ));
    }

    /**
     * 
     * @param type
     * @return 
     */
    public boolean matches( String type ){
        return deconstruct( Ast.typeRef(type)) != null;
    }

    /**
     * 
     * @param astType
     * @return 
     */
    public boolean matches( _typeRef _tr ){
        return deconstruct(_tr) != null;
    }
    
    /**
     * 
     * @param astType
     * @return 
     */
    public boolean matches( Type astType ){
        return deconstruct(astType) != null;
    }

    @Override
    public List<String> list$(){
        return this.typeStencil.list$();
    }

    @Override
    public List<String> list$Normalized(){
        return this.typeStencil.list$Normalized();
    }

    public Tokens deconstruct( String type ){
        try{
            return deconstruct(_typeRef.of(type));
        }catch(Exception e){
            return null;
        }
    }
    /**
     * 
     * @param _tr
     * @return 
     */
    public Tokens deconstruct( _typeRef _tr){
        //System.out.println( "tryiong decons of " + _tr+ " "+ typeClass);
        //if( typeClass.isAssignableFrom(astType.getClass())){
        //System.out.println( "   Assignable " + typeClass);    
        if( this.constraint.test(_tr ) ) {
            //System.out.println( "      Passed const " + typeClass + " "+ typeStencil + " "+_tr );
            Tokens ts = typeStencil.deconstruct(_tr.toString() );
            //System.out.println( ts );
            return ts;
        }        
        return null;
        //return deconstruct( _tr.ast() );
    }
    
    /**
     * Deconstruct the expression into tokens, or return null if the statement doesnt match
     *
     * @param astType TYPE instance
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public Tokens deconstruct( Type astType ){
        return deconstruct( _typeRef.of(astType ));
        /*
        System.out.println( "tryiong decons of " + astType+ " "+ typeClass);
        if( typeClass.isAssignableFrom(astType.getClass())){
            System.out.println( "   Assignable " + typeClass);    
            if( this.constraint.test((T)astType ) ) {
                System.out.println( "      Passed const " + typeClass + " "+ typeStencil );
                
                return typeStencil.deconstruct(astType.toString() );
            }
        }
        return null;
        */
    }
    
    /**
     * 
     * @param typeRef
     * @return 
     */
    public Select select( _typeRef typeRef){
        return select(typeRef.ast());
    }
    
    /**
     * 
     * @param astType
     * @return 
     */
    public Select select( Type astType){
        Tokens ts = this.deconstruct(astType);
        if( ts != null){
            return new Select( astType, ts );
        }
        return null;
    }
    
    /**
     * Returns the first Type that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first Type that matches (or null if none found)
     */
    public _typeRef firstIn( _node _n ){
        Optional<T> f = _n.ast().findFirst(this.typeClass, s -> this.matches(s) );         
        if( f.isPresent()){
            return _typeRef.of(f.get());
        }
        return null;
    }

    /**
     * Returns the first Type that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first Type that matches (or null if none found)
     */
    public _typeRef firstIn( Node astNode ){
        Optional<T> f = astNode.findFirst(this.typeClass, s -> this.matches(s) );         
        if( f.isPresent()){
            return _typeRef.of(f.get());
        }
        return null;
    }
    
    /**
     * Returns the first Type that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first Type that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n ){
        Optional<T> f = _n.ast().findFirst(this.typeClass, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }

    /**
     * Returns the first Type that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first Type that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode ){
        Optional<T> f = astNode.findFirst(this.typeClass, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }

    @Override
    public List<Select<T>> selectListIn(Node astNode ){
        List<Select<T>>sts = new ArrayList<>();
        astNode.walk(this.typeClass, e-> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    @Override
    public List<Select<T>> selectListIn(_node _n ){
        return selectListIn(_n.ast() );
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConsumer
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select> selectConsumer ){
        Walk.in(_n, this.typeClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
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
        astNode.walk(this.typeClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return astNode;
    }

    @Override
    public List<T> listIn(_node _n ){
        return listIn( _n.ast() );
    }

    @Override
    public List<T> listIn(Node astNode ){
        List<T> typesList = new ArrayList<>();
        astNode.walk(this.typeClass, t->{
            if( this.matches(t) ){
                typesList.add(t);
            }
        } );
        return typesList;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param replacementType
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, Class replacementType){
        return replaceIn( _n, $typeRef.of(replacementType));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param replacementType
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, _typeRef replacementType){
        return replaceIn( _n, $typeRef.of(replacementType));
    }

    /**
     * Find and replaceIn
     * @param _n
     * @param astReplacementType
     * @param <N>
     * @return
     */
    public <N extends _node> N replaceIn(N _n, Type astReplacementType){
        return replaceIn(_n, $typeRef.of(astReplacementType));
    }

    /**
     * Find and replaceIn the TYPE
     *
     * <PRE>
     * for example:
     * _class _c = _class.of(MyType.class);
     * //replaceIn all instances of TreeSet with HashSet within the code
     * _c = $typeRef.of("TreeSet<$key$>").replaceIn(_c, $typeRef.of("HashSet<$key$>"));
     * </PRE>
     *
     * @param _n
     * @param $replacementType the template for the replacement TYPE
     * @param <N>
     * @return
     */
    public <N extends _node> N replaceIn(N _n, $typeRef $replacementType){
        Walk.in(_n, this.typeClass, e -> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                if( !e.replace($replacementType.construct(tokens))){
                    throw new DraftException("unable to replaceIn "+ e + " in "+ _n+" with "+$replacementType);
                }
            }
        });
        return _n;
    }

    @Override
    public <N extends Node> N removeIn(N astNode ){
        astNode.walk(this.typeClass, e -> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                e.removeForced();
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N removeIn(N _n ){
        Walk.in(_n, this.typeClass, e -> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                e.removeForced();
            }
        });
        return _n;
    }

    @Override
    public <N extends Node> N forIn(N astNode, Consumer<T> expressionActionFn){
        astNode.walk(this.typeClass, e-> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                expressionActionFn.accept( e);
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forIn(N _n, Consumer<T> expressionActionFn){
        Walk.in(_n, this.typeClass, e -> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                expressionActionFn.accept( e);
            }
        });
        return _n;
    }

    @Override
    public String toString() {
        return "(" + this.typeClass.getSimpleName() + ") : \"" + this.typeStencil + "\"";
    }

    /**
     * 
     * @param <T> 
     */
    public static class Select<T extends Type> implements $query.selected, 
            $query.selectedAstNode<T>, $query.selected_model<_typeRef> {
        public T astType;
        public $args args;

        public Select( T type, Tokens tokens){
            this.astType = type;
            this.args = $args.of( tokens );
        }
        
        @Override
        public $args getArgs(){
            return args;
        } 
        
        @Override
        public String toString(){
            return "$typeRef.Select {"+ System.lineSeparator()+
                Text.indent(astType.toString() )+ System.lineSeparator()+
                Text.indent("ARGS : " + args) + System.lineSeparator()+
                "}";
        }

        @Override
        public T ast() {
            return astType;
        }

        @Override
        public _typeRef model() {
            return _typeRef.of(astType);
        }
    }
}
