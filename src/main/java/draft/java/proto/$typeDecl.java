package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.Type;
import draft.*;
import draft.java.Ast;
import draft.java.Walk;
import draft.java._model._node;
import draft.java._typeDecl;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Template for a Java Type Reference
 */
public final class $typeDecl
    implements Template<_typeDecl>, $proto<_typeDecl>{

    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> _typeDecl first( N _n, String pattern ){
        return $typeDecl.of(pattern).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @return 
     */
    public static final <N extends _node> _typeDecl first( N _n, Class typeClass ){
        return $typeDecl.of(typeClass).firstIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> _typeDecl first( N _n, _typeDecl proto ){
        return $typeDecl.of(proto).firstIn(_n);
    }    
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _typeDecl first( N _n, String pattern, Predicate<_typeDecl> constraint){
        return $typeDecl.of(pattern).constraint(constraint).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _typeDecl first( N _n, Class typeClass, Predicate<_typeDecl> constraint ){
        return $typeDecl.of(typeClass).constraint(constraint).firstIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _typeDecl first( N _n, _typeDecl proto,Predicate<_typeDecl> constraint){
        return $typeDecl.of(proto).constraint(constraint).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String pattern ){
        return $typeDecl.of(pattern).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, Class typeClass ){
        return $typeDecl.of(typeClass).selectFirstIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, _typeDecl proto ){
        return $typeDecl.of(proto).selectFirstIn(_n);
    }    
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String pattern, Predicate<_typeDecl> constraint){
        return $typeDecl.of(pattern).constraint(constraint).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, Class typeClass, Predicate<_typeDecl> constraint ){
        return $typeDecl.of(typeClass).constraint(constraint).selectFirstIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, _typeDecl proto, Predicate<_typeDecl> constraint){
        return $typeDecl.of(proto).constraint(constraint).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<_typeDecl> list( N _n, String pattern ){
        return $typeDecl.of(pattern).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @return 
     */
    public static final <N extends _node> List<_typeDecl> list( N _n, Class typeClass ){
        return $typeDecl.of(typeClass).listIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<_typeDecl> list( N _n, _typeDecl proto ){
        return $typeDecl.of(proto).listIn(_n);
    }    
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_typeDecl> list( N _n, String pattern, Predicate<_typeDecl> constraint){
        return $typeDecl.of(pattern).constraint(constraint).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_typeDecl> list( N _n, Class typeClass, Predicate<_typeDecl> constraint ){
        return $typeDecl.of(typeClass).constraint(constraint).listIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_typeDecl> list( N _n, _typeDecl proto,Predicate<_typeDecl> constraint){
        return $typeDecl.of(proto).constraint(constraint).listIn(_n);
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String pattern ){
        return $typeDecl.of(pattern).listSelectedIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, Class typeClass ){
        return $typeDecl.of(typeClass).listSelectedIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, _typeDecl proto ){
        return $typeDecl.of(proto).listSelectedIn(_n);
    }    
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String pattern, Predicate<_typeDecl> constraint){
        return $typeDecl.of(pattern).constraint(constraint).listSelectedIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, Class typeClass, Predicate<_typeDecl> constraint ){
        return $typeDecl.of(typeClass).constraint(constraint).listSelectedIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, _typeDecl proto, Predicate<_typeDecl> constraint){
        return $typeDecl.of(proto).constraint(constraint).listSelectedIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final <N extends _node> N replace(N _n, _typeDecl sourceProto, _typeDecl targetProto){
        return (N)$typeDecl.of(sourceProto)
            .replaceIn(_n, $typeDecl.of(targetProto));
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
        return (N)$typeDecl.of(sourceType)
            .replaceIn(_n, $typeDecl.of(targetType));
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
        return (N)$typeDecl.of(sourcePattern)
            .replaceIn(_n, $typeDecl.of(targetPattern));
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
        return (N)$typeDecl.of(sourceType)
            .replaceIn(_n, $typeDecl.of(targetType));
    }
    
    /**
     * 
     * @param pattern
     * @return 
     */
    public static $typeDecl of(String pattern ){
        return new $typeDecl(Ast.typeDecl(pattern));
    }

    /**
     * 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $typeDecl of(String pattern, Predicate<_typeDecl> constraint){
        return new $typeDecl(Ast.typeDecl(pattern)).constraint(constraint);
    }
    
    /**
     * 
     * @param typeClass
     * @return 
     */
    public static $typeDecl of( Class typeClass ){
        return of( Ast.typeDecl(typeClass) );
    }

    /**
     * 
     * @param astType
     * @return 
     */
    public static $typeDecl of(Type astType ){
        return new $typeDecl( astType );
    }

    /**
     * 
     * @param astType
     * @param constraint
     * @return 
     */
    public static $typeDecl of(Type astType, Predicate<_typeDecl> constraint){
        return new $typeDecl( astType ).constraint(constraint);
    }
    
    /**
     * 
     * @param _proto
     * @return 
     */
    public static $typeDecl of( _typeDecl _proto){
        return new $typeDecl(_proto.ast());
    }
    
    /**
     * 
     * @param _proto
     * @param constraint
     * @return 
     */
    public static $typeDecl of( _typeDecl _proto, Predicate<_typeDecl> constraint){
        return new $typeDecl(_proto.ast()).constraint(constraint);
    }
    
    /** Matching constraint */
    public Predicate<_typeDecl> constraint = (t)-> true;
    
    /** type code Pattern */
    public Stencil typePattern;

    /**
     * 
     * @param astProtoType 
     */
    public $typeDecl( Type astProtoType ){
        this.typePattern = Stencil.of(_typeDecl.of(astProtoType).toString() );
    }

    /**
     * 
     * @param _proto the proto to build the $typeRef prototype from 
     */
    public $typeDecl(_typeDecl _proto){
        this.typePattern = Stencil.of(_proto.toString() );
    }
    
    /**
     * SETS/ OVERRIDES the constraint
     * @param constraint the new constraint
     * @return the modified 
     */
    public $typeDecl constraint(Predicate<_typeDecl> constraint){
        this.constraint = constraint;
        return this;
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public $typeDecl addConstraint( Predicate<_typeDecl> constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    @Override
    public $typeDecl $(String target, String $name ) {
        this.typePattern = this.typePattern.$(target, $name);
        return this;
    }

    /**
     * 
     * @param astExpr
     * @param $name
     * @return 
     */
    public $typeDecl $(Expression astExpr, String $name){
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
    public $typeDecl hardcode$( Tokens kvs ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $typeDecl hardcode$( Object... keyValues ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, Tokens.of( keyValues ) );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param translator translates values to be hardcoded into the Stencil
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified $field
     */
    public $typeDecl hardcode$( Translator translator, Object... keyValues ) {
        return hardcode$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $typeDecl hardcode$( Translator translator, Tokens kvs ) {
        this.typePattern.hardcode$(translator,kvs);
        return this;
    }
    
    /**
     * 
     * @param _n
     * @return 
     */
    public _typeDecl construct( _node _n ){
        return construct(_n.deconstruct());
    }
    
    @Override
    public _typeDecl construct( Translator t, Map<String,Object> tokens ){
        return _typeDecl.of(typePattern.construct( t, tokens ));
    }

    /**
     * 
     * @param type
     * @return 
     */
    public boolean matches( String type ){
        return select( Ast.typeDecl(type)) != null;
    }

    /**
     * 
     * @param _tr
     * @return 
     */
    public boolean matches( _typeDecl _tr ){
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
    public Select select( _typeDecl _tr){
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
        return select(_typeDecl.of(astType));       
    }
    
    /**
     * 
     * @param type
     * @return 
     */
    public Select select( String type ){
        return select(_typeDecl.of(type));
    }
   
    /**
     * Returns the first Type that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first Type that matches (or null if none found)
     */
    public _typeDecl firstIn( _node _n ){
        Optional<Type> f = _n.ast().findFirst(Type.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _typeDecl.of(f.get());
        }
        return null;
    }

    /**
     * Returns the first Type that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first Type that matches (or null if none found)
     */
    public _typeDecl firstIn( Node astNode ){
        Optional<Type> f = astNode.findFirst(Type.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _typeDecl.of(f.get());
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
    public List<Select> listSelectedIn(Node astNode ){
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
    public List<Select> listSelectedIn(_node _n ){
        return listSelectedIn(_n.ast() );
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
    public List<_typeDecl> listIn(_node _n ){
        return listIn( _n.ast() );
    }

    @Override
    public List<_typeDecl> listIn(Node astNode ){
        List<_typeDecl> typesList = new ArrayList<>();
        astNode.walk(Type.class, t->{
            if( this.matches(t) ){
                typesList.add(_typeDecl.of(t));
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
        return replaceIn(_n, $typeDecl.of(replacementType));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param replacementType
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, _typeDecl replacementType){
        return replaceIn(_n, $typeDecl.of(replacementType));
    }

    /**
     * Find and replaceIn
     * @param _n
     * @param astReplacementType
     * @param <N>
     * @return
     */
    public <N extends _node> N replaceIn(N _n, Type astReplacementType){
        return replaceIn(_n, $typeDecl.of(astReplacementType));
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
    public <N extends _node> N replaceIn(N _n, $typeDecl $replacementType){
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
    public <N extends Node> N forEachIn(N astNode, Consumer<_typeDecl> expressionActionFn){
        astNode.walk(Type.class, t-> {
            //$args tokens = deconstruct( t );
            Select select = select(t);
            if( select != null ){
                expressionActionFn.accept(_typeDecl.of(t) );
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_typeDecl> expressionActionFn){
        Walk.in(_n, Type.class, e -> {
            //$args tokens = deconstruct( e );
            Select select = select(e);
            if( select != null ){
                expressionActionFn.accept(_typeDecl.of(e));
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
            $proto.selected_model<_typeDecl> {
        
        /** The underlying selected _typeRef */
        public _typeDecl type;
        
        /** the arguments selected*/
        public $args args;

        public Select(_typeDecl _tr, Tokens tokens ){
            this.type = _tr;
            this.args = $args.of(tokens);
        }
        
        public Select( Type type, $args tokens){
            this.type = _typeDecl.of(type);
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
        public _typeDecl model() {
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
