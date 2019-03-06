/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java;

import draft.java._differ._add;
import draft.java._differ._change;
import draft.java._differ._delta;
import draft.java._differ._editNode;
import draft.java._differ._remove;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Eric
 */
public interface _dif {

    /**
     * @return a list of all diff nodes
     */
    List<_delta> list();

    /**
     * @return number of diffs
     */
    default int size() {
        return list().size();
    }

    default boolean isEmpty() {
        return list().isEmpty();
    }

    /**
     * List all paths that have diffs
     *
     * @return
     */
    default List<_inspect._path> paths() {
        return list().stream().map(d -> d.path()).collect(Collectors.toList());
    }

    /**
     * Return the _diffNode at the path, (or null if no diff exists at this
     * path)
     *
     * @param _p the underlying _path
     * @return the _diffNode at this path, or null if not found
     */
    default _delta at(_inspect._path _p) {
        return first(d -> d.path().equals(_p));
    }

    /**
     * returns the DiffNode that can be found at this (Full) path
     *
     * @param pathAsTokens the tokens that make up the path
     * @return the _diffNode found at this path or null if not found
     */
    default _delta atPath(Object... pathAsTokens) {
        return at(_inspect._path.of(pathAsTokens));
    }

    /**
     * IS there a Diff Node at the specified Component type?
     *
     * @param component the component type
     * @return true if there is a diff node at this type
     */
    default boolean isAt(_java.Component component) {
        return first(d -> d.at(component)) != null;
    }

    /**
     * Is there a Diff Node at the specified Component type
     *
     * @param component the component type
     * @param id the identifier for the node
     * @return true if a Diff was found at this Node, false otherwise
     */
    default boolean isAt(_java.Component component, String id) {
        return first(d -> d.at(component) && d.at(id)) != null;
    }

    /**
     * For each of the matching DiffNodes in the Tree, perform some action
     *
     * @param _nodeMatchFn function for matching specific _diffNodes
     * @param _nodeActionFn consumer action function on selected _diffNodes
     * @return the potentially modified) _diffTree
     */
    default _dif forEach(Predicate<_delta> _nodeMatchFn, Consumer<_delta> _nodeActionFn) {
        list(_nodeMatchFn).forEach(_nodeActionFn);
        return this;
    }

    /**
     * For each diff _node use run this _nodeActionFn
     *
     * @param _nodeActionFn action to take on each node
     * @return the potentially modified _diff
     */
    default _dif forEach(Consumer<_delta> _nodeActionFn) {
        list().forEach(_nodeActionFn);
        return this;
    }

    /**
     * For all diffs that are ADD (meaning they are ABSENT in LEFT and PRESENT
     * in RIGHT) check if they match the _addNodeMatchFn and, if so, execute the
     * _addNodeActionFn
     *
     * @param _addNodeMatchFn matching function for Add nodes
     * @param _addNodeActionFn the action for adds that pass the matchFn
     * @return the potentially modified _diff
     */
    default _dif forAdds(Predicate<_add> _addNodeMatchFn, Consumer<_add> _addNodeActionFn) {
        List<_delta> ns = list(d -> d instanceof _add && _addNodeMatchFn.test((_add) d));
        List<_add> ans = new ArrayList<>();
        ns.forEach(n -> ans.add((_add) n));
        ans.forEach(_addNodeActionFn);
        return this;
    }

    /**
     * For all diff _nodes that are ADD (meaning they are ABSENT in LEFT and
     * PRESENT in RIGHT) execute the _addNodeActionFn
     *
     * @param _addNodeActionFn the action for adds that pass the matchFn
     * @return the potentially modified _diff
     */
    default _dif forAdds(Consumer<_add> _addNodeActionFn) {
        List<_delta> ns = list(d -> d instanceof _add);
        List<_add> ans = new ArrayList<>();
        ns.forEach(n -> ans.add((_add) n));
        ans.forEach(_addNodeActionFn);
        return this;
    }

    /**
     * For all diffs that are REMOVE (meaning they are PRESENT in LEFT and
     * ABSENT in RIGHT) check if they match the _removeNodeMatchFn and, if so,
     * execute the _removeNodeActionFn
     *
     * @param _removeNodeMatchFn matching function for Remove nodes
     * @param _removeNodeActionFn the action for removes that pass the matchFn
     * @return the potentially modified _diff
     */
    default _dif forRemoves(Predicate<_remove> _removeNodeMatchFn, Consumer<_remove> _removeNodeActionFn) {
        List<_delta> ns = list(d -> d instanceof _remove && _removeNodeMatchFn.test((_remove) d));
        List<_remove> ans = new ArrayList<>();
        ns.forEach(n -> ans.add((_remove) n));
        ans.forEach(_removeNodeActionFn);
        return this;
    }

