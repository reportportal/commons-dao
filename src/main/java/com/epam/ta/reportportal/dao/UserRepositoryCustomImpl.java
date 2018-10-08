package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BinaryData;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.filesystem.DataStore;
import com.epam.ta.reportportal.jooq.tables.JUsers;
import com.epam.ta.reportportal.ws.model.ErrorType;
import org.apache.commons.io.IOUtils;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.image.ImageParser;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.dao.util.RecordMappers.USER_FETCHER;
import static com.epam.ta.reportportal.jooq.tables.JUsers.USERS;

/**
 * @author Pavel Bortnik
 */
@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	private final DataStore dataStore;

	private final DSLContext dsl;

	@Autowired
	public UserRepositoryCustomImpl(DataStore dataStore, DSLContext dsl) {
		this.dataStore = dataStore;
		this.dsl = dsl;
	}

	@Override
	public String uploadUserPhoto(String username, BinaryData binaryData) {
		return dataStore.save(username, binaryData.getInputStream());
	}

	@Override
	public String replaceUserPhoto(String username, BinaryData binaryData) {
		return dataStore.save(username, binaryData.getInputStream());
	}

	@Override
	public String replaceUserPhoto(User user, BinaryData binaryData) {
		return dataStore.save(user.getLogin(), binaryData.getInputStream());
	}

	@Override
	public BinaryData findUserPhoto(String path) {
		InputStream inputStream = dataStore.load(path);
		try {
			byte[] bytes = IOUtils.toByteArray(inputStream);
			String contentType = resolveContentType(bytes);

			return new BinaryData(contentType, (long) bytes.length, inputStream);
		} catch (IOException e) {
			//TODO add new exception type
			throw new ReportPortalException(ErrorType.BAD_REQUEST_ERROR);
		}
	}

	@Override
	public void deleteUserPhoto(String path) {
		dataStore.delete(path);
	}

	@Override
	public Page<User> searchForUser(String term, Pageable pageable) {

		SelectConditionStep<Record> select = dsl.select()
				.from(USERS)
				.where(USERS.LOGIN.like("%" + term + "%")
						.or(USERS.FULL_NAME.like("%" + term + "%"))
						.or(USERS.EMAIL.like("%" + term + "%")));
		return PageableExecutionUtils.getPage(USER_FETCHER.apply(select.fetch()), pageable, () -> dsl.fetchCount(select));
	}

	@Override
	public Page<User> findByFilterExcluding(Queryable filter, Pageable pageable, String... exclude) {

		List<Field<?>> fieldsForSelect = JUsers.USERS.fieldStream()
				.map(Field::getName)
				.filter(f -> Arrays.stream(exclude).noneMatch(exf -> exf.equalsIgnoreCase(f)))
				.map(JooqFieldNameTransformer::fieldName)
				.collect(Collectors.toList());

		return PageableExecutionUtils.getPage(
				USER_FETCHER.apply(dsl.select(fieldsForSelect).from(QueryBuilder.newBuilder(filter).with(pageable).build()).fetch()),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build())
		);
	}

	private String resolveContentType(byte[] data) {
		AutoDetectParser parser = new AutoDetectParser(new ImageParser());
		try {
			return parser.getDetector().detect(TikaInputStream.get(data), new Metadata()).toString();
		} catch (IOException e) {
			return MediaType.OCTET_STREAM.toString();
		}
	}

	@Override
	public List<User> findByFilter(Filter filter) {
		return USER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Page<User> findByFilter(Filter filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(USER_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).with(pageable).build())),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build())
		);
	}
}
