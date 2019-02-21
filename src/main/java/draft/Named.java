package draft;

/**
 * An entity that can be identified by a name
 * This is also used for Standardizing names across components 
 * (i.e. {@link draft.java._java.Component} )
 * 
 * @author Eric
 */
public interface Named {

    /** get the name of the named entity */
    String getName();
}
