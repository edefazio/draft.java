/**
 * <P>Bottom up Prototypes that can be used for constructing, querying, removing
 * and replacing code.</ P>
 * 
 * While the core draft API has the tools to define a proper Data Definition 
 * Language:
 * <PRE>
 * Data Definition Language(DDL) 
 * (CREATE, DROP, TRUNCATE, COMMENT, RENAME)
 * </PRE>
 * 
 * draft.java.proto provides an additional API that provides tools similar to
 * a Data Manipulation Language:
 * 
 * Data Manipulation Language (DML)
 * (SELECT, INSERT, UPDATE, DELETE)
 * 
 * Unlike Top-Down (DML) Data Manipulation Languages (like SQL) start with the 
 * premise that data is structured in a hierarchial top down fashion 
 * (schemas, tables, columns, rows)
 * 
 * (i.e. a "top down query" in SQL
 * <PRE>
 * SELECT * 
 * FROM SCHEMA.TABLE <-- Top Level
 * WHERE ROW = 1     <-- Detail
 * </PRE>
 * 
 * Prototypes are a "bottom up" way of representing patterns in the 
 * code, since the code is more a "network of nodes" than a purely top down 
 * structure. (i.e. the code hierarchy has indeterminate depth).  
 * <PRE>
 * 
 * //Querying and modifying existing code
 * _class _c = _class.of(SomeClass.class);
 * 
 * //With Prototypes, we start bottom up (here a Statement)
 * $stmt $println =  $stmt.of("System.out.println($any$);");
 * 
 * // the prototype will "walk" the code and SELECT, DELETE or REPLACE 
 * // the code as it is walked
 * 
 * //SELECT list any System.out.println methods that occur in SomeClass.class
 * $println.listIn(_c); 
 * 
 * //DELETE remove any System out println calls in the SomeClass.class
 * $println.removeIn(_c);
 * 
 * //UPDATE replace all System.out.println with LOG.debug statements
 * $println.replaceIn(_c, "LOG.debug($any$);");
 * </PRE>
 * 
 */
package draft.java.proto;
