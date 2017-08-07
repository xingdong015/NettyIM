package reactor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhengcheng
 * @date 2017/8/7
 * @time 下午4:49
 **/

public class ClientConnectionMap {

    private static Logger log = LoggerFactory.getLogger(ClientConnectionMap.class);

    private static ConcurrentHashMap<Long,ClientConnection> allClientMap = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String,Long> userid2netidMap = new ConcurrentHashMap<>();

    public static void registerUserid(String userId,Long netId){

        if(userid2netidMap.putIfAbsent(userId, netId) == null){

            ClientConnection connection = ClientConnectionMap.getClientConnection(netId);

            if(connection != null){
                connection.set_userId(userId);
            }else{
                log.error("ClientConnection is null");
                return;
            }
        }else{
            log.error("userid:{} has registered in userid2netidMap",userId);
        }
    }

    private static ClientConnection getClientConnection(Long netId) {
        ClientConnection connection = allClientMap.get(netId);

        if(connection == null){
            log.error("ClientConnection not found in allClientMap,netId={}",netId);
        }

        return connection;
    }


}
