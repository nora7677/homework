package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	User findById(Integer id);

	// nativeQuery表示该条语句是原生sql语句；
	@Query(value = "SELECT * FROM users WHERE sex = ?1 AND flag=1", nativeQuery = true)
	List<User> findBySex(int sex);

	/**
	 * Hibernate: update users set id=? where id=?
	 * 
	 * @param user
	 * @param id
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("UPDATE FROM User u SET u=:user WHERE u.id=:id")
	int updateById(@Param("user") User user, @Param("id") int id);

	@Transactional
	@Modifying
	@Query("UPDATE FROM User u SET u.flag=:flag WHERE u.id=:id")
	int deleteById(@Param("flag") int flag, @Param("id") int id);

}
