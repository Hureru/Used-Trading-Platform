package com.hureru.design_v2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zheng
 */
@Service
public class DumpSQLService {
    @Autowired
    private SendEmailService sendEmailService;

    @Value(value = "${spring.mail.username}")
    private String to;

    public String dataBaseDump(String host, String port, String username, String password, String dbName, String currentDate){
        String filePath = "D:\\zheng\\Documents\\Myfiles\\"+dbName+currentDate+".sql";
        String command = "cmd /c \"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump\" -h "+ host +" -P "+port+" -u "+username+" -p"+password+" "+dbName+" >" + filePath;
        System.out.println("数据库备份命令："+command);
        try {
            Runtime.getRuntime().exec(command);
            System.out.println("数据库备份成功！！！");
            return filePath;
        } catch (IOException e) {
            System.out.println("数据库备份失败！！！");
            e.printStackTrace();
            return null;
        }
    }
//    @Scheduled(cron = "0/5 * * * * *")
    @Scheduled(cron = "0 0 0 * * *")
    public void dump(){
        System.out.println("开始备份数据库\n");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss");
        String now = dateFormat.format(new Date());
        String filePath = this.dataBaseDump("localhost", "3306", "root", "Zyt485129", "myjava", now);
        if (filePath != null){
            sendEmailService.sendComplexEmail(to, "数据库备份邮件", "数据库备份成功", filePath);
        }

    }
}
