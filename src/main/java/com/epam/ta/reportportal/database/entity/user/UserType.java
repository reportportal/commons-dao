package com.epam.ta.reportportal.database.entity.user;

import com.epam.ta.reportportal.database.entity.project.EntryType;

import java.util.Arrays;
import java.util.Optional;

/**
 * User Type enumeration<br>
 * Used for supporting different project types processing
 *
 * @author <a href="mailto:andrei_varabyeu@epam.com">Andrei Varabyeu</a>
 */
public enum UserType {

    //@formatter:off
    INTERNAL,
    UPSA,
    GITHUB;

    //@formatter:on

    public static UserType getByName(String type) {
        return UserType.valueOf(type);
    }

    public static Optional<UserType> findByName(String name) {
        return Arrays.stream(UserType.values()).filter(type -> type.name().equals(name)).findAny();
    }

    public static boolean isPresent(String name) {
        return findByName(name).isPresent();
    }
}