    /**
     * For all diffs that are REMOVE (meaning they are PRESENT in LEFT and
     * ABSENT in RIGHT) execute the _removeNodeActionFn
     *
     * @param _removeNodeActionFn the action for removes that pass the matchFn
     * @return the potentially modified _diff
     */
    default _dif forRemoves(Consumer<_remove> _removeNodeActionFn) {
        List<_delta> ns = list(d -> d instanceof _remove);
        List<_remove> ans = new ArrayList<>();
        ns.forEach(n -> ans.add((_remove) n));
        ans.forEach(_removeNodeActionFn);
        return this;
    }

    /**
     * For all diffs that are CHANGE (meaning they are PRESENT in LEFT & RIGHT
     * but different) check if they match the _changeNodeMatchFn and, if so,
     * execute the _changeNodeActionFn
     *
     * @param _changeNodeMatchFn matching function for change nodes
     * @param _changeNodeActionFn the action for changes that pass the matchFn
     * @return the potentially modified _diff
     */
    default _dif forChanges(Predicate<_change> _changeNodeMatchFn, Consumer<_change> _changeNodeActionFn) {
        List<_delta> ns = list(d -> d instanceof _change && _changeNodeMatchFn.test((_change) d));
        List<_change> ans = new ArrayList<>();
        ns.forEach(n -> ans.add((_change) n));
        ans.forEach(_changeNodeActionFn);
        return this;
    }

    /**
     * For all diffs that are CHANGE (meaning they are PRESENT in LEFT & RIGHT
     * but different) check if they match the _changeNodeMatchFn and, if so,
     * execute the _changeNodeActionFn
     *
     * @param _changeNodeActionFn the action for changes that pass the matchFn
     * @return the potentially modified _diff
     */
    default _dif forChanges(Consumer<_change> _changeNodeActionFn) {
        List<_delta> ns = list(d -> d instanceof _change);
        List<_change> ans = new ArrayList<>();
        ns.forEach(n -> ans.add((_change) n));
        ans.forEach(_changeNodeActionFn);
        return this;
    }

    /**
     * For all diffs that are EDITs (meaning they are PRESENT in LEFT & RIGHT
     * but different on a textual level... this means the underlying code is put
     * through a diff) execute the _editNodeActionFn
     *
     * @param _editNodeActionFn the action for edits that pass the matchFn
     * @return the potentially modified _diff
     */
    default _dif forEdits(Consumer<_editNode> _editNodeActionFn) {
        List<_delta> ns = list(d -> d instanceof _editNode);
        List<_editNode> ans = new ArrayList<>();
        ns.forEach(n -> ans.add((_editNode) n));
        ans.forEach(_editNodeActionFn);
        return this;
    }

    /**
     * For all diffs that are EDITs (meaning they are PRESENT in LEFT & RIGHT
     * but different on a textual level... this means the underlying code is put
     * through a diff) check if they match the _editNodeMatchFn and, if so,
     * execute the _editNodeActionFn
     *
     * @param _editNodeMatchFn matching function for edit nodes
     * @param _editNodeActionFn the action for edits that pass the matchFn
     * @return the potentially modified _diff
     */
    default _dif forEdits(Predicate<_editNode> _editNodeMatchFn, Consumer<_editNode> _editNodeActionFn) {
        List<_delta> ns = list(d -> d instanceof _editNode && _editNodeMatchFn.test((_editNode) d));
        List<_editNode> ans = new ArrayList<>();
        ns.forEach(n -> ans.add((_editNode) n));
        ans.forEach(_editNodeActionFn);
        return this;
    }

    /**
     * List all add nodes that comply with the _addNodeActionFn
     *
     * @param _addNodeMatchFn match function for selecting diff nodes
     * @return a list of _addNodes
     */
    default List<_add> listAdds(Predicate<_add> _addNodeMatchFn) {
        List<_add> ens = new ArrayList<>();
        list(d -> d instanceof _add && _addNodeMatchFn.test((_add) d))
                .forEach(e -> ens.add((_add) e));
        return ens;
    }

    /**
     * Lists the diffs that are add Nodes (not found in the left, but found in
     * the right)
     *
     * @see _addNode
     * @return a list of _addNode that are FOUND on the RIGHT and NOT FOUND on
     * the LEFT
     */
    default List<_add> listAdds() {
        List<_add> tds = new ArrayList<>();
        list(d -> d instanceof _add).forEach(n -> tds.add((_add) n));
        return tds;
    }

