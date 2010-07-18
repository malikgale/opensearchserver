/**   
 * License Agreement for Jaeksoft OpenSearchServer
 *
 * Copyright (C) 2008-2010 Emmanuel Keller / Jaeksoft
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

package com.jaeksoft.searchlib.util;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomUtils {

	final private static void getNodes(Node parent, int pos, String[] path,
			List<Node> nodes) {
		if (pos == path.length) {
			nodes.add(parent);
			return;
		}
		NodeList childrens = parent.getChildNodes();
		int l = childrens.getLength();
		int nextPos = pos + 1;
		for (int i = 0; i < l; i++) {
			Node node = childrens.item(i);
			switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				if (node.getNodeName().equals(path[pos]))
					getNodes(node, nextPos, path, nodes);
				break;
			default:
				continue;
			}
		}
	}

	final public static void getNodes(List<Node> nodes, Node parent,
			String... path) {
		if (path == null)
			return;
		if (path.length == 0)
			return;
		getNodes(parent, 0, path, nodes);
	}

	final public static List<Node> getNodes(Node parent, String... path) {
		if (path == null)
			return null;
		if (path.length == 0)
			return null;
		List<Node> nodes = new ArrayList<Node>();
		getNodes(nodes, parent, path);
		return nodes;
	}

	final private static void getText(Node parent, StringBuffer sb) {
		if (parent.getNodeType() == Node.TEXT_NODE)
			sb.append(parent.getNodeValue());
		NodeList childrens = parent.getChildNodes();
		int l = childrens.getLength();
		for (int i = 0; i < l; i++)
			getText(childrens.item(i), sb);
	}

	final public static String getText(Node node) {
		StringBuffer sb = new StringBuffer();
		getText(node, sb);
		return sb.toString();
	}

	final public static int countElements(Node parent) {
		int counter = 0;
		NodeList childrens = parent.getChildNodes();
		int l = childrens.getLength();
		for (int i = 0; i < l; i++) {
			Node node = childrens.item(i);
			switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				counter++;
				counter += countElements(node);
				break;
			default:
				continue;
			}
		}
		return counter;
	}

	final public static String getAttributeText(Node node, String name) {
		NamedNodeMap nnm = node.getAttributes();
		if (nnm == null)
			return null;
		Node attr = nnm.getNamedItem(name);
		if (attr == null)
			return null;
		return getText(attr);
	}

	final private static void getAllNodes(Node parent, String tagName,
			List<Node> nodes) {
		if (parent.getNodeName().equals(tagName))
			nodes.add(parent);
		NodeList childrens = parent.getChildNodes();
		int l = childrens.getLength();
		for (int i = 0; i < l; i++)
			getAllNodes(childrens.item(i), tagName, nodes);

	}

	final public static List<Node> getAllNodes(Node parent, String tagName) {
		List<Node> nodes = new ArrayList<Node>();
		getAllNodes(parent, tagName, nodes);
		return nodes;
	}

}
