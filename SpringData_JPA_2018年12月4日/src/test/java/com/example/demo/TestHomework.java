package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.domain.PageInfo;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHomework {

	@Autowired
	UserService us;

	/**
	 * 批量插入数据
	 */
	@Test
	public void testInsert() {
		List<User> userList = new ArrayList<>();
		for (int i = 1; i <= 50; i++) {
			User user = new User();
			user.setName("Tom_" + i);
			user.setAge(12 + i);
			user.setSex(i % 2 == 0 ? 0 : 1);
			user.setFlag(1);
			userList.add(user);
		}
		us.insert(userList);
	}

	/**
	 * findAll
	 */
	@Test
	public void testFindAll() {
		List<User> userList = us.findAll();
		for (User user : userList) {
			System.out.println(user);
		}
	}

	/**
	 * find所有ids
	 * 
	 * @param ids
	 */
	@Test
	public void testFindAllByIds() {
		List<Integer> ids = new ArrayList<>();
		ids.add(120);
		ids.add(131);
		ids.add(135);
		ids.add(150);
		List<User> userList = us.findAllByIds(ids);
		for (User user : userList) {
			System.out.println(user);
		}
	}

	/**
	 * find by id
	 * 
	 * @param id
	 */
	@Test
	public void testFindById() {
		User user = us.findById(147);
		System.out.println(user);
	}

	/**
	 * find by sex
	 * 
	 * @param sex
	 */
	@Test
	public void testFindBySex() {
		List<User> userList = us.findBySex(1);
		for (User user : userList) {
			System.out.println(user);
		}
	}

	/**
	 * update:不行
	 * 
	 * @param user
	 * @param id
	 */
	@Test
	public void testUpdateById() {
		User user = new User();
		user.setName("Jerry");
		user.setId(147);
		user.setAge(21);
		user.setSex(0);
		user.setFlag(1);

		us.updateById(user, user.getId());
	}

	/**
	 * 直接用saveandflush():可行
	 */
	@Test
	public void testUpdateById2() {
		User user = new User();
		user.setName("Jerry");
		user.setId(147);
		user.setAge(21);
		user.setSex(0);
		user.setFlag(1);

		us.updateById(user);
	}

	/**
	 * delete
	 * 
	 * @param flag
	 * @param id
	 */
	@Test
	public void testDeleteById() {
		int id = 147;
		int flag = 0;
		us.deleteById(flag, id);
	}

	/**
	 * 分页
	 */
	@Test
	public void testPages() {
		User user = new User();
		user.setName("T");
		PageInfo page = new PageInfo();
		// 当前页
		page.setPage(1);
		// 页容量
		page.setLimit(10);
		// 排序字段名
		page.setSortName("id");
		Page<User> p = us.search(user, page);
		long total = p.getTotalElements();
		List<User> userList = p.getContent();
		System.out.println("Number=" + total);
		for (User u : userList) {
			System.out.println(u);
		}
	}

}
