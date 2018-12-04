package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.domain.PageInfo;
import com.example.demo.domain.User;

public interface UserService {

	public void insert(User user);

	public void insert(List<User> userList);

	public List<User> findAll();

	public List<User> findAllByIds(List<Integer> ids);

	public User findById(Integer id);

	public List<User> findBySex(int sex);

	public int updateById(User user, int id);

	public void updateById(User user);

	public int deleteById(int flag, int id);

	public void deleteById(User user);

	public Page<User> search(final User user, PageInfo page);

}
