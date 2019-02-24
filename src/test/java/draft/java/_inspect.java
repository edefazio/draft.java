package draft.java;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import draft.Diff;
import draft.Diff.DiffList;
import static draft.java.Ast.typesEqual;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Eric
 */
public interface _inspect<T> {
    
    public boolean equivalent( T left, T right );
    
    public DiffList diff( String path, DiffList dl, T left, T right );
    
    default DiffList diff( T left, T right ){
        DiffList dl = new DiffList();
        return diff( "", dl, left, right);
    }
    
    default DiffList diff(String path, T left, T right ){
        DiffList dl = new DiffList();
        return diff( path, dl, left, right);
    }
    
    static final Map<_java.Component, _inspect> _inspectMap = new HashMap<>();
    
    /** Given the component, return the inspector 
    public static _inspect of( _java.Component component ){
        parts.put( _java.Component.ANNOS, getAnnos() );
        parts.put( _java.Component.BODY, getBody() );
        parts.put( _java.Component.TYPE, getType() );
        parts.put( _java.Component.PARAMETERS, getParameters() );
        parts.put( _java.Component.MODIFIERS, getEffectiveModifiers()  );
        parts.put( _java.Component.JAVADOC, getJavadoc() );
        parts.put( _java.Component.RECEIVER_PARAMETER, getReceiverParameter() );
        //parts.put( _java.Component.TYPE_PARAMETERS, getTypeParameters() );
        //parts.put( _java.Component.THROWS, getThrows() );
        //parts.put( _java.Component.NAME, getName() );
    }
    **/ 

    public static final ReceiverParameterInspect INSPECT_RECEIVER_PARAMETER = 
        new ReceiverParameterInspect();
    
