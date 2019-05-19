package draft.java.proto;

import com.github.javaparser.ast.Node;
import draft.Template;
import draft.Translator;
import draft.java._typeParameter;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * @author Eric
 */
public class $typeParameter 
    implements Template<_typeParameter>, $proto<_typeParameter>, $method.$part, $constructor.$part {

    public $annos as = $annos.of();
    public $id name = $id.of();
    
    
    public static $typeParameter any(){
        return null;
    }
    
    public $typeParameter(_typeParameter _tp){
        as = $annos.of(_tp);
        name = $id.of(_tp.getName() );
        
        
        
        //_tp.getTypeBound()
                
                
                
    }
    
    @Override
    public _typeParameter construct(Translator translator, Map<String, Object> keyValues) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Template<_typeParameter> $(String target, String $Name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> list$() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> list$Normalized() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public _typeParameter firstIn(Node astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <S extends selected> S selectFirstIn(Node n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<_typeParameter> listIn(Node astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<? extends selected> listSelectedIn(Node astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N forEachIn(N astRootNode, Consumer<_typeParameter> _nodeActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
