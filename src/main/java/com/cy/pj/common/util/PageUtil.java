package com.cy.pj.common.util;
import java.util.List;

import com.cy.pj.common.vo.PageObject;

public class PageUtil {
	private static int pageSize=3;
	public static int getPageSize() {
		return pageSize;
	}
	public static int getStartIndex(Integer pageCurrent) {
		return (pageCurrent-1)*pageSize;
	}
	//alt+shift+m
	public static <T>PageObject<T> 
	 newPageObject(Integer pageCurrent, int rowCount,
	 int pageSize, List<T> records) {
		PageObject<T> po=new PageObject<>();
		po.setRowCount(rowCount);
		po.setRecords(records);
		po.setPageSize(pageSize);
		po.setPageCount((rowCount-1)/pageSize+1);
		po.setPageCurrent(pageCurrent);
		return po;
	}
}
