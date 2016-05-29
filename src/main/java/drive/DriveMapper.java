package drive;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DriveMapper {
	List<Drive> selectDriveInfo(int id2);
	List<Folder> selectFolderAll();
	Folder selectBydr_id(int dr_id);
	Folder selectByfd_id(int fd_id);
	Drive selectDrive(int drive_id);
	List<Folder> selectBydr_id1(int drive_id);
	List<Folder> selectBydr_id2(int drive_id);
	List<Folder> selectBypr_id (int parent_id);
	List<Files> selectByf_id(int folder_id);
	Files selectByff_id(int files_id);
	void insert_folder(Folder folder);
	void insert_folder2(Folder folder);
	void insert_sfolder(Folder folder);
	void insert_files(Files files);
	void deleteFolder(int folder_id);
	void deleteFiles(int files_id);
	void insert_favorites(@Param("id") int id, @Param("folder_id") int folder_id);//폴더 즐겨찾기
	void insert_favorites_drive(@Param("id") int id, @Param("drive_id") int drive_id);//드라이브 즐겨찾기
	
	//파일
	int selectCount();
	List<Drive> selectPage(Pagination pagination);
	
	//폴더
	int selectCount2();
	List<Drive> selectPage2(Pagination pagination);
}
