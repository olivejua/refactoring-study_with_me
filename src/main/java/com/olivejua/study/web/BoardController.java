package com.olivejua.study.web;

import com.olivejua.study.utils.ImageUploader;
import com.olivejua.study.web.dto.board.ImageDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Controller
public class BoardController {


    @RequestMapping("/photoUpload")
    public String updateImage(HttpServletRequest request, ImageDto imageDto) {
        String fileResult = "";
        try {
            if (imageDto.getFileData() != null &&
                    StringUtils.isEmpty(imageDto.getFileData().getOriginalFilename())) {
                String originalName = imageDto.getFileData().getOriginalFilename();
                String extension = originalName.substring(originalName.lastIndexOf(".") + 1);

                String defaultPath = request.getSession().getServletContext().getRealPath("/");

                String path = defaultPath + "resource" + File.separator + "photo_upload" + File.separator;
                File file = new File(path);
                ImageUploader.tempPath = path;

                if (!file.exists()) {
                    file.mkdirs();
                }

                String realName = UUID.randomUUID().toString() + "." + extension;
                imageDto.getFileData().transferTo(new File(path + realName));

                fileResult += "&bNewLine=true&sFileName="+originalName+"&sFileURL=/resource/photo_upload/"+realName;
            } else {
                fileResult += "&errstr=error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + imageDto.getCallback() + "?callback_func="+imageDto.getCallback_func()+fileResult;
    }
}
