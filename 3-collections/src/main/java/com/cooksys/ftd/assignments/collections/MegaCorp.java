package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
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
    	FatCat myParent = me.getParent();
		Set<Capitalist> myFellowWorkers = megaList.get(myParent);
		
		if(myFellowWorkers.contains(me)) return false;//if worker is already on list
		
		//if parent is on list but worker is not
		if(has(myParent)){
			myFellowWorkers.add(me);
			megaList.put(myParent, myFellowWorkers);
			return true;
		}
		//if parent is not on list but parent exists
		if(me.hasParent()){
			myFellowWorkers = new HashSet<Capitalist>();
			myFellowWorkers.add(me);
			megaList.put(myParent, myFellowWorkers);
			return true;
		}
		//if has no parent but is Parent
		if(me instanceof FatCat){
			megaList.put((FatCat) me, null);
		}else{//if has no parent and no children
			return false;
		}
		return true;
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
        if(megaList.containsKey(capitalist)) return true;
        else return false;
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
		return megaList.keySet();
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
        return megaList.get(fatCat);
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
        return megaList;
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
        while(capitalist.getParent() != null){
        	parentChain.add(capitalist.getParent());
        	capitalist = capitalist.getParent();
        }
        
        return parentChain;
    }
}
