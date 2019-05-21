package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.Type;
import draft.*;
import draft.java.Ast;
import draft.java.Walk;
import draft.java._model._node;
import draft.java._type;
import draft.java._typeRef;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Template for a Java Type Reference
 */
public final class $typeRef
    implements Template<_typeRef>, $proto<_typeRef>, $method.$part, $field.$part, 
        $parameter.$part, $typeParameter.$part {

    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final _typeRef first( Class clazz, String pattern ){
        return first(_type.of(clazz), pattern);
    }
    
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
        return $typeRef.of(pattern).addConstraint(constraint).firstIn(_n);
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
        return $typeRef.of(typeClass).addConstraint(constraint).firstIn(_n);
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
        return $typeRef.of(proto).addConstraint(constraint).firstIn(_n);
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
        return $typeRef.of(pattern).addConstraint(constraint).selectFirstIn(_n);
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
        return $typeRef.of(typeClass).addConstraint(constraint).selectFirstIn(_n);
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
        return $typeRef.of(proto).addConstraint(constraint).selectFirstIn(_n);
    }
    
    /**
     * list all typeRefs in the clazz
     * @param clazz
     * @return 
     */
    public static final List<_typeRef> list( Class clazz ){
        return of().listIn(clazz);
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final List<_typeRef> list( Class clazz, String pattern ){
        return list(_type.of(clazz), pattern);
    }
    
    /**
     * 
     * @param clazz
     * @param target
     * @return 
     */
    public static final List<_typeRef> list( Class clazz, Class target ){
        return list(_type.of(clazz), target);
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
     * @param clazz
     * @param pattern
     * @param constraint
     * @return 
     */
    public static List<_typeRef> list(Class clazz, String pattern, Predicate<_typeRef> constraint){
        return list(_type.of(clazz), pattern, constraint);
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
        return $typeRef.of(pattern).addConstraint(constraint).listIn(_n);
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
        return $typeRef.of(typeClass).addConstraint(constraint).listIn(_n);
    }
        
    /**
     * 
     * @param clazz
     * @param proto
     * @param constraint
     * @return 
     */
    public static final List<_typeRef> list( Class clazz, _typeRef proto, Predicate<_typeRef> constraint){
       return list(_type.of(clazz), proto, constraint); 
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_typeRef> list( N _n, _typeRef proto, Predicate<_typeRef> constraint){
        return $typeRef.of(proto).addConstraint(constraint).listIn(_n);
    }

    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final List<Select> listSelected( Class clazz, String pattern ){
        return listSelected(_type.of(clazz), pattern);    
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<Select> listSelected( N _n, String pattern ){
        return $typeRef.of(pattern).listSelectedIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @return 
     */
    public static final <N extends _node> List<Select> listSelected( N _n, Class typeClass ){
        return $typeRef.of(typeClass).listSelectedIn(_n);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> listSelected( N _n, _typeRef proto ){
        return $typeRef.of(proto).listSelectedIn(_n);
    }  
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final List<Select> listSelected( Class clazz, String pattern, Predicate<Select> constraint){
        return listSelected(_type.of(clazz), pattern, constraint);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> listSelected( N _n, String pattern, Predicate<Select> constraint){
        return $typeRef.of(pattern).listSelectedIn(_n, constraint);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param typeClass
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> listSelected( N _n, Class typeClass, Predicate<Select> constraint ){
        return $typeRef.of(typeClass).listSelectedIn(_n, constraint);
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> listSelected( N _n, _typeRef proto, Predicate<Select> constraint){
        return $typeRef.of(proto).listSelectedIn(_n, constraint);
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
        return new $typeRef(Ast.typeRef(pattern)).addConstraint(constraint);
    }
    
    /**
     * 
     * @param typeClass
     * @return 
     */
    public static $typeRef of( Class typeClass ){
        return $typeRef.of( Ast.typeRef(typeClass) );
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
        return new $typeRef( astType ).addConstraint(constraint);
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
        return new $typeRef(_proto.ast()).addConstraint(constraint);
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
    
    private $typeRef(String pattern){
        this.typePattern = Stencil.of(pattern);
    } 
    
    /**
     * Will this typeDecl match ALL typeDecl's 
     * @return 
     */
    public boolean isMatchAny(){
        if( this.typePattern.isMatchAny() ){
            try{ //test that the predicate is a match all, if we pass in null
                return this.constraint.test(null);
            }catch(Exception e){
                return false;
            }
        }
        return false;
    }
    
    /**
     * SETS/ OVERRIDES the constraint
     * @param constraint the new constraint
     * @return the modified 
     
    public $typeRef constraint(Predicate<_typeRef> constraint){
        this.constraint = constraint;
        return this;
    }
    */ 
    
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
    public $typeRef hardcode$( Tokens kvs ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $typeRef hardcode$( Object... keyValues ) {
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
    public $typeRef hardcode$( Translator translator, Object... keyValues ) {
        return hardcode$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $typeRef hardcode$( Translator translator, Tokens kvs ) {
        this.typePattern = this.typePattern.hardcode$(translator,kvs);
        return this;
    }
    
    public Tokens decomposeTo( _typeRef _t, Tokens allTokens ){
        if(allTokens == null){
            return allTokens;
        }
        Select sel = select(_t);
        if( sel != null ){
            if( allTokens.isConsistent(sel.args.asTokens()) ){
                allTokens.putAll(sel.args.asTokens());
                return allTokens;
            }
        }
        return null;
    }
    
    public static $typeRef any(){
        return of();
    }
    
    /**
     * 
     * @return 
     */
    public static $typeRef of() {
        return new $typeRef("$type$");
    }
    
    /**
     * A TypeRef 
     * @param constraint
     * @return 
     */
    public static $typeRef of( Predicate<_typeRef> constraint ){
        return of().addConstraint(constraint);
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
    
    /**
     * 
     * @param astNode
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(Node astNode, Predicate<Select>selectConstraint){
        List<Select>sts = new ArrayList<>();
        astNode.walk( Type.class, e-> {
            Select s = select( e );
            if( s != null && selectConstraint.test(s)){
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
     * @param _n
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(_node _n,Predicate<Select> selectConstraint){
        return listSelectedIn(_n.ast(), selectConstraint);
    }
    
    public _type forSelectedIn( Class clazz, Consumer<Select> selectConsumer ){
        return forSelectedIn(_type.of(clazz), selectConsumer);
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
     * @param clazz
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public _type forSelectedIn(Class clazz, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer ){
        return forSelectedIn( _type.of(clazz), selectConstraint, selectConsumer);
    }
    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer ){
        Walk.in(_n, Type.class, e-> {
            Select sel = select( e );
            if( sel != null && selectConstraint.test(sel) ){
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

    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer ){
        astNode.walk(Type.class, e-> {
            Select sel = select( e );
            if( sel != null && selectConstraint.test(sel)){
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
     * @param clazz
     * @param replacementType
     * @return 
     */
    public _type replaceIn(Class clazz, Class replacementType){
        return replaceIn(_type.of(clazz), replacementType);
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
     * @param clazz
     * @param replacementType
     * @return 
     */
    public _type replaceIn( Class clazz, _typeRef replacementType ){
        return replaceIn(_type.of(clazz), replacementType);
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
            Select select = select(e);
            if( select != null ){
                if( !e.replace($replacementType.construct(select.args).ast() )){
                    throw new DraftException("unable to replaceIn "+ e + " in "+ _n+" with "+$replacementType);
                }
            }
        });
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param replacementPattern
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, String...replacementPattern){
        return replaceIn(_n, $typeRef.of( Text.combine(replacementPattern)) );
    }

    @Override
    public <N extends Node> N removeIn(N astNode ){
        astNode.walk(Type.class, e -> {
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
            Select select = select(t);
            if( select != null ){
                expressionActionFn.accept(_typeRef.of(t) );
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_typeRef> expressionActionFn){
        Walk.in(_n, Type.class, e -> {
            Select select = select(e);
            if( select != null ){
                expressionActionFn.accept(_typeRef.of(e));
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
        public $args args(){
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
        
        /**
         * Unknown Type is used in lambda like:
         * <PRE>
         * (o)-> System.out.println(o);
         * 
         * ...where o is a parameter with unspecified type
         * </PRE>
         * @return 
         */
        public boolean isUnknownType(){
            return this.type.isUnknownType();
        }
        
        public boolean isPrimitive(){            
            return this.type.isPrimitive();
        }
    }
}
