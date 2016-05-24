package drive;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DriveService {
	@Autowired
	DriveMapper driveMapper;
	
	public String validatepw(Folder folder) throws Exception {
        String s = folder.getSfolder_pw();
        if (StringUtils.isBlank(s))
            return "비밀번호를 입력해주세요.";

        return null;
    }
	
	public boolean isAuthor(Drive drive){
		return(UserService.getCurrentUser()!=null)&&
				(drive.getId()==UserService.getCurrentUser().getId());
	}
	
	public boolean canCreate(int sfolder_id){
		User user = UserService.getCurrentUser();
		if(user == null) return false;
		if("2".equals(user.getU_auth()))return true;
		return false;
	}
	public boolean canEdit(Drive drive) {
        return isAuthor(drive);
    }

    public boolean canDelete(Drive drive) {
        User user = UserService.getCurrentUser();
        if (user == null) return false;
        if ("2".equals(user.getU_auth())) return true;
        return isAuthor(drive);
    }


}
