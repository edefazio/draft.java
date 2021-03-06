package draft.java.macro;

import com.github.javaparser.ast.Node;
import draft.DraftException;
import draft.java._anno._hasAnnos;
import draft.java.*;

import java.lang.annotation.*;

/**
 * Annotation Macro to add imports to a _type
 */
@Retention( RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
public @interface _replace {
    String[] value() default {};

    class Macro implements _macro<_hasAnnos> {
        String[] replacementKeyValues;

        @Override
        public String toString(){
            StringBuilder body = new StringBuilder();
            for(int i=0;i<replacementKeyValues.length;i+=2){
                body.append("( \"");
                body.append(replacementKeyValues[i]);
                body.append("\" to \"");
                body.append(replacementKeyValues[i+1]);
                body.append("\" )");
            }
            return "macro[replace(" + body.toString()+ ")]"; 
        }
        
        public Macro( _replace _r ){
            this.replacementKeyValues = _r.value();
        }
        public Macro( String[] replacements ){
            this.replacementKeyValues = replacements;
        }

        public _hasAnnos apply( _hasAnnos _a){
            return to( _a, replacementKeyValues);
        }

        public static _anno._hasAnnos to(_anno._hasAnnos _model, String[] replacements ){

            //_model.removeAnnos(_replace.class);
            String str = _model.toString();
            for(int i=0;i<replacements.length; i+=2){
                str = str.replace(replacements[i], replacements[i+1]);
            }
            Node oldNode = null;
            Node newNode = null;
            if( _model instanceof _type ) {
                oldNode = ((_type) _model).ast();
                newNode = _java.of(((_type) _model).ast().getClass(), str);
            }else {
                oldNode = ((draft.java._java._node) _model).ast();
                newNode = _java.of(((draft.java._java._node) _model).ast().getClass(), str);
            }
            //System.out.println( oldNode.getClass() + " "+ newNode.getClass() );
            boolean isReplaced = oldNode.replace(newNode);
            if (!isReplaced) {
                if( oldNode.getParentNode().isPresent()){
                    //System.out.println( "OLDNODE HAS A PARENT");
                } else{
                    //System.out.println( "OLDNODE HAS NO PARENT");
                }
                throw new DraftException(
                        "Unable to replaceIn "+oldNode+" with "+newNode+" in Macro at AST level");
            }
            return (_anno._hasAnnos) _java.of(newNode);
        }
    }
}  