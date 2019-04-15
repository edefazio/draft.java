/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import draft.Stencil;
import draft.Template;
import draft.Tokens;
import draft.Translator;
import draft.java.Expr;
import draft.java._anno;
import draft.java._model;
import draft.java.proto.$proto.$component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This will be the NEW $anno
 * @author Eric
 */
public class $a 
    implements Template<_anno>, $proto<_anno> {

    public static $a of(String...anno ){
        return new $a(_anno.of(anno) );
    }
    
    /** Default Matching constraint (by default ALWAYS Match)*/
    public Predicate<_anno> constraint = a-> true;
    
    public $component $name = new $component("$name$", t-> true);
    
    public List<$memberValue> $mvs = new ArrayList<>();
    
    public $a( _anno proto ){
        this.$name.$form = Stencil.of( proto.getName() );
        AnnotationExpr astAnn = proto.ast();
        if( astAnn instanceof NormalAnnotationExpr ){
            NormalAnnotationExpr na = (NormalAnnotationExpr)astAnn;
            na.getPairs().forEach(mv -> $mvs.add($memberValue.of(mv.getNameAsString(), mv.getValue())));
        } else if( astAnn instanceof SingleMemberAnnotationExpr ){
            SingleMemberAnnotationExpr sa = (SingleMemberAnnotationExpr)astAnn;
            $mvs.add($memberValue.of(sa.getMemberValue()));
        }
    }
    
    public $a constraint( Predicate<_anno> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    /**
     * ADDS an additional matching constraint to the prototype
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public $a addConstraint( Predicate<_anno>constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    public Tokens decompose( _anno _a ){
        Tokens ts = $name.decompose(_a.getName());
        if( $mvs.size() == 0 || ts == null ){
            return ts;
        }
        AnnotationExpr astAnn = _a.ast();        
        if( astAnn instanceof MarkerAnnotationExpr ){
            
            if( $mvs.size() ==1 ){ 
                if( $mvs.get(0).isMatchAll() ){
                    return ts;
                }
                return null;
            }
            return null;
        }        
        if( astAnn instanceof SingleMemberAnnotationExpr ){                        
            if( $mvs.size() ==1 ){
                SingleMemberAnnotationExpr sme = (SingleMemberAnnotationExpr)astAnn;                
                Tokens props = $mvs.get(0).decompose( sme.getMemberValue() );
                if( ts.isConsistent(props) ){
                    ts.putAll(props);
                    return ts;
                }
            }
            return null;
        }
        /** TODO
        if( astAnn instanceof NormalAnnotationExpr ){
            NormalAnnotationExpr sme = (NormalAnnotationExpr)astAnn;
            if( $mvs.size() < sme.getPairs().size() ){
                
                NodeList<MemberValuePair> sme.getPairs()
                        //.forEach(mvp -> );
                Tokens props = $mvs.get(0).decompose( getMemberValue() );
                if( ts.isConsistent(props) ){
                    ts.putAll(props);
                    return ts;
                }
            }
            return null;
        }
        * */
        
        for(int i=0;i<$mvs.size();i++){
            $memberValue $mv = $mvs.get(i);
            
        }
        return null;
    }
    
    public boolean matches( String...anno ){
        return matches( _anno.of(anno));
    }
    
    public boolean matches( _anno _a){
        return decompose( _a ) != null;
    }
    
    @Override
    public _anno construct(Translator translator, Map<String, Object> keyValues) {
        StringBuilder sb = new StringBuilder();
        sb.append( $name.compose(translator, keyValues) );
        String properties = "";
        for(int i=0;i<$mvs.size();i++){
            if( ( properties.length() > 0 )&&( !properties.endsWith(",")) ) {
                properties = properties + ",";
            }
            properties += $mvs.get(i).compose( translator, keyValues );
        }
        if(properties.length() == 0 ){
            return _anno.of(sb.toString());
        }
        return _anno.of(sb.toString()+"("+properties+")" );
    }

    @Override
    public Template<_anno> $(String target, String $Name) {
        $name.$form.$(target, $Name);
        $mvs.forEach(mv -> mv.name.$form.$(target, $Name));
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> list$() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> list$Normalized() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<_anno> listIn(_model._node _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<_anno> listIn(Node astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<? extends selected> selectListIn(Node astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<? extends selected> selectListIn(_model._node _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node> N removeIn(N _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N forEachIn(N astRootNode, Consumer<_anno> _nodeActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node> N forEachIn(N _n, Consumer<_anno> _nodeActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static class $memberValue {
        public $component<String> name = new $component("$key$", t->true);
        public $component<String> value = new $component("$value$", t->true);                
        public Predicate<MemberValuePair> constraint = t-> true;
        
        public static $memberValue of( Expression value ){
            return new $memberValue("$_$", value);
        }
        
        public static $memberValue of(String key, String value){
            return new $memberValue(key, value);
        }
        
        public String compose( Translator translator, Map<String,Object> keyValues ){
            String k = name.compose(translator, keyValues);
            String v = value.compose(translator, keyValues);
            if( k == null || k.length() == 0){
                return v;
            }
            return k+"="+v;
        }
        
        public boolean isMatchAll(){
            return name.$form.isMatchAll() && value.$form.isMatchAll();
        }
        
        public static $memberValue of(String key, Expression exp){
            return new $memberValue(key, exp);
        }
        
        
        public static $memberValue any(){
            return new $memberValue("$key$", "$value$");
        }
        
        public $memberValue(String name, String value ){
            this( name, Expr.of(value) );
        }
        
        public $memberValue( String name, Expression value ){
            this.name.$form = Stencil.of(name);
            this.value.$form = Stencil.of(value.toString());
        }
        
        public $memberValue $( String target, String $name ){
            this.name.$form = this.name.$form.$(target, $name);
            this.value.$form = this.value.$form.$(target, $name);
            return this;
        }
        
        public boolean matches( Expression value ){
            return decompose( value )!= null;
        }
        
        public boolean matches( MemberValuePair mvp ){
            return decompose(mvp) != null;
        }
        
        public Tokens decompose(Expression e){            
            if( constraint.test( new MemberValuePair("", e) ) ) {
                return value.decompose(e.toString());
            }
            return null;
        }
        
        public Tokens decompose(MemberValuePair t) {            
            if (t == null) {
                return null;
            }
            if (constraint.test(t)){                    
                Tokens ts = name.decompose(t.getNameAsString() );
                ts = value.decomposeTo(t.getValue().toString(), ts);
                return ts;
            }
            return null;
        }
    }
    
    public static class $value {
        public $component<String> value = new $component("$value$", t->true);                
    }
    
    
    
}
