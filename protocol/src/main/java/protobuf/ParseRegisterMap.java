package protobuf;

import protobuf.analysis.ParseMap;
import protobuf.generate.cli2srv.auth.Auth;
import protocol.generate.chat.Chat;
import protocol.generate.internal.InterProtocol;

import java.io.IOException;

/**
 * Created by chengzheng on 2017/7/27.
 */

public class ParseRegisterMap {
    public static final int GTRANSFER = 900;
    public static final int GREET = 901;

    public static final int CLOGIN = 1000;
    public static final int CREGISTER = 1001;

    public static final int SREPONSE = 1002;

    public static final int CPRIVATECHAT = 1003;
    public static final int SPRIVATECHAT = 1004;

    public static void initRegistry()throws IOException{
        /**
         * 服务内部传输协议注册
         */
        ParseMap.register(GTRANSFER, InterProtocol.GTransfer::parseFrom,InterProtocol.GTransfer.class);
        ParseMap.register(GREET,InterProtocol.Greet::parseFrom,InterProtocol.Greet.class);

        ParseMap.register(CLOGIN, Auth.CLogin::parseFrom,Auth.CLogin.class);
        ParseMap.register(CREGISTER,Auth.CRegister::parseFrom,Auth.CRegister.class);

        ParseMap.register(SREPONSE, Chat.SResponse::parseFrom,Chat.SResponse.class);
        ParseMap.register(CPRIVATECHAT,Chat.CPrivateChat::parseFrom,Chat.CPrivateChat.class);
        ParseMap.register(SPRIVATECHAT,Chat.SPrivateChat::parseFrom,Chat.SPrivateChat.class);




    }

}
