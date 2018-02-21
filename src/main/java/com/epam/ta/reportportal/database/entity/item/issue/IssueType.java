package com.epam.ta.reportportal.database.entity.item.issue;

import com.epam.ta.reportportal.database.entity.enums.TestItemIssueType;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "issue_type", schema = "public", indexes = { @Index(name = "issue_type_pk", unique = true, columnList = "id ASC") })
public class IssueType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 32)
	private Integer id;

	@Column(name = "issue_group", nullable = false)
	private TestItemIssueType testItemIssueType;

	@Column(name = "locator", length = 64)
	private String locator;

	@Column(name = "long_name", length = 256)
	private String longName;

	@Column(name = "short_name", length = 64)
	private String shortName;

	@Column(name = "hex_color", length = 7)
	private String hexColor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TestItemIssueType getTestItemIssueType() {
		return testItemIssueType;
	}

	public void setTestItemIssueType(TestItemIssueType testItemIssueType) {
		this.testItemIssueType = testItemIssueType;
	}

	public String getLocator() {
		return locator;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getHexColor() {
		return hexColor;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}

	@Override
	public String toString() {
		return "IssueType{" + "id=" + id + ", testItemIssueType=" + testItemIssueType + ", locator='" + locator + '\'' + ", longName='"
				+ longName + '\'' + ", shortName='" + shortName + '\'' + ", hexColor='" + hexColor + '\'' + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		IssueType issueType = (IssueType) o;
		return Objects.equals(id, issueType.id) && testItemIssueType == issueType.testItemIssueType && Objects.equals(
				locator, issueType.locator) && Objects.equals(longName, issueType.longName) && Objects.equals(
				shortName, issueType.shortName) && Objects.equals(
				hexColor, issueType.hexColor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, testItemIssueType, locator, longName, shortName, hexColor);
	}
}
