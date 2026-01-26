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

/**
 * State changes for a {@link Breadcrumb}
 * 
 * @param <S>  the type of state
 * @param <V>  the type of value
 */
public class BreadcrumbEvent<S, V> {
	
	/**
	 * The type of breadcrumb event.
	 */
	public static enum BreadcrumbEventType {
		/** A new state was added to the breadcrumb */
		STATE_ADDED,
		/** The breadcrumb navigated to an existing state */
		GOTO_STATE
	};

	private Breadcrumb<S, V> breadcrumb;
	
	private S state;
	
	/**
	 * Used for GOTO_STATE events, <code>null</code> otherwise
	 */
	private S oldState;
	
	private V value;
	
	/**
	 * Used for GOTO_STATE events, <code>null</code> otherwise
	 */
	private V oldValue; 

	private int stateIndex;
	
	private BreadcrumbEventType eventType = BreadcrumbEventType.GOTO_STATE;
	
	/**
	 * Creates a new empty BreadcrumbEvent.
	 */
	public BreadcrumbEvent() {
		super();
	}
	
	/**
	 * Creates a new BreadcrumbEvent.
	 * 
	 * @param breadcrumb  the breadcrumb that fired the event
	 * @param state  the current state
	 * @param value  the current value
	 * @param stateIndex  the index of the current state
	 * @param eventType  the type of event
	 */
	public BreadcrumbEvent(Breadcrumb<S, V> breadcrumb, S state, V value, int stateIndex,
			BreadcrumbEventType eventType) {
		this(breadcrumb, state, value, stateIndex, null, null, eventType);
	}

	/**
	 * Creates a new BreadcrumbEvent.
	 * 
	 * @param breadcrumb  the breadcrumb that fired the event
	 * @param state  the current state
	 * @param value  the current value
	 * @param stateIndex  the index of the current state
	 * @param oldState  the previous state (for GOTO_STATE events)
	 * @param oldValue  the previous value (for GOTO_STATE events)
	 * @param eventType  the type of event
	 */
	public BreadcrumbEvent(Breadcrumb<S, V> breadcrumb, S state, V value, int stateIndex, S oldState, V oldValue, 
			BreadcrumbEventType eventType) {
		super();
		this.breadcrumb = breadcrumb;
		this.state = state;
		this.value = value;
		this.stateIndex = stateIndex;
		this.oldState = oldState;
		this.oldValue = oldValue;
		this.eventType = eventType;
	}

	/**
	 * Gets the breadcrumb that fired this event.
	 * 
	 * @return the breadcrumb
	 */
	public Breadcrumb<S, V> getBreadcrumb() {
		return breadcrumb;
	}

	/**
	 * Sets the breadcrumb that fired this event.
	 * 
	 * @param breadcrumb  the breadcrumb to set
	 */
	public void setBreadcrumb(Breadcrumb<S, V> breadcrumb) {
		this.breadcrumb = breadcrumb;
	}

	/**
	 * Gets the current state.
	 * 
	 * @return the state
	 */
	public S getState() {
		return state;
	}

	/**
	 * Sets the current state.
	 * 
	 * @param state  the state to set
	 */
	public void setState(S state) {
		this.state = state;
	}

	/**
	 * Gets the current value.
	 * 
	 * @return the value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * Sets the current value.
	 * 
	 * @param value  the value to set
	 */
	public void setValue(V value) {
		this.value = value;
	}

	/**
	 * Gets the index of the current state.
	 * 
	 * @return the state index
	 */
	public int getStateIndex() {
		return stateIndex;
	}

	/**
	 * Sets the index of the current state.
	 * 
	 * @param stateIndex  the state index to set
	 */
	public void setStateIndex(int stateIndex) {
		this.stateIndex = stateIndex;
	}

	/**
	 * Gets the previous state (for GOTO_STATE events).
	 * 
	 * @return the old state, or null
	 */
	public S getOldState() {
		return oldState;
	}

	/**
	 * Sets the previous state.
	 * 
	 * @param oldState  the old state to set
	 */
	public void setOldState(S oldState) {
		this.oldState = oldState;
	}

	/**
	 * Gets the previous value (for GOTO_STATE events).
	 * 
	 * @return the old value, or null
	 */
	public V getOldValue() {
		return oldValue;
	}

	/**
	 * Sets the previous value.
	 * 
	 * @param oldValue  the old value to set
	 */
	public void setOldValue(V oldValue) {
		this.oldValue = oldValue;
	}

	/**
	 * Gets the type of this event.
	 * 
	 * @return the event type
	 */
	public BreadcrumbEventType getEventType() {
		return eventType;
	}

	/**
	 * Sets the type of this event.
	 * 
	 * @param eventType  the event type to set
	 */
	public void setEventType(BreadcrumbEventType eventType) {
		this.eventType = eventType;
	}
	
}
