package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
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
 *
 */
public class $var
    implements Template<VariableDeclarator>, $proto<VariableDeclarator> {
    
    public static final $var any(){
        return of("$type$ $name$");
    }
    
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
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String pattern, Predicate<VariableDeclarator> constraint){
        return $var.of(pattern, constraint).selectFirstIn(_n);
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
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, VariableDeclarator proto, Predicate<VariableDeclarator> constraint){
        return $var.of(proto, constraint).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String pattern ){
        return $var.of(pattern).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String pattern, Predicate<VariableDeclarator> constraint){
        return $var.of(pattern, constraint).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, VariableDeclarator proto ){
        return $var.of(proto).selectListIn(_n);
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
        return $var.of(proto, constraint).selectListIn(_n);
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
     * a Var Prototype only specifying the type
     * @param varType
     * @return 
     */
    public static $var of( Class varType ){
        final _typeRef e = _typeRef.of(varType );
        return any().constraint( v-> e.is(v.getType()) );
    }
    
    /**
     * a Var Prototype only specifying the type
     * @param varType
     * @return 
     */
    public static $var of( Type varType ){
        final _typeRef e = _typeRef.of(varType );
        return any().constraint( v-> e.is(v) );
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
        return deconstruct(astVar ) != null;
    }   

    /**
     * Deconstruct the expression into tokens, or return null if the field doesnt match
     *
     * @param astVar 
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public $args deconstruct(VariableDeclarator astVar ){
        
        Tokens all = new Tokens();
        if(this.constraint.test(astVar)) {            
            if( this.initPattern != null ){
                //System.out.println( "checking init "+ $init );
                if( astVar.getInitializer().isPresent()){
                    //System.out.println( " .... against "+ astVar.getInitializer().get() );    
                    all = this.initPattern.deconstruct( astVar.getInitializer().get().toString(NO_COMMENTS) );
                    if( all == null ){
                        return null;
                    }
                } else{
                    return null;
                }                
            }
            //System.out.println( "checking "+ astVar+" against "+ this.varStencil );
            Tokens matchedName = 
                    this.varPattern.deconstruct( astVar.getType()+ " " +astVar.toString(Ast.PRINT_NO_ANNOTATIONS_OR_COMMENTS) );
            if( matchedName != null ){
                all.putAll(matchedName);
                return $args.of(all);
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
    public $var assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $var assign$( Object... keyValues ) {
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
    public $var assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $var assign$( Translator translator, Tokens kvs ) {
        this.varPattern = this.varPattern.assign$(translator,kvs);
        if( this.initPattern != null ){
            this.initPattern = this.initPattern.assign$(translator,kvs);
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
     * 
     * @param astVar
     * @return 
     */
    public Select select(VariableDeclarator astVar){
        $args ts = this.deconstruct(astVar);
        if( ts != null){
            return new Select( astVar, ts );
        }
        return null;
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
        return selectListIn(_type.of(clazz));
    }
    
    @Override
    public List<Select> selectListIn(Node astNode ){
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
    public List<Select> selectListIn(_node _n ){
        List<Select>sts = new ArrayList<>();
        Walk.in(_n, VariableDeclarator.class, e -> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

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
            $args tokens = deconstruct( e );
            if( tokens != null ){
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

        @Override
        public VariableDeclarator ast() {
            return astVar;
        }
    }
}
