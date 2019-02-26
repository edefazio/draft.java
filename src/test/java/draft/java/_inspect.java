package draft.java;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import draft.ObjectDiff;
import draft.ObjectDiff.DiffList;
import static draft.java.Ast.typesEqual;
import draft.java._parameter._parameters;
import draft.java._typeParameter._typeParameters;
import java.util.*;
import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;

/**
 *
 * @author Eric
 */
public interface _inspect<T> {
    
    public boolean equivalent( T left, T right );
    
    public DiffList diff( String path, DiffList dl, T left, T right );
    
    default DiffList diff( T left, T right ){
        DiffList dl = new DiffList();
        return diff( "", dl, left, right);
    }
    
    default DiffList diff(String path, T left, T right ){
        DiffList dl = new DiffList();
        return diff( path, dl, left, right);
    }
    
    static final Map<_java.Component, _inspect> _inspectMap = new HashMap<>();
    
    public static _enumConstantsInspect INSPECT_ENUM_CONSTANTS = new _enumConstantsInspect();
    
    public static class _enumConstantsInspect implements _inspect<List<_enum._constant>>{

        @Override
        public boolean equivalent( List<_enum._constant> left, List<_enum._constant> right) {
            Set<_enum._constant>ls = new HashSet<>();
            Set<_enum._constant>rs = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            return Objects.equals(ls, rs);
        }

        public _enum._constant sameName( _enum._constant target, Set<_enum._constant> source ){
            Optional<_enum._constant> ec = 
                    source.stream().filter(c-> c.getName().equals(target.getName())).findFirst();
            if( ec.isPresent()){
                return ec.get();
            }
            return null;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, List<_enum._constant> left, List<_enum._constant> right) {
            Set<_enum._constant>ls = new HashSet<>();
            Set<_enum._constant>rs = new HashSet<>();
            Set<_enum._constant>both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            both.addAll(ls);
            both.retainAll(rs);
            
            ls.removeAll(both);
            ls.forEach(f -> {
                _enum._constant cc = sameName( f, rs );
                if( cc != null ){
                    rs.remove( cc );
                    dl.add(path+_java.Component.CONSTANT, f, cc);
                } else{
                    dl.add(path+_java.Component.CONSTANT, f, null);
                }
            });
            
            rs.forEach(f -> {
                dl.add(path+_java.Component.CONSTANT, null,f);                
            });
            return dl;
        }        
    }
    
    public static ListImportDeclarationInspect INSPECT_IMPORTS = new ListImportDeclarationInspect(_java.Component.IMPORT.getName() );
    
    public static class ListImportDeclarationInspect implements _inspect<NodeList<ImportDeclaration>>{        

        private String name;
        
        public ListImportDeclarationInspect(String name){
            this.name = name;
        }
                
        @Override
        public boolean equivalent(NodeList<ImportDeclaration> left, NodeList<ImportDeclaration> right) {
            Set<ImportDeclaration> ls = new HashSet<>();
            Set<ImportDeclaration> rs = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            return Objects.equals( ls, rs);
        }

        @Override
        public DiffList diff(String path, DiffList dl, NodeList<ImportDeclaration> left, NodeList<ImportDeclaration> right) {
            Set<ImportDeclaration> ls = new HashSet<>();
            Set<ImportDeclaration> rs = new HashSet<>();
            Set<ImportDeclaration> both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            
            both.addAll( left);
            both.retainAll( right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            
            ls.forEach(c -> dl.add(name, c, null) );
            rs.forEach(c -> dl.add(name, null, c) );
            
            return dl;
        }        
    }
    
    public static ListClassOrInterfaceTypeInspect INSPECT_EXTENDS = new ListClassOrInterfaceTypeInspect(_java.Component.EXTENDS.getName() );
    
    public static ListClassOrInterfaceTypeInspect INSPECT_IMPLEMENTS = new ListClassOrInterfaceTypeInspect(_java.Component.IMPLEMENTS.getName() );
    
    public static class ListClassOrInterfaceTypeInspect implements _inspect<NodeList<ClassOrInterfaceType>>{        

        private String name;
        
        public ListClassOrInterfaceTypeInspect(String name){
            this.name = name;
        }
                
