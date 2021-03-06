package com.wxd.spread.core.wechat.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wxd.spread.core.constant.Constant;
import com.wxd.spread.core.model.SysTemplateMessage;
import com.wxd.spread.core.service.SysTemplateMessageService;
import com.wxd.spread.core.wechat.SysTemplateMsgSender;

@Component("aboutUsSender")
public class AboutUsSender implements SysTemplateMsgSender {
	@Autowired
	private SysTemplateMessageService sysTemplateMessageService;
	@Resource(name="withdrawSender")
	private SysTemplateMsgSender nextSender;
	
	private boolean selfSupport(String templateKey) {
		if (StringUtils.equals(templateKey, Constant.SYS_TEMPLATE_ABOUT_US_KEY)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void sendWechatTemplateMsg(SysTemplateMessage msg,String openid) {
		if (selfSupport(msg.getTemplateKey())) {
			return;
		}
		if (nextSender != null) {
			nextSender.sendWechatTemplateMsg(msg,openid);
		}
	}

	@Override
	public SysTemplateMessage findByMenuKey(String menuKey) {
		if (StringUtils.equals(menuKey, Constant.WECHAT_MENU_ABOUT_US_KEY)) {
			return sysTemplateMessageService.findByTemplateKey(Constant.SYS_TEMPLATE_ABOUT_US_KEY);
		}
		if (nextSender != null) {
			return nextSender.findByMenuKey(menuKey);
		}
		return null;
	}

}
