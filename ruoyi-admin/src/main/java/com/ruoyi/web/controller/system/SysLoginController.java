package com.ruoyi.web.controller.system;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.WxAppConfig;
import com.ruoyi.common.core.domain.model.WxLoginBody;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.Base64;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.system.service.ISysMenuService;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@Slf4j
@RestController
public class SysLoginController
{
    private static final Logger logger = LoggerFactory.getLogger(SysLoginController.class);
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WxAppConfig wxAppConfig;

    @PostMapping("/wxLogin")
    public AjaxResult wxLogin(@RequestBody WxLoginBody wxLoginBody){
        log.info("微信登录");
        logger.info("登录参数："+ JSON.toJSONString(wxLoginBody));
        String code = wxLoginBody.getCode();
        String encryptedIv = wxLoginBody.getEncryptedIv();
        String encryptedData = wxLoginBody.getEncryptedData();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+wxAppConfig.getAppId()+"&secret="+wxAppConfig.getAppSecret()+"&js_code="+code+"&grant_type=authorization_code";
        log.info("url:"+url);
        String res = restTemplate.getForObject(url,String.class);
        log.info("res:"+res);
        JSONObject jsonObject = JSONObject.parseObject(res);

        String sessionKey = jsonObject.getString("session_key");
        log.info("sessionKey:"+sessionKey);
        String openid = jsonObject.getString("openid");

        String decryptResult = "";
        try{
            decryptResult = decrypt(sessionKey,encryptedIv,encryptedData);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("微信登录失败!");
        }
        if (StringUtils.hasText(decryptResult)){
            String token = loginService.wxLogin(decryptResult);
            AjaxResult ajax = AjaxResult.success();
            ajax.put(Constants.TOKEN,token);
            ajax.put("openid",openid);
            return ajax;
        }else {
            return AjaxResult.error("微信登录失败!");
        }
    }

    public static String decrypt(String sessionKey, String iv,String encryptedData){

        // 被加密的数据

        byte[] dataByte = Base64.decode(encryptedData);

        // 加密秘钥

        byte[] keyByte = Base64.decode(sessionKey);

        // 偏移量

        byte[] ivByte = Base64.decode(iv);



        try {

            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要

            int base = 16;

            if (keyByte.length % base != 0) {

                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);

                byte[] temp = new byte[groups * base];

                Arrays.fill(temp, (byte) 0);

                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);

                keyByte = temp;

            }

            // 初始化

            Security.addProvider(new BouncyCastleProvider());

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");

            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");

            parameters.init(new IvParameterSpec(ivByte));

            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化

            byte[] resultByte = cipher.doFinal(dataByte);

            if (null != resultByte && resultByte.length > 0) {

                String result = new String(resultByte, "UTF-8");

                return result;

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        ajax.put("username",loginBody.getUsername());
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
