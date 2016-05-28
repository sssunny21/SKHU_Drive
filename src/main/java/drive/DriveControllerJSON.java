package drive;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	@Autowired
	UserService userService;

	@RequestMapping(value = "/pdrive/mainJSON.pd", method = RequestMethod.GET)
	public @ResponseBody List<Drive> main(@RequestParam("d_id") int id2) {
		List<Drive> drive = driveMapper.selectDriveInfo(id2);
		return drive;
	}

	@RequestMapping(value = "/pdrive/folderListJSON.pd", method = RequestMethod.GET)
	public @ResponseBody List<Folder> folderlist(@RequestParam("dr_id") int dr_id) {
		List<Folder> dr1 = driveMapper.selectBydr_id1(dr_id);
		// List<Folder> dr2 = driveMapper.selectBydr_id2(dr_id);
		// dr2는 공유폴더 ?? 일단 더해서 넘겨봄.
		// dr1.addAll(dr2);
		// dr1 dr2가 어떻게 그려짐 ?
		return dr1;
	}

	@RequestMapping(value = "/pdrive/folderListJSON.pd", method = RequestMethod.POST, params = "cmd=saveFavorites")
	public @ResponseBody List<Folder> saveFavorites(@RequestParam("dr_id") int dr_id,
			@RequestParam("folder_id") int[] folder_id) {
		User u = (User) userService.getCurrentUser();
		List<Folder> dr1 = driveMapper.selectBydr_id1(dr_id);

		return dr1;
	}

	// 폴더리턴
	@RequestMapping(value = "/pdrive/folderList2JSON.pd", method = RequestMethod.GET)
	public @ResponseBody List<Folder> folderlist2Folder(@RequestParam("fd_id") int fd_id) {
		List<Folder> pr = driveMapper.selectBypr_id(fd_id);

		return pr;
	}

	//파일리턴 
	@RequestMapping(value = "/pdrive/folderList2JSON2.pd", method = RequestMethod.GET)
	public @ResponseBody List<Files> folderlist2File(@RequestParam("fd_id") int fd_id) {
		List<Files> fd = driveMapper.selectByf_id(fd_id);

		return fd;
	}

	//다운로
	@RequestMapping(value = "/pdrive/downloadJSON.pd", method = RequestMethod.GET)
	public @ResponseBody byte[] download(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
		Files files = driveMapper.selectByff_id(id);
		if (files == null)
			return null;
		
		return files.getFiles_body();
	}

}