package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.materialized.StaleMaterializedView;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

import static com.epam.ta.reportportal.jooq.tables.JStaleMaterializedView.STALE_MATERIALIZED_VIEW;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Repository
public class StaleMaterializedViewRepositoryImpl implements StaleMaterializedViewRepository {

	private final DSLContext dsl;

	@Autowired
	public StaleMaterializedViewRepositoryImpl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public Optional<StaleMaterializedView> findById(Long id) {
		return dsl.select()
				.from(STALE_MATERIALIZED_VIEW)
				.where(STALE_MATERIALIZED_VIEW.ID.eq(id))
				.fetchOptionalInto(StaleMaterializedView.class);
	}

	@Override
	public StaleMaterializedView insert(StaleMaterializedView view) {
		Long id = dsl.insertInto(STALE_MATERIALIZED_VIEW)
				.columns(STALE_MATERIALIZED_VIEW.NAME, STALE_MATERIALIZED_VIEW.CREATION_DATE)
				.values(view.getName(), Timestamp.valueOf(view.getCreationDate()))
				.returningResult(STALE_MATERIALIZED_VIEW.ID)
				.fetchOne()
				.into(Long.class);
		view.setId(id);
		return view;
	}
}
