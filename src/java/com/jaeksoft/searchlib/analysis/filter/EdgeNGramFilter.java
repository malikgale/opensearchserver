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

package com.jaeksoft.searchlib.analysis.filter;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenFilter;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenFilter.Side;

import com.jaeksoft.searchlib.SearchLibException;
import com.jaeksoft.searchlib.analysis.ClassPropertyEnum;
import com.jaeksoft.searchlib.analysis.FilterFactory;

public class EdgeNGramFilter extends FilterFactory {

	private int min;

	private int max;

	private Side side;

	private final static Object[] SIDE_VALUE_LIST = {
			EdgeNGramTokenFilter.Side.FRONT.getLabel(),
			EdgeNGramTokenFilter.Side.BACK.getLabel() };

	@Override
	protected void initProperties() throws SearchLibException {
		super.initProperties();
		addProperty(ClassPropertyEnum.MIN_GRAM,
				Integer.toString(EdgeNGramTokenFilter.DEFAULT_MIN_GRAM_SIZE),
				null);
		addProperty(ClassPropertyEnum.MAX_GRAM,
				Integer.toString(EdgeNGramTokenFilter.DEFAULT_MAX_GRAM_SIZE),
				null);
		addProperty(ClassPropertyEnum.SIDE,
				EdgeNGramTokenFilter.DEFAULT_SIDE.getLabel(), SIDE_VALUE_LIST);
	}

	@Override
	protected void checkValue(ClassPropertyEnum prop, String value)
			throws SearchLibException {
		if (prop == ClassPropertyEnum.MIN_GRAM)
			min = Integer.parseInt(value);
		else if (prop == ClassPropertyEnum.MAX_GRAM)
			max = Integer.parseInt(value);
		else if (prop == ClassPropertyEnum.SIDE)
			side = Side.getSide(value);
	}

	@Override
	public TokenStream create(TokenStream input) {
		return new EdgeNGramTokenFilter(input, side, min, max);
	}

}
