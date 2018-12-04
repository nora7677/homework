package com.example.demo.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.dao.UserRepository;
import com.example.demo.domain.PageInfo;
import com.example.demo.domain.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository ur;

	public Page<User> search(final User user, PageInfo page) {
		System.out.println();
		return ur.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate userFlag = cb.equal(root.get("flag"), 1);
				query.where(userFlag);

				Predicate userNameLike = null;
				if (null != user && !StringUtils.isEmpty(user.getName())) {
					userNameLike = cb.like(root.<String>get("name"), "%" + user.getName() + "%");
				}

				if (null != userNameLike)
					query.where(userNameLike);
				return null;
			}
		}, new PageRequest(page.getPage() - 1, page.getLimit(), new Sort(Direction.DESC, page.getSortName())));
	}

	@Override
	public List<User> findAll() {
		return ur.findAll();
	}

	@Override
	public List<User> findAllByIds(List<Integer> ids) {
		return ur.findAll(ids);
	}

	@Override
	public User findById(Integer id) {
		return ur.findById(id);
	}

	@Override
	public List<User> findBySex(int sex) {
		return ur.findBySex(sex);
	}

	@Override
	public int updateById(User user, int id) {
		return ur.updateById(user, id);
	}

	@Override
	public int deleteById(int flag, int id) {
		return ur.deleteById(flag, id);
	}

	@Override
	public void insert(User user) {
		ur.saveAndFlush(user);
	}

	@Override
	public void insert(List<User> userList) {
		ur.save(userList);
		ur.flush();
	}

	@Override
	public void updateById(User user) {
		ur.saveAndFlush(user);
	}

	@Override
	public void deleteById(User user) {
		ur.saveAndFlush(user);
	}

}
