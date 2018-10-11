package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BinaryData;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Pavel Bortnik
 */
public interface UserRepositoryCustom extends FilterableRepository<User>{

	String uploadUserPhoto(String username, BinaryData binaryData);

	String replaceUserPhoto(String username, BinaryData binaryData);

	String replaceUserPhoto(User user, BinaryData binaryData);

	void deleteUserPhoto(String path);

	Page<User> searchForUser(String term, Pageable pageable);

	/**
	 * Finds entities list according provided filter
	 *
	 * @param filter   Filter - Query representation
	 * @param pageable Page Representation
	 * @param exclude  Fields to exclude from query
	 * @return Found Paged objects
	 */
	Page<User> findByFilterExcluding(Queryable filter, Pageable pageable, String... exclude);



}
