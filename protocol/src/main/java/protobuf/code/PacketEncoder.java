package protobuf.code;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protobuf.analysis.ParseMap;

/**
 * Created by chengzheng on 2017/7/26.
 * 10:45
 */
public class PacketEncoder extends MessageToByteEncoder<Message> {
    private Logger logger = LoggerFactory.getLogger(PacketEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {

        byte[]bytes = msg.toByteArray();
        int length = bytes.length;
        int ptoNum = ParseMap.msg2ptoNum.get(msg);

        ByteBuf buffer = Unpooled.buffer(8 + length);
        buffer.writeInt(length);
        buffer.writeInt(ptoNum);
        buffer.writeBytes(bytes);

        out.writeBytes(buffer);

        logger.info("GateServer Send Message, remoteAddress: {}, content length {}, ptoNum: {}", ctx.channel().remoteAddress(), length, ptoNum);


    }
}
