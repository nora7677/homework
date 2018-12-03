package cn.com.taiji.helloJPAM;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.com.taiji.helloJPAM.pojo.Address;
import cn.com.taiji.helloJPAM.pojo.Person;

/**
 * Unit test for simple App.
 */
public class AppTest {

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
	 * persist和merge的区别：当实体类中id有主键生成策略时，persist中不允许有id；
	 * merge的作用：数据库中不存在该条数据时，insert；存在时，update
	 */
	@Test
	public void test() {
		// 4. 持久化操作
		for (int i = 2; i <= 10; i++) {
			Person p = new Person();
			p.setId(i);
			p.setName("Tom_" + i);
			Address addr = new Address();
			addr.setId(i);
			addr.setInfo("Chaoyang_" + i + ",Beijing_" + i);
			p.setAddress(addr);

			// 添加user到数据库，相当于hibernate的save();
			entityManager.merge(p);

		}
	}

	/**
	 * find和getReference的区别：find不支持延迟加载
	 */
	@Test
	public void testDelete() {
		Person p = entityManager.find(Person.class, 9);
		System.out.println(p);
		entityManager.remove(p);
	}

}
