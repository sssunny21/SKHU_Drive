package drive;

import java.util.List;

public interface DriveMapper {
	List<Drive> selectDriveInfo(int id2);
	List<Drive> selectFolderAll();
	Folder selectBydr_id(int dr_id);
	List<Folder> selectBydr_id1(int drive_id);
	List<Folder> selectBydr_id2(int drive_id);
	List<Drive> selectBypr_id (int parent_id);
	List<Drive> selectByf_id(int folder_id);
	void insert_folder(Folder folder);
	void insert_sfolder(Folder folder);
	void insert_files(Files files);
	void delete(int f_id);
}
