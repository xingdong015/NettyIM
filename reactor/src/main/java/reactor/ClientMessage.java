package reactor;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protobuf.Utils;
import protobuf.analysis.ParseMap;
import protocol.generate.chat.Chat;
import protocol.generate.internal.InterProtocol;
import reactor.util.ClientConnection;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by chengzheng on 2017/7/30.
 */
public class ClientMessage {

    private static Logger logger = LoggerFactory.getLogger(ClientMessage.class);

    public static HashMap<Integer, Transfer> transferHandlerHashmap = new HashMap<>();
    public static HashMap<Class<?>, Integer> msg2ptoNum = new HashMap<>();


    @FunctionalInterface
    public interface Transfer {
        void process(Message message, ClientConnection clientConnection);
    }

    public static void registerTranferHandler(Integer ptoNum, Transfer transfer, Class<?> cla) {

        if (transferHandlerHashmap.get(ptoNum) == null) {
            transferHandlerHashmap.put(ptoNum, transfer);
        } else {
            logger.error("pto has been registered in transferHandlerHashmap,ptoNum={}", ptoNum);
            return;
        }

        if (msg2ptoNum.get(cla) == null) {
            msg2ptoNum.put(cla, ptoNum);
        } else {
            logger.error("pto has been registered in msg2ptoNum,ptoNum={}", ptoNum);
            return;
        }
    }

    public static void processTransferHandler(Message message, ClientConnection conn) throws IOException {
        logger.info("MessageName {}",message.getClass());

        int ptoNum = msg2ptoNum.get(message.getClass());
        Transfer transfer = transferHandlerHashmap.get(ptoNum);

        if(transfer != null){
            transfer.process(message,conn);
        }

    }

    public static void transfer2Biz(Message message,ClientConnection conn){
        ByteBuf byteBuf = null;
        if(conn.get_userId() == null){
            logger.error("user not login");
            return;
        }

        if(message instanceof Chat.CPrivateChat){
            byteBuf = Utils.pack2Server(message, ParseMap.getPtoNum(message),conn.get_netId(), InterProtocol.Dest.buslogin,conn.get_userId());
        }


    }


}
