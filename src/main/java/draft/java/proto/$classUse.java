/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import com.github.javaparser.ast.Node;
import draft.Tokens;
import draft.java.Ast;
import draft.java._model;
import draft.java._type;
import java.util.function.Predicate;

/**
 * any references to a specific type (either simple or fully qualified)
 * 
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
     * Whenever the simple name (i.e. Map) is used
     * i.e. static method call Map.of(...), static field access, cast, etc.)
     */
    $node $simpleName;
    
    public $classUse( Class type ){
        this.packageName = type.getPackageName();
        this.type = type;
        this.$fullName = new $node(type.getCanonicalName());
        //Note: there can be (0, 1, or more OTHER niodes that represent
        //Inner member classes, i.e. not fully qualified 
        this.$simpleName = new $node(type.getSimpleName());        
    }
    
    public $classUse constraint(Predicate<Node> constraint){
        $fullName.constraint(constraint);
        $simpleName.constraint(constraint);
        return this;
    }
    
    public <N extends _model._node> N replaceIn(N _n, Class replacement) {
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            System.out.println( "TOP CLASS ");
            replaceIn( ((_type)_n).findCompilationUnit(), replacement);
            return _n;
        }
        System.out.println( "AST CLASS ");
        replaceIn(_n.ast(), replacement);
        return _n;
    }
    
    public <N extends Node> N replaceIn(N astRootNode, Class replacement) {
        
        //$node $n = new $node(replacement.getCanonicalName());
        //$typeUse $replacement = $typeUse.of(replacement);
        
        //System.out.println( "   FULL NAME PATTERN "+ $fullName.pattern +" to " + $replacement.$fullName.pattern);
        
        
        $fullName.replaceIn(astRootNode, replacement.getCanonicalName());
        
        /*
        if( type.isMemberClass() ){
            String mem = type.getDeclaringClass().getSimpleName();
            //$node $n = $node.of( mem + "." +type.getSimpleName() );
            System.out.println( "MEM  \"" + mem + "." +type.getSimpleName()+"\"");
            Ast.of(type, code)
            $node.replace( astRootNode, mem + "." +type.getSimpleName(), replacement.getSimpleName() );
            //Class dc = type.getDeclaringClass();
        }
        */
        //$simpleName.replaceIn(astRootNode, replacement.getSimpleName());
        return astRootNode;
    }
    
   
    public <N extends Node> N replaceIn(N astRootNode, String replacement) {
        $fullName.replaceIn(astRootNode, replacement);
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
