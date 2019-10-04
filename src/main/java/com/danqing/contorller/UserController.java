package com.danqing.contorller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 * @author 丹青
 * @date 2019/10/2-14:42
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * springmvc文件上传
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/fileupload2")
    public String fileupload2(HttpServletRequest request, MultipartFile upload) throws Exception {
        System.out.println("文件上传……");
        //使用fileupload组件完成文件上传
        //上传的位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        //判断路径是否存在
        File file = new File(path);
        if(!file.exists()){
            //创建文件夹
            file.mkdirs();
        }

        //说明上传文件项
        //获取上传文件的名称
        String filename = upload.getName();
        //把文件名设成唯一值
        String uuid = UUID.randomUUID().toString().replace("-","");
        filename = uuid+filename;
        //完成文件上传
        upload.transferTo(new File(path,filename));
        //删除临时文件
        file.delete();



        return "success";
    }

    /**
     * 普通的javaweb方法实现文件上传
     */
    @RequestMapping("/fileupload1")
    public String fileupload1(HttpServletRequest request) throws Exception {
        System.out.println("文件上传……");
        //使用fileupload组件完成文件上传
        //上传的位置
        //String path = "D:/数据文件/IJ ide/springmvc_day02_02fileupload/src/main/webapp/WEB-INF/uploads/";
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        //判断路径是否存在
        System.out.println(path);
        File file = new File(path);
        if(!file.exists()){
            //创建文件夹
            file.mkdirs();
        }
        //解析request对象，获取上传文件项
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        //解析request
        List<FileItem> items = upload.parseRequest(request);
        //遍历
        for(FileItem item:items){
            //进行判断，当前对象是否是上传文件项
            if(item.isFormField()) {
                //说明普通表单项
            }else {
                //说明上传文件项
                //获取上传文件的名称
                String filename = item.getName();
                //把文件名设成唯一值
                String uuid = UUID.randomUUID().toString().replace("-","");
                filename = uuid+filename;
                //完成文件上传
                item.write(new File(path,filename));
                //删除临时文件
                item.delete();
            }
        }

        return "success";
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> fileDownload(HttpServletRequest request,
                                               String filename) throws Exception{
        // 指定要下载的文件所在路径
        //String path = request.getServletContext().getRealPath("/uploads/");
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        System.out.println(path);
        // 创建该文件对象
        File file = new File(path+File.separator+filename);
        // 对文件名编码，防止中文文件乱码
        filename = this.getFilename(request, filename);
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        // 通知浏览器以下载的方式打开文件
        headers.setContentDispositionFormData("attachment", filename);
        // 定义以流的形式下载返回文件数据
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 使用Sring MVC框架的ResponseEntity对象封装返回下载数据
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.OK);
    }
    /**
     * 根据浏览器的不同进行编码设置，返回编码后的文件名
     */
    public String getFilename(HttpServletRequest request,
                              String filename) throws Exception {
        // IE不同版本User-Agent中出现的关键词
        String[] IEBrowserKeyWords = {"MSIE", "Trident", "Edge"};
        // 获取请求头代理信息
        String userAgent = request.getHeader("User-Agent");
        for (String keyWord : IEBrowserKeyWords) {
            if (userAgent.contains(keyWord)) {
                //IE内核浏览器，统一为UTF-8编码显示
                return URLEncoder.encode(filename, "UTF-8");
            }
        }
        //火狐等其它浏览器统一为ISO-8859-1编码显示
        return new String(filename.getBytes("UTF-8"), "ISO-8859-1");
    }



}
