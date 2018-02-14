package com.epam.ta.reportportal;

import com.epam.ta.reportportal.database.dao.LaunchRepository;
import com.epam.ta.reportportal.database.dao.TestItemRepository;
import com.epam.ta.reportportal.database.entity.launch.Launch;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	private TestItemRepository itemRepository;

	@Autowired
	private DSLContext dslContext;

	@GetMapping("/launches")
	public Launch getLaunches() {
		return launchRepository.findById(1L).orElse(null);
	}

	@GetMapping("/jooq")
	public List<Launch> getLaunches2() {
		return dslContext.select().from(LAUNCH).fetchInto(Launch.class);
	}

	@GetMapping("/projects/{launchId}")
	public boolean bal(@PathVariable Long launchId) {
		return launchRepository.hasItems(launchId);
	}

}
