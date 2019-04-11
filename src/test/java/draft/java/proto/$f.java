package draft.java.proto;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import draft.Stencil;
import draft.Template;
import draft.Text;
import draft.Tokens;
import draft.Translator;
import draft.java.Expr;
import draft.java._anno._annos;
import draft.java._field;
import draft.java._javadoc;
import draft.java._modifiers;
import draft.java._typeRef;
import draft.java.proto.$proto;
import draft.java.proto.$proto.$component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Eric
 */
public class $f implements Template<_field> {
 
    public static $f any(){
        return of (_field.of(" $type$ $name$;") );
    }
        
    public static $f of( String...field ){
        return of( _field.of(field));
    }
    
    public static $f of( String field, Predicate<_field> constraint){
        return of( _field.of(field)).constraint(constraint);
    }
    
    public static $f of( Predicate<_field> constraint ){
        return any().constraint( constraint);
    }
    
    public static $f ofName( String str){
        return of( _field.of("$type$ "+str) );
    }
    
    public static $f ofName( Predicate<String> nameConstraint){
        return any().$name(nameConstraint);
    }
    
    public static $f ofType( Class clazz ){
        return ofType( _typeRef.of(clazz) );
    }
    
    /**
     * represents a prototype field of this type (any name, doesn't matter)
     * @param type
     * @return 
     */
    public static $f ofType( _typeRef type ){
        _field _f = _field.of( type+ " $name$;" );
        return of( _f );
    }
    
    public static $f ofType( String type ){
        return of( _field.of( type+" $name$;") );
    }
    
    public static $f ofType( Predicate<_typeRef> _typeConstraint ){
        return any().$type(_typeConstraint);
    }
    
    public static $f of( _field _f ){
        $f $inst = new $f();
        if( _f.hasJavadoc() ){
            $inst.javadoc = new $component<>(_f.getJavadoc().toString() );
        }
        if( _f.hasAnnos() ){
            //ugg
            $inst.annos = new $component<>(_f.getAnnos());
        }
        if( !_f.getModifiers().isEmpty() ){
            final _modifiers ms = _f.getModifiers();
            $inst.modifiers = new $component(
                    ms.toString(), 
                    (m)-> ms.equals(m));
        }
        $inst.type = new $component<>(_f.getType());
        $inst.name = new $component(_f.getName());
        if( _f.hasInit() ){
            $inst.init = new $component(_f.getInit());
        }
        return $inst;
    }
    
    public Predicate<_field> constraint = t->true;
    public $component<_javadoc> javadoc = new $component( "$javadoc$", t->true);
    public $component<_annos> annos = new $component( "$annos$", t->true);    
    public $component<_modifiers> modifiers = new $component( "$modifiers$", t->true);
    public $component<_typeRef> type = new $component( "$type$", t->true);
    public $component<String> name = new $component( "$name$", t->true);    
    public $component<Expression> init = new $component( "$init$", t->true);
    
    private $f(){        
    }
    
    /**
     * Post parameterize the 
     * @param form
     * @return 
     */
    public $f $javadoc(String... form){
        this.javadoc.$form = Stencil.of(Text.combine(form) );
        return this;
    }
    
    public $f $javadoc(Predicate<_javadoc> javadocConstraint ){
        this.javadoc.constraint = this.javadoc.constraint.and(javadocConstraint);
        return this;
    }
    
    public $f $type( String typeRefForm ){
        this.type.$form = Stencil.of(_typeRef.of(typeRefForm).toString());
        return this;
    }
    
    public $f $type(Predicate<_typeRef> typeConstraint ){
        this.type.constraint = this.type.constraint.and(typeConstraint);
        return this;
    }
    
    public $f $name( String name ){
        this.name.$form = Stencil.of( name );
        return this;
    }
    
    public $f $name(Predicate<String> nameConstraint ){
        this.name.constraint = this.name.constraint.and(nameConstraint);
        return this;
    }
    
    public $f $init( String init ){
        this.init.$form = Stencil.of( Expr.of(init).toString() );
        return this;
    }
    
    public $f $init( Expression init ){
        this.init.$form = Stencil.of( init.toString() );
        return this;
    }
    
    public $f $init( Predicate<Expression> exprConstraint ){
        this.init.constraint = this.init.constraint.and(exprConstraint);
        return this;
    }
    
    /**
     * Sets or resets the _field constraint
     * @param constraint the constraint
     * @return the modified $f
     */
    public $f constraint( Predicate<_field> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    /**
     * Adds ANOTHER constraint to the existing _field level constraint
     * @param constraint
     * @return 
     */ 
    public $f addConstraint( Predicate<_field> constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    public boolean matches( String...field ){
        try{
            return matches(_field.of(field));
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean matches( _field _f ){
        return deconstruct(_f) != null;
    }
    
    public $proto.$args deconstruct( String...field ){
        try{
            _field _f = _field.of(field);
            return deconstruct( _f);
        }catch(Exception e){
            return null;
        }
    }
    
    public $proto.$args deconstruct( VariableDeclarator astVar){
        return deconstruct( _field.of(astVar ) ); 
    }
    
    public $proto.$args deconstruct ( _field _f){
        if( this.constraint.test(_f) ){
            Tokens all = new Tokens();
            all = javadoc.decomposeTo(_f.getJavadoc(), all);
            all = annos.decomposeTo(_f.getAnnos(), all);
            all = modifiers.decomposeTo(_f.getModifiers(), all);
            all = type.decomposeTo(_f.getType(), all);
            all = name.decomposeTo(_f.getName(), all);
            all = init.decomposeTo(_f.getInit(), all);
            if(all != null){
                return $proto.$args.of(all);
            }
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
    
   
    
    public $f $(String target, String $Name) {
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
}
