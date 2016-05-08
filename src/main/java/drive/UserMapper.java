package drive;

import java.util.List;


public interface UserMapper {
	User selectById(int id);
	User selectByU_id(String u_id);
	List<Drive> selectProfessor(int d_id);
	List<User> selectAll();
	List<User> selectPage(Pagination pagination);
	int selectCount(Pagination pagination);
	void insert(User user);
	void update(User user);
	void user_update(User user);
	void delete(int id);
}
