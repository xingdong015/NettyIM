package reactor.util;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;


/**
 * 客户端连接封装类
 *
 *
 * @author chengzheng
 * @Date 2017/8/6
 * @TIME 下午3:24
 */
public class ClientConnection {

    private Logger logger = LoggerFactory.getLogger(ClientConnection.class);

    public static final AtomicLong netidGenerator = new AtomicLong(0);

    private String _userId;
    private long _netId;
    private ChannelHandlerContext _ctx;


    ClientConnection(ChannelHandlerContext context){
        _netId = netidGenerator.incrementAndGet();
        _ctx = context;
        _ctx.attr(ClientConnection.NETID).set(_netId);
    }

    public static AttributeKey<Long> NETID = AttributeKey.valueOf("netid");
    public static AttributeKey<Long> ENCRYPT = AttributeKey.valueOf("encrypt");

    public String get_userId() {
        return _userId;
    }

    public void set_userId(String _userId) {
        this._userId = _userId;
    }

    public long get_netId() {
        return _netId;
    }

    public void set_netId(long _netId) {
        this._netId = _netId;
    }

    public ChannelHandlerContext get_ctx() {
        return _ctx;
    }

    public void set_ctx(ChannelHandlerContext _ctx) {
        this._ctx = _ctx;
    }
}
