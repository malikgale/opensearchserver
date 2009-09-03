/**   
 * License Agreement for Jaeksoft OpenSearchServer
 *
 * Copyright (C) 2009 Emmanuel Keller / Jaeksoft
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

package com.jaeksoft.searchlib.web.xmlrpc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.LockObtainFailedException;

import com.jaeksoft.searchlib.Client;
import com.jaeksoft.searchlib.ClientCatalog;
import com.jaeksoft.searchlib.SearchLibException;

public class DeleteXmlRpc extends AbstractXmlRpc {

	public Map<String, ?> deleteById(String index, Integer docId)
			throws SearchLibException, NamingException,
			NoSuchAlgorithmException, IOException, URISyntaxException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Client client = ClientCatalog.getClient(index);
		if (client.deleteDocument(docId))
			return newInfoMap("Document deleted");
		return newInfoMap("Nothing to delete");
	}

	public Map<String, ?> deleteByIds(String index, List<Integer> docIds)
			throws SearchLibException, NamingException,
			NoSuchAlgorithmException, IOException, URISyntaxException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Client client = ClientCatalog.getClient(index);
		int n = client.deleteDocumentsById(docIds);
		return newInfoMap(n + " document(s) deleted");
	}

	public Map<String, ?> deleteByUniqueKey(String index, String uniqueKey)
			throws SearchLibException, NamingException, CorruptIndexException,
			LockObtainFailedException, IOException, URISyntaxException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Client client = ClientCatalog.getClient(index);
		if (client.deleteDocument(uniqueKey))
			return newInfoMap("Document deleted");
		return newInfoMap("Nothing to delete");
	}

	public Map<String, ?> deleteByUniqueKeys(String index,
			List<String> uniqueKeys) throws SearchLibException,
			NamingException, CorruptIndexException, LockObtainFailedException,
			IOException, URISyntaxException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Client client = ClientCatalog.getClient(index);
		int n = client.deleteDocuments(uniqueKeys);
		return newInfoMap(n + " document(s) deleted");
	}
}