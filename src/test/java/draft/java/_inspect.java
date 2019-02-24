package draft.java;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import draft.ObjectDiff;
import draft.ObjectDiff.DiffList;
import static draft.java.Ast.typesEqual;
import draft.java._parameter._parameters;
import draft.java._typeParameter._typeParameters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;

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
        //parts.put( _java.Component.ANNOS, getAnnos() );
        //parts.put( _java.Component.BODY, getBody() );
        //parts.put( _java.Component.TYPE, getType() );
        //parts.put( _java.Component.PARAMETERS, getParameters() );
        //parts.put( _java.Component.MODIFIERS, getEffectiveModifiers()  );
        //parts.put( _java.Component.JAVADOC, getJavadoc() );
        //parts.put( _java.Component.RECEIVER_PARAMETER, getReceiverParameter() );
        //parts.put( _java.Component.TYPE_PARAMETERS, getTypeParameters() );
        //parts.put( _java.Component.THROWS, getThrows() );
        //parts.put( _java.Component.NAME, getName() );
    }
    **/ 

    public static final _methodInspect INSPECT_METHOD = new _methodInspect();
    public static class _methodInspect implements _inspect<_method>{

        @Override
        public boolean equivalent(_method left, _method right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _method left, _method right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.METHOD, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.METHOD, left, null);
                return dl;
            }
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());
            INSPECT_ANNOS.diff( path, dl, left.getAnnos(), right.getAnnos());
            INSPECT_MODIFIERS.diff(path, dl, left.getModifiers(), right.getModifiers());
            INSPECT_TYPE_REF.diff(path, dl, left.getType(), right.getType());
            INSPECT_NAME.diff( path, dl, left.getName(), right.getName());            
            INSPECT_RECEIVER_PARAMETER.diff(path, dl, left.getReceiverParameter(), right.getReceiverParameter());
            INSPECT_PARAMETERS.diff(path, dl, left.getParameters(), right.getParameters());
            INSPECT_TYPE_PARAMETERS.diff(path, dl, left.getTypeParameters(), right.getTypeParameters());
            INSPECT_THROWS.diff( path, dl, left.getThrows(), right.getThrows());            
            INSPECT_BODY.diff(path, dl, left.getBody(), right.getBody());            
            return dl;
        }
        
    }
    
    public static final _bodyInspect INSPECT_BODY =
        new _bodyInspect();

    public static class _bodyInspect implements _inspect<_body>{

        private static final diff_match_patch plainTextDiff = new diff_match_patch();
        
        @Override
        public boolean equivalent(_body left, _body right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _body left, _body right) {
            if(!left.isPresent() ){
                if( ! right.isPresent() ){
                    return dl;
                }
                else{
                    dl.add(path+_java.Component.BODY.getName(), left, right);
                    return dl;
                }
            }
            if( !right.isPresent()) {
                dl.add(path+_java.Component.BODY.getName(), left, right);
                return dl;
            }
            String leftSer = left.toString(Ast.PRINT_NO_COMMENTS);
            String rightSer = right.toString(Ast.PRINT_NO_COMMENTS);
            if( !Objects.equals( leftSer, rightSer )){
                //ok. we know at least one diff (other than comments) are in the text
                // lets diff the originals WITH comments
                LinkedList<Diff> diffs = plainTextDiff.diff_main(left.toString(), right.toString());
                _textDiff td = new _textDiff(diffs);
                // NOTE: we treat code DIFFERENTLY than other objects that are diffedbecause 
                // diffs in code can cross object boundaries and it's not as "clean"
                // as simply having members with properties because of the nature of code
                // instead we have a _textDiff which encapsulates the apparent changes
                // the text has to undergo to get from LEFT, to RIGHT
                
                dl.add(path+_java.Component.BODY.getName(), td, td);                
            }            
            return dl;
        }
        
    }   
    
    public static final _parametersInspect INSPECT_PARAMETERS = 
        new _parametersInspect();

    public static class _parametersInspect 
            implements _inspect<_parameters>{

        @Override
        public boolean equivalent(_parameters left, _parameters right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _parameters left, _parameters right) {
            for(int i=0;i<left.count();i++){
                _parameter _l = left.get(i);
                _parameter _r = null;
                if( i < right.count() ){
                    _r = right.get(i);
                }
                if( !Objects.equals(left, right) ){
                    dl.add(path+_java.Component.PARAMETER+"["+i+"]", _l, _r);
                }                
            }
            if( right.count() > left.count() ){
                for(int i=left.count(); i<right.count(); i++){
                    _parameter _r = right.get(i);
                    dl.add(path+_java.Component.PARAMETER+"["+i+"]", null, _r);
                }
            }
            return dl;
        }        
    }   
    
    public static final _modifiersInspect INSPECT_MODIFIERS = 
        new _modifiersInspect();
    
    public static class _modifiersInspect implements _inspect<_modifiers>{
       
        @Override
        public boolean equivalent(_modifiers left, _modifiers right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _modifiers left, _modifiers right) {
            if( !equivalent( left, right)){
                dl.add(path+_java.Component.MODIFIERS.getName(), left, right);
            }
            return dl;
        }        
    }
    
    public static final _javadocInspect INSPECT_JAVADOC = 
        new _javadocInspect();
    
    public static class _javadocInspect implements _inspect<_javadoc>{
       
        @Override
        public boolean equivalent(_javadoc left, _javadoc right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _javadoc left, _javadoc right) {
            if( !equivalent( left, right)){
                dl.add(path+_java.Component.JAVADOC.getName(), left, right);
            }
            return dl;
        }        
    }
    
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
                INSPECT_NAME.diff(path+name+".", dl, left.getName(), right.getName());
                INSPECT_TYPE_REF.diff(path+name+".", dl, left.getType(), right.getType());
                INSPECT_ANNOS.diff(path+name+".", left.getAnnos(), right.getAnnos());
            }
            return dl;
        }        
    }
    
    public static final AnnosInspect INSPECT_ANNOS = 
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
    
    public static final TypeRefInspect INSPECT_TYPE_REF = 
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
        implements _inspect<_typeParameters> {

        String name = _java.Component.TYPE_PARAMETERS.getName();
        
        public TypeParametersInspect(){ 
        }
        @Override
        public boolean equivalent(_typeParameters left, _typeParameters right) {            
            return Objects.equals( left, right );
        }

        @Override
        public DiffList diff(String path, DiffList dl, _typeParameters left, _typeParameters right) {
            return diff(path, dl, left.ast(), right.ast());
        }
        
        public boolean equivalent(NodeList<TypeParameter>left, NodeList<TypeParameter> right) {            
            return Ast.typesEqual(left, right);
        }

        public DiffList diff(String path, DiffList dl, NodeList<TypeParameter> left, NodeList<TypeParameter> right) {
            
            List<ObjectDiff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.size();i++){
                Type cit = left.get(i);
                
                if( ! right.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, cit, null) );
                }
            }
            for(int i=0; i<right.size();i++){
                Type cit = right.get(i);
                if( ! left.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, null, cit) );
                }
            }
            return dl.addList(des);
        }        
    }
    
    public static final ThrowsInspect INSPECT_THROWS = 
            new ThrowsInspect();
    
    public static class ThrowsInspect
        implements _inspect<_throws> {

        String name = _java.Component.THROWS.getName();
        
        public ThrowsInspect(){ 
        }
        
        @Override
        public boolean equivalent(_throws left, _throws right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _throws left, _throws right) {
            
            List<ObjectDiff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.count();i++){
                ReferenceType cit = left.get(i);
                if( ! right.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, cit, null) );
                }
            }
            for(int i=0; i<right.count();i++){
                ReferenceType cit = right.get(i);
                if( ! left.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, null, cit) );
                }
            }
            return dl.addList(des);
        }        

        public boolean equivalent(NodeList<ReferenceType>left, NodeList<ReferenceType> right) {            
            return Ast.typesEqual(left, right);
        }

        public DiffList diff(String path, DiffList dl, NodeList<ReferenceType> left, NodeList<ReferenceType> right) {
            
            List<ObjectDiff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.size();i++){
                Type cit = left.get(i);
                if( ! right.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, cit, null) );
                }
            }
            for(int i=0; i<right.size();i++){
                Type cit = right.get(i);
                if( ! left.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, null, cit) );
                }
            }
            return dl.addList(des);
        }        
    }
    
    /*
    public static final ThrowsInspect INSPECT_THROWS = 
            new ThrowsInspect();
    
    public static class ThrowsInspect
        implements _inspect<NodeList<ReferenceType>> {

        String name = _java.Component.THROWS.getName();
        
        public ThrowsInspect(){ 
        }
        
        @Override
        
    }
    */
    
    public static final StringInspect INSPECT_NAME = new StringInspect(_java.Component.NAME.getName());
    
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
    /*
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
    */
    
    
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
    
    
    /**
     * This represents a Textual Diff, it wraps/hides the 
     * underlying _diff_match_patch nuances and tried to make it
     * 
     * NOTE: we use the Concept LEFT and RIGHT to represent the two separate
     * pieces of text, rather than ASSUME the left is a previous and the right
     * is the current with a Patch Applied.
     * 
     */
    public static class _textDiff{
        
        /* the diff_match_patch derived diffs */
        public final LinkedList<Diff>diffs;
        
        public _textDiff(LinkedList<Diff> diffs ){
            this.diffs = diffs;
        }
        
        /**
         * Return the ENTIRE LEFT document as a String
         * @return 
         */
        public String left(){
            StringBuilder sb = new StringBuilder();
            diffs.forEach(s -> {
                if(s.operation != diff_match_patch.Operation.INSERT){
                    sb.append(s.text);
                }
            });
            return sb.toString();
        }
        
        /** 
         * return the entire RIGHT document as a String
         * 
         * @return the right document
         */
        public String right(){
            StringBuilder sb = new StringBuilder();
            diffs.forEach(s -> {
                if(s.operation != diff_match_patch.Operation.DELETE){
                    sb.append(s.text);
                }
            });
            return sb.toString();
        }
    }
}
