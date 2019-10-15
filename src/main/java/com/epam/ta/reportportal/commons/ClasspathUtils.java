/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.commons;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

/**
 * Set of useful utils for working with classpath
 *
 * @author Andrei Varabyeu
 */
public class ClasspathUtils {

	/*
	 * Reflections object for scanning classpath of context class loader
	 */
	private static final Reflections REFLECTIONS = new Reflections(
			new ConfigurationBuilder().addClassLoader(ClasspathHelper.contextClassLoader())
					.addUrls(ClasspathHelper.forJavaClassPath())
					.addScanners(new SubTypesScanner()));

	/**
	 * Finds in classpath subclasses of provided class
	 *
	 * @param clazz Class to find subclasses of
	 * @return Set of sublcasses
	 */
	public static <T> Set<Class<? extends T>> findSubclassesOf(Class<T> clazz) {
		return REFLECTIONS.getSubTypesOf(clazz);
	}
}
