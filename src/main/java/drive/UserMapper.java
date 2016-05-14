package drive;

import java.util.List;


public interface UserMapper {
	User selectById(int id);
	User selectByU_id(String p_id);
	List<User> selectAll();
	List<User> selectPage(Pagination pagination);
	int selectCount(Pagination pagination);
	void insert(User user);

	void update(User user);
	void updatePW(User user);
	
	void user_update(User user);
	void delete(int id);
	List<Drive> selectProfessor(int d_id); //학생의 학과를 불러오기위해 유저멤퍼에 저장.
}
