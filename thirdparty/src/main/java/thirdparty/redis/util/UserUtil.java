package thirdparty.redis.util;

import java.nio.charset.Charset;

/**
 * @author chengzheng
 * @Date 2017/8/6
 * @TIME 上午9:34
 */
public class UserUtil {
    private static Charset charset = Charset.forName("UTF-8");
    public static enum userFields{
        Account;

        public final byte[] field;

        private userFields(){
            this.field = this.name().toLowerCase().getBytes(Charset.forName("UTF-8"));
        };
    }

    public static byte[] genDBKey(String userId){
        StringBuilder sb = new StringBuilder();
        return sb.append("userId={").append(userId).append("}").toString().getBytes(charset);
    }


}
