package reactor;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protobuf.ParseRegisterMap;
import protobuf.code.PacketDecoder;
import protobuf.code.PacketEncoder;
import reactor.handler.ReactorHandler;

import java.net.InetSocketAddress;

/**
 * Created by chengzheng on 2017/7/22.
 */
public class ReactorServer {
    private static final Logger logger = LoggerFactory.getLogger(ReactorServer.class);

    public static void startServer(int port) throws Exception {
        logger.info("startServer start....");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {


            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("MessageDecoder",new PacketDecoder());
                            pipeline.addLast("MessageEncoder",new PacketEncoder());
                            pipeline.addLast("ClientMessageHandler",new ReactorHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

           bindConnectOptions(b);

           b.bind(new InetSocketAddress(port)).addListener(new ChannelFutureListener() {
               @Override
               public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){
                        ParseRegisterMap.initRegistry();
                        TransferHandlerMap.initRegistry();
                        logger.info("[GateServer] Started Successed, registry is complete, waiting for client connect...");
                    }else {
                        logger.error("[GateServer] Started Failed, registry is incomplete");
                    }
               }
           });
        } finally {

            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

    private static void bindConnectOptions(ServerBootstrap b) {
        b.option(ChannelOption.SO_BACKLOG,1024);
        b.option(ChannelOption.SO_LINGER,0);
        b.option(ChannelOption.TCP_NODELAY,true);
        b.option(ChannelOption.SO_REUSEADDR,true);
        b.option(ChannelOption.SO_KEEPALIVE,true);//心跳机制暂时使用TCP选项，之后再自己实现
    }
}
