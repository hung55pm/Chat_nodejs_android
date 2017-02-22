package ngochung.app.Untils;

import android.content.Context;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ngochung.app.Constants.Constants;
import ngochung.app.Models.Message;

/**
 * Created by NGOCHUNG on 2/21/2017.
 */

public class SocketioHandling {
    public static void socketchat1v1connect(String user_id, String friend_id, Socket socket){
        String roomid=user_id+" "+friend_id;
        socket.emit(Constants.KEY_CONNECT_1VS1,roomid);
    }

    public static void socketsend1vs1(Context mContext,String roomid, String message, Socket socket){
        String name= new SharedConfig(mContext).getValueString(SharedConfig.NAME);
        String user_id= new SharedConfig(mContext).getValueString(SharedConfig.USER_ID);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        Message mes=new Message(roomid,user_id,name,message,date);
        Gson gson = new Gson();
         String jsonInString = gson.toJson(mes);
        socket.emit(Constants.KEY_CHAT_1VS1,jsonInString);


    }
}
