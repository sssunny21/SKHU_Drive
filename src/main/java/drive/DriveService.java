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
	
	public String editsFolder(Folder folder) throws Exception {
		
        String s = folder.getSfolder_name();
        if (StringUtils.isBlank(s))
            return "폴더이름을 입력해주세요.";
        
      s = folder.getSfolder_pw();
        if (StringUtils.isBlank(s))
            return "비밀번호를 입력해주세요.";

        return null;
    }
	
	public String editFolder(Folder folder) throws Exception {
        String s = folder.getFolder_name();
        if (StringUtils.isBlank(s))
            return "폴더이름을 입력해주세요.";

        return null;
    }
	
	public String beforePw(Folder folder) throws Exception {
        String s = folder.getSfolder_pw();
        if (StringUtils.isBlank(s))
            return "암호를 입력해주세요.";

        return null;
    }
	
	public boolean eqaulPw(Folder folder,String sfolder_pw){
		if(folder.getSfolder_pw()==sfolder_pw){
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean isAuthor(Drive drive){
		return(UserService.getCurrentUser()!=null)&&
				(drive.getId()==UserService.getCurrentUser().getId());
	}//로그인한 사용자가 드라이브 소유자인지 검사
	
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