    public static class ReceiverParameterInspect
        implements _inspect<_receiverParameter> {

        public static final String name = _java.Component.RECEIVER_PARAMETER.getName();
        
        @Override
        public boolean equivalent(_receiverParameter left, _receiverParameter right) {
            return Objects.equals( left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _receiverParameter left, _receiverParameter right) {
            if( !equivalent( left, right )){
                if(left == null){
                    dl.add(path+name, null, right);
                    return dl;
                }
                if(right == null){
                    dl.add(path+name, left, null);
                    return dl;
                }                
                NAME_INSPECTOR.diff(path+name+".", dl, left.getName(), right.getName());
                TYPE_REF_INSPECTOR.diff(path+name+".", dl, left.getType(), right.getType());
                ANNOS_INSPECTOR.diff(path+name+".", left.getAnnos(), right.getAnnos());
            }
            return dl;
        }        
    }
    
    public static final AnnosInspect ANNOS_INSPECTOR = 
            new AnnosInspect();
    
    public static class AnnosInspect
        implements _inspect<_anno._annos> {

        String name = _java.Component.ANNOS.getName();
        
        public AnnosInspect(){ 
        }
        
        @Override
        public boolean equivalent(_anno._annos left, _anno._annos right) {            
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _anno._annos left, _anno._annos right) {
            NodeList<AnnotationExpr> laes = left.astAnnNode.getAnnotations();
            NodeList<AnnotationExpr> raes = right.astAnnNode.getAnnotations();
            
            for( int i = 0; i < laes.size(); i++ ) {
                AnnotationExpr e = (AnnotationExpr)laes.get( i );
                //find a matching annotation in other, if one isnt found, then not equal
                if( !raes.stream().filter( a -> Ast.annotationEqual( e, (AnnotationExpr)a ) ).findFirst().isPresent() ) {
                    dl.add(path+name, e, null);
                }
            }
            
            for( int i = 0; i < raes.size(); i++ ) {
                AnnotationExpr e = (AnnotationExpr)raes.get( i );
                //find a matching annotation in other, if one isnt found, then not equal
                if( !laes.stream().filter( a -> Ast.annotationEqual( e, (AnnotationExpr)a ) ).findFirst().isPresent() ) {
                    dl.add(path+name, null, e);
                }
            }            
            return dl;
        }        
    }
    
    public static final TypeRefInspect TYPE_REF_INSPECTOR = 
        new TypeRefInspect();
    
    public static class TypeRefInspect
        implements _inspect<_typeRef> {

        String name = _java.Component.TYPE.getName();
        
        public TypeRefInspect(){ 
        }
        
        @Override
        public boolean equivalent(_typeRef left, _typeRef right) {            
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _typeRef left, _typeRef right) {
            if( !equivalent(left, right) ){
                dl.add(path+name, left, right);
            }            
            return dl;
        }        
    }
    
    
    public static final TypeParametersInspect INSPECT_TYPE_PARAMETERS = 
        new TypeParametersInspect();
    
    public static class TypeParametersInspect
        implements _inspect<NodeList<TypeParameter>> {

        String name = _java.Component.TYPE_PARAMETERS.getName();
        
        public TypeParametersInspect(){ 
        }
        
        @Override
        public boolean equivalent(NodeList<TypeParameter>left, NodeList<TypeParameter> right) {            
            return Ast.typesEqual(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, NodeList<TypeParameter> left, NodeList<TypeParameter> right) {
            
            List<Diff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.size();i++){
                Type cit = left.get(i);
                
                if( ! right.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new Diff.Entry( path + name, cit, null) );
                }
            }
            for(int i=0; i<right.size();i++){
                Type cit = right.get(i);
                if( ! left.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new Diff.Entry( path + name, null, cit) );
                }
            }
            return dl.addList(des);
        }        
    }
    
    
    public static final ThrowsInspect INSPECT_THROWS = 
            new ThrowsInspect();
    
    public static class ThrowsInspect
        implements _inspect<NodeList<ReferenceType>> {

        String name = _java.Component.THROWS.getName();
        
        public ThrowsInspect(){ 
        }
        
        @Override
        public boolean equivalent(NodeList<ReferenceType>left, NodeList<ReferenceType> right) {            
            return Ast.typesEqual(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, NodeList<ReferenceType> left, NodeList<ReferenceType> right) {
            
            List<Diff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.size();i++){
                Type cit = left.get(i);
                if( ! right.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new Diff.Entry( path + name, cit, null) );
                }
            }
            for(int i=0; i<right.size();i++){
                Type cit = right.get(i);
                if( ! left.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new Diff.Entry( path + name, null, cit) );
                }
            }
            return dl.addList(des);
        }        
    }
    
    public static final StringInspect NAME_INSPECTOR = new StringInspect(_java.Component.NAME.getName());
    
    public static class StringInspect 
            implements _inspect<String>{
        
        public String name;
        
        public StringInspect( String name ){
            this.name = name;
        }
        
        public boolean equivalent( String left, String right){
            return Objects.equals(left, right);
        }
        
        public DiffList diff( String path, DiffList dl, String left, String right ){
            if( !equivalent(left, right) ){                
                return dl.add(path+name, left, right);
            }
            return dl;
        }
    }
    
    /*
    public static class _annosInspect
        implements _inspect<_anno._annos>{
        
    }
*/
    
    public static class _methodInspect 
        implements _inspect<_method>{
        
        public boolean equivalent( _method left, _method right ){
            return left.equals(right);
        }
        
        public DiffList diff( String path, DiffList dl, _method left, _method right ){
            Map<String,Object> ld = left.componentize();
            Map<String,Object> rd = right.componentize();
        
            ld.forEach((leftKey,leftComponent) -> {
                Object rightComponent = rd.get(leftKey);
                if( !Objects.equals(leftComponent, rightComponent)){
                    dl.add(path + leftKey, leftComponent, rightComponent);
                }            
            });        
            return dl;
        }
        
    }
    
    
    /*
    public static class TypeListInspect
        implements _inspect<List<Type>> {

        String name;
        
        public TypeListInspect(String name){ 
            this.name = name;
        }
        
        @Override
        public boolean equivalent(List<Type> left, List<Type> right) {            
            return Ast.typesEqual(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, List<Type> left, List<Type> right) {
            
            List<Type> leftAstTypes = left;
            List<Type> rightAstTypes = right;
            
            List<Diff.Entry> des = new ArrayList<>();
            for(int i=0; i<leftAstTypes.size();i++){
                Type cit = leftAstTypes.get(i);
                if( ! rightAstTypes.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new Diff.Entry( path + name, cit, null) );
                }
            }
            for(int i=0; i<rightAstTypes.size();i++){
                Type cit = rightAstTypes.get(i);
                if( ! leftAstTypes.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new Diff.Entry( path + name, null, cit) );
                }
            }
            return dl.addList(des);
        }        
    }
    
    public static class _typeRefCollectionInspect
        implements _inspect<Collection<_typeRef>> {

        public _typeRefCollectionInspect(){ 
        }
        
        @Override
        public boolean equivalent(Collection<_typeRef> left, Collection<_typeRef> right) {
            List<Type> leftAstTypes = new ArrayList<>();
            List<Type> rightAstTypes = new ArrayList<>();
            left.forEach(t-> leftAstTypes.add(t.ast()));
            right.forEach(t-> rightAstTypes.add(t.ast()));
            return Ast.typesEqual(leftAstTypes, rightAstTypes);
        }

        @Override
        public DiffList diff(String path, DiffList dl, Collection<_typeRef> left, Collection<_typeRef> right) {
            
            List<Type> leftAstTypes = new ArrayList<>();
            List<Type> rightAstTypes = new ArrayList<>();
            left.forEach(t-> leftAstTypes.add(t.ast()));
            right.forEach(t-> rightAstTypes.add(t.ast()));
            
            List<Diff.Entry> des = new ArrayList<>();
            for(int i=0; i<leftAstTypes.size();i++){
                Type cit = leftAstTypes.get(i);
                if( ! rightAstTypes.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new Diff.Entry( path, cit, null) );
                }
            }
            for(int i=0; i<rightAstTypes.size();i++){
                Type cit = rightAstTypes.get(i);
                if( ! leftAstTypes.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new Diff.Entry( path, null, cit) );
                }
            }
            return dl.addList(des);
        }        
    }
    */
    
}
