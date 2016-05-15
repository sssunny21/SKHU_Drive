package drive;

import java.util.List;

public interface DriveMapper {
	List<Drive> selectDriveInfo(int id2);
	List<Folder> selectFolderAll();
	Folder selectBydr_id(int dr_id);
	List<Folder> selectBydr_id1(int drive_id);
	List<Folder> selectBydr_id2(int drive_id);
	List<Folder> selectBypr_id (int parent_id);
	List<Files> selectByf_id(int folder_id);
	Files selectByff_id(int files_id);
	void insert_folder(Folder folder);
	void insert_sfolder(Folder folder);
	void insert_files(Files files);
	void delete(int f_id);
	
	
	void insert_favorites(int id, int[] folder_id);
	
	//파일
	int selectCount();
	List<Drive> selectPage(Pagination pagination);
	
	//폴더
	int selectCount2();
	List<Drive> selectPage2(Pagination pagination);
}
