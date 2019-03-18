package com.jeesite.modules.userinfosecond.messageTest;

import com.jeesite.common.config.Global;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.msg.entity.MsgPush;
import com.jeesite.modules.msg.entity.content.*;
import com.jeesite.modules.msg.service.MsgPushService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 常规发生消息时的发送类
 */
@Component
public class PushMessage {

    @Resource
    private MsgPushService msgPushService;

    /**
     * 推送消息（即时推送）
     * @param type 		消息类型（MsgPush.TYPE_PC、TYPE_APP、TYPE_SMS、TYPE_EMAIL）
     * @param title		消息标题
     * @param content	消息内容
     * @param bizKey	关联业务主键
     * @param bizType	关联业务类型
     * @param receiveUserCodes	接受者用户编码
     * @author ThinkGem
     */
    public MsgPush push(String type, String title, String content, String bizKey, String bizType, String receiveUserCodes){
        BaseMsgContent msgContent = null;
        if (MsgPush.TYPE_PC.equals(type)){
            msgContent = new PcMsgContent();
        }else if (MsgPush.TYPE_APP.equals(type)){
            msgContent = new AppMsgContent();
        }else if (MsgPush.TYPE_SMS.equals(type)){
            msgContent = new SmsMsgContent();
        }else if (MsgPush.TYPE_EMAIL.equals(type)){
            msgContent = new EmailMsgContent();
        }
        if (msgContent != null){
            msgContent.setTitle(title);
            msgContent.setContent(content);
            return push(msgContent, bizKey, bizType, receiveUserCodes, new Date(), Global.NO);
        }
        return null;
    }

    /**
     * 推送消息（即时推送）
     * @param msgContent 消息内容实体：PcMsgContent、AppMsgContent、SmsMsgContent、EmailMsgContent、WeicxinMsgContent
     * @param bizKey	关联业务主键
     * @param bizType	关联业务类型
     * @param receiveUserCodes	接受者用户编码
     * @author ThinkGem
     */
    public MsgPush push(BaseMsgContent msgContent, String bizKey, String bizType, String receiveUserCodes){
        return push(msgContent, bizKey, bizType, receiveUserCodes, new Date(), Global.NO);
    }

    /**
     * 推送消息
     * @param msgContent 消息内容实体：PcMsgContent、AppMsgContent、SmsMsgContent、EmailMsgContent、WeicxinMsgContent
     * @param bizKey	关联业务主键
     * @param bizType	关联业务类型
     * @param receiveUserCodes	接受者用户编码，多个用逗号隔开，用[CODE]作为前缀，可直接指定接受者手机号或邮箱地址等
     * @param planPushDate  计划推送时间（指定推送时间，延迟推送）
     * @param isMergePush	是否是合并推送（将消息合并为一条，延迟推送，用于不重要的提醒），默认：Global.NO
     * @author ThinkGem
     */
    public MsgPush push(BaseMsgContent msgContent, String bizKey, String bizType, String receiveUserCodes, Date planPushDate, String isMergePush) {
        boolean isNone = StringUtils.startsWith(receiveUserCodes, "[CODE]");
        if (isNone){
            receiveUserCodes = StringUtils.substringAfter(receiveUserCodes, "[CODE]");
        }
        MsgPush msgPush = null;
        for (String receiveUserCode : StringUtils.split(receiveUserCodes, ",")){
            msgPush = new MsgPush();
            msgPush.setMsgContentEntity(msgContent);
            msgPush.setBizKey(bizKey);
            msgPush.setBizType(bizType);
            if (isNone){
                msgPush.setReceiveCode(receiveUserCode);
            }else{
                msgPush.setReceiveUserCode(receiveUserCode);
            }
            msgPush.setPlanPushDate(planPushDate);
            msgPush.setIsMergePush(isMergePush);
            push(msgPush);
        }
        return msgPush;
    }

    /**
     * 推送消息
     * @param msgPush 推送对象
     * @example
     * 		MsgPush msgPush = new MsgPush();
     * 		SmsMsgContent msgContent = new SmsMsgContent();
     * 		msgContent.setTitle(title);
     * 		msgContent.setContent(content);
     * 		msgPush.setMsgContentEntity(msgContent);
     * 		msgPush.setBizKey(bizKey);
     * 		msgPush.setBizType(bizType);
     * 		msgPush.setReceiveUserCode(receiveUserCode);
     * 		msgPush.setPlanPushDate(planPushDate);
     * 		msgPush.setIsMergePush(isMergePush);
     * @author ThinkGem
     */
    public  void push(MsgPush msgPush) {
        msgPushService.save(msgPush);
    }

    /**
     * 读取消息（业务处理完成后调用，自动处理对应业务的消息）
     * @param bizKey 业务主键
     * @param bizType 业务类型
     * @param receiveUserCode 接受者用户编码（必填）
     * @author ThinkGem
     */
    public  void readMsgByBiz(String bizKey, String bizType, String receiveUserCode) {
        MsgPush mp = new MsgPush();
        mp.setBizKey(bizKey);
        mp.setBizType(bizType);
        if(StringUtils.isBlank(receiveUserCode)){
            return;
        }
        mp.setReceiveUserCode(receiveUserCode);
        List<MsgPush> list = msgPushService.findList(mp);
        for (MsgPush msgPush : list){
            msgPush.setReadDate(new Date());
            msgPush.setReadStatus(MsgPush.READ_STATUS_READ);
            msgPushService.updateMsgPush(msgPush);
        }
    }
}
