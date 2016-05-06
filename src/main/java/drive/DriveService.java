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
}
