package com.util;

import java.io.*;
import java.util.Objects;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/08/19
 */
public class Test {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("D:\\IDEAWorkspace\\Spring-boot\\spring-boot-sftp\\src\\main\\resources\\Info.txt");
        FileWriter fw = new FileWriter("D:\\IDEAWorkspace\\Spring-boot\\spring-boot-sftp\\src\\main\\resources\\result.txt");
        BufferedReader br = new BufferedReader(fr);
        BufferedWriter bw=new BufferedWriter(fw);
        String data = "";
        while ((data = br.readLine()) != null) {
            System.out.println(data);
            String[] info = data.split(" ");
            SftpExtentionUtils sftpExtentionUtils = null;
            try {
                sftpExtentionUtils = new SftpExtentionUtils(info[0], Integer.valueOf(info[2]), 60000, info[3], info[4]);
                String connectResult = "%s %s %s";
                String format = String.format(connectResult, info[0], "%s", "%s");
                boolean login = sftpExtentionUtils.login();
                if (login) {
                    sftpExtentionUtils.changeDir("/");
                    System.out.println(sftpExtentionUtils.existDir(info[4]));

//                    boolean b = sftpExtentionUtils.changeDir(info[4]);
                    format = String.format(format, login, "ss");
                } else {
                    format = String.format(format, login, false);
                }

                System.out.println(format);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (Objects.nonNull(sftpExtentionUtils)) {
                    sftpExtentionUtils.logout();
                }
//                bw.write();
                bw.flush();
            }
        }

        br.close();
    }
}
