package com.epam.ta.reportportal.dao;

import java.util.List;

/**
 * @author Yauheni_Martynau
 */
public interface LaunchTagRepositoryCustom {

	List<String> getTags(Long projectId, String value);
}
