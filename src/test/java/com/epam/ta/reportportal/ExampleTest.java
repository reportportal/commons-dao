package com.epam.ta.reportportal;

import com.epam.ta.reportportal.jooq.tables.pojos.Project;
import com.epam.ta.reportportal.jooq.tables.pojos.Users;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.jooq.impl.DefaultDSLContext;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static com.epam.ta.reportportal.jooq.tables.Project.PROJECT;
import static com.epam.ta.reportportal.jooq.tables.Users.USERS;
import static com.epam.ta.reportportal.jooq.tables.UsersProject.USERS_PROJECT;

/**
 * @author Pavel Bortnik
 */
@Ignore
public class ExampleTest extends BaseTest {

	@Autowired
	private DefaultDSLContext dsl;

	@Test
	public void selectGroupsTest() {
		AssertionsForInterfaceTypes.assertThat(aggregateUserProjects()).hasSize(3);
	}

	@Test
	public void selectWithJoinTest() {
		AssertionsForInterfaceTypes.assertThat(getUserProjects(1)).hasSize(1);
	}


	public Map<Users, List<Project>> aggregateUserProjects() {
		return dsl.select()
				.from(USERS)
				.join(USERS_PROJECT)
				.on(USERS_PROJECT.USER_ID.eq(USERS.ID))
				.join(PROJECT)
				.on(USERS_PROJECT.PROJECT_ID.eq(PROJECT.ID))
				.fetchGroups(
						// Map records first into the USER table and then into the key POJO type
						r -> r.into(Users.class),

						// Map records first into the ROLE table and then into the value POJO type
						r -> r.into(Project.class)
				);
	}

	public List<Project> getUserProjects(Integer id) {
		return dsl.select(USERS.fields())
				.from(USERS)
				.join(USERS_PROJECT)
				.on(USERS_PROJECT.USER_ID.eq(USERS.ID))
				.join(PROJECT)
				.on(USERS_PROJECT.PROJECT_ID.eq(PROJECT.ID))
				.where(USERS.ID.eq(id))
				.fetchInto(Project.class);
	}

}
