package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import draft.*;
import draft.java.*;
import draft.java._model._node;
import draft.java.macro._ctor;
import draft.java.macro._remove;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Prototype for a Java constructor
 *
 */
public final class _pConstructor
        implements Template<_constructor>, _pQuery<_constructor> {

    public Stencil javadocStencil;

    /** Additional matching constraint on the constructor */
    public Predicate<_constructor> constraint = t -> true;
    
    /** stencil for the signature of the constructor */
    public Stencil signatureStencil;
    
    /** stencil for the body of the constructor */
    public _pSnip $body;

    /**
     * 
     * @param _c
     * @return 
     */
    public static _pConstructor of(_constructor _c ){
        return new _pConstructor( _c, t->true);
    }
    
    public static _pConstructor of(_constructor _c, Predicate<_constructor> constraint ){
        return new _pConstructor( _c, constraint);
    }

    public static _pConstructor of(String...code ){
        return new _pConstructor(_constructor.of(code), t-> true);
    }

    public static _pConstructor of( String protoCtor ){
        return of(new String[]{protoCtor});
    }

    public static _pConstructor of( String protoCtor, Predicate<_constructor> constraint ){
        return new _pConstructor( _constructor.of(protoCtor), constraint );
    }
    
    public static _pConstructor of( Predicate<_constructor> constraint ){
        return new _pConstructor( _constructor.of("c(){}"), constraint )
                .$(_constructor.of("c(){}").toString(), "any");
    }
    
    /**
     * Pass in an anonymous Object containing the method to import
     * NOTE: if the anonymous Object contains more than one method, ENSURE only one method
     * DOES NOT have the @_remove annotation, (mark all trivial METHODS with @_remove)
     *
     * @param anonymousObjectContainingCtor
     * @return
     */
    public static _pConstructor of( Object anonymousObjectContainingCtor ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject( ste );
        MethodDeclaration theMethod = (MethodDeclaration)
                oce.getAnonymousClassBody().get().stream().filter(m -> m instanceof MethodDeclaration &&
                        !m.isAnnotationPresent(_remove.class) ).findFirst().get();

        _constructor _ct = _constructor.of(theMethod.getNameAsString());
        _ct.annotate( theMethod.getAnnotations() );
        if( theMethod.hasJavaDocComment() ){
            _ct.ast().setJavadocComment( theMethod.getJavadocComment().get() );
        }

        theMethod.getParameters().forEach( p -> _ct.addParameter(p)); //params
        _ct.ast().setModifiers( Ast.merge( _ct.ast().getModifiers(), theMethod.getModifiers() ) );
        //_ct.ast().getModifiers().addAll( theMethod.getModifiers() ); //MODIFIERS
        _ct.ast().setBody( theMethod.getBody().get()); //BODY
        _ct.removeAnnos(_ctor.class);
        return of( _ct );
    }

    private _pConstructor(_constructor _protoCtor, Predicate<_constructor> constraint){
        if( _protoCtor.hasBody() ) {
            this.$body = _pSnip.of(_protoCtor.getBody());
            _constructor _cp = _protoCtor.copy();
            if(_cp.ast().getJavadocComment().isPresent() ){
                this.javadocStencil = Stencil.of(Ast.getContent( _cp.ast().getJavadocComment().get() ));
            }
            this.signatureStencil = Stencil.of( _cp.setBody("").toString(Ast.PRINT_NO_COMMENTS) );
        } else {
            this.signatureStencil = Stencil.of(_protoCtor.toString() );
            this.$body = null; //no BODY
        }
        if(constraint != null ){
            this.constraint = constraint;
        }
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public _pConstructor constraint( Predicate<_constructor> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    @Override
    public List<String> list$Normalized(){
        List<String>normalized$ = new ArrayList<>();
        if( this.javadocStencil != null ){
            normalized$.addAll(Stencil.of(this.javadocStencil, this.signatureStencil).list$Normalized());
        } else {
            normalized$.addAll(this.signatureStencil.list$Normalized());
        }
        if( this.$body != null){
            this.$body.list$Normalized().forEach( l-> {
                if (!normalized$.contains(l)) {
                    normalized$.add(l);
                }
            });
        }
        return normalized$;
    }

    @Override
    public List<String> list$(){
        List<String>normalized$ = new ArrayList<>();
        if( this.javadocStencil != null) {
            normalized$.addAll(this.javadocStencil.list$Normalized());
        }
        normalized$.addAll( this.signatureStencil.list$Normalized() );
        normalized$.addAll( this.$body.list$Normalized() );
        return normalized$;
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public _pConstructor assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public _pConstructor assign$( Object... keyValues ) {
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
    public _pConstructor assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public _pConstructor assign$( Translator translator, Tokens kvs ) {
        if(this.javadocStencil != null ){
            this.javadocStencil = this.javadocStencil.assign$(translator,kvs);
        }
        this.signatureStencil = this.signatureStencil.assign$(translator,kvs);
        this.$body = this.$body.assign$(translator, kvs);
        return this;
    }

    @Override
    public _constructor construct(Translator translator, Map<String, Object> keyValues) {
        //_1_build the signature
        _constructor _c = _constructor.of(this.signatureStencil.construct(translator, keyValues ));

        if( this.$body != null) {
            //_1_build the BODY
            List<Statement> sts = $body.construct(translator, keyValues);
            sts.forEach( s -> _c.add( s ) );
        }
        if( this.javadocStencil != null ){
            _c.javadoc(this.javadocStencil.construct(translator, keyValues));
        }
        return _c;
    }

    @Override
    public _constructor construct(Map<String, Object> keyValues) {
        return _pConstructor.this.construct( Translator.DEFAULT_TRANSLATOR, keyValues );
    }

    /**
     * 
     * @param _n
     * @return 
     */
    public _constructor construct( _node _n ){
        return _pConstructor.this.construct(_n.deconstruct());
    }

    @Override
    public _constructor construct(Object... keyValues) {
        return _pConstructor.this.construct( Translator.DEFAULT_TRANSLATOR, keyValues );
    }

    @Override
    public _constructor construct(Translator translator, Object... keyValues) {
        return _pConstructor.this.construct(translator, Tokens.of(keyValues));
    }

    @Override
    public _constructor fill(Object...values){
        return fill( Translator.DEFAULT_TRANSLATOR, values );
    }

    @Override
    public _constructor fill(Translator t, Object...values){
        List<String> keys = list$Normalized();
        if( values.length < keys.size() ){
            throw new DraftException("not enough values("+values.length+") to fill ("+keys.size()+") variables "+ keys);
        }
        Map<String,Object> kvs = new HashMap<>();
        for(int i=0;i<values.length;i++){
            kvs.put( keys.get(i), values[i]);
        }
        return _pConstructor.this.construct( t, kvs );
    }

    @Override
    public _pConstructor $(String target, String $Name) {
        if( this.javadocStencil != null ){
            this.javadocStencil = javadocStencil.$(target, $Name);
        }
        this.$body = this.$body.$(target, $Name);
        this.signatureStencil = this.signatureStencil.$(target, $Name);
        return this;
    }

    /**
     * 
     * @param astExpr
     * @param $name
     * @return 
     */
    public _pConstructor $(Expression astExpr, String $name ){
        String exprString = astExpr.toString();
        return $(exprString, $name);
    }

    /**
     * 
     * @param astStmt
     * @param $name
     * @return 
     */
    public _pConstructor $(Statement astStmt, String $name ){
        String exprString = astStmt.toString();
        return $(exprString, $name);
    }

    /**
     * 
     * @param _ctor
     * @return 
     */
    public boolean matches( _constructor _ctor ){
        return deconstruct(_ctor.ast() ) != null;
    }

    /**
     * 
     * @param astCtor
     * @return 
     */
    public boolean matches(ConstructorDeclaration astCtor ){
        return deconstruct(astCtor ) != null;
    }

    /**
     * 
     * @param _c
     * @return 
     */
    public Select select( _constructor _c){
        return select( _c.ast());
    }

    /**
     * 
     * @param astCtor
     * @return 
     */
    public Select select( ConstructorDeclaration astCtor){
        args ts = deconstruct( astCtor );
        if( ts != null ){
            return new Select( astCtor, ts );
        }
        return null;
    }

    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _constructor that matches (or null if none found)
     */
    public _constructor firstIn( _node _n ){
        Optional<ConstructorDeclaration> f = _n.ast().findFirst(ConstructorDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _constructor.of(f.get());
        }
        return null;
    }

    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _constructor that matches (or null if none found)
     */
    public _constructor firstIn( Node astNode ){
        Optional<ConstructorDeclaration> f = astNode.findFirst(ConstructorDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _constructor.of(f.get());
        }
        return null;
    }
    
    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _constructor that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n ){
        Optional<ConstructorDeclaration> f = _n.ast().findFirst(ConstructorDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }

    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _constructor that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode ){
        Optional<ConstructorDeclaration> f = astNode.findFirst(ConstructorDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }
    
    @Override
    public String toString(){
        if( this.javadocStencil != null ){
            return this.javadocStencil.toString() +System.lineSeparator() 
                    + this.signatureStencil.toString()+System.lineSeparator() +
                    this.$body.toString();
        }
        return this.signatureStencil.toString()+System.lineSeparator()+ this.$body.toString();
    }
    
    /**
     * 
     * @param _ctor
     * @return 
     */
    public args deconstruct( _constructor _ctor ){
        return deconstruct( _ctor.ast() );                
    }
    
    /**
     * 
     * NOTE we dont check the Javadoc for deconstruct
     * @param astTarget
     * @return 
     */
    public args deconstruct( ConstructorDeclaration astTarget ){
        if( !this.constraint.test( _constructor.of(astTarget))){
            return null;
        }
        Tokens ts = null;
        if( astTarget.getBody().isEmpty() ){
            ts = new Tokens();
        } else {
            ts = this.$body.deconstruct(astTarget.getBody().getStatement(0));
        }
        if( ts != null ){
            //final Tokens tdd = ts;
            String signature = astTarget.clone()
                    .setBody(EMPTY) //make the clones' BODY empty
                    .setAnnotations(new NodeList<>()) //remove all ANNOTATIONS
                    .removeComment() //removeIn any comments/JAVADOC from the clone
                    .toString();

            Tokens tss = this.signatureStencil.deconstruct( signature );
            if( tss == null ){
                return null;
            }
            AtomicBoolean isConsistent = new AtomicBoolean(true);
            tss.forEach( (String k, Object v)->{
                Object val = tss.get(k);
                if( val != null && !val.equals(v) ){
                    isConsistent.set(false);
                }
            });
            if( isConsistent.get() ){
                ts.putAll(tss);
                return args.of(ts);
            }
        }
        return null; //the BODY or signature isnt the same or BODY / signature tokens were inconsistent
    }

    @Override
    public List<_constructor> listIn(_node _n ){
        return listIn(_n.ast() );
    }

    @Override
    public List<_constructor> listIn(Node astNode){
        List<_constructor> typesList = new ArrayList<>();
        astNode.walk(ConstructorDeclaration.class, t->{
            if( this.matches(t) ){
                typesList.add(_constructor.of(t));
            }
        } );
        return typesList;
    }

    @Override
    public List<Select> selectListIn(Node astNode){
        List<Select>sts = new ArrayList<>();
        astNode.walk(ConstructorDeclaration.class, c-> {
            Select sel = select( c );
            if( sel != null ){
                sts.add(sel);
            }
        });
        return sts;
    }

    @Override
    public List<Select> selectListIn(_node _n){
        List<Select>sts = new ArrayList<>();
        Walk.in(_n, ConstructorDeclaration.class, c-> {
            Select sel = select( c );
            if( sel != null ){
                sts.add(sel);
            }
        });
        return sts;
    }

    @Override
    public <N extends Node> N forEachIn(N astNode, Consumer<_constructor> _constructorActionFn ){
        astNode.walk( ConstructorDeclaration.class, c-> {
            Select s = select( c );
            if( s != null ){
                _constructorActionFn.accept( _constructor.of(s.astCtor) );
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_constructor> _constructorActionFn ){
        Walk.in(_n, _constructor.class, c-> {
            Select s = select( c );
            if( s != null ){
                _constructorActionFn.accept( _constructor.of(s.astCtor) );
            }
        });
        return _n;
    }

    @Override
    public <N extends _node> N removeIn(N _n ){
        selectListIn(_n).forEach(s -> s.astCtor.remove() );
        return _n;
    }

    @Override
    public <N extends Node> N removeIn(N astNode ){
        selectListIn(astNode).forEach(s -> s.astCtor.remove() );
        return astNode;
    }

    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectedActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Consumer<Select> selectedActionFn ){
        astNode.walk( ConstructorDeclaration.class, c-> {
            Select s = select( c );
            if( s != null ){
                selectedActionFn.accept( s );
            }
        });
        return astNode;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param selectedActionFn
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select> selectedActionFn ){
        Walk.in(_n, _constructor.class, c-> {
            Select s = select( c );
            if( s != null ){
                selectedActionFn.accept( s );
            }
        });
        return _n;
    }

    private static final BlockStmt EMPTY = Stmt.block("{}");
   
    /**
     * A Matched Selection result returned from matching a prototype $constructor
     * inside of some Node or _node
     */    
    public static class Select implements _pQuery.selected, 
            _pQuery.selectedAstNode<ConstructorDeclaration>, 
            _pQuery.selected_model<_constructor> {
        
        public final ConstructorDeclaration astCtor;
        public final args args;

        public Select( ConstructorDeclaration astCtor, args tokens ){
            this.astCtor = astCtor;
            this.args = tokens;
        }

        @Override
        public args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$constructor.Select{"+ System.lineSeparator()+
                    Text.indent(astCtor.toString() )+ System.lineSeparator()+
                    Text.indent("ARGS : " + args) + System.lineSeparator()+
                    "}";
        }
        
        @Override
        public ConstructorDeclaration ast(){
            return astCtor;
        } 
        
        @Override
        public _constructor model() {
            return _constructor.of(astCtor);
        }
    }
}
