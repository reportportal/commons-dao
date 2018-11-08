///*
// *  Copyright (C) 2018 EPAM Systems
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// *
// *   http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//
//package com.epam.ta.reportportal.mapper;
//
//import com.epam.ta.reportportal.commons.JsonbUserType;
//import com.epam.ta.reportportal.exception.ReportPortalException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.postgresql.util.PGobject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.Optional;
//
//import static org.apache.commons.lang.CharEncoding.UTF_8;
//
//public class JsonbMapper {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(JsonbMapper.class);
//	private static ObjectMapper objectMapper;
//
//	static {
//
//		objectMapper = new ObjectMapper();
//		objectMapper.enableDefaultTyping();
//	}
//
//	private JsonbMapper() {
//		//static only
//	}
//
//	public static <T extends Serializable> T getJsonb(Object object) {
//		return (T) Optional.ofNullable(object)
//				.filter(v -> v instanceof PGobject)
//				.map(v -> ((PGobject) object).getValue())
//				.flatMap(JsonbMapper::read)
//				.orElseThrow(() -> new ReportPortalException("Failed to convert String to JsonObject"));
//	}
//
//	private static Optional<JsonbUserType> read(String value) {
//		try {
//			return Optional.ofNullable(objectMapper.readValue(value.getBytes(UTF_8), JsonbObject.class));
//		} catch (IOException e) {
//
//			LOGGER.error("Failed to convert String to JsonObject: ", e);
//
//			return Optional.empty();
//		}
//	}
//}
