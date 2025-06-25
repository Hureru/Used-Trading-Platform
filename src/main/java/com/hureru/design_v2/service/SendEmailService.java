package com.hureru.design_v2.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value(value = "${spring.mail.username}")
    private String from;
//2240709336_郑伊涛
    public void sendComplexEmail(String to, String subject, String body, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setText(body);
            helper.setTo(to);
            helper.setFrom(from);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            System.out.println("复杂邮件发送成功！");
        }catch(MessagingException e){
            System.out.println("复杂邮件发送失败:"+e.getMessage());
            e.printStackTrace();
        }
    }
// 2240709336_郑伊涛
    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        try {
            mailSender.send(message);
            System.out.println("纯文本文件发送成功！");
        }catch (Exception e) {
            System.out.println("纯文本文件发送失败" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 发送模板邮件
     * @param to       收件人地址
     * @param subject  邮件标题
     * @param content  邮件内容
     */
    public void sendTemplateEmail(String to, String subject, String content) {
        MimeMessage message=mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            System.out.println("模板邮件发送成功");
        }catch (MessagingException e){
            System.out.println("模板邮件发送失败"+e.getMessage());
            e.printStackTrace();
        }
    }

}
