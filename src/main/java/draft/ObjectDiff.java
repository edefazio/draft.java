package draft;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Finding the differences in the components between two {@link Composite}s
 * NOTE: changed name from Diff to ObjectDiff to avoid name conflicts with 
 * {@link name.fraser.neil.plaintext.Diff} which is a plaintext diff tool 
 * 
 * "ObjectDiff" is Object Diffing
 * 
 * @author Eric
 */
public enum ObjectDiff {
    ;
 
    /**
     * Representation of a diff of components between two {@link Composite}s
     * NOTE: left or right COULD be null
     */    
    public static class Entry{
        public Entry(String name, Object left, Object right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }

        public Object left;
        public Object right;
        public String name;
    }
    
    public static Entry of( String name, Object left, Object right ) {
        return new Entry(name, left, right);
    }
    
    /**
     * Consumer for accepting a Diff for modularity
     * (Although currently the diff method is private to minimize API bloat
     */
    private interface ObjectDiffConsumer{
        public void onDiff( String key, Object left, Object right );
    }
    
    /**
     * Componentize and compare the components of 2 Composite entities
     * 
     * @param left
     * @param right
     * @return 
     */
    public static DiffList components( Composite left, Composite right ){
        return components( left, right, new String[0]);
    }
    
    /**
     * 
     * @param left
     * @param right
     * @param excludeNames Named entities( i.e. {@link draft.java._java.Component}
     * @return 
     */
    public static DiffList components( Composite left, Composite right, Named...excludeNames){
       Set<String> excluding = new HashSet<>();
       Arrays.stream(excludeNames).forEach(n -> excluding.add(n.getName()) );
       DiffList dl = new DiffList();            
        
        components(left, right, 
            (String key, Object left1, Object right1) -> {
                dl.add(key, left1, right1);
            }, 
            excluding);
        return dl;    
    }
    
    /**
     * Shallow (1-level deep) diff of the individual properties
     * on the two composite entities
     * 
     * @param left the left Composite to be componentized and compared
     * @param right the right Composite to be componentized and compared
     * @param excludeNames the names of components that are excluded from being compared
     * @return a DiffList containing all differences between the left and right
     */
    public static DiffList components( 
        Composite left, Composite right, String... excludeNames ){
        
        DiffList dl = new DiffList();            
        
        components(left, right, 
            (String key, Object left1, Object right1) -> {
                dl.add(key, left1, right1);
            }, 
            excludeNames);
        return dl;    
    }
    
    /**
     * Diff the Composite components of left and right... omitting components that
     * are labeled by excluding (i.e. {@link draft.java._java.Component#JAVADOC#name} )
     * 
     * @param left the left component node to diff against the right node
     * @param right the right component node to diff against the left
     * @param excludeNames names of part types that are omitted (i.e. {@link draft.java._java.Component#JAVADOC#name})
     * @return a DiffList Mapping Parts and their Objects that are different between left and right
     */
    private static void components( 
        Composite left, Composite right, ObjectDiffConsumer diffConsumer, String... excludeNames ){
        
        Set<String> omit = new HashSet<>();
        Arrays.stream(excludeNames).forEach( n -> omit.add(n) );
        final Set<String> excludedComponents = omit;
        components(left, right, diffConsumer, excludedComponents);
    }
    
    /**
     * Diff the Composite components of left and right... omitting components that
     * are labeled by excluding (i.e. {@link draft.java._java.Component#JAVADOC#name} )
     * 
     * @param left the left component node to diff against the right node
     * @param right the right component node to diff against the left
     * @param excludeNames names of part types that are omitted (i.e. {@link draft.java._java.Component#JAVADOC#name})
     * @return a DiffList Mapping Parts and their Objects that are different between left and right
     */
    private static void components( 
        Composite left, Composite right, ObjectDiffConsumer diffConsumer, Set<String> excludeNames ){
        Map<String,Object> ld = left.componentize();
        Map<String,Object> rd = right.componentize();
        //DiffList dl = new DiffList();
        ld.forEach((leftKey,leftComponent) -> {
            if( !excludeNames.contains( leftKey ) ){
                Object rightComponent = rd.get(leftKey);
                if( !Objects.equals(leftComponent, rightComponent)){
                    diffConsumer.onDiff(leftKey, leftComponent, rightComponent );
                }
            }
        });        
        //for "LIKE" things this will remove ALL
        rd.keySet().removeAll( ld.keySet() );
        
        rd.forEach((rightKey,rightComponent) -> {
            if( !excludeNames.contains( rightKey ) ){
                Object leftComponent = ld.get(rightKey);
                if( !Objects.equals(leftComponent, rightComponent)){
                    diffConsumer.onDiff(rightKey, leftComponent, rightComponent );
                }
            }
        });
    }    
    
   
     /**
     * Find the FIRST diff between the left and right composites and return it
     * (or NULL if there are no differences)
     *  the strategy is ... go through the left component keys and match against the
     * values in right... If no diffs are found, remove all of lefts keys from right 
     * and check the remaining keys
     * 
     * @param left the left component node to diff against the right node
     * @param right the right component node to diff against the left
     * @param excludeNames names of component types that are omitted (i.e. {@link draft.java._java.Component#JAVADOC#name} )
     * @return a Diff between left and right, or null if no differences exist
     */
    public static Entry first( 
        Composite left, Composite right, Named... excludeNames ){
        Set<String> excl = new HashSet<>();
        Arrays.stream(excludeNames).forEach(e -> excl.add(e.getName()) );
        return first(left, right, excl);
    }
    
