package protobuf.code;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protobuf.analysis.ParseMap;

import java.util.List;

/**
 * Created by chengzheng on 2017/7/26.
 */
public class PacketDecoder extends ByteToMessageDecoder{
    private Logger logger = LoggerFactory.getLogger(PacketEncoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        in.markReaderIndex();

        if(in.readableBytes() < 4){
            logger.info("readableBytes length less than 4 bytes,ignored");
            in.resetReaderIndex();
            return;
        }

        int length = in.readableBytes();

        if(length < 0){
            ctx.close();
            logger.error("message length less than 0,channel closed");
            return;
        }

        if(length > in.readableBytes() - 4){
            in.resetReaderIndex();
            return;
        }

        int ptoNum = in.readInt();

        ByteBuf byteBuf = Unpooled.buffer(8+length);

        in.readBytes(byteBuf);

        try {
            /* 解密消息体
            ThreeDES des = ctx.channel().attr(ClientAttr.ENCRYPT).get();
            byte[] bareByte = des.decrypt(inByte);*/

            byte[] body= byteBuf.array();

            Message msg = ParseMap.getMessage(ptoNum, body);
            out.add(msg);
            logger.info("GateServer Received Message: content length {}, ptoNum: {}", length, ptoNum);

        } catch (Exception e) {
            logger.error(ctx.channel().remoteAddress() + ",decode failed.", e);
        }
    }
}
