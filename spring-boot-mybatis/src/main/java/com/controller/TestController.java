package com.controller;

import com.pojo.User;
import com.service.IUserService;
import com.service.impl.TxImpl;
import com.upload.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @Description:
 * @author: LinQin
 * @date: 2018/10/25
 */
@RestController
public class TestController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IUserService userService;

    @Autowired
    TxImpl tx;

    @RequestMapping("/test")
    public void test() {

        User user = new User();
        user.setUserAge("99");
        user.setUserName("testTx");

        User insert = userService.insert(user);

    }


    @PostMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        // 获取文件名
        String fileName = file.getOriginalFilename();// 文件上传后的路径
        String filePath = "D:/package_test/" + fileName;
        File saveFile = new File(filePath);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }

        Path path = Paths.get(filePath);
        Files.copy(file.getInputStream(), Paths.get(filePath), new StandardCopyOption[]{StandardCopyOption.REPLACE_EXISTING});
//        Files.write(path, file.getBytes(), StandardOpenOption.SPARSE);
        return filePath;
    }


    @GetMapping("getPercent")
    public Integer getUploadPercent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Progress status = (Progress) session.getAttribute("status");
        logger.info("{}", (int)(status.getBytesRead() * 100.0 / status.getContentLength()));
        int percent = status == null ? 0 : (int) (status.getBytesRead() * 100.0 / status.getContentLength());
        return percent;
    }


    /**
     * 重置上传进度 前端调用进度之前调用此接口
     *
     * @param request
     * @return void
     */
    @PostMapping("resetPercent")
    public Boolean resetPercent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("status");
        return true;
    }

}
