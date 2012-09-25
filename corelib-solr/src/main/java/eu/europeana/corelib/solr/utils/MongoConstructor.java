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

package eu.europeana.corelib.solr.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.RDF.Choice;
import eu.europeana.corelib.definitions.solr.entity.EuropeanaAggregation;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.server.impl.EdmMongoServerImpl;
import eu.europeana.corelib.solr.server.importer.util.AgentFieldInput;
import eu.europeana.corelib.solr.server.importer.util.AggregationFieldInput;
import eu.europeana.corelib.solr.server.importer.util.ConceptFieldInput;
import eu.europeana.corelib.solr.server.importer.util.EuropeanaAggregationFieldInput;
import eu.europeana.corelib.solr.server.importer.util.PlaceFieldInput;
import eu.europeana.corelib.solr.server.importer.util.ProvidedCHOFieldInput;
import eu.europeana.corelib.solr.server.importer.util.ProxyFieldInput;
import eu.europeana.corelib.solr.server.importer.util.TimespanFieldInput;
import eu.europeana.corelib.solr.server.importer.util.WebResourcesFieldInput;

/**
 * A FullBean Constructor from an EDM XML
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public class MongoConstructor {

//	private EdmMongoServerImpl mongoServer;
	private final static String EUROPEANA_URI="http:///www.europeana.eu/portal/record";

//	public void setMongoServer(EdmMongoServerImpl mongoServer) {
//		this.mongoServer = mongoServer;
//	}

	/**
	 * Construct a FullBean out of A JiBX RDF record
	 * 
	 * @param record
	 *            The JiBX RDF record
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public FullBeanImpl constructFullBean(RDF record, EdmMongoServerImpl mongoServer)
			throws InstantiationException, IllegalAccessException,
			MalformedURLException, IOException {
		FullBeanImpl fullBean = new FullBeanImpl();
		List<AgentImpl> agents = new ArrayList<AgentImpl>();
		List<AggregationImpl> aggregations = new ArrayList<AggregationImpl>();
		List<ConceptImpl> concepts = new ArrayList<ConceptImpl>();
		List<PlaceImpl> places = new ArrayList<PlaceImpl>();
		List<WebResourceImpl> webResources = new ArrayList<WebResourceImpl>();
		List<TimespanImpl> timespans = new ArrayList<TimespanImpl>();
		List<ProxyImpl> proxies = new ArrayList<ProxyImpl>();
		List<ProvidedCHOImpl> providedCHOs = new ArrayList<ProvidedCHOImpl>();
		List<Choice> elements = record.getChoiceList();
		EuropeanaAggregation europeanaAggregation = new EuropeanaAggregationImpl();
		for (Choice element : elements) {

			if (element.ifProvidedCHO()) {
				fullBean.setAbout(element.getProvidedCHO().getAbout());
				try {
					providedCHOs.add(new ProvidedCHOFieldInput()
							.createProvidedCHOMongoFields(
									element.getProvidedCHO(), mongoServer));

				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			if (element.ifProxy()) {
				if (proxies.size() > 0) {
					proxies.set(0, new ProxyFieldInput()
							.createProxyMongoFields(new ProxyImpl(),
									element.getProxy(), mongoServer));
				} else {
					proxies.add(new ProxyFieldInput().createProxyMongoFields(
							new ProxyImpl(), element.getProxy(), mongoServer));
				}
			}
			if (element.ifAggregation()) {
				aggregations.add(new AggregationFieldInput()
						.createAggregationMongoFields(element.getAggregation(),
								mongoServer));
				if (webResources.size() > 0) {
					aggregations.set(0, new AggregationFieldInput()
							.appendWebResource(aggregations, webResources,
									mongoServer));
				}
				if (proxies.size() > 0) {
					proxies.set(0, new ProxyFieldInput().addProxyForMongo(
							proxies.get(0), element.getAggregation(),
							mongoServer));
				} else {
					proxies.add(new ProxyFieldInput().addProxyForMongo(
							new ProxyImpl(), element.getAggregation(),
							mongoServer));
				}

			}
			if (element.ifConcept()) {
				concepts.add(new ConceptFieldInput().createConceptMongoFields(
						element.getConcept(), mongoServer, record));
			}
			if (element.ifPlace()) {
				places.add(new PlaceFieldInput().createPlaceMongoFields(
						element.getPlace(), mongoServer));
			}

			if (element.ifWebResource()) {
				WebResourceImpl webResource = new WebResourcesFieldInput()
						.createWebResourceMongoField(element.getWebResource(),
								mongoServer);
				webResources.add(webResource);
				if (aggregations.size() > 0) {
					aggregations.set(0, new AggregationFieldInput()
							.appendWebResource(aggregations, webResource,
									mongoServer));
				}
				europeanaAggregation = new EuropeanaAggregationFieldInput()
						.appendWebResource(europeanaAggregation, webResource,
								mongoServer);

			}
			if (element.ifTimeSpan()) {
				timespans.add(new TimespanFieldInput()
						.createTimespanMongoField(element.getTimeSpan(),
								mongoServer));
			}
			if (element.ifAgent()) {
				agents.add(new AgentFieldInput().createAgentMongoEntity(
						element.getAgent(), mongoServer));
			}
			if (element.ifEuropeanaAggregation()) {

				europeanaAggregation = new EuropeanaAggregationFieldInput()
						.createAggregationMongoFields(
								element.getEuropeanaAggregation(), mongoServer);
				if (webResources.size() > 0) {
					europeanaAggregation = new EuropeanaAggregationFieldInput()
							.appendWebResource(europeanaAggregation,
									webResources, mongoServer);
				}

				proxies.add(new ProxyFieldInput().addEuropeanaProxyForMongo(
						new ProxyImpl(), element.getEuropeanaAggregation(),
						mongoServer));

			}
		}

		AggregationImpl aggregation = aggregations.get(0);
		aggregation.setWebResources(webResources);
		MongoUtils.updateAggregation(aggregation,mongoServer);
		
		fullBean.setProvidedCHOs(providedCHOs);
		if (mongoServer.searchByAbout(EuropeanaAggregationImpl.class,"/aggregation/europeana"+fullBean.getAbout()
				) == null) {
			europeanaAggregation.setAbout("/aggregation/europeana"+fullBean.getAbout()
					);
			europeanaAggregation.setEdmLandingPage(EUROPEANA_URI+fullBean.getAbout()+".html");
			europeanaAggregation.setAggregatedCHO(fullBean.getAbout());
			mongoServer.getDatastore().save(europeanaAggregation);
		} else {
			MongoUtils.updateEuropeanaAggregation(europeanaAggregation,mongoServer);
		}
		fullBean.setEuropeanaAggregation(europeanaAggregation);

		fullBean.setAggregations(aggregations);
		try {
			if (agents.size() > 0) {
				fullBean.setAgents(agents);
			}
			if (concepts.size() > 0) {
				fullBean.setConcepts(concepts);
			}
			if (places.size() > 0) {
				fullBean.setPlaces(places);
			}
			if (timespans.size() > 0) {
				fullBean.setTimespans(timespans);
			}
			if (proxies.size() > 0) {
				fullBean.setProxies(proxies);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fullBean;
	}

	
}
