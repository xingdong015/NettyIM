package reactor;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protobuf.code.PacketDecoder;
import protobuf.code.PacketEncoder;
import reactor.handler.ReactorBizConnectionHandler;

/**
 * @author chengzheng
 * @Date 2017/8/6
 * @TIME 下午3:46
 */
public class ReactorBizConnection {
    private static final Logger logger = LoggerFactory.getLogger(ReactorBizConnection.class);

    public static void startReactorBizConnection(String ip,int port){

        EventLoopGroup boss = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(boss)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();


                        pipeline.addLast("MessageDecoder",new PacketDecoder());
                        pipeline.addLast("MessageEncoder",new PacketEncoder());

                        pipeline.addLast("ReactorBizConnectionHandler",new ReactorBizConnectionHandler());
                    }
                });

        bootstrap.connect(ip, port);

    }
}
