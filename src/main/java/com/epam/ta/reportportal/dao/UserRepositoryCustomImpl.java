package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BinaryData;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer;
import com.epam.ta.reportportal.dao.util.JsonbConverter;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.user.UserType;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.filesystem.DataStore;
import com.epam.ta.reportportal.jooq.tables.JUsers;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.image.ImageParser;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.commons.validation.Suppliers.formattedSupplier;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.jooq.tables.JUsers.USERS;
import static java.util.Optional.ofNullable;

/**
 * @author Pavel Bortnik
 */
@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	public static final RecordMapper<? super Record, User> USER_RECORD_MAPPER = r -> {
		User user = new User();
		Project defaultProject = new Project();
		Map<String, String> metaData = r.get(fieldName(USERS.METADATA), new JsonbConverter());
		user.setMetadata(metaData);

		r = r.into(USERS.ID,
				USERS.LOGIN,
				USERS.PASSWORD,
				USERS.EMAIL,
				USERS.EXPIRED,
				USERS.FULL_NAME,
				USERS.DEFAULT_PROJECT_ID,
				USERS.ATTACHMENT,
				USERS.ATTACHMENT_THUMBNAIL,
				USERS.TYPE,
				USERS.ROLE
		);
		defaultProject.setId(r.get(USERS.DEFAULT_PROJECT_ID));
		user.setId(r.get(USERS.ID));
		user.setAttachment(r.get(USERS.ATTACHMENT));
		user.setAttachmentThumbnail(r.get(USERS.ATTACHMENT_THUMBNAIL));
		user.setDefaultProject(defaultProject);
		user.setEmail(r.get(USERS.EMAIL));
		user.setExpired(r.get(USERS.EXPIRED));
		user.setFullName(r.get(USERS.FULL_NAME));
		user.setLogin(r.get(USERS.LOGIN));
		user.setPassword(r.get(USERS.PASSWORD));
		user.setRole(UserRole.findByName(r.get(USERS.ROLE)).orElseThrow(() -> new ReportPortalException(ErrorType.ROLE_NOT_FOUND)));
		user.setUserType(UserType.findByName(r.get(USERS.TYPE))
				.orElseThrow(() -> new ReportPortalException(ErrorType.INCORRECT_AUTHENTICATION_TYPE)));
		return user;
	};

	public static final Function<Result<? extends Record>, List<User>> USER_FETCHER = result -> {
		Map<Long, User> userMap = Maps.newHashMap();
		result.forEach(res -> {
			Long userId = res.get(USERS.ID);
			if (!userMap.containsKey(userId)) {
				userMap.put(userId, USER_RECORD_MAPPER.map(res));
			}
		});

		return Lists.newArrayList(userMap.values());
	};

	private final DataStore dataStore;

	private final DSLContext dsl;

	@Autowired
	public UserRepositoryCustomImpl(DataStore dataStore, DSLContext dsl) {
		this.dataStore = dataStore;
		this.dsl = dsl;
	}

	@Override
	public String uploadUserPhoto(String login, BinaryData binaryData) {
		return dataStore.save(login, binaryData.getInputStream());
	}

	@Override
	public String replaceUserPhoto(String login, BinaryData binaryData) {
		return dataStore.save(login, binaryData.getInputStream());
	}

	@Override
	public String replaceUserPhoto(User user, BinaryData binaryData) {
		return dataStore.save(user.getLogin(), binaryData.getInputStream());
	}

	@Override
	public BinaryData findUserPhoto(User user) {
		String path = ofNullable(user.getAttachment()).orElseThrow(() -> new ReportPortalException(ErrorType.BAD_REQUEST_ERROR,
				formattedSupplier("User - '{}' does not have a photo.", user.getLogin())
		));
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
	public void deleteUserPhoto(User user) {
		String path = ofNullable(user.getAttachment()).orElseThrow(() -> new ReportPortalException(ErrorType.BAD_REQUEST_ERROR,
				formattedSupplier("User - '{}' does not have a photo.", user.getLogin())
		));
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
