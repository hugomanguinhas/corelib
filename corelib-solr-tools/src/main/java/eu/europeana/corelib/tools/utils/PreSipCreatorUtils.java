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
package eu.europeana.corelib.tools.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class to read mapping files created before SIPCreator
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class PreSipCreatorUtils extends MappingParser {

	private String repository;
	private final static String INPUT_FOLDER = "/mappings/";
	private final static String SUFFIX = "_mapping.txt";
	private final static String IDENTIFIER = "(ID)";
	private final static String RECORD_SPLITTER = "=>";
	private final static String LINE_SPLITTER = "\n";

	@Override
	public String getHashField(String collectionId, String fileName) {
		String inputString = this.readFile(repository + collectionId
				+ INPUT_FOLDER + fileName + SUFFIX);
		if (inputString != null) {
			String[] lines = StringUtils.split(inputString, LINE_SPLITTER);
			for (String line : lines) {
				if (StringUtils.contains(line, IDENTIFIER)
						&& StringUtils.contains(line, RECORD_SPLITTER)) {
					return StringUtils.replace(
							StringUtils.substringBetween(line, RECORD_SPLITTER,
									IDENTIFIER).trim(), ":", "_");
				}
			}
		}
		return null;
	}

	
	
}
