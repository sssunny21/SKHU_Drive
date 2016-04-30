package drive;

import java.util.List;

public interface DriveMapper {
	List<Drive> selectDriveInfo(int id2);
	List<Drive> selectFolderAll();
	List<Drive> selectBydr_id1(int drive_id);
	List<Drive> selectBydr_id2(int drive_id);
	List<Drive> selectBypr_id (int parent_id);
	List<Drive> selectByf_id(int folder_id);
	int insert_folder(Drive drive);
	int insert_files(Drive drive);
	void delete(int f_id);
}
