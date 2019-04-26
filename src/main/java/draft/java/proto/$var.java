package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import draft.*;
import draft.java.*;
import draft.java._model._node;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Template for a {@link VariableDeclarator}
 * NOTE: this INCLUDES BOTH: 
 * <UL>
 *    <LI>FIELDS (member fields defined on a type)
 *    <LI>VARIABLES (variables created within a scope body)
 * </UL>
 * 
 * if you would like to operate ONLY on fields, you have the same features on
 * $field... (use this abstraction instead)
 * 
 * if you want to operate ONLY on (local body variables) you can use Select
 * and 
 */
public class $var
    implements Template<VariableDeclarator>, $proto<VariableDeclarator> {
    
    /**
     * List ALL variables within the Node
     * @param <N>
     * @param _n
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( N _n ){
        return any().listIn(_n);
    }
    
    /**
     * List ALL variables within the clazz
     * @param <N>
     * @param clazz
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( Class clazz ){
        return any().listIn( _type.of(clazz) );
    }
    
    /**
     * List ALL variables that pass the predicate within the Node
     * @param <N>
     * @param _n
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( N _n, Predicate<VariableDeclarator> constraint){
        return any().constraint(constraint).listIn(_n);
    }
    
    /**
     * List ALL variables that pass the predicate within the Node
     * @param <N>
     * @param clazz
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( Class clazz, Predicate<VariableDeclarator> constraint){
        return any().constraint(constraint).listIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( N _n, String pattern ){
        return $var.of(pattern).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param clazz the class
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( Class clazz, String pattern ){
        return $var.of(pattern).listIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( N _n, String pattern, Predicate<VariableDeclarator> constraint){
        return $var.of(pattern, constraint).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param clazz
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( Class clazz, String pattern, Predicate<VariableDeclarator> constraint){
        return $var.of(pattern, constraint).listIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( N _n, VariableDeclarator proto ){
        return $var.of(proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( N _n, VariableDeclarator proto, Predicate<VariableDeclarator> constraint){
        return $var.of(proto, constraint).listIn(_n);
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param varActionFn
     * @return 
     */
    public static <N extends _node> N forEach(N _n, Consumer<VariableDeclarator> varActionFn){
        any().forEachIn(_n.ast(), varActionFn);
        return _n;
    }
    
    /**
     * 
     * @param clazz
     * @param varActionFn
     * @return 
     */
    public static _type forEach(Class clazz, Consumer<VariableDeclarator> varActionFn){
        _type _t = _type.of(clazz);
        any().forEachIn(_t, varActionFn);
        return _t;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param varActionFn
     * @return 
     */
    public static <N extends Node> N forEach(N astNode, Consumer<VariableDeclarator> varActionFn){
        any().forEachIn(astNode, varActionFn);
        return  astNode;
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param varActionFn
     * @return 
     */
    public static <N extends _node> N forEach(N _n, String pattern, Consumer<VariableDeclarator> varActionFn){
        of(pattern).forEachIn(_n.ast(), varActionFn);
        return _n;
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @param varActionFn
     * @return 
     */
    public static _type forEach(Class clazz, String pattern, Consumer<VariableDeclarator> varActionFn){
        _type _t = _type.of(clazz);
        of(pattern).forEachIn(_t, varActionFn);
        return _t;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param pattern
     * @param varActionFn
     * @return 
     */
    public static <N extends Node> N forEach(N astNode, String pattern, Consumer<VariableDeclarator> varActionFn){
        of(pattern).forEachIn(astNode, varActionFn);
        return  astNode;
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param varActionFn
     * @return 
     */
    public static <N extends _node> N forEach(N _n, VariableDeclarator proto, Consumer<VariableDeclarator> varActionFn){
        of(proto).forEachIn(_n.ast(), varActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @param varActionFn
     * @return 
     */
    public static <N extends Node> N forEach(N astNode, VariableDeclarator proto, Consumer<VariableDeclarator> varActionFn){
        of(proto).forEachIn(astNode, varActionFn);
        return  astNode;
    }
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param varActionFn
     * @return 
     */
    public static <N extends _node> N forSelected(N _n, String pattern, Consumer<Select> varActionFn){
        of(pattern).forSelectedIn(_n.ast(), varActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param pattern
     * @param varActionFn
     * @return 
     */
    public static <N extends Node> N forSelected(N astNode, String pattern, Consumer<Select> varActionFn){
        of(pattern).forSelectedIn(astNode, varActionFn);
        return  astNode;
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param varActionFn
     * @return 
     */
    public static <N extends _node> N forSelected(N _n, VariableDeclarator proto, Consumer<Select> varActionFn){
        of(proto).forSelectedIn(_n.ast(), varActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @param varActionFn
     * @return 
     */
    public static <N extends Node> N forSelected(N astNode, VariableDeclarator proto, Consumer<Select> varActionFn){
        of(proto).forSelectedIn(astNode, varActionFn);
        return  astNode;
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConstraint
     * @param varActionFn
     * @return 
     */
    public static <N extends _node> N forSelected(N _n, String pattern, Predicate<Select> selectConstraint, Consumer<Select> varActionFn){
        of(pattern).forSelectedIn(_n.ast(), varActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param pattern
     * @param selectConstraint
     * @param varActionFn
     * @return 
     */
    public static <N extends Node> N forSelected(N astNode, String pattern, Predicate<Select> selectConstraint,Consumer<Select> varActionFn){
        of(pattern).forSelectedIn(astNode, varActionFn);
        return  astNode;
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param selectConstraint
     * @param varActionFn
     * @return 
     */
    public static <N extends _node> N forSelected(N _n, VariableDeclarator proto, Predicate<Select> selectConstraint, Consumer<Select> varActionFn){
        of(proto).forSelectedIn(_n.ast(), selectConstraint, varActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @param selectConstraint
     * @param varActionFn
     * @return 
     */
    public static <N extends Node> N forSelected(N astNode, VariableDeclarator proto, Predicate<Select> selectConstraint, Consumer<Select> varActionFn){
        of(proto).forSelectedIn(astNode, selectConstraint, varActionFn);
        return  astNode;
    }
   
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> VariableDeclarator first( N _n, String pattern ){
        return $var.of(pattern).firstIn(_n);
    }
    
    /**
     * finds the first instance of the var pattern within the clazz
     * @param <N>
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final <N extends _node> VariableDeclarator first( Class clazz, String pattern ){
        return $var.of(pattern).firstIn( _type.of(clazz) );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> VariableDeclarator first( N _n, String pattern, Predicate<VariableDeclarator> constraint){
        return $var.of(pattern, constraint).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> VariableDeclarator first( N _n, VariableDeclarator proto ){
        return $var.of(proto).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> VariableDeclarator first( N _n, VariableDeclarator proto, Predicate<VariableDeclarator> constraint){
        return $var.of(proto, constraint)
                .firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String pattern ){
        return $var.of(pattern).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConstraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String pattern, Predicate<Select> selectConstraint ){
        return $var.of(pattern).selectFirstIn(_n, selectConstraint);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, VariableDeclarator proto ){
        return $var.of(proto).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param selectConstraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, VariableDeclarator proto, Predicate<Select> selectConstraint){
        return $var.of(proto).selectFirstIn(_n, selectConstraint);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String pattern ){
        return $var.of(pattern).listSelectedIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConstraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String pattern, Predicate<Select> selectConstraint){
        return $var.of(pattern)
                .selectListIn(_n, selectConstraint);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, VariableDeclarator proto ){
        return $var.of(proto).listSelectedIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, VariableDeclarator proto, Predicate<VariableDeclarator> constraint){
        return $var.of(proto, constraint).listSelectedIn(_n);
    }
    
    /**
     * Removes all occurrences of the source field in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param proto
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, VariableDeclarator proto ){
        return $var.of(proto).removeIn(_n);
    }
    
    /**
     * Removes all occurrences of the source field in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, VariableDeclarator proto, Predicate<VariableDeclarator> constraint){
        return $var.of(proto, constraint).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String pattern ){
        return $var.of(pattern).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final <N extends _node> N replace(N rootNode, VariableDeclarator sourceProto, VariableDeclarator targetProto){
        return $var.of(sourceProto)
            .replaceIn(rootNode, $var.of(targetProto));
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param sourcePattern
     * @param targetPattern
     * @return 
     */
    public static final <N extends _node> N replace(N rootNode, String sourcePattern, String targetPattern){
        return $var.of(Ast.variable(sourcePattern))
            .replaceIn(rootNode, $var.of(targetPattern));
    }
    
    /**
     * build a var prototype to match any var (field or local variable declaration)
     * @return the var prototype that matches any var
     */
    public static final $var any(){
        return of("$type$ $name$");
    }
    
    /**
     * a Var Prototype only specifying the type
     * @param varType
     * @return 
     */
    public static $var ofType( Class varType ){
        final _typeRef e = _typeRef.of(varType );
        return any().constraint( v-> e.is(v.getType()) );
    }
    
    /**
     * a Var Prototype only specifying the type
     * @param varType
     * @return 
     */
    public static $var ofType( Type varType ){
        final _typeRef e = _typeRef.of(varType );
        return any().constraint( v-> e.is(v) );
    }

    /**
     * a Var Prototype only specifying the type
     * @param varType
     * @return 
     */
    public static $var ofType( _typeRef varType ){        
        return any().constraint( v-> varType.is(v) );
    }    
    
    /**
     * 
     * @param pattern
     * @return 
     */
    public static $var of( String pattern ){
        return of(new String[]{pattern});
    }
    
    /**
     * 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $var of( String pattern, Predicate<VariableDeclarator> constraint){
        return of(new String[]{pattern}).constraint(constraint);
    }
    
    /**
     * 
     * @param pattern
     * @return 
     */
    public static $var of(String...pattern){
        return new $var( Ast.variable(pattern ) );
    }

    /**
     * 
     * @param proto
     * @return 
     */
    public static $var of(VariableDeclarator proto){
        return new $var( proto  );
    }

    public static $var of(VariableDeclarator proto, Predicate<VariableDeclarator> constraint){
        return new $var( proto  ).constraint(constraint);
    }
    
    /** Matching constraint */
    public Predicate<VariableDeclarator> constraint = t -> true;
    
    /** Var code pattern */
    public Stencil varPattern;
    
    public Stencil initPattern = null;

    public static final PrettyPrinterConfiguration NO_COMMENTS = new PrettyPrinterConfiguration()
        .setPrintComments(false).setPrintJavadoc(false);
  
    private $var( VariableDeclarator astProtoVar ){
        String str = astProtoVar.getTypeAsString() + " " + astProtoVar.toString(Ast.PRINT_NO_COMMENTS);                 
        varPattern = Stencil.of( str );
        if( astProtoVar.getInitializer().isPresent() ){
            //System.out.println( "Creating init ");
            initPattern = Stencil.of( astProtoVar.getInitializer().get() );
        }
    }
    
    public $var constraint( Predicate<VariableDeclarator> constraint){
        this.constraint = constraint;
        return this;
    }
    
    public $var init( Stencil initPattern ){
        this.initPattern = initPattern;
        return this;
    }
      
    /**
     * 
     * @param var
     * @return 
     */
    public boolean matches( String...var ){
        return matches(Ast.variable(var));
    }

    /**
     * 
     * @param astVar
     * @return 
     */
    public boolean matches( VariableDeclarator astVar ){
        return select(astVar ) != null;
    }   

     /**
     * 
     * @param astVar
     * @return 
     */
    public Select select(VariableDeclarator astVar){       
        Tokens all = new Tokens();
        if(this.constraint.test(astVar)) {            
            if( this.initPattern != null ){
                if( astVar.getInitializer().isPresent()){
                    all = this.initPattern.deconstruct( astVar.getInitializer().get().toString(NO_COMMENTS) );
                    if( all == null ){
                        return null;
                    }
                } else{
                    return null;
                }                
            }
            Tokens matchedName = 
                this.varPattern.deconstruct( astVar.getType()+ " " +astVar.toString(Ast.PRINT_NO_ANNOTATIONS_OR_COMMENTS) );
            if( matchedName != null ){
                all.putAll(matchedName);
                return new Select( astVar, $args.of(all));
            }
        }
        return null;                
    }
    
    @Override
    public String toString() {
        return "($var) : \"" + this.varPattern + "\"";
    }

    @Override
    public VariableDeclarator construct(Translator translator, Map<String, Object> keyValues) {        
        return Ast.variable(varPattern.construct(translator, keyValues));
    }
   
    @Override
    public VariableDeclarator construct(Map<String, Object> keyValues) {
        return construct( Translator.DEFAULT_TRANSLATOR, keyValues);
    }

    @Override
    public VariableDeclarator construct(Object... keyValues) {
        return construct( Translator.DEFAULT_TRANSLATOR, Tokens.of(keyValues));
    }

    @Override
    public VariableDeclarator construct(Translator translator, Object... keyValues) {
        return construct( translator, Tokens.of(keyValues));
    }

    @Override
    public VariableDeclarator fill(Object... values) {
        return fill( Translator.DEFAULT_TRANSLATOR, values );
    }

    @Override
    public VariableDeclarator fill(Translator translator, Object... values) {
        
        if( this.initPattern != null ){
            Stencil combined = Stencil.of(varPattern, Stencil.of(" = "), initPattern );
            return Ast.variable(combined.fill(translator, values));
        }
        return Ast.variable(varPattern.fill(translator, values));
    }

    @Override
    public $var $(String target, String $Name) {
        this.varPattern = this.varPattern.$(target, $Name);
        this.initPattern = this.initPattern.$(target, $Name);
        return this;
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $var hardcode$( Tokens kvs ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $var hardcode$( Object... keyValues ) {
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
    public $var hardcode$( Translator translator, Object... keyValues ) {
        return hardcode$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $var hardcode$( Translator translator, Tokens kvs ) {
        this.varPattern = this.varPattern.hardcode$(translator,kvs);
        if( this.initPattern != null ){
            this.initPattern = this.initPattern.hardcode$(translator,kvs);
        }
        return this;
    }

    @Override
    public List<String> list$() {
        if( this.initPattern == null){
            return this.varPattern.list$();
        }
        return Stencil.of(varPattern, initPattern).list$();
    }

    @Override
    public List<String> list$Normalized() {
        if( this.initPattern == null){
            return this.varPattern.list$Normalized();
        }
        return Stencil.of(varPattern, initPattern).list$Normalized();        
    }
 
    /**
     * Returns the first VaribleDeclarator that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first VaribleDeclarator that matches (or null if none found)
     */
    public VariableDeclarator firstIn( _node _n ){
        Optional<VariableDeclarator> v = _n.ast().findFirst(VariableDeclarator.class, s -> this.matches(s) );         
        if( v.isPresent()){
            return v.get();
        }
        return null;
    }

    /**
     * Returns the first VaribleDeclarator that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first VaribleDeclarator that matches (or null if none found)
     */
    public VariableDeclarator firstIn( Node astNode ){
        Optional<VariableDeclarator> f = astNode.findFirst(VariableDeclarator.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return f.get();
        }
        return null;
    }
    
    /**
     * Returns the first VaribleDeclarator that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first VaribleDeclarator that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n ){
        Optional<VariableDeclarator> f = _n.ast().findFirst(VariableDeclarator.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }

    /**
     * Returns the first Select VaribleDeclarator that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first Select VaribleDeclarator that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode ){
        Optional<VariableDeclarator> f = astNode.findFirst(VariableDeclarator.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }
    
    /**
     * Returns the first VaribleDeclarator that matches the pattern and constraint
     * @param _n the _java node
     * @param selectConstraint
     * @return  the first VaribleDeclarator that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n, Predicate<Select> selectConstraint ){
        Optional<VariableDeclarator> f = _n.ast().findFirst(VariableDeclarator.class, s -> this.matches(s) );         
        if( f.isPresent()){
            Select sel = select(f.get());
            if( selectConstraint.test(sel)){
                return sel;
            }
        }
        return null;
    }

    /**
     * Returns the first Select VaribleDeclarator that matches the pattern and constraint
     * @param astNode the node to look through
     * @param selectConstraint
     * @return  the first Select VaribleDeclarator that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode, Predicate<Select> selectConstraint ){
        Optional<VariableDeclarator> f = astNode.findFirst(VariableDeclarator.class, s -> this.matches(s) );         
        if( f.isPresent()){
            Select sel = select(f.get());
            if( selectConstraint.test(sel)){
                return sel;
            }
        }
        return null;
    }
    
    @Override
    public List<VariableDeclarator> listIn(Class clazz){
        return listIn(_type.of(clazz) );
    }
    
    @Override
    public List<VariableDeclarator> listIn(_node _n ){
        return listIn(_n.ast() );
    }

    @Override
    public List<VariableDeclarator> listIn(Node astNode ){
        List<VariableDeclarator> varList = new ArrayList<>();
        astNode.walk(VariableDeclarator.class, v->{
            if( this.matches(v) ){
                varList.add( v );
            }
        } );
        return varList;
    }

    public List<Select> selectListIn(Class clazz){
        return listSelectedIn(_type.of(clazz));
    }
    
    @Override
    public List<Select> listSelectedIn(Node astNode ){
        List<Select>sts = new ArrayList<>();
        astNode.walk(VariableDeclarator.class, e-> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    @Override
    public List<Select> listSelectedIn(_node _n ){
        List<Select>sts = new ArrayList<>();
        Walk.in(_n, VariableDeclarator.class, e -> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }
    
    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @return 
     */
    public List<Select> selectListIn(Class clazz, Predicate<Select> selectConstraint){
        return selectListIn(_type.of(clazz), selectConstraint);
    }
    
    /**
     * 
     * @param astNode
     * @param selectConstraint
     * @return 
     */
    public List<Select> selectListIn(Node astNode, Predicate<Select> selectConstraint){
        List<Select>sts = new ArrayList<>();
        astNode.walk(VariableDeclarator.class, e-> {
            Select s = select( e );
            if( s != null && selectConstraint.test(s)){
                sts.add( s);
            }
        });
        return sts;
    }

    /**
     * 
     * @param _n
     * @param selectConstraint
     * @return 
     */
    public List<Select> selectListIn(_node _n, Predicate<Select> selectConstraint){
        List<Select>sts = new ArrayList<>();
        Walk.in(_n, VariableDeclarator.class, e -> {
            Select s = select( e );
            if( s != null && selectConstraint.test(s)){
                sts.add( s);
            }
        });
        return sts;
    }
    
    @Override
    public _type removeIn( Class clazz){
        return removeIn(_type.of(clazz) );
    }
    
    @Override
    public <N extends Node> N removeIn(N astNode ){
        astNode.walk(VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astVar.removeForced();
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N removeIn(N _n ){
        Walk.in(_n, VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astVar.removeForced();
            }
        });
        return _n;
    }

    /**
     * 
     * @param clazz
     * @param $replaceProto
     * @return 
     */
    public _type replaceIn(Class clazz, $var $replaceProto ){
        return replaceIn( _type.of(clazz), $replaceProto);    
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param $replaceProto
     * @return 
     */
    public <N extends Node> N replaceIn(N astNode, $var $replaceProto ){
        astNode.walk(VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astVar.replace($replaceProto.construct(sel.args) );
            }
        });
        return astNode;
    }

    /**
     * 
     * @param <N>
     * @param _le
     * @param $replaceProto
     * @return 
     */
    public <N extends _node> N replaceIn(N _le, $var $replaceProto ){
        Walk.in(_le, VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astVar.replace($replaceProto.construct(sel.args) );
            }
        });
        return _le;
    }

    /**
     * 
     * @param clazz
     * @param selectConsumer
     * @return 
     */
    public _type forSelectedIn( Class clazz, Consumer<Select> selectConsumer){
        return forSelectedIn( _type.of(clazz), selectConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConsumer
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select> selectConsumer ){
        Walk.in(_n, VariableDeclarator.class, e-> {
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
        astNode.walk(VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return astNode;
    }
    
    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public _type forSelectedIn( Class clazz, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer){
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
        Walk.in(_n, VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null && selectConstraint.test(sel)){
                selectConsumer.accept( sel );
            }
        });
        return _n;
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
        astNode.walk(VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null && selectConstraint.test(sel)){
                selectConsumer.accept( sel );
            }
        });
        return astNode;
    }

    @Override
    public _type forEachIn( Class clazz, Consumer<VariableDeclarator> varActionFn){
        return forEachIn( _type.of(clazz), varActionFn);
    }
    
    @Override
    public <N extends Node> N forEachIn(N astNode, Consumer<VariableDeclarator> varActionFn){
        astNode.walk(VariableDeclarator.class, e-> {
            //Tokens tokens = this.stencil.partsMap( e.toString());
            Select sel = select( e );
            if( sel != null ){
                varActionFn.accept( e );
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<VariableDeclarator> varActionFn){
        Walk.in(_n, VariableDeclarator.class, e-> {
            //$args tokens = deconstruct( e );
            Select sel = select(e);
            if( sel != null ){
                varActionFn.accept( e );
            }
        });
        return _n;
    }

    /**
     * A Matched Selection result returned from matching a prototype $var
     * inside of some Node or _node
     */
    public static class Select implements $proto.selected, 
            $proto.selectedAstNode<VariableDeclarator> {
        
        public final VariableDeclarator astVar;
        public final $args args;

        public Select( VariableDeclarator v, $args tokens){
            this.astVar = v;
            this.args = tokens;
        }
        
        @Override
        public $args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$var.Select{"+ System.lineSeparator()+
                    Text.indent(astVar.toString() )+ System.lineSeparator()+
                    Text.indent("$args : " + args) + System.lineSeparator()+
                    "}";
        }

        /**
         * Is the variable selected a part of a field (member) variable 
         * (opposite of isLocalVar)
         * @return 
         */
        public boolean isFieldVar(){
            return astVar.getParentNode().isPresent() && astVar.getParentNode().get() instanceof FieldDeclaration;
        }
        
        /**
         * is this selected var part of a local variable (i.e. declared within a
         * body or lambda, not a "member" of a Class) as apposed to a Field
         * @return 
         */
        public boolean isLocalVar(){
            return !isFieldVar();
        }
        
        public boolean isNamed(String name){
            return Objects.equals( astVar.getNameAsString(), name);
        }
        
        public boolean is( String... varDeclaration ){
            try{
                return _field.of(astVar).is(varDeclaration);
            }catch(Exception e){
                return false; 
            }
        }
        
        public boolean hasInit(){
            return getInit() != null;
        }
        
        public Expression getInit(){
            if( astVar.getInitializer().isPresent() ){
                return astVar.getInitializer().get();
            }
            return null;
        }
        
        public boolean isInit( Predicate<Expression> initMatchFn){
            return astVar.getInitializer().isPresent() && initMatchFn.test( astVar.getInitializer().get());
        }
        
        public boolean isInit(String init){
            return Objects.equals( getInit(), Expr.of(init));
        }
        
        public boolean isInit(byte init){
            return Objects.equals( getInit(), Expr.of(init));
        }
        
        public boolean isInit(short init){
            return Objects.equals( getInit(), Expr.of(init));
        }
        
        public boolean isInit(long init){
            return Objects.equals( getInit(), Expr.of(init));
        }
        
        public boolean isInit(char init){
            return Objects.equals( getInit(), Expr.of(init));
        }
        
        public boolean isInit(double init){
            return Objects.equals( getInit(), Expr.of(init));
        }
        
        public boolean isInit(float init){
            return Objects.equals( getInit(), Expr.of(init));
        }
        
        public boolean isInit(boolean init){
            return Objects.equals( getInit(), Expr.of(init));
        }
        
        public boolean isInit(int init){
            return Objects.equals( getInit(), Expr.of(init));
        }
        
        public boolean isInit(Expression init){
            return Objects.equals( getInit(), init);
        }
        
        public boolean isType( Class expectedType){
            return _typeRef.of(astVar.getType()).is( expectedType );
        }
        
        public boolean isType( String expectedType){
            return _typeRef.of(astVar.getType()).is( expectedType );
        }
        
        public boolean isType( _typeRef expectedType){
            return _typeRef.of(astVar.getType()).equals( expectedType );
        }
        
        public boolean isType( Type expectedType){
            return _typeRef.of(astVar.getType()).is( expectedType );
        }
        
        @Override
        public VariableDeclarator ast() {
            return astVar;
        }
    }
}
