package draft.java.proto;

import com.github.javaparser.ast.Node;
import draft.java._model;
import draft.java._type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Prototype representing any uses of a specific Class within code
 * (either simple or partially/fully qualified) including (but not limited to): 
 * <UL>
 *   <LI> Imports:  imports fully.qualified.A, imports static fully.qualified.A.*;
 *   <LI> Annotations: @A, @A, @MemberClass.A, @fully.qualified.MemberClass.A
 *   <LI> Throws: throws A, throws MemberClass.A, throws fully.qualified.A
 *   <LI> Extends: extends A, extends MemberClass.A, extends fully.qualified.MemberClass.A
 *   <LI> Implements: implements A, implements MemberClass.A, implements fully.qualified.MemberClass.A
 *   <LI> Fields/Vars : public A a; public MemberClass.A; public fully.qualified.MemberClass A;
 *   <LI> Return Types: public A m(){}, public MemberClass.A m()
 *   <LI> Parameter Type: public void m(A a), public void m( MemberClass.A a){}
 *   <LI> Casts : a = (A)obj; a = (MemberClass.A)obj; a= (fully.qualified.MemberClass.A)obj;
 *   <LI> Class Expression : A.class.isAssignableFrom(val);
 *   <LI> Static Method Call : A.staticMethod(), MemberClass.A.staticMethod()
 *   <LI> Generics : List<A> as, Map<A, String> am, Map<MemberClass.A, String> 
 *   <LI> TypeParameter: void <M extends A> doIt( M m ){...}
 * </UL>
 * @author Eric
 */
public class $classUse {
    
    public static <N extends _model._node> N replace(N _n, Class target, Class replacement) {
        return $classUse.of(target).replaceIn(_n, replacement);
    }
    
    public static $classUse of( Class clazz ){
        return new $classUse(clazz);
    }
    
    String packageName;
    Class type;
    
    /** 
     * Whenever the fully qualified name (i.e. java.util.Map)
     * (i.e. imports, implements, extends, throws, annotationName, cast, 
     * instanceof, etc.)
     */ 
    $node $fullName;
    
    /**
     * If the class is a member class (or a member of a member class)
     * then you can refer to it not only by it's fully qualified name:
     * "pkgname.className"... but you can also refer to it by
     * "parentClassName.MemberClassName"
     * 
     * ..and if it's a deeply nested Class
     * "parentClassName.MemberClassName.deeplyNestedClassName"
     * 
     * this will create an ordered list of names that can be used to be refer
     * to the class in this fashion
     */
    List<$node> $memberNames; 
    
    /**
     * Whenever the simple name (i.e. Map) is used
     * i.e. static method call Map.of(...), static field access, cast, etc.)
     */
    $node $simpleName;
    
    public $classUse( Class type ){
        if( type.getPackage() != null ) {
            this.packageName = type.getPackage().getName();
        } else{
            this.packageName ="";
        }
        this.type = type;
        this.$fullName = new $node(type.getCanonicalName());
        //Note: there can be (0, 1, or more OTHER nodes that represent
        //Inner member classes, i.e. not fully qualified 
        
        this.$simpleName = new $node(type.getSimpleName());        
        $memberNames = $classUse.buildMemberClassNames( type );
    }
    
    public $classUse constraint(Predicate<Node> constraint){
        $fullName.constraint(constraint);
        $simpleName.constraint(constraint);
        $memberNames.forEach(m -> constraint(constraint));
        return this;
    }
    
    public <N extends _model._node> N replaceIn(N _n, Class replacement) {
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            //System.out.println( "TOP CLASS ");
            replaceIn( ((_type)_n).findCompilationUnit(), replacement);
            return _n;
        }
        //System.out.println( "AST CLASS ");
        replaceIn(_n.ast(), replacement);
        return _n;
    }
    
    private static List<$node> buildMemberClassNames(Class clazz ){
        List<$node> nodes = new ArrayList<>();
        String currentPath = clazz.getSimpleName();
        buildMemberClassNames(clazz, currentPath, nodes);
        
        //reverse the order of the nmes... (which puts the LONGER NAMES UP FRONT)
        // so that when we match/replace, we look for a pattern in the longer 
        // name FIRST before looking at shorter names (to match and replace)
        Collections.reverse(nodes);
        return nodes;
    }
    
    private static void buildMemberClassNames(Class clazz, String currentPath, List<$node> nodes){        
        if( clazz.isMemberClass()){
            Class declaringClass = clazz.getDeclaringClass();
            currentPath = declaringClass.getSimpleName()+"."+currentPath;
            nodes.add( $node.of(currentPath) );
            buildMemberClassNames( declaringClass, currentPath, nodes);
        }
    }
    
    public <N extends Node> N replaceIn(N astRootNode, Class replacement) {
        $fullName.replaceIn(astRootNode, replacement.getCanonicalName());
        
        //List<p_node> nodes = buildMemberClass_p_nodes( type );
        $memberNames.forEach(n -> n.replaceIn(astRootNode, replacement.getSimpleName()));

        $simpleName.replaceIn(astRootNode, replacement.getSimpleName());
        
        return astRootNode;
    }
   
    public <N extends Node> N replaceIn(N astRootNode, String replacement) {
        $fullName.replaceIn(astRootNode, replacement);
        $memberNames.forEach(n -> n.replaceIn(astRootNode, replacement));
        $simpleName.replaceIn(astRootNode, replacement);        
        return astRootNode;
    }

    public <N extends _model._node> N replaceIn(N _n, String replacement) {
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            replaceIn( ((_type)_n).findCompilationUnit(), replacement);
            return _n;
        }
        replaceIn(_n.ast(), replacement);
        return _n;
    }
    
    public <N extends Node> N replaceIn(N astRootNode, Node replacement) {
        $fullName.replaceIn(astRootNode, replacement);
        $memberNames.forEach(n -> n.replaceIn(astRootNode, replacement));
        $simpleName.replaceIn(astRootNode, replacement);        
        return astRootNode;
    }

    public <N extends _model._node> N replaceIn(N _n, Node replacement) {
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            replaceIn( ((_type)_n).findCompilationUnit(), replacement);
            return _n;
        }
        replaceIn(_n.ast(), replacement);
        return _n;
    }
}
