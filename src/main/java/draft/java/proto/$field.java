package draft.java.proto;

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
import java.util.function.Predicate;

/**
 * Template for a {@link _field}
 *
 */
public class $field
    implements Template<_field>, $query<_field> {

     /**
     * 
     * @param <N>
     * @param _rootNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<_field> list( N _rootNode, String proto ){
        return $field.of(proto).listIn(_rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param _rootNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<_field> list( N _rootNode, _field proto ){
        return $field.of(proto).listIn(_rootNode);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param _rootNode
     * @param proto
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _rootNode, _field proto ){
        return $field.of(proto).removeIn(_rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param _rootNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> N remove( N _rootNode, String proto ){
        return $field.of(proto).removeIn(_rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param _protoSource
     * @param _protoTarget
     * @return 
     */
    public static final <N extends _node> N replace(N rootNode, _field _protoSource, _field _protoTarget){
        return $field.of(_protoSource)
            .replaceIn(rootNode, $field.of(_protoTarget));
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
        return $field.of(_field.of(protoSource))
            .replaceIn(rootNode, $field.of(_field.of(protoTarget)));
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param astProtoSource
     * @param astProtoTarget
     * @return 
     */
    public static final <N extends _node> N replace( 
        N rootNode, FieldDeclaration astProtoSource, FieldDeclaration astProtoTarget){
        
        return $field.of(astProtoSource)
            .replaceIn(rootNode, $field.of(astProtoTarget));
    }
    
    /**
     * 
     * @param anonymousObject
     * @return 
     */
    public static $field of( Object anonymousObject ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        FieldDeclaration fd = (FieldDeclaration)
                oce.getAnonymousClassBody().get().stream().filter(m -> m.isFieldDeclaration() &&
                !m.getAnnotationByClass(_remove.class).isPresent()).findFirst().get();

        _field _f = _macro.to(anonymousObject.getClass(), _field.of(fd.getVariable(0)) );
        return of( _f);
    }

    /**
     * 
     * @param proto
     * @return 
     */
    public static $field of( String proto ){
        return of(new String[]{proto});
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $field of( String proto, Predicate<_field> constraint){
        return of(new String[]{proto}).constraint(constraint);
    }
    
    /**
     * 
     * @param proto
     * @return 
     */
    public static $field of(String...proto){
        _field _f = _field.of(proto );
        return new $field( _f );
    }

    /**
     * 
     * @param _protoField
     * @return 
     */
    public static $field of(_field _protoField){
        return new $field( _protoField  );
    }

    public static $field of(_field _protoField, Predicate<_field> constraint){
        return new $field( _protoField  ).constraint(constraint);
    }
    
    public Predicate<_field> constraint;
    public Stencil fieldStencil;
    public Stencil commentStencil;

    public static final PrettyPrinterConfiguration NO_COMMENTS = new PrettyPrinterConfiguration()
        .setPrintComments(false).setPrintJavadoc(false);

    public $field constraint( Predicate<_field> constraint){
        this.constraint = constraint;
        return this;
    }
    
    private $field( _field _protoField ){
        if( _protoField.getFieldDeclaration().hasJavaDocComment() ){
            Comment c = _protoField.getFieldDeclaration().getComment().get();
            commentStencil = Stencil.of( c.toString() );
        }
        fieldStencil = Stencil.of(_protoField.getFieldDeclaration().toString(NO_COMMENTS) );
    }
    
    /**
     * 
     * @param field
     * @return 
     */
    public boolean matches( String...field ){
        return matches(_field.of(field));
    }

    /**
     * 
     * @param astField
     * @return 
     */
    public boolean matches( FieldDeclaration astField ){
        return deconstruct(astField ) != null;
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
     * 
     * @param _f
     * @return 
     */
    public boolean matches( _field _f){
        return this.constraint.test(_f) && deconstruct(_f.ast() ) != null;
    }

    /**
     * Deconstruct the expression into tokens, or return null if the field doesnt match
     *
     * @param _f
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public Tokens deconstruct(_field _f){
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
            return fieldStencil.deconstruct( astFieldDeclaration.toString(Ast.PRINT_NO_ANNOTATIONS_OR_COMMENTS ) );
        }

        /** this is painful, but hopefully not too common */
        for(int i=0; i< astFieldDeclaration.getVariables().size();i++){
            //I need to build separate FieldDeclarations
            _field _f = _field.of( astFieldDeclaration.getModifiers()+" "+astFieldDeclaration.getVariable( i ) +";");
            if( this.constraint.test(_f)) {
                Tokens tks = deconstruct( _f.getFieldDeclaration() );
                if( tks != null ){
                    return tks;
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param astVar
     * @return 
     */
    public Tokens deconstruct(VariableDeclarator astVar ){
        if( !astVar.getParentNode().isPresent()){
            throw new DraftException("cannot check Field Variable "+astVar+" :: no parent FieldDeclaration");
        }
        FieldDeclaration fd = (FieldDeclaration) astVar.getParentNode().get();
        _field _f = _field.of(fd.getModifiers()+" "+astVar+";" );
        if( this.constraint.test( _f) ){
            return this.fieldStencil.deconstruct(_f.toString(Ast.PRINT_NO_ANNOTATIONS_OR_COMMENTS));
        }
        return null;
    }

    @Override
    public String toString() {
        if( commentStencil != null ){
            return "($field) : \"" + this.commentStencil + System.lineSeparator() + this.fieldStencil + "\"";
        }
        return "($field) : \"" + this.fieldStencil + "\"";
    }

    @Override
    public _field construct(Translator translator, Map<String, Object> keyValues) {
        if( this.commentStencil != null ){
            return _field.of(Stencil.of(this.commentStencil, this.fieldStencil ).construct(translator, keyValues));
        }
        return _field.of(fieldStencil.construct(translator, keyValues));
    }

    /**
     * 
     * @param _protoNode
     * @return 
     */
    public _field construct(_node _protoNode ){
        return $field.this.construct(Translator.DEFAULT_TRANSLATOR, _protoNode.componentize() );
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
            return _field.of(Stencil.of(commentStencil, this.fieldStencil ).fill(translator, values));
        }
        return _field.of(fieldStencil.fill(translator, values));
    }

    @Override
    public $field $(String target, String $Name) {
        if(this.commentStencil != null ){
            this.commentStencil = this.commentStencil.$(target,$Name);
        }
        this.fieldStencil = this.fieldStencil.$(target, $Name);
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

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $field assign$( Translator translator, Tokens kvs ) {
        if( this.commentStencil != null ){
            this.commentStencil = this.commentStencil.assign$(translator, kvs);
        }
        this.fieldStencil = this.fieldStencil.assign$(translator,kvs);
        return this;
    }

    @Override
    public List<String> list$() {
        if( this.commentStencil != null ){
            List<String> strs = this.commentStencil.list$();
            strs.addAll(this.fieldStencil.list$());
            return strs;
        }
        return this.fieldStencil.list$();
    }

    @Override
    public List<String> list$Normalized() {
        if( this.commentStencil != null ){
            return Stencil.of(this.commentStencil, this.fieldStencil).list$Normalized();
        }
        return this.fieldStencil.list$Normalized();
    }

    /**
     * 
     * @param _f
     * @return 
     */
    public Select select(_field _f){
        Tokens ts = deconstruct(_f.ast() );
        if( ts != null ){
            return new Select(_f, ts);
        }
        return null;
    }

    /**
     * 
     * @param astField
     * @return 
     */
    public List<Select> select(FieldDeclaration astField){
        List<Select> sels = new ArrayList<>();
        astField.getVariables().forEach( v-> {
            Select sel = select( v );
            if( sel != null ){
                sels.add(sel);
            }
        });
        return sels;
    }

    /**
     * 
     * @param astVar
     * @return 
     */
    public Select select(VariableDeclarator astVar){
        Tokens ts = this.deconstruct(astVar);
        if( ts != null){
            return new Select( _field.of(astVar), ts );
        }
        return null;
    }

    /**
     * Returns the first _field that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _field that matches (or null if none found)
     */
    public _field firstIn( _node _n ){
        Optional<VariableDeclarator> f = _n.ast().findFirst(VariableDeclarator.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _field.of(f.get());
        }
        return null;
    }

    /**
     * Returns the first _field that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _field that matches (or null if none found)
     */
    public _field firstIn( Node astNode ){
        Optional<VariableDeclarator> f = astNode.findFirst(VariableDeclarator.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _field.of(f.get());
        }
        return null;
    }
    
    @Override
    public List<_field> listIn(_node _n ){
        return listIn(_n.ast() );
    }

    @Override
    public List<_field> listIn(Node astNode ){
        List<_field> fieldsList = new ArrayList<>();
        astNode.walk(VariableDeclarator.class, v->{
            if( this.matches(v) ){
                fieldsList.add( _field.of(v) );
            }
        } );
        return fieldsList;
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

    @Override
    public <N extends Node> N removeIn(N astNode ){
        astNode.walk(VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel._f.ast().removeForced();
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N removeIn(N _n ){
        Walk.in(_n, VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel._f.ast().removeForced();
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
    public <N extends Node> N replaceIn(N astNode, $field $replaceProto ){
        astNode.walk(VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel._f.ast().replace($replaceProto.construct(sel.tokens).ast() );
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
    public <N extends _node> N replaceIn(N _le, $field $replaceProto ){
        Walk.in(_le, VariableDeclarator.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel._f.ast().replace($replaceProto.construct(sel.tokens).ast() );
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
    public <N extends Node> N forIn(N astNode, Consumer<_field> _fieldActionFn){
        astNode.walk(VariableDeclarator.class, e-> {
            //Tokens tokens = this.stencil.partsMap( e.toString());
            Select sel = select( e );
            if( sel != null ){
                _fieldActionFn.accept( _field.of(e) );
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forIn(N _n, Consumer<_field> _fieldActionFn){
        Walk.in(_n, VariableDeclarator.class, e-> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                _fieldActionFn.accept( _field.of(e) );
            }
        });
        return _n;
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
