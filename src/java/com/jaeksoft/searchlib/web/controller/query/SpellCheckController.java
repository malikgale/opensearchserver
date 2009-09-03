/**   
 * License Agreement for Jaeksoft OpenSearchServer
 *
 * Copyright (C) 2008-2009 Emmanuel Keller / Jaeksoft
 * 
 * http://www.open-search-server.com
 * 
 * This file is part of Jaeksoft OpenSearchServer.
 *
 * Jaeksoft OpenSearchServer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * Jaeksoft OpenSearchServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jaeksoft OpenSearchServer. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.jaeksoft.searchlib.web.controller.query;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.RowRenderer;

import com.jaeksoft.searchlib.SearchLibException;
import com.jaeksoft.searchlib.schema.FieldList;
import com.jaeksoft.searchlib.schema.SchemaField;
import com.jaeksoft.searchlib.spellcheck.SpellCheckField;

public class SpellCheckController extends QueryController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5132791474383971273L;

	private String selectedField = null;

	private List<String> fieldLeft = null;

	private RowRenderer rowRenderer = null;

	public SpellCheckController() throws SearchLibException {
		super();
	}

	public RowRenderer getSpellCheckFieldRenderer() {
		synchronized (this) {
			if (rowRenderer != null)
				return rowRenderer;
			rowRenderer = new SpellCheckFieldRenderer();
			return rowRenderer;
		}
	}

	public boolean isFieldLeft() throws SearchLibException {
		synchronized (this) {
			return getSpellCheckFieldLeft().size() > 0;
		}
	}

	public List<String> getSpellCheckFieldLeft() throws SearchLibException {
		synchronized (this) {
			if (fieldLeft != null)
				return fieldLeft;
			fieldLeft = new ArrayList<String>();
			FieldList<SpellCheckField> spellCheckFields = getRequest()
					.getSpellCheckFieldList();
			for (SchemaField field : getClient().getSchema().getFieldList())
				if (field.isIndexed())
					if (spellCheckFields.get(field.getName()) == null) {
						if (selectedField == null)
							selectedField = field.getName();
						fieldLeft.add(field.getName());
					}
			return fieldLeft;
		}
	}

	public void onFieldRemove(Event event) throws SearchLibException {
		synchronized (this) {
			SpellCheckField spellCheckField = (SpellCheckField) event.getData();
			getRequest().getSpellCheckFieldList().remove(spellCheckField);
			reloadPage();
		}
	}

	public void setSelectedField(String value) {
		synchronized (this) {
			selectedField = value;
		}
	}

	public String getSelectedField() {
		synchronized (this) {
			return selectedField;
		}
	}

	public void onFieldAdd() throws SearchLibException {
		synchronized (this) {
			if (selectedField == null)
				return;
			getRequest().getSpellCheckFieldList().add(
					new SpellCheckField(selectedField, 0.5F));
			reloadPage();
		}
	}

	@Override
	public void reloadPage() {
		synchronized (this) {
			fieldLeft = null;
			selectedField = null;
			super.reloadPage();
		}
	}

}