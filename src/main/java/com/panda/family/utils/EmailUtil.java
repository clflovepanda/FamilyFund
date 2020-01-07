package com.panda.family.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {

    public static boolean checkEmailFormat(String email) {
        String checkReg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(checkReg);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

    public static boolean sendNormalEmail(String to, String subject, String content) {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.163.com");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        Session session = Session.getInstance(properties);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        try {
            Address address = new InternetAddress("clibchina@163.com");
            message.setFrom(address);
            Address toAddress = new InternetAddress(to);
            message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setSentDate(new Date());
            Multipart multipart = new MimeMultipart();
            BodyPart html = new MimeBodyPart();
            html.setContent(content, "text/html; charset=utf-8");
            multipart.addBodyPart(html);
            message.setContent(multipart);
            message.saveChanges();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.163.com", "clibchina@163.com", "panda15846504013");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static class MailThread implements Runnable {

        private String to;
        private String subject;
        private String content;

        public MailThread(String to, String subject, String content) {
            this.to = to;
            this.subject = subject;
            this.content = content;
        }

        @Override
        public void run() {
            sendNormalEmail(to, subject, content);
        }
    }

    public static void sendMail(String to, String subject, String content) {
        MailThread mailThread = new MailThread(to, subject, content);
        mailThread.run();
    }

    public static void main(String[] args) {
        String content = "<!DOCTYPE> <div><h1>这只是一封测试邮件</h1>您好~！我是家庭管家小精灵~ 用户Panda（xxx），邀请你加入家庭：xxx。点击链接同意邀请。 </div>";
        boolean result = EmailUtil.sendNormalEmail("982294498@qq.com", "成员邀请邮件", content);
        System.out.println(result);
    }
}
