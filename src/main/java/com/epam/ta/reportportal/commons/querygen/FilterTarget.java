package com.epam.ta.reportportal.commons.querygen;

import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.jooq.Tables;
import com.epam.ta.reportportal.jooq.tables.*;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.jooq.Tables.*;

public enum FilterTarget {

	LAUNCH(Launch.class, Arrays.asList(
			//@formatter:off
			new CriteriaHolder("description", "l.description", String.class, false),
			new CriteriaHolder("project_id", "l.project_id", Long.class, false),
			new CriteriaHolder("status", "l.status", StatusEnum.class, false),
			new CriteriaHolder("name", "l.name", String.class, false),
			new CriteriaHolder("project", "p.name", String.class, false),
			new CriteriaHolder("es_status", "es.es_status", String.class, false)
			//@formatter:on
	)) {
		public SelectQuery<? extends Record> getQuery() {
			JLaunch l = JLaunch.LAUNCH.as("l");
			JIssueStatistics is = JIssueStatistics.ISSUE_STATISTICS.as("is");
			JExecutionStatistics es = JExecutionStatistics.EXECUTION_STATISTICS.as("es");
			JIssueType it = JIssueType.ISSUE_TYPE.as("it");
			JIssueGroup ig = JIssueGroup.ISSUE_GROUP.as("ig");
			JProject p = JProject.PROJECT.as("p");

			return DSL.select(l.ID,
					l.UUID,
					l.PROJECT_ID,
					l.USER_ID,
					l.NAME.as("launch_name"),
					l.DESCRIPTION,
					l.START_TIME,
					l.END_TIME,
					l.NUMBER,
					l.LAST_MODIFIED,
					l.MODE,
					l.STATUS,
					es.ES_ID,
					es.ES_COUNTER,
					es.ES_STATUS,
					es.POSITIVE,
					es.ITEM_ID,
					es.LAUNCH_ID.as("es_launch_id"),
					is.IS_ID,
					is.ISSUE_TYPE_ID,
					is.IS_COUNTER,
					is.ITEM_ID,
					is.LAUNCH_ID.as("is_launch_id"),
					it.LOCATOR,
					ig.ISSUE_GROUP_,
					p.NAME
			)
					.from(l)
					.join(es)
					.on(l.ID.eq(es.LAUNCH_ID))
					.join(is)
					.on(l.ID.eq(is.LAUNCH_ID))
					.join(it)
					.on(is.ISSUE_TYPE_ID.eq(it.ID))
					.join(ig)
					.on(it.ISSUE_GROUP_ID.eq(ig.ISSUE_GROUP_ID))
					.join(p)
					.on(l.PROJECT_ID.eq(p.ID))
					.getQuery();
		}
	},

	TEST_ITEM(TestItem.class, Arrays.asList(new CriteriaHolder("name", "ti.name", String.class, false))) {
		@Override
		public SelectQuery<? extends Record> getQuery() {
			return DSL.select()
					.from(TEST_ITEM_STRUCTURE)
					.join(Tables.TEST_ITEM)
					.on(TEST_ITEM_STRUCTURE.STRUCTURE_ID.eq(Tables.TEST_ITEM.ITEM_ID))
					.join(TEST_ITEM_RESULTS)
					.on(TEST_ITEM_STRUCTURE.STRUCTURE_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
					.join(EXECUTION_STATISTICS)
					.on(TEST_ITEM_RESULTS.RESULT_ID.eq(EXECUTION_STATISTICS.ITEM_ID))
					.join(ISSUE_STATISTICS)
					.on(TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE_STATISTICS.ITEM_ID))
					.join(ISSUE_TYPE)
					.on(ISSUE_STATISTICS.ISSUE_TYPE_ID.eq(ISSUE_TYPE.ID))
					.join(ISSUE_GROUP)
					.on(ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID))
					.join(ISSUE)
					.on(TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID))
					.getQuery();
		}
	},

	INTEGRATION(Integration.class, Arrays.asList(
			//@formatter:off
			new CriteriaHolder("project", "p.name", String.class, false),
			new CriteriaHolder("type", "it.name", String.class, false)
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

			new CriteriaHolder("name", "p.name", String.class, false))) {
		public SelectQuery<? extends Record> getQuery() {
			JProject p = JProject.PROJECT.as("p");

			return DSL.select(p.ID, p.NAME).from(p).getQuery();

		}
	},

	LOG(Log.class, Arrays.asList(

			new CriteriaHolder("log_message", "l.log_message", String.class, false))) {
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

	FilterTarget(Class<?> clazz, List<CriteriaHolder> criterias) {
		this.clazz = clazz;
		this.criterias = criterias;
	}

	public abstract SelectQuery<? extends Record> getQuery();

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

}
