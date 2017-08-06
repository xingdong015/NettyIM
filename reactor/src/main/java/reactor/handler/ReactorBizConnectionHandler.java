package reactor.handler;

import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author chengzheng
 * @Date 2017/8/6
 * @TIME 下午3:52
 */
public class ReactorBizConnectionHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

    }
}
