package com.ruoyi.web.applicantion.controller;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.applicantion.service.ArticleService;
import com.ruoyi.web.applicantion.service.dto.ArticleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 封面,文本内容上传
     */
    //增
    @PostMapping("/upload")
    public AjaxResult upload(@RequestParam MultipartFile file,
                             @RequestParam MultipartFile content,
                             @RequestParam String Title,
                             @RequestParam String Author) {
        log.info("Title:" + Title + ",Author:" + Author);
        log.info("file:"+file);
        return articleService.upload(file, content, Title, Author);
    }

    //查(全部)
    @GetMapping("/selectAll")
    public AjaxResult getAll() {
        List<ArticleDto> list = articleService.getAll();
        return AjaxResult.success(list);
    }

    //查(id)
    @GetMapping("/select")
    public AjaxResult getById(@RequestParam String id) {
        log.info("id:" + id);
        List<ArticleDto> list = articleService.getById(id);
        return AjaxResult.success(list);
    }

    //删
    @DeleteMapping("/delete")
    public AjaxResult deleteById(@RequestParam String id, @RequestParam String url) {
        log.info("id:" + id + ",url:" + url);
        int code = articleService.deleteById(id, url);
        if (code == 1) {
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }

    //改
    @PutMapping("/update")
    public AjaxResult update(@RequestParam MultipartFile file,
                             @RequestParam MultipartFile content,
                             @RequestParam String Title,
                             @RequestParam String Author,
                             @RequestParam String id,
                             @RequestParam String src) throws IOException {
        log.info("Title:" + Title + ",Author:" + Author + ",id:" + id+",src:"+src);
        articleService.update(id, file, content, Title, Author,src);
        return AjaxResult.success();
    }

    @Anonymous
    @GetMapping("/load/{imgPath}")
    public AjaxResult getImg(HttpServletResponse response, @PathVariable String imgPath) {
        log.info("imgPath:" + imgPath);
        File file = new File("D:\\aaa\\imge\\"+imgPath);
        if (file.exists() && file.isFile()) {
            FileInputStream fis = null;
            OutputStream os = null;
            try {
                fis = new FileInputStream(file);
                os = response.getOutputStream();
                int count = 0;
                byte[] buffer = new byte[1024 * 8];
                while ((count = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                    os.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return AjaxResult.success("导出成功");
        }
        return AjaxResult.error("导出失败");
    }
}
