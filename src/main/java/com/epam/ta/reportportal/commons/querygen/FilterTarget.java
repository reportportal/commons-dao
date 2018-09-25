package com.epam.ta.reportportal.commons.querygen;

import com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant;
import com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant;
import com.epam.ta.reportportal.dao.PostgresCrosstabWrapper;
import com.epam.ta.reportportal.entity.Activity;
import com.epam.ta.reportportal.entity.bts.Ticket;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.enums.JTestItemTypeEnum;
import com.epam.ta.reportportal.jooq.tables.*;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.commons.querygen.constant.ActivityCriteriaConstant.ACTION;
import static com.epam.ta.reportportal.commons.querygen.constant.ActivityCriteriaConstant.LOGIN;
import static com.epam.ta.reportportal.commons.querygen.constant.IntegrationCriteriaConstant.TYPE;
import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.*;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.max;

public enum FilterTarget {

	LAUNCH(Launch.class, Arrays.asList(
			//@formatter:off
			new CriteriaHolder(ID, "launch.id", Long.class, false),
			new CriteriaHolder(DESCRIPTION, "launch.description", String.class, false),
			new CriteriaHolder(PROJECT_ID, "project_id", Long.class, false),
			new CriteriaHolder(STATUS, "status", JStatusEnum.class, false),
			new CriteriaHolder(MODE, "mode", JLaunchModeEnum.class, false),
			new CriteriaHolder(NAME, "name", String.class, false)
			//@formatter:on
	)) {
		public SelectQuery<? extends Record> getQuery() {
			JLaunch l = JLaunch.LAUNCH;
			JStatistics s = JStatistics.STATISTICS;

			Select<?> fieldsForSelect = DSL.select(
					l.ID,
					l.UUID,
					l.PROJECT_ID,
					l.USER_ID,
					l.NAME,
					l.DESCRIPTION,
					l.START_TIME,
					l.END_TIME,
					l.NUMBER,
					l.LAST_MODIFIED,
					l.MODE,
					l.STATUS
			);

			Select<?> raw = DSL.select(s.LAUNCH_ID, s.S_FIELD, max(s.S_COUNTER))
					.from(s)
					.groupBy(s.LAUNCH_ID, s.S_FIELD)
					.orderBy(s.LAUNCH_ID, s.S_FIELD);

			return getPostgresWrapper().pivot(fieldsForSelect, raw, crossTabValues)
					.rightJoin(l)
					.on(field(DSL.name(LAUNCH_ID)).eq(l.ID))
					.getQuery();
		}
	},

	ACTIVITY(Activity.class, Arrays.asList(
			new CriteriaHolder(ID, "a.id", Long.class, false),
			new CriteriaHolder(PROJECT_ID, "a.project_id", Long.class, false),
			new CriteriaHolder(LOGIN, "u.login", String.class, false),
			new CriteriaHolder(ACTION, "a.action", String.class, false)
	)) {
		@Override
		public SelectQuery<? extends Record> getQuery() {

			JActivity a = JActivity.ACTIVITY.as("a");
			JUsers u = JUsers.USERS.as("u");
			JProject p = JProject.PROJECT.as("p");
			return DSL.select(a.ID, a.PROJECT_ID, a.USER_ID, a.ENTITY, a.ACTION, a.CREATION_DATE, u.LOGIN, p.NAME)
					.from(a)
					.join(u)
					.on(a.USER_ID.eq(u.ID))
					.join(p)
					.on(a.PROJECT_ID.eq(p.ID))
					.getQuery();
		}
	},

	TEST_ITEM(TestItem.class, Arrays.asList(
			new CriteriaHolder(PROJECT_ID, "l.project_id", Long.class, false),
			new CriteriaHolder("type", "ti.type", JTestItemTypeEnum.class, false),
			new CriteriaHolder(LAUNCH_ID, "tis.launch_id", Long.class, false),
			new CriteriaHolder(STATUS, "l.status", JStatusEnum.class, false),
			new CriteriaHolder(MODE, "l.mode", JLaunchModeEnum.class, false)
	)) {
		@Override
		public SelectQuery<? extends Record> getQuery() {
			JTestItem ti = JTestItem.TEST_ITEM.as("ti");
			JTestItem tis = JTestItem.TEST_ITEM.as("tis");
			JTestItemResults tir = JTestItemResults.TEST_ITEM_RESULTS;
			JLaunch l = JLaunch.LAUNCH.as("l");

			return DSL.select(ti.ITEM_ID, ti.NAME, ti.START_TIME, ti.TYPE, ti.UNIQUE_ID, tir.STATUS, tir.END_TIME, tir.DURATION)
					.from(ti)
					.join(tis)
					.on(tis.ITEM_ID.eq(ti.PARENT_ID))
					.join(l)
					.on(l.ID.eq(tis.LAUNCH_ID))
					.join(tir)
					.on(tir.RESULT_ID.eq(ti.ITEM_ID))
					.getQuery();
		}
	},

