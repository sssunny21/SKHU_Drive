package drive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DriveControllerJSON {
	@Autowired
	DriveMapper driveMapper;
	@Autowired
	DriveService driveService;

	@RequestMapping(value = "/pdrive/mainJSON.pd", method = RequestMethod.GET)
	public @ResponseBody List<Drive> main(@RequestParam("id2") int id2) {
		List<Drive> drive = driveMapper.selectDriveInfo(id2);
		return drive;
	}

	@RequestMapping(value = "/pdrive/folderListJSON.pd", method = RequestMethod.GET)
	public @ResponseBody List<Folder> folderlist(@RequestParam("dr_id") int dr_id) {
		List<Folder> dr1 = driveMapper.selectBydr_id1(dr_id);
		List<Folder> dr2 = driveMapper.selectBydr_id2(dr_id);
		
		//dr1 dr2가 어떻게 그려짐 ?
		return dr1;
	}

	@RequestMapping(value = "/pdrive/folderList2JSON.pd", method = RequestMethod.GET)
	public @ResponseBody List<Drive> folderlist2(@RequestParam("parent_id") int parent_id) {
		List<Drive> pr = driveMapper.selectBypr_id(parent_id);
		List<Drive> all = driveMapper.selectFolderAll();
		
		//pr1 all가 어떻게 그려짐 ?
		return pr;
	}

	@RequestMapping(value = "/pdrive/fileListJSON.pd", method = RequestMethod.GET)
	public @ResponseBody List<Drive> filelist(@RequestParam("fd_id") int fd_id) {
		List<Drive> fd = driveMapper.selectByf_id(fd_id);
		return fd;
	}

	

}
