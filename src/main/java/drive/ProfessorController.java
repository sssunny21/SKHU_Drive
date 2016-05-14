package drive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class ProfessorController {

	@Autowired ProfessorMapper professorMapper;
	@Autowired DepartmentMapper departmentMapper;
	@Autowired ProfessorService professorService;

	@RequestMapping(value="/home/p_join.pd")
	public String p_join(Professor professor, Model model) throws Exception{
		String message = professorService.validateBeforeInsert(professor);
		if (message == null){
			professorMapper.insertP(professor);
			model.addAttribute("successMsg", "저장했습니다.");
		}else{
			model.addAttribute("errorMsg", message);
		}
		return "home/p_join";
	}

	@RequestMapping("/professor/p_page.pd")
	public String mypage(Model model) {
		return "professor/p_page";
	}




	@RequestMapping(value="/professor/p_info_edit.pd", method=RequestMethod.GET)
	public String p_info_edit(Model model) {
		model.addAttribute("professor", ProfessorService.getCurrentProfessor());
		model.addAttribute("department", departmentMapper.selectAll());
		return "professor/p_info_edit";
	}

	@RequestMapping(value="/professor/p_info_edit.pd", method=RequestMethod.POST)
	public String mp_info_edit(Professor professor, Model model) throws Exception {
		professor.setId2(ProfessorService.getCurrentProfessor().getId2());
		String message = professorService.validateBeforeUpdate(professor);
		if (message == null) {
			professorMapper.professor_update(professor);
			ProfessorService.setCurrentProfessor(professor);
			model.addAttribute("successMsg", "저장했습니다.");
		} else{
			model.addAttribute("errorMsg", message);
		}
		model.addAttribute("professor", ProfessorService.getCurrentProfessor());
		model.addAttribute("department", departmentMapper.selectAll());

		return "professor/p_info_edit";
	}

}