	TICKET(Ticket.class, Arrays.asList(
			new CriteriaHolder(NAME, "ti.name", String.class, false),
			new CriteriaHolder(PROJECT_ID, JLaunch.LAUNCH.PROJECT_ID.getQualifiedName().toString(), Long.class, false)
	)) {
		@Override
		public SelectQuery<? extends Record> getQuery() {

			JTestItem ti = JTestItem.TEST_ITEM.as("ti");
			JTestItemResults tir = JTestItemResults.TEST_ITEM_RESULTS.as("tir");
			JStatistics s = JStatistics.STATISTICS.as("s");
			JTicket tic = JTicket.TICKET;
			JUsers u = JUsers.USERS;
			JIssueTicket it = JIssueTicket.ISSUE_TICKET;
			JIssue i = JIssue.ISSUE;
			JLaunch l = JLaunch.LAUNCH;

			Select<?> fieldsForSelect = DSL.select(tic.TICKET_ID, tic.SUBMIT_DATE, tic.URL, ti.NAME, u.LOGIN);

			Select<?> raw = DSL.select(s.ITEM_ID, s.S_FIELD, max(s.S_COUNTER))
					.from(s)
					.groupBy(s.ITEM_ID, s.S_FIELD)
					.orderBy(s.ITEM_ID, s.S_FIELD);
			Select<?> crossTabValues = DSL.selectDistinct(s.S_FIELD).from(s).orderBy(s.S_FIELD);

			return getPostgresWrapper().pivot(fieldsForSelect, raw, crossTabValues)
					.join(ti)
					.on(field(DSL.name("ct", "item_id")).eq(ti.ITEM_ID))
					.join(tir)
					.on(ti.ITEM_ID.eq(tir.RESULT_ID))
					.join(l)
					.on(ti.LAUNCH_ID.eq(l.ID))
					.leftJoin(i)
					.on(tir.RESULT_ID.eq(i.ISSUE_ID))
					.leftJoin(it)
					.on(i.ISSUE_ID.eq(it.ISSUE_ID))
					.join(tic)
					.on(it.TICKET_ID.eq(tic.ID))
					.join(u)
					.on(tic.SUBMITTER_ID.eq(u.ID))
					.getQuery();
		}
	},

	INTEGRATION(Integration.class, Arrays.asList(
			//@formatter:off
			new CriteriaHolder(GeneralCriteriaConstant.PROJECT, "p.name", String.class, false),
			new CriteriaHolder(TYPE, "it.name", String.class, false)
			//@formatter:on
	)) {
		public SelectQuery<? extends Record> getQuery() {
			JIntegration i = JIntegration.INTEGRATION.as("i");
			JIntegrationType it = JIntegrationType.INTEGRATION_TYPE.as("it");
			JProject p = JProject.PROJECT.as("p");

			return DSL.select(i.ID, i.PROJECT_ID, i.TYPE, i.PARAMS, i.CREATION_DATE)
					.from(i)
					.leftJoin(it)
					.on(i.TYPE.eq(it.ID))
					.leftJoin(p)
					.on(i.PROJECT_ID.eq(p.ID))
					.groupBy(i.ID, i.PROJECT_ID, i.TYPE, i.PARAMS, i.CREATION_DATE)
					.getQuery();
		}
	},

	PROJECT(Project.class, Arrays.asList(

			new CriteriaHolder(NAME, "p.name", String.class, false))) {
		public SelectQuery<? extends Record> getQuery() {
			JProject p = JProject.PROJECT.as("p");

			return DSL.select(p.ID, p.NAME).from(p).getQuery();

		}
	},

	LOG(Log.class, Arrays.asList(new CriteriaHolder(LogCriteriaConstant.LOG_MESSAGE, "l.log_message", String.class, false))) {
		@Override
		public SelectQuery<? extends Record> getQuery() {
			JLog l = JLog.LOG.as("l");
			JTestItem ti = JTestItem.TEST_ITEM.as("ti");

			return DSL.select(
					l.ID,
					l.LOG_TIME,
					l.LOG_MESSAGE,
					l.LAST_MODIFIED,
					l.LOG_LEVEL,
					l.ITEM_ID,
					l.ATTACHMENT,
					l.ATTACHMENT_THUMBNAIL,
					l.CONTENT_TYPE
			)
					.from(l)
					.leftJoin(ti)
					.on(l.ITEM_ID.eq(ti.ITEM_ID))
					.groupBy(l.ID, l.LOG_TIME, l.LOG_MESSAGE, l.LAST_MODIFIED, l.LOG_LEVEL, l.ITEM_ID)
					.getQuery();
		}
	};

	private Class<?> clazz;
	private List<CriteriaHolder> criterias;
	private PostgresCrosstabWrapper postgresCrosstabWrapper;

	FilterTarget(Class<?> clazz, List<CriteriaHolder> criterias) {
		this.clazz = clazz;
		this.criterias = criterias;
	}

	public abstract SelectQuery<? extends Record> getQuery();

	public void setPostgresWrapper(PostgresCrosstabWrapper postgresCrosstabWrapper) {
		this.postgresCrosstabWrapper = postgresCrosstabWrapper;
	}

	public PostgresCrosstabWrapper getPostgresWrapper() {
		return postgresCrosstabWrapper;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public List<CriteriaHolder> getCriterias() {
		return criterias;
	}

	public Optional<CriteriaHolder> getCriteriaByFilter(String filterCriteria) {
		return criterias.stream().filter(holder -> holder.getFilterCriteria().equals(filterCriteria)).findAny();
	}

	public static FilterTarget findByClass(Class<?> clazz) {
		return Arrays.stream(values())
				.filter(val -> val.clazz.equals(clazz))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException(String.format("No target query builder for clazz %s", clazz)));
	}

	@Component
	public static class FilterTargetServiceInjector {
		private final PostgresCrosstabWrapper postgresWrapper;

		@Autowired
		public FilterTargetServiceInjector(PostgresCrosstabWrapper postgresWrapper) {
			this.postgresWrapper = postgresWrapper;
		}

		@PostConstruct
		public void postConstruct() {
			Arrays.stream(FilterTarget.values()).forEach(filterTarget -> filterTarget.setPostgresWrapper(postgresWrapper));
		}
	}

}
