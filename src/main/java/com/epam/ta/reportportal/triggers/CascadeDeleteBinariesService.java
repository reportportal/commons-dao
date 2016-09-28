package com.epam.ta.reportportal.triggers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.ta.reportportal.database.DataStorage;

@Service
public class CascadeDeleteBinariesService {

	private DataStorage dataStorage;

	@Autowired
	public CascadeDeleteBinariesService(DataStorage dataStorage) {
		this.dataStorage = dataStorage;
	}

	public void delete(List<String> ids) {
		dataStorage.delete(ids);
	}
}
