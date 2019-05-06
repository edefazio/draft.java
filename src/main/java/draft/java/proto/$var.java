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
//import static draft.java.proto.$var.NO_COMMENTS;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
     * list all variables in the clazz
     * 
     * @param clazz
     * @return 
     */
    public static final List<VariableDeclarator> list(Class clazz){
       return any().listIn(clazz);
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
        return of(pattern).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param clazz the class
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( Class clazz, String pattern ){
        return of(pattern).listIn(_type.of(clazz));
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
        return of(pattern, constraint).listIn(_n);
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
        return of(pattern, constraint).listIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( N _n, VariableDeclarator proto ){
        return of(proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param clazz
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( Class clazz, VariableDeclarator proto ){
        return of( proto).listIn(clazz);
    }
    
    /**
     * 
     * @param clazz
     * @param proto
     * @param constraint
     * @return 
     */
    public static final List<VariableDeclarator> list(Class clazz, VariableDeclarator proto, Predicate<VariableDeclarator> constraint){
        return of(proto, constraint).listIn(clazz);
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
        return of(proto, constraint).listIn(_n);
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
     * @param clazz
     * @param proto
     * @param varActionFn
     * @return 
     */
    public static _type forEach(Class clazz, VariableDeclarator proto, Consumer<VariableDeclarator> varActionFn){
        return forEach( _type.of(clazz), proto, varActionFn);
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
     * @param clazz
     * @param pattern
     * @param selectedActionFn
     * @return 
     */
    public static _type forSelected(Class clazz, String pattern, Consumer<Select> selectedActionFn){
        return of(pattern).forSelectedIn(clazz, selectedActionFn);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectedActionFn
     * @return 
     */
    public static <N extends _node> N forSelected(N _n, String pattern, Consumer<Select> selectedActionFn){
        of(pattern).forSelectedIn(_n.ast(), selectedActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param pattern
     * @param selectedActionFn
     * @return 
     */
    public static <N extends Node> N forSelected(N astNode, String pattern, Consumer<Select> selectedActionFn){
        of(pattern).forSelectedIn(astNode, selectedActionFn);
        return  astNode;
    }
    /**
     * 
     * @param clazz
     * @param astProtoVar
     * @param selectedActionFn
     * @return 
     */
    public static _type forSelected(Class clazz, VariableDeclarator astProtoVar, Consumer<Select> selectedActionFn){
        return of(astProtoVar).forSelectedIn(clazz, selectedActionFn);
    }
    /**
     * 
     * @param <N>
     * @param _n
     * @param astProtoVar
     * @param selectedActionFn
     * @return 
     */
    public static <N extends _node> N forSelected(N _n, VariableDeclarator astProtoVar, Consumer<Select> selectedActionFn){
        of(astProtoVar).forSelectedIn(_n.ast(), selectedActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param astProtoVar
     * @param selectedActionFn
     * @return 
     */
    public static <N extends Node> N forSelected(N astNode, VariableDeclarator astProtoVar, Consumer<Select> selectedActionFn){
        of(astProtoVar).forSelectedIn(astNode, selectedActionFn);
        return  astNode;
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @param selectConstraint
     * @param selectedActionFn
     * @return 
     */
    public static _type forSelected(Class clazz, String pattern, Predicate<Select> selectConstraint, Consumer<Select> selectedActionFn){
        return forSelected(_type.of(clazz), pattern, selectConstraint, selectedActionFn);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public static <N extends _node> N forSelected(N _n, String pattern, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn){
        of(pattern).forSelectedIn(_n.ast(), selectActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param pattern
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public static <N extends Node> N forSelected(N astNode, String pattern, Predicate<Select> selectConstraint,Consumer<Select> selectActionFn){
        of(pattern).forSelectedIn(astNode, selectActionFn);
        return  astNode;
    }
    
    /**
     * 
     * @param clazz
     * @param astProtoVar
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public static _type forSelected(Class clazz, VariableDeclarator astProtoVar, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn){
        return forSelected(_type.of(clazz), astProtoVar, selectConstraint, selectActionFn);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param astProtoVar
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public static <N extends _node> N forSelected(N _n, VariableDeclarator astProtoVar, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn){
        of(astProtoVar).forSelectedIn(_n.ast(), selectConstraint, selectActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param astProtoVar
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public static <N extends Node> N forSelected(N astNode, VariableDeclarator astProtoVar, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn){
        of(astProtoVar).forSelectedIn(astNode, selectConstraint, selectActionFn);
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
        return of(pattern).firstIn(_n);
    }
    
    /**
     * finds the first instance of the var pattern within the clazz
     * @param <N>
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final <N extends _node> VariableDeclarator first( Class clazz, String pattern ){
        return of(pattern).firstIn( _type.of(clazz) );
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
        return of(pattern, constraint).firstIn(_n);
    }
    
    public static final VariableDeclarator first(Class clazz, VariableDeclarator astProtoVar ){
        return of(astProtoVar).firstIn(clazz);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param astProtoVar
     * @return 
     */
    public static final <N extends _node> VariableDeclarator first( N _n, VariableDeclarator astProtoVar ){
        return of(astProtoVar).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param astProtoVar
     * @param constraint
     * @return 
     */
    public static final <N extends _node> VariableDeclarator first( N _n, VariableDeclarator astProtoVar, Predicate<VariableDeclarator> constraint){
        return of(astProtoVar, constraint)
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
        return of(pattern).selectFirstIn(_n);
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
        return of(pattern).selectFirstIn(_n, selectConstraint);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, VariableDeclarator proto ){
        return of(proto).selectFirstIn(_n);
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
        return of(proto).selectFirstIn(_n, selectConstraint);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String pattern ){
        return of(pattern).listSelectedIn(_n);
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
        return of(pattern)
                .listSelectedIn(_n, selectConstraint);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, VariableDeclarator proto ){
        return of(proto).listSelectedIn(_n);
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
        return of(proto, constraint).listSelectedIn(_n);
    }
    
    /**
     * Removes all occurrences of the source field in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param proto
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, VariableDeclarator proto ){
        return of(proto).removeIn(_n);
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
        return of(proto, constraint).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String pattern ){
        return of(pattern).removeIn(_n);
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
        return of(sourceProto)
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
        return of(Ast.variable(sourcePattern))
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
    
    
    public $typeRef type = $typeRef.of("$type$");
    public $id name = $id.any();
    public $component<Expression> init = new $component( "$init$", t->true);

    public static final PrettyPrinterConfiguration NO_COMMENTS = new PrettyPrinterConfiguration()
        .setPrintComments(false).setPrintJavadoc(false);
  
    private $var( VariableDeclarator astProtoVar ){
        this.name = $id.of(astProtoVar.getNameAsString());
        this.type = $typeRef.of(astProtoVar.getTypeAsString());
        if( astProtoVar.getInitializer().isPresent() ){
            this.init = $component.of(astProtoVar.getInitializer().get());
            this.constraint = v -> v.getInitializer().isPresent();
        }           
    }
    
    public $var constraint( Predicate<VariableDeclarator> constraint){
        this.constraint = constraint;
        return this;
    }
    
    public $var addConstraint(Predicate<VariableDeclarator> constraint){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    /*
    public $v init( Stencil initPattern ){
        this.init.pattern = initPattern;
        return this;
    }
    */
    
    public $var init( Expression initExprProto ){
        init.pattern( initExprProto.toString(NO_COMMENTS) );
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
            if( astVar.getInitializer().isPresent()){
                //System.out.println("INIT" + astVar.getInitializer().get() );
                //System.out.println(this.init.pattern);
                all = this.init.decomposeTo(astVar.getInitializer().get(), all );
                if( all == null ){
                    return null;
                }                             
            }
            all = this.name.decomposeTo(astVar.getNameAsString(), all);
            all = this.type.decomposeTo(_typeRef.of(astVar.getType()), all);
            
            if( all != null ){
                return new Select(astVar, $nameValues.of(all));
            }            
        }
        return null;                
    }
    
    @Override
    public String toString() {
        if( this.init.isMatchAny()){
            return "($var) : \"" + this.type + " "+this.name+ ";\"";
        }
        return "($var) : \"" + this.type + " "+this.name+ " = "+this.init+";\"";
    }

    @Override
    public VariableDeclarator construct(Translator translator, Map<String, Object> keyValues) {
        Tokens base = new Tokens();
        base.put("init", "");
        base.putAll(keyValues);
        
        String in = init.compose(translator, base);
        if( in != null ){
            return Ast.variable(this.type.construct(translator, base)+ " "+ this.name.compose(translator, base)+" = "+in+";");
        }        
        return Ast.variable(this.type.construct(translator, base)+ " "+ this.name.compose(translator, base)+";");
            //varPattern.construct(translator, keyValues));
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
    public $var $(String target, String $Name) {
        this.name.$(target, $Name);
        this.type.$(target, $Name);
        this.init.$(target, $Name);
        return this;
    }
    
    @Override
    public VariableDeclarator fill(Translator translator, Object... values) {
        List<String> vars = this.type.list$Normalized();
        vars.addAll(this.name.list$Normalized());
        vars =  vars.stream().distinct().collect(Collectors.toList());
        
        List<String> allVars = new ArrayList<String>();
        allVars.addAll( vars );
        allVars.addAll( this.init.list$Normalized() );
        allVars = allVars.stream().distinct().collect(Collectors.toList());
        
        if( values.length == allVars.size() ){
            Map<String,Object> toCompose = new HashMap<>();
            for(int i=0;i<vars.size();i++){
                toCompose.put(allVars.get(i), values[i]);
            }
            return construct(translator, toCompose);
        }
        if( values.length == vars.size() ){ //no init
            Map<String,Object> toCompose = new HashMap<>();
            for(int i=0;i<vars.size();i++){
                toCompose.put(allVars.get(i), values[i]);
            }
            return Ast.variable( type.construct(translator, toCompose) + " "+ name.compose(translator, toCompose) );
        }
        throw new DraftException("Expected fill fields of size ("+allVars.size()+") or ("+vars.size()+") got ("+values.length+")");
        /*
        if( values.length > vars.size() ){
            //they must want an init
            List<String> initVars = this.init.list$Normalized();
            
        }
        if( this.initPattern != null ){
            Stencil combined = Stencil.of(varPattern, Stencil.of(" = "), initPattern );
            return Ast.variable(combined.fill(translator, values));
        }
        return Ast.variable(varPattern.fill(translator, values));
        */
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
        this.name = this.name.hardcode$(translator, kvs);
        this.type = this.type.hardcode$(translator, kvs);
        this.init = this.init.hardcode$(translator, kvs);        
        return this;
    }

    @Override
    public List<String> list$() {
        //List<String> $names = new ArrayList<>();
        List<String> $names = this.type.list$();
        $names.addAll( this.name.list$());
        $names.addAll( this.init.list$());
        return $names;        
    }

    @Override
    public List<String> list$Normalized() {
        List<String> $names = this.type.list$Normalized();
        $names.addAll( this.name.list$Normalized());
        $names.addAll( this.init.list$Normalized());
        
        return $names.stream().distinct().collect(Collectors.toList());        
    }
 
    /**
     * 
     * @param clazz
     * @return 
     */
    public VariableDeclarator firstIn( Class clazz){
        return firstIn( _type.of(clazz));
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
     * 
     * @param clazz
     * @return 
     */
    public Select selectFirstIn( Class clazz){
        return selectFirstIn(_type.of(clazz));
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
     * 
     * @param clazz
     * @param selectConstraint
     * @return 
     */
    public Select selectFirstIn( Class clazz, Predicate<Select> selectConstraint){
       return selectFirstIn(_type.of(clazz), selectConstraint); 
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

    @Override
    public List<Select> listSelectedIn(Class clazz){
        return $var.this.listSelectedIn(_type.of(clazz));
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
    public List<Select> listSelectedIn(Class clazz, Predicate<Select> selectConstraint){
        return listSelectedIn(_type.of(clazz), selectConstraint);
    }
    
    /**
     * 
     * @param astNode
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(Node astNode, Predicate<Select> selectConstraint){
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
    public List<Select> listSelectedIn(_node _n, Predicate<Select> selectConstraint){
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
        public final $nameValues args;

        public Select( VariableDeclarator v, $nameValues tokens){
            this.astVar = v;
            this.args = tokens;
        }
        
        @Override
        public $nameValues args(){
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
