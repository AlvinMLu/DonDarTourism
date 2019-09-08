package com.cy;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysLogDaoTests {
	@Autowired
	private SysLogDao sysLogDao;
	
	@Test
	public void testGetRowCount() {
	  int rowCount=
	  sysLogDao.getRowCount("admin");
	  System.out.println(rowCount);
	}
	
	@Test
	public void testFindPageObjects() {
		List<SysLog> list=
		sysLogDao.findPageObjects(
		"admin",0, 3);
		System.out.println(list.size());
	}
	
	
	@Test
	public void testDeleteObject() {
		int rows=sysLogDao.deleteObject(16);
		System.out.println(rows);
	}
	@Test
	public void testDeleteObjects() {
		int rows=
		sysLogDao.deleteObjects(17,18);
		System.out.println(rows);
	}
}





