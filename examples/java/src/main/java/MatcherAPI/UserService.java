package MatcherAPI;

import java.util.ArrayList;

public class UserService {
 
  // returns a list of all users
  public ArrayList<User> getAllUsers() {
	  User a = new User("1", "a", "a@a.com");
	  User b = new User("1", "b", "b@b.com");
	  User c = new User("1", "c", "c@c.com");
	  ArrayList<User> ret = new ArrayList<User>();
	  ret.add(a);
	  ret.add(b);
	  ret.add(c);
	  return ret;
  }
//   
//  // returns a single user by id
//  public User getUser(String id) { .. }
// 
//  // creates a new user
//  public User createUser(String name, String email) { .. }
// 
//  // updates an existing user
//  public User updateUser(String id, String name, String email) { .. }
}
