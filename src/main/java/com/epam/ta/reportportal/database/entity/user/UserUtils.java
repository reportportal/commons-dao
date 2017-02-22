/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
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

package com.epam.ta.reportportal.database.entity.user;

import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.ProjectRole;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * User entry related utilities
 *
 * @author Andrei_Ramanchuk
 */
public class UserUtils {

    /**
     * MD5 password hashing
     *
     * @param initial
     * @return
     */
    public static String generateMD5(String initial) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] byteInitial = initial.getBytes("UTF-8");
            byteInitial = md.digest(byteInitial);
            // convert the byte to hex format
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteInitial.length; i++) {
                sb.append(Integer.toString((byteInitial[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Unable apply MD5 algorithm for password hashing: ", ex);
        } catch (UnsupportedEncodingException unsEx) {
            throw new RuntimeException("Unable apply UTF-8 encoding for password string: ", unsEx);
        }
    }

    /**
     * Validate email format against RFC822
     *
     * @param email
     * @return
     */
    public static boolean isEmailValid(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    /**
     * Transforms to map Project->ProjectRole
     *
     * @param user     Username
     * @param projects List of assigned projects
     * @return Project->ProjectRole map
     */
    public static Map<String, ProjectRole> getUserRoles(String user, List<Project> projects) {
        return projects
                .stream()
                .filter(p -> p.getUsers().containsKey(user))
                .collect(Collectors.
                        toMap(Project::getName, p -> p.getUsers().get(user).getProjectRole()));
    }
}
