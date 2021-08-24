package com;


import com.google.common.collect.ImmutableList;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.GeneralSecurityException;
import java.util.List;

import static java.lang.Integer.valueOf;

/**
 * @Description:
 * @author: LinQin
 * @date: 2020/06/08
 */
public class HtmlMailUtil {

    private Logger logger = LoggerFactory.getLogger(HtmlMailUtil.class);

    HtmlEmail htmlEmail = new HtmlEmail();

    public HtmlMailUtil(String sendUsername, String sendUser, String password, String smtpName, boolean asSSL, String port, boolean username) throws EmailException {
        htmlEmail.setHostName(smtpName);
        htmlEmail.setFrom(sendUser);
        if (username){
            // 验证账户名登录
            htmlEmail.setAuthentication(sendUsername, password);
        } else {
            // 验证邮箱帐号登录
            htmlEmail.setAuthentication(sendUser, password);
        }
        htmlEmail.setCharset("UTF-8");
        htmlEmail.setSSLOnConnect(asSSL);
        htmlEmail.setSmtpPort(valueOf(port));
    }

    public void send(List<String> recipients, String subject, String content) throws EmailException {
        htmlEmail.setSubject(subject);
        htmlEmail.setHtmlMsg(content);
        for (String recipient : recipients) {
            htmlEmail.addTo(recipient);
        }
        htmlEmail.send();
    }

    public void sendWithAttachment(List<String> recipients, String subject, String content, String attachmentPath) throws EmailException {
        htmlEmail.setSubject(subject);
        htmlEmail.setHtmlMsg(content);
        for (String recipient : recipients) {
            htmlEmail.addTo(recipient);
        }
        EmailAttachment ea = new EmailAttachment();
        ea.setPath(attachmentPath);
        htmlEmail.attach(ea);
        htmlEmail.send();
    }


    public static void main(String[] args) throws EmailException, GeneralSecurityException {
//        HtmlMailUtil htmlMailUtil = new HtmlMailUtil("linqin", "woshilinqin163@163.com", "HNWHSBQTMZKGTHDP", "smtp.163.com", false, "25");
//        htmlMailUtil.sendWithAttachment(ImmutableList
//                .of("linqin@.com"), "html邮件测试", "cao, <h1>caonima</h1>", "C:\\Users\\LinQin\\Desktop\\无标题文档.html");

        HtmlMailUtil htmlMailUtil = new HtmlMailUtil(args[0], args[1], args[2], args[3], args[4].equals("true"), args[5], args[8].equals("true"));
        htmlMailUtil.sendWithAttachment(ImmutableList
                .of(args[6]), "html邮件测试", "cao, <h1>caonima</h1>", args[7]);


    }
}
