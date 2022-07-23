package com.newcoder.community.controller;

import com.newcoder.community.annotation.LoginRequired;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.FollowService;
import com.newcoder.community.service.LikeService;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.CommunityUtil;
import com.newcoder.community.util.HostHolder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebParam;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * @author yt
 * date 2022-07-10
 */
@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${qiniu.key.access}")
    private String accessKey;

    @Value("${qiniu.key.secret}")
    private String secretKey;

    @Value("${qiniu.bucket.header.url}")
    private String headerBucketUrl;

    @Value("${qiniu.bucket.header.name}")
    private String headerBucketName;

    @Autowired
    private UserService userService;    //更新用户信息

    @Autowired
    private HostHolder hostHolder;  //取用户信息

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @LoginRequired
    @RequestMapping(path = "/setting",method = RequestMethod.GET)
    public String getSettingPage(Model model){
        //上传文件名称
        String fileName = CommunityUtil.generateUUID();
        //设置响应信息
        StringMap policy = new StringMap();
        policy.put("returnBody",CommunityUtil.getJSONString(0));
        //生成上传的凭证
        Auth auth = Auth.create(accessKey,secretKey);
        String uploadToken = auth.uploadToken(headerBucketName,fileName,3600,policy);

        model.addAttribute("uploadToken",uploadToken);
        model.addAttribute("fileName",fileName);

        return "/site/setting";
    }

    //更新头像路径
    @RequestMapping(path="/header/url",method = RequestMethod.POST)
    @ResponseBody
    public String updateHeaderUrl(String fileName){
        if(StringUtils.isBlank(fileName)){
            return CommunityUtil.getJSONString(1,"文件名不能为空");
        }
        String url = headerBucketUrl + "/"+ fileName;
        userService.updateHeader(hostHolder.getUser().getId(),url);
        return CommunityUtil.getJSONString(0);
    }


    //废弃
    @Deprecated
    @LoginRequired
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImg, Model model){
        if(headerImg == null){
            model.addAttribute("error","您还没有选择图片！");
            return "/site/setting";
        }
        //上传文件
        String filename = headerImg.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if(StringUtils.isBlank(suffix)){
            model.addAttribute("error","文件的格式不正确");
            return "/site/setting";
        }

        //生成随机的文件名
        filename = CommunityUtil.generateUUID() + suffix;
        //确定文件的存放路径
        File dest = new File(uploadPath + "/" + filename);
        try {
            //存储文件
            headerImg.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败: ",e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常！",e);
        }
        //更新当前用户的头像的路径（web访问路径）
        //http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/"+filename;
        userService.updateHeader(user.getId(),headerUrl);

        return "redirect:/index";
    }

    //废弃
    @Deprecated
    @RequestMapping(path = "/header/{filename}",method = RequestMethod.GET)
    public void getHeader(@PathVariable("filename") String fileName, HttpServletResponse response){
        //服务器存放的路径
        fileName = uploadPath+"/"+fileName;
        //解析文件的后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //响应图片
        response.setContentType("image/" + suffix);
        try (
                //自动在finally关闭
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream outputStream = response.getOutputStream();
                ){
            byte[] buffer = new byte[1024];
            int b = 0;
            while((b = fis.read(buffer))!=-1){
                outputStream.write(buffer,0,b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败： "+e.getMessage());

        }
    }

    @LoginRequired
    @RequestMapping(path = "/updatePwd",method = RequestMethod.POST)
    public String updatePassword(String oldPwd, String newPwd, Model model){
        if(oldPwd == null ){
            model.addAttribute("oldPwdError","您还未输入！");
            return "/site/setting";
        }
        if(newPwd == null ){
            model.addAttribute("newPwdError","您还未输入！");
            return "/site/setting";
        }
        User user = hostHolder.getUser();
        Map<String, Object> map = userService.updatePassword(user.getId(), oldPwd, newPwd);
        if(map.containsKey("passwordMsg")){
            model.addAttribute("oldPwdError",map.get("passwordMsg"));
            return "/site/setting";
        }
        else if(map.containsKey("newPwdMsg"))
        {
            model.addAttribute("newPwdError",map.get("newPwdMsg"));
            return "/site/setting";
        }
        return "redirect:/index";   //修改密码成功后重新登录
    }

    //个人主页
    @RequestMapping(path = "/profile/{userId}",method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model){
        User user = userService.findUserById(userId);
        if(user == null){
            throw new RuntimeException("用户不存在！");
        }

        //用户
        model.addAttribute("user",user);
        //获得赞的数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);

        //关注数量
        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
        model.addAttribute("followeeCount",followeeCount);
        //粉丝数量
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER,userId);
        model.addAttribute("followerCount",followerCount);
        //是否被当前用户关注
        boolean hasFollowed = false;
        if(hostHolder.getUser()!=null)
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(),ENTITY_TYPE_USER,userId);
        model.addAttribute("hasFollowed",hasFollowed);

        return "/site/profile";
    }

}
