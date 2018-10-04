package com.epam.ta.reportportal.entity.filter;

import com.epam.ta.reportportal.commons.validation.BusinessRule;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.ws.model.ErrorType;

import java.util.Arrays;
import java.util.Optional;

import static com.epam.ta.reportportal.commons.Predicates.isPresent;

/**
 * Describe possible object types for {@link com.epam.ta.reportportal.commons.querygen.Filter} object<br>
 * construction and contains functionality for calculating classes objects <br>
 * by appropriate ObjectType's elements.
 *
 * @author Aliaksei_Makayed
 */
public enum ObjectType {

	Launch(Launch.class),
	TestItem(TestItem.class),
	Log(Log.class);

	private Class<?> classObject;

	ObjectType(Class<?> classObject) {
		this.classObject = classObject;
	}

	public static ObjectType getObjectTypeByName(String name) {
		Optional<ObjectType> objectType = Arrays.stream(ObjectType.values()).filter(type -> type.name().equalsIgnoreCase(name)).findAny();
		BusinessRule.expect(objectType, isPresent()).verify(
				ErrorType.BAD_SAVE_USER_FILTER_REQUEST,
				"Unknown Filter object type '" + name + "'. Possible types: log, launch, testItem."
		);
		return objectType.get();
	}

	public static Class<?> getTypeByName(String name) {
		Optional<ObjectType> objectType = Arrays.stream(ObjectType.values()).filter(type -> type.name().equalsIgnoreCase(name)).findAny();
		BusinessRule.expect(objectType, isPresent()).verify(
				ErrorType.BAD_SAVE_USER_FILTER_REQUEST,
				"Unknown Filter object type '" + name + "'. Possible types: log, launch, testItem."
		);
		return objectType.get().classObject;
	}

	public Class<?> getClassObject() {
		return classObject;
	}
}
