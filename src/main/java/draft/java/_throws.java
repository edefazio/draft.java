package draft.java;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithThrownExceptions;
import com.github.javaparser.ast.type.ReferenceType;
import draft.Text;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Model of a Java throws clause
 *
 * @author Eric
 */
public final class _throws
        implements _model {

    public static _throws of( NodeWithThrownExceptions nte ) {
        return new _throws( nte );
    }

    public static _throws of( Class<? extends Throwable>...clazzes ){
        MethodDeclaration md = Ast.method( "void a(){}");
        Arrays.stream( clazzes ).forEach(c -> md.addThrownException(c) );
        return new _throws( md );
    }

    /**
     * Here we are creating a throws clause without a target, so create
     * a "dummy" method to hold the ELEMENTS
     *
     * @param throwsClause
     * @return
     */
    public static _throws of( String... throwsClause ) {
        String t = Text.combine( throwsClause ).trim();
        if( t.length() == 0 ) {
            return of(Ast.method( "void a(){}"));
        }
        if( t.startsWith( "throws " ) ) {
            t = t.substring( "throws ".length() );
        }
        MethodDeclaration md = Ast.method( "void a() throws " + t + System.lineSeparator() + ";" );
        return new _throws( md );
    }

    private final NodeWithThrownExceptions astNodeWithThrows;

    public _throws(){
        this( Ast.method("void m(){}") );
    }

    public _throws( NodeWithThrownExceptions th ) {
        this.astNodeWithThrows = th;
    }

    public NodeList<ReferenceType> ast() {
        return astNodeWithThrows.getThrownExceptions();
    }

    public boolean contains( ReferenceType rt ) {
        return this.astNodeWithThrows.getThrownExceptions().stream().filter(r -> Ast.typesEqual(rt, (ReferenceType)r) ).findFirst().isPresent();
        //return this.astNodeWithThrows.getThrownExceptions().contains( rt );
    }

    /** verify this throws contains all of the ReferenceTypes in rt */
    public boolean containsAll( List<ReferenceType> rt ){

        for(int i=0;i<rt.size();i++){
            if( !contains(rt.get(i) ) ){
                return false;
            }
        }
        return true;
    }


    public boolean is( Class<? extends Throwable>...clazzes ){
        try {
            return of( clazzes ).equals( this );
        }
        catch( Exception e ) {
        }
        return false;
    }

    public boolean is( String... str ) {
        try {
            return of( str ).equals( this );
        }
        catch( Exception e ) {
        }
        return false;
    }

    public void forEach( Consumer<? super _typeRef<ReferenceType>> elementAction ) {
        this.astNodeWithThrows.getThrownExceptions().forEach( elementAction );
    }

    public void forEach( Predicate<? super _typeRef<ReferenceType>> matchFn,
                         Consumer<? super _typeRef<ReferenceType>> elementAction ) {
        this.astNodeWithThrows.getThrownExceptions().stream().filter( matchFn ).forEach( elementAction );
    }

    public List<_typeRef<ReferenceType>> list() {
        return this.astNodeWithThrows.getThrownExceptions();
    }

    public List<_typeRef<ReferenceType>> list(
            Predicate<? super _typeRef<ReferenceType>> matchFn ) {
        return (List<_typeRef<ReferenceType>>)this.astNodeWithThrows.getThrownExceptions().stream().filter( matchFn ).collect( Collectors.toList() );
    }

    public _throws add( Class<? extends Throwable>... throwsClasses ) {
        Arrays.stream( throwsClasses ).forEach( t ->this.astNodeWithThrows.addThrownException(t));
        return this;
    }

    public _throws add( String... elements ) {
        Arrays.stream( elements ).forEach( t -> this.astNodeWithThrows.getThrownExceptions().add( Ast.typeRef( t)  ) );
        return this;
    }

    public _throws add( ReferenceType... elements ) {
        Arrays.stream( elements ).forEach( t -> this.astNodeWithThrows.getThrownExceptions().add( t ) );
        return this;
    }

    public _throws remove( ReferenceType... elements ) {
        Arrays.stream( elements ).forEach( t -> this.astNodeWithThrows.getThrownExceptions().remove( t ) );
        return this;
    }

    public _throws remove( Predicate<? super _typeRef<ReferenceType>> matchFn ) {
        list( matchFn ).forEach( t -> this.astNodeWithThrows.getThrownExceptions().remove( t ) );
        return this;
    }

    public _throws clear() {
        this.astNodeWithThrows.getThrownExceptions().clear();
        return this;
    }

    public int count() {
        return this.astNodeWithThrows.getThrownExceptions().size();
    }

    public boolean isEmpty() {
        return this.astNodeWithThrows.getThrownExceptions().isEmpty();
    }

    public int indexOf( ReferenceType node ) {
        return this.astNodeWithThrows.getThrownExceptions().indexOf( node );
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
        final _throws other = (_throws)obj;
        if( this.astNodeWithThrows == other.astNodeWithThrows ) {
            return true; //two _throws pointing to the same NodeWithThrownException
        }
        if( !Ast.typesEqual( astNodeWithThrows.getThrownExceptions(), other.astNodeWithThrows.getThrownExceptions())){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Ast.typesHashCode( this.astNodeWithThrows.getThrownExceptions() );
    }

    @Override
    public String toString() {
        if( this.astNodeWithThrows.getThrownExceptions().isEmpty() ) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append( "throws " );
        for( int i = 0; i < this.astNodeWithThrows.getThrownExceptions().size(); i++ ) {
            if( i > 0 ) {
                sb.append( ", " );
            }
            sb.append( this.astNodeWithThrows.getThrownExceptions().get( i ) );
        }
        return sb.toString();
    }

    public NodeWithThrownExceptions astHolder() {
        return this.astNodeWithThrows;
    }

    public _throws add( _typeRef<ReferenceType>... elements ) {
        Arrays.stream( elements ).forEach( t -> this.astNodeWithThrows.addThrownException( (ReferenceType)t.ast() ) );
        return this;
    }

    public _throws remove( _typeRef<ReferenceType>... elements ) {
        Arrays.stream( elements ).forEach( t -> this.astNodeWithThrows.getThrownExceptions().remove( (ReferenceType)t.ast() ) );
        return this;
    }

    public int indexOf( _typeRef<ReferenceType> element ) {
        return this.astNodeWithThrows.getThrownExceptions().indexOf( (ReferenceType)element.ast() );
    }

    public static final _java.Semantic<Collection<ReferenceType>> EQIVALENT_THROWS = (o1, o2)->{
        if( o1 == null){
            return o2 == null;
        }
        if( o2 == null ){
            return false;
        }
        if( o1.size() != o2.size()){
            return false;
        }
        Set<ReferenceType> tm = new HashSet<>();
        Set<ReferenceType> om = new HashSet<>();
        tm.addAll(o1);
        om.addAll(o2);
        return Objects.equals(tm, om);        
    };
    
    /** 
     * Are these (2) collections of throws equivalent ?
     * @param left
     * @param right
     * @return true if these collections are semantically equivalent
     */
    public static boolean equivalent( Collection<ReferenceType> left, Collection<ReferenceType> right ){
        return EQIVALENT_THROWS.equivalent(left, right);
    }
    
    /**
     * examples:
     * {@link _method}
     * {@link _constructor}
     *
     * @author Eric
     * @param <T> the hasThrows container {@link _method} {@link _constructor}
     */
    public interface _hasThrows<T extends _hasThrows> extends _model {

        _throws getThrows();

        default boolean hasThrows() {
            return getThrows().isEmpty();
        }

        T addThrows( String... throwExceptions );

        T addThrows( String throwException );

        T addThrows( Class<? extends Throwable>... throwExceptions );

        T addThrows( Class<? extends Throwable> throwException );

        boolean isThrown( Class<? extends Throwable> thrownClass );

        boolean isThrown( ReferenceType refType );

        boolean isThrown( String typeName );
    }
}
