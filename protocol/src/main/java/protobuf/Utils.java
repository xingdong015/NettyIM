package protobuf;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protobuf.analysis.ParseMap;
import protocol.generate.internal.InterProtocol;

/**
 * Created by chengzheng on 2017/7/27.
 */
public class Utils {

    public static ByteBuf pack2Server(Message msg, int ptoNum, long netId, InterProtocol.Dest dest,String userId){
        InterProtocol.GTransfer.Builder gtf = InterProtocol.GTransfer.newBuilder();
        gtf.setDest(dest);
        gtf.setMsg(msg.toByteString());
        gtf.setNetId(netId);
        gtf.setPtoNum(ptoNum);
        gtf.setUserId(userId);


        byte[] bytes = gtf.build().toByteArray();
        int length = bytes.length;
        int gtfNum = ParseRegisterMap.GTRANSFER;

        ByteBuf buffer = Unpooled.buffer(8 + length);
        buffer.writeInt(length);
        buffer.writeInt(gtfNum);
        buffer.writeBytes(bytes);

        return buffer;
    }

    public static ByteBuf pack2Client(Message msg){
        byte[] bytes = msg.toByteArray();
        int lenth = bytes.length;
        int ptoNum = ParseMap.getPtoNum(msg);

        ByteBuf buffer = Unpooled.buffer(8 + lenth);
        buffer.writeInt(lenth);
        buffer.writeInt(ptoNum);
        buffer.writeBytes(bytes);

        return buffer;
    }
}
