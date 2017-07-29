package protobuf.analysis;

import com.google.protobuf.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by chengzheng on 2017/7/26.
 */
public class ParseMap {
    private static final Logger log = LoggerFactory.getLogger(ParseMap.class);

    @FunctionalInterface
    public interface Parsing {
        Message process(byte[] buf) throws IOException;
    }

    public static HashMap<Integer, ParseMap.Parsing> parseMap = new HashMap<>();
    public static HashMap<Class<?>, Integer> msg2ptoNum = new HashMap<>();

    public static void register(int ptoNum, ParseMap.Parsing parsing, Class<?> cla) {
        if (parseMap.get(ptoNum) == null) {
            parseMap.put(ptoNum, parsing);
        } else {
            log.error("pto has been registered in parseMap,ptoNum:{}", ptoNum);
            return;
        }

        if(msg2ptoNum.get(cla) == null){
            msg2ptoNum.put(cla,ptoNum);
        }else{
            log.error("pto has been registered in msg2ptoNum,ptoNum:{}",ptoNum);
            return;
        }

    }

    public static Message getMessage(int ptoNum,byte[]bytes)throws IOException{
        Parsing parser = parseMap.get(ptoNum);
        if(parser == null){
            log.error("unknown Protocol Num:{}"+ptoNum);
            return null;
        }
        Message message = parser.process(bytes);
        return message;
    }


    public static Integer getPtoNum(Message message){
        return getPtoNum(message.getClass());
    }

    private static Integer getPtoNum(Class<? extends Message> clz) {
        return msg2ptoNum.get(clz);
    }
}
