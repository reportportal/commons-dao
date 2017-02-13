/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
 * 
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */ 

package com.epam.ta.reportportal.database.dao;

import java.io.Serializable;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.epam.ta.reportportal.database.entity.sharing.Shareable;

/**
 * Factory Bean to add support of custom Mongo Repositories
 * 
 * @author Andrei Varabyeu
 * 
 * @param <R>
 *            - Repository Type
 * @param <ID>
 *            - Entity ID Type
 * @param <T>
 *            - Entity Type
 */
public class ReportPortalRepositoryFactoryBean<R extends MongoRepository<T, ID>, ID extends Serializable, T> extends
		MongoRepositoryFactoryBean<R, T, ID> {

	/**
	 * Creates a new {@link MongoRepositoryFactoryBean} for the given repository interface.
	 *
	 * @param repositoryInterface must not be {@literal null}.
	 */
	public ReportPortalRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	/*
         * (non-Javadoc)
         *
         * @see org.springframework.data.mongodb.repository.support. MongoRepositoryFactoryBean
         * #getFactoryInstance(org.springframework.data.mongodb .core.MongoOperations)
         */
	@Override
	protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
		return new ReportPortalRepositoryFactory<T, Serializable>(operations);
	}

	private static class ReportPortalRepositoryFactory<T, ID extends Serializable> extends MongoRepositoryFactory {

		private MongoOperations mongoOperations;

		public ReportPortalRepositoryFactory(MongoOperations mongoOperations) {
			super(mongoOperations);
			this.mongoOperations = mongoOperations;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.data.mongodb.repository.support. MongoRepositoryFactory
		 * #getTargetRepository(org.springframework.data.repository .core.RepositoryMetadata)
		 */
		@SuppressWarnings({ "unchecked" })
		@Override
		protected Object getTargetRepository(RepositoryInformation repositoryInformation) {
			MongoEntityInformation<T, ID> entityInformation = (MongoEntityInformation<T, ID>) getEntityInformation(
					repositoryInformation.getDomainType());
			if (Shareable.class.isAssignableFrom(entityInformation.getJavaType())) {
				return new ShareableRepositoryImpl<>(entityInformation, mongoOperations);
			} else {
				return new ReportPortalRepositoryImpl<>(entityInformation, mongoOperations);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.data.mongodb.repository.support. MongoRepositoryFactory
		 * #getRepositoryBaseClass(org.springframework.data .repository.core.RepositoryMetadata)
		 */
		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			if (Shareable.class.isAssignableFrom(metadata.getDomainType())) {
				return ShareableRepository.class;
			} else {
				return ReportPortalRepository.class;
			}
		}
	}
}
