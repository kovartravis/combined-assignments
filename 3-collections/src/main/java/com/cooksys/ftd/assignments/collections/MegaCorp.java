package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	
	HashMap<FatCat, Set<Capitalist>> megaList = new HashMap<>();

    /**
     * Adds a given element to the hierarchy.
     * <p>
     * If the given element is already present in the hierarchy,
     * do not add it and return false
     * <p>
     * If the given element has a parent and the parent is not part of the hierarchy,
     * add the parent and then add the given element
     * <p>
     * If the given element has no parent but is a Parent itself,
     * add it to the hierarchy
     * <p>
     * If the given element has no parent and is not a Parent itself,
     * do not add it and return false
     *
     * @param capitalist the element to add to the hierarchy
     * @return true if the element was added successfully, false otherwise
     */
    @Override
    public boolean add(Capitalist capitalist) {
    	Capitalist me = capitalist;
    	Set<Capitalist> myWorkers;
    	Set<Capitalist> myParentsWorkers;
    	
    	if(me == null || !(me instanceof Capitalist) || has(me)) return false;
    	if(me instanceof WageSlave && !me.hasParent()) return false;
    	
    	if(me.hasParent()){
    		add(me.getParent());
    		myParentsWorkers = megaList.get(me.getParent());
    		myParentsWorkers.add(me);
    		megaList.put(me.getParent(), myParentsWorkers);
    	}
    	if(me instanceof FatCat){
    		myWorkers = new HashSet<Capitalist>();
    		megaList.put((FatCat) me, myWorkers);
    	}
    	return true;
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
        if(megaList.isEmpty()) return false;
        if(capitalist == null) return false;
    
        if(capitalist instanceof FatCat){
        	return megaList.containsKey(capitalist);
        }
        if(capitalist instanceof WageSlave){
        	if(capitalist.hasParent()){
        		if(megaList.get(capitalist.getParent())!= null){
        			return megaList.get(capitalist.getParent()).contains(capitalist);
        		}
        	}
        }
        return false;
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() {
        Set<Capitalist> returnSet = new HashSet<Capitalist>();
		Set<FatCat> myKeys = megaList.keySet();
    
        for(Capitalist myKey : myKeys){
        	for(Capitalist myWorker : megaList.get(myKey)){
        		returnSet.add(myWorker);
        	}
        }
        returnSet.addAll(myKeys);
        
        return returnSet;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	Set<FatCat> defensiveSet = new HashSet<FatCat>();
    	defensiveSet.addAll(megaList.keySet());
		return defensiveSet;
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
    	Set<Capitalist> defensiveSet = new HashSet<Capitalist>();
    	if(megaList.containsKey(fatCat)){
        	defensiveSet.addAll(megaList.get(fatCat));	
    	}
        return defensiveSet;
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
        Map<FatCat, Set<Capitalist>> defensiveSet = new HashMap<FatCat, Set<Capitalist>>();
        Set<FatCat> parents = getParents();
        for(FatCat fat : parents){
        	defensiveSet.put(fat, getChildren(fat));
        }
        return defensiveSet;
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {
        List<FatCat> parentChain = new ArrayList<FatCat>();
        if(capitalist == null) return parentChain;
        
        while(capitalist.getParent() != null){
            if(!megaList.containsKey(capitalist.getParent())) return parentChain;
        	parentChain.add(capitalist.getParent());
        	capitalist = capitalist.getParent();
        }
        
        return parentChain;
    }
}
