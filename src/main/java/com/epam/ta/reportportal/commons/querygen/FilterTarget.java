package com.epam.ta.reportportal.commons.querygen;

import com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant;
import com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant;
import com.epam.ta.reportportal.dao.PostgresWrapper;
import com.epam.ta.reportportal.entity.Activity;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.*;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.NAME;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.PROJECT_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.IntegrationCriteriaConstant.TYPE;
import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.*;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.max;

public enum FilterTarget {

	LAUNCH(Launch.class, Arrays.asList(
			//@formatter:off
			new CriteriaHolder("id", "l.id", Long.class, false),
			new CriteriaHolder(DESCRIPTION, "l.description", String.class, false),
			new CriteriaHolder(PROJECT_ID, "project_id", Long.class, false),
			new CriteriaHolder(STATUS, "status", JStatusEnum.class, false),
			new CriteriaHolder(MODE, "mode", JLaunchModeEnum.class, false),
			new CriteriaHolder(NAME, "name", String.class, false)
			//@formatter:on
	)) {
		public SelectQuery<? extends Record> getQuery() {
			JLaunch l = JLaunch.LAUNCH;
			JStatistics s = JStatistics.STATISTICS;

			Select<?> fieldsForSelect = DSL.select(l.ID,
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

			Select<?> crossTabValues = DSL.selectDistinct(s.S_FIELD) //these are is known to be distinct
					.from(s).orderBy(s.S_FIELD);

			return getPostgresWrapper().pivot(fieldsForSelect, raw, crossTabValues)
					.join(l)
					.on(field(DSL.name("launch_id")).eq(l.ID))
					.getQuery();
			//			return DSL.select(l.ID,
			//					l.UUID,
			//					l.PROJECT_ID,
			//					l.USER_ID,
			//					l.NAME.as("launch_name"),
			//					l.DESCRIPTION,
			//					l.START_TIME,
			//					l.END_TIME,
			//					l.NUMBER,
			//					l.LAST_MODIFIED,
			//					l.MODE,
			//					l.STATUS,
			//					s.LAUNCH_ID.as("s_launch_id"),
			//					s.S_ID,
			//					s.S_FIELD,
			//					s.S_COUNTER,
			//					s.ITEM_ID.as("s_item_id"),
			//					p.NAME
			//			)
			//					.from(l)
			//					.join(s)
			//					.on(l.ID.eq(s.LAUNCH_ID))
			//					.join(p)
			//					.on(l.PROJECT_ID.eq(p.ID))
			//					.getQuery();
		}
	},

	ACTIVITY(Activity.class,
			Arrays.asList(new CriteriaHolder("id", "a.id", Long.class, false),
					new CriteriaHolder(PROJECT_ID, "a.project_id", Long.class, false)
			)
	) {
		@Override
		public SelectQuery<? extends Record> getQuery() {
			JActivity a = JActivity.ACTIVITY.as("a");
			return DSL.select(a.ID, a.PROJECT_ID, a.USER_ID).from(a).getQuery();
		}
	},

	TEST_ITEM(TestItem.class, Arrays.asList(new CriteriaHolder(NAME, "ti.name", String.class, false))) {
		@Override
		public SelectQuery<? extends Record> getQuery() {

			JTestItem ti = JTestItem.TEST_ITEM.as("ti");
			JTestItemStructure tis = JTestItemStructure.TEST_ITEM_STRUCTURE.as("tis");
			JTestItemResults tir = JTestItemResults.TEST_ITEM_RESULTS.as("tir");
			JStatistics s = JStatistics.STATISTICS.as("s");

			Select<?> raw = DSL.select(s.ITEM_ID, s.S_FIELD, max(s.S_COUNTER))
					.from(s)
					.groupBy(s.ITEM_ID, s.S_FIELD)
					.orderBy(s.ITEM_ID, s.S_FIELD);
			Select<?> crossTabValues = DSL.selectDistinct(s.S_FIELD).from(s).orderBy(s.S_FIELD);

			return getPostgresWrapper().pivot(DSL.select(), raw, crossTabValues)
					.join(tis)
					.on(field(DSL.name("item_id")).eq(tis.STRUCTURE_ID))
					.join(ti)
					.on(tis.STRUCTURE_ID.eq(ti.ITEM_ID))
					.join(tir)
					.on(tis.STRUCTURE_ID.eq(tir.RESULT_ID))
					.getQuery();

			//			return DSL.select()
			//					.from(TEST_ITEM_STRUCTURE)
			//					.join(Tables.TEST_ITEM)
			//					.on(TEST_ITEM_STRUCTURE.STRUCTURE_ID.eq(Tables.TEST_ITEM.ITEM_ID))
			//					.join(TEST_ITEM_RESULTS)
			//					.on(TEST_ITEM_STRUCTURE.STRUCTURE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
			//					.join(EXECUTION_STATISTICS)
			//					.on(TEST_ITEM_RESULTS.RESULT_ID.eq(EXECUTION_STATISTICS.ITEM_ID))
			//					.join(ISSUE_STATISTICS)
			//					.on(TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE_STATISTICS.ITEM_ID))
			//					.join(ISSUE_TYPE)
			//					.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
			//					.join(ISSUE_GROUP)
			//					.on(ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID))
			//					.join(ISSUE)
			//					.on(TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID))
			//					.getQuery();
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

			return DSL.select(l.ID,
					l.LOG_TIME,
					l.LOG_MESSAGE,
					l.LAST_MODIFIED,
					l.LOG_LEVEL,
					l.ITEM_ID,
					l.FILE_PATH,
					l.THUMBNAIL_FILE_PATH,
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
	private PostgresWrapper postgresWrapper;

	FilterTarget(Class<?> clazz, List<CriteriaHolder> criterias) {
		this.clazz = clazz;
		this.criterias = criterias;
	}

	public abstract SelectQuery<? extends Record> getQuery();

	public void setPostgresWrapper(PostgresWrapper postgresWrapper) {
		this.postgresWrapper = postgresWrapper;
	}

	public PostgresWrapper getPostgresWrapper() {
		return postgresWrapper;
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
		private final PostgresWrapper postgresWrapper;

		@Autowired
		public FilterTargetServiceInjector(PostgresWrapper postgresWrapper) {
			this.postgresWrapper = postgresWrapper;
		}

		@PostConstruct
		public void postConstruct() {
			for (FilterTarget filterTarget : EnumSet.allOf(FilterTarget.class)) {
				filterTarget.setPostgresWrapper(postgresWrapper);
			}
		}
	}

}
