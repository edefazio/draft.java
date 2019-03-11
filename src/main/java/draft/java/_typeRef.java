package draft.java;

import com.github.javaparser.ast.type.Type;
import draft.java._java.Component;
import draft.java._model.*;
import draft.java._java._path;

import java.util.*;

/**
 * Model of a Java TYPE Reference (i.e. String, int, List<Boolean>)
 * wraps an AST {@link Type} implementation
 *
 *
 * @param <T> the Type implementation (i.e. ReferenceType)
 */
public final class _typeRef<T extends Type>
        implements _node<Type> {

    public static _typeRef of( java.lang.reflect.AnnotatedType t){
        return new _typeRef( Ast.typeRef( t ) );
    }

    public static _typeRef of( java.lang.reflect.Type t){
        return new _typeRef( Ast.typeRef( t ) );
    }

    public static _typeRef of( Class clazz ) {
        return new _typeRef( Ast.typeRef( clazz ) );
    }

    public static _typeRef of( String string ) {
        return new _typeRef( Ast.typeRef( string ) );
    }

    public static _typeRef of( Type t ) {
        return new _typeRef( t );
    }

    private final T astType;

    public _typeRef( T t ) {
        this.astType = t;
    }

    @Override
    public Type ast() {
        return astType;
    }

    public boolean isPrimitive() {
        return astType.isPrimitiveType();
    }

    public Type getElementType(){
        return this.astType.getElementType();
    }

    public boolean isWildcard() {
        return astType.isWildcardType();
    }

    public boolean isIntersection() {
        return astType.isIntersectionType();
    }

    public boolean isArray() {
        return astType.isArrayType();
    }

    public boolean isVoid() {
        return astType.isVoidType();
    }

    public int getDimensions() {
        return astType.getArrayLevel();
    }

    @Override
    public String toString() {
        return this.astType.toString();
    }

    @Override
    public int hashCode() {
        return Deconstructed.of(this.astType.toString() ).normalize().hashCode();
    }

    public _typeRef copy(){
        return of(this.astType.clone());
    }
    
    public boolean is( String type  ){
        try{
            return of( type ).equals(this);
        }
        catch( Exception e) {
            return false;
        }
    }

    public boolean is( Type typeDecl ){
        try{
            return of( typeDecl ).equals(this);
        }
        catch( Exception e) {
            return false;
        }
    }

    @Override
    public Map<_java.Component, Object> componentsMap( ) {
        Map<_java.Component, Object> parts = new HashMap<>();
        parts.put( _java.Component.AST_TYPE, this.astType);
        parts.put( _java.Component.ARRAY_LEVEL, this.astType.getArrayLevel());
        parts.put( _java.Component.ELEMENT_TYPE, this.astType.getElementType());
        return parts;
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
        final _typeRef<?> other = (_typeRef<?>)obj;
        if( this.astType == other.astType){
            return true; // two _typeRef s pointing to the same ast Type
        }
        if( this.isPrimitive() && other.isPrimitive() ) {
            return Objects.equals(this.astType, other.astType );
        }
        Deconstructed td = Deconstructed.of(this.astType.toString() );
        Deconstructed od = Deconstructed.of(other.astType.toString() );
        return td.equals( od );
    }

    /** we "partsMap" the TYPE into symbols and tokens to check for equality */
    private static class Deconstructed {

        /**
         * When we create a Local Class and ask for it's name, it will have
         * this weird "$#$" qualifier, where # is some number...
         * Here is an example:
         * <PRE>
         * draft.java._classTest$1$Hoverboard
         * </PRE>
         * ...well we want to identify these patterns and convert them into dots
         * draft.java._classTest.Hoverboard
         */
        public static final String LOCAL_CLASS_NAME_PACKAGE_PATTERN = "\\$?\\d+\\$";

        public List<Character> symbols;
        public List<String> tokens;


        public static Deconstructed of( String str ) {
            List<Character> symbols = new ArrayList<>();
            List<String> tokens = new ArrayList<>();

            StringBuilder sb = new StringBuilder();
            for( int i = 0; i < str.length(); i++ ) {
                char c = str.charAt( i );
                switch( c ) {
                    case '<':
                    case '&':
                    case '>':
                    case '|':
                    case ',':
                        symbols.add( c );
                        if( sb.length() > 0 ) {
                            String tok = sb.toString().trim();
                            if( tok.length() > 0 ){
                                tokens.add( tok );
                            }
                            sb.delete( 0, sb.length() );
                        }
                        break;
                    default:
                        sb.append( c );
                        break;
                }
            }
            if( sb.length() > 0 ) {
                tokens.add( sb.toString() );
            }
            return new Deconstructed( symbols, tokens );
        }

        /** normalize fully qualified names used as tokens to Simple Names */
        public Deconstructed normalize(){
            for(int i=0;i<tokens.size();i++){
                tokens.set( i, normalize(tokens.get(i)) );
            }
            return this;
        }

        private static String normalize( String s ) {

            /*
            look for the tell-tale local anonymous package pattern $#$ wheren # is a number... like "$1$" in :
            "draft.java._classTest$1$Hoverboard"
            ...and replace it with a '.'
            */
            s = s.replaceAll(LOCAL_CLASS_NAME_PACKAGE_PATTERN, ".");
            int idx = s.lastIndexOf( '.' );
            if( idx < 0 ) {
                return s;
            }
            return s.substring( idx + 1 );
        }

        public Deconstructed( List<Character> symbols, List<String> tokens ) {
            this.symbols = symbols;
            this.tokens = tokens;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            List<String>toks = new ArrayList<>();
            this.tokens.forEach( t-> toks.add( normalize(t )) );
            hash = 29 * hash + Objects.hashCode( this.symbols );
            hash = 29 * hash + Objects.hashCode( toks );
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
            final Deconstructed other = (Deconstructed)obj;
            return equals( other );
        }

        public boolean equals( Deconstructed od ) {
            //the broken up symbols must be the same
            if( !this.symbols.equals( od.symbols ) ) {
                return false;
            }
            if( this.tokens.size() != od.tokens.size() ) {
                return false;
            }
            for( int i = 0; i < this.tokens.size(); i++ ) {
                if( !this.tokens.get( i ).equals( od.tokens.get( i ) ) ) {
                    String tn = normalize( this.tokens.get( i ) );
                    String on = normalize( od.tokens.get( i ) );
                    if( !tn.equals( on ) ) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
