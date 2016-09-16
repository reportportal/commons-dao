/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
 * 
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */ 

package com.epam.ta.reportportal.database.search;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/**
 * Holds mappings with search criterias for specified package. Reads all classes from package and
 * tries to find classes which corresponds given criteria<br>
 * 
 * Here some interface should be exposed since now we are finding classes by hardcoded annotation
 * {@link Document}
 * 
 * @author Andrei Varabyeu
 * 
 */
public class CriteriaMapFactory {

	public static final Supplier<CriteriaMapFactory> DEFAULT_INSTANCE_SUPPLIER = Suppliers
			.memoize(() -> new CriteriaMapFactory("com.epam.ta.reportportal.database.entity"));

	private static final Logger LOGGER = LoggerFactory.getLogger(CriteriaMapFactory.class);

	/**
	 * Mapping between found class and criteria map created for it
	 */
	private Map<Class<?>, CriteriaMap<?>> classCriterias;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CriteriaMapFactory(String basePackage) {
		classCriterias = new HashMap<>();
		for (Class<?> clazz : findNeededTypes(basePackage)) {
			classCriterias.put(clazz, new CriteriaMap(clazz));
		}
	}

	/**
	 * Returns Criteria Map for specified class
	 */
	@SuppressWarnings("unchecked")
	public <T> CriteriaMap<T> getCriteriaMap(Class<T> resource) {
		if (!classCriterias.containsKey(resource)) {
			throw new IllegalArgumentException("Unable to find search criteria map for resource '" + resource + "'");
		}
		return (CriteriaMap<T>) classCriterias.get(resource);
	}

	/**
	 * Find Classes into specified package
	 */
	private List<Class<?>> findNeededTypes(String basePackage) {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

		List<Class<?>> candidates = new LinkedList<>();

		try {
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resolveBasePackage(basePackage) + "/"
					+ "**/*.class";
			Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
			for (Resource resource : resources) {
				if (resource.isReadable()) {
					MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
					if (isCandidate(metadataReader)) {
						candidates.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
					}
				}
			}
			return candidates;
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to scan base package '" + basePackage + "'", e);
		}
	}

	/**
	 * Converts base package to resource name path
	 * 
	 * @param basePackage
	 * @return
	 */
	private String resolveBasePackage(String basePackage) {
		return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
	}

	/**
	 * Main criteria for finding classes in package
	 * 
	 * @param metadataReader
	 * @return
	 * @throws ClassNotFoundException
	 */
	private boolean isCandidate(MetadataReader metadataReader) throws ClassNotFoundException {
		try {
			Class<?> c = Class.forName(metadataReader.getClassMetadata().getClassName());
			if (c.getAnnotation(Document.class) != null) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Class with name " + metadataReader.getClassMetadata().getClassName() + " not found", e);
		}
		return false;
	}
}