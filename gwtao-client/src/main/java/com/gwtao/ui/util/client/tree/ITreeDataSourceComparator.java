/* 
 * Copyright 2012 GWTAO
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtao.ui.util.client.tree;

import java.util.Comparator;

/**
 * Compare objects for sorting of tree items.<br>
 * Let a data source implement this interface to get a special sort order. Otherwise the order of an item
 * array (returned from {@link ITreeDataSource#getItems(Object)}) is used.
 * 
 * @author mah
 * 
 */
public interface ITreeDataSourceComparator extends Comparator<Object> {

}
