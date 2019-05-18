package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithThrownExceptions;
import com.github.javaparser.ast.nodeTypes.NodeWithTypeParameters;
import com.github.javaparser.ast.type.ReferenceType;
import draft.*;
import draft.java.*;
import draft.java._model._node;
import draft.java._typeParameter._typeParameters;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * prototype for an _import declaration on a Java top level _type 
 *
 */
public final class $typeParameters
    implements Template<_typeParameters>, $proto<_typeParameters>, $method.$part, $constructor.$part {

    /**
     * 
     * @param clazz
     * @return 
     */
    public static final List<_typeParameters> list(Class clazz){
        return of().listIn(clazz);
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final List<_typeParameters> list(Class clazz, String pattern){
        return list(_type.of(clazz), pattern);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @return 
     */
    public static final <T extends _type> List<_typeParameters> list( T _type, String pattern ){
        return $typeParameters.of(pattern).listIn(_type);
    }
    
    /**
     * 
     * @param clazz
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final List<_typeParameters> list(Class clazz, _typeParameters _protoTarget, Predicate<_typeParameters> constraint){
        return list(_type.of(clazz), _protoTarget, constraint);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<_typeParameters> list( T _type, _typeParameters _protoTarget, Predicate<_typeParameters> constraint){
        return $typeParameters.of(_protoTarget).constraint(constraint).listIn(_type);
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final List<_typeParameters> list( Class clazz, String pattern, Predicate<_typeParameters> constraint){
        return list(_type.of(clazz), pattern, constraint);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<_typeParameters> list( T _type, String pattern, Predicate<_typeParameters> constraint){
        return $typeParameters.of(pattern).constraint(constraint).listIn(_type);
    }   

    /**
     * 
     * @param clazz
     * @param _protoTarget
     * @return 
     */
    public static final List<Select> listSelected(Class clazz, _typeParameters _protoTarget){
        return (List<Select>)$typeParameters.of(_protoTarget).listSelectedIn(clazz);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */
    public static final <T extends _type> List<Select> listSelected( T _type, _typeParameters _protoTarget){
        return $typeParameters.of(_protoTarget).listSelectedIn(_type);
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final List<Select> listSelected( Class clazz, String pattern ){
       return $typeParameters.listSelected(_type.of(clazz), pattern);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @return 
     */
    public static final <T extends _type> List<Select> listSelected( T _type, String pattern ){
        return $typeParameters.of(pattern).listSelectedIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<Select> listSelected( T _type, _typeParameters _protoTarget, Predicate<Select> constraint){
        return $typeParameters.of(_protoTarget).listSelectedIn(_type, constraint);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<Select> listSelected( T _type, String pattern, Predicate<_typeParameters> constraint){
        return $typeParameters.of(pattern).constraint(constraint).listSelectedIn(_type);
    }    
   
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */      
    public static final <T extends _type> _typeParameters first( T _type, _typeParameters _protoTarget){
        return $typeParameters.of(_protoTarget).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @return 
     */      
    public static final <T extends _type> _typeParameters first( T _type, String pattern ){
        return $typeParameters.of(pattern).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> _typeParameters first( T _type, _typeParameters _protoTarget, Predicate<_typeParameters> constraint){
        return $typeParameters.of(_protoTarget).constraint(constraint).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <T extends _type> _typeParameters first( T _type, String pattern, Predicate<_typeParameters> constraint){
        return $typeParameters.of(pattern).constraint(constraint).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forEach( T _type, _typeParameters _protoTarget, Consumer<_typeParameters> actionFn){
        return (T) $typeParameters.of(_protoTarget).forEachIn(_type, actionFn);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forEach( T _type, String pattern, Consumer<_typeParameters> actionFn){
        return $typeParameters.of(pattern).forEachIn(_type, actionFn);
    }    
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forEach( T _type, _typeParameters _protoTarget, Predicate<_typeParameters> constraint, Consumer<_typeParameters> actionFn){
        return $typeParameters.of(_protoTarget).constraint(constraint).forEachIn(_type, actionFn);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoTargetImport
     * @param constraint
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forEach( T _type, String protoTarget, Predicate<_typeParameters> constraint, Consumer<_typeParameters> actionFn){
        return $typeParameters.of(protoTarget).constraint(constraint).forEachIn(_type, actionFn);
    }

    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forSelected( T _type, _typeParameters _protoTarget, Consumer<Select> actionFn){
        return (T) $typeParameters.of(_protoTarget).forSelectedIn(_type, actionFn);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forSelected( T _type, String pattern, Consumer<Select> actionFn){
        return $typeParameters.of(pattern).forSelectedIn(_type, actionFn);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forSelected( T _type, _typeParameters _protoTarget, Predicate<_typeParameters> constraint, Consumer<Select> actionFn){
        return $typeParameters.of(_protoTarget).constraint(constraint)
                .forSelectedIn(_type, actionFn);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @param constraint
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forSelected( T _type, String pattern, Predicate<_typeParameters> constraint, Consumer<Select> actionFn){
        return $typeParameters.of(pattern).constraint(constraint)
                .forSelectedIn(_type, actionFn);
    }    
   
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, _typeParameters _protoTarget){
        return $typeParameters.of(_protoTarget).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, String pattern ){
        return $typeParameters.of(pattern).selectFirstIn(_type);
    }
               
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, _typeParameters _protoTarget, Predicate<_typeParameters> constraint){
        return $typeParameters.of(_protoTarget).constraint(constraint).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, String pattern, Predicate<_typeParameters> constraint){
        return $typeParameters.of(pattern).constraint(constraint).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _sourceProto
     * @param _targetProto
     * @return 
     */
    public static final <T extends _type> T replace( T _type, _typeParameters _sourceProto, _typeParameters _targetProto ){
        return $typeParameters.of(_sourceProto).replaceIn(_type, _targetProto);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param sourcePattern
     * @param targetPattern
     * @return 
     */
    public static final <T extends _type> T replace( T _type, String sourcePattern, String targetPattern ){        
        return $typeParameters.of(sourcePattern).replaceIn(_type, targetPattern);
    }   
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */
    public static final <T extends _type> T remove( T _type, _typeParameters _protoTarget){
        return $typeParameters.of(_protoTarget).removeIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @return 
     */
    public static final <T extends _type> T remove( T _type, String pattern ){
        return $typeParameters.of(pattern).removeIn(_type);
    }
    
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> T remove( T _type, _typeParameters _protoTarget, Predicate<_typeParameters> constraint){
        return $typeParameters.of(_protoTarget).constraint(constraint).removeIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <T extends _type> T remove( T _type, String pattern, Predicate<_typeParameters> constraint){
        return $typeParameters.of(pattern).constraint(constraint).removeIn(_type);
    }
    
    public static $typeParameters any(){
        return of();
    }
    
    /**
     * Match ANY import
     * @return 
     */
    public static $typeParameters of(){
        return new $typeParameters( t-> true );        
    }
    
    /**
     * 
     * @param pattern
     * @return 
     */
    public static $typeParameters of( String... pattern){
        return new $typeParameters( _typeParameters.of(pattern)  );
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public static $typeParameters of( Predicate<_typeParameters> constraint ){
        return new $typeParameters( constraint );
    }
    
    /**
     * 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $typeParameters of( String pattern, Predicate<_typeParameters> constraint){        
        return new $typeParameters( _typeParameters.of(pattern) ).constraint(constraint);
    }
    
    
    /**
     * 
     * @param _proto
     * @return 
     */
    public static $typeParameters of( _typeParameters _proto){
        return new $typeParameters( _proto  );
    }

    /**
     * 
     * @param _proto
     * @param constraint
     * @return 
     */
    public static $typeParameters of( _typeParameters _proto, Predicate<_typeParameters> constraint){
        return new $typeParameters( _proto ).constraint(constraint);
    }
    
    public Predicate<_typeParameters> constraint = t-> true;
        
    public List<$id> typeParamPatterns = new ArrayList<>();
    
    private $typeParameters(_typeParameters proto ){
        proto.forEach(t-> typeParamPatterns.add(new $id(t.toString())));
    }

    private $typeParameters( Predicate<_typeParameters> constraint ){        
        this.constraint = constraint;
    }
    
    /**
     * ADDS an additional matching constraint to the prototype
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public $typeParameters addConstraint( Predicate<_typeParameters>constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    /**
     * SETs a constraint
     * @param constraint
     * @return 
     */
    public $typeParameters constraint( Predicate<_typeParameters> constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    /**
     * 
     * @return 
     */
    public boolean matches( String... thrws ){
        return matches(_typeParameters.of(thrws) );
    }
    
    public boolean matches( CallableDeclaration astCallable ){
        return select(astCallable) != null;
    }
    
    /**
     * 
     * @param _i
     * @return 
     */
    public boolean matches( _typeParameters _t){
        return select( _t ) != null;
    }

    
    public Select select( CallableDeclaration astCallable ){
        return select( _typeParameters.of( astCallable ) );
    }
    
    /**
     * 
     * @param _i
     * @return 
     */
    public Select select(_typeParameters _i){
        if( this.constraint.test(_i)){            
            return null;
            /*
            List<ReferenceType> listed = _i.list();
            if( listed.size() < this.typeParamPatterns.size() ){
                return null;
            }
            Tokens ts = new Tokens();
            for(int i=0;i<typeParamPatterns.size();i++ ){
                $id $tp = typeParamPatterns.get(i);
                
                Optional<ReferenceType> ort = 
                    listed.stream().filter(rt -> $tp.matches(rt.asString()) ).findFirst();
                if( !ort.isPresent() ){
                    return null;
                }
                ts = $tp.decomposeTo( ort.get().asString(), ts );
                listed.remove( ort.get() );
                if( ts == null ){
                    return null;
                }        
            }
            if( ts != null ){
                return new Select(_i, $nameValues.of(ts) );
            }
            */
        }
            
        return null;
            
    }
 
    @Override
    public String toString() {
        return "($typeParameters) : \"" +this.typeParamPatterns + "\"";
    }

    @Override
    public _typeParameters construct(Translator translator, Map<String, Object> keyValues) {        
        
        return null;
        
        
        /** 
        _typeParameters _ts = _typeParameters.of();
        if( keyValues.get("$typeParameters$") != null ){ //PARAMETER OVERRIDE
            $typeParameters $ths = $typeParameters.of( keyValues.get("$typeParameters$").toString() );
            Map<String,Object> kvs = new HashMap<>();
            kvs.putAll(keyValues);
            kvs.remove("$typeParameters$");
            return $ths.construct(translator, kvs);
        } 
        
        this.typeParamPatterns.forEach( tp -> _ts.add( tp.compose(translator, keyValues) ) );        
        return _ts;
        */ 
    }
    
    
    public Tokens decomposeTo( _typeParameters _ts, Tokens allTokens ){
        if(allTokens == null){
            return allTokens;
        }
        Select sel = select(_ts);
        if( sel != null ){
            if( allTokens.isConsistent(sel.args.asTokens()) ){
                allTokens.putAll(sel.args.asTokens());
                return allTokens;
            }
        }
        return null;
    }
     
    @Override
    public $typeParameters $(String target, String $Name) {
        this.typeParamPatterns.forEach(t -> t.$(target, $Name) );        
        return this;
    }

    /**
     * Hardcode (one or more) parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param hardcodedKeyValues the key parameter NAME and VALUE to hardcode
     * @return the modified Stencil
     */
    public $typeParameters hardcode$( Tokens hardcodedKeyValues ) {
        this.typeParamPatterns.forEach(t -> t.hardcode$(Translator.DEFAULT_TRANSLATOR, hardcodedKeyValues) );
        return this;
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $typeParameters hardcode$( Object... keyValues ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, Tokens.of( keyValues ) );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param translator translates values to be hardcoded into the Stencil
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $typeParameters hardcode$( Translator translator, Object... keyValues ) {
        return hardcode$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $typeParameters hardcode$( Translator translator, Tokens kvs ) {
        this.typeParamPatterns.forEach(t -> t.hardcode$(translator, kvs) );
        return this;
    }

    @Override
    public List<String> list$() {
        List<String> $names = new ArrayList<>();
        this.typeParamPatterns.forEach(t -> $names.addAll( t.list$() ) );
        return $names;
    }

    @Override
    public List<String> list$Normalized() {
        List<String> $namesNormalized = new ArrayList<>();
        this.typeParamPatterns.forEach(t -> $namesNormalized.addAll( t.list$Normalized() ) );
        return $namesNormalized.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Returns the first _import that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _import that matches (or null if none found)
     */
    public _typeParameters firstIn( _node _n ){
        if( _n.ast().findCompilationUnit().isPresent() ){
            Optional<CallableDeclaration> f = _n.ast().findCompilationUnit().get().findFirst(CallableDeclaration.class, s -> this.matches(s) );         
            if( f.isPresent()){
                return _typeParameters.of(f.get());
            }
        }        
        return null;
    }

    /**
     * Returns the first _import that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _import that matches (or null if none found)
     */
    public _typeParameters firstIn( Node astNode ){
        if( astNode.findCompilationUnit().isPresent() ){
            Optional<CallableDeclaration> f = astNode.findCompilationUnit().get()
                .findFirst(CallableDeclaration.class, s -> this.matches(s) );         
            if( f.isPresent()){
                return _typeParameters.of(f.get());
            }
        }
        return null;
    }
    
    /**
     * Returns the first _import that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _import that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n ){
        if( _n.ast().findCompilationUnit().isPresent() ){
            Optional<CallableDeclaration> f = _n.ast().findCompilationUnit().get()
                    .findFirst(CallableDeclaration.class, s -> this.matches(s) );         
            if( f.isPresent()){
                return select(f.get());
            }
        }
        return null;
    }

    /**
     * Returns the first _import that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _import that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode ){
        if( astNode.findCompilationUnit().isPresent() ){
            Optional<CallableDeclaration> f = astNode.findCompilationUnit().get()
                    .findFirst(CallableDeclaration.class, s -> this.matches(s) );         
            if( f.isPresent()){
                return select(f.get());
            }         
        }
        return null;
    }
    
    /**
     * Returns the first _import that matches the pattern and constraint
     * @param _n the _java node
     * @param selectConstraint
     * @return  the first _import that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n, Predicate<Select> selectConstraint ){
        if( _n.ast().findCompilationUnit().isPresent() ){
            Optional<CallableDeclaration> f = _n.ast().findCompilationUnit().get()
                    .findFirst(CallableDeclaration.class, s -> {
                        Select sel = this.select(s); 
                        return sel != null && selectConstraint.test(sel);
                    });                
            if( f.isPresent()){
                return select(f.get());
            }
        }
        return null;
    }

    /**
     * Returns the first _import that matches the pattern and constraint
     * @param astNode the node to look through
     * @param selectConstraint
     * @return  the first _import that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode, Predicate<Select> selectConstraint ){
        if( astNode.findCompilationUnit().isPresent() ){
            Optional<CallableDeclaration> f = astNode.findCompilationUnit().get()
                    .findFirst(CallableDeclaration.class, s -> {
                        Select sel = this.select(s); 
                        return sel != null && selectConstraint.test(sel);
                    });         
            if( f.isPresent()){
                return select(f.get());
            }         
        }
        return null;
    }
    
    
    @Override
    public List<_typeParameters> listIn( _node _n ){
        return listIn( _n.ast() );
    }

    @Override
    public List<_typeParameters> listIn(Node astNode ){
        if( astNode.findCompilationUnit().isPresent()){
            List<_typeParameters> l = new ArrayList<>();
            astNode.findCompilationUnit().get().walk(CallableDeclaration.class, t->{
                if( this.matches(t) ){
                    l.add(_typeParameters.of(t));
                }
            } );
            return l;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Select> listSelectedIn( Node astNode ){
        List<Select>sts = new ArrayList<>();
        if(astNode.findCompilationUnit().isPresent() ){
            astNode.findCompilationUnit().get().walk(CallableDeclaration.class, e-> {
                Select s = select( e );
                if( s != null ){
                    sts.add( s);
                }
            });
        }
        return sts;
    }

    /**
     * 
     * @param clazz
     * @return 
     */
    public List<Select> listSelectedIn(Class clazz){
        return listSelectedIn(_type.of(clazz));
    }
    
    @Override
    public List<Select> listSelectedIn( _node _n ){
        return listSelectedIn( _n.ast() );        
    }
    
    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn( Class clazz, Predicate<Select> selectConstraint ){
        return listSelectedIn(_type.of(clazz), selectConstraint);
    }
    
    /**
     * 
     * @param astNode
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn( Node astNode, Predicate<Select> selectConstraint ){
        List<Select>sts = new ArrayList<>();
        if(astNode.findCompilationUnit().isPresent() ){
            astNode.findCompilationUnit().get().walk(CallableDeclaration.class, e-> {
                Select s = select( e );
                if( s != null && selectConstraint.test(s)){
                    sts.add( s);
                }
            });
        }
        return sts;
    }

    /**
     * 
     * @param _n
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn( _node _n, Predicate<Select> selectConstraint ){
        return listSelectedIn( _n.ast() );        
    }

    /**
     * Build ans return a _type with the import prototypes removed
     * @param clazz
     * @return 
     */
    @Override
    public _type removeIn( Class clazz){
        return removeIn( _type.of(clazz));
    }
    
    /**
     * Remove all matching occurrences of the proto in the node and return the
     * modified node
     * @param astNode the root node to start search
     * @param <N> the input node TYPE
     * @return the modified node
     */
    @Override
    public <N extends Node> N removeIn(N astNode ){
        astNode.walk( CallableDeclaration.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.typeParameters.astHolder().setTypeParameters(new NodeList());
            }
        });
        return astNode;
    }

    /**
     *
     * @param _n the root model node to start from
     * @param <N> the TYPE of model node
     * @return the modified model node
     */
    @Override
    public <N extends _node> N removeIn(N _n ){
        removeIn( _n.ast() );
        return _n;
    }
  
    /**
     * 
     * @param clazz
     * @param importDecl
     * @return 
     */
    public _type replaceIn(Class clazz, String importDecl){
        return replaceIn( _type.of(clazz), importDecl);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param importDecl
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, String importDecl){
        return replaceIn(_n, $typeParameters.of(importDecl));
    }
    
    /**
     * 
     * @param clazz
     * @param _i
     * @return 
     */
    public _type replaceIn(Class clazz, _typeParameters _i){
        return replaceIn( _type.of(clazz), _i);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _i
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, _typeParameters _i){
        Node astNode = _n.ast();
        if( astNode.findCompilationUnit().isPresent() ){
            astNode.findCompilationUnit().get().walk( CallableDeclaration.class, e-> {
                Select sel = select( e );
                if( sel != null ){
                    _typeParameters _ths = $typeParameters.of(_i).construct(sel.args.asTokens());
                    sel.typeParameters.astHolder().setTypeParameters(_ths.ast());                    
                }
            });
        }
        return _n;
    }
    
    /**
     * 
     * @param clazz
     * @param $i
     * @return 
     */
    public _type replaceIn(Class clazz, $typeParameters $i ){
        return replaceIn(_type.of(clazz), $i);
    }
    
        
    /**
     * Replace all occurrences of the template in the code with the replacement
     * (composing the replacement from the constructed tokens in the source)
     *
     * @param _n the model to find replacements
     * @param $i the template to be constructed as the replacement
     * @param <N> the TYPE of model
     * @return
     */
    public <N extends _node> N replaceIn(N _n, $typeParameters $i ){
        replaceIn( _n.ast(), $i);        
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param $i
     * @return 
     */
    public <N extends Node> N replaceIn(N astNode, $typeParameters $i ){
        if( astNode.findCompilationUnit().isPresent() ){
            astNode.findCompilationUnit().get().walk(CallableDeclaration.class, e-> {
                Select sel = select( e );
                if( sel != null ){                    
                    _typeParameters _ths = $i.construct(sel.args.asTokens());
                    sel.typeParameters.astHolder().setTypeParameters(_ths.ast());                    
                }
            });
        }
        return astNode;
    }

    /**
     * 
     * @param clazz
     * @param selectConsumer
     * @return 
     */
    public _type forSelectedIn( Class clazz, Consumer<Select> selectConsumer){
        return forSelectedIn(_type.of(clazz), selectConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConsumer
     * @return 
     */
    public <T extends _type> T forSelectedIn( T _t, Consumer<Select> selectConsumer ){
        forSelectedIn(_t.findCompilationUnit(), selectConsumer);
        return _t;
    }

    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectConsumer
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Consumer<Select> selectConsumer ){
        if( astNode.findCompilationUnit().isPresent()){
            astNode.findCompilationUnit().get().walk(CallableDeclaration.class, e-> {
                Select sel = select( e );
                if( sel != null ){
                    selectConsumer.accept( sel );
                }
            });
        }
        return astNode;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public <T extends _type> T forSelectedIn(T _t, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer ){
        forSelectedIn(_t.findCompilationUnit(), selectConstraint, selectConsumer);
        return _t;
    }

    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer ){
        if( astNode.findCompilationUnit().isPresent()){
            astNode.findCompilationUnit().get().walk(CallableDeclaration.class, e-> {
                Select sel = select( e );
                if( sel != null && selectConstraint.test(sel) ){
                    selectConsumer.accept( sel );
                }
            });
        }
        return astNode;
    }
    
    @Override
    public <N extends Node> N forEachIn(N astNode, Consumer<_typeParameters> _typeParamActionFn){
        astNode.walk(CallableDeclaration.class, e-> {
            Select sel = select(e);
            if( sel != null ){
                _typeParamActionFn.accept(sel.typeParameters );
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_typeParameters> _importActionFn){
        forEachIn( _n.ast(), _importActionFn);        
        return _n;
    }
 
    /**
     * A Matched Selection result returned from matching a prototype $import
     * inside of some CompilationUnit
     */
    public static class Select implements $proto.selected, 
        $proto.selected_model<_typeParameters> {
    
        public final _typeParameters typeParameters;
        public final $nameValues args;

        public Select(_typeParameters _i, $nameValues tokens){
            this.typeParameters = _i;  
            this.args = tokens;
        }
        
        public Select( NodeWithTypeParameters astImport, $nameValues tokens){
            this.typeParameters = _typeParameters.of(astImport );
            this.args = tokens;
        }
        
        @Override
        public $nameValues args(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$typeParameters.Select {"+ System.lineSeparator()+
                Text.indent(typeParameters.toString() )+ System.lineSeparator()+
                Text.indent("$args : " + args) + System.lineSeparator()+
                "}";
        }
        
        @Override
        public _typeParameters model() {
            return typeParameters;
        }
    }
}