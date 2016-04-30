package drive;

import java.util.List;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DriveController {
	@Autowired DriveMapper driveMapper;

	@RequestMapping(value="/pdrive/main.pd" ,method = RequestMethod.GET)
	public String main(@RequestParam("id2") int id2,Model model) {
		List<Drive> drive = driveMapper.selectDriveInfo(id2);
		model.addAttribute("main", drive);
		return "pdrive/main";
	}
	
	@RequestMapping(value="/pdrive/folderList.pd" ,method = RequestMethod.GET)
	public String folderlist(@RequestParam("dr_id") int dr_id,Model model) {
		List<Drive> dr1 = driveMapper.selectBydr_id1(dr_id);
		List<Drive> dr2 = driveMapper.selectBydr_id2(dr_id);
		model.addAttribute("dr1", dr1);
		model.addAttribute("dr2", dr2);
		return "pdrive/folderList";
	}
	
	@RequestMapping(value="/pdrive/folderList2.pd" ,method = RequestMethod.GET)
	public String folderlist2(@RequestParam("pr_id") int pr_id,Model model) {
		List<Drive> pr = driveMapper.selectBypr_id(pr_id);
		List<Drive> all = driveMapper.selectFolderAll();
		model.addAttribute("pr", pr);
		model.addAttribute("all",all);
		return "pdrive/folderList2";
	}
	
	@RequestMapping(value="/pdrive/fileList.pd" ,method = RequestMethod.GET)
	public String filelist(@RequestParam("fd_id") int fd_id,Model model) {
		List<Drive> fd = driveMapper.selectByf_id(fd_id);
		model.addAttribute("fd", fd);
		return "pdrive/fileList";
	}
	/**
	@RequestMapping(value = "/pdrive/fileUpload.pd", method = RequestMethod.POST)
	public String upload(@RequestParam("drive") MultipartFile uploadedFile) throws IOException {
		if (uploadedFile.getSize() > 0 ) {
			Drive drive = new Drive();
			drive.setF_name(Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString());
			drive.setF_size((int)uploadedFile.getSize());
			drive.setData(uploadedFile.getBytes());
			driveMapper.insert(drive);
		}
		return "redirect:/pdrive/main.pd";
	}

	@RequestMapping("/pdrive/fileDelete.pd")
	public String delete(@RequestParam("id") int id) {
		driveMapper.delete(id);
		return "redirect:/pdrive/main.pd";
	}

	@RequestMapping("/pdrive/fileDownload.pd")
	public void download(@RequestParam("fd_id") int fd_id, HttpServletResponse response) throws IOException {
		List<Drive> drive = driveMapper.selectById(fd_id);
		String f_name = URLEncoder.encode(((Drive) drive).getF_name(),"UTF-8");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;f_name=" + f_name + ";");
		try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
			output.write(((Drive) drive).getData());
		}
	}
	**/

}
