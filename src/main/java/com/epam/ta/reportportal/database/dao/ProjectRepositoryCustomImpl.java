package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.project.Project;
import com.epam.ta.reportportal.database.entity.project.ProjectUser;
import com.epam.ta.reportportal.database.entity.user.Users;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.epam.ta.reportportal.jooq.Tables.*;

/**
 * @author Pavel Bortnik
 */
@Repository
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

	private DSLContext dsl;

	@Autowired
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public ProjectUser selectProjectUser(String projectName, String userName) {
		return dsl.select()
				.from(PROJECT)
				.join(PROJECT_USER)
				.on(PROJECT.ID.eq(PROJECT_USER.PROJECT_ID))
				.join(USERS)
				.on(PROJECT_USER.USER_ID.eq(USERS.ID))
				.where(PROJECT.NAME.eq(projectName))
				.and(USERS.LOGIN.eq(userName))
				.fetchOne(r -> {
					ProjectUser into = r.into(ProjectUser.class);
					into.setProject(r.into(Project.class));
					into.setUser(r.into(Users.class));
					return into;
				});
	}
}
