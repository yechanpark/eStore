package kr.ac.ycp.cse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.ycp.cse.dao.UserDao;
import kr.ac.ycp.cse.model.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public void addUser(User user) {
		userDao.addUser(user);
	}

	public User getUSerById(int userId) {
		return userDao.getUserById(userId);
	}

	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}
}
