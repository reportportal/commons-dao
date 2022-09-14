package com.epam.ta.reportportal.entity.item;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public class NestedItemPage extends NestedItem {

	private Integer pageNumber;

	public NestedItemPage(Long id, String type, Integer pageNumber) {
		super(id, type);
		this.pageNumber = pageNumber;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

}
