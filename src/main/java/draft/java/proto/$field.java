package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.type.Type;
import draft.*;
import draft.java.Expr;
import draft.java.*;
import draft.java._java._node;
import draft.java.macro._macro;
import draft.java.macro._remove;
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
  
    public interface $part{} 
    
    /**
     * 
     * @return 
     */
    public static $field any(){
        return of();
    }
    
    /** @return BUILD AND RETURN prototype instances */    
    public static $field of(){
        return of (_field.of(" $type$ $name$;") );
    }
        
    public static final $field of( Object anonymousObjectWithField ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        FieldDeclaration fd = (FieldDeclaration) oce.getAnonymousClassBody().get().stream().filter(bd -> bd instanceof FieldDeclaration
                && !bd.getAnnotationByClass(_remove.class).isPresent()).findFirst().get();

        //add the field to a class so I can run macros
        _class _c = _class.of("Temp").add(_field.of(fd.clone().getVariable(0)));
        _macro.to(anonymousObjectWithField.getClass(), _c);

        return of( _c.getField(0) );        
    }
    
 
    public static $field of(String field ){
        return of( _field.of(field));
    }
    
    public static $field of( String...field ){
        return of( _field.of(field));
    }
    
    public static $field of( String field, Predicate<_field> constraint){
        return of( _field.of(field)).addConstraint(constraint);
    }
    
    public static $field of( _field _f, Predicate<_field> constraint){
        return of(_f).addConstraint(constraint);
    }
    
    public static $field of( Predicate<_field> constraint ){
        return of().addConstraint( constraint);
    }
    
    public static $field ofName( String str){
        return of( _field.of("$type$ "+str) );
    }
    
    public static $field ofName( Predicate<String> nameConstraint){
        return of().$name(nameConstraint);
    }
    
    public static $field ofType( Class clazz ){
        return ofType( _typeRef.of(clazz) );
    }
    
    /**
     * represents a prototype field of this type (any name, doesn't matter)
     * @param type
     * @return 
     */
    public static $field ofType( _typeRef type ){
        _field _f = _field.of( type+ " $name$;" );
        return of( _f );
    }
    
    public static $field ofType( String type ){
        return of( _field.of( type+" $name$;") );
    }
    
    public static $field ofType( Predicate<_typeRef> _typeConstraint ){
        return of().$type(_typeConstraint);
    }
    
    public static $field of( _field _f ){
        $field $inst = new $field();
        if( _f.hasJavadoc() ){
            $inst.javadoc = $comment.javadocComment(_f.getJavadoc());
        }
        if( _f.hasAnnos() ){
            $inst.annos = $annos.of(_f.getAnnos());
        }
        $inst.modifiers = $modifiers.of(_f);        
        $inst.type = $typeRef.of(_f.getType());
        $inst.name = $id.of( _f.getName() );
        if( _f.hasInit() ){
            $inst.init = $expr.of(_f.getInit() );
        }
        return $inst;
    }
    
    public static $field of($part part ){
        $part[] parts = new $part[]{part};
        return of(parts);
    }
    
    /**
     * 
     * @param parts
     * @return 
     */
    public static $field of($part...parts ){
        return new $field(parts);
    }
    
    public Predicate<_field> constraint = t->true;
    public $comment<JavadocComment> javadoc = $comment.javadocComment("$javadoc$");
    public $annos annos = new $annos(); 
    public $modifiers modifiers = $modifiers.of();
    public $typeRef type = $typeRef.of();
    public $id name = $id.of();
    public $expr init = null;
    
    private $field( $part...parts ){
        for(int i=0;i<parts.length;i++){
            if(parts[i] instanceof $annos){
                this.annos = ($annos)parts[i];
            }
            else if( parts[i] instanceof $anno ){
                this.annos.$annosList.add( ($anno)parts[i]);
            }
            else if( parts[i] instanceof $modifiers ){
                this.modifiers = ($modifiers)parts[i]; 
            }
            else if( parts[i] instanceof $typeRef){
                this.type = ($typeRef)parts[i];
            }
            else if( parts[i] instanceof $id){
                this.name = ($id)parts[i];
            }
            else if( parts[i] instanceof $expr ){
                this.init = ($expr)parts[i];
            }
            else if(parts[i] instanceof $comment ){
                this.javadoc = ($comment<JavadocComment>)parts[i];
            }
        }
    }
    
    private $field(){        
    }
    
    /** prototype post parameterization (i.e. the query can change) */
    
    /**
     * Post parameterize the javadoc field (accept any and return it as "javadoc")
     * @return 
     */
    public $field $javadoc(){
        this.javadoc = $comment.javadocComment("$javadoc$");        
        return this;
    }
    
    public $field $javadoc( String... javadoc ){
        this.javadoc.contentsPattern = Stencil.of((Object[])javadoc);
        return this;
    }
    
    public $field $javadoc( _javadoc _jd ){
        this.javadoc.contentsPattern = Stencil.of(_jd.getContent() );
        return this;
    }  
    
     public $field $annos(){
        this.annos = $annos.of();
        return this;
    }
    
    public $field $annos( Predicate<_anno._annos> as ){
        this.annos.addConstraint(as);
        return this;
    }
    
    public $field $annos(String...annoPatterns ){
        this.annos.add(annoPatterns);
        return this;
    }
    
    public $field $annos($annos $as ){
        this.annos = $as;
        return this;
    }
    
    public $field $anno( Class clazz){
        this.annos.$annosList.add($anno.of(clazz) );
        return this;
    }
    
    public $field $anno( _anno _an){
        this.annos.$annosList.add($anno.of(_an) );
        return this;
    }
    /**
     * set a constraint on the 
     * @param javadocConstraint
     * @return 
     */
    public $field $javadoc(Predicate<JavadocComment> javadocConstraint ){
        this.javadoc.addConstraint(javadocConstraint);
        return this;
    }
    
    public $field $type(){
        this.type = $typeRef.of("$type$");
        //this.type.typePattern = Stencil.of("$type$");
        return this;
    }
    
    public $field $type( _typeRef _tr){
        this.type = $typeRef.of(_tr);
        return this;
    }
    
    public $field $type( $typeRef tr){
        this.type = tr;
        return this;
    }
    
    public $field $type(Class clazz){
        this.type = $typeRef.of(clazz);
        return this;
    }
    
    public $field $type( String pattern ){
        this.type.typePattern = Stencil.of(_typeRef.of(pattern).toString());
        return this;
    }
    
    public $field $type(Predicate<_typeRef> typeConstraint ){
        this.type.constraint = this.type.constraint.and(typeConstraint);
        return this;
    }
    
    public $field $name(){
        this.name.pattern = Stencil.of("$name$");
        return this;
    }
    
    public $field $name( $id id ){
        this.name = id;
        return this;
    }
    
    public $field $name(String pattern ){
        this.name.pattern = Stencil.of(pattern);
        return this;
    }
    
    public $field $name(Predicate<String> nameConstraint ){
        this.name.constraint = this.name.constraint.and(nameConstraint);
        return this;
    }
    
    public $field $init(){
        this.init = $expr.of();        
        this.init.exprPattern = Stencil.of( "$init$" );
        return this;
    }
    
    public $field $init( String initPattern ){
        this.init.exprPattern = Stencil.of(Expr.of(initPattern).toString() );
        return this;
    }
    
    public $field $init( Expression initProto ){
        this.init.exprPattern = Stencil.of(initProto.toString() );
        return this;
    }
    
    public $field $init( Predicate<Expression> initConstraint ){
        this.init.constraint = this.init.constraint.and(initConstraint);
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
     * @param astNode the node to look through
     * @param _fieldMatchFn
     * @return  the first _field that matches (or null if none found)
     */
    @Override
    public _field firstIn( Node astNode, Predicate<_field> _fieldMatchFn){
        Optional<VariableDeclarator> f = astNode.findFirst(VariableDeclarator.class, s ->{
            Select sel = select(s);
            return sel != null && _fieldMatchFn.test(sel._f);
            });         
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
    @Override
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
    public Select selectFirstIn( Class clazz, Predicate<Select>selectConstraint ){
       return selectFirstIn(_java.type(clazz), selectConstraint); 
    }
    
    /**
     * Returns the first _field that matches the pattern and constraint
     * @param _n the _java node
     * @param selectConstraint
     * @return  the first _field that matches (or null if none found)
     */
    public Select selectFirstIn( _java _n, Predicate<Select> selectConstraint){
         if( _n instanceof _code ){
            if( ((_code) _n).isTopLevel()){
                return selectFirstIn(((_code) _n).astCompilationUnit(), selectConstraint);
            }
            return selectFirstIn(((_type) _n).ast(), selectConstraint);
        }
        return selectFirstIn( ((_node)_n).ast(), selectConstraint );
        
        //return selectFirstIn(_n.ast(), selectConstraint );        
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
            if( s != null && selectConstraint.test(s) ){
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
        return listSelectedIn(_java.type(clazz), selectConstraint);    
    }
    
    /**
     * 
     * @param _n
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(_java _n, Predicate<Select> selectConstraint){
         if( _n instanceof _code ){
            if( ((_code) _n).isTopLevel()){
                return listSelectedIn(((_code) _n).astCompilationUnit(), selectConstraint);
            }
            return listSelectedIn(((_type) _n).ast(), selectConstraint);
        }
        return listSelectedIn( ((_node)_n).ast(), selectConstraint);    
    }

    /**
     * 
     * @param clazz
     * @param $replaceProto
     * @return 
     */
    public _type replaceIn(Class clazz, $field $replaceProto ){
        return replaceIn(_java.type(clazz), $replaceProto);
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
    public <N extends _java> N replaceIn(N _le, $field $replaceProto ){
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
     * @param clazz
     * @param selectConsumer
     * @return 
     */
    public _type forSelectedIn(Class clazz, Consumer<Select> selectConsumer ){
       return forSelectedIn(_java.type(clazz), selectConsumer ); 
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConsumer
     * @return 
     */
    public <N extends _java> N forSelectedIn(N _n, Consumer<Select> selectConsumer ){
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
    public _type forSelectedIn(Class clazz, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer ){
        return forSelectedIn(_java.type(clazz), selectConstraint, selectConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public <N extends _java> N forSelectedIn(N _n, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer ){
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
    public <N extends Node> N forEachIn(N astNode, Predicate<_field> _fieldMatchFn, Consumer<_field> _fieldActionFn){
        astNode.walk(VariableDeclarator.class, e-> {
            //Tokens tokens = this.stencil.partsMap( e.toString());
            Select sel = select( e );
            if( sel != null && _fieldMatchFn.test(sel._f)){
                _fieldActionFn.accept( sel._f );
            }
        });
        return astNode;
    }
    
    /**
     * @param field 
     * @return true is the field declaration  matches the prototype
     */
    public boolean matches( String...field ){
        try{
            return matches(_field.of(field));
        }catch(Exception e){
            return false;
        }
    }
    
    /**
     * does this variable match the prototype
     * @param astVar
     * @return 
     */
    public boolean matches( VariableDeclarator astVar ){
        return select(astVar) != null;        
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
        if( this.constraint.test(_f) && modifiers.select(_f) != null ){            
            Tokens all = new Tokens();
            if (_f.getJavadoc() == null ){
                all = javadoc.decomposeTo(_f.getJavadoc().ast(), all);
            } else{
                all = javadoc.decomposeTo(null, all);
            }            
            all = annos.decomposeTo(_f.getAnnos(), all);
            all = type.decomposeTo(_f.getType(), all);
            all = name.decomposeTo(_f.getName(), all);
            if( init == null ){
                //if we dont care what the init is or is not
            }else{
                if( !_f.hasInit() ){
                    return null;
                }
                $expr.Select sel = init.select(_f.getInit());
                if( all.isConsistent(sel.args.asTokens()) ){
                    all.putAll( sel.args().asTokens() );
                } else{
                    return null;
                }
                //all = init.decomposeTo(_f.getInit(), all);
            }
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
        JavadocComment jdc = javadoc.construct(translator, baseMap);
        if( jdc != null){
            sb.append(jdc);
            sb.append(System.lineSeparator());
        }        
        sb.append(annos.construct(translator, baseMap) );
        sb.append(System.lineSeparator());
        sb.append(modifiers.construct(translator, baseMap) );
        sb.append(" ");
        sb.append(type.construct(translator, baseMap) );
        sb.append(" ");
        sb.append(name.pattern.construct(translator, baseMap) );
        if( init != null ){
            Expression expr = init.construct(translator, baseMap);
            if( expr != null ){
                sb.append( " = ");
                sb.append( expr );
            }
        }
        sb.append(";");
        String s = sb.toString();
        //System.out.println( s );
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
        javadoc.$(target, $Name);
        annos.$(target, $Name);
        type.$(target, $Name);
        name.$(target, $Name);
        if( init != null ){
            init.$(target, $Name);
        }
        return this;
    }

    @Override
    public List<String> list$() {
        List<String> vars = new ArrayList<>();
        vars.addAll( javadoc.list$() );
        vars.addAll( annos.list$() );
        vars.addAll( type.list$() );
        vars.addAll( name.list$() );
        if( init != null ){
            vars.addAll( init.list$() );
        }        
        return vars;
    }

    @Override
    public List<String> list$Normalized() {
        List<String> vars = new ArrayList<>();
        vars.addAll( javadoc.list$Normalized() );
        vars.addAll( annos.list$Normalized() );
        vars.addAll( type.list$Normalized() );
        vars.addAll( name.list$Normalized() );
        if( init != null ){
            vars.addAll( init.list$Normalized() );
        }
        return vars.stream().distinct().collect(Collectors.toList());
    }    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(javadoc );
        sb.append(System.lineSeparator());
        sb.append(annos );
        sb.append(System.lineSeparator());
        sb.append(modifiers.toString()); //construct(Translator.TypeTranslate, new HashMap<String,Object>() ) );
        sb.append(" ");
        sb.append(type.typePattern );
        sb.append(" ");
        sb.append(name.pattern );
        if( init != null ){
            sb.append(" = ");
            sb.append(init.toString());
        }
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
        public $args args(){
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
        
        public boolean isType( _typeRef expectedType){
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