        @Override
        public boolean equivalent(NodeList<ClassOrInterfaceType> left, NodeList<ClassOrInterfaceType> right) {
            Set<ClassOrInterfaceType> ls = new HashSet<>();
            Set<ClassOrInterfaceType> rs = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            return Objects.equals( ls, rs);
        }

        @Override
        public DiffList diff(String path, DiffList dl, NodeList<ClassOrInterfaceType> left, NodeList<ClassOrInterfaceType> right) {
            Set<ClassOrInterfaceType> ls = new HashSet<>();
            Set<ClassOrInterfaceType> rs = new HashSet<>();
            Set<ClassOrInterfaceType> both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            
            both.addAll( left);
            both.retainAll( right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            
            ls.forEach(c -> dl.add(name, c, null) );
            rs.forEach(c -> dl.add(name, null, c) );
            
            return dl;
        }        
    }
    
    public static _enumConstantInspect INSPECT_ENUM_CONSTANT = new _enumConstantInspect();
    
    public static class _enumConstantInspect implements _inspect<_enum._constant>{        
        
        @Override
        public boolean equivalent(_enum._constant left, _enum._constant right) {
            return Objects.equals(left,right);
        }
        
         @Override
        public DiffList diff(String path, DiffList dl,_enum._constant left, _enum._constant right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.ELEMENT, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.ELEMENT, left, null);
                return dl;
            }
            INSPECT_ANNOS.diff(path, dl, left.getAnnos(), right.getAnnos());
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());
            INSPECT_NAME.diff(path, dl, left.getName(), right.getName());
            INSPECT_ARGUMENTS.diff(path, dl, left.listArguments(), right.listArguments());
            INSPECT_METHODS.diff(path, dl, left.listMethods(), right.listMethods());
            INSPECT_FIELDS.diff(path, dl, left.listFields(), right.listFields());            
            return dl;
        }                
    }
    //INSPECT_STATIC_BLOCKS
    
    //INSPECT_ANNOTATION_ELEMENTS
    //INSPECT_ENUM_CONSTANTS
    
    //INSPECT_METHODS
    //INSPECT_FIELDS
    //INSPECT_CONSTRUCTORS
    
    //INSPECT_EXTENDS
    //INSPECT_IMPLEMENTS
    
    //INSPECT CLASS
    //INSPECT_IMPORTS
    //INSPECT_PACKAGE (String)
    
    //INSPECT_NESTS
    
    
    public static _annotationElementsInspect INSPECT_ANNOTATION_ELEMENTS = new _annotationElementsInspect();
    
    public static class _annotationElementsInspect implements _inspect<List<_annotation._element>>{

        @Override
        public boolean equivalent( List<_annotation._element> left, List<_annotation._element> right) {
            Set<_annotation._element>ls = new HashSet<>();
            Set<_annotation._element>rs = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            return Objects.equals(ls, rs);
        }

        public _annotation._element sameName( _annotation._element target, Set<_annotation._element> source ){
            Optional<_annotation._element> ec = 
                    source.stream().filter(c-> c.getName().equals(target.getName())).findFirst();
            if( ec.isPresent()){
                return ec.get();
            }
            return null;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, List<_annotation._element> left, List<_annotation._element> right) {
            Set<_annotation._element>ls = new HashSet<>();
            Set<_annotation._element>rs = new HashSet<>();
            Set<_annotation._element>both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            both.addAll(ls);
            both.retainAll(rs);
            
            ls.removeAll(both);
            ls.forEach(f -> {
                _annotation._element cc = sameName( f, rs );
                if( cc != null ){
                    rs.remove( cc );
                    dl.add(path+_java.Component.ELEMENT, f, cc);
                } else{
                    dl.add(path+_java.Component.ELEMENT, f, null);
                }
            });
            
            rs.forEach(f -> {
                dl.add(path+_java.Component.ELEMENT, null,f);                
            });
            return dl;
        }       
    }
    
    public static _annotationElementInspect INSPECT_ANNOTATION_ELEMENT = new _annotationElementInspect();
    
    public static class _annotationElementInspect implements _inspect<_annotation._element>{

        @Override
        public boolean equivalent(_annotation._element left, _annotation._element right) {
            return Objects.equals(left,right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _annotation._element left, _annotation._element right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.ELEMENT, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.ELEMENT, left, null);
                return dl;
            }
            INSPECT_NAME.diff(path, dl, left.getName(), right.getName());
            INSPECT_TYPE_REF.diff(path, dl, left.getType(), right.getType());
            INSPECT_DEFAULT.diff(path, dl, left.getDefaultValue(), right.getDefaultValue());
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());
            INSPECT_ANNOS.diff(path, dl, left.getAnnos(), right.getAnnos());
            
            return dl;
        }
    }
    
    
    public static _fieldsInspect INSPECT_FIELDS = new _fieldsInspect();
    
    public static class _fieldsInspect implements _inspect<List<_field>>{

        @Override
        public boolean equivalent(List<_field> left, List<_field> right) {
            Set<_field> lf = new HashSet<>();
            Set<_field> rf = new HashSet<>();
            lf.addAll(left);
            rf.addAll(right);
            return Objects.equals(lf, rf);
        }

        public _field getFieldNamed(Set<_field> _fs , String name){
            Optional<_field> _f = 
                    _fs.stream().filter(f-> f.getName().equals( name) ).findFirst();
            if( _f.isPresent() ){
                return _f.get();
            }
            return null;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, List<_field> left, List<_field> right) {
            Set<_field> lf = new HashSet<>();
            Set<_field> rf = new HashSet<>();
            lf.addAll(left);
            rf.addAll(right);
            Set<_field> both = new HashSet<>();
            both.addAll(left);
            both.retainAll(right);
            
            //organize fields that have the same name as edits
            lf.removeAll(both);
            rf.removeAll(both);
            
            lf.forEach(f ->  {
                _field match = getFieldNamed( rf, f.getName() );
                if( match != null ){
                    dl.add(path+_java.Component.FIELD, f, match);
                } else{
                    dl.add(path+_java.Component.FIELD, f, null);
                }
            });
            
            rf.forEach(f ->  {
                _field match = getFieldNamed( lf, f.getName() );
                if( match != null ){
                    dl.add(path+_java.Component.FIELD, match, f);
                } else{
                    dl.add(path+_java.Component.FIELD, null, f);
                }
            });
            return dl;
        }        
    }
    
    public static _fieldInspect INSPECT_FIELD = new _fieldInspect();
    
    public static class _fieldInspect implements _inspect<_field>{

        @Override
        public boolean equivalent(_field left, _field right) {
            return Objects.equals(left,right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _field left, _field right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.FIELD, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.FIELD, left, null);
                return dl;
            }
            INSPECT_NAME.diff(path, dl, left.getName(), right.getName());
            INSPECT_TYPE_REF.diff(path, dl, left.getType(), right.getType());
            INSPECT_MODIFIERS.diff(path, dl, left.getModifiers(), right.getModifiers());
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());
            INSPECT_ANNOS.diff(path, dl, left.getAnnos(), right.getAnnos());
            INSPECT_INIT.diff(path, dl, left.getInit(), right.getInit() );
            return dl;
        }        
    }
    
    public static _staticBlocksInspect INSPECT_STATIC_BLOCKS 
            = new _staticBlocksInspect();
    
    public static class _staticBlocksInspect 
            implements _inspect<List<_staticBlock>> {

        @Override
        public boolean equivalent(List<_staticBlock> left, List<_staticBlock> right) {
            Set<_staticBlock> ls = new HashSet<>();
            Set<_staticBlock> rs = new HashSet<>();
            ls.addAll( left);
            rs.addAll(right);
            
            return Objects.equals(ls, rs);
        }

        @Override
        public DiffList diff(String path, DiffList dl, List<_staticBlock> left, List<_staticBlock> right) {
            Set<_staticBlock> ls = new HashSet<>();
            Set<_staticBlock> rs = new HashSet<>();
            Set<_staticBlock> both = new HashSet<>();
            ls.addAll( left);
            rs.addAll(right);
            
            both.addAll(left);
            both.retainAll(right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            
            ls.forEach(s -> dl.add(path+_java.Component.STATIC_BLOCK, s, null));
            rs.forEach(s -> dl.add(path+_java.Component.STATIC_BLOCK, null, s));
            return dl;
        }        
    }
    
    public static _staticBlockInspect INSPECT_STATIC_BLOCK 
            = new _staticBlockInspect();
            
    public static class _staticBlockInspect 
            implements _inspect<_staticBlock> {

        @Override
        public boolean equivalent(_staticBlock left, _staticBlock right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _staticBlock left, _staticBlock right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.STATIC_BLOCK, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.STATIC_BLOCK, left, null);
                return dl;
            }
            INSPECT_BODY.diff(path, dl, left.getBody(), right.getBody());            
            return dl;
        }        
    }
    
    public static final _constructorsInspect INSPECT_CONSTRUCTORS = 
            new _constructorsInspect();
    
    public static class _constructorsInspect implements _inspect<List<_constructor>>{

        public static _constructor sameNameAndParameterTypes(_constructor ct, Set<_constructor> lcs ){
            _parameters _pts = ct.getParameters();
            _typeRef[] _trs = new _typeRef[_pts.count()];
            for(int i=0;i<_pts.count();i++){
                _trs[i] = _pts.get(i).getType();
            }
//            _pts.forEach( p -> _trs[i]  );
                    
            Optional<_constructor> oc = 
                lcs.stream().filter(
                    c -> c.getName().equals(ct) && c.getParameters().hasParametersOfType(_trs)).findFirst();
            if( oc.isPresent() ){
                return oc.get();
            }
            return null;
        }
        
        @Override
        public boolean equivalent(List<_constructor> left, List<_constructor> right) {
            Set<_constructor> ls = new HashSet<>();
            Set<_constructor> rs = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            return Objects.equals( ls, rs );
        }

        @Override
        public DiffList diff(String path, DiffList dl, List<_constructor> left, List<_constructor> right) {
            Set<_constructor> ls = new HashSet<>();
            Set<_constructor> rs = new HashSet<>();
            Set<_constructor> both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            
            both.addAll(left);
            both.retainAll(right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            ls.forEach(c -> {
                _constructor _rct = sameNameAndParameterTypes(c, rs); 
                if( _rct != null ){
                    rs.remove(_rct);
                    dl.add(path+_java.Component.CONSTRUCTOR, c, _rct);
                } else{
                    dl.add(path+_java.Component.CONSTRUCTOR, c, null);
                }
            });
            rs.forEach(c -> {
                dl.add(path+_java.Component.CONSTRUCTOR, c, null);                
            });
            return dl;
        }        
    }
    
    public static final _constructorInspect INSPECT_CONSTRUCTOR = 
            new _constructorInspect();
    
    public static class _constructorInspect implements _inspect<_constructor>{

        @Override
        public boolean equivalent(_constructor left, _constructor right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _constructor left, _constructor right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.CONSTRUCTOR, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.CONSTRUCTOR, left, null);
                return dl;
            }        
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());
            INSPECT_ANNOS.diff( path, dl, left.getAnnos(), right.getAnnos());
            INSPECT_MODIFIERS.diff(path, dl, left.getModifiers(), right.getModifiers());            
            INSPECT_NAME.diff( path, dl, left.getName(), right.getName());            
            INSPECT_RECEIVER_PARAMETER.diff(path, dl, left.getReceiverParameter(), right.getReceiverParameter());
            INSPECT_PARAMETERS.diff(path, dl, left.getParameters(), right.getParameters());
            INSPECT_TYPE_PARAMETERS.diff(path, dl, left.getTypeParameters(), right.getTypeParameters());
            INSPECT_THROWS.diff( path, dl, left.getThrows(), right.getThrows());            
            INSPECT_BODY.diff(path, dl, left.getBody(), right.getBody());            
            return dl;
        }        
    } 
    
    
    public static final _methodsInspect INSPECT_METHODS = new _methodsInspect();
    
    public static class _methodsInspect implements _inspect<List<_method>>{

        static class _matchedMethods{
            _method left;
            _method right;
            
            public _matchedMethods(_method left, _method right){
                this.left = left;
                this.right = right;
            }
        }
        
        @Override
        public boolean equivalent(List<_method> left, List<_method> right) {
            Set<_method>ls = new HashSet<>();
            ls.addAll(left);
            Set<_method>rs = new HashSet<>();
            rs.addAll(right);
            return Objects.equals(ls,rs);            
        }

        @Override
        public DiffList diff(String path, DiffList dl, List<_method> left, List<_method> right) {
            Set<_method>ls = new HashSet<>();
            ls.addAll(left);
            Set<_method>rs = new HashSet<>();
            rs.addAll(right);
            
            Set<_method> both = new HashSet<>();
            both.addAll(left);
            both.retainAll(right);
            
            if( both.size() ==  right.size() ){
                return dl;
            }
            
            List<_matchedMethods> matchedUnequal = new ArrayList<>();
            left.removeAll(both);
            right.removeAll(both);
            
            //int autoMatchSim = (1 << 7) + 1 << 5;
            if( !( left.isEmpty() || right.isEmpty() ) ){ //if either has nothing left, we cant match
                _method[]ll = left.toArray(new _method[0]);
                _method[]rr = right.toArray(new _method[0]);
                
                int maxSim = 0;
                //int[][] sim = new int[ll.length][rr.length];
                List<Integer> allSims = new ArrayList<>();
                for(int i=0; i<ll.length;i++){                    
                    for(int j=0;j<rr.length;j++){
                        int si = INSPECT_METHOD.calcSimularity( ll[i], rr[j] );
                        //sim[i][j] = si;
                        allSims.add(si);
                        if( si > maxSim ){
                            maxSim = si;
                        }
                    }
                }                
                //Collections.sort(allSims);
                System.out.println( allSims );
                //TODO matching routine & remove matches from left and right
            }
            left.forEach(m -> dl.add(path+_java.Component.METHOD, m, null));
            right.forEach(m -> dl.add(path+_java.Component.METHOD, null, m));            
            return dl;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }        
    }
    
    public static final _methodInspect INSPECT_METHOD = new _methodInspect();
    
    public static class _methodInspect implements _inspect<_method>{

        @Override
        public boolean equivalent(_method left, _method right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _method left, _method right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.METHOD, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.METHOD, left, null);
                return dl;
            }
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());
            INSPECT_ANNOS.diff( path, dl, left.getAnnos(), right.getAnnos());
            INSPECT_MODIFIERS.diff(path, dl, left.getModifiers(), right.getModifiers());
            INSPECT_TYPE_REF.diff(path, dl, left.getType(), right.getType());
            INSPECT_NAME.diff( path, dl, left.getName(), right.getName());            
            INSPECT_RECEIVER_PARAMETER.diff(path, dl, left.getReceiverParameter(), right.getReceiverParameter());
            INSPECT_PARAMETERS.diff(path, dl, left.getParameters(), right.getParameters());
            INSPECT_TYPE_PARAMETERS.diff(path, dl, left.getTypeParameters(), right.getTypeParameters());
            INSPECT_THROWS.diff( path, dl, left.getThrows(), right.getThrows());            
            INSPECT_BODY.diff(path, dl, left.getBody(), right.getBody());            
            return dl;
        }
        
        /**
         * Calculates the similarity among (2) instances 
         * @param left
         * @param right
         * @return 
         */
        public int calcSimularity(_method left, _method right){
            int simularity = 0;
            if( INSPECT_BODY.equivalent(left.getBody(), right.getBody()) ){
                simularity += (1 << 9);
            }
            if( INSPECT_NAME.equivalent(left.getName(), right.getName()) ){
                simularity += (1 << 8);
            }
            if( INSPECT_TYPE_REF.equivalent(left.getType(), right.getType()) ){
                simularity += (1 << 7);
            }
            if( INSPECT_PARAMETERS.equivalent(left.getParameters(), right.getParameters()) ){
                simularity += (1 << 6);
            }
            if( INSPECT_MODIFIERS.equivalent(left.getModifiers(), right.getModifiers()) ){
                simularity += (1 << 5);
            }
            if( INSPECT_ANNOS.equivalent(left.getAnnos(), right.getAnnos()) ){
                simularity += (1 << 4);
            }
            if( INSPECT_THROWS.equivalent(left.getThrows(), right.getThrows()) ){
                simularity += (1 << 3);
            }
            if( INSPECT_TYPE_PARAMETERS.equivalent(left.getTypeParameters(), right.getTypeParameters()) ){
                simularity += (1 << 2);
            }
            if( INSPECT_RECEIVER_PARAMETER.equivalent(left.getReceiverParameter(), right.getReceiverParameter()) ){
                simularity += (1 << 1);
            }
            if( INSPECT_JAVADOC.equivalent(left.getJavadoc(), right.getJavadoc() ) ){
                simularity += (1);
            }
            return simularity;
        }
    }
    
    public static final _bodyInspect INSPECT_BODY =
        new _bodyInspect();

    public static class _bodyInspect implements _inspect<_body>{

        private static final diff_match_patch plainTextDiff = new diff_match_patch();
        
        @Override
        public boolean equivalent(_body left, _body right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _body left, _body right) {
            if(!left.isPresent() ){
                if( ! right.isPresent() ){
                    return dl;
                }
                else{
                    dl.add(path+_java.Component.BODY.getName(), left, right);
                    return dl;
                }
            }
            if( !right.isPresent()) {
                dl.add(path+_java.Component.BODY.getName(), left, right);
                return dl;
            }
            String leftSer = left.toString(Ast.PRINT_NO_COMMENTS);
            String rightSer = right.toString(Ast.PRINT_NO_COMMENTS);
            if( !Objects.equals( leftSer, rightSer )){
                //ok. we know at least one diff (other than comments) are in the text
                // lets diff the originals WITH comments
                LinkedList<Diff> diffs = plainTextDiff.diff_main(left.toString(), right.toString());
                _textDiff td = new _textDiff(diffs);
                // NOTE: we treat code DIFFERENTLY than other objects that are diffedbecause 
                // diffs in code can cross object boundaries and it's not as "clean"
                // as simply having members with properties because of the nature of code
                // instead we have a _textDiff which encapsulates the apparent changes
                // the text has to undergo to get from LEFT, to RIGHT
                
                dl.add(path+_java.Component.BODY.getName(), td, td);                
            }            
            return dl;
        }        
    }   
    
    public static final _parametersInspect INSPECT_PARAMETERS = 
        new _parametersInspect();

    public static class _parametersInspect 
            implements _inspect<_parameters>{

        @Override
        public boolean equivalent(_parameters left, _parameters right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _parameters left, _parameters right) {
            for(int i=0;i<left.count();i++){
                _parameter _l = left.get(i);
                _parameter _r = null;
                if( i < right.count() ){
                    _r = right.get(i);
                }
                if( !Objects.equals(left, right) ){
                    dl.add(path+_java.Component.PARAMETER+"["+i+"]", _l, _r);
                }                
            }
            if( right.count() > left.count() ){
                for(int i=left.count(); i<right.count(); i++){
                    _parameter _r = right.get(i);
                    dl.add(path+_java.Component.PARAMETER+"["+i+"]", null, _r);
                }
            }
            return dl;
        }        
    }   
    
    public static final _modifiersInspect INSPECT_MODIFIERS = 
        new _modifiersInspect();
    
    public static class _modifiersInspect implements _inspect<_modifiers>{
       
        @Override
        public boolean equivalent(_modifiers left, _modifiers right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _modifiers left, _modifiers right) {
            if( !equivalent( left, right)){
                dl.add(path+_java.Component.MODIFIERS.getName(), left, right);
            }
            return dl;
        }        
    }
    
    public static final _javadocInspect INSPECT_JAVADOC = 
        new _javadocInspect();
    
    public static class _javadocInspect implements _inspect<_javadoc>{
       
        @Override
        public boolean equivalent(_javadoc left, _javadoc right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _javadoc left, _javadoc right) {
            if( !equivalent( left, right)){
                dl.add(path+_java.Component.JAVADOC.getName(), left, right);
            }
            return dl;
        }        
    }
    
    public static final ReceiverParameterInspect INSPECT_RECEIVER_PARAMETER = 
        new ReceiverParameterInspect();
    
    public static class ReceiverParameterInspect
        implements _inspect<_receiverParameter> {

        public static final String name = _java.Component.RECEIVER_PARAMETER.getName();
        
        @Override
        public boolean equivalent(_receiverParameter left, _receiverParameter right) {
            return Objects.equals( left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _receiverParameter left, _receiverParameter right) {
            if( !equivalent( left, right )){
                if(left == null){
                    dl.add(path+name, null, right);
                    return dl;
                }
                if(right == null){
                    dl.add(path+name, left, null);
                    return dl;
                }                
                INSPECT_NAME.diff(path+name+".", dl, left.getName(), right.getName());
                INSPECT_TYPE_REF.diff(path+name+".", dl, left.getType(), right.getType());
                INSPECT_ANNOS.diff(path+name+".", left.getAnnos(), right.getAnnos());
            }
            return dl;
        }        
    }
    
    
    public static final AnnosInspect INSPECT_ANNOS = 
            new AnnosInspect();
    
    public static class AnnosInspect
        implements _inspect<_anno._annos> {

        String name = _java.Component.ANNOS.getName();
        
        public AnnosInspect(){ 
        }
        
        @Override
        public boolean equivalent(_anno._annos left, _anno._annos right) {            
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _anno._annos left, _anno._annos right) {
            NodeList<AnnotationExpr> laes = left.astAnnNode.getAnnotations();
            NodeList<AnnotationExpr> raes = right.astAnnNode.getAnnotations();
            
            for( int i = 0; i < laes.size(); i++ ) {
                AnnotationExpr e = (AnnotationExpr)laes.get( i );
                //find a matching annotation in other, if one isnt found, then not equal
                if( !raes.stream().filter( a -> Ast.annotationEqual( e, (AnnotationExpr)a ) ).findFirst().isPresent() ) {
                    dl.add(path+name, e, null);
                }
            }
            
            for( int i = 0; i < raes.size(); i++ ) {
                AnnotationExpr e = (AnnotationExpr)raes.get( i );
                //find a matching annotation in other, if one isnt found, then not equal
                if( !laes.stream().filter( a -> Ast.annotationEqual( e, (AnnotationExpr)a ) ).findFirst().isPresent() ) {
                    dl.add(path+name, null, e);
                }
            }            
            return dl;
        }        
    }
    
    public static final TypeRefInspect INSPECT_TYPE_REF = 
        new TypeRefInspect();
    
    public static class TypeRefInspect
        implements _inspect<_typeRef> {

        String name = _java.Component.TYPE.getName();
        
        public TypeRefInspect(){ 
        }
        
        @Override
        public boolean equivalent(_typeRef left, _typeRef right) {            
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _typeRef left, _typeRef right) {
            if( !equivalent(left, right) ){
                dl.add(path+name, left, right);
            }            
            return dl;
        }        
    }
    
    
    public static final TypeParametersInspect INSPECT_TYPE_PARAMETERS = 
        new TypeParametersInspect();
    
    public static class TypeParametersInspect
        implements _inspect<_typeParameters> {

        String name = _java.Component.TYPE_PARAMETERS.getName();
        
        public TypeParametersInspect(){ 
        }
        @Override
        public boolean equivalent(_typeParameters left, _typeParameters right) {            
            return Objects.equals( left, right );
        }

        @Override
        public DiffList diff(String path, DiffList dl, _typeParameters left, _typeParameters right) {
            return diff(path, dl, left.ast(), right.ast());
        }
        
        public boolean equivalent(NodeList<TypeParameter>left, NodeList<TypeParameter> right) {            
            return Ast.typesEqual(left, right);
        }

        public DiffList diff(String path, DiffList dl, NodeList<TypeParameter> left, NodeList<TypeParameter> right) {
            
            List<ObjectDiff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.size();i++){
                Type cit = left.get(i);
                
                if( ! right.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, cit, null) );
                }
            }
            for(int i=0; i<right.size();i++){
                Type cit = right.get(i);
                if( ! left.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, null, cit) );
                }
            }
            return dl.addList(des);
        }        
    }
    
    public static final ThrowsInspect INSPECT_THROWS = 
            new ThrowsInspect();
    
    public static class ThrowsInspect
        implements _inspect<_throws> {

        String name = _java.Component.THROWS.getName();
        
        public ThrowsInspect(){ 
        }
        
        @Override
        public boolean equivalent(_throws left, _throws right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, _throws left, _throws right) {
            
            List<ObjectDiff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.count();i++){
                ReferenceType cit = left.get(i);
                if( ! right.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, cit, null) );
                }
            }
            for(int i=0; i<right.count();i++){
                ReferenceType cit = right.get(i);
                if( ! left.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, null, cit) );
                }
            }
            return dl.addList(des);
        }        

        public boolean equivalent(NodeList<ReferenceType>left, NodeList<ReferenceType> right) {            
            return Ast.typesEqual(left, right);
        }

        public DiffList diff(String path, DiffList dl, NodeList<ReferenceType> left, NodeList<ReferenceType> right) {
            
            List<ObjectDiff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.size();i++){
                Type cit = left.get(i);
                if( ! right.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, cit, null) );
                }
            }
            for(int i=0; i<right.size();i++){
                Type cit = right.get(i);
                if( ! left.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, null, cit) );
                }
            }
            return dl.addList(des);
        }        
    }
    
    public static final ArgsInspect INSPECT_ARGUMENTS = 
            new ArgsInspect();
    
    public static class ArgsInspect implements _inspect<List<Expression>>{

        @Override
        public boolean equivalent(List<Expression> left, List<Expression> right) {
            return Objects.equals(left, right);
        }

        @Override
        public DiffList diff(String path, DiffList dl, List<Expression> left, List<Expression> right) {
            if(left == null ){
                if(right == null){
                    return dl;
                }
                for(int i=0;i<right.size();i++){
                    dl.add(path+_java.Component.ARGUMENT+"["+i+"]", null, right);
                }
                return dl;
            }
            if( right == null ){
                 for(int i=0;i<left.size();i++){
                    dl.add(path+_java.Component.ARGUMENT+"["+i+"]", left, null);
                }
                return dl;
            }            
            return dl;
        }
        
    }
    public static final ExpressionInspect INSPECT_DEFAULT = new ExpressionInspect(_java.Component.DEFAULT.getName());
    
    public static final ExpressionInspect INSPECT_INIT = new ExpressionInspect(_java.Component.INIT.getName());
    
    public static class ExpressionInspect 
            implements _inspect<Expression>{
        
        public String name;
        
        public ExpressionInspect( String name ){
            this.name = name;
        }
        
        @Override
        public boolean equivalent( Expression left, Expression right){
            return Objects.equals(left, right);
        }
        
        @Override
        public DiffList diff( String path, DiffList dl, Expression left, Expression right ){
            if( !equivalent(left, right) ){                
                return dl.add(path+name, left, right);
            }
            return dl;
        }        
    }
    
    public static final StringInspect INSPECT_PACKAGE_NAME = new StringInspect(_java.Component.PACKAGE_NAME.getName());
    
    public static final StringInspect INSPECT_NAME = new StringInspect(_java.Component.NAME.getName());
    
    public static class StringInspect 
            implements _inspect<String>{
        
        public String name;
        
        public StringInspect( String name ){
            this.name = name;
        }
        
        @Override
        public boolean equivalent( String left, String right){
            return Objects.equals(left, right);
        }
        
        @Override
        public DiffList diff( String path, DiffList dl, String left, String right ){
            if( !equivalent(left, right) ){                
                return dl.add(path+name, left, right);
            }
            return dl;
        }
    }
    
    
    /**
     * This represents a Textual Diff, it wraps/hides the 
     * underlying _diff_match_patch nuances and tried to make it
     * 
     * NOTE: we use the Concept LEFT and RIGHT to represent the two separate
     * pieces of text, rather than ASSUME the left is a previous and the right
     * is the current with a Patch Applied.
     * 
     */
    public static class _textDiff{
        
        /* the diff_match_patch derived diffs */
        public final LinkedList<Diff>diffs;
        
        public _textDiff(LinkedList<Diff> diffs ){
            this.diffs = diffs;
        }
        
        /**
         * Return the ENTIRE LEFT document as a String
         * @return 
         */
        public String left(){
            StringBuilder sb = new StringBuilder();
            diffs.forEach(s -> {
                if(s.operation != diff_match_patch.Operation.INSERT){
                    sb.append(s.text);
                }
            });
            return sb.toString();
        }
        
        /** 
         * return the entire RIGHT document as a String
         * 
         * @return the right document
         */
        public String right(){
            StringBuilder sb = new StringBuilder();
            diffs.forEach(s -> {
                if(s.operation != diff_match_patch.Operation.DELETE){
                    sb.append(s.text);
                }
            });
            return sb.toString();
        }
    }
}
