package com.epam.ta.reportportal.dao.util;

import com.google.gson.Gson;
import org.jooq.Converter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ivan Budaev
 */
public class JsonbConverter implements Converter<Object, Map> {

	@Override
	public Map from(Object t) {
		return t == null ? new LinkedHashMap() : new Gson().fromJson(String.valueOf(t), Map.class);
	}

	@Override
	public Object to(Map u) {
		return u == null || u.isEmpty() ? null : new Gson().toJson(u);
	}

	@Override
	public Class<Object> fromType() {
		return Object.class;
	}

	@Override
	public Class<Map> toType() {
		return Map.class;
	}
};

