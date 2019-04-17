/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import draft.Stencil;
import draft.Template;
import draft.Text;
import draft.Tokens;
import draft.Translator;
import draft.java.Expr;
import draft.java._anno;
import draft.java._model;
import draft.java.proto.$proto.$component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This will be the NEW $anno
 *
 * @author Eric
 */
public class $a
        implements Template<_anno>, $proto<_anno> {

    public static $a of(String... anno) {
        return new $a(_anno.of(anno));
    }

    /**
     * Default Matching constraint (by default ALWAYS Match)
     */
    public Predicate<_anno> constraint = a -> true;

    public $component $name = new $component("$name$", t -> true);

    public List<$memberValue> $mvs = new ArrayList<>();

    public $a(_anno proto) {
        this.$name.$form = Stencil.of(proto.getName());
        AnnotationExpr astAnn = proto.ast();
        if (astAnn instanceof NormalAnnotationExpr) {
            NormalAnnotationExpr na = (NormalAnnotationExpr) astAnn;
            na.getPairs().forEach(mv -> $mvs.add($memberValue.of(mv.getNameAsString(), mv.getValue())));
        } else if (astAnn instanceof SingleMemberAnnotationExpr) {
            SingleMemberAnnotationExpr sa = (SingleMemberAnnotationExpr) astAnn;
            $mvs.add($memberValue.of(sa.getMemberValue()));
        }
    }

    public $a constraint(Predicate<_anno> constraint) {
        this.constraint = constraint;
        return this;
    }

    /**
     * ADDS an additional matching constraint to the prototype
     *
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public $a addConstraint(Predicate<_anno> constraint) {
        this.constraint = this.constraint.and(constraint);
        return this;
    }

    public Tokens decompose(_anno _a) {
        Tokens ts = $name.decompose(_a.getName());
        if ($mvs.isEmpty() || ts == null) {
            System.out.println( "Returning "+ts);
            return ts;
        }
        AnnotationExpr astAnn = _a.ast();
        if (astAnn instanceof MarkerAnnotationExpr) {
            System.out.println( "Marketer");
            if ($mvs.size() == 1) {
                if ($mvs.get(0).isMatchAll()) {
                    return ts;
                }
                return null;
            }
            return null;
        }
        if (astAnn instanceof SingleMemberAnnotationExpr) {
            System.out.println( "SingleMember");
            if ($mvs.size() == 1) {
                SingleMemberAnnotationExpr sme = (SingleMemberAnnotationExpr) astAnn;
                Tokens props = $mvs.get(0).decompose(sme.getMemberValue());
                if (ts.isConsistent(props)) {                    
                    ts.putAll(props);
                    return ts;
                } else{
                    System.out.println("Not consistent with "+ ts + props );
                }
            }
            return null;
        }
        if (astAnn instanceof NormalAnnotationExpr) {
            System.out.println( "Normal");
            NormalAnnotationExpr sme = (NormalAnnotationExpr) astAnn;
            if ($mvs.size() <= sme.getPairs().size()) {
                //NodeList<MemberValuePair> mvps = sme.getPairs();
                //.forEach(mvp -> );
                //Map<$memberValue.Select, $memberValue> matched = new HashMap<>();
                //List<$memberValue> remainingMatchers = new ArrayList<>();
                //remainingMatchers.addAll($mvs);
                
                //whenever I find a match, I remove the property
                List<MemberValuePair> mvpsC = new ArrayList<>();
                mvpsC.addAll(sme.getPairs());

                for (int i = 0; i < $mvs.size(); i++) {
                    $memberValue.Select sel = $mvs.get(i).selectFirst(mvpsC);
                    //MemberValuePair mvp = remainingMatchers.get(i).matchFirst(mvpsC);
                    if (sel == null) {
                        System.out.println("NO FINDING "+ mvpsC);
                        return null;
                    } else {
                        //matched.put(sel, remainingMatchers.get(i));
                        mvpsC.remove(sel.astMvp);
                        if( !ts.isConsistent(sel.args.asTokens())){
                            System.out.println( "NOT CONSISTENT WITH "+ts + " "+ sel.args.asTokens() );
                            return null;
                        }
                        ts.putAll(sel.args.asTokens());
                    }
                }
                return ts;                 
            }
            return null;
        }

        for (int i = 0; i < $mvs.size(); i++) {
            $memberValue $mv = $mvs.get(i);

        }
        return null;
    }

    public boolean matches(String... anno) {
        return matches(_anno.of(anno));
    }

    public boolean matches(_anno _a) {
        return decompose(_a) != null;
    }

    @Override
    public _anno construct(Translator translator, Map<String, Object> keyValues) {
        StringBuilder sb = new StringBuilder();
        sb.append($name.compose(translator, keyValues));
        String properties = "";
        for (int i = 0; i < $mvs.size(); i++) {
            if ((properties.length() > 0) && (!properties.endsWith(","))) {
                properties = properties + ",";
            }
            properties += $mvs.get(i).compose(translator, keyValues);
        }
        if (properties.length() == 0) {
            return _anno.of(sb.toString());
        }
        return _anno.of(sb.toString() + "(" + properties + ")");
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

        public $component<String> name = new $component("$key$", t -> true);
        public $component<String> value = new $component("$value$", t -> true);
        public Predicate<MemberValuePair> constraint = t -> true;

        public static $memberValue of(Expression value) {
            return new $memberValue("$_$", value);
        }

        public static $memberValue of(String key, String value) {
            return new $memberValue(key, value);
        }

        public String compose(Translator translator, Map<String, Object> keyValues) {
            String k = name.compose(translator, keyValues);
            String v = value.compose(translator, keyValues);
            if (k == null || k.length() == 0) {
                return v;
            }
            return k + "=" + v;
        }

        public boolean isMatchAll() {
            return name.$form.isMatchAll() && value.$form.isMatchAll();
        }

        public static $memberValue of(String key, Expression exp) {
            return new $memberValue(key, exp);
        }

        public static $memberValue any() {
            return new $memberValue("$key$", "$value$");
        }

        public $memberValue(String name, String value) {
            this(name, Expr.of(value));
        }

        public $memberValue(String name, Expression value) {
            this.name.$form = Stencil.of(name);
            this.value.$form = Stencil.of(value.toString());
        }

        public $memberValue $(String target, String $name) {
            this.name.$form = this.name.$form.$(target, $name);
            this.value.$form = this.value.$form.$(target, $name);
            return this;
        }

        
        public Select selectFirst( List<MemberValuePair> pairs ){
            for(int i=0;i<pairs.size();i++){
                Select sel = select(pairs.get(i) );
                if( sel != null ){
                    return sel;
                }
            }            
            return null;
        }
        
        /*
        public MemberValuePair matchFirst( List<MemberValuePair> pairs ){
            for(int i=0;i<pairs.size();i++){
                if( matches(pairs.get(i) ) ){
                    return pairs.get(i);
                }
            }
            return null;
        }
         */
 /*
        public boolean matches( Expression value ){
            return decompose( value )!= null;
        }
         */
        public boolean matches(MemberValuePair mvp) {
            return select(mvp) != null;
        }

        /**
         * 
         * @param onlyValueExpression
         * @return 
         */
        public Tokens decompose(Expression onlyValueExpression){            
            if( constraint.test( new MemberValuePair("_", onlyValueExpression) ) ) {
                return value.decompose(onlyValueExpression.toString());
            }
            return null;
        }
        
        public Tokens decompose(MemberValuePair mvp ){
            if (mvp == null) {
                return null;
            }
            if (constraint.test(mvp)) {
                Tokens ts = name.decompose(mvp.getNameAsString());
                ts = value.decomposeTo(mvp.getValue().toString(), ts);
                return ts;
            }
            return null;
        }
        
        /**
         * When we have an anno like
         * @param onlyValueExpression
         * @return 
         * @count(100)
         * (a SingleMemberAnnotationExpr)
         * 
         */
        public Select select (Expression onlyValueExpression){            
            MemberValuePair mvp = new MemberValuePair("", onlyValueExpression); 
            if( constraint.test( mvp ) ) {
                Tokens ts = value.decompose(onlyValueExpression.toString());
                return new Select(mvp, ts);
            }
            return null;
        }
        
        /**
         * 
         * @param mvp
         * @return 
         */
        public Select select(MemberValuePair mvp) {
            if (mvp == null) {
                return null;
            }
            if (constraint.test(mvp)) {
                Tokens ts = name.decompose(mvp.getNameAsString());
                ts = value.decomposeTo(mvp.getValue().toString(), ts);
                if( ts != null ){
                    return new Select(mvp, ts);
                }
            }
            return null;
        }

        public static class Select
            implements $proto.selected, selectedAstNode<MemberValuePair> {

            public final MemberValuePair astMvp;
            public final $args args;

            public Select(MemberValuePair astMvp, Tokens tokens) {
                this.astMvp = astMvp;
                this.args = $args.of(tokens);
            }

            @Override
            public $args getArgs() {
                return args;
            }

            @Override
            public String toString() {
                return "$memberValue.Select {" + System.lineSeparator()
                        + Text.indent(astMvp.toString()) + System.lineSeparator()
                        + Text.indent("$args : " + args) + System.lineSeparator()
                        + "}";
            }

            /**
             * It's not a "true" member value pair, but rather a synthesized
             * MemberValue Pair with no name... this is for cases where we have an 
             * annotation like 
             * @name("Eric")
             * as apposed to cases where the key and value are spelled out
             * @name(value="Eric")
             * 
             * @return true if its a "value only" member value pair
             */
            public boolean isValueOnly(){
                return astMvp.getNameAsString().equals("");
            }
            
            @Override
            public MemberValuePair ast() {
                return astMvp;
            }

            public String getName() {
                return astMvp.getNameAsString();
            }

            public Expression getValue() {
                return astMvp.getValue();
            }
        }
    }

    public static class $value {
        public $component<String> value = new $component("$value$", t -> true);
    }

    /**
     * A Matched Selection result returned from matching a prototype $anno
     * inside of some Node or _node
     */
    public static class Select
            implements $proto.selected, selected_model<_anno>, selectedAstNode<AnnotationExpr> {

        public final AnnotationExpr astAnno;
        public final $args args;

        public Select(_anno _a, Tokens tokens) {
            this(_a, $args.of(tokens));
        }

        public Select(_anno _a, $args tokens) {
            this.astAnno = _a.ast();
            args = tokens;
        }

        public Select(AnnotationExpr expression, $args tokens) {
            this.astAnno = expression;
            this.args = tokens; //$args.of( tokens);
        }

        @Override
        public $args getArgs() {
            return args;
        }

        @Override
        public String toString() {
            return "$anno.Select {" + System.lineSeparator()
                    + Text.indent(astAnno.toString()) + System.lineSeparator()
                    + Text.indent("$args : " + args) + System.lineSeparator()
                    + "}";
        }

        public boolean isNamed(String name) {
            return astAnno.getNameAsString().equals(name);
        }

        public boolean isSingleValue() {
            return astAnno instanceof SingleMemberAnnotationExpr;
        }

        public boolean hasKeyValues() {
            return astAnno instanceof NormalAnnotationExpr;
        }

        public boolean hasNoValues() {
            return astAnno instanceof MarkerAnnotationExpr;
        }

        @Override
        public AnnotationExpr ast() {
            return astAnno;
        }

        @Override
        public _anno model() {
            return _anno.of(astAnno);
        }
    }
}
