package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithThrownExceptions;
import com.github.javaparser.ast.type.ReferenceType;
import draft.*;
import draft.java.*;
import draft.java._model._node;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * prototype for an _import declaration on a Java top level _type 
 *
 */
public final class $throws
    implements Template<_throws>, $proto<_throws>, $method.$part, $constructor.$part {

    /**
     * 
     * @param clazz
     * @return 
     */
    public static final List<_throws> list(Class clazz){
        return of().listIn(clazz);
    }
    
    /**
     * 
     * @param clazz
     * @param protoTarget
     * @return 
     */
    public static final List<_throws> list( Class clazz, Class protoTarget ){
        return list(_type.of(clazz), protoTarget);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */
    public static final <T extends _type> List<_throws> list( T _type, Class _protoTarget){
        return $throws.of(_protoTarget).listIn(_type);
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final List<_throws> list(Class clazz, String pattern){
        return list(_type.of(clazz), pattern);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @return 
     */
    public static final <T extends _type> List<_throws> list( T _type, String pattern ){
        return $throws.of(pattern).listIn(_type);
    }
    
    /**
     * 
     * @param clazz
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final List<_throws> list(Class clazz, _throws _protoTarget, Predicate<_throws> constraint){
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
    public static final <T extends _type> List<_throws> list( T _type, _throws _protoTarget, Predicate<_throws> constraint){
        return $throws.of(_protoTarget).constraint(constraint).listIn(_type);
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final List<_throws> list( Class clazz, String pattern, Predicate<_throws> constraint){
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
    public static final <T extends _type> List<_throws> list( T _type, String pattern, Predicate<_throws> constraint){
        return $throws.of(pattern).constraint(constraint).listIn(_type);
    }
    
    /**
     * 
     * @param clazz
     * @param astProtoTarget
     * @param constraint
     * @return 
     */
    public static final List<_throws> list(Class clazz, Class exception, Predicate<_throws> constraint){
        return list(_type.of(clazz), exception, constraint);
    }    
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<_throws> list( T _type, Class target, Predicate<_throws> constraint){
        return $throws.of(target).constraint(constraint).listIn(_type);
    }

    /**
     * 
     * @param clazz
     * @param _protoTarget
     * @return 
     */
    public static final List<Select> listSelected(Class clazz, _throws _protoTarget){
        return (List<Select>)$throws.of(_protoTarget).listSelectedIn(clazz);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */
    public static final <T extends _type> List<Select> listSelected( T _type, _throws _protoTarget){
        return $throws.of(_protoTarget).listSelectedIn(_type);
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final List<Select> listSelected( Class clazz, String pattern ){
       return $throws.listSelected(_type.of(clazz), pattern);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @return 
     */
    public static final <T extends _type> List<Select> listSelected( T _type, String pattern ){
        return $throws.of(pattern).listSelectedIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @return 
     */
    public static final <T extends _type> List<Select> listSelected( T _type, Class target ){
        return $throws.of(target).listSelectedIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<Select> listSelected( T _type, _throws _protoTarget, Predicate<Select> constraint){
        return $throws.of(_protoTarget).listSelectedIn(_type, constraint);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<Select> listSelected( T _type, String pattern, Predicate<_throws> constraint){
        return $throws.of(pattern).constraint(constraint).listSelectedIn(_type);
    }    
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param constraint
     * @return 
     */
    public static final <T extends _type> List<Select> listSelected( T _type, Class target, Predicate<_throws> constraint){
        return $throws.of(target).constraint(constraint).listSelectedIn(_type);
    }
   
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */      
    public static final <T extends _type> _throws first( T _type, _throws _protoTarget){
        return $throws.of(_protoTarget).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @return 
     */      
    public static final <T extends _type> _throws first( T _type, String pattern ){
        return $throws.of(pattern).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @return the first _import matching
     */ 
    public static final <T extends _type> _throws first( T _type, Class target ){
        return $throws.of(target).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> _throws first( T _type, _throws _protoTarget, Predicate<_throws> constraint){
        return $throws.of(_protoTarget).constraint(constraint).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <T extends _type> _throws first( T _type, String pattern, Predicate<_throws> constraint){
        return $throws.of(pattern).constraint(constraint).firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param constraint
     * @return 
     */
    public static final <T extends _type> _throws first( T _type, Class target, Predicate<_throws> constraint){
        return $throws.of(target)
                .constraint(constraint)
                .firstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forEach( T _type, _throws _protoTarget, Consumer<_throws> actionFn){
        return (T) $throws.of(_protoTarget).forEachIn(_type, actionFn);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forEach( T _type, String pattern, Consumer<_throws> actionFn){
        return $throws.of(pattern).forEachIn(_type, actionFn);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forEach( T _type, Class target, Consumer<_throws> actionFn){
        return $throws.of(target).forEachIn(_type, actionFn);
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
    public static final <T extends _type> T forEach( T _type, _throws _protoTarget, Predicate<_throws> constraint, Consumer<_throws> actionFn){
        return $throws.of(_protoTarget).constraint(constraint).forEachIn(_type, actionFn);
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
    public static final <T extends _type> T forEach( T _type, String protoTarget, Predicate<_throws> constraint, Consumer<_throws> actionFn){
        return $throws.of(protoTarget).constraint(constraint).forEachIn(_type, actionFn);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param constraint
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forEach( T _type, Class target, Predicate<_throws> constraint, Consumer<_throws> actionFn){
        return $throws.of(target).constraint(constraint).forEachIn(_type, actionFn);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forSelected( T _type, _throws _protoTarget, Consumer<Select> actionFn){
        return (T) $throws.of(_protoTarget).forSelectedIn(_type, actionFn);
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
        return $throws.of(pattern).forSelectedIn(_type, actionFn);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forSelected( T _type, Class target, Consumer<Select> actionFn){
        return $throws.of(target).forSelectedIn(_type, actionFn);
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
    public static final <T extends _type> T forSelected( T _type, _throws _protoTarget, Predicate<_throws> constraint, Consumer<Select> actionFn){
        return $throws.of(_protoTarget).constraint(constraint)
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
    public static final <T extends _type> T forSelected( T _type, String pattern, Predicate<_throws> constraint, Consumer<Select> actionFn){
        return $throws.of(pattern).constraint(constraint)
                .forSelectedIn(_type, actionFn);
    }    
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param constraint
     * @param actionFn
     * @return 
     */
    public static final <T extends _type> T forSelected( T _type, Class target, Predicate<_throws> constraint, Consumer<Select> actionFn){
        return $throws.of(target).constraint(constraint).forSelectedIn(_type, actionFn);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, _throws _protoTarget){
        return $throws.of(_protoTarget).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, String pattern ){
        return $throws.of(pattern).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, Class... target ){
        return $throws.of(target).selectFirstIn(_type);
    }
               
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, _throws _protoTarget, Predicate<_throws> constraint){
        return $throws.of(_protoTarget).constraint(constraint).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, String pattern, Predicate<_throws> constraint){
        return $throws.of(pattern).constraint(constraint).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param constraint
     * @return 
     */
    public static final <T extends _type> Select selectFirst( T _type, Class target, Predicate<_throws> constraint){
        return $throws.of(target).constraint(constraint).selectFirstIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _sourceProto
     * @param _targetProto
     * @return 
     */
    public static final <T extends _type> T replace( T _type, _throws _sourceProto, _throws _targetProto ){
        return $throws.of(_sourceProto).replaceIn(_type, _targetProto);
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
        return $throws.of(sourcePattern).replaceIn(_type, targetPattern);
    }   
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param protoSource
     * @param protoTarget
     * @return 
     */
    public static final <T extends _type> T replace( T _type, Class protoSource, Class protoTarget ){
        return $throws.of(protoSource)
            .replaceIn(_type, _throws.of(protoTarget));
    }

    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @return 
     */
    public static final <T extends _type> T remove( T _type, _throws _protoTarget){
        return $throws.of(_protoTarget).removeIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @return 
     */
    public static final <T extends _type> T remove( T _type, String pattern ){
        return $throws.of(pattern).removeIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @return 
     */
    public static final <T extends _type> T remove( T _type, Class target ){
        return $throws.of(_throws.of(target)).removeIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param _protoTarget
     * @param constraint
     * @return 
     */
    public static final <T extends _type> T remove( T _type, _throws _protoTarget, Predicate<_throws> constraint){
        return $throws.of(_protoTarget).constraint(constraint).removeIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <T extends _type> T remove( T _type, String pattern, Predicate<_throws> constraint){
        return $throws.of(pattern).constraint(constraint).removeIn(_type);
    }
    
    /**
     * 
     * @param <T>
     * @param _type
     * @param target
     * @param constraint
     * @return 
     */
    public static final <T extends _type> T remove( T _type, Class target, Predicate<_throws> constraint){
        return $throws.of(_throws.of(target)).constraint(constraint).removeIn(_type);
    }
    
    public static $throws any(){
        return of();
    }
    
    /**
     * Match ANY import
     * @return 
     */
    public static $throws of(){
        return new $throws( t-> true );        
    }
    
    /**
     * 
     * @param pattern
     * @return 
     */
    public static $throws of( String... pattern){
        return new $throws( _throws.of(pattern)  );
    }

    public static $throws of( Class<? extends Throwable>...throwsClasses ){
        return new $throws( _throws.of(throwsClasses ) );
    }
    /**
     * 
     * @param constraint
     * @return 
     */
    public static $throws of( Predicate<_throws> constraint ){
        return new $throws( constraint );
    }
    
    /**
     * 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $throws of( String pattern, Predicate<_throws> constraint){        
        return new $throws( _throws.of(pattern) ).constraint(constraint);
    }
    
    /**
     * 
     * @param clazz
     * @return 
     
    public static <C extends Throwable> $throws of( Class<C>... clazz ){
        return new $throws( clazz  );
    }
    */ 
    
    /**
     * 
     * @param clazz
     * @param constraint
     * @return 
     */
    public static <C extends Throwable> $throws of( Class<C> clazz, Predicate<_throws> constraint){
        return new $throws( clazz  ).constraint(constraint);
    }
    
    /**
     * 
     * @param _proto
     * @return 
     */
    public static $throws of( _throws _proto){
        return new $throws( _proto  );
    }

    /**
     * 
     * @param _proto
     * @param constraint
     * @return 
     */
    public static $throws of( _throws _proto, Predicate<_throws> constraint){
        return new $throws( _proto ).constraint(constraint);
    }
    
    public Predicate<_throws> constraint = t-> true;
        
    public List<$id> throwsPatterns = new ArrayList<>();
    
    
    private <C extends Throwable> $throws( Class<C>...thrownExceptions ){
        this(_throws.of(thrownExceptions));
    } 
    
    private $throws(_throws proto ){
        proto.forEach( t-> throwsPatterns.add(new $id(t.toString())));
    }

    private $throws( Predicate<_throws> constraint ){        
        this.constraint = constraint;
    }
    
    /**
     * ADDS an additional matching constraint to the prototype
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public $throws addConstraint( Predicate<_throws>constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    /**
     * SETs a constraint
     * @param constraint
     * @return 
     */
    public $throws constraint( Predicate<_throws> constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    /**
     * 
     * @return 
     */
    public boolean matches( String... thrws ){
        return matches(_throws.of(thrws) );
    }


    public boolean matches( NodeWithThrownExceptions nwt ){
        return select( nwt ) != null;
    }
    
    public boolean matches( CallableDeclaration astCallable ){
        return select(astCallable) != null;
    }
    
    /**
     * 
     * @param _i
     * @return 
     */
    public boolean matches( _throws _t){
        return select( _t ) != null;
    }

    public Select select( NodeWithThrownExceptions astCallable ){
        return select( _throws.of( astCallable ) );
    }
    public Select select( CallableDeclaration astCallable ){
        return select( _throws.of( astCallable ) );
    }
    
    /**
     * 
     * @param _i
     * @return 
     */
    public Select select(_throws _i){
        if( this.constraint.test(_i)){            
            List<ReferenceType> listed = _i.list();
            if( listed.size() < this.throwsPatterns.size() ){
                return null;
            }
            Tokens ts = new Tokens();
            for(int i=0;i<throwsPatterns.size();i++ ){
                $id $tp = throwsPatterns.get(i);
                
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
        }
        return null;
    }
 
    @Override
    public String toString() {
        return "($throws) : \"" +this.throwsPatterns + "\"";
    }

    @Override
    public _throws construct(Translator translator, Map<String, Object> keyValues) {        
        _throws _ts = new _throws();
        if( keyValues.get("$throws$") != null ){ //PARAMETER OVERRIDE
            $throws $ths = $throws.of( keyValues.get("$throws$").toString() );
            Map<String,Object> kvs = new HashMap<>();
            kvs.putAll(keyValues);
            kvs.remove("$throws$");
            return $ths.construct(translator, kvs);
        } 
        this.throwsPatterns.forEach( tp -> _ts.add( tp.compose(translator, keyValues) ) );        
        return _ts;
    }
    
    
     public Tokens decomposeTo( _throws _ts, Tokens allTokens ){
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
    public $throws $(String target, String $Name) {
        this.throwsPatterns.forEach(t -> t.$(target, $Name) );        
        return this;
    }

    /**
     * Hardcode (one or more) parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param hardcodedKeyValues the key parameter NAME and VALUE to hardcode
     * @return the modified Stencil
     */
    public $throws hardcode$( Tokens hardcodedKeyValues ) {
        this.throwsPatterns.forEach(t -> t.hardcode$(Translator.DEFAULT_TRANSLATOR, hardcodedKeyValues) );
        return this;
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $throws hardcode$( Object... keyValues ) {
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
    public $throws hardcode$( Translator translator, Object... keyValues ) {
        return hardcode$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $throws hardcode$( Translator translator, Tokens kvs ) {
        this.throwsPatterns.forEach(t -> t.hardcode$(translator, kvs) );
        return this;
    }

    @Override
    public List<String> list$() {
        List<String> $names = new ArrayList<>();
        this.throwsPatterns.forEach(t -> $names.addAll( t.list$() ) );
        return $names;
    }

    @Override
    public List<String> list$Normalized() {
        List<String> $namesNormalized = new ArrayList<>();
        this.throwsPatterns.forEach(t -> $namesNormalized.addAll( t.list$Normalized() ) );
        return $namesNormalized.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Returns the first _import that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _import that matches (or null if none found)
     */
    public _throws firstIn( _node _n ){
        if( _n.ast().findCompilationUnit().isPresent() ){
            Optional<CallableDeclaration> f = _n.ast().findCompilationUnit().get().findFirst(CallableDeclaration.class, s -> this.matches(s) );         
            if( f.isPresent()){
                return _throws.of(f.get());
            }
        }        
        return null;
    }

    /**
     * Returns the first _import that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _import that matches (or null if none found)
     */
    public _throws firstIn( Node astNode ){
        if( astNode.findCompilationUnit().isPresent() ){
            Optional<CallableDeclaration> f = astNode.findCompilationUnit().get()
                .findFirst(CallableDeclaration.class, s -> this.matches(s) );         
            if( f.isPresent()){
                return _throws.of(f.get());
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
    public List<_throws> listIn( _node _n ){
        return listIn( _n.ast() );
    }

    @Override
    public List<_throws> listIn(Node astNode ){
        if( astNode.findCompilationUnit().isPresent()){
            List<_throws> l = new ArrayList<>();
            astNode.findCompilationUnit().get().walk(CallableDeclaration.class, t->{
                if( this.matches(t) ){
                    l.add(_throws.of(t));
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
                for(int i=0;i< this.throwsPatterns.size(); i++){
                    final NodeList<ReferenceType> nodes = sel.thrown.astNodeWithThrows.getThrownExceptions();
                    $id th = this.throwsPatterns.get(i);
                    nodes.removeIf(t -> th.matches(t.toString()) );
                }
                //sel._i.astNodeWithThrows.getThrownExceptions().clear();
                //this.throwsPatterns.
                //NodeList<ReferenceType> tes = e.getThrownExceptions();
                ///TODO remove the thrown exceptions
                //tes.stream().filter(t -> this. );                
                //sel.ast().removeForced();
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
     * @param throwClasses
     * @return 
     */
    public _type replaceIn(Class clazz, Class<? extends Throwable>... throwClasses){
        return replaceIn(_type.of(clazz), _throws.of(throwClasses));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param throwClasses
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, Class<? extends Throwable>... throwClasses){
        return replaceIn(_n, $throws.of(throwClasses));
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
        return replaceIn(_n, $throws.of(importDecl));
    }
    
    /**
     * 
     * @param clazz
     * @param _i
     * @return 
     */
    public _type replaceIn(Class clazz, _throws _i){
        return replaceIn( _type.of(clazz), _i);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _i
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, _throws _i){
        Node astNode = _n.ast();
        if( astNode.findCompilationUnit().isPresent() ){
            astNode.findCompilationUnit().get().walk( CallableDeclaration.class, e-> {
                Select sel = select( e );
                if( sel != null ){
                    $throws res = $throws.of(_i);
                    _throws ct = res.construct(sel.args.asTokens());
                    for(int i=0;i< this.throwsPatterns.size(); i++){
                        final NodeList<ReferenceType> nodes = sel.thrown.astNodeWithThrows.getThrownExceptions();
                        $id th = this.throwsPatterns.get(i);
                        nodes.removeIf(t -> th.matches(t.toString()) );
                    }
                    sel.thrown.astNodeWithThrows.getThrownExceptions().addAll( res.construct(sel.args).list() );
                    //sel.ast().replace(_i.ast() );
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
    public _type replaceIn(Class clazz, $throws $i ){
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
    public <N extends _node> N replaceIn(N _n, $throws $i ){
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
    public <N extends Node> N replaceIn(N astNode, $throws $i ){
        if( astNode.findCompilationUnit().isPresent() ){
            astNode.findCompilationUnit().get().walk(CallableDeclaration.class, e-> {
                Select sel = select( e );
                if( sel != null ){
                    for(int i=0;i< this.throwsPatterns.size(); i++){
                        final NodeList<ReferenceType> nodes = sel.thrown.astNodeWithThrows.getThrownExceptions();
                        $id th = this.throwsPatterns.get(i);
                        nodes.removeIf(t -> th.matches(t.toString()) );
                    }
                    _throws _ths = $i.construct(sel.args.asTokens());
                    sel.thrown.addAll(_ths.list());
                    //sel.ast().replace($i.construct(sel.args).ast() );
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
    public <N extends Node> N forEachIn(N astNode, Consumer<_throws> _importActionFn){
        astNode.walk(CallableDeclaration.class, e-> {
            Select sel = select(e);
            if( sel != null ){
                _importActionFn.accept(sel.thrown );
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_throws> _importActionFn){
        forEachIn( _n.ast(), _importActionFn);        
        return _n;
    }

    /**
     * A Matched Selection result returned from matching a prototype $import
     * inside of some CompilationUnit
     */
    public static class Select implements $proto.selected, 
        $proto.selected_model<_throws> {
    
        public final _throws thrown;
        public final $nameValues args;

        public Select(_throws _i, $nameValues tokens){
            this.thrown = _i;  
            this.args = tokens;
        }
        
        public Select( NodeWithThrownExceptions astImport, $nameValues tokens){
            this.thrown = _throws.of(astImport );
            this.args = tokens;
        }
        
        @Override
        public $nameValues args(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$throws.Select {"+ System.lineSeparator()+
                Text.indent(thrown.toString() )+ System.lineSeparator()+
                Text.indent("$args : " + args) + System.lineSeparator()+
                "}";
        }

        public boolean is(String... throwsDecl){
            return thrown.is(throwsDecl);
        }
        
        public boolean is(Class... clazz){
            return thrown.is(clazz);
        }
        
        public boolean has(Class clazz){
            return thrown.has( clazz );
        }
        
        public boolean has(Class... clazz){
            return thrown.has( clazz );
        }
        
        public boolean has(String thrown){
            return this.thrown.has( thrown );
        }
        
        public boolean has(String... throwNames){
            return thrown.has( throwNames );
        }
        
        @Override
        public _throws model() {
            return thrown;
        }
    }
}
