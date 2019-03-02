package draft.java;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.nodeTypes.NodeWithParameters;
import com.github.javaparser.ast.type.Type;
import draft.Text;
import draft.java._modifiers._hasFinal;
import draft.java._anno.*;
import draft.java._model.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * model of a parameter declaration (for {@link _method}s or {@link _constructor}s)
 *
 * @author Eric
 */
public final class _parameter
        implements _namedType<_parameter>, _anno._hasAnnos<_parameter>, _hasFinal<_parameter>,
        _node<Parameter> {

    public static _parameter of( Class type, String name ) {
        return of( new Parameter( Ast.typeRef( type ), name ) );
    }

    public static _parameter of( Type type, String name ) {
        return of( new Parameter( type, name ) );
    }

    public static _parameter of( Parameter p ) {
        return new _parameter( p );
    }

    public static _parameter of( String parameter ) {
        return of( Ast.parameter( parameter ) );
    }

    @Override
    public Parameter ast() {
        return this.astParameter;
    }

    private final Parameter astParameter;

    public _parameter( Parameter p ) {
        this.astParameter = p;
    }

    @Override
    public _parameter name( String name ) {
        this.astParameter.setName( name );
        return this;
    }

    @Override
    public _typeRef getType() {
        return _typeRef.of( this.astParameter.getType() );
    }

    @Override
    public _parameter type( Type _tr ) {
        this.astParameter.setType( _tr );
        return this;
    }

    @Override
    public String getName() {
        return this.astParameter.getNameAsString();
    }

    @Override
    public _annos getAnnos() {
        return _annos.of( this.astParameter );
    }

    public boolean isVarArg() {
        return this.astParameter.isVarArgs();
    }

    @Override
    public boolean isFinal() {
        return this.astParameter.isFinal();
    }

    @Override
    public boolean isType( Class clazz ) {
        Type t = Ast.typeRef( clazz.getSimpleName() );
        return this.astParameter.getTypeAsString().equals( clazz.getSimpleName() )
                || this.astParameter.getTypeAsString().equals( clazz.getCanonicalName() );
    }

    @Override
    public boolean isType( String type ) {
        Type t = Ast.typeRef( type );
        return this.astParameter.getType().equals( t );
    }

    @Override
    public boolean isType( Type type ) {
        return this.astParameter.getType().equals( type );
    }

    public boolean isNamed( String name ) {
        return this.astParameter.getNameAsString().equals( name );
    }

    public boolean is( String... paramDecl ) {
        try {
            return equals( _parameter.of( Text.combine( paramDecl ) ) );
        }
        catch( Exception e ) {
        }
        return false;
    }

    public boolean is( Parameter astParam ){
        try {
            return equals( _parameter.of( astParam ) );
        }
        catch( Exception e ) {
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash
                + //Objects.hashCode(this.astParameter );
                Objects.hash( Ast.annotationsHash(astParameter),
                        this.getName(),
                        Ast.typeHash(astParameter.getType()),
                        this.isVarArg(),
                        this.isFinal() );
        return hash;
    }

    public static boolean isEqual( Parameter left, Parameter right ) {
        if( left == right ) {
            return true;
        }
        if( left == null ) {
            return false;
        }
        if( right == null ) {
            return false;
        }
        if( !left.getNameAsString().equals( right.getNameAsString())){
            return false;
        }
        if( left.isVarArgs() != right.isVarArgs()){
            return false;
        }
        if( left.isFinal() != right.isFinal()){
            return false;
        }
        if( !Ast.annotationsEqual( left, right)){
            return false;
        }
        if( ! Ast.typesEqual(left.getType(), right.getType())){
            return false;
        }
        return true;
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        final _parameter other = (_parameter)obj;
        if( !Objects.equals( this.astParameter.getName(), other.astParameter.getName() )){
            return false;
        }
        if( !Objects.equals( this.astParameter.isFinal(), other.astParameter.isFinal()) ){
            return false;
        }
        if( !Objects.equals( this.astParameter.isVarArgs(), other.astParameter.isVarArgs() )){
            return false;
        }
        if( !Ast.typesEqual(astParameter.getType(), other.astParameter.getType())){
            return false;
        }
        if(!Ast.annotationsEqual(astParameter, other.astParameter)){
            return false;
        }
        //if( !Objects.equals( _typeRef.of(this.astParameter.getType()), _typeRef.of(other.astParameter.getType()) )){
        //    return false;
        //}
        //if( !Objects.typesEqual( _typeRef.of(this.astParameter.getType()), _typeRef.of(other.astParameter.getType()) )){
        //    return false;
        //}
        //if( !Objects.equals( _annos.of(this.astParameter), _annos.of(other.astParameter) )){
        //    return false;
        //}

        return true;
        //return typesEqual( this.astParameter, other.astParameter );
    }

    @Override
    public String toString() {
        return this.astParameter.toString();
    }

    @Override
    public _parameter setFinal() {
        this.astParameter.setFinal( true );
        return this;
    }

    @Override
    public _parameter setFinal( boolean toSet ) {
        this.astParameter.setFinal( toSet );
        return this;
    }

    public Map<_java.Component, Object> componentsMap( ) {
        Map<_java.Component, Object> parts = new HashMap<>();
        parts.put( _java.Component.FINAL, isFinal() );
        parts.put(_java.Component.ANNOS, getAnnos() );
        parts.put( _java.Component.TYPE, getType() );
        parts.put( _java.Component.NAME, getName() );
        parts.put( _java.Component.VAR_ARG, isVarArg() );
        return parts;
    }

    /**
     *
     * @author Eric
     * @param <T>
     */
    public interface _hasParameters<T extends _hasParameters>
            extends _model {

        _parameters getParameters();

        _parameter getParameter( int index );

        default boolean hasParameters() {
            return !getParameters().isEmpty();
        }

        /**
         * When we get parameters we should do a
         * _class _c = _class.of(Local.class);
         *
         * Method m = Local.class.getMethod("sort");
         * java.lang.reflect.Type[] genericParameterTypes = m.getGenericParameterTypes();
         * _c.getMethod("sort").hasParametersOfType( genericParameterTypes );
         *
         * @param genericParameterTypes
         * @return
         
        boolean hasParametersOfType(java.lang.reflect.Type...genericParameterTypes );
        */
        
        //NOTE: this is removed becayuse it gives BAD results for Generic Parameters
        // List<String>, etc.
        //boolean hasParametersOfType( Class<?>... clazz );

        default List<_parameter> listParameters() {
            return getParameters().list();
        }

        default List<_parameter> listParameters(
                Predicate<? super _parameter> paramMatchFn ) {
            return getParameters().list( paramMatchFn );
        }

        default T forParameters( Consumer<? super _parameter> paramActionFn ) {
            listParameters().forEach( paramActionFn );
            return (T)this;
        }

        default T forParameters( Predicate<? super _parameter> paramMatchFn,
                                 Consumer<? super _parameter> paramActionFn ) {
            listParameters( paramMatchFn ).forEach( paramActionFn );
            return (T)this;
        }

        default T addParameters( String... parameters ) {
            Arrays.stream( parameters ).forEach( p -> addParameter( p ) );
            return (T)this;
        }

        default T addParameter( _typeRef type, String name ) {
            return addParameter( new Parameter( type.ast(), name ) );
        }

        default T addParameter( String parameter ) {
            addParameter( Ast.parameter( parameter ) );
            return (T)this;
        }

        default T addParameters( _parameter... parameters ) {
            Arrays.stream( parameters ).forEach( p -> addParameter( p ) );
            return (T)this;
        }

        default T addParameter( _parameter parameter ) {
            addParameter( parameter.ast() );
            return (T)this;
        }

        T addParameter( Parameter p );

        T addParameters( Parameter... ps );

        default boolean isVarArg() {
            return getParameters().isVarArg();
        }
    }

    
    /**
     *
     * Parameter is the AST node TYPE (the syntax and storage TYPE in the AST)
     * _parameter is the individual element TYPE (provides a interface for
     * modifying to the AST)
     * _parameters is a container of ELEMENTS
     *
     * @author Eric
     */
    public static final class _parameters
            implements _model {

        
        public static _parameters of( String... strs ) {
            //we need a holder for the Nodes
            StringBuilder params = new StringBuilder();
            for( int i = 0; i < strs.length; i++ ) {
                if( i > 0 && !(params.charAt( params.length() - 1 ) == ',') ) {
                    params.append( ',' );
                }
                params.append( strs[ i ] );
            }
            String ps = params.toString();
            if( ps.startsWith("(") ){
                ps = ps.substring(1);
            }
            if( ps.endsWith(")")){
                ps = ps.substring(0, ps.length() -1);
            }
            return of( Ast.method( "void $$(" + ps + ");" ) );
        }

        public static _parameters of( NodeWithParameters np ) {
            return new _parameters( np );
        }

        public boolean isVarArg() {
            if( !isEmpty() ) {
                return get( count() - 1 ).isVarArg();
            }
            return false;
        }

        public boolean is( String... params ) {
            try {
                return of( params ).equals( this );
            }
            catch( Exception e ) {

            }
            return false;
        }

        private final NodeWithParameters astNodeWithParams;

        public NodeList<Parameter> ast() {
            return astNodeWithParams.getParameters();
        }

        public _parameters( NodeWithParameters nwp ) {
            this.astNodeWithParams = nwp;
        }

        public _parameter get( int index ) {
            return _parameter.of( astNodeWithParams.getParameter( index ) );
        }

        public boolean isEmpty() {
            return this.astNodeWithParams.getParameters().isEmpty();
        }

        public int count() {
            return this.astNodeWithParams.getParameters().size();
        }

        public _parameters clear() {
            this.astNodeWithParams.getParameters().clear();
            return this;
        }

        public _parameters copy(){
            NodeList<Parameter> ps = new NodeList<>();
            MethodDeclaration md = Ast.method("void a(){}");
            ast().forEach( p-> md.addParameter(p) );
            return _parameters.of( md );
        }

        public int indexOf( _parameter _p ) {
            return indexOf( _p.ast() );
        }

        public int indexOf( Parameter p ) {
            for( int i = 0; i < this.astNodeWithParams.getParameters().size(); i++ ) {
                if( p.equals( this.astNodeWithParams.getParameter( i ) ) ) {
                    return i;
                }
            }
            return -1;
        }

        
        public _typeRef[] types(){
            _typeRef[] ts = new _typeRef[count()];
            for(int i=0;i<this.count();i++){
                ts[i] = _typeRef.of( this.astNodeWithParams.getParameter(i).getType() );
            }
            return ts;
        }
        
        public _parameters remove( Parameter... ps ) {
            for( int i = 0; i < ps.length; i++ ) {
                this.astNodeWithParams.getParameters().remove( ps[ i ] );
            }
            return this;
        }

        public _parameters remove( _parameter... _ps ) {
            for( int i = 0; i < _ps.length; i++ ) {
                this.astNodeWithParams.getParameters().remove( _ps[ i ].ast() );
            }
            return this;
        }

        public List<_parameter> list() {
            List<_parameter> ps = new ArrayList<>();
            this.astNodeWithParams.getParameters().forEach( p -> ps.add( _parameter.of( (Parameter)p ) ) );
            return ps;
        }

        public void forEach( Consumer<? super _parameter> paramAction ) {
            list().forEach( paramAction );
        }

        public void forEach( Predicate<? super _parameter> paramMatchFn,
                             Consumer<? super _parameter> paramAction ) {
            list( paramMatchFn ).forEach( paramAction );
        }

        public List<_parameter> list( Predicate<? super _parameter> paramMatchFn ) {
            return list().stream().filter( paramMatchFn ).collect( Collectors.toList() );
        }

        public _parameters remove( Predicate<? super _parameter> paramMatchFn ) {
            list( paramMatchFn ).forEach( p -> remove( p ) );
            return this;
        }

        public _parameter get( String name ) {
            Optional<Parameter> p = astNodeWithParams.getParameterByName( name );
            if( p.isPresent() ) {
                return _parameter.of( p.get() );
            }
            return null;
        }

        public _parameter get( Class clazz ) {
            Optional<Parameter> p = astNodeWithParams.getParameterByType( clazz );
            if( p.isPresent() ) {
                return _parameter.of( p.get() );
            }
            return null;
        }

        public _parameters set( int index, _parameter _p ) {
            astNodeWithParams.setParameter( index, _p.ast() );
            return this;
        }

        public boolean hasParametersOfType( _typeRef... typeRefs ) {
            String[] pts = new String[typeRefs.length];
            for(int i=0;i<typeRefs.length;i++){
                pts[i] = typeRefs[i].toString();
            }
            return astNodeWithParams.hasParametersOfType( pts );
        }
        
        public boolean hasParametersOfType( String... paramTypes ) {
            return astNodeWithParams.hasParametersOfType( paramTypes );
        }

        public boolean hasParametersOfType( Class... paramTypes ) {
            return astNodeWithParams.hasParametersOfType( paramTypes );
        }

        public _parameters add( String... _ps ) {
            for( int i = 0; i < _ps.length; i++ ) {
                astNodeWithParams.addParameter( Ast.parameter( _ps[ i ] ) );
            }
            return this;
        }

        public _parameters add( _parameter... _ps ) {
            for( int i = 0; i < _ps.length; i++ ) {
                astNodeWithParams.addParameter( _ps[ i ].ast() );
            }
            return this;
        }

        public _parameters add( Parameter... ps ) {
            for( int i = 0; i < ps.length; i++ ) {
                astNodeWithParams.addParameter( ps[ i ] );
            }
            return this;
        }

        @Override
        public boolean equals( Object obj ) {
            if( this == obj ) {
                return true;
            }
            if( obj == null ) {
                return false;
            }
            if( getClass() != obj.getClass() ) {
                return false;
            }
            final _parameters other = (_parameters)obj;
            if( this.astNodeWithParams.getParameters().size() != other.astNodeWithParams.getParameters().size() ) {
                return false;
            }
            for( int i = 0; i < this.count(); i++ ) {
                _parameter t = _parameter.of(this.astNodeWithParams.getParameter( i ));
                _parameter o = _parameter.of(other.astNodeWithParams.getParameter( i ));

                if( ! Objects.equals(t, o)){
                    return false;
                }                
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            //list because order matters
            List<_parameter> _ps = new ArrayList<>();
            this.astNodeWithParams.getParameters().forEach( p -> _ps.add( _parameter.of( (Parameter)p) ));
            hash = 79 * hash + Objects.hashCode( _ps );
            //hash = 79 * hash + Objects.hashCode( this.astNodeWithParams.getParameters() );
            return hash;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append( "(" );
            int size = this.astNodeWithParams.getParameters().size();
            for( int i = 0; i < size; i++ ) {
                if( i > 0 ) {
                    sb.append( ", " );
                }
                sb.append( this.astNodeWithParams.getParameter( i ).toString() );
            }
            sb.append( ")" );
            return sb.toString();
        }

        public NodeWithParameters astHolder() {
            return this.astNodeWithParams;
        }
    }
    
    /**
     * Inspects parameters
     */
    public static final _parametersInspect INSPECT_PARAMETERS = 
        new _parametersInspect();

    public static class _parametersInspect 
            implements _inspect<_parameters>{

        @Override
        public boolean equivalent(_parameters left, _parameters right) {
            return Objects.equals(left, right);
        }

        @Override
        public _inspect._diffTree diffTree( _java._inspector _ins, _inspect._path path, _inspect._diffTree dt, _parameters left, _parameters right) {
            for(int i=0;i<left.count();i++){
                _parameter _l = left.get(i);
                _parameter _r = null;
                if( i < right.count() ){
                    _r = right.get(i);
                }
                if( !Objects.equals(left, right) ){
                    dt.add(path.in(_java.Component.PARAMETER, i+""), _l, _r);
                }                
            }
            if( right.count() > left.count() ){
                for(int i=left.count(); i<right.count(); i++){
                    _parameter _r = right.get(i);
                    dt.add(path.in( _java.Component.PARAMETER,i+""), null, _r);
                }
            }
            return dt;
        }        
    }   
}
