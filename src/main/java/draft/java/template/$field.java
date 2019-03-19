package draft.java.template;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import draft.*;
import draft.java.*;
import draft.java._model._node;
import draft.java.macro._macro;
import draft.java.macro._remove;

import java.util.*;
import java.util.function.Consumer;

/**
 * Template for a {@link _field}
 *
 */
public class $field
    implements Template<_field>, $query<_field> {

     /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @return 
     */
    public static final <N extends _node> List<_field> list( N rootNode, String source ){
        return $field.of(source).listIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @return 
     */
    public static final <N extends _node> List<_field> list( N rootNode, _field source ){
        return $field.of(source).listIn(rootNode);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param rootNode
     * @param source
     * @return the modified N
     */
    public static final <N extends _node> N remove( N rootNode, _field source ){
        return $field.of(source).removeIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @return 
     */
    public static final <N extends _node> N remove( N rootNode, String source ){
        return $field.of(source).removeIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @param target
     * @return 
     */
    public static final <N extends _node> N replace(N rootNode, _field source, _field target){
        return $field.of(source)
            .replaceIn(rootNode, $field.of(target));
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @param target
     * @return 
     */
    public static final <N extends _node> N replace(N rootNode, String source, String target){
        return $field.of(_field.of(source))
            .replaceIn(rootNode, $field.of(_field.of(target)));
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @param target
     * @return 
     */
    public static final <N extends _node> N replace( 
        N rootNode, FieldDeclaration source, FieldDeclaration target){
        
        return $field.of(source)
            .replaceIn(rootNode, $field.of(target));
    }
    
    public static $field of( Object anonymousObject ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        FieldDeclaration fd = (FieldDeclaration)
                oce.getAnonymousClassBody().get().stream().filter(m -> m.isFieldDeclaration() &&
                !m.getAnnotationByClass(_remove.class).isPresent()).findFirst().get();

        _field _f = _macro.to(anonymousObject.getClass(), _field.of(fd.getVariable(0)) );
        return of( _f);
    }

    public static $field of( String code ){
        return of( new String[]{code});
    }
    public static $field of(String...code){
        _field _f = _field.of( code );
        return new $field( _f );
    }

    public static $field of(_field _f){
        return new $field( _f  );
    }

    private Stencil stencil;
    private Stencil commentStencil;

    public static final PrettyPrinterConfiguration NO_COMMENTS = new PrettyPrinterConfiguration()
            .setPrintComments(false).setPrintJavadoc(false);

    private $field( _field _f ){
        if( _f.getFieldDeclaration().hasJavaDocComment() ){
            Comment c = _f.getFieldDeclaration().getComment().get();
            commentStencil = Stencil.of( c.toString() );
        }
        stencil = Stencil.of( _f.getFieldDeclaration().toString(NO_COMMENTS) );
    }
    //private $field(String stencil) {
    //    this.stencil = Stencil.of(stencil );
    //}

    public boolean matches( String...field ){
        return matches(_field.of(field));
    }

    public boolean matches( FieldDeclaration expression ){
        return $field.this.deconstruct( expression ) != null;
    }

    public boolean matches( VariableDeclarator var ){
        return deconstruct( var ) != null;
    }

    public boolean matches( _field _f){
        return deconstruct(_f.ast() ) != null;
    }

    /**
     * Deconstruct the expression into tokens, or return null if the field doesnt match
     *
     * @param _f
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public Tokens deconstruct(_field _f ){
        return deconstruct( _f.ast() );
    }

    /**
     * Deconstruct the expression into tokens, or return null if the field doesnt match
     *
     * @param astFieldDeclaration 
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public Tokens deconstruct(FieldDeclaration astFieldDeclaration ){
        if( astFieldDeclaration.getVariables().size() == 1 ){
            return stencil.deconstruct( astFieldDeclaration.toString(Ast.PRINT_NO_ANNOTATIONS_OR_COMMENTS ) );
        }

        /** this is painful, but hopefully not too common */
        for(int i=0; i< astFieldDeclaration.getVariables().size();i++){
            //I need to build separate FieldDeclarations
            Tokens tks = $field.this.deconstruct( _field.of( astFieldDeclaration.getModifiers()+" "+astFieldDeclaration.getVariable( i ) +";").getFieldDeclaration() );
            if( tks != null ){
                return tks;
            }
        }
        return null;
    }

    public Tokens deconstruct(VariableDeclarator varD ){
        if( !varD.getParentNode().isPresent()){
            throw new DraftException("cannot check Field Variable "+varD+" :: no parent FieldDeclaration");
        }
        FieldDeclaration fd = (FieldDeclaration) varD.getParentNode().get();
        _field _f = _field.of( fd.getModifiers()+" "+varD+";" );
        return this.stencil.deconstruct(_f.toString(Ast.PRINT_NO_ANNOTATIONS_OR_COMMENTS));
    }

    @Override
    public String toString() {
        if( commentStencil != null ){
            return "($field) : \"" + this.commentStencil + System.lineSeparator() + this.stencil + "\"";
        }
        return "($field) : \"" + this.stencil + "\"";
    }

    @Override
    public _field construct(Translator translator, Map<String, Object> keyValues) {
        if( this.commentStencil != null ){
            return _field.of(Stencil.of(this.commentStencil, this.stencil ).construct(translator, keyValues));
        }
        return _field.of(stencil.construct(translator, keyValues));
    }

    public _field construct(_model._node modelNode ){
        return $field.this.construct( Translator.DEFAULT_TRANSLATOR, modelNode.componentize() );
    }

    @Override
    public _field construct(Map<String, Object> keyValues) {
        return $field.this.construct( Translator.DEFAULT_TRANSLATOR, keyValues);
    }

    @Override
    public _field construct(Object... keyValues) {
        return $field.this.construct( Translator.DEFAULT_TRANSLATOR, Tokens.of(keyValues));
    }

    @Override
    public _field construct(Translator translator, Object... keyValues) {
        return $field.this.construct( translator, Tokens.of(keyValues));
    }

    @Override
    public _field fill(Object... values) {
        return fill( Translator.DEFAULT_TRANSLATOR, values );
        //return _field.of( stencil.fill(Translator.DEFAULT_TRANSLATOR, values));
    }

    @Override
    public _field fill(Translator translator, Object... values) {
        if( this.commentStencil != null ){
            return _field.of( Stencil.of( commentStencil, this.stencil ).fill(translator, values));
        }
        return _field.of( stencil.fill(translator, values));
    }

    @Override
    public $field $(String target, String $Name) {
        if(this.commentStencil != null ){
            this.commentStencil = this.commentStencil.$(target,$Name);
        }
        this.stencil = this.stencil.$(target, $Name);
        return this;
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $field assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $field assign$( Object... keyValues ) {
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
    public $field assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    public $field assign$( Translator translator, Tokens kvs ) {
        if( this.commentStencil != null ){
            this.commentStencil = this.commentStencil.assign$(translator, kvs);
        }
        this.stencil = this.stencil.assign$(translator,kvs);
        return this;
    }

    @Override
    public List<String> list$() {
        if( this.commentStencil != null ){
            List<String> strs = this.commentStencil.list$();
            strs.addAll(this.stencil.list$());
            return strs;
        }
        return this.stencil.list$();
    }

    @Override
    public List<String> list$Normalized() {
        if( this.commentStencil != null ){
            return Stencil.of( this.commentStencil, this.stencil).list$Normalized();
        }
        return this.stencil.list$Normalized();
    }

    public Select select(_field _f){
        Tokens ts = deconstruct(_f.ast() );
        if( ts != null ){
            return new Select(_f, ts);
        }
        return null;
    }

    public List<Select> select(FieldDeclaration e){
        List<Select> sels = new ArrayList<>();
        e.getVariables().forEach( v-> {
            Select sel = select( v );
            if( sel != null ){
                sels.add(sel);
            }
        });
        return sels;
    }

    public Select select(VariableDeclarator v){
        Tokens ts = this.deconstruct(v);
        if( ts != null){
            return new Select( _field.of(v), ts );
        }
        return null;
    }

    @Override
    public List<_field> listIn(_model._node _t ){
        return listIn( _t.ast() );
    }

    @Override
    public List<_field> listIn(Node rootNode ){
        List<_field> fieldsList = new ArrayList<>();
        rootNode.walk(VariableDeclarator.class, v->{
            if( this.matches(v) ){
                fieldsList.add( _field.of(v) );
            }
        } );
        return fieldsList;
    }

    @Override
    public List<Select> listSelectedIn(Node n ){
        List<Select>sts = new ArrayList<>();
        n.walk(VariableDeclarator.class, e-> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    @Override
    public List<Select> listSelectedIn(_model._node _t ){
        List<Select>sts = new ArrayList<>();
        Walk.in( _t, VariableDeclarator.class, e -> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    @Override
    public <N extends Node> N removeIn(N node ){
        node.walk(VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel._f.ast().removeForced();
            }
        });
        return node;
    }

    @Override
    public <M extends _model._node> M removeIn(M _m ){
        Walk.in( _m, VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel._f.ast().removeForced();
            }
        });
        return _m;
    }

    public <N extends Node> N replaceIn(N node, $field $a ){
        node.walk(VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel._f.ast().replace($a.construct(sel.tokens).ast() );
            }
        });
        return node;
    }

    public <M extends _model._node> M replaceIn(M _le, $field $a ){
        Walk.in(_le, VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel._f.ast().replace($a.construct(sel.tokens).ast() );
            }
        });
        return _le;
    }

    public <M extends _model._node> M forSelectedIn(M _le, Consumer<Select> selectConsumer ){
        Walk.in( _le, VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return _le;
    }

    public <N extends Node> N forSelectedIn(N n, Consumer<Select> selectConsumer ){
        n.walk(VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return n;
    }

    @Override
    public <N extends Node> N forIn(N n, Consumer<_field> _fieldActionFn){
        n.walk(VariableDeclarator.class, e-> {
            //Tokens tokens = this.stencil.partsMap( e.toString());
            Select sel = select( e );
            if( sel != null ){
                _fieldActionFn.accept( _field.of(e) );
            }
        });
        return n;
    }

    @Override
    public <M extends _model._node> M forIn(M m, Consumer<_field> _fieldActionFn){
        Walk.in( m, VariableDeclarator.class, e-> {
            Tokens tokens = this.stencil.deconstruct( e.toString());
            if( tokens != null ){
                _fieldActionFn.accept( _field.of(e) );
            }
        });
        return m;
    }

    public static class Select implements $query.selected {
        public _field _f;
        public Tokens tokens;

        public Select( _field _f, Tokens tokens){
            this._f = _f;
            this.tokens = tokens;
        }
        
        @Override
        public String toString(){
            return "$field.Select{"+ System.lineSeparator()+
                    Text.indent( _f.toString() )+ System.lineSeparator()+
                    Text.indent( "TOKENS : " + tokens) + System.lineSeparator()+
                    "}";
        }
    }
}
