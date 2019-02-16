package draft.java;

import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.type.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import draft.java._anno.*;
import draft.java._model.*;

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

    private final ReceiverParameter astReceiverParam;

    public _receiverParameter( ReceiverParameter rp ) {
        this.astReceiverParam = rp;
    }

    public boolean is( String string ) {
        try {
            return of(string).equals(this);
        }catch (Exception e){
            return false;
        }
    }

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
        if( ! Ast.annotationsEqual(this.astReceiverParam, other.astReceiverParam)){
            return false;
        }
        //if( !Objects.equals( this.getAnnos(), other.getAnnos() ) ) {
        //    return false;
        //}
        if( !Objects.equals( this.getName(), other.getName() ) ) {
            return false;
        }
        if( ! Ast.typesEqual( astReceiverParam.getType(), other.astReceiverParam.getType())){
            return false;
        }
        //if( !Objects.equals( this.getType(), other.getType() ) ) {
        //    return false;
        //}
        return true;
    }

    public Map<_java.Part, Object> partsMap( ) {
        Map<_java.Part, Object> parts = new HashMap<>();
        parts.put( _java.Part.ANNOTATIONS, getAnnos() );
        parts.put( _java.Part.TYPE, getType() );
        parts.put( _java.Part.NAME, getName() );
        return parts;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Ast.annotationsHash( astReceiverParam ); //Objects.hashCode( this.getAnnos() );
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
            extends _model {

        _receiverParameter getReceiverParameter();

        boolean hasReceiverParameter();

        T setReceiverParameter( String receiverParameter );

        T setReceiverParameter( _receiverParameter _rp );

        T setReceiverParameter( ReceiverParameter rp );

        T removeReceiverParameter();
    }
}
