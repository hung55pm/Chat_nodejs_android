package ngochung.app.Untils;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import ngochung.app.Constants.Constants;

/**
 * Created by NGOCHUNG on 2/21/2017.
 */

public class SocketioHandling {




    public static void socketchat1v1connect(String user_id, String friend_id, Socket socket){
        String roomid=user_id+" "+friend_id;

        socket.emit(Constants.KEY_CONNECT_1VS1,roomid);
    }
}
