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
        $parameter.$part, $typeParameter.$part, $var.$part {
    
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
    
    /**
     * 
     * @param pattern 
     */
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
    
    /**
     * 
     * @param _t
     * @param allTokens
     * @return 
     */
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
     * @param astNode the node to look through
     * @param _typeRefActionFn
     * @return  the first Type that matches (or null if none found)
     */
    @Override
    public _typeRef firstIn( Node astNode, Predicate<_typeRef> _typeRefActionFn){
        Optional<Type> f = astNode.findFirst(Type.class, s ->{
            Select sel = select(s);
            return sel != null && _typeRefActionFn.test(sel.type);
            });         
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
    @Override
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
    @Override
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
    
    /**
     * 
     * @param clazz
     * @param selectConsumer
     * @return 
     */
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
    public <N extends Node> N forEachIn(N astNode, Predicate<_typeRef>typeRefMatchFn, Consumer<_typeRef> typeRefActionFn){
        astNode.walk(Type.class, t-> {
            Select select = select(t);
            if( select != null && typeRefMatchFn.test(select.type)){
                typeRefActionFn.accept( select.type );
            }
        });
        return astNode;
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
