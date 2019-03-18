package com.jeesite.modules.userinfosecond.messageTest;

import com.jeesite.common.msg.SmsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对于短信发送来说，要自己写发送的编辑模板
 */
public class SmsUtilsImpl extends SmsUtils {
    private static final Logger logger = LoggerFactory.getLogger(SmsUtils.class);
    public static String send(String content, String mobile) {
        logger.warn("模拟发送短信成功！请实现 " + SmsUtils.class + " 的 send 方法。");
        return "{result:0,message:\"模拟发送短信成功！\"}";
    }
}
