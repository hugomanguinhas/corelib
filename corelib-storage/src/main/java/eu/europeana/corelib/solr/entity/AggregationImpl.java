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

package eu.europeana.corelib.solr.entity;


import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

//import org.springframework.data.neo4j.annotation.GraphProperty;
//import org.springframework.data.neo4j.annotation.NodeEntity;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.utils.StringArrayUtils;
//import javax.persistence.Transient;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.model.definitions.Aggregation
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
//@NodeEntity(partial = true)
@Entity("Aggregation")
public class AggregationImpl extends AbstractEdmEntityImpl implements Aggregation {

//	@GraphProperty(propertyType = String.class)
	private Map<String,List<String>> edmDataProvider;
	private String edmIsShownBy;
	private String edmIsShownAt;
	private String edmObject;
	
//	@GraphProperty(propertyType = String.class)
	private Map<String,List<String>> edmProvider;
	
//	@GraphProperty(propertyType = String.class)
	private Map<String,List<String>> edmRights;
	
//	@GraphProperty(defaultValue="")
	private String edmUgc;
	
//	@GraphProperty(propertyType = String.class)
	private Map<String,List<String>> dcRights;
	
//	@GraphProperty(defaultValue="")
	private String[] hasView;
	
//	@GraphProperty(defaultValue="")
	private String aggregatedCHO;
	
//	@GraphProperty(defaultValue="")
	private String[] aggregates;
	
//	@GraphProperty(defaultValue="")
	private String[] edmUnstored;

//	@Transient
	@Reference
	private List<WebResourceImpl> webResources;

//	@GraphProperty
	private Boolean edmPreviewNoDistribute;

	@Override
	public String getAggregatedCHO() {
		return this.aggregatedCHO;
	}

	@Override
	public void setAggregatedCHO(String aggregatedCHO) {
		this.aggregatedCHO = aggregatedCHO;
	}

	@Override
	public Boolean getEdmPreviewNoDistribute() {
		return this.edmPreviewNoDistribute;
	}

	@Override
	public void setEdmPreviewNoDistribute(Boolean edmPreviewNoDistribute) {
		this.edmPreviewNoDistribute = edmPreviewNoDistribute;
	}

	@Override
	public String getEdmUgc() {
		return this.edmUgc;
	}

	@Override
	public void setEdmUgc(String edmUgc) {
		this.edmUgc = edmUgc;
	}

	
	@Override
	public void setEdmDataProvider(Map<String,List<String>> edmDataProvider) {
		this.edmDataProvider = edmDataProvider;
	}

	@Override
	public void setEdmIsShownBy(String edmIsShownBy) {
		this.edmIsShownBy = edmIsShownBy;
	}

	@Override
	public void setEdmIsShownAt(String edmIsShownAt) {
		this.edmIsShownAt = edmIsShownAt;
	}

	@Override
	public void setEdmObject(String edmObject) {
		this.edmObject = edmObject;
	}

	@Override
	public void setEdmProvider(Map<String,List<String>> edmProvider) {
		this.edmProvider = edmProvider;
	}

	@Override
	public void setEdmRights(Map<String,List<String>> edmRights) {
		this.edmRights = edmRights;
	}

	@Override
	public void setDcRights(Map<String,List<String>> dcRights) {
		this.dcRights = dcRights;
	}

	@Override
	public Map<String,List<String>> getEdmDataProvider() {
		return this.edmDataProvider;
	}

	@Override
	public String getEdmIsShownBy() {
		return this.edmIsShownBy;
	}

	@Override
	public String getEdmIsShownAt() {
		return this.edmIsShownAt;
	}

	@Override
	public String getEdmObject() {
		return this.edmObject;
	}

	@Override
	public Map<String,List<String>> getEdmProvider() {
		return this.edmProvider;
	}

	@Override
	public Map<String,List<String>> getDcRights() {
		return this.dcRights;
	}

	@Override
	public Map<String,List<String>> getEdmRights() {
		return this.edmRights;
	}



	@Override
	public List<? extends WebResource> getWebResources() {
		return this.webResources;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setWebResources(List<? extends WebResource> webResources) {
		this.webResources = (List<WebResourceImpl>) webResources;
	}

	@Override
	public String[] getHasView() {
		return (StringArrayUtils.isNotBlank(this.hasView) ? this.hasView.clone() : null);
	}

	@Override
	public void setHasView(String[] hasView) {
		this.hasView = hasView !=null? hasView.clone():null;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o.getClass() == this.getClass()) {
			return this.getAbout().equals(((AggregationImpl) o).getAbout());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.about != null ? this.about.hashCode() : this.id.hashCode();
	}

	@Override
	public String[] getAggregates() {
		return (StringArrayUtils.isNotBlank(this.aggregates) ? this.aggregates.clone() : null);
	}

	@Override
	public void setAggregates(String[] aggregates) {
		this.aggregates = aggregates!=null? aggregates.clone():null;
	}

	@Override
	public String[] getEdmUnstored() {
		return (StringArrayUtils.isNotBlank(this.edmUnstored) ? this.edmUnstored.clone() : null);
	}

	@Override
	public void setEdmUnstored(String[] edmUnstored) {
		this.edmUnstored = edmUnstored.clone();
	}
}
