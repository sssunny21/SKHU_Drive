package drive;

import java.util.List;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class DriveController {
	@Autowired DriveMapper driveMapper;
	@Autowired DriveService driveService;
	@Autowired UserService userService;

	@RequestMapping(value="/pdrive/main.pd" ,method = RequestMethod.GET)
	public String main(@RequestParam("d_id") int d_id,Model model) {
		List<Drive> drive = driveMapper.selectDriveInfo(d_id);
		model.addAttribute("main", drive);
		return "pdrive/main";
	}

	@RequestMapping(value="/pdrive/folderList.pd" ,method = RequestMethod.GET)
	public String folderlist(@RequestParam("dr_id") int dr_id,Model model) {
		List<Folder> dr1 = driveMapper.selectBydr_id1(dr_id);
		List<Folder> dr2 = driveMapper.selectBydr_id2(dr_id);
		model.addAttribute("dr1", dr1);
		model.addAttribute("dr2", dr2);
		return "pdrive/folderList";
	}

	@RequestMapping(value="/pdrive/folderList.pd",method = RequestMethod.POST,params="cmd=saveFavorites")
	public String saveFavorites(@RequestParam("dr_id") int dr_id,@RequestParam("folder_id") int[] folder_id,Model model) {
		User u = (User)userService.getCurrentUser();
		for(int i=0 ; i<folder_id.length; ++i){
			driveMapper.insert_favorites(u.id,folder_id[i]);
		}
		System.out.println(folder_id);

		List<Folder> dr1 = driveMapper.selectBydr_id1(dr_id);
		List<Folder> dr2 = driveMapper.selectBydr_id2(dr_id);
		model.addAttribute("dr1", dr1);
		model.addAttribute("dr2", dr2);
		return "pdrive/folderList";
	}

	@RequestMapping(value="/pdrive/folderList2.pd" ,method = RequestMethod.GET)
	public String folderlist2(@RequestParam(value="fd_id",required=false) int fd_id,Model model) {
		List<Folder> pr = driveMapper.selectBypr_id(fd_id);
		List<Folder> all = driveMapper.selectFolderAll();
		model.addAttribute("pr", pr);
		model.addAttribute("all",all);
		
		Folder folder = new Folder();
		folder.setFolder_id(fd_id);
		System.out.println("폴더아이디--");
		System.out.println(folder.getFolder_id());
		model.addAttribute("folder",folder);
		
		List<Files> fd = driveMapper.selectByf_id(fd_id);
		model.addAttribute("fd", fd);
		return "pdrive/folderList2";
	}
	
	@RequestMapping(value="/pdrive/folderList2.pd" ,method = RequestMethod.POST)
	public String folderlist2(@RequestParam(value="fd_id",required=false) int fd_id,HttpServletRequest request,@RequestParam("file") MultipartFile uploadedFile,Model model)throws IOException {
		Files files = new Files();
		files.setFolder_id(fd_id);
		files.setFiles_name(Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString());
		files.setFiles_size((int)uploadedFile.getSize());
		//files.setFiles_size(folder.getFolder_id());
		files.setData(uploadedFile.getBytes());
		files.setFiles_body(uploadedFile.getBytes());
		driveMapper.insert_files(files);

		return "pdrive/folderList2";
	}
/**
	@RequestMapping(value="/pdrive/fileUpload.pd")
	public String upload(HttpServletRequest request,@RequestParam("files") MultipartFile uploadedFile, Model model)throws IOException {
		System.out.println("request----");
		System.out.println(request.getParameter("fd_id"));
		if(uploadedFile.getSize() > 0) {
			Files files = new Files();
			files.setFolder_id(1);
			files.setFiles_name(Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString());
			files.setFiles_size((int)uploadedFile.getSize());
			files.setData(uploadedFile.getBytes());
			driveMapper.insert_files(files);
		}
		return "redirect:/pdrive/folderList2.pd";
	}


   @RequestMapping(value="/pdrive/fileList.pd" ,method = RequestMethod.GET)
   public String filelist(@RequestParam("fd_id") int fd_id,Model model) {
      List<Drive> fd = driveMapper.selectByf_id(fd_id);
      model.addAttribute("fd", fd);
      return "pdrive/fileList";
   }
	 **/
	@RequestMapping(value="/popup/createFolder.pd",method = RequestMethod.GET)
	public String createFolder(@RequestParam("dr_id") Integer dr_id,Model model){
		Folder folder = driveMapper.selectBydr_id(dr_id);
		model.addAttribute("folder", folder);
		return "popup/createFolder";
	}

	@RequestMapping(value="/popup/createFolder.pd",method = RequestMethod.POST)
	public String createFolder(Folder folder,Model model) throws Exception {
		String message = driveService.validatepw(folder);
		if (message == null){
			driveMapper.insert_sfolder(folder);
			model.addAttribute("successMsg", "저장했습니다.");
		}else{
			driveMapper.insert_folder(folder);
			model.addAttribute("successMsg", "저장했습니다.");
		}
		return "popup/createFolder";
	}
	@RequestMapping(value="/popup/createFile.pd",method = RequestMethod.GET)
	public String createFile(@RequestParam("dr_id") Integer dr_id,Model model){
		model.addAttribute("folder", driveMapper.selectBydr_id1(dr_id));
		return "popup/createFile";
	}

	@RequestMapping(value="/popup/createFile.pd",method = RequestMethod.POST)
	public String createFile(Folder folder,@RequestParam("file") MultipartFile uploadedFile,Model model)throws Exception{
		Files files = new Files();
		files.setFolder_id(1);
		files.setFiles_name(Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString());
		files.setFiles_size((int)uploadedFile.getSize());
		files.setData(uploadedFile.getBytes());
		files.setFiles_body(uploadedFile.getBytes());
		driveMapper.insert_files(files);
		return "popup/createFile";
	}
	/**
	@RequestMapping(value = "/pdrive/fileUpload.pd", method = RequestMethod.POST)
	public String upload(@RequestParam("files") MultipartFile uploadedFile) throws IOException {
		if (uploadedFile.getSize() > 0 ) {
			Files files = new Files();
			files.setFiles_name(Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString());
			files.setFiles_size((int)uploadedFile.getSize());
			files.setData(uploadedFile.getBytes());
			files.setFolder_id(files.folder_id);
			driveMapper.insert_files(files);
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
    @RequestMapping("/pdrive/download.pd")
    public void download(@RequestParam("id") int id, HttpServletResponse response) throws IOException { 
       Files files = driveMapper.selectByff_id(id);
       if (files == null) return; 
       String fileName = URLEncoder.encode(files.getFiles_name(),"UTF-8"); 
       response.setContentType("application/octet-stream"); 
       response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";"); 
       try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) { 
          output.write(files.getFiles_body()); 
       } 
    }

}