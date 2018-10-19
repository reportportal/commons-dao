package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserFilterRepositoryCustom extends FilterableRepository<UserFilter> {

    /**
     * Get all permitted filters for specified project and user
     */
    Page<UserFilter> getPermittedFilters(Long projectId, Filter filter,
        Pageable pageable, String userName);

    /**
     * Get only filters for which the user is owner
     */
    Page<UserFilter> getOwnFilters(Long projectId, Filter filter, Pageable pageable,
        String userName);

    /**
     * Get all shared filters  for specified project and user without own filters
     */
    Page<UserFilter> getSharedFilters(Long projectId, Filter filter, Pageable pageable,
        String userName);
}
