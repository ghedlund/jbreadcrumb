/*
 * Copyright (C) 2012-2018 Gregory Hedlund
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *    http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.phon.ui.jbreadcrumb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;

import ca.phon.ui.jbreadcrumb.BreadcrumbEvent.BreadcrumbEventType;

/**
 * A Breadcrumb maintains a linear navigation history.
 * 
 * @param <S>  the type of state
 * @param <V>  the type of value associated with a state 
 */
public class Breadcrumb<S, V> extends Stack<S> { 

	private static final long serialVersionUID = 5763249273383818764L;

	/** The values stack */
	private LinkedHashMap<S, V> valueMap = new LinkedHashMap<>();

	/**
	 * Creates a new empty Breadcrumb.
	 */
	public Breadcrumb() {
		super();
	}
	
	/**
	 * Gets whether or not the breadcrumb contains a state.
	 * 
	 * @param state  the state to look for
	 * 
	 * @return <code>true</code> if the breadcrumb contains the given state,
	 *         <code>false</code> otherwise
	 */
	public boolean containsState(S state) {
		return super.contains(state);
	}
	
	/**
	 * Gets the current state of this breadcrumb.
	 * 
	 * @return  the current state, or <code>null</code> if no states available
	 */
	public S getCurrentState() {
		return (isEmpty() ? null : super.peek());
	}

	/**
	 * Gets the value associated with the current state of this breadcrumb.
	 * 
	 * @return  the current state's value, or <code>null</code> if no states available
	 */
	public V getCurrentValue() {
		return (isEmpty() ? null : valueMap.get(getCurrentState()));
	}

	/**
	 * Index of given state
	 * 
	 * @param state  the state to find
	 * @return index of state or -1
	 */
	public int getIndexOfState(S state) {
		return super.indexOf(state);
	}

	/**
	 * Empties this breadcrumb.
	 */
	public void clear() {
		if(!isEmpty()) {
			final S oldState = getCurrentState();
			super.clear();
			valueMap.clear();
			fireStateChanged(oldState, null);
		}
	}

	/**
	 * Gets the state at the specified index from the current state.
	 * 
	 * @param index  the index of the state to peek at
	 * 
	 * @return the state at the given index (from the current state)
	 * 
	 * @throws IndexOutOfBoundsException  if the index is out of range (i.e.,
	 *                                    {@code index < 0 || index > size()})
	 */
	public S peekState(int index) {
		return super.get(index);
	}

	/**
	 * Removes and returns the current state from this breadcrumb.
	 * 
	 * @return the removed state and value as an EntrySet, or null if breadcrumb is empty
	 */
	public EntrySet<S, V> popState() {
		EntrySet<S, V> retVal = null;
		if(!isEmpty()) {
			S state = super.pop();
			V value = valueMap.remove(state);
			
			final S newState = getCurrentState();
			fireStateChanged(state, newState);
			
			retVal = new EntrySet<>(state, value);
		}
		return retVal;
	}

	/**
	 * Goes to the given state. If the state is part of this breadcrumb, all
	 * states following it will be removed.
	 * 
	 * @param state  the state to go to
	 */
	public void gotoState(S state) {
		S oldState = getCurrentState();
		
		if(!containsState(state)) return;
		while(!isEmpty() && getCurrentState() != state) valueMap.remove(super.pop());
		
		fireStateChanged(oldState, state);
	}

	/**
	 * Sets the complete state of the breadcrumb to a given list of
	 * state/value pairs.
	 * 
	 * @param states  the new set of states
	 */
	public void set(List<EntrySet<S, V>> states) {
		final S oldState = getCurrentState();

		clear();

		for(EntrySet<S, V> state : states) {
			super.add(state.getState());
			valueMap.put(state.getState(), state.getValue());
			fireStateAdded(state.getState(), state.getValue());
		}

		fireStateChanged(oldState, getCurrentState());
	}

	/**
	 * Append the given state to this breadcrumb with a <code>null</code> value.
	 * 
	 * @param state  the state
	 */
	public void addState(final S state) {
		addState(state, null);
	}

