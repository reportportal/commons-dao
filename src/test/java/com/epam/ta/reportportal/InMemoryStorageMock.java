/*
 * This file is part of Report Portal.
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.ta.reportportal;

import com.epam.ta.reportportal.database.BinaryData;
import com.epam.ta.reportportal.database.DataStorage;
import com.epam.ta.reportportal.database.Time;
import com.mongodb.gridfs.GridFSDBFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Simple file storage.
 *
 * @author Andrei Varabyeu
 */
class InMemoryStorageMock implements DataStorage {

	private Map<DataKey, BinaryData> inMemoryStorage = new ConcurrentHashMap<>();

	static class DataKey {
		String filename;
		String id;

		public DataKey(String filename, String id) {
			this.filename = filename;
			this.id = id;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			DataKey dataKey = (DataKey) o;
			return Objects.equals(filename, dataKey.filename) && Objects.equals(id, dataKey.id);
		}

		@Override
		public int hashCode() {
			return Objects.hash(filename, id);
		}
	}

	@Override
	public String saveData(BinaryData data, String filename) {
		String id = UUID.randomUUID().toString();
		inMemoryStorage.put(new DataKey(filename, id), data);
		return id;
	}

	@Override
	public BinaryData fetchData(String dataId) {
		return inMemoryStorage.entrySet().stream()
				.filter(e -> dataId.equals(e.getKey().id)).findAny().map(Map.Entry::getValue).orElse(null);
	}

	@Override
	public List<BinaryData> findByFilename(String filename) {
		return inMemoryStorage.entrySet().stream().filter(e -> filename.equals(e.getKey().filename)).map(Map.Entry::getValue)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteData(String dataId) {
		inMemoryStorage.entrySet().stream().filter(e -> dataId.equals(e.getKey().id)).map(Map.Entry::getKey)
				.forEach(k -> inMemoryStorage.remove(k));
	}

	@Override
	public void deleteAll() {
		inMemoryStorage.clear();

	}

	@Override
	public void delete(List<String> ids) {
		inMemoryStorage.entrySet().stream().filter(e -> ids.contains(e.getKey().id)).map(Map.Entry::getKey)
				.forEach(k -> inMemoryStorage.remove(k));
	}

	@Override
	public void deleteByFilename(String filename) {
		inMemoryStorage.entrySet().stream().filter(e -> filename.equals(e.getKey().filename)).map(Map.Entry::getKey)
				.forEach(k -> inMemoryStorage.remove(k));

	}

	@Override
	public List<GridFSDBFile> findModifiedLaterAgo(Time period, String project) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public String saveData(BinaryData binaryData, String filename, Map<String, String> metainfo) {
		return saveData(binaryData, filename);
	}

}
