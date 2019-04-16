/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import com.github.javaparser.ast.Node;
import draft.Template;
import draft.Translator;
import draft.java._anno;
import draft.java._anno._annos;
import draft.java._model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * @author Eric
 */
public class $annos 
    implements Template<_anno>, $proto<_anno> {

    List<$anno> $annosList = new ArrayList<>();
    
    public $annos( String...form ){
        this(_annos.of(form));
    }
    
    public $annos( _annos _anns){
        
    }
    
    @Override
    public _anno construct(Translator translator, Map<String, Object> keyValues) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Template<_anno> $(String target, String $Name) {
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
    public List<_anno> listIn(_model._node _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<_anno> listIn(Node astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<? extends selected> selectListIn(Node astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<? extends selected> selectListIn(_model._node _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node> N removeIn(N _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N forEachIn(N astRootNode, Consumer<_anno> _nodeActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node> N forEachIn(N _n, Consumer<_anno> _nodeActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
