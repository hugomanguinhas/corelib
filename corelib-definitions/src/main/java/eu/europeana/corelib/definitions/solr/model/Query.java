/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */

package eu.europeana.corelib.definitions.solr.model;

import java.util.HashMap;
import java.util.Map;

import eu.europeana.corelib.definitions.solr.Facet;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class Query {

	/**
	 * Default start parameter for Solr
	 */
	private static final int DEFAULT_START = 0;
	
	/**
	 * Default number of items in the SERP
	 */
	private static final int DEFAULT_PAGE_SIZE = 12;
	
	
	private String query;
	
	private String[] refinements;
	
	private int start;
	
	private int pageSize;
	
	private Facet[] facets = Facet.values();
	
	private Map<String, String> parameters = new HashMap<String, String>();

	/**
	 * CONSTRUCTORS
	 */
	
	public Query(String query) {
		this.query = query;
		start = DEFAULT_START;
		pageSize = DEFAULT_PAGE_SIZE;
	}
	

	/**
	 * GETTERS & SETTTERS
	 */

	public String getQuery() {
		return query;
	}

	public Query setQuery(String query) {
		this.query = query;
		return this;
	}

	public String[] getRefinements() {
		return refinements;
	}

	public Query setRefinements(String... refinements) {
		if (refinements != null) {
			this.refinements = refinements.clone();
		} else {
			this.refinements = StringArrayUtils.EMPTY_ARRAY;
		}
		return this;
	}

	public Integer getStart() {
		return start;
	}

	public Query setStart(int start) {
		this.start = start;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public Query setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	
	public Facet[] getFacets() {
		return facets;
	}
	
	public Query setFacets(Facet[] facets) {
		if (facets != null) {
			this.facets = facets.clone();
		} else {
			this.facets = Facet.values();
		}
		return this;
	}
	
	public Map<String, String> getParameters() {
		return parameters;
	}
	
	/**
	 * Adds Solr parameters to the Query object
	 *
	 * @param key
	 *   The parameter name
	 * @param value
	 *   The value of the parameter
	 * @return 
	 *   The Query object
	 */
	public Query setParameter(String key, String value) {
		parameters.put(key, value);
		return this;
	}
}
