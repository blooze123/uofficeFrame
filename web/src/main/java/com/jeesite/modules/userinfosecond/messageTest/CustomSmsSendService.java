package com.jeesite.modules.userinfosecond.messageTest;

import com.jeesite.common.config.Global;
import com.jeesite.common.lang.ExceptionUtils;
import com.jeesite.common.lang.ObjectUtils;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.common.msg.EmailUtils;
import com.jeesite.common.msg.SmsUtils;
import com.jeesite.common.service.BaseService;
import com.jeesite.modules.msg.entity.MsgPush;
import com.jeesite.modules.msg.entity.content.EmailMsgContent;
import com.jeesite.modules.msg.entity.content.SmsMsgContent;
import com.jeesite.modules.msg.send.MsgSendService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 发送邮件、短信使用的发送类
 * @author ThinkGem
 * @version 2018年5月13日
 */
@Service("customSmsSendService")
public class CustomSmsSendService extends BaseService implements MsgSendService {

    @Override
    public void sendMessage(MsgPush msgPush) {
        try {
            System.out.println("进入发送消息方法");
            //发送短信

            if(msgPush.getMsgType().equalsIgnoreCase("email")){
                EmailMsgContent content=msgPush.parseMsgContent(EmailMsgContent.class);
                boolean isSend= EmailUtils.send(Global.getConfig("msg.email.fromAddress"), Global.getConfig("msg.email.fromPassword"), Global.getConfig("msg.email.fromHostName"), Global.getConfig("msg.email.sslOnConnect"), Global.getConfig("msg.email.sslSmtpPort"),msgPush.getReceiveUserCode(),content.getTitle(),content.getContent());
                //发送失败
                if(isSend){
                    System.out.println("成功发送消息！！！！");
                }
                //发送失败
                else{
                    throw  new RuntimeException();
                }
            }
            if(msgPush.getMsgType().equalsIgnoreCase("sms")){
                SmsMsgContent content=msgPush.parseMsgContent(SmsMsgContent.class);
                String result= SmsUtils.send(content.getContent(),msgPush.getReceiveCode());
                Map<String,Object> map= JsonMapper.fromJson(result,Map.class);
                //发送成功
                if(ObjectUtils.toInteger(map.get("result"))==0){
                    msgPush.setPushStatus(MsgPush.PUSH_STATUS_SUCCESS);
                    msgPush.addPushReturnContent(result);
                    System.out.println("成功发送消息！！！！");
                }
                //发送失败
                else{
                    throw  new RuntimeException(result);
                }
            }
        }catch (Exception e){
            logger.error("发送短信失败！！！！！！！！！！！！！",e);
            msgPush.setPushDate(new Date());
            msgPush.setPushStatus(MsgPush.PUSH_STATUS_FAIL);
            msgPush.addPushReturnContent(ExceptionUtils.getStackTraceAsString(e));
        }
    }
}
