/**   
 * License Agreement for Jaeksoft OpenSearchServer
 *
 * Copyright (C) 2010 Emmanuel Keller / Jaeksoft
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

package com.jaeksoft.searchlib.user;

public enum Role {

	INDEX_QUERY("Index: query the index"),

	INDEX_UPDATE("Index: insert data"),

	INDEX_SCHEMA("Index: edit the schema"),

	WEB_CRAWLER_EDIT_PATTERN_LIST("Web crawler: edit then pattern list"),

	WEB_CRAWLER_EDIT_PARAMETERS("Web crawler: edit parameters"),

	WEB_CRAWLER_START_STOP("Web crawler: start and stop"),

	FILE_CRAWLER_EDIT_PATTERN_LIST("File crawler: edit the pattern list"),

	FILE_CRAWLER_EDIT_PARAMETERS("File crawler: edit parameters"),

	FILE_CRAWLER_START_STOP("File crawler: start  and stop");

	private String label;

	public static Role[] GROUP_INDEX = { INDEX_QUERY, INDEX_UPDATE,
			INDEX_SCHEMA };

	public static Role[] GROUP_WEB_CRAWLER = { WEB_CRAWLER_EDIT_PATTERN_LIST,
			WEB_CRAWLER_EDIT_PARAMETERS, WEB_CRAWLER_START_STOP };

	public static Role[] GROUP_FILE_CRAWLER = { FILE_CRAWLER_EDIT_PATTERN_LIST,
			FILE_CRAWLER_EDIT_PARAMETERS, FILE_CRAWLER_START_STOP };

	private Role(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static Role find(String roleName) {
		try {
			return Role.valueOf(roleName);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}