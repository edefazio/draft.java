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
import draft.java.proto.$proto;
import draft.java.proto.$snip;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Prototype for a Java constructor
 *
 */
public final class $OLDconstructor
    implements Template<_constructor>, $proto<_constructor> {

    public Stencil javadocPattern;

    /** Additional matching constraint on the constructor */
    public Predicate<_constructor> constraint = t -> true;
    
    /** stencil for the signature of the constructor */
    public Stencil signaturePattern;
    
    /** stencil for the body of the constructor */
    public $snip $body;

    /**
     * 
     * @param _c
     * @return 
     */
    public static $OLDconstructor of(_constructor _c ){
        return new $OLDconstructor( _c, t->true);
    }
    
    public static $OLDconstructor of(_constructor _c, Predicate<_constructor> constraint ){
        return new $OLDconstructor( _c, constraint);
    }

    public static $OLDconstructor of(String...pattern ){
        return new $OLDconstructor(_constructor.of(pattern), t-> true);
    }

    public static $OLDconstructor of( String pattern ){
        return of(new String[]{pattern});
    }

    public static $OLDconstructor of( String pattern, Predicate<_constructor> constraint ){
        return new $OLDconstructor( _constructor.of(pattern), constraint );
    }
    
    public static $OLDconstructor of( Predicate<_constructor> constraint ){
        return new $OLDconstructor( _constructor.of("c(){}"), constraint )
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
    public static $OLDconstructor of( Object anonymousObjectContainingCtor ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject( ste );
        MethodDeclaration theMethod = (MethodDeclaration)
            oce.getAnonymousClassBody().get().stream().filter(m -> m instanceof MethodDeclaration &&
                !m.isAnnotationPresent(_remove.class) ).findFirst().get();

        _constructor _ct = _constructor.of(theMethod.getNameAsString());
        //_ct.annotate( theMethod.getAnnotations() );
        if( theMethod.hasJavaDocComment() ){
            _ct.ast().setJavadocComment( theMethod.getJavadocComment().get() );
        }

        theMethod.getParameters().forEach( p -> _ct.addParameter(p)); //params
        _ct.ast().setModifiers( Ast.merge( _ct.ast().getModifiers(), theMethod.getModifiers() ) );
        //_ct.ast().getModifiers().addAll( theMethod.getModifiers() ); //MODIFIERS
        //if( theMethod.hasJavaDocComment() ){
        //    _ct.javadoc(theMethod.getJavadocComment().get());
        //}
        _ct.annotate( theMethod.getAnnotations()); //add annos        
        _ct.ast().setBody( theMethod.getBody().get()); //BODY
        _ct.removeAnnos(_ctor.class);
        return of( _ct );
    }

    private $OLDconstructor(_constructor _protoCtor, Predicate<_constructor> constraint){
        if( _protoCtor.hasBody() ) {
            this.$body = $snip.of(_protoCtor.getBody());
            _constructor _cp = _protoCtor.copy();
            if(_cp.ast().getJavadocComment().isPresent() ){
                this.javadocPattern = Stencil.of(Ast.getContent( _cp.ast().getJavadocComment().get() ));
            }
            this.signaturePattern = Stencil.of( _cp.setBody("").toString(Ast.PRINT_NO_COMMENTS) );
        } else {
            this.signaturePattern = Stencil.of(_protoCtor.toString() );
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
    public $OLDconstructor constraint( Predicate<_constructor> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    
    /**
     * ADDS an additional matching constraint to the prototype
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public $OLDconstructor addConstraint( Predicate<_constructor>constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    @Override
    public List<String> list$Normalized(){
        List<String>normalized$ = new ArrayList<>();
        if( this.javadocPattern != null ){
            normalized$.addAll(Stencil.of(this.javadocPattern, this.signaturePattern).list$Normalized());
        } else {
            normalized$.addAll(this.signaturePattern.list$Normalized());
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
        if( this.javadocPattern != null) {
            normalized$.addAll(this.javadocPattern.list$Normalized());
        }
        normalized$.addAll(this.signaturePattern.list$Normalized() );
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
    public $OLDconstructor hardcode$( Tokens kvs ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $OLDconstructor hardcode$( Object... keyValues ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, Tokens.of( keyValues ) );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param translator translates values to be hardcoded into the Stencil
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $OLDconstructor hardcode$( Translator translator, Object... keyValues ) {
        return hardcode$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $OLDconstructor hardcode$( Translator translator, Tokens kvs ) {
        if(this.javadocPattern != null ){
            this.javadocPattern = this.javadocPattern.hardcode$(translator,kvs);
        }
        this.signaturePattern = this.signaturePattern.hardcode$(translator,kvs);
        this.$body = this.$body.hardcode$(translator, kvs);
        return this;
    }

    @Override
    public _constructor construct(Translator translator, Map<String, Object> keyValues) {
        //_1_build the signature
        _constructor _c = _constructor.of(this.signaturePattern.construct(translator, keyValues ));

        if( this.$body != null) {
            //_1_build the BODY
            List<Statement> sts = $body.construct(translator, keyValues);
            sts.forEach( s -> _c.add( s ) );
        }
        if( this.javadocPattern != null ){
            _c.javadoc(this.javadocPattern.construct(translator, keyValues));
        }
        return _c;
    }

    @Override
    public $OLDconstructor $(String target, String $Name) {
        if( this.javadocPattern != null ){
            this.javadocPattern = javadocPattern.$(target, $Name);
        }
        this.$body = this.$body.$(target, $Name);
        this.signaturePattern = this.signaturePattern.$(target, $Name);
        return this;
    }

    /**
     * 
     * @param astExpr
     * @param $name
     * @return 
     */
    public $OLDconstructor $(Expression astExpr, String $name ){
        String exprString = astExpr.toString();
        return $(exprString, $name);
    }

    /**
     * 
     * @param astStmt
     * @param $name
     * @return 
     */
    public $OLDconstructor $(Statement astStmt, String $name ){
        String exprString = astStmt.toString();
        return $(exprString, $name);
    }

    /**
     * 
     * @param _ctor
     * @return 
     */
    public boolean matches( _constructor _ctor ){
        return select(_ctor.ast() ) != null;
    }

    /**
     * 
     * @param astCtor
     * @return 
     */
    public boolean matches(ConstructorDeclaration astCtor ){
        return select(astCtor ) != null;
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
    
    
    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param _n the _java node
     * @param selectConstraint
     * @return  the first _constructor that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n, Predicate<Select> selectConstraint){
        Optional<ConstructorDeclaration> f = _n.ast().findFirst(ConstructorDeclaration.class, s -> {
            Select sel = select(s);
            return sel != null && selectConstraint.test(sel);
                });         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }

    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param astNode the node to look through
     * @param selectConstraint
     * @return  the first _constructor that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode, Predicate<Select> selectConstraint){
        Optional<ConstructorDeclaration> f = astNode.findFirst(ConstructorDeclaration.class, s -> {
            Select sel = select(s);
            return sel != null && selectConstraint.test(sel);
                });            
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }
    
    @Override
    public String toString(){
        if( this.javadocPattern != null ){
            return this.javadocPattern.toString() +System.lineSeparator() 
                + this.signaturePattern.toString()+System.lineSeparator() 
                + this.$body.toString();
        }
        return this.signaturePattern.toString()+System.lineSeparator()+ this.$body.toString();
    }
    
    /**
     * 
     * @param _c
     * @return 
     */
    public Select select( _constructor _c){
        if( !this.constraint.test( _c )){
            return null;
        }
        Tokens ts = null;
        if( !_c.hasBody() || _c.listStatements().isEmpty() ){
            ts = new Tokens();
        } else {
            $snip.Select ss = this.$body.select( _c.ast().getBody().getStatement(0) );
            if( ss != null ){
                ts = new Tokens();
                ts.putAll(ss.args);
            }
            //ts = this.$body.deconstruct(_c.ast().getBody().getStatement(0));
        }
        if( ts != null ){
            //final Tokens tdd = ts;
            String signature = _c.ast().clone()
                    .setBody(EMPTY) //make the clones' BODY empty
                    .setAnnotations(new NodeList<>()) //remove all ANNOTATIONS
                    .removeComment() //removeIn any comments/JAVADOC from the clone
                    .toString();

            Tokens tss = this.signaturePattern.deconstruct( signature );
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
                return new Select(_c, ts);                
            }
        }
        return null; //the BODY or signature isnt the same or BODY / signature tokens were inconsistent
    }

    /**
     * 
     * @param astCtor
     * @return 
     */
    public Select select( ConstructorDeclaration astCtor){
        return select(_constructor.of(astCtor));        
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
    public List<Select> listSelectedIn(Node astNode){
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
    public List<Select> listSelectedIn(_node _n){
        List<Select>sts = new ArrayList<>();
        Walk.in(_n, ConstructorDeclaration.class, c-> {
            Select sel = select( c );
            if( sel != null ){
                sts.add(sel);
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
    public List<Select> listSelectedIn(Node astNode, Predicate<Select> selectConstraint){
        List<Select>sts = new ArrayList<>();
        astNode.walk(ConstructorDeclaration.class, c-> {
            Select sel = select( c );
            if( sel != null && selectConstraint.test(sel) ){
                sts.add(sel);
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
        Walk.in(_n, ConstructorDeclaration.class, c-> {
            Select sel = select( c );
            if( sel != null && selectConstraint.test(sel) ){
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
                _constructorActionFn.accept( s._ct );
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_constructor> _constructorActionFn ){
        Walk.in(_n, _constructor.class, c-> {
            Select s = select( c );
            if( s != null ){
                _constructorActionFn.accept( s._ct );
            }
        });
        return _n;
    }

    @Override
    public <N extends _node> N removeIn(N _n ){
        $OLDconstructor.this.listSelectedIn(_n).forEach(s -> s._ct.ast().remove() );
        return _n;
    }

    @Override
    public <N extends Node> N removeIn(N astNode ){
        $OLDconstructor.this.listSelectedIn(astNode).forEach(s -> s._ct.ast().remove() );
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
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectConstraint
     * @param selectedActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Predicate<Select> selectConstraint, Consumer<Select> selectedActionFn ){        
        astNode.walk( ConstructorDeclaration.class, c-> {
            Select s = select( c );
            if( s != null && selectConstraint.test(s)){
                selectedActionFn.accept( s );
            }
        });
        return astNode;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConstraint
     * @param selectedActionFn
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Predicate<Select> selectConstraint, Consumer<Select> selectedActionFn ){
        Walk.in(_n, _constructor.class, c-> {
            Select s = select( c );
            if( s != null && selectConstraint.test(s )){
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
    public static class Select implements $proto.selected, 
            $proto.selectedAstNode<ConstructorDeclaration>, 
            $proto.selected_model<_constructor> {
        
        public final _constructor _ct;
        public final $nameValues args;

        public Select(_constructor _c, Tokens tokens ){
            this._ct = _c;
            this.args = $nameValues.of(tokens);
        }
        
        public Select( ConstructorDeclaration astCtor, $nameValues tokens ){
            this._ct = _constructor.of(astCtor);
            this.args = tokens;
        }
        
        @Override
        public $nameValues args(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$constructor.Select{"+ System.lineSeparator()+
                    Text.indent(_ct.toString() )+ System.lineSeparator()+
                    Text.indent("$args : " + args) + System.lineSeparator()+
                    "}";
        }
        
        public boolean isVarArg(){
            return _ct.isVarArg();
        }
        
        public boolean hasBody(){
            return _ct.hasBody();
        }
        
        public boolean hasThrows(){
            return _ct.hasThrows();
        }
        
        public boolean hasThrow(Class<? extends Throwable> throwsClass){            
            return _ct.hasThrow(throwsClass);
        }
        
        public boolean hasParameters(){            
            return _ct.hasParameters();
        }
        
        public boolean is(String...ctorDeclaration){
            return _ct.is(ctorDeclaration);
        }
        
        public boolean hasTypeParameters(){            
            return _ct.hasTypeParameters();
        }
        
        @Override
        public ConstructorDeclaration ast(){
            return _ct.ast();
        } 
        
        @Override
        public _constructor model() {
            return _ct;
        }
    }
}
