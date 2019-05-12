package com.summer.icommon.utils;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.summer.icommon.constant.AccessToken;
import com.summer.icommon.constant.WxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by lsy on 2018/7/12.
 */
public class WeiXinUtil {
    private static Logger logger = LoggerFactory.getLogger(WeiXinUtil.class);

    private static String accToken = "";


    // 获取access_token的接口地址（GET） 限200（次/天）
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


//    /**
//     * 第一步：用户同意授权，获取code(引导关注者打开如下页面：)
//     * 获取 code、state
//     */
//    public static String    getStartURLGetCode(String redirectUrl){
//        String authUrl = WxConstants.AUTH_BASE_URL + "appid=" + WxConstants.APPID
//                + "&redirect_uri=" + URLEncoder.encode(redirectUrl)
//                + "&response_type=code"
//                + "&scope=" + WxConstants.SCOPE
//                + "&state=STATE#wechat_redirect";
//        return authUrl;
//    }

    /**
     * 获取access_token、openid
     * 第二步：通过code获取access_token
     * @param code url = "https://api.weixin.qq.com/sns/oauth2/access_token
     *   ?appid=APPID
     *   &secret=SECRET
     *   &code=CODE
     *   &grant_type=authorization_code"
     * */
//    public static String getAccess_token(String code){
//        String authUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code ";
//        authUrl= authUrl.replace("APPID", WxConstants.APPID);
//        authUrl = authUrl.replace("SECRET", WxConstants.APPSECRET);
//        authUrl = authUrl.replace("CODE", code);
//        HttpUtil h = new HttpUtil();
//        String  url=   h.sendGet(authUrl);
//        return url;
//    }




    /**测试token
     * @return
     */
    public static String check(String token,String openId){
        //测试token是否有效
        String accessUrl = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
        accessUrl= accessUrl.replace("ACCESS_TOKEN", token);
        accessUrl= accessUrl.replace("OPENID", openId);
        HttpUtil h = new HttpUtil();
        String  url=   h.sendGet(accessUrl);
        JSONObject tokenJsonObject = JSONObject.parseObject(url);
        String msg="";
        if(null!=tokenJsonObject){
            msg= tokenJsonObject.getString("errmsg");
        }
         return msg;
    }
//    /**
//     *
//     *
//     *
//     *
//     *   v拉取用户信息
//     * @return
//     */
//    public static WxChatUser  getWxChatUser(WxChatUser wxchatUser){
//        String token =wxchatUser.getToken();
//        String openId=wxchatUser.getOpenId();
//        String msg=check(token,openId);
//        if("ok".equals(msg)){
//             token=wxchatUser.getToken();
//        }else{
//             String newToken=updateToken(wxchatUser);
//             token=newToken;
//            wxchatUser.setToken(newToken);
//        }
//        String authUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
//        authUrl= authUrl.replace("OPENID", openId);
//        authUrl = authUrl.replace("ACCESS_TOKEN", token);
//        HttpUtil h = new HttpUtil();
//        String  url=   h.sendGet(authUrl);
//        wxchatUser.setUrl(url);
//
//        return wxchatUser;
//    }
//    /**
//     * 刷新token
//     * @return
//     */
//    public static String updateToken(WxChatUser wxchatUser){
//        String authUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
//        authUrl= authUrl.replace("APPID", WxConstants.APPID);
//        authUrl = authUrl.replace("REFRESH_TOKEN", wxchatUser.getRefreshToken());
//        HttpUtil h = new HttpUtil();
//        String  url=   h.sendGet(authUrl);
//        JSONObject jsonObject = JSONObject.parseObject(url);
//        String access_token="";
//        if (null != jsonObject) {
//            try {
//              access_token=jsonObject.getString("access_token");
//            } catch (JSONException e) {
//                // 获取token失败
//                logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.get("errcode"),
//                        jsonObject.getString("errmsg"));
//            }
//        }
//        return access_token;
//    }
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr, Integer cnt) {
        JSONObject jsonObject = null;
        HttpsURLConnection httpUrlConn = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            // 1.创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 2.从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            httpUrlConn = (HttpsURLConnection)url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            // 3.打开读写属性，默认均为false,Post请求不能使用缓存
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setInstanceFollowRedirects(true);
            httpUrlConn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            // 4.设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            } else {
                httpUrlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpUrlConn.connect();
            }
            // 5.当有数据需要提交时
            if (null != outputStr) {
                outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
            }

            // 6.将返回的输入流转换成字符串,后及时关闭资源
            inputStream = httpUrlConn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            // 7.接受返回结果
            StringBuffer buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            Integer statusCode = httpUrlConn.getResponseCode();
            if (statusCode == 200) {
                jsonObject = new JSONObject().getJSONObject(buffer.toString());
            } else {
                jsonObject = new JSONObject();
                jsonObject.put("errcode", 40001);
            }
            Object msgcode = jsonObject.get("errcode");
            if (msgcode != null) {
                logger.info("wx2:statusCode|" + statusCode + "|url|" + requestUrl + "|error|" + msgcode + "|"
                        + jsonObject.get("errmsg"));
                // ex：token失效，重新生成
                if ("42001".equals(msgcode.toString()) || "40001".equals(msgcode.toString())
                        || "-1".equals(msgcode.toString())) {
                    // 睡眠3秒，二次请求
//                    String newtoken = getAccessToken();
                    Thread.sleep(WxConstants.SED_RESULT_TIME);
                    cnt = cnt != null ? cnt : 1;
                    if (0 < cnt) {
                        // 重复请求次数
                        cnt -= 1;
                        int index = requestUrl.indexOf("access_token=");
                        int index2 = requestUrl.indexOf('&', index);
                        requestUrl = requestUrl.substring(0, index + 13)
                                + (index2 == -1 ? "" : requestUrl.substring(index2));
                        jsonObject = httpRequest(requestUrl, requestMethod, outputStr, cnt);
                    }
                }
            } else {
                logger.info("wx1:statusCode|" + statusCode + "|url|" + requestUrl + "|jsonObject|" + jsonObject != null
                        ? jsonObject.toString() : null);
            }
        } catch (Exception e) {
            logger.error("wx:https1|request|" + requestUrl + "|error:" + e);
        } finally {
            // 8.资源释放
            try {
                if (httpUrlConn != null) {
                    httpUrlConn.disconnect();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception e) {
                logger.error("wx:close|request|" + requestUrl + "|error:" + e);
            }
        }
        return jsonObject;
    }

