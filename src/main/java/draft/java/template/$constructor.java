package draft.java.template;

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
import draft.java.macro._ctor;
import draft.java.macro._remove;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * Template for a Java constructor
 *
 */
public final class $constructor
        implements Template<_constructor>, $query<_constructor> {

    public Stencil javadocStencil;

    public Stencil signatureStencil;
    public $snip $body;

    public static $constructor of(_constructor _c ){
        return new $constructor( _c);
    }

    public static $constructor of(String...code ){
        return new $constructor(_constructor.of(code));
    }

    public static $constructor of( String ctorDeclaration ){
        return of( new String[]{ctorDeclaration});
    }

    /**
     * Pass in an anonymous Object containing the method to import
     * NOTE: if the anonymous Object contains more than one method, ENSURE only one method
     * DOES NOT have the @_remove annotation, (mark all trivial METHODS with @_remove)
     *
     * @param anonymousObjectContainingCtor
     * @return
     */
    public static $constructor of( Object anonymousObjectContainingCtor ){
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

    private $constructor(_constructor _c ){
        if( _c.hasBody() ) {
            this.$body = $snip.of(_c.getBody());
            _constructor _cp = _c.copy();
            if(_cp.ast().getJavadocComment().isPresent() ){
                this.javadocStencil = Stencil.of(Ast.getContent( _cp.ast().getJavadocComment().get() ));
            }
            this.signatureStencil = Stencil.of( _cp.setBody("").toString(Ast.PRINT_NO_COMMENTS) );
        } else {
            this.signatureStencil = Stencil.of( _c.toString() );
            this.$body = null; //no BODY
        }
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
    public $constructor assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $constructor assign$( Object... keyValues ) {
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
    public $constructor assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    public $constructor assign$( Translator translator, Tokens kvs ) {
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
        return $constructor.this.construct( Translator.DEFAULT_TRANSLATOR, keyValues );
    }

    public _constructor construct( _model._node model ){
        return $constructor.this.construct(model.componentize());
    }

    @Override
    public _constructor construct(Object... keyValues) {
        return $constructor.this.construct( Translator.DEFAULT_TRANSLATOR, keyValues );
    }

    @Override
    public _constructor construct(Translator translator, Object... keyValues) {
        return $constructor.this.construct(translator, Tokens.of(keyValues));
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
        return $constructor.this.construct( t, kvs );
    }

    @Override
    public $constructor $(String target, String $Name) {
        if( this.javadocStencil != null ){
            this.javadocStencil = javadocStencil.$(target, $Name);
        }
        this.$body = this.$body.$(target, $Name);
        this.signatureStencil = this.signatureStencil.$(target, $Name);
        return this;
    }

    public $constructor $(Expression expr, String $name ){
        String exprString = expr.toString();
        return $(exprString, $name);
    }

    public $constructor $(Statement st, String $name ){
        String exprString = st.toString();
        return $(exprString, $name);
    }

    public boolean matches( _constructor _m ){
        return deconstruct( _m.ast() ) != null;
    }

    public boolean matches(ConstructorDeclaration astM ){
        return deconstruct( astM ) != null;
    }

    public Select select( _constructor _c){
        return select( _c.ast());
    }

    public Select select( ConstructorDeclaration astCtor){
        Tokens ts = deconstruct( astCtor );
        if( ts != null ){
            return new Select( astCtor, ts );
        }
        return null;
    }

    @Override
    public String toString(){
        if( this.javadocStencil != null ){
            return this.javadocStencil.toString() +System.lineSeparator() + this.signatureStencil.toString()+System.lineSeparator()+
                    this.$body.toString();
        }
        return this.signatureStencil.toString()+System.lineSeparator()+ this.$body.toString();
    }
    
    
    public Tokens deconstruct( _constructor _ctor ){
        return deconstruct( _ctor.ast() );
    }
    
    // NOTE we dont check the Javadoc for deconstruct
    public Tokens deconstruct( ConstructorDeclaration astTarget ){
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
                return ts;
            }
        }
        return null; //the BODY or signature isnt the same or BODY / signature tokens were inconsistent
    }

    @Override
    public List<_constructor> listIn(_model._node _t ){
        return listIn( _t.ast() );
    }

    @Override
    public List<_constructor> listIn(Node rootNode ){
        List<_constructor> typesList = new ArrayList<>();
        rootNode.walk(ConstructorDeclaration.class, t->{
            if( this.matches(t) ){
                typesList.add(_constructor.of(t));
            }
        } );
        return typesList;
    }

    @Override
    public List<Select> listSelectedIn(Node n){
        List<Select>sts = new ArrayList<>();
        n.walk(ConstructorDeclaration.class, c-> {
            Select sel = select( c );
            if( sel != null ){
                sts.add(sel);
            }
        });
        return sts;
    }

    @Override
    public List<Select> listSelectedIn(_model._node _t){
        List<Select>sts = new ArrayList<>();
        Walk.in(_t, ConstructorDeclaration.class, c-> {
            Select sel = select( c );
            if( sel != null ){
                sts.add(sel);
            }
        });
        return sts;
    }

    @Override
    public <N extends Node> N forIn(N n, Consumer<_constructor> _constructorActionFn ){
        n.walk( ConstructorDeclaration.class, c-> {
            Select s = select( c );
            if( s != null ){
                _constructorActionFn.accept( _constructor.of(s.ctor) );
            }
        });
        return n;
    }

    @Override
    public <M extends _model._node> M forIn(M _t, Consumer<_constructor> _constructorActionFn ){
        Walk.in(_t, _constructor.class, c-> {
            Select s = select( c );
            if( s != null ){
                _constructorActionFn.accept( _constructor.of( s.ctor) );
            }
        });
        return _t;
    }

    @Override
    public <M extends _model._node> M removeIn(M _t ){
        listSelectedIn(_t).forEach(s -> s.ctor.remove() );
        return _t;
    }

    @Override
    public <N extends Node> N removeIn(N node ){
        listSelectedIn(node).forEach(s -> s.ctor.remove() );
        return node;
    }

    public <N extends Node> N forSelectedIn(N n, Consumer<Select> selectedActionFn ){
        n.walk( ConstructorDeclaration.class, c-> {
            Select s = select( c );
            if( s != null ){
                selectedActionFn.accept( s );
            }
        });
        return n;
    }

    public <M extends _model._node> M forSelectedIn(M _t, Consumer<Select> selectedActionFn ){
        Walk.in(_t, _constructor.class, c-> {
            Select s = select( c );
            if( s != null ){
                selectedActionFn.accept( s );
            }
        });
        return _t;
    }

    private static final BlockStmt EMPTY = Stmt.block("{}");

    public static class Select implements $query.selected {
        public ConstructorDeclaration ctor;
        public Tokens tokens;

        public Select( ConstructorDeclaration astCtor, Tokens tokens ){
            this.ctor = astCtor;
            this.tokens = tokens;
        }

        @Override
        public String toString(){
            return "$constructor.Select{"+ System.lineSeparator()+
                    Text.indent( ctor.toString() )+ System.lineSeparator()+
                    Text.indent( "TOKENS : " + tokens) + System.lineSeparator()+
                    "}";
        }
    }
}
