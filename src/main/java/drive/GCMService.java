package drive;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Service
public class GCMService {

	//서버 프로젝트를 Google에 등록할 때 발급받은 API KEY 
	private static final String API_KEY = "AIzaSyAreig7bzPKB4-H8vB-iBHVJgYP-40eG3c";

	//1.스마트폰의 registrationId
	//2.전송할 메시지 
    private String send(String registrationId, Message message) throws IOException {
        Sender sender = new Sender(API_KEY);
        Result result = sender.send(message, registrationId, 5);

        if (result.getMessageId() != null)
            return "Success " + result.getMessageId() + " " +  result.getCanonicalRegistrationId();
        else
            return result.getErrorCodeName();
    }
    
    public void sendMessage(List<User> users,String title, String body) throws IOException{
    	Message.Builder builder = new Message.Builder();
    	builder.addData("title", title);
    	builder.addData("body", body);
    	
    	Message message = builder.build();
    	
    	//Users와 title, body를 받아와서 그대로 메시지로 보낸다.
    	for(int i=0;i<users.size();i++){
    		send(users.get(i).getRegistrationId(),message);
    	}
    }

	
}

