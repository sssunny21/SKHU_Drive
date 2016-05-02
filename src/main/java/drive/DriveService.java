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
	
	public String validateBeforeInsert(Folder folder) throws Exception {
        String s = folder.getFolder_name();
        if (StringUtils.isBlank(s))
            return "폴더 이름을 입력하세요.";

        return null;
    }
}
