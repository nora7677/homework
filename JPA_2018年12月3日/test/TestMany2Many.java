package cn.com.taiji.helloJPAM;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.com.taiji.helloJPAM.pojo.Emp;
import cn.com.taiji.helloJPAM.pojo.Job;

public class TestMany2Many {

	EntityManagerFactory factory;
	EntityManager entityManager;
	EntityTransaction transaction;

	@Before
	public void beforeTest() {
		// 1. 创建EntityManagerFactory
		factory = Persistence.createEntityManagerFactory("helloJPAM");

		// 2. 创建EntityManager
		entityManager = factory.createEntityManager();

		// 3.开启事务
		transaction = entityManager.getTransaction();
		transaction.begin();
	}

	@After
	public void afterTest() {
		// 5. 提交事务
		transaction.commit();

		// 6. 关闭EntityManager
		entityManager.close();

		// 7. 关闭EntityManagerFactory
		factory.close();
	}

	/**
	 * 生成表结构
	 */
	@Test
	public void testCreateTables() {

	}

	/**
	 * 插入数据
	 */
	@Test
	public void testInsert() {
		List<Emp> empList = new ArrayList<>();
		List<Job> jobList = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			Emp e = new Emp();
			e.setName("emp_" + i);
			e.setJobs(new ArrayList<Job>());
			empList.add(e);
		}
		for (int i = 1; i <= 3; i++) {
			Job j = new Job();
			j.setName("emp_" + i);
			jobList.add(j);
			entityManager.persist(j);
		}
		for (Emp e : empList) {
			for (Job j : jobList) {
				e.getJobs().add(j);
			}
			entityManager.persist(e);
		}
	}

	@Test
	public void testFind() {
		Emp e = entityManager.find(Emp.class, 2);
		System.out.println(e);
	}

	@Test
	public void testQuery() {
		TypedQuery<Emp> query = entityManager.createQuery("from Emp where id=:empid", Emp.class);
		query.setParameter("empid", 3);
		Emp e = query.getSingleResult();
		System.out.println(e);
	}

}
