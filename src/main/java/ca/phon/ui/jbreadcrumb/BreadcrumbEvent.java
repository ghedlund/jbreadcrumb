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
 * @param <S>
 * @param <V>
 */
public class BreadcrumbEvent<S, V> {
	
	public static enum BreadcrumbEventType {
		STATE_ADDED,
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
	
	public BreadcrumbEvent() {
		super();
	}
	
	public BreadcrumbEvent(Breadcrumb<S, V> breadcrumb, S state, V value, int stateIndex,
			BreadcrumbEventType eventType) {
		this(breadcrumb, state, value, stateIndex, null, null, eventType);
	}

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

	public Breadcrumb<S, V> getBreadcrumb() {
		return breadcrumb;
	}

	public void setBreadcrumb(Breadcrumb<S, V> breadcrumb) {
		this.breadcrumb = breadcrumb;
	}

	public S getState() {
		return state;
	}

	public void setState(S state) {
		this.state = state;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public int getStateIndex() {
		return stateIndex;
	}

	public void setStateIndex(int stateIndex) {
		this.stateIndex = stateIndex;
	}

	public S getOldState() {
		return oldState;
	}

	public void setOldState(S oldState) {
		this.oldState = oldState;
	}

	public V getOldValue() {
		return oldValue;
	}

	public void setOldValue(V oldValue) {
		this.oldValue = oldValue;
	}

	public BreadcrumbEventType getEventType() {
		return eventType;
	}

	public void setEventType(BreadcrumbEventType eventType) {
		this.eventType = eventType;
	}
	
}
