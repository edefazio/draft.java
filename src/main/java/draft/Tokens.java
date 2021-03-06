package draft;

import java.util.*;

/**
 * A simple mapping of (String) keys to (Object) values
 * (API used for simple initialization and sequential _bulk loading)
 *
 * Tokens tokens = Tokens.of("A", 100, "NAME", "Barbara");
 * //it's equivalent to:
 * Map<String,Object> tokens = new HashMap<>();
 * m.put("A", 100);
 * m.put("NAME", "Barbara");
 *
 * @author Eric
 */
public class Tokens implements Map<String,Object>{

    private final Map<String,Object> kvMap;

    public Tokens(){
        this.kvMap = new HashMap<>();
    }

    public static Tokens of(Tokens o ){
        Tokens kvs = new Tokens();
        kvs.kvMap.putAll( o.kvMap );
        return kvs;
    }

    public static Tokens of(Object...data ){
        Tokens kvs = new Tokens();
        return kvs.add(data);
    }

    /**
     * Create and return a copy/clone of the Tokens
     * @return a copy of the Tokens
     */
    public Tokens copy(){
        return of( this );
    }

    /**
     * add/overwrites (potentially many) key VALUE pairs
     *
     * @param keyValuePairs
     * @return the updated Tokens
     */
    public Tokens add(Object...keyValuePairs){
        if( keyValuePairs.length % 2 != 0 ){
            throw new DraftException("expected in pairs (divisible where 2), got ("
                    + keyValuePairs.length + ")");
        }
        for(int i=0;i<keyValuePairs.length; i+=2){
            put((String)Translator.DEFAULT_TRANSLATOR.translate( keyValuePairs[i] ), keyValuePairs[i+1] );
        }
        return this;
    }

    @Override
    public void clear(){
        this.kvMap.clear();
    }

    public Object get( String key ){
        return this.kvMap.get(key);
    }

    @Override
    public void putAll( Map<? extends String, ?> map ){
        this.kvMap.putAll(map);
    }

    @Override
    public int size() {
        return this.kvMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.kvMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.kvMap.containsKey(key);
    }

    /** 
     * check that it has this this exact key VALUE combination
     * @param key     
     * @param value     
     * @return true if 
     */
    public boolean has( String key, String value){
        Object val = this.get(key);
        if( val != null ){
            return val.equals(value);
        }
        return false;
    }

    /**
     * returns true is all keys are within the Tokens
     * @param keys all the keys to find
     * @return true if all keys are found in the Tokens
     */
    public boolean containsKeys( String...keys ){
        for( String key : keys ) {
            if( !containsKey( key ) ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean containsValue(Object value) {
        return this.kvMap.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return this.kvMap.get(key);
    }

    @Override
    public Object remove(Object key) {
        return this.kvMap.remove(key);
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.kvMap.entrySet();
    }

    @Override
    public Object put( String key, Object value ){
        return this.kvMap.put(key, value );
    }

    public Map<String,Object> map(){
        return kvMap;
    }

    @Override
    public Set<String> keySet(){
        return new HashSet<>(kvMap.keySet());
    }

    /** return the keys as a List
     * @return
     */
    public List<String> keys(){
        return new ArrayList<>(kvMap.keySet());
    }

    @Override
    public List<Object> values(){
        return new ArrayList<>(this.kvMap.values());
    }

    @Override
    public boolean equals(Object o ){
        if( o == null || !o.getClass().equals( Tokens.class ) ) {
            return false;
        }
        Tokens kvs = (Tokens)o;
        return this.kvMap.equals( kvs.kvMap );
    }

    /**
     * For all key-values in t, if there are any common keys in this that have a different VALUE,
     * they are inconsistent ( return false)
     *
     * CAN WE MERGE THIS TOKENS t INTO this Tokens without overriding any Key-VALUE bindings that exist?
     *
     * @param t a prospective Tokens
     * @return true if we can merge the tokens without conflict, false if there is a conflict
     */
    public boolean isConsistent( Tokens t ){
        Optional<String> unmatchedKey = 
            t.kvMap.keySet().stream().filter( k-> containsKey(k) && (!Objects.equals( t.get(k), get(k)) )).findFirst();
        if( unmatchedKey.isPresent() ){
            System.out.println( "Unmatched key \""+ unmatchedKey.get()+"\" values : \""+ t.get(unmatchedKey.get())+ "\"  \""+ get(unmatchedKey.get())+"\""  );
            return false;
        }
        return true;        
    }

    public boolean is( Tokens tks ){
        return this.equals(tks);
    }

    public boolean is( Object...keyValues ){
        try{
            return is( Tokens.of(keyValues));
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public int hashCode(){
        return this.kvMap.hashCode();
    }

    @Override
    public String toString(){
        return this.kvMap.toString();
    }
}
