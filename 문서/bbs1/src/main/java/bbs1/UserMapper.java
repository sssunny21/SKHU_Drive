package bbs1;

import java.util.List;

public interface UserMapper {
	User selectById(int id);
	User selectByLoginID(String loginId);
	List<User> selectAll();
	void insert(User user);
	void update(User user);
	void delete(int id);
}
