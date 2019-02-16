package draft.java;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import draft.DraftException;
import draft.Text;
import draft.java._model.*;
import static draft.java.Ast.field;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Instance of a single @ annotation
 * i.e.
 * <UL>
 * <LI> @base : "naked" annotation</LI>
 * <LI> @singleValue("VALUE") : "VALUE" annotation</LI>
 * <LI> @keyValue(key="VALUE",anotherKey=VALUE) : "key-VALUE pair"
 * annotation</LI>
 * </UL>
 *
 * <UL>
 * <LI>{@link _class} i.e. @Deprecated class C{...}
 * <LI>{@link _enum} i.e. @Deprecated enum E{...}
 * <LI>{@link _interface} i.e. @Deprecated interface I{...}
 * <LI>{@link _annotation} i.e. @Deprecated @interface A{...}
 * <LI>{@link _field} i.e. @nonnull String NAME;
 * <LI>{@link _method} i.e. @Deprecated String toString(){...}
 * <LI>{@link _enum._constant} @Deprecated E(2)
 * <LI>{@link _parameter} (@nonNull Object VALUE)
 * </UL>
 *
 * @name
 * @name("value")
 * @name(key="value")
 * @name(key1="value1", key2="value2")
 *
 * @author Eric
 */
public final class _anno
        implements _node<AnnotationExpr> {

    public static _anno of( String anno ){
        return of( new String[]{anno} );
    }
    public static _anno of( String... annotation ) {
        return new _anno( Ast.anno( annotation ) );
    }

    public static _anno of( Object anonymousObject ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousClass(ste);
        NodeList<BodyDeclaration<?>> bds = oce.getAnonymousClassBody().get();
        BodyDeclaration bd = bds.stream().filter(b -> b.getAnnotations().isNonEmpty() ).findFirst().get();
        return of( bd.getAnnotation(0) );
    }

    public static _anno of( Class<? extends Annotation> annotationClass){
        return of( new MarkerAnnotationExpr(annotationClass.getSimpleName()));
    }
    public static _anno of( AnnotationExpr ae ) {
        return new _anno( ae );
    }

    private AnnotationExpr annotationExpr;

    public _anno( AnnotationExpr annotationExpr ) {
        this.annotationExpr = annotationExpr;
    }

    public _anno copy() {
        return new _anno( this.annotationExpr.clone() );
    }

    public String getName() {
        return this.annotationExpr.getNameAsString();
    }

    public _anno setName( String name ) {
        this.annotationExpr.setName( name );
        return this;
    }

    public boolean isNamed( String name ) {
        return this.annotationExpr.getName().asString().equals( name );
    }

    public boolean isInstance( Class clazz ) {
        String str = this.annotationExpr.getNameAsString();
        return str.equals( clazz.getName() ) || str.equals( clazz.getSimpleName() );
    }

    public List<String> listKeys() {
        if( this.annotationExpr instanceof NormalAnnotationExpr ) {
            NormalAnnotationExpr n = (NormalAnnotationExpr)this.annotationExpr;
            List<String> keys = new ArrayList<>();
            n.getPairs().forEach( e -> keys.add( e.getNameAsString() ) );
            return keys;
        }
        return new ArrayList<>();
    }

    public List<Expression> listValues() {
        return listValues( t -> true );
    }

    public List<Expression> listValues( Predicate<Expression> matchFn ) {
        return listValues( Expression.class, matchFn );
    }

    public <E extends Expression> List<E> listValues( Class<E> expressionClass ) {
        return listValues( expressionClass, t -> true );
    }

    public <E extends Expression> List<E> listValues( Class<E> expressionClass,
                                                      Predicate<E> valueMatchFn ) {
        List<E> values = new ArrayList<>();
        if( this.annotationExpr instanceof NormalAnnotationExpr ) {
            NormalAnnotationExpr n = (NormalAnnotationExpr)this.annotationExpr;
            n.getPairs().forEach( a -> {
                if( expressionClass.isAssignableFrom( expressionClass ) && valueMatchFn.test( (E)a.getValue() ) ) {
                    values.add( (E)a.getValue() );
                }
            } );
        }
        else {
            SingleMemberAnnotationExpr s = (SingleMemberAnnotationExpr)this.annotationExpr;
            if( expressionClass.isAssignableFrom( expressionClass ) && valueMatchFn.test( (E)s.getMemberValue() ) ) {
                values.add( (E)s.getMemberValue() );
            }
        }
        return values;
    }

    public void forValues( Consumer<Expression> valueAction ) {
        listValues().forEach( valueAction );
    }

    public <E extends Expression> void forValues( Class<E> expressionClass, Consumer<E> expressionAction ) {
        listValues( expressionClass ).stream().forEach( expressionAction );
    }

    public <E extends Expression> void forValues(
            Class<E> expressionClass, Predicate<E> expressionMatchFn, Consumer<E> expressionAction ) {
        listValues( expressionClass, expressionMatchFn ).forEach( expressionAction );
    }

    public Expression getValue( String name ) {
        if( this.annotationExpr instanceof NormalAnnotationExpr ) {
            NormalAnnotationExpr n = (NormalAnnotationExpr)this.annotationExpr;
            Optional<MemberValuePair> om = n.getPairs().stream()
                    .filter( m -> m.getNameAsString().equals( name ) ).findFirst();
            if( om.isPresent() ) {
                return om.get().getValue();
            }
        }
        return null;
    }

    public boolean is( AnnotationExpr ae ){
        try {
            return _anno.of( ae ).equals( this );
        }
        catch( Exception e ) {
            return false;
        }
    }

    public boolean is( String... str ) {
        try {
            return is( Ast.anno( str ) );
        }
        catch( Exception e ) {
            //System.out.println( "bad annotation "+str );
        }
        return false;
    }

    @Override
    public Map<_java.Part,Object> partsMap(){
        Map<_java.Part,Object> m = new HashMap();
        m.put(_java.Part.NAME, this.getName() );
        if( this.annotationExpr instanceof NormalAnnotationExpr ){
            NormalAnnotationExpr nae = (NormalAnnotationExpr)this.annotationExpr;
            m.put(_java.Part.KEY_VALUES, nae.getPairs() );
        } else if( this.annotationExpr instanceof SingleMemberAnnotationExpr){
            SingleMemberAnnotationExpr se = (SingleMemberAnnotationExpr)this.annotationExpr;
            m.put(_java.Part.VALUE, se.getMemberValue());
        }
        return m;
    }

    public _anno removeAttrs() {
        if( this.annotationExpr instanceof MarkerAnnotationExpr ) {
            return this;
        }
        if( !this.annotationExpr.getParentNode().isPresent() ) {
            throw new DraftException( "Cannot change attrs of annotation with no parent" );
        }
        MarkerAnnotationExpr m = new MarkerAnnotationExpr( this.getName() );
        this.annotationExpr.getParentNode().get().replace( annotationExpr, m );
        this.annotationExpr = m;
        return this;
    }

    public _anno addAttr( String key, char c ) {
        return addAttr( key, Expr.of( c ) );
    }

    public _anno addAttr( String key, boolean b ) {
        return addAttr( key, Expr.of( b ) );
    }

    public _anno addAttr( String key, int value ) {
        return addAttr( key, Expr.of( value ) );
    }

    public _anno addAttr( String key, long value ) {
        return addAttr( key, Expr.of( value ) );
    }

    public _anno addAttr( String key, float f ) {
        return addAttr( key, Expr.of( f ) );
    }

    public _anno addAttr( String key, double d ) {
        return addAttr( key, Expr.of( d ) );
    }

    public _anno addAttr( String key, Expression value ) {
        if( this.annotationExpr instanceof NormalAnnotationExpr ) {
            NormalAnnotationExpr n = (NormalAnnotationExpr)this.annotationExpr;
            n.addPair( key, value );
            return this;
        }
        else {
            NodeList<MemberValuePair> nl = new NodeList<>();
            nl.add( new MemberValuePair( key, value ) );
            NormalAnnotationExpr n = new NormalAnnotationExpr( this.annotationExpr.getName(), nl );
            annotationExpr.replace( n ); //this will set the parent pointer if necessary
            this.annotationExpr = n; //this will update the local reference
        }
        return this;
    }

    public _anno addAttr( String key, String value ) {
        if( this.annotationExpr instanceof NormalAnnotationExpr ) {
            NormalAnnotationExpr n = (NormalAnnotationExpr)this.annotationExpr;
            n.addPair( key, value );
            return this;
        }
        else {
            NodeList<MemberValuePair> nl = new NodeList<>();
            nl.add( new MemberValuePair( key, Expr.of( value ) ) );
            NormalAnnotationExpr n = new NormalAnnotationExpr( this.annotationExpr.getName(), nl );
            annotationExpr.replace( n ); //this will set the parent pointer if necessary
            this.annotationExpr = n; //this will update the local reference
        }
        return this;
    }

    public _anno setValue( String key, char c ) {
        return setValue( key, Expr.of( c ) );
    }

    public _anno setValue( String key, boolean b ) {
        return addAttr( key, Expr.of( b ) );
    }

    public _anno setValue( String key, int value ) {
        return addAttr( key, Expr.of( value ) );
    }

    public _anno setValue( String key, long value ) {
        return addAttr( key, Expr.of( value ) );
    }

    public _anno setValue( String key, float f ) {
        return addAttr( key, Expr.of( f ) );
    }

    public _anno setValue( String key, double d ) {
        return addAttr( key, Expr.of( d ) );
    }

    public _anno setValue( String name, String expression ) {
        return setValue( name, Expr.of( expression ) );
    }

    public _anno removeAttr( String name ) {
        if( this.annotationExpr instanceof NormalAnnotationExpr ) {
            NormalAnnotationExpr nae = (NormalAnnotationExpr)this.annotationExpr;
            nae.getPairs().removeIf( mvp -> mvp.getNameAsString().equals( name ) );
            return this;
        }
        //cant removeIn what is not there
        return this;
    }

    public _anno removeAttr( int index ) {
        if( this.annotationExpr instanceof NormalAnnotationExpr ) {
            NormalAnnotationExpr nae = (NormalAnnotationExpr)this.annotationExpr;
            nae.getPairs().remove( index );
            if( nae.getPairs().isEmpty() ) {
                MarkerAnnotationExpr mae = new MarkerAnnotationExpr( getName() );
                nae.getParentNode().get().replace( nae, mae );
                this.annotationExpr = mae;
            }
            return this;
        }
        if( index == 0 && this.annotationExpr instanceof SingleMemberAnnotationExpr ) {
            MarkerAnnotationExpr mae = new MarkerAnnotationExpr( this.annotationExpr.getNameAsString() );
            this.annotationExpr.getParentNode().get().replace( this.annotationExpr, mae );
            this.annotationExpr = mae;
        }
        //cant removeIn what is not there
        return this;
    }

    public _anno setValue( String name, Expression e ) {
        this.listKeys().contains( name );
        if( this.annotationExpr instanceof NormalAnnotationExpr ) {
            NormalAnnotationExpr nae = (NormalAnnotationExpr)this.annotationExpr;
            Optional<MemberValuePair> omvp
                    = nae.getPairs().stream().filter( mvp -> mvp.getNameAsString().equals( name ) ).findFirst();
            if( omvp.isPresent() ) {
                omvp.get().setValue( e );
            }
            else {
                //not found, add it
                nae.addPair( name, e );
            }
            return this;
        }
        NormalAnnotationExpr nae = new NormalAnnotationExpr();
        nae.setName( this.annotationExpr.getNameAsString() );
        nae.addPair( name, e );

        this.annotationExpr.getParentNode().get().replace( this.annotationExpr, nae );
        this.annotationExpr = nae;
        return this;
    }

    public _anno setValue( int index, String expression ) {
        return setValue( index, Expr.of( expression ) );
    }

    public _anno setValue( int index, Expression value ) {
        if( index == 0 && this.annotationExpr instanceof MarkerAnnotationExpr ) {
            MarkerAnnotationExpr ma = (MarkerAnnotationExpr)this.annotationExpr;
            SingleMemberAnnotationExpr sv = new SingleMemberAnnotationExpr( ma.getName(), value );
            if( this.annotationExpr.getParentNode().isPresent() ) {
                this.annotationExpr.getParentNode().get().replace( ma, sv );
                this.annotationExpr = sv;
            }
            else {
                throw new DraftException( "cannot add VALUE to annotation with no parent" );
            }
        }
        if( this.annotationExpr instanceof NormalAnnotationExpr ) {
            NormalAnnotationExpr n = (NormalAnnotationExpr)this.annotationExpr;
            n.getPairs().get( index ).setValue( value );
            return this;
        }
        if( this.annotationExpr instanceof SingleMemberAnnotationExpr ) {
            if( index == 0 ) {
                SingleMemberAnnotationExpr sv = (SingleMemberAnnotationExpr)this.annotationExpr;
                sv.setMemberValue( value );
                return this;
            }
        }
        throw new DraftException( "No Values at index " + index + " in annotation " + this.toString() );
    }

    //if the impl is anything other than a marker annotation expression
    //it has values
    public boolean hasValues() {
        return !(this.annotationExpr instanceof MarkerAnnotationExpr);
    }

    public Expression getValue( int index ) {
        if( !this.hasValues() ) {
            throw new DraftException( "No Values on Marker annotation " + this.toString() );
        }
        if( this.annotationExpr instanceof SingleMemberAnnotationExpr ) {
            if( index == 0 ) {
                SingleMemberAnnotationExpr sv = (SingleMemberAnnotationExpr)this.annotationExpr;
                return sv.getMemberValue();
            }
            throw new DraftException( "No Values at index " + index + " in annotation " + this.toString() );
        }
        NormalAnnotationExpr n = (NormalAnnotationExpr)this.annotationExpr;
        return n.getPairs().get( index ).getValue();
    }

    @Override
    public String toString() {
        return this.annotationExpr.toString();
    }

    @Override
    public int hashCode() {
        //if the annotation is the
        return Objects.hashCode( this.annotationExpr );
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
        final _anno other = (_anno)obj;

        return Ast.annotationEqual( this.annotationExpr, other.annotationExpr );
    }



    @Override
    public AnnotationExpr ast() {
        return this.annotationExpr;
    }

    /**
     * Extracts the values from an _anno as a List<String>
     *
     * ASSUMPTIONS:
     * the _anno has a SINGLE property that is a String[]
     * <PRE>
     *    @interface ann{
     *        String[] someVariable
     *    }
     * </PRE>
     *
     * @param _a
     * @return
     */
    public static List<String> valuesAsStringList( _anno _a ) {
        if( _a.listValues().isEmpty() || !_a.getValue( 0 ).isArrayInitializerExpr() ) {
            return new ArrayList<>();
        }
        NodeList<Expression> vals
                = _a.getValue( 0 ).asArrayInitializerExpr().getValues();
        if( vals.size() % 2 != 0 ) {
            throw new DraftException( "replacements must be in pairs " + vals );
        }
        List<String> strs = new ArrayList<>();
        for( int i = 0; i < vals.size(); i++ ) {
            strs.add( vals.get( i ).asStringLiteralExpr().asString() );
        }
        return strs;
    }

    /**
     * _model of an entity that may be annotated with multiple Annotations
     * this includes:
     * <UL>
     * {@link _type}
     * {@link _field}
     * {@link _method}
     * {@link _constructor}
     * {@link _parameter}
     * {@link _annotation._element}
     * {@link _enum._constant}
     * </UL>
     *
     * @author Eric
     *
     * @param <T> the container TYPE
     */
    public interface _hasAnnos<T extends _hasAnnos> // & _referenceable
            extends _model {

        _annos getAnnos();

        default _anno getAnno( int index ) {
            return getAnnos().get( index );
        }

        /**
         *
         * @param _as
         * @return
         */
        default T replace( _annos _as ) {
            this.getAnnos().clear().add( _as.list().toArray( new _anno[ 0 ] ) );
            return (T)this;
        }

        /**
         * @return true if there are ANNOTATIONS for ane entity
         */
        default boolean isAnnotated() {
            return !getAnnos().isEmpty();
        }

        default T forAnnos( Consumer<_anno> _annoActionFn ){
            getAnnos().forEach(_annoActionFn);
            return (T) this;
        }

        default T forAnnos( Predicate<_anno> _annoMatchFn, Consumer<_anno> _annoActionFn ){
            getAnnos().forEach(_annoMatchFn, _annoActionFn);
            return (T) this;
        }

        /**
         * Gets the FIRST annotation that matches the _annoMatchFn predicate
         *
         * @param _annoMatchFn
         * @return the first matching {@link _anno}, or null if none query
         */
        default _anno getAnno( Predicate<? super _anno> _annoMatchFn ) {
            return getAnnos().get( _annoMatchFn );
        }

        /**
         * Gets the FIRST _anno that matches the annotationClass
         *
         * @param annotationClass the annotationClass
         * @return the first annotation that is of the annotationClass or null
         */
        default _anno getAnno( Class<? extends Annotation> annotationClass ) {
            return getAnnos().get( annotationClass );
        }

        /**
         * Gets the FIRST _anno that matches the annoName
         *
         * @param annoName the NAME of the anno
         * @return the first _anno that has the NAME, or null
         */
        default _anno getAnno( String annoName ) {
            return getAnnos().get( annoName );
        }

        /**
         *
         * @param annotationClass
         * @return
         */
        default boolean hasAnno( Class<? extends Annotation> annotationClass ) {
            return getAnno( annotationClass ) != null;
        }

        default boolean hasAnno( String name ) {
            return getAnno( name ) != null;
        }

        default T ifHasAnno(Class<? extends Annotation>annotationClass, Consumer<T>_ifHasAnnoFn ){
            if( hasAnno(annotationClass) ){
                _ifHasAnnoFn.accept( (T)this );
            }
            return (T)this;
        }

        default T ifHasAnno(Class<? extends Annotation>annotationClass, Consumer<T>_ifHasAnnoFn, Consumer<T>_ifNotHasAnnoFn ){
            if( hasAnno(annotationClass) ){
                _ifHasAnnoFn.accept( (T)this );
            } else{
                _ifNotHasAnnoFn.accept( (T)this);
            }
            return (T)this;
        }

        default T ifHasAnno(String annotationName, Consumer<T>_ifHasAnnoFn ){
            if( hasAnno(annotationName) ){
                _ifHasAnnoFn.accept( (T)this );
            }
            return (T)this;
        }

        default T ifHasAnno(String annoName, Consumer<T>_ifHasAnnoFn, Consumer<T>_ifNotHasAnnoFn ){
            if( hasAnno(annoName) ){
                _ifHasAnnoFn.accept( (T)this );
            } else{
                _ifNotHasAnnoFn.accept( (T)this);
            }
            return (T)this;
        }

        default boolean hasAnno( Predicate<_anno> _annoMatchFn ) {
            return getAnno( _annoMatchFn ) != null;
        }

        default List<_anno> listAnnos() {
            return getAnnos().list();
        }

        default List<_anno> listAnnos( Predicate<? super _anno> _annMatchFn ) {
            return getAnnos().list( _annMatchFn );
        }

        default List<_anno> listAnnos( String annoName ) {
            return getAnnos().list( annoName );
        }

        default List<_anno> listAnnos(Class<? extends Annotation> annotationClass ) {
            return getAnnos().list( annotationClass );
        }

        default T annotate( List<AnnotationExpr> aes ){
            aes.forEach( a -> getAnnos().add(a) );
            return (T)this;
        }

        /**
         * annotate with the ANNOTATIONS and return the _annotated
         *
         * @param annotations ANNOTATIONS to to
         * @return the annotated entity
         */
        default T annotate( _anno... annotations ) {
            getAnnos().add( annotations );
            return (T)this;
        }

        /**
         * annotate with the ANNOTATIONS and return the model
         *
         * @param anns
         * @return
         */
        default T annotate( Class<? extends Annotation>... anns ) {
            getAnnos().add( anns );
            return (T)this;
        }

        /**
         * accept one or more ANNOTATIONS
         *
         * @param annotations
         * @return
         */
        default T annotate( String... annotations ) {
            getAnnos().add( annotations );
            return (T)this;
        }

        default T removeAnno( AnnotationExpr ae ){
            getAnnos().remove(ae);
            return (T)this;
        }

        /**
         * removeAll all _anno that accept the _annoMatchFn
         *
         * @param _annoMatchFn
         * @return
         */
        default T removeAnnos( Predicate<? super _anno> _annoMatchFn ) {
            getAnnos().remove( _annoMatchFn );
            return (T)this;
        }

        default boolean hasAnnos() {
            return !getAnnos().isEmpty();
        }

        /**
         * removeIn all ANNOTATIONS that are of the annotationClass
         *
         * @param annotationClass
         * @return
         */
        default T removeAnnos( Class<? extends Annotation> annotationClass ) {
            getAnnos().remove( annotationClass );
            return (T)this;
        }

        /**
         * removeAll all ANNOTATIONS that have the annoName
         *
         * @param annoNames
         * @return
         */
        default T removeAnnos( String... annoNames ) {
            getAnnos().remove( annoNames );
            return (T)this;
        }

        /**
         * removeIn all ANNOTATIONS
         *
         * @param annosToRemove
         * @return
         */
        default T removeAnnos( List<_anno> annosToRemove ) {
            getAnnos().remove( annosToRemove.toArray( new _anno[ 0 ] ) );
            return (T)this;
        }

    }

    /**
     * Grouping of _anno (s) expressions ({@link AnnotationExpr})
     * annotating a Class, Field, Method, Enum Constant, etc.
     *
     */
    public static class _annos
            implements _model { //_propertyList<NodeWithAnnotations, AnnotationExpr, _anno, _annos> {

        /** A reference to the container entity that is being annotated*/
        public final NodeWithAnnotations astAnnNode;

        public NodeWithAnnotations astHolder() {
            return astAnnNode;
        }

        public static _annos of( String... anns ) {
            String f = Text.combine( anns ) + System.lineSeparator() + "NOT_A_REAL_FIELD AST_ANNO_HOLDER;";
            FieldDeclaration fd = field( f );
            return new _annos( fd );
        }

        public static _annos of( NodeWithAnnotations astAnns ) {
            return new _annos( astAnns );
        }

        public _annos( NodeWithAnnotations astAnns ) {
            this.astAnnNode = astAnns;
        }

        public _annos add( _anno... anns ) {
            for( int i = 0; i < anns.length; i++ ) {
                //astAnns.add( anns[i].asAst() );
                astAnnNode.addAnnotation( anns[ i ].ast() );
            }
            return this;
        }

        public _annos add( String... annotations ) {
            for( String annotation : annotations ) {
                this.astAnnNode.addAnnotation( Ast.anno( annotation ) );
            }
            return this;
        }

        public _annos add( Class<? extends Annotation>... anns ) {
            for( Class<? extends Annotation> ann : anns ) {
                this.astAnnNode.addAnnotation( Ast.anno( "@" + ann.getSimpleName() ) );
            }
            return this;
        }

        public _annos add( AnnotationExpr... anns ) {
            for( AnnotationExpr ann : anns ) {
                this.astAnnNode.addAnnotation( ann );
            }
            return this;
        }

        public _anno get( int index ) {
            return _anno.of( this.astAnnNode.getAnnotation( index ) );
        }

        public _anno get( String name ) {
            List<_anno> a = this.list( name );
            if( a.size() >= 1 ) {
                return a.get( 0 );
            }
            return null;
        }

        public _anno get( Class<? extends Annotation> clazz ) {
            List<_anno> a = this.list( clazz );
            if( a.size() >= 1 ) {
                return a.get( 0 );
            }
            return null;
        }

        public _anno get( Predicate<? super _anno> _annoMatchFn ) {
            List<_anno> a = this.list( _annoMatchFn );
            if( a.size() >= 1 ) {
                return a.get( 0 );
            }
            return null;
        }

        public List<_anno> list() {
            return list( t -> true );
        }

        /**
         * Lists all ANNOTATIONS of the NAME
         *
         * @param name
         * @return
         */
        public List<_anno> list( String name ) {
            return list( a -> a.getName().equals( name ) );
        }

        /**
         * Lists all ANNOTATIONS that are of the Class
         *
         * @param clazz
         * @return
         */
        public List<_anno> list( Class<? extends Annotation> clazz ) {
            return list( a -> a.getName().equals( clazz.getSimpleName() )
                    || a.getName().equals( clazz.getCanonicalName() )
                    || a.getName().endsWith( "."+clazz.getSimpleName() ) );
        }

        public List<_anno> list( Predicate<? super _anno> matchFn ) {
            List<_anno> l = new ArrayList<>();
            this.astAnnNode.getAnnotations().forEach( a -> {
                _anno _a = _anno.of( (AnnotationExpr)a );
                if( matchFn.test( _a ) ) {
                    l.add( _a );
                }
            } );
            return l;
        }

        public int count() {
            return this.astAnnNode.getAnnotations().size();
        }

        public boolean isEmpty() {
            return this.astAnnNode.getAnnotations().isEmpty();
        }

        public _annos remove( _anno... annos ) {
            Arrays.stream( annos ).forEach( a -> this.astAnnNode.getAnnotations().remove( a.ast() ) );
            return this;
        }

        public _annos remove( AnnotationExpr... anns ) {
            Arrays.stream( anns ).forEach( a -> this.astAnnNode.getAnnotations().remove( a ) );
            return this;
        }

        public _annos remove( Predicate<? super _anno> annPredicate ) {
            list( annPredicate ).forEach( a -> this.astAnnNode.getAnnotations().remove( a.ast() ) );
            return this;
        }

        /**
         * removeIn all ANNOTATIONS that are
         *
         * @param annClasses
         * @return
         */
        public _annos remove( Class<? extends Annotation>... annClasses ) {
            List<Class<? extends Annotation>> ls = new ArrayList<>();
            Arrays.stream( annClasses ).forEach( c -> ls.add( c ) );

            //send a predicate
            return remove( a -> ls.stream().filter(
                    c -> c.getSimpleName().equals( a.getName() )
                            || c.getCanonicalName().equals( a.getName() ) ).findFirst().isPresent() );
        }

        /**
         * removeIn all ANNOTATIONS that have any of the names provided
         *
         * @param annName
         * @return
         */
        public _annos remove( String... annName ) {
            List<String> ls = new ArrayList<>();
            Arrays.stream( annName ).forEach( c -> ls.add( c ) );

            return remove( a -> ls.stream().filter(
                    n -> n.equals( a.getName() ) ).findFirst().isPresent() );
        }

        public _annos clear() {
            this.astAnnNode.getAnnotations().clear();
            return this;
        }

        public int indexOf( _anno _a ) {
            return indexOf( _a.ast() );
        }

        public int indexOf( AnnotationExpr ae ) {
            for( int i = 0; i < this.astAnnNode.getAnnotations().size(); i++ ) {
                if( Ast.annotationEqual( (AnnotationExpr)this.astAnnNode.getAnnotations().get( i ), ae ) ) {
                    return i;
                }
            }
            return -1;
        }

        public boolean contains( _anno _a ) {
            return contains( _a.ast() );
        }

        public boolean contains( AnnotationExpr astA ) {
            return this.astAnnNode.getAnnotations().stream().filter( a -> Ast.annotationEqual( (AnnotationExpr)a, astA ) ).findFirst().isPresent();
        }

        public boolean contains( Class<? extends Annotation> clazz ) {
            return list( clazz ).size() > 0;
        }

        public void forEach( Consumer<? super _anno> annoAction ) {
            list().forEach( annoAction );
        }

        public void forEach( Predicate<? super _anno> annoMatchFn,
                             Consumer<? super _anno> annoAction ) {
            list( annoMatchFn ).forEach( annoAction );
        }

        public void forEach( Class<? extends Annotation> annotationClazz,
                             Consumer<? super _anno> annoAction ) {
            list( annotationClazz ).forEach( annoAction );
        }

        public boolean is( String... annos ) {
            try {
                return _annos.of( annos ).equals( this );
            }
            catch( Exception e ) {
            }
            return false;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if( this.astAnnNode == null ){
                return "";
            }
            this.astAnnNode.getAnnotations().forEach( a -> sb.append( a ).append( System.lineSeparator() ) );
            return sb.toString();
        }

        @Override
        public int hashCode() {
            Set<AnnotationExpr> s = new HashSet<>();
            if( this.astAnnNode == null ){
                return 0;
            }
            this.astAnnNode.getAnnotations().forEach( a -> s.add( (AnnotationExpr)a ) ); //add each of the exprs to the set for order
            return s.hashCode();
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
            final _annos other = (_annos)obj;
            if( this.astAnnNode == null ){
                return false;
            }
            if( this.astAnnNode == other.astAnnNode ) {
                return true; //two _annos instances pointing to the saem NodeWithAnnotations instance
            }
            if( this.astAnnNode.getAnnotations().size() != other.astAnnNode.getAnnotations().size() ) {
                return false;
            }
            for( int i = 0; i < this.astAnnNode.getAnnotations().size(); i++ ) {
                AnnotationExpr e = (AnnotationExpr)this.astAnnNode.getAnnotations().get( i );
                //find a matching annotation in other, if one isnt found, then not equal
                if( !other.astAnnNode.getAnnotations().stream().filter( a -> Ast.annotationEqual( e, (AnnotationExpr)a ) ).findFirst().isPresent() ) {
                    return false;
                }
            }
            return true;
        }

        public NodeList<AnnotationExpr> ast() {
            if( this.astAnnNode != null ) {
                return (NodeList<AnnotationExpr>) astAnnNode.getAnnotations();
            }
            return new NodeList<>();
        }

        public _annos copy() {
            //NodeList<AnnotationExpr> exprs = new NodeList<>();
            FieldDeclaration fd = Ast.field( "NOT_A_REAL_FIELD AST_ANNO_PARENT;" );
            this.astAnnNode.getAnnotations().forEach( a -> fd.addAnnotation( ((AnnotationExpr)a).clone() ) );
            return new _annos( fd );
        }
    }
}
