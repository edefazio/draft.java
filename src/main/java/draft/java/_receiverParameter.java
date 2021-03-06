package draft.java;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.type.Type;
import draft.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import draft.java._anno.*;
import draft.java._java.*;

/**
 *
 * All ANNOTATIONS preceding the TYPE will be set on this object, not on the
 * TYPE.
 *
 * @author Eric
 */
public final class _receiverParameter
        implements _node<ReceiverParameter>, _namedType<_receiverParameter>, _anno._hasAnnos<_receiverParameter> {

    public static _receiverParameter of( String rp ) {
        return of( Ast.receiverParameter( rp ) );
    }

    public static _receiverParameter of( ReceiverParameter rp ) {
        return new _receiverParameter( rp );
    }

    public final ReceiverParameter astReceiverParam;

    public _receiverParameter( ReceiverParameter rp ) {
        this.astReceiverParam = rp;
    }

    @Override
    public boolean is( String... string ) {
        try {
            return of(Text.combine(string)).equals(this);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean is ( ReceiverParameter astRp ){
        return of( astRp ).equals( this );
    }

    @Override
    public ReceiverParameter ast() {
        return this.astReceiverParam;
    }

    @Override
    public _receiverParameter name( String name ) {
        this.astReceiverParam.setName( name );
        return this;
    }

    @Override
    public String getName() {
        return this.astReceiverParam.getNameAsString();
    }

    @Override
    public _typeRef getType() {
        return _typeRef.of( this.astReceiverParam.getType() );
    }

    @Override
    public _receiverParameter type( Type astType ) {
        this.astReceiverParam.setType( astType );
        return this;
    }

    @Override
    public _annos getAnnos() {
        return _annos.of( this.astReceiverParam );
    }

    @Override
    public String toString() {
        return this.astReceiverParam.toString();
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
        final _receiverParameter other = (_receiverParameter)obj;
        if( this.astReceiverParam == other.astReceiverParam ) {
            return true; //two _receiverParameter s pointing to the same ReceiverParameter
        }
        if( ! Expr.equivalentAnnos(this.astReceiverParam, other.astReceiverParam)){
            return false;
        }
        //if( ! Ast.annotationsEqual(this.astReceiverParam, other.astReceiverParam)){
        //    return false;
        //}
        if( !Objects.equals( this.getName(), other.getName() ) ) {
            return false;
        }
        if( ! Ast.typesEqual( astReceiverParam.getType(), other.astReceiverParam.getType())){
            return false;
        }
        return true;
    }

    @Override
    public Map<_java.Component, Object> componentsMap( ) {
        Map<_java.Component, Object> parts = new HashMap<>();
        parts.put( _java.Component.ANNOS, getAnnos() );
        parts.put( _java.Component.TYPE, getType() );
        parts.put( _java.Component.NAME, getName() );
        return parts;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        //hash = 97 * hash + Ast.annotationsHash( astReceiverParam ); //Objects.hashCode( this.getAnnos() );
        hash = 97 * hash + Expr.hashAnnos(astReceiverParam);
        hash = 97 * hash + Objects.hashCode( this.getName() );
        hash = 97 * hash + Ast.typeHash( astReceiverParam.getType()); //Objects.hashCode( this.getType() );
        return hash;
    }

    /**
     * <A HREF="https://blog.joda.org/2015/12/explicit-receiver-parameters.html">
     * Receiver Parameters</A>
     * (A rarely used feature in Java METHODS and CONSTRUCTORS
     *
     * @author Eric
     * @param <T>
     */
    public interface _hasReceiverParameter<T extends _hasReceiverParameter>
            extends _java {

        default boolean hasReceiverParameter() {      
            return getAstReceiverParameter() != null;            
        }

        default _receiverParameter getReceiverParameter() {
            ReceiverParameter astRp = getAstReceiverParameter();
            if( astRp != null ){
                return of(astRp);
            }
            return null;            
        }
        
        default ReceiverParameter getAstReceiverParameter(){
            Node n = (Node) ((_node)this).ast();
            if( n instanceof MethodDeclaration ){
                MethodDeclaration md = (MethodDeclaration)n;
                if(md.getReceiverParameter().isPresent()){
                    return md.getReceiverParameter().get();
                }
                return null;
            }
            ConstructorDeclaration cd = (ConstructorDeclaration)n;
            if(cd.getReceiverParameter().isPresent()){
                return cd.getReceiverParameter().get();
            }
            return null;
        }
        
        default T removeReceiverParameter() {
            if( hasReceiverParameter()){
                Node n = (Node) ((_node)this).ast();
                if( n instanceof MethodDeclaration ){
                    MethodDeclaration md = (MethodDeclaration)n;
                    md.removeReceiverParameter();
                } else{
                    ConstructorDeclaration cd = (ConstructorDeclaration)n;
                    cd.removeReceiverParameter();
                }
            }            
            return (T)this;
        }

        default T receiverParameter( String receiverParameter ) {
            return receiverParameter( Ast.receiverParameter( receiverParameter ) );
        }

        default T receiverParameter( _receiverParameter _rp ) {
            return receiverParameter( _rp.ast() );
        }
        
        default T receiverParameter( ReceiverParameter rp ) {
            Node n = (Node) ((_node)this).ast();
            if( n instanceof MethodDeclaration ){
                MethodDeclaration md = (MethodDeclaration)n;
                md.setReceiverParameter(rp);
            } else{
                ConstructorDeclaration cd = (ConstructorDeclaration)n;
                cd.setReceiverParameter(rp);
            }
            return (T) this;
        }    
    }    
}