    /**
     * List all remove nodes that comply with the _removeNodeActionFn
     *
     * @param _removeNodeMatchFn match function for selecting _removeNode s
     * @return a list of _removeNodes that match the _removeNodeMatchFn
     */
    default List<_remove> listRemoves(Predicate<_remove> _removeNodeMatchFn) {
        List<_remove> ens = new ArrayList<>();
        list(d -> d instanceof _remove && _removeNodeMatchFn.test((_remove) d))
                .forEach(e -> ens.add((_remove) e));
        return ens;
    }

    /**
     * Lists the diffs that are _removeNodes (Found on the left, but NOT FOUND
     * on the right)
     *
     * @see _removeNode
     * @return a list of _removeNode that have removes (Found on left, not on
     * right)
     */
    default List<_remove> listRemoves() {
        List<_remove> tds = new ArrayList<>();
        list(d -> d instanceof _remove).forEach(n -> tds.add((_remove) n));
        return tds;
    }

    /**
     * List all change nodes that comply with the _changeNodeActionFn
     *
     * @param _changeNodeMatchFn match function for selecting _changeNode s
     * @return a list of _changeNodes that match the _changeNodeMatchFn
     */
    default List<_change> listChanges(Predicate<_change> _changeNodeMatchFn) {
        List<_change> ens = new ArrayList<>();
        list(d -> d instanceof _change && _changeNodeMatchFn.test((_change) d))
                .forEach(e -> ens.add((_change) e));
        return ens;
    }

    /**
     * Lists the diffs that are _changeNodes Objects matched (API-wise) on both
     * LEFT and RIGHT but changed (i.e. Modifiers, Annotations, etc.)
     *
     * @see _changeNode
     * @return a list of _changeNode that have changes (Found on left & right
     * but different)
     */
    default List<_change> listChanges() {
        List<_change> tds = new ArrayList<>();
        list(d -> d instanceof _change).forEach(n -> tds.add((_change) n));
        return tds;
    }

    /**
     * List all edit nodes that comply with the _editNodeActionFn
     *
     * @param _editNodeMatchFn match function for selecting diff nodes
     * @return a list of _editNodes
     */
    default List<_editNode> listEdits(Predicate<_editNode> _editNodeMatchFn) {
        List<_editNode> ens = new ArrayList<>();
        list(d -> d instanceof _editNode && _editNodeMatchFn.test((_editNode) d))
                .forEach(e -> ens.add((_editNode) e));
        return ens;
    }

    /**
     * Lists the diffs that are edit "text diffs" (usually diffs for some body
     * of code)
     *
     * these involve the edit distance algorithm (more low level involved for
     * determining edits needed to be applied to the left text to create the
     * right text
     *
     * @see _editNode
     * @return a list of _editNode that have edits (usually in code bodies)
     */
    default List<_editNode> listEdits() {
        List<_editNode> tds = new ArrayList<>();
        list(d -> d instanceof _editNode).forEach(n -> tds.add((_editNode) n));
        return tds;
    }

    /**
     * @return a list of all diff nodes
     *
     * @Override public List<_delta> list(){ return this.diffs; }
     */
    /**
     * Find the first diff that is at (the last component in the path is) the
     * component type
     *
     * @param componet the last component in the path
     * @return the first diff node found at this path or null if none found
     */
    default _delta firstAt(_java.Component componet) {
        Optional<_delta> first
                = list().stream().filter(d -> d.at(componet)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    /**
     * Find the first diff that is at (the last component in the path is) the
     * component type with this id
     *
     * @param component the last component in the path
     * @param id the last id in the path to the component
     * @return the first diff node found at this path or null if none found
     */
    default _delta firstAt(_java.Component component, String id) {
        Optional<_delta> first
                = list().stream().filter(d -> d.at(component, id)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    /**
     * find the first node that matches the predicate or return null
     *
     * @param _nodeMatchFn
     * @return the first node that matches the function or null if none found
     */
    default _delta first(Predicate<_delta> _nodeMatchFn) {
        Optional<_delta> first
                = list().stream().filter(_nodeMatchFn).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    /**
     * List all diff _nodes that have this component as the last component in
     * the path
     *
     * @param id the leaf (last) id in the path (i.e. "0", "m(String)")
     * @return
     */
    default List<_delta> listAt(String id) {
        return list(d -> d.at(id));
    }

    /**
     * List all diff _nodes that have this component as the last component in
     * the path
     *
     * @param component the leaf component to look for (i.e. METHOD, FIELD)
     * @return
     */
    default List<_delta> listAt(_java.Component component) {
        return list(d -> d.at(component));
    }

    /**
     * Lists nodes matching a matchFn
     *
     * @param _nodeMatchFn matches diff nodes for retrieval
     * @return list of diff nodes that match the nodeMatchFn
     */
    default List<_delta> list(Predicate<_delta> _nodeMatchFn) {
        return list().stream().filter(_nodeMatchFn).collect(Collectors.toList());
    }

}
