package drive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DriveController {
	@Autowired DriveMapper driveMapper;

	@RequestMapping(value="/pdrive/main.pd" ,method = RequestMethod.GET)
	public String main(@RequestParam("id") int id,Model model) {
		List<Drive> drive = driveMapper.selectFileInfo(id);
		model.addAttribute("main", drive);
		return "pdrive/main";
	}

}
