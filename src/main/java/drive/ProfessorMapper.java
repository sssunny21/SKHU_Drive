package drive;

import java.util.List;

public interface ProfessorMapper {

	Professor selectByPId(int id2);
	Professor selectByP_id(String p_id);
	List<Professor> selectAll();
	void insertP(Professor professor);
	void updateP(Professor professor);
	void professor_update(Professor professor);
	void deleteP(int id2);
	List<Professor> selectPage1(Pagination pagination);
	int selectCount1(Pagination pagination);
}                                                                                                                                                                                  
