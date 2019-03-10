package draft.java;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithThrownExceptions;
import com.github.javaparser.ast.type.ReferenceType;
import draft.Text;
import draft.java._java._path;
import static draft.java.Ast.typesEqual;
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

    public ReferenceType get(int index){
        return this.astNodeWithThrows.getThrownException(index);       
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
            return !getThrows().isEmpty();
        }
        
        default T addThrows(String... throwExceptions) {
            Arrays.stream(throwExceptions).forEach(t -> addThrows(t));
            return (T)this;
        }
        
        default T addThrows(String throwException) {
            getThrows().astNodeWithThrows.addThrownException((ReferenceType) Ast.typeRef(throwException));
            return (T)this;
        }    

        default T addThrows(Class<? extends Throwable>... throwExceptions) {
            Arrays.stream(throwExceptions).forEach(t -> addThrows(t));
            return (T)this;
        }

    
        default T addThrows(Class<? extends Throwable> throwException) {
            getThrows().astNodeWithThrows.addThrownException((ReferenceType) Ast.typeRef(throwException));
            return (T)this;
        }
        
        default T setThrows( NodeList<ReferenceType> thrws ){
            getThrows().astNodeWithThrows.setThrownExceptions(thrws);
            return (T)this;
        }
    
        default boolean hasThrow(Class<? extends Throwable> clazz) {
            return getThrows().astNodeWithThrows.isThrown(clazz)
                || getThrows().astNodeWithThrows.isThrown(clazz.getCanonicalName());
        }

        default boolean hasThrow(String name) {
            return getThrows().astNodeWithThrows.isThrown(name);
        }

        default boolean hasThrow(ReferenceType rt) {
            return this.getThrows().contains(rt);
        }
        
        default T removeThrow( Class<? extends Throwable> thrownClass ){
            getThrows().list( t -> t.is(thrownClass.getCanonicalName()) ).forEach( t -> t.ast().remove() );
            return (T)this;
        }
    }
    
    public static final _throwsInspect INSPECT_THROWS = new _throwsInspect();
    
    public static class _throwsInspect
        implements _inspect<_throws>, _differ<_throws, _node> {

        String name = _java.Component.THROWS.getName();
        
        public _throwsInspect(){ 
        }
        
        @Override
        public boolean equivalent(_throws left, _throws right) {
            return Objects.equals(left, right);
        }
        
        @Override
        public _inspect._diff diff( _java._inspector _ins, _path path, _inspect._diff dt, _throws left, _throws right) {
            //List<ObjectDiff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.count();i++){
                ReferenceType cit = left.get(i);
                if( ! right.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    dt.add( path.in(_java.Component.THROWS), cit, null ); 
                }
            }
            for(int i=0; i<right.count();i++){
                ReferenceType cit = right.get(i);
                if( ! left.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    dt.add( path.in(_java.Component.THROWS), null, cit ); 
                }
            }
            return dt;
        }
        
        public boolean equivalent(NodeList<ReferenceType>left, NodeList<ReferenceType> right) {            
            return Ast.typesEqual(left, right);
        }

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, _throws left, _throws right) {
            if( !Objects.equals( left, right)){
                dt.node( new _change_throws(path.in(_java.Component.THROWS), (_hasThrows)leftRoot, (_hasThrows)rightRoot ) );
            }
            return (_dif)dt;
        }

        public static class _change_throws
                implements _delta<_hasThrows>, _change<List<ReferenceType>>{

            public _path path;
            public _hasThrows leftRoot;
            public _hasThrows rightRoot;
            public NodeList<ReferenceType> left;
            public NodeList<ReferenceType> right;
            
            public _change_throws(_path p, _hasThrows leftRoot, _hasThrows rightRoot){
                this.path = p;
                this.leftRoot = leftRoot;
                this.rightRoot = rightRoot;
                left = new NodeList<>();
                left.addAll( leftRoot.getThrows().astNodeWithThrows.getThrownExceptions() );
                right = new NodeList<>();
                right.addAll( rightRoot.getThrows().astNodeWithThrows.getThrownExceptions() );
            }
            
            @Override
            public _hasThrows leftRoot() {
                return leftRoot;
            }

            @Override
            public _hasThrows rightRoot() {
                return rightRoot;
            }

            @Override
            public _path path() {
                return this.path;
            }

            @Override
            public List<ReferenceType> left() {
                return left;
            }

            @Override
            public List<ReferenceType> right() {
                return right;
            }

            @Override
            public void keepLeft() {
                leftRoot.setThrows(left);
                rightRoot.setThrows(left);
            }

            @Override
            public void keepRight() {
                leftRoot.setThrows( right );
                rightRoot.setThrows( right );
            }
            
            @Override
            public String toString(){
                return "   ~ "+ path;
            }
        }
    }
}
