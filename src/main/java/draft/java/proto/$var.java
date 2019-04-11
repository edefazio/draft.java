package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
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

    public static final $var ANY = of("$type$ $name$");
    
    /**
     * List ALL variables within the Node
     * @param <N>
     * @param _n
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( N _n ){
        return ANY.listIn(_n);
    }
    
    /**
     * List ALL variables that pass the predicate within the Node
     * @param <N>
     * @param _n
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( N _n, Predicate<VariableDeclarator> constraint){
        return of("$type$ $name$").constraint(constraint).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<VariableDeclarator> list( N _n, String proto ){
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
    public static final <N extends _node> List<VariableDeclarator> list( N _n, String proto, Predicate<VariableDeclarator> constraint){
        return $var.of(proto, constraint).listIn(_n);
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
        ANY.forEachIn(_n.ast(), varActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param varActionFn
     * @return 
     */
    public static <N extends Node> N forEach(N astNode, Consumer<VariableDeclarator> varActionFn){
        ANY.forEachIn(astNode, varActionFn);
        return  astNode;
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param varDecl
     * @param varActionFn
     * @return 
     */
    public static <N extends _node> N forEach(N _n, String varDecl, Consumer<VariableDeclarator> varActionFn){
        of(varDecl).forEachIn(_n.ast(), varActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param varDecl
     * @param varActionFn
     * @return 
     */
    public static <N extends Node> N forEach(N astNode, String varDecl, Consumer<VariableDeclarator> varActionFn){
        of(varDecl).forEachIn(astNode, varActionFn);
        return  astNode;
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param varDecl
     * @param varActionFn
     * @return 
     */
    public static <N extends _node> N forEach(N _n, VariableDeclarator varDecl, Consumer<VariableDeclarator> varActionFn){
        of(varDecl).forEachIn(_n.ast(), varActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param varDecl
     * @param varActionFn
     * @return 
     */
    public static <N extends Node> N forEach(N astNode, VariableDeclarator varDecl, Consumer<VariableDeclarator> varActionFn){
        of(varDecl).forEachIn(astNode, varActionFn);
        return  astNode;
    }
    
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param varDecl
     * @param varActionFn
     * @return 
     */
    public static <N extends _node> N forSelected(N _n, String varDecl, Consumer<Select> varActionFn){
        of(varDecl).forSelectedIn(_n.ast(), varActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param varDecl
     * @param varActionFn
     * @return 
     */
    public static <N extends Node> N forSelected(N astNode, String varDecl, Consumer<Select> varActionFn){
        of(varDecl).forSelectedIn(astNode, varActionFn);
        return  astNode;
    }
    
        /**
     * 
     * @param <N>
     * @param _n
     * @param varDecl
     * @param varActionFn
     * @return 
     */
    public static <N extends _node> N forSelected(N _n, VariableDeclarator varDecl, Consumer<Select> varActionFn){
        of(varDecl).forSelectedIn(_n.ast(), varActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param varDecl
     * @param varActionFn
     * @return 
     */
    public static <N extends Node> N forSelected(N astNode, VariableDeclarator varDecl, Consumer<Select> varActionFn){
        of(varDecl).forSelectedIn(astNode, varActionFn);
        return  astNode;
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> VariableDeclarator first( N _n, String proto ){
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
    public static final <N extends _node> VariableDeclarator first( N _n, String proto, Predicate<VariableDeclarator> constraint){
        return $var.of(proto, constraint).firstIn(_n);
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
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String proto ){
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
    public static final <N extends _node> Select selectFirst( N _n, String proto, Predicate<VariableDeclarator> constraint){
        return $var.of(proto, constraint).selectFirstIn(_n);
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
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String proto ){
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
    public static final <N extends _node> List<Select> selectList( N _n, String proto, Predicate<VariableDeclarator> constraint){
        return $var.of(proto, constraint).selectListIn(_n);
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
     * @param proto
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String proto ){
        return $var.of(proto).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param _protoSource
     * @param _protoTarget
     * @return 
     */
    public static final <N extends _node> N replace(N rootNode, VariableDeclarator _protoSource, VariableDeclarator _protoTarget){
        return $var.of(_protoSource)
            .replaceIn(rootNode, $var.of(_protoTarget));
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param protoSource
     * @param protoTarget
     * @return 
     */
    public static final <N extends _node> N replace(N rootNode, String protoSource, String protoTarget){
        return $var.of(Ast.variable(protoSource))
            .replaceIn(rootNode, $var.of(protoTarget));
    }
    
    /**
     * 
     * @param proto
     * @return 
     */
    public static $var of( String proto ){
        return of(new String[]{proto});
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $var of( String proto, Predicate<VariableDeclarator> constraint){
        return of(new String[]{proto}).constraint(constraint);
    }
    
    /**
     * 
     * @param proto
     * @return 
     */
    public static $var of(String...proto){
        return new $var( Ast.variable( proto ) );
    }

    /**
     * 
     * @param _protoField
     * @return 
     */
    public static $var of(VariableDeclarator _protoField){
        return new $var( _protoField  );
    }

    public static $var of(VariableDeclarator _protoField, Predicate<VariableDeclarator> constraint){
        return new $var( _protoField  ).constraint(constraint);
    }
    
    public Predicate<VariableDeclarator> constraint = t -> true;
    
    //var stencil matches on type and name
    public Stencil varStencil;
    
    public Stencil $init = null;

    public static final PrettyPrinterConfiguration NO_COMMENTS = new PrettyPrinterConfiguration()
        .setPrintComments(false).setPrintJavadoc(false);
  
    private $var( VariableDeclarator astProtoVar ){
        String str = astProtoVar.getTypeAsString() + " " + astProtoVar.toString(Ast.PRINT_NO_COMMENTS);                 
        varStencil = Stencil.of( str );
        if( astProtoVar.getInitializer().isPresent() ){
            //System.out.println( "Creating init ");
            $init = Stencil.of( astProtoVar.getInitializer().get() );
        }
    }
    
    public $var constraint( Predicate<VariableDeclarator> constraint){
        this.constraint = constraint;
        return this;
    }
    
    public $var init( Stencil $initProto ){
        this.$init = $initProto;
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
            if( this.$init != null ){
                //System.out.println( "checking init "+ $init );
                if( astVar.getInitializer().isPresent()){
                    //System.out.println( " .... against "+ astVar.getInitializer().get() );    
                    all = this.$init.deconstruct( astVar.getInitializer().get().toString(NO_COMMENTS) );
                    if( all == null ){
                        return null;
                    }
                } else{
                    return null;
                }                
            }
            //System.out.println( "checking "+ astVar+" against "+ this.varStencil );
            Tokens matchedName = 
                    this.varStencil.deconstruct( astVar.getType()+ " " +astVar.toString(Ast.PRINT_NO_ANNOTATIONS_OR_COMMENTS) );
            if( matchedName != null ){
                all.putAll(matchedName);
                return $args.of(all);
            }
        }
        return null;                
    }

    @Override
    public String toString() {
        return "($var) : \"" + this.varStencil + "\"";
    }

    @Override
    public VariableDeclarator construct(Translator translator, Map<String, Object> keyValues) {        
        return Ast.variable(varStencil.construct(translator, keyValues));
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
        
        if( this.$init != null ){
            Stencil combined = Stencil.of(varStencil, Stencil.of(" = "), $init );
            return Ast.variable(combined.fill(translator, values));
        }
        return Ast.variable(varStencil.fill(translator, values));
    }

    @Override
    public $var $(String target, String $Name) {
        this.varStencil = this.varStencil.$(target, $Name);
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
        this.varStencil = this.varStencil.assign$(translator,kvs);
        return this;
    }

    @Override
    public List<String> list$() {
        return this.varStencil.list$();
    }

    @Override
    public List<String> list$Normalized() {
        return this.varStencil.list$Normalized();
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

    @Override
    public <N extends Node> N removeIn(N astNode ){
        astNode.walk(VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.var.removeForced();
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N removeIn(N _n ){
        Walk.in(_n, VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.var.removeForced();
            }
        });
        return _n;
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
                sel.var.replace($replaceProto.construct(sel.args) );
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
                sel.var.replace($replaceProto.construct(sel.args) );
            }
        });
        return _le;
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
        
        public final VariableDeclarator var;
        public final $args args;

        public Select( VariableDeclarator v, $args tokens){
            this.var = v;
            this.args = tokens;
        }
        
        @Override
        public $args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$var.Select{"+ System.lineSeparator()+
                    Text.indent( var.toString() )+ System.lineSeparator()+
                    Text.indent("$args : " + args) + System.lineSeparator()+
                    "}";
        }

        @Override
        public VariableDeclarator ast() {
            return var;
        }
    }
}