    /**
     * Find the FIRST diff between the left and right composites and return it
     * (or NULL if there are no differences)
     *  the strategy is ... go through the left component keys and match against the
     * values in right... If no diffs are found, remove all of lefts keys from right 
     * and check the remaining keys
     * 
     * @param left the left component node to diff against the right node
     * @param right the right component node to diff against the left
     * @param excludeNames names of component types that are omitted (i.e. {@link draft.java._java.Component#JAVADOC#name} )
     * @return a Diff between left and right, or null if no differences exist
     */
    public static Entry first( 
        Composite left, Composite right, String... excludeNames ){
        
        Set<String> exclude = new HashSet<>();
        Arrays.stream(excludeNames).forEach( n -> exclude.add(n) );
        //final Set<String> omitComponents = omit;
        return first( left, right, exclude );
    }
    
    public static Entry first( Composite left, Composite right, Set<String> excludeNames ){
        Map<String,Object> ld = left.componentize();
        Map<String,Object> rd = right.componentize();
        
        Optional<String> firstDiffKey = ld.keySet().stream().filter((leftKey) -> {
            if( !excludeNames.contains( leftKey ) ){
                Object leftComponent = ld.get(leftKey);
                Object rightComponent = rd.get(leftKey);
                return !Objects.equals(leftComponent, rightComponent);
            }
            return false;
        }).findFirst();
        
        if( firstDiffKey.isPresent() ){
            return new Entry( firstDiffKey.get(), ld.get(firstDiffKey.get()), rd.get(firstDiffKey.get()) );
        }
        
        rd.keySet().removeAll( ld.keySet() );
        
        firstDiffKey = ld.keySet().stream().filter((rightKey) -> {
            if( !excludeNames.contains( rightKey ) ){
                Object leftComponent = ld.get(rightKey);
                Object rightComponent = rd.get(rightKey);
                return !Objects.equals(leftComponent, rightComponent);                
            }
            return false;
        }).findFirst();
        
        if( firstDiffKey.isPresent() ){
            return new Entry( firstDiffKey.get(), ld.get(firstDiffKey.get()), rd.get(firstDiffKey.get()) );
        }
        return null;
    }
    
    /**
     * List of Diffs between two entities
     */    
    public static class DiffList{
        public List<Entry> diffs = new ArrayList<>();
        
        public int size(){
            return diffs.size();
        }
        
        public boolean isEmpty(){
            return size() == 0;
        }
        
        public List<String>names(){
            return diffs.stream().map(d -> d.name ).collect(Collectors.toList());
        }
        
        public Object left( String name ){
            Optional<Entry> od = this.diffs.stream().filter( d -> d.name.equals( name ) ).findFirst();
            if(od.isPresent() ){
                return od.get().left;
            }
            throw new DraftException("no diff for \""+ name+"\"");
        }
        
        public Object right( String name ){
            Optional<Entry> od = this.diffs.stream().filter( d -> d.name.equals( name ) ).findFirst();
            if(od.isPresent() ){
                return od.get().right;
            }
            throw new DraftException("no diff for \""+ name+"\"");
        }
        
        public boolean containsNames( String ...names){
            return Arrays.stream(names)
                    .allMatch(n -> diffs.stream().filter(d -> d.name.equals(n)).findFirst().isPresent());
        }
        
        public DiffList add( String name, Object left, Object right ){
            diffs.add(ObjectDiff.of(name, left, right));
            return this;
        }
        
        public DiffList addList( List<ObjectDiff.Entry> des ){
            for(int i=0; i< des.size();i++){
                diffs.add( des.get(i));
            }            
            return this;
        }
        
        @Override
        public String toString(){
            if( isEmpty() ){
                return " - no diffs found -";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("(").append( diffs.size()).append(") diffs").append(System.lineSeparator());
            this.diffs.forEach( d -> {
                sb.append("    ").append( d.name ).append(System.lineSeparator() );
                });
            return sb.toString();
        }
    }
}
