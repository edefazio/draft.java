package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.type.Type;
import draft.*;
import draft.java.Expr;
import draft.java._anno._annos;
import draft.java.*;
import draft.java._model._node;
import draft.java.proto.$proto.$component;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Eric
 */
public class $field implements Template<_field>, $proto<_field> {
  
    /**
     * 
     * @param clazz
     * @param proto
     * @return 
     */
    public static final List<_field> list( Class clazz, String proto ){
        return of(proto).listIn(_type.of( clazz ) );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<_field> list( N _n, String proto ){
        return of(proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_field> list( N _n, String proto, Predicate<_field> constraint){
        return of(proto, constraint).listIn(_n);
    }
    
    /**
     * 
     * @param clazz
     * @param proto
     * @param constraint
     * @return 
     */
    public static final List<_field> list( Class clazz, String proto, Predicate<_field> constraint ){
        return of(proto, constraint).listIn(_type.of( clazz ) );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<_field> list( N _n, _field proto ){
        return of(proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_field> list( N _n, _field proto, Predicate<_field> constraint){
        return of(proto, constraint).listIn(_n);
    }

    /**
     * 
     * @param clazz
     * @param proto
     * @return 
     */
    public static final _field first(Class clazz, String proto ){
        return of(proto).firstIn(_type.of(clazz) );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> _field first( N _n, String proto ){
        return of(proto).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _field first( N _n, String proto, Predicate<_field> constraint){
        return of(proto, constraint).firstIn(_n);
    }
    
    /**
     * 
     * @param clazz
     * @param proto
     * @param constraint
     * @return 
     */
    public static final _field first(Class clazz, String proto, Predicate<_field> constraint){
        return of(proto, constraint).firstIn(_type.of(clazz) );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> _field first( N _n, _field proto ){
        return of(proto).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _field first( N _n, _field proto, Predicate<_field> constraint){
        return of(proto, constraint).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String proto ){
        return of(proto).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param clazz
     * @param proto
     * @return 
     */
    public static final Select selectFirst( Class clazz, String proto ){
        return of(proto).selectFirstIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String proto, Predicate<_field> constraint){
        return of(proto, constraint).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param clazz
     * @param proto
     * @param constraint
     * @return 
     */
    public static final Select selectFirst( Class clazz, String proto, Predicate<_field>constraint){
        return of(proto, constraint).selectFirstIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, _field proto ){
        return of(proto).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, _field proto, Predicate<_field> constraint){
        return of(proto, constraint).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String proto ){
        return of(proto).selectListIn(_n);
    }
    
    /**
     *     
     * @param clazz
     * @param proto
     * @return 
     */
    public static final List<Select> selectList( Class clazz, String proto ){
        return of(proto).selectListIn(_type.of(clazz) );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String proto, Predicate<_field> constraint){
        return of(proto, constraint).selectListIn(_n);
    }
    
    /**
     *     
     * @param clazz
     * @param proto
     * @param constraint
     * @return 
     */
    public static final List<Select> selectList( Class clazz, String proto, Predicate<_field>constraint){
        return of(proto, constraint).selectListIn(_type.of(clazz) );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, _field proto ){
        return of(proto).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, _field proto, Predicate<_field> constraint){
        return of(proto, constraint).selectListIn(_n);
    }
    
    /**
     * Removes all occurrences of the source field in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param proto
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, _field proto ){
        return of(proto).removeIn(_n);
    }
    
    /**
     * Removes all occurrences of the source field in the Class
     * @param clazz
     * @param proto
     * @return the modified N
     */
    public static final _type remove( Class clazz, _field proto ){
        return of(proto).removeIn(_type.of(clazz));
    }
    
    /**
     * Removes all occurrences of the source field in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, _field proto, Predicate<_field> constraint){
        return of(proto, constraint).removeIn(_n);
    }
    
    /**
     * Removes all occurrences of the source field in the Class
     * @param clazz
     * @param proto
     * @param constraint
     * @return the modified N
     */
    public static final _type remove( Class clazz, _field proto, Predicate<_field> constraint){
        return of(proto, constraint).removeIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String proto ){
        return of(proto).removeIn(_n);
    }
    
    /**
     * 
     * @param clazz
     * @param _protoSource
     * @param _protoTarget
     * @return 
     */
    public static final _type replace(Class clazz, _field _protoSource, _field _protoTarget){
        return of(_protoSource)
            .replaceIn(_type.of(clazz), $field.of(_protoTarget));
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
        return of(_protoSource)
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
        return of(_field.of(protoSource))
            .replaceIn(rootNode, of(_field.of(protoTarget)));
    }
    
    /**
     * 
     * @param clazz
     * @param _protoSource
     * @param _protoTarget
     * @return 
     */
    public static final _type replace(Class clazz, String _protoSource, String _protoTarget){
        return of(_protoSource)
            .replaceIn(_type.of(clazz), $field.of(_protoTarget));
    }
    
    /** @return BUILD AND RETURN prototype instances */    
    public static $field any(){
        return of (_field.of(" $type$ $name$;") );
    }
        
    public static $field of( String...field ){
        return of( _field.of(field));
    }
    
    public static $field of( String field, Predicate<_field> constraint){
        return of( _field.of(field)).constraint(constraint);
    }
    
    public static $field of( _field _f, Predicate<_field> constraint){
        return of(_f).constraint(constraint);
    }
    
    public static $field of( Predicate<_field> constraint ){
        return any().constraint( constraint);
    }
    
    public static $field ofName( String str){
        return of( _field.of("$type$ "+str) );
    }
    
    public static $field ofName( Predicate<String> nameConstraint){
        return any().$name(nameConstraint);
    }
    
    public static $field ofType( Class clazz ){
        return ofType( _typeDecl.of(clazz) );
    }
    
    /**
     * represents a prototype field of this type (any name, doesn't matter)
     * @param type
     * @return 
     */
    public static $field ofType( _typeDecl type ){
        _field _f = _field.of( type+ " $name$;" );
        return of( _f );
    }
    
    public static $field ofType( String type ){
        return of( _field.of( type+" $name$;") );
    }
    
    public static $field ofType( Predicate<_typeDecl> _typeConstraint ){
        return any().$type(_typeConstraint);
    }
    
    public static $field of( _field _f ){
        $field $inst = new $field();
        if( _f.hasJavadoc() ){
            $inst.javadoc = new $component<>(_f.getJavadoc().toString() );
        }
        if( _f.hasAnnos() ){
            //ugg
            $inst.annos = new $component<>(_f.getAnnos().toString());
        }
        if( !_f.getModifiers().isEmpty() ){
            final _modifiers ms = _f.getModifiers();
            $inst.modifiers = new $component(
                    ms.toString(), 
                    (m)-> ms.equals(m));
        }
        $inst.type = new $component<>(_f.getType().toString());
        $inst.name = new $component(_f.getName());
        if( _f.hasInit() ){
            $inst.init = new $component(_f.getInit().toString());
        }
        return $inst;
    }
    
    public Predicate<_field> constraint = t->true;
    public $component<_javadoc> javadoc = new $component( "$javadoc$", t->true);
    public $component<_annos> annos = new $component( "$annos$", t->true);    
    public $component<_modifiers> modifiers = new $component( "$modifiers$", t->true);
    public $component<_typeDecl> type = new $component( "$type$", t->true);
    public $component<String> name = new $component( "$name$", t->true);    
    public $component<Expression> init = new $component( "$init$", t->true);
    
    private $field(){        
    }
    
    /** prototype post initialization self mutations */
    
    /**
     * Post parameterize the javadoc field (accept any and return it as "javadoc")
     * @return 
     */
    public $field $javadoc(){
        this.javadoc.$form = Stencil.of("$javadoc$");
        return this;
    }
    
    /**
     * Post parameterize the javadoc (expecting a specific form)
     * @param pattern
     * @return 
     */
    public $field $javadoc(String... pattern){
        this.javadoc.$form = Stencil.of(Text.combine(pattern) );
        return this;
    }
    
    /**
     * set a constraint on the 
     * @param javadocConstraint
     * @return 
     */
    public $field $javadoc(Predicate<_javadoc> javadocConstraint ){
        this.javadoc.constraint = this.javadoc.constraint.and(javadocConstraint);
        return this;
    }
    
    public $field $type(){
        this.type.$form = Stencil.of("$type$");
        return this;
    }
    
    public $field $type( String pattern ){
        this.type.$form = Stencil.of(_typeDecl.of(pattern).toString());
        return this;
    }
    
    public $field $type(Predicate<_typeDecl> typeConstraint ){
        this.type.constraint = this.type.constraint.and(typeConstraint);
        return this;
    }
    
    public $field $name(){
        this.name.$form = Stencil.of("$name$");
        return this;
    }
    
    public $field $name(String pattern ){
        this.name.$form = Stencil.of(pattern );
        return this;
    }
    
    public $field $name(Predicate<String> nameConstraint ){
        this.name.constraint = this.name.constraint.and(nameConstraint);
        return this;
    }
    
    public $field $init(){
        this.init.$form = Stencil.of( "$init$" );
        return this;
    }
    
    public $field $init( String initPattern ){
        this.init.$form = Stencil.of(Expr.of(initPattern).toString() );
        return this;
    }
    
    public $field $init( Expression initProto ){
        this.init.$form = Stencil.of(initProto.toString() );
        return this;
    }
    
    public $field $init( Predicate<Expression> initConstraint ){
        this.init.constraint = this.init.constraint.and(initConstraint);
        return this;
    }
    
    /**
     * Sets or resets the _field constraint
     * @param constraint the constraint
     * @return the modified $f
     */
    public $field constraint( Predicate<_field> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    /**
     * Adds ANOTHER constraint to the existing _field level constraint
     * @param constraint
     * @return 
     */ 
    public $field addConstraint( Predicate<_field> constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
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
    
    /**
     * Returns the first _field that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _field that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n ){
        Optional<VariableDeclarator> f = 
            _n.ast().findFirst( 
                VariableDeclarator.class, 
                (VariableDeclarator s) -> select(s) != null );                     
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }

    /**
     * Returns the first _field that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _field that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode ){
        Optional<VariableDeclarator> f = astNode.findFirst(VariableDeclarator.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }
    
    /**
     * Returns the first _field that matches the pattern and constraint
     * @param _n the _java node
     * @param selectConstraint
     * @return  the first _field that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n, Predicate<Select> selectConstraint){
        return selectFirstIn(_n.ast(), selectConstraint );        
    }

    /**
     * Returns the first _field that matches the pattern and constraint
     * @param astNode the node to look through
     * @param selectConstraint
     * @return  the first _field that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode, Predicate<Select> selectConstraint){
        Optional<VariableDeclarator> f = astNode.findFirst(VariableDeclarator.class, s ->{
            Select sel = this.select(s);
            return sel != null && selectConstraint.test(sel);
            });         
        
        if( f.isPresent()){
            return select(f.get());
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
        return selectListIn(_n.ast());        
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
            if( s != null && selectConstraint.test(s) ){
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
        return selectListIn(_n.ast(), selectConstraint);        
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
                sel._f.ast().replace($replaceProto.construct(sel.args).ast() );
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
                sel._f.ast().replace($replaceProto.construct(sel.args).ast() );
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
    public <N extends Node> N forEachIn(N astNode, Consumer<_field> _fieldActionFn){
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
    public <N extends _model._node> N forEachIn(N _n, Consumer<_field> _fieldActionFn){
        Walk.in(_n, VariableDeclarator.class, e-> {
            //$proto.$args tokens = deconstruct( e );
            Select sel = select( e );
            if( sel != null ){
                _fieldActionFn.accept( _field.of(e) );
            }
        });
        return _n;
    }
    
    /** @return true is the field declaration  matches the prototype*/
    public boolean matches( String...field ){
        try{
            return matches(_field.of(field));
        }catch(Exception e){
            return false;
        }
    }
    
    /**
     * does this variable match the prototype
     * @param vd
     * @return 
     */
    public boolean matches( VariableDeclarator vd ){
        return select(vd) != null;        
    }
    
    /**
     * does this field match the prototype?
     * @param _f
     * @return 
     */
    public boolean matches( _field _f ){
        return select(_f) != null;
    }

    /**
     * Returns a Select based on a field declaration 
     * OR NULL if this field declaration does not fit the prototype
     * @param field field declaration
     * @return a Select of the field
     */
    public Select select(String...field){
        try{
            return select(_field.of(field));
        }catch(Exception e){
            return null;
        }        
    }
    
    /**
     * 
     * @param _f
     * @return 
     */
    public Select select(_field _f){
        if( this.constraint.test(_f) ){
            Tokens all = new Tokens();
            all = javadoc.decomposeTo(_f.getJavadoc(), all);
            all = annos.decomposeTo(_f.getAnnos(), all);
            all = modifiers.decomposeTo(_f.getModifiers(), all);
            all = type.decomposeTo(_f.getType(), all);
            all = name.decomposeTo(_f.getName(), all);
            all = init.decomposeTo(_f.getInit(), all);
            if(all != null){
                return new Select( _f, $args.of(all));
            }
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
            Select sel = select( _field.of(v) );
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
        if( astVar.getParentNode().isPresent() && astVar.getParentNode().get() instanceof FieldDeclaration ){
            return select( _field.of(astVar));            
        }   
        return null;
    } 
    

    @Override
    public _field construct(Translator translator, Map<String, Object> keyValues) {
        Map<String,Object> baseMap = new HashMap<>();
        //we need to set these to empty
        baseMap.put("javadoc", "" );
        baseMap.put("annos", "");
        baseMap.put("modifiers", "");
        baseMap.put("type", "");
        baseMap.put("name", "");
        baseMap.put("init", "");
        
        baseMap.putAll(keyValues);
        
        StringBuilder sb = new StringBuilder();
        sb.append(javadoc.$form.construct(translator, baseMap) );
        sb.append(System.lineSeparator());
        sb.append(annos.$form.construct(translator, baseMap) );
        sb.append(System.lineSeparator());
        sb.append(modifiers.$form.construct(translator, baseMap) );
        sb.append(" ");
        sb.append(type.$form.construct(translator, baseMap) );
        sb.append(" ");
        sb.append(name.$form.construct(translator, baseMap) );
        
        String str = init.$form.construct(translator, baseMap);
        if( str.length() > 0 ){
            sb.append(" = ");
            sb.append(str);
        }
        sb.append(";");
        String s = sb.toString();
        System.out.println( s );
        return _field.of( sb.toString() );        
    }

    public static final List<String> DEFAULT_COMPONENT_$NAMES = new ArrayList();
    static{
        DEFAULT_COMPONENT_$NAMES.add("javadoc");
        DEFAULT_COMPONENT_$NAMES.add("annos");
        DEFAULT_COMPONENT_$NAMES.add("modifiers");
        DEFAULT_COMPONENT_$NAMES.add("type");
        DEFAULT_COMPONENT_$NAMES.add("name");
        DEFAULT_COMPONENT_$NAMES.add("init");
    }
    
    @Override
    public $field $(String target, String $Name) {
        javadoc.$form.$(target, $Name);
        annos.$form.$(target, $Name);
        modifiers.$form.$(target, $Name);
        type.$form.$(target, $Name);
        name.$form.$(target, $Name);
        init.$form.$(target, $Name);
        return this;
    }

    @Override
    public List<String> list$() {
        List<String> vars = new ArrayList<>();
        vars.addAll( javadoc.$form.list$() );
        vars.addAll( annos.$form.list$() );
        vars.addAll( modifiers.$form.list$() );
        vars.addAll( type.$form.list$() );
        vars.addAll( name.$form.list$() );
        vars.addAll( init.$form.list$() );
        
        return vars;
    }

    @Override
    public List<String> list$Normalized() {
        List<String> vars = new ArrayList<>();
        vars.addAll( javadoc.$form.list$Normalized() );
        vars.addAll( annos.$form.list$Normalized() );
        vars.addAll( modifiers.$form.list$Normalized() );
        vars.addAll( type.$form.list$Normalized() );
        vars.addAll( name.$form.list$Normalized() );
        vars.addAll( init.$form.list$Normalized() );
        return vars.stream().distinct().collect(Collectors.toList());
    }    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(javadoc.$form );
        sb.append(System.lineSeparator());
        sb.append(annos.$form );
        sb.append(System.lineSeparator());
        sb.append(modifiers.$form );
        sb.append(" ");
        sb.append(type.$form );
        sb.append(" ");
        sb.append(name.$form );
        sb.append(" = ");
        sb.append(init.$form );
        sb.append(";");
        
        return "($field): "+ System.lineSeparator()+ Text.indent( sb.toString() );
    }
    
     /**
     * A Matched Selection result returned from matching a prototype $field
     * inside of some Node or _node
     */
    public static class Select implements $proto.selected, 
            $proto.selectedAstNode<VariableDeclarator>, 
            $proto.selected_model<_field> {
        
        public final _field _f;
        public final $args args;

        public Select( _field _f, $args tokens){
            this._f = _f;
            this.args = tokens;
        }
        
        @Override
        public $args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$field.Select{"+ System.lineSeparator()+
                    Text.indent( _f.toString() )+ System.lineSeparator()+
                    Text.indent("$args : " + args) + System.lineSeparator()+
                    "}";
        }

        @Override
        public VariableDeclarator ast() {
            return _f.ast();
        }

        public boolean is( String fieldDeclaration ){
            return _f.is(fieldDeclaration);
        }
        
        public boolean hasJavadoc(){
            return _f.hasJavadoc();
        }
        
        public boolean isStatic(){
            return _f.isStatic();
        }
        
        public boolean isFinal(){            
            return _f.isFinal();
        }
        
        public boolean hasAnnos(){
            return _f.hasAnnos();
        }
        
        public boolean hasAnno(Class<? extends Annotation> annoClass){            
            return _f.hasAnno(annoClass);
        }
        
        public boolean isInit( Predicate<Expression> initMatchFn){            
            return _f.isInit(initMatchFn);
        }
        
        public boolean isInit(String init){
            return _f.isInit(init);
        }
        
        public boolean isInit(byte init){
            return _f.isInit(init);
        }
        
        public boolean isInit(short init){
            return _f.isInit(init);
        }
        
        public boolean isInit(long init){
            return _f.isInit(init);
        }
        
        public boolean isInit(char init){
            return _f.isInit(init);
        }
        
        public boolean isInit(double init){
            return _f.isInit(init);
        }
        
        public boolean isInit(float init){
            return _f.isInit(init);
        }
        
        public boolean isInit(boolean init){
            return _f.isInit(init);
        }
        
        public boolean isInit(int init){
            return _f.isInit(init);
        }
        
        public boolean isInit(Expression init){
            return _f.isInit(init);
        }
        
        public boolean isType( Class expectedType){
            return _f.isType(expectedType);
        }
        
        public boolean isType( String expectedType){
            return _f.isType(expectedType);
        }
        
        public boolean isType( _typeDecl expectedType){
            return _f.isType(expectedType);
        }
        
        public boolean isType( Type expectedType){
            return _f.isType(expectedType);
        }
        
        @Override
        public _field model() {
            return _f;
        }
    }
}