	/**
	 * Append the given state/value pair to this breadcrumb.
	 * 
	 * @param state  the state
	 * @param value  the value to associate with the given state
	 */
	public void addState(S state, V value) {
		final S oldState = getCurrentState();
		super.add(state);
		valueMap.put(state, value);
		fireStateAdded(state, value);
		fireStateChanged(oldState, state);
	}

	/**
	 * Gets the set of states as an immutable list.
	 * 
	 * @return the list of states
	 */
	public List<S> getStates() {
		return Collections.unmodifiableList(this);
	}

	/**
	 * Gets the set of values as an immutable list.
	 * 
	 * @return the list of values
	 */
	public Collection<V> getValues() {
		return Collections.unmodifiableCollection(valueMap.values());
	}
	
	/**
	 * Get value for given state.
	 * 
	 * @param state  the state to get the value for
	 * @return the value associated with the state
	 */
	public V getValue(S state) {
		return valueMap.get(state);
	}
	
	/**
	 * Set value for given state.  The state must already exist.
	 * 
	 * @param state  the state to set the value for
	 * @param value  the value to set
	 */
	public void setValue(S state, V value) {
		if(containsState(state))
			valueMap.put(state, value);
	}

	//
	// Listeners
	//

	/**
	 * List of breadcrumb listeners
	 */
	private ArrayList<BreadcrumbListener<S, V>> listeners = new ArrayList<BreadcrumbListener<S, V>>();

	/**
	 * Adds a breadcrumb listener to this breadcrumb.
	 * 
	 * @param listener  the listener to add
	 */
	public void addBreadcrumbListener(BreadcrumbListener<S, V> listener) {
		synchronized(listeners) {
			if(listener != null && !listeners.contains(listener))
				listeners.add(listener);
		}
	}

	/**
	 * Removes a breadcrumb listener from this breadcrumb.
	 * 
	 * @param listener  the listener to remove
	 */
	public void removeBreadcrumbListener(BreadcrumbListener<S, V> listener) {
		synchronized(listeners) {
			listeners.remove(listener);
		}
	}

	/**
	 * Fires a state changed event to all registered listeners.
	 * 
	 * @param oldState  the previous state
	 * @param newState  the new current state
	 */
	protected void fireStateChanged(S oldState, S newState) {
		synchronized(listeners) {
			int stateIdx = getIndexOfState(newState);
			if(stateIdx >= 0) {
				final BreadcrumbEvent<S, V> evt = 
						new BreadcrumbEvent<>(this, newState, valueMap.get(get(stateIdx)), 
								stateIdx, oldState, valueMap.get(oldState), BreadcrumbEventType.GOTO_STATE);
				for(BreadcrumbListener<S, V> listener : listeners)
					listener.breadCrumbEvent(evt);
			}
		}
	}

	/**
	 * Fires a state added event to all registered listeners.
	 * 
	 * @param state  the state that was added
	 * @param value  the value associated with the state
	 */
	protected void fireStateAdded(S state, V value) {
		synchronized(listeners) {
			int index = getIndexOfState(state);
			if(index >= 0) {
				final BreadcrumbEvent<S, V> evt = 
						new BreadcrumbEvent<>(this, state, value, index, BreadcrumbEventType.STATE_ADDED);
				for(BreadcrumbListener<S, V> listener : listeners)
					listener.breadCrumbEvent(evt);
			}
		}
	}
	
	/**
	 * Container class for a state/value pair in a {@link Breadcrumb}.
	 * 
	 * @param <S>  the type of state
	 * @param <V>  the type of value
	 */
	public static class EntrySet<S, V> {
		
		private S state;
		
		private V value;
		
		/**
		 * Creates a new EntrySet with the given state and value.
		 * 
		 * @param state  the state
		 * @param value  the value
		 */
		public EntrySet(S state, V  value) {
			super();
			this.state = state;
			this.value = value;
		}

		/**
		 * Gets the state.
		 * 
		 * @return the state
		 */
		public S getState() {
			return state;
		}

		/**
		 * Sets the state.
		 * 
		 * @param state  the state to set
		 */
		public void setState(S state) {
			this.state = state;
		}

		/**
		 * Gets the value.
		 * 
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value.
		 * 
		 * @param value  the value to set
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
	}
	
}
