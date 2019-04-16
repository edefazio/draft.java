package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
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
 */
public final class $typeRef
    implements Template<_typeRef>, $proto<_typeRef>{

    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> _typeRef first( N _n, String pattern ){
        return $typeRef.of(pattern).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @return 
     */
    public static final <N extends _node> _typeRef first( N _n, Class typeClass ){
        return $typeRef.of(typeClass).firstIn(_n);
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
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _typeRef first( N _n, String pattern, Predicate<_typeRef> constraint){
        return $typeRef.of(pattern).constraint(constraint).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _typeRef first( N _n, Class typeClass, Predicate<_typeRef> constraint ){
        return $typeRef.of(typeClass).constraint(constraint).firstIn(_n);
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
     * @param pattern
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String pattern ){
        return $typeRef.of(pattern).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, Class typeClass ){
        return $typeRef.of(typeClass).selectFirstIn(_n);
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
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String pattern, Predicate<_typeRef> constraint){
        return $typeRef.of(pattern).constraint(constraint).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, Class typeClass, Predicate<_typeRef> constraint ){
        return $typeRef.of(typeClass).constraint(constraint).selectFirstIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, _typeRef proto, Predicate<_typeRef> constraint){
        return $typeRef.of(proto).constraint(constraint).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<_typeRef> list( N _n, String pattern ){
        return $typeRef.of(pattern).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @return 
     */
    public static final <N extends _node> List<_typeRef> list( N _n, Class typeClass ){
        return $typeRef.of(typeClass).listIn(_n);
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
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_typeRef> list( N _n, String pattern, Predicate<_typeRef> constraint){
        return $typeRef.of(pattern).constraint(constraint).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_typeRef> list( N _n, Class typeClass, Predicate<_typeRef> constraint ){
        return $typeRef.of(typeClass).constraint(constraint).listIn(_n);
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
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String pattern ){
        return $typeRef.of(pattern).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, Class typeClass ){
        return $typeRef.of(typeClass).selectListIn(_n);
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
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String pattern, Predicate<_typeRef> constraint){
        return $typeRef.of(pattern).constraint(constraint).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, Class typeClass, Predicate<_typeRef> constraint ){
        return $typeRef.of(typeClass).constraint(constraint).selectListIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, _typeRef proto, Predicate<_typeRef> constraint){
        return $typeRef.of(proto).constraint(constraint).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final <N extends _node> N replace(N _n, _typeRef sourceProto, _typeRef targetProto){
        return (N)$typeRef.of(sourceProto)
            .replaceIn(_n, $typeRef.of(targetProto));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param sourceType
     * @param targetType
     * @return 
     */
    public static final <N extends _node> N replace(N _n, Type sourceType, Type targetType){
        return (N)$typeRef.of(sourceType)
            .replaceIn(_n, $typeRef.of(targetType));
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param sourcePattern
     * @param targetPattern
     * @return 
     */
    public static final <N extends _node> N replace(N _n, String sourcePattern, String targetPattern){
        return (N)$typeRef.of(sourcePattern)
            .replaceIn(_n, $typeRef.of(targetPattern));
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
     * @param pattern
     * @return 
     */
    public static $typeRef of(String pattern ){
        return new $typeRef(Ast.typeRef(pattern));
    }

    /**
     * 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $typeRef of(String pattern, Predicate<_typeRef> constraint){
        return new $typeRef(Ast.typeRef(pattern)).constraint(constraint);
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
     * @param _proto
     * @return 
     */
    public static $typeRef of( _typeRef _proto){
        return new $typeRef(_proto.ast());
    }
    
    /**
     * 
     * @param _proto
     * @param constraint
     * @return 
     */
    public static $typeRef of( _typeRef _proto, Predicate<_typeRef> constraint){
        return new $typeRef(_proto.ast()).constraint(constraint);
    }
    
    /** Matching constraint */
    public Predicate<_typeRef> constraint = (t)-> true;
    
    /** type code Pattern */
    public Stencil typePattern;

    /**
     * 
     * @param astProtoType 
     */
    public $typeRef( Type astProtoType ){
        this.typePattern = Stencil.of(_typeRef.of(astProtoType).toString() );
    }

    /**
     * 
     * @param _proto the proto to build the $typeRef prototype from 
     */
    public $typeRef(_typeRef _proto){
        this.typePattern = Stencil.of(_proto.toString() );
    }
    
    /**
     * SETS/ OVERRIDES the constraint
     * @param constraint the new constraint
     * @return the modified 
     */
    public $typeRef constraint(Predicate<_typeRef> constraint){
        this.constraint = constraint;
        return this;
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public $typeRef addConstraint( Predicate<_typeRef> constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    @Override
    public $typeRef $(String target, String $name ) {
        this.typePattern = this.typePattern.$(target, $name);
        return this;
    }

    /**
     * 
     * @param astExpr
     * @param $name
     * @return 
     */
    public $typeRef $(Expression astExpr, String $name){
        this.typePattern = this.typePattern.$(astExpr.toString(), $name);
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
        this.typePattern.assign$(translator,kvs);
        return this;
    }
    
    /**
     * 
     * @param _n
     * @return 
     */
    public _typeRef construct( _node _n ){
        return construct(_n.deconstruct());
    }
    
    @Override
    public _typeRef construct( Translator t, Map<String,Object> tokens ){
        return _typeRef.of(typePattern.construct( t, tokens ));
    }

    /**
     * 
     * @param type
     * @return 
     */
    public boolean matches( String type ){
        return select( Ast.typeRef(type)) != null;
    }

    /**
     * 
     * @param _tr
     * @return 
     */
    public boolean matches( _typeRef _tr ){
        return select(_tr) != null;
    }
    
    /**
     * 
     * @param astType
     * @return 
     */
    public boolean matches( Type astType ){
        return select(astType) != null;
    }

    @Override
    public List<String> list$(){
        return this.typePattern.list$();
    }

    @Override
    public List<String> list$Normalized(){
        return this.typePattern.list$Normalized();
    }
    
    /**
     * 
     * @param _tr
     * @return 
     */
    public Select select( _typeRef _tr){
        if( this.constraint.test(_tr ) ) {            
            Tokens ts = typePattern.deconstruct(_tr.toString() );
            if( ts != null ){
                return new Select( _tr, ts); //$args.of(ts);
            }
        }        
        return null;
    }
    
    /**
     * 
     * @param astType
     * @return 
     */
    public Select select( Type astType){
        return select(_typeRef.of(astType));       
    }
    
    /**
     * 
     * @param type
     * @return 
     */
    public Select select( String type ){
        return select(_typeRef.of(type));
    }
   
    /**
     * Returns the first Type that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first Type that matches (or null if none found)
     */
    public _typeRef firstIn( _node _n ){
        Optional<Type> f = _n.ast().findFirst(Type.class, s -> this.matches(s) );         
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
        Optional<Type> f = astNode.findFirst(Type.class, s -> this.matches(s) );         
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
        Optional<Type> f = _n.ast().findFirst(Type.class, s -> this.matches(s) );         
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
        Optional<Type> f = astNode.findFirst(Type.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }

    @Override
    public List<Select> selectListIn(Node astNode ){
        List<Select>sts = new ArrayList<>();
        astNode.walk( Type.class, e-> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    @Override
    public List<Select> selectListIn(_node _n ){
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
        Walk.in(_n, Type.class, e-> {
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
        astNode.walk(Type.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return astNode;
    }

    @Override
    public List<_typeRef> listIn(_node _n ){
        return listIn( _n.ast() );
    }

    @Override
    public List<_typeRef> listIn(Node astNode ){
        List<_typeRef> typesList = new ArrayList<>();
        astNode.walk(Type.class, t->{
            if( this.matches(t) ){
                typesList.add(_typeRef.of(t));
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
        return replaceIn(_n, $typeRef.of(replacementType));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param replacementType
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, _typeRef replacementType){
        return replaceIn(_n, $typeRef.of(replacementType));
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
        Walk.in(_n, Type.class, e -> {
            //$args tokens = deconstruct( e );
            Select select = select(e);
            if( select != null ){
                if( !e.replace($replacementType.construct(select.args).ast() )){
                    throw new DraftException("unable to replaceIn "+ e + " in "+ _n+" with "+$replacementType);
                }
            }
        });
        return _n;
    }

    @Override
    public <N extends Node> N removeIn(N astNode ){
        astNode.walk(Type.class, e -> {
            //$args tokens = deconstruct( e );
            Select select = select(e);
            if( select != null ){
                e.removeForced();
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N removeIn(N _n ){
        Walk.in(_n, Type.class, e -> {
            //$args tokens = deconstruct( e );
            Select select = select(e);
            if( select != null ){
                e.removeForced();
            }
        });
        return _n;
    }

    @Override
    public <N extends Node> N forEachIn(N astNode, Consumer<_typeRef> expressionActionFn){
        astNode.walk(Type.class, t-> {
            //$args tokens = deconstruct( t );
            Select select = select(t);
            if( select != null ){
                expressionActionFn.accept( _typeRef.of(t) );
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_typeRef> expressionActionFn){
        Walk.in(_n, Type.class, e -> {
            //$args tokens = deconstruct( e );
            Select select = select(e);
            if( select != null ){
                expressionActionFn.accept( _typeRef.of(e));
            }
        });
        return _n;
    }

    @Override
    public String toString() {
        return "(_typeRef) : \"" + this.typePattern + "\"";
    }

    /**
     * 
     */
    public static class Select implements $proto.selected, 
            $proto.selected_model<_typeRef> {
        
        /** The underlying selected _typeRef */
        public _typeRef type;
        
        /** the arguments selected*/
        public $args args;

        public Select(_typeRef _tr, Tokens tokens ){
            this.type = _tr;
            this.args = $args.of(tokens);
        }
        
        public Select( Type type, $args tokens){
            this.type = _typeRef.of(type);
            this.args = tokens;
        }
        
        @Override
        public $args getArgs(){
            return args;
        } 
        
        @Override
        public String toString(){
            return "$typeRef.Select {"+ System.lineSeparator()+
                Text.indent(type.toString() )+ System.lineSeparator()+
                Text.indent("$args : " + args) + System.lineSeparator()+
                "}";
        }

        public Type ast() {
            return type.ast();
        }

        @Override
        public _typeRef model() {
            return type;
        }
        
        public boolean is(String typeRef){
            return this.type.is(typeRef);
        }
        
        public boolean is(Class typeClass){
            return this.type.is(typeClass);
        }
        
        public boolean is(Type astType){
            return this.type.is(astType);
        }
        
        public boolean isArray(){
            return this.type.isArray();
        }
        
        public boolean isPrimitive(){            
            return this.type.isPrimitive();
        }
    }
}
