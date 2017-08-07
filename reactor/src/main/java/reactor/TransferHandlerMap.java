package reactor;


import protobuf.generate.cli2srv.auth.Auth;
import protocol.generate.chat.Chat;

import java.io.IOException;

/**
 * Created by chengzheng on 2017/7/30.
 */
public class TransferHandlerMap {

    public static void initRegistry() throws IOException{
        ClientMessage.registerTranferHandler(1000,ClientMessage::transfer2Auth, Auth.CLogin.class);
        ClientMessage.registerTranferHandler(1001,ClientMessage::transfer2Auth,Auth.CRegister.class);
        ClientMessage.registerTranferHandler(1002,ClientMessage::transfer2Biz, Chat.SPrivateChat.class);
    }
}
