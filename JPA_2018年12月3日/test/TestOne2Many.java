package cn.com.taiji.helloJPAM;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.com.taiji.helloJPAM.pojo.Clazz;
import cn.com.taiji.helloJPAM.pojo.Student;

public class TestOne2Many {
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

	@Test
	public void testInsert() {
		for (int i = 1; i <= 3; i++) {
			Clazz clazz = new Clazz();
			clazz.setName("3-" + i);
			List<Student> stuList = new ArrayList<>();
			for (int j = 1; j <= 3; j++) {
				Student stu = new Student();
				stu.setName("stu_" + j);
				stuList.add(stu);
				entityManager.persist(stu);
			}
			clazz.setStuList(stuList);
			entityManager.persist(clazz);
		}
	}

	@Test
	public void testDel() {
		Clazz clazz = entityManager.find(Clazz.class, 9);
		System.out.println(clazz);
		entityManager.remove(clazz);
	}

}