//    public static String getAccessToken() {
//        accToken = "";
//        AccessToken accessToken = getAccessToken(WxConstants.APPID, WxConstants.APPSECRET);
//        if (accessToken == null) {
//            return null;
//        } else {
//            return accessToken.getToken();
//        }
//    }

    /**
     * 获取access_token
     *
     * @param appid
     *            凭证
     * @param appsecret
     *            密钥
     * @return
     */
    public static synchronized AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;
        if (!"".equals(accToken)) {
            accessToken = new AccessToken();
            accessToken.setToken(accToken);
        } else {
            String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
            JSONObject jsonObject = httpRequest(requestUrl, "GET", null, 3);
            // 如果请求成功
            if (null != jsonObject) {
                try {
                    accessToken = new AccessToken();
                    accessToken.setToken(jsonObject.getString("access_token"));
                    accessToken.setExpiresIn((int)jsonObject.get("expires_in"));
                    accToken = jsonObject.getString("access_token");
                } catch (JSONException e) {
                    accessToken = null;
                    // 获取token失败
                    logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.get("errcode"),
                            jsonObject.getString("errmsg"));
                }
            }
        }
        return accessToken;
    }
    /**
     *  获取code
     */


    //微信分享

    public static JSONObject getJSON(String requestTokenUrl){
        HttpUtil h = new HttpUtil();
        String  url=   h.sendGet(requestTokenUrl);
        JSONObject obj = JSONObject.parseObject(url);
        return  obj;
    }


    /**
     * 调取签名需要的签名算法
     * @param jsApiTicket  weChatNumber中的jsApiTicket
     * @param appId  weChatNumber中的appId
     * @param url
     * @return
     */
    public static Map<String,Object> getWeiXinSDK(String jsApiTicket, String appId, String url){
        Map<String, Object> result = null;
        String noncestr = CreateNoncestr();
        long time = System.currentTimeMillis()/1000;
        MessageDigest md = null;
        String tmpStr = null;

         String resultMap = "jsapi_ticket="+jsApiTicket+"&noncestr=" + noncestr + "&timestamp=" + time + "&url=" + url;
        System.out.println("String1===="+resultMap);
        String signature = jiaMi(resultMap);
        if(signature!=null){
            result = new HashMap<String, Object>();
            result.put("timestamp",time);
            result.put("nonceStr",noncestr);
            result.put("signature",signature);
            result.put("appId", appId);
            result.put("jsApiTicket",jsApiTicket);
            result.put("url",url);
            result.put("noncestr",noncestr);
            result.put("time",time);
        }
        return result;
    }

    public static String CreateNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }
    public static  String jiaMi(String txt){
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(txt.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return tmpStr;
    }
    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    /**
     *生成微信加密url  方便分享使用
     * @param url
     */
    public  static String initWxUrl (String url, String appId) {
        try {
            url = URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String EncryptUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=" + url + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
        System.out.println("EncryptUrl="+EncryptUrl);
        return EncryptUrl;
    }

    public String url(String url, String appId){
       String a = initWxUrl(url,appId);
       return a;
    }

     /*
      小程序登录
      */
    public static String getMiniLogin(String code){
        String authUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        authUrl = authUrl.replace("APPID", WxConstants.MINI_APPID);
        authUrl = authUrl.replace("SECRET", WxConstants.MINI_APPSECRET);
        authUrl = authUrl.replace("JSCODE", code);
        HttpUtil h = new HttpUtil();
        String  url=   h.sendGet(authUrl);
        System.out.println("url"+url);
        return url;
    }


}
