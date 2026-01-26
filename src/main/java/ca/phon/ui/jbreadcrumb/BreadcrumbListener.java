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
 * An interface for classes that want to listen to state changes in
 * a {@link Breadcrumb}.
 * 
 * @param <S>  the type of state
 * @param <V>  the type of value associated with a state
 */
@FunctionalInterface
public interface BreadcrumbListener<S, V> {
	
	/**
	 * Called when a BreadcrumbEvent occurs.
	 * 
	 * @param event  the breadcrumb event
	 */
	public void breadCrumbEvent(BreadcrumbEvent<S, V> event);
	
}
