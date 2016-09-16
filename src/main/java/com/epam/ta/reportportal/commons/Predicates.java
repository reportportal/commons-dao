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
package com.epam.ta.reportportal.commons;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author dzmitry_kavalets
 */
public class Predicates {

	private Predicates() {
		//statics only
	}

	public static <T> Predicate<T> notNull() {
		return t -> t != null;
	}

	public static <T> Predicate<T> isNull() {
		return t -> t == null;
	}

	public static <T> Predicate<T> equalTo(T target) {
		return (target == null) ? Predicates.<T> isNull() : t -> t.equals(target);
	}

	public static <T> Predicate<T> not(Predicate<T> predicate) {
		return item -> !predicate.test(item);
	}

	public static <T> Predicate<T> in(Collection<? extends T> target) {
		return target::contains;
	}

	public static <T> Predicate<T> alwaysFalse() {
		return t -> false;
	}

	public static <T> Predicate<T> and(List<? extends Predicate<? super T>> components) {
		return t -> !components.stream().filter(predicate -> !predicate.test(t)).findFirst().isPresent();
	}

	@SuppressWarnings("unchecked")
	public static <T> Predicate<T> or(Predicate<? super T>... components) {
		return t -> Stream.of(components).filter(predicate -> predicate.test(t)).findFirst().isPresent();
	}

	public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> components) {
		return t -> StreamSupport.stream(components.spliterator(), false).filter(predicate -> predicate.test(t)).findFirst().isPresent();
	}

	public static Predicate<Optional<?>> isPresent() {
		return Optional::isPresent;
	}
}