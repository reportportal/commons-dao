package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.jooq.tables.JProject;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.jooq.Tables.PROJECT;
import static com.epam.ta.reportportal.jooq.Tables.USERS;

@Repository
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

	private static final RecordMapper<? super Record, Project> PROJECT_MAPPER = r -> new Project(r.get(JProject.PROJECT.ID, Long.class),
			r.get(JProject.PROJECT.NAME, String.class)
	);

	@Autowired
	private DSLContext dsl;

	@Override
	public List<Project> findByFilter(Filter filter) {

		return dsl.fetch(QueryBuilder.newBuilder(filter).build()).map(PROJECT_MAPPER);
	}

	@Override
	public Optional<String> findPersonalProjectName(String username) {
		Optional<Project> projectOptional = Optional.ofNullable(dsl.select()
				.from(USERS)
				.join(PROJECT)
				.on(USERS.DEFAULT_PROJECT_ID.eq(PROJECT.ID))
				.where(USERS.LOGIN.eq(username))
				.fetchOne(PROJECT_MAPPER));
		return projectOptional.map(Project::getName);
	}
}
