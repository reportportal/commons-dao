package com.epam.ta.reportportal;

import com.epam.ta.reportportal.database.dao.LaunchRepository;
import com.epam.ta.reportportal.database.entity.launch.Launch;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.epam.ta.reportportal.jooq.tables.Launch.LAUNCH;

/**
 * @author Pavel Bortnik
 */
@RestController
@RequestMapping("/api")
public class FastController {

	@Autowired
	private LaunchRepository launchRepository;

	@Autowired
	private DSLContext dslContext;

	@GetMapping("/launches")
	public List<Launch> getLaunches() {
		return launchRepository.findAll();
	}
	
	@GetMapping("/jooq")
	public List<Launch> getLaunches2() {
		return dslContext.select().from(LAUNCH).fetchInto(Launch.class);
	}

}
