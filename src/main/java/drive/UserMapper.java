package drive;

import java.util.List;


public interface UserMapper {
	User selectById(int id);
	User selectByU_id(String p_id);
	List<User> selectAll();
	List<User> selectPage(Pagination pagination);
	int selectCount(Pagination pagination);
	void insert(User user);
	User checkQA(User user);
	void changePW(User user);
	List<Folder> selectMyFolder(int id);//내 즐겨찾기 폴더
	List<Drive> selectMyDrive(int id);//내 즐겨찾기 드라이브

	void update(User user);
	void updatePW(User user);

	void user_update(User user);
	void delete(int id);
	void deleteJoinFolder(int folder_id);//폴더 즐겨찾기 해제
	void deleteJoinDrive(int drive_id);//드라이브 즐겨찾기 해제
	List<Drive> selectProfessor(int d_id); //학생의 학과를 불러오기위해 유저멤퍼에 저장.
}
