package draft.java;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithTypeParameters;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import draft.Text;
import static draft.java.Ast.typesEqual;
import draft.java._model.*;
import draft.java._java._path;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Model of a Java TypeParameter
 *
 * @author Eric
 */
public final class _typeParameter
        implements _node<TypeParameter> {

    public static _typeParameter of( String typeParam ) {
        return of( Ast.typeParameter( typeParam ) );
    }

    public static _typeParameter of( TypeParameter typeParameter ) {
        return new _typeParameter( typeParameter );
    }

    private final TypeParameter typeParameter;

    /**
     *
     * @return
     */
    public NodeList<ClassOrInterfaceType> getTypeBound() {
        return typeParameter.getTypeBound();
    }

    @Override
    public TypeParameter ast() {
        return this.typeParameter;
    }

    public _typeParameter( TypeParameter tp ) {
        this.typeParameter = tp;
    }

    public boolean is( String typeParameterDecl ){
        try{
            return of( typeParameterDecl ).equals(this);
        }
        catch( Exception e) {
            return false;
        }
    }

    public boolean is( TypeParameter typeParameterDecl ){
        try{
            return of( typeParameterDecl ).equals(this);
        }
        catch( Exception e) {
            return false;
        }
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
        final _typeParameter other = (_typeParameter)obj;

        if( Objects.equals(this.typeParameter, other.typeParameter)){
            return true;
        }
        List<String>ttp = Ast.normalizeTypeParameter( this.typeParameter);
        List<String>otp = Ast.normalizeTypeParameter( other.typeParameter);
        return Objects.equals( ttp, otp );
    }

    @Override
    public Map<_java.Component, Object> componentsMap( ) {
        Map<_java.Component, Object> parts = new HashMap<>();
        parts.put( _java.Component.TYPE_PARAMETER, this.typeParameter );
        return parts;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode( Ast.normalizeTypeParameter(this.typeParameter) );
    }

    @Override
    public String toString() {
        return typeParameter.toString();
    }

    /**
     *
     * @author Eric
     */
    public static final class _typeParameters
            implements _model {

        public static _typeParameters of( String...tps){
            ClassOrInterfaceDeclaration coid = Ast.classDeclaration("class Dummy"+ Text.combine(tps) +"{}");
            return of( coid );
        }

        public static _typeParameters of( NodeWithTypeParameters tps ) {
            return new _typeParameters( tps );
        }

        private final NodeWithTypeParameters astNodeWithTypeParams;

        public _typeParameters( NodeWithTypeParameters ntp ) {
            this.astNodeWithTypeParams = ntp;
        }

        public int indexOf( _typeParameter tp ) {
            return list().indexOf( tp );
        }

        public int indexOf( TypeParameter tp ) {
            return astNodeWithTypeParams.getTypeParameters().indexOf( tp );
        }

        public _typeParameters clear() {
            astNodeWithTypeParams.getTypeParameters().clear();
            return this;
        }

        public List<_typeParameter> list() {
            List<_typeParameter> lp = new ArrayList<>();
            astNodeWithTypeParams.getTypeParameters().forEach( t -> lp.add( _typeParameter.of( (TypeParameter)t ) ) );
            return lp;
        }

        public List<_typeParameter> list( Predicate<? super _typeParameter> tps ) {
            return list().stream().filter( tps ).collect( Collectors.toList() );
        }

        public _typeParameters remove( _typeParameter... tps ) {
            Arrays.stream( tps ).forEach( t -> remove( t.ast() ) );
            return this;
        }

        public _typeParameters remove( TypeParameter... tps ) {
            Arrays.stream( tps ).forEach( t -> remove( t ) );
            return this;
        }

        public _typeParameters remove( Predicate<? super _typeParameter> tps ) {
            remove( list( tps ) );
            return this;
        }

        public _typeParameters remove( List<? super _typeParameter> tps ) {
            astNodeWithTypeParams.getTypeParameters().removeAll( tps );
            return this;
        }

        public NodeList<TypeParameter> ast() {
            return astNodeWithTypeParams.getTypeParameters();
        }

        public int count() {
            return astNodeWithTypeParams.getTypeParameters().size();
        }

        public boolean isEmpty() {
            return this.astNodeWithTypeParams.getTypeParameters().isEmpty();
        }

        public _typeParameter get( int index ) {
            return _typeParameter.of( (TypeParameter)this.astNodeWithTypeParams.getTypeParameters().get( index ) );
        }

        public boolean is( String typeParameters ) {
            try {
                _typeParameters _tps = _typeParameters.of(typeParameters);
                return _tps.equals(this);
                //NodeList<TypeParameter> ntp = Ast.TYPE_PARAMETERS( TYPE_PARAMETERS );
                //return ntp.typesEqual( this.astNodeWithTypeParams.getTypeParameters() );
            }
            catch( Exception e ) {
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 7;

            Set<_typeParameter> _tps = new HashSet<>();
            this.astNodeWithTypeParams.getTypeParameters().forEach( t -> _tps.add( _typeParameter.of( (TypeParameter)t)) );
            hash = 29 * hash + Objects.hashCode( _tps );
            //hash = 29 * hash + Objects.hashCode( this.astNodeWithTypeParams.getTypeParameters() );
            return hash;
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
            final _typeParameters other = (_typeParameters)obj;
            if( this.astNodeWithTypeParams.getTypeParameters().size() !=  other.astNodeWithTypeParams.getTypeParameters().size()){
                return false;
            }
            Set<_typeParameter> _tps = new HashSet<>();
            this.astNodeWithTypeParams.getTypeParameters().forEach( t -> _tps.add( _typeParameter.of( (TypeParameter)t) ) );

            //Set<_typeParameter> _tps = new HashSet<>();
            //this.astNodeWithTypeParams.getTypeParameters().forEach( t -> _tps.add( _typeParameter.of( (TypeParameter)t) ) );

            for(int i=0; i<other.astNodeWithTypeParams.getTypeParameters().size(); i++){
                _typeParameter _tp = _typeParameter.of( other.astNodeWithTypeParams.getTypeParameter(i));
                if( ! _tps.contains(_tp) ){
                    //System.out.println("Donest contain "+ _tp);
                    return false;
                }
            }
            /*
            if( !Objects.typesEqual( this.astNodeWithTypeParams.getTypeParameters(), other.astNodeWithTypeParams.getTypeParameters() ) ) {
                return false;
            }
            */
            return true;
        }

        @Override
        public String toString() {
            if( this.astNodeWithTypeParams.getTypeParameters().isNonEmpty() ) {
                return this.astNodeWithTypeParams.getTypeParameters().toString();
            }
            return "";
        }

        public NodeWithTypeParameters astHolder() {
            return this.astNodeWithTypeParams;
        }

        public void forEach( Consumer<? super _typeParameter> elementAction ) {
            list().forEach( elementAction );
        }

        public void forEach( Predicate<? super _typeParameter> matchFn,
                             Consumer<? super _typeParameter> elementAction ) {

            list( matchFn ).forEach( elementAction );
        }

        public _typeParameters add( _typeParameter... elements ) {
            Arrays.stream( elements ).forEach( t -> this.astNodeWithTypeParams.addTypeParameter( t.ast() ) );
            return this;
        }

        public _typeParameters add( TypeParameter... nodes ) {
            Arrays.stream( nodes ).forEach( t -> this.astNodeWithTypeParams.addTypeParameter( t ) );
            return this;
        }
    }
    
    /**
     *
     * @author Eric
     * @param <T>
     */
    public interface _hasTypeParameters<T extends _hasTypeParameters>
        extends _model {

        /**
         * @return  the typeParameters entity which wraps the AST NodeList<TypeParameter> entities
         */
        _typeParameters getTypeParameters();

        /* return a list of AST typeParameters */
        NodeList<TypeParameter> listAstTypeParameters();
        
        /**
         * parse the string as TypeParameters and set the typeParameters
         * @param typeParameters string of typeParameters
         * @return the modified T
         */
        T typeParameters( String typeParameters );

        /**
         * @param typeParameters Strings that represent individual TypeParameters
         */
        T typeParameters( String... typeParameters );

        T typeParameters( _typeParameters _tps );

        T typeParameters( NodeList<TypeParameter> typeParams );

        /**
         * remove all typeParametersfrom the entity
         */
        T removeTypeParameters();

        /** does this have non empty type parameters */
        boolean hasTypeParameters();
    }
    
    public static final _typeParametersInspect INSPECT_TYPE_PARAMETERS = 
        new _typeParametersInspect();
    
    public static class _typeParametersInspect
        implements _inspect<_typeParameters>, _differ<_typeParameters,_node> {

        String name = _java.Component.TYPE_PARAMETERS.getName();
        
        public _typeParametersInspect(){ 
        }
        @Override
        public boolean equivalent(_typeParameters left, _typeParameters right) {            
            return Objects.equals( left, right );
        }

        @Override
        public _inspect._diff diff( _java._inspector _ins, _path path, _inspect._diff dt, _typeParameters left, _typeParameters right) {
            for(int i=0; i<left.ast().size();i++){
                Type cit = left.ast().get(i);
                
                if( ! right.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    dt.add(path.in(_java.Component.TYPE_PARAMETER), cit, null);
                }
            }
            for(int i=0; i<right.ast().size();i++){
                Type cit = right.ast().get(i);
                if( ! left.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    dt.add(path.in(_java.Component.TYPE_PARAMETER), null, cit);
                }
            }
            return dt;
        }
        
        public boolean equivalent(NodeList<TypeParameter>left, NodeList<TypeParameter> right) {            
            return Ast.typesEqual(left, right);
        }

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, _typeParameters left, _typeParameters right) {
            if( !Ast.typesEqual(left.astNodeWithTypeParams.getTypeParameters(), right.astNodeWithTypeParams.getTypeParameters())){
                dt.node( new _change_typeParameters(path.in(_java.Component.TYPE_PARAMETERS), (_hasTypeParameters)leftRoot, (_hasTypeParameters)rightRoot));
            }
            return (_dif)dt;
        }
        
        public static class _change_typeParameters 
                implements _delta<_hasTypeParameters>, _change<NodeList<TypeParameter>>{

            public _path path;
            public _hasTypeParameters leftRoot;
            public _hasTypeParameters rightRoot;
            public NodeList<TypeParameter> left;
            public NodeList<TypeParameter> right;
            
            public _change_typeParameters(_path path, _hasTypeParameters leftRoot, _hasTypeParameters rightRoot){
                this.path = path;
                this.leftRoot = leftRoot;
                this.rightRoot = rightRoot;
                
                this.left = new NodeList<>();
                this.right = new NodeList<>();
                this.left.addAll(leftRoot.listAstTypeParameters());
                this.right.addAll(rightRoot.listAstTypeParameters()); 
            }
            
            @Override
            public _hasTypeParameters leftRoot() {
                return leftRoot;
            }

            @Override
            public _hasTypeParameters rightRoot() {
                return rightRoot;
            }

            @Override
            public _path path() {
                return path;
            }

            @Override
            public NodeList<TypeParameter> left() {
                return left;
            }

            @Override
            public NodeList<TypeParameter> right() {
                return right;
            }

            @Override
            public void keepLeft() {
                this.leftRoot.removeTypeParameters();
                this.leftRoot.typeParameters(left);
                
                this.rightRoot.removeTypeParameters();
                this.rightRoot.typeParameters(left);
            }

            @Override
            public void keepRight() {
                this.leftRoot.removeTypeParameters();
                this.leftRoot.typeParameters(right);
                
                this.rightRoot.removeTypeParameters();
                this.rightRoot.typeParameters(right);
            }
            
            @Override
            public String toString(){
                return "   ~ "+path;
            }            
        }
    }
}
