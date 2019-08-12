//package com.summer.icore.utils;
//
//import java.io.BufferedReader;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.InterruptedIOException;
//import java.io.UnsupportedEncodingException;
//import java.math.BigDecimal;
//import java.net.InetAddress;
//import java.net.MalformedURLException;
//import java.net.Socket;
//import java.net.URL;
//import java.net.URLConnection;
//import java.net.UnknownHostException;
//import java.security.MessageDigest;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.StringTokenizer;
//import java.util.UUID;
//import java.util.Vector;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.lang.StringUtil;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.input.SAXBuilder;
//
//import com.efreen.cargo.mh.vo.SysConfig;
//import com.efreen.cargo.task.utils.JiMiUtil;
//import com.efreen.common.vo.ReturnData;
//import com.pay.demo.Demo;
//
//import net.sf.json.JSONObject;
//
///**
// * @author liudongting
// * @date 2019/7/26 12:00
// */
//public class function {
//        public function() {
//        }
//
//        public static String getImUrl() {
//            String path = function.class.getResource("Function.class").getFile();
//            path = path.replace("file:/", "/");
//            path = path.substring(0, path.indexOf("WEB-INF")) + "webServiceConnectXml/webServiceConnect.xml";
//            SAXBuilder builder = new SAXBuilder();
//            Document doc = null;
//            String imUrl = "";
//            try {
//                doc = builder.build(new File(path));
//                Element root = doc.getRootElement();
//                // 只要在xml中添加一个路径 此处追加一个root.getChildText
//                imUrl = root.getChildText("imURL");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return imUrl;
//        }
//
//        /****************************************************************************
//         * 将长消息截断，主要用于短信显示
//         *
//         *
//         */
//        public static String cutStr(String content, int len) {
//            if (content == null) {
//                return "";
//            } else if (content.length() <= len) {
//                return content;
//            } else {
//                return content.substring(0, len) + "...";
//            }
//
//        }
//
//        /************************************************************************************
//         * 将金钱单位的分格式转化为元
//         *
//         */
//        public static String fen2Yuan(String fen) {
//            if (fen == null) {
//                return "0";
//            }
//            if (fen.length() == 1) {
//                return "0.0" + fen;
//            } else if (fen.length() == 2) {
//                return "0." + fen;
//            } else {
//                return fen.substring(0, fen.length() - 2) + "." + fen.substring(fen.length() - 2, fen.length());
//            }
//
//        }
//
//        public static int getInt(String str, int value) {
//            int result = value;
//            try {
//                result = Integer.parseInt(str);
//            } catch (Exception e) {
//            }
//            return result;
//        }
//
//        public static int getInt(Object obj, int value) {
//            int result = value;
//            try {
//                result = Integer.parseInt(obj.toString());
//            } catch (Exception e) {
//            }
//            return result;
//        }
//
//        public static String getCurrentTime() {
//            String theFormatString = "yyyy-MM-dd HH:mm:ss";
//            GregorianCalendar todaysdate = new GregorianCalendar();
//            Date theDate = todaysdate.getTime();
//            if (theDate == null || theFormatString == null) {
//                return "";
//            }
//            String theDateString = "";
//            try {
//                SimpleDateFormat theDateFormater = new SimpleDateFormat(theFormatString);
//                theDateString = theDateFormater.format(theDate);
//            } catch (IllegalArgumentException theException) {
//                //
//            }
//            return theDateString;
//        }
//
//
//
//        public static String getCurrentTime(String theFormatString) {
//            return getCurrentTime(theFormatString, 0);
//        }
//
//        public static String getCurrentTime(String theFormatString, int days) {
//            // String theFormatString = "yyyy-MM-dd HH:mm:ss";
//            GregorianCalendar todaysdate = new GregorianCalendar();
//            todaysdate.add(Calendar.DAY_OF_MONTH, days); // 移动相应的天数。
//            Date theDate = todaysdate.getTime();
//            if (theDate == null || theFormatString == null) {
//                return "";
//            }
//            String theDateString = "";
//            try {
//                SimpleDateFormat theDateFormater = new SimpleDateFormat(theFormatString);
//                theDateString = theDateFormater.format(theDate);
//            } catch (IllegalArgumentException theException) {
//                //
//            }
//            return theDateString;
//        }
//
//        /*
//         * 整型转换为BCD型 public byte intToBcd(int i) { switch (i) { case 0: return (byte) 0;
//         * case 1: return (byte) 1; case 2: return (byte) 2; case 3: return (byte) 3;
//         * case 4: return (byte) 4; case 5: return (byte) 5; case 6: return (byte) 6;
//         * case 7: return (byte) 7; case 8: return (byte) 8; case 9: return (byte) 9;
//         * case 0x0b: return (byte) 0x0b; case 59: return (byte) 0x0e; } return
//         * (byte)0xff; }
//         */
//
//        // bcd码转换为String
//        public byte[] stringToBcd(String str) {
//            byte[] result = new byte[(str.length() + 1) / 2];
//            try {
//                if (str == null || str.length() == 0) {
//                    return new byte[0];
//                }
//                for (int i = 0; i < result.length; i++) {
//                    result[i] = (byte) 0xff;
//                    if (i * 2 < str.length()) {
//                        char c = str.charAt(i * 2);
//                        result[i] = getStringBcd(c);
//                    }
//                    if (i * 2 + 1 < str.length()) {
//                        char c = str.charAt(i * 2 + 1);
//                        result[i] = (byte) ((((int) result[i]) << 4 & 0xf0) + ((int) getStringBcd(c) & 0xf));
//                    } else {
//                        result[i] = (byte) ((((int) result[i]) << 4 & 0xf0) + (int) 0xf);
//                    }
//                }
//            } catch (Exception e) {
//            }
//            return result;
//        }
//
//        // byte[] bcd码 转换为String
//        public String bcdToString(byte[] bcd) {
//            String result = "";
//            for (int i = 0; bcd != null && i < bcd.length; i++) {
//                int tmp = bcd[i];
//                String c = getBcdString(tmp >> 4 & 0xf);
//                if (c == null) {
//                    break;
//                }
//                result += c;
//                c = getBcdString(tmp & 0xf);
//                if (c == null) {
//                    break;
//                }
//                result += c;
//            }
//            return result;
//        }
//
//        private byte getStringBcd(char i) {
//            Character c = new Character(i);
//            if (c.compareTo(new Character('0')) == 0) {
//                return (byte) 0;
//            }
//            if (c.compareTo(new Character('1')) == 0) {
//                return (byte) 1;
//            }
//            if (c.compareTo(new Character('2')) == 0) {
//                return (byte) 2;
//            }
//            if (c.compareTo(new Character('3')) == 0) {
//                return (byte) 3;
//            }
//            if (c.compareTo(new Character('4')) == 0) {
//                return (byte) 4;
//            }
//            if (c.compareTo(new Character('5')) == 0) {
//                return (byte) 5;
//            }
//            if (c.compareTo(new Character('6')) == 0) {
//                return (byte) 6;
//            }
//            if (c.compareTo(new Character('7')) == 0) {
//                return (byte) 7;
//            }
//            if (c.compareTo(new Character('8')) == 0) {
//                return (byte) 8;
//            }
//            if (c.compareTo(new Character('9')) == 0) {
//                return (byte) 9;
//            }
//            if (c.compareTo(new Character('*')) == 0) {
//                return (byte) 0x0b;
//            }
//            if (c.compareTo(new Character('#')) == 0) {
//                return (byte) 0x0C;
//            }
//            return (byte) 0xff;
//        }
//
//        private String getBcdString(int i) {
//            if (i < 10) {
//                return i + "";
//            }
//            switch (i) {
//                case 0x0A:
//                    return "";
//                case 0x0B:
//                    return "*";
//                case 0x0c:
//                    return "#";
//                case 0x0d:
//                    return "";
//                case 0x0e:
//                    return "";
//                case 0x0f:
//                    return null; // 结束字符
//            }
//            return "";
//        }
//
//        /**********************************************************
//         * 判断是否IP端口号"ip:port"
//         *
//         */
//        public boolean isIpPort(String ipPort) {
//            if (ipPort == null) {
//                return false;
//            }
//            String[] num = ipPort.split(":");
//            if (num.length != 2) {
//                return false;
//            }
//            if (!isIp(num[0])) {
//                return false;
//            }
//            if (!isPort(num[1])) {
//                return false;
//            }
//            return true;
//        }
//
//        public boolean isIp(String ip) {
//            if (ip == null) {
//                return false;
//            }
//            try {
//                String[] num = new String[4];
//                num[0] = ip.substring(0, ip.indexOf("."));
//                ip = ip.replaceFirst(num[0] + ".", "");
//                num[1] = ip.substring(0, ip.indexOf("."));
//                ip = ip.replaceFirst(num[1] + ".", "");
//                num[2] = ip.substring(0, ip.indexOf("."));
//                ip = ip.replaceFirst(num[2] + ".", "");
//                num[3] = ip;
//
//                if (num.length != 4) {
//                    return false;
//                }
//                if (!isInt(num[0])) {
//                    return false;
//                }
//                if (!isInt(num[1])) {
//                    return false;
//                }
//                if (!isInt(num[2])) {
//                    return false;
//                }
//                if (!isInt(num[3])) {
//                    return false;
//                }
//                if (Integer.parseInt(num[0]) > 255 || Integer.parseInt(num[0]) < 0) {
//                    return false;
//                }
//                if (Integer.parseInt(num[1]) > 255 || Integer.parseInt(num[1]) < 0) {
//                    return false;
//                }
//                if (Integer.parseInt(num[2]) > 255 || Integer.parseInt(num[2]) < 0) {
//                    return false;
//                }
//                if (Integer.parseInt(num[3]) > 255 || Integer.parseInt(num[3]) < 0) {
//                    return false;
//                }
//            } catch (Exception e) {
//                return false;
//            }
//            return true;
//        }
//
//        public boolean isPort(String port) {
//            if (!isInt(port)) {
//                return false;
//            }
//            if (Integer.parseInt(port) > 0xffff || Integer.parseInt(port) <= 0) {
//                return false;
//            }
//            return true;
//        }
//
//        /*******************************************************************
//         *
//         * 获取属性的类型 0：整型；1：字符型；2：浮点型 -1:表示监控不支持的属性
//         */
//        public int getAttributeType(String type) {
//            if ("Integer".equals(type)) {
//                return 0;
//            }
//            if ("int".equals(type)) {
//                return 0;
//            }
//            if ("java.lang.String".equals(type)) {
//                return 1;
//            }
//            if ("String".equals(type)) {
//                return 1;
//            }
//            if ("float".equals(type)) {
//                return 2;
//            }
//
//            return -1;
//        }
//
//        /*************************************************************
//         * 获取socket接受的请求数据 格式：len(4 byte)+type(1 byte)+msg "type" ＝ 接收到的请求类型 "result" ＝
//         * 接收结果：true、false "msg" ＝ 接收到的数据
//         *
//         */
//        public HashMap socketReceive(Socket socket) {
//            String str = null;
//            HashMap result = new HashMap();
//            DataInputStream dataRec = null;
//            int len = 0;
//            int type = 0;
//            byte[] body = new byte[0];
//            synchronized (socket) {
//                try {
//                    dataRec = new DataInputStream(socket.getInputStream());
//                } catch (IOException ioexception) { // IO错误
//                    return result;
//                }
//                try {
//                    len = dataRec.readInt();
//                    /*
//                     * byte[] l=new byte[4]; l[0]=dataRec.readByte(); l[1]=dataRec.readByte();
//                     * l[2]=dataRec.readByte(); l[3]=dataRec.readByte(); len=byte2Int(l);
//                     */
//                    // len=dataRec.readInt();
//                    type = dataRec.readByte();
//
//                    System.out.println("len=" + len);
//                    System.out.println("type=" + type);
//
//                    body = new byte[len - 5];
//                    for (int readBytes = 0; readBytes < len - 5;) {
//                        readBytes += dataRec.read(body, readBytes, len - 5 - readBytes);
//                    }
//                } catch (InterruptedIOException e) { // 超时
//                    return result;
//                } catch (IOException ioexception) { // IO错误
//                    return result;
//                } catch (Exception ioexception) { // 其它错误
//                    return result;
//                }
//            }
//            String msg = new String(body);
//            // System.out.println("msg="+msg);
//            result.put("result", "true");
//            result.put("type", type + "");
//            result.put("msg", msg);
//            return result;
//        }
//
//        /****************************************************************
//         * 发送socket数据 格式：len(4 byte)+type(1 byte)+msg
//         */
//        public boolean socketSend(Socket socket, int type, String msg) {
//            byte[] body = msg.getBytes();
//            int len = body.length + 5;
//            /*
//             * byte[] head=new byte[5]; byte[] l=int2Byte(len); head[0]=l[0]; head[1]=l[1];
//             * head[2]=l[2]; head[3]=l[3]; head[4]=(byte)type;
//             */
//            synchronized (socket) {
//                try {
//                    DataOutputStream dataSend = new DataOutputStream(socket.getOutputStream());
//                    // dataSend.write(head, 0, 5);
//                    dataSend.writeInt(len);
//                    dataSend.writeByte(type);
//                    if (body != null) {
//                        dataSend.write(body, 0, body.length);
//                    }
//                    dataSend.flush();
//                } catch (Exception e) {
//                    System.out.println("util.function.scoketSend() error:" + e);
//                    return false;
//                }
//            }
//            return true;
//        }
//
//        /**************************************************************************
//         *
//         * 将String转换成固定长度的byte数组
//         */
//        public byte[] getBytes(String src, int len) {
//            byte[] result = new byte[len];
//            try {
//                byte[] temp = src.getBytes();
//                System.arraycopy(temp, 0, result, 0, temp.length);
//
//            } catch (IndexOutOfBoundsException e) {
//                System.out.println("submitStruct String2Byte error:" + e);
//            } catch (ArrayStoreException e) {
//                System.out.println("submitStruct String2Byte error:" + e);
//            } catch (Exception e) {
//                System.out.println("submitStruct String2Byte error:" + e);
//            }
//            return result;
//        }
//
//        public byte[] getBytes(String[] src, int len) {
//            byte[] result = new byte[len * src.length];
//            for (int i = 0; i < src.length; i++) {
//                try {
//                    byte[] temp = src[i].getBytes("GB2312");
//                    // byte[] temp = src[i].getBytes();
//                    System.arraycopy(temp, 0, result, i * len, temp.length);
//                } catch (IndexOutOfBoundsException e) {
//                    System.out.println("submitStruct String2Byte error:" + e);
//                } catch (ArrayStoreException e) {
//                    System.out.println("submitStruct String2Byte error:" + e);
//                } catch (Exception e) {
//                    System.out.println("submitStruct String2Byte error:" + e);
//                }
//            }
//            return result;
//        }
//
//        /*************************************************************
//         * 数据初始化
//         *
//         */
//
//        public void memset(byte abyte0[], int i) {
//            for (int j = 0; j < i; j++) {
//                abyte0[j] = 0;
//
//            }
//        }
//
//        /****************************************************
//         *
//         * 数组复制，与System.arraycopy()一样
//         */
//        public int strcpy(byte abyte0[], byte abyte1[], int i, int j) {
//            int k;
//            for (k = 0; k < j; k++) {
//                abyte0[k + i] = abyte1[k];
//
//            }
//            abyte0[k + i] = 0;
//            return k;
//        }
//
//        /****************************************************************************
//         *
//         * MD5加密
//         *
//         */
//
//        public byte[] getMD5(byte[] b, int len) {
//            MessageDigest digest;
//            String algorithm = "MD5";
//            try {
//                digest = MessageDigest.getInstance(algorithm);
//                digest.update(b, 0, len);
//                return digest.digest();
//            } catch (Exception e) {
//                e.printStackTrace(System.err);
//                return null;
//            }
//        }
//
//        /*************************************************************
//         *
//         * byte[]转换成整型
//         *
//         */
//        public int byte2Int(byte b) {
//            int j = (int) b;
//            if (j >= 0) {
//                j = b;
//            } else {
//                j = 256 + b;
//            }
//            return j;
//        }
//
//        /*
//         * public int byte2Int(byte[] b) { int s = 0; for (int i = 0; i < 3; i++) { if
//         * (b[i] >= 0) s = s + b[i]; else s = s + 256 + b[i]; s = s 256; } if (b[3] >=
//         * 0) s = s + b[3]; else s = s + 256 + b[3]; return s; }
//         */
//        public int byte2Int(byte b[]) {
//            int i1, i2, i3, i4;
//            i1 = (int) (b[0]);
//            if (i1 < 0) {
//                i1 = (int) (256 + i1);
//            }
//            i2 = (int) (b[1]);
//            if (i2 < 0) {
//                i2 = (int) (256 + i2);
//            }
//            i3 = (int) (b[2]);
//            if (i3 < 0) {
//                i3 = (int) (256 + i3);
//            }
//            i4 = (int) (b[3]);
//            if (i4 < 0) {
//                i4 = (int) (256 + i4);
//            }
//            i4 = (int) (16777216 * i4 + 65536 * i3 + 256 * i2 + i1);
//            return i4;
//        }
//
//        public int byte2Int2(byte b[]) {
//            int i1, i2, i3, i4;
//            i1 = (int) (b[0]);
//            if (i1 < 0) {
//                i1 = (int) (256 + i1);
//            }
//            i2 = (int) (b[1]);
//            if (i2 < 0) {
//                i2 = (int) (256 + i2);
//            }
//            i4 = (int) (256 * i2 + i1);
//            return i4;
//        }
//
//        public int byte2Int(byte[] b, int start, int len) {
//            byte[] dat = new byte[len];
//            System.arraycopy(b, start, dat, 0, len);
//            return byte2Int(dat);
//        }
//
//        /*************************************************************
//         *
//         * 整型转换成byte[]
//         *
//         */
//        /*
//         * public byte[] int2Byte(int i) { byte abyte0[] = new byte[4]; abyte0[3] =
//         * (byte) i; i >>>= 8; abyte0[2] = (byte) i; i >>>= 8; abyte0[1] = (byte) i; i
//         * >>>= 8; abyte0[0] = (byte) i; return abyte0; }
//         */
//
//        public byte[] int2Byte(int i) {
//            byte abyte0[] = new byte[4];
//            abyte0[0] = (byte) i;
//            i >>>= 8;
//            abyte0[1] = (byte) i;
//            i >>>= 8;
//            abyte0[2] = (byte) i;
//            i >>>= 8;
//            abyte0[3] = (byte) i;
//            return abyte0;
//        }
//
//        public byte[] int2Byte(int i, int len) {
//            byte abyte0[] = new byte[len];
//            for (int j = 0; j < len; j++) {
//                abyte0[j] = (byte) i;
//                i >>>= 8;
//            }
//            return abyte0;
//        }
//
//        /********************************************************************
//         *
//         * 获取当前时间，格式MMDDHHMMSS
//         *
//         */
//        public String getCurrentTimeStamp() {
//            // return "994573501";
//            byte time[] = new byte[4];
//            GregorianCalendar todaysdate = new GregorianCalendar();
//            int month = todaysdate.get(Calendar.MONTH) + 1;
//            int day = todaysdate.get(Calendar.DAY_OF_MONTH);
//            int hour = todaysdate.get(Calendar.HOUR_OF_DAY);
//            int minute = todaysdate.get(Calendar.MINUTE);
//            int second = todaysdate.get(Calendar.SECOND);
//            String strMonth = Integer.toString(month);
//            String strDay = Integer.toString(day);
//            String strHour = Integer.toString(hour);
//            String strMinute = Integer.toString(minute);
//            String strSecond = Integer.toString(second);
//            if (day < 10) {
//                strDay = "0" + strDay;
//            }
//            if (month < 10) {
//                strMonth = "0" + strMonth;
//            }
//            if (hour < 10) {
//                strHour = "0" + strHour;
//            }
//            if (minute < 10) {
//                strMinute = "0" + strMinute;
//            }
//            if (second < 10) {
//                strSecond = "0" + strSecond;
//            }
//            return strMonth + strDay + strHour + strMinute + strSecond;
//        }
//
//        /***********************************************************************
//         * null to 0
//         *
//         */
//        public String nullTo0(String str) {
//            if (str == null) {
//                return "0";
//            }
//            return str;
//        }
//
//        public String nullToEmpty(String str) {
//            if (str == null) {
//                return "";
//            }
//            return str;
//        }
//
//        /************************************************************************
//         * 访问url，获取返回的数据
//         *
//         */
//        public String getUrlContent(String url) {
//            // System.out.println("url="+url);
//            String result = "";
//            try {
//                URL server = new URL(url);
//                URLConnection con = server.openConnection();
//                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                String dat = "";
//                while ((dat = in.readLine()) != null) {
//                    if (result.length() != 0) {
//                        result += "\r";
//                    }
//                    result += dat;
//                }
//                in.close();
//            } catch (Exception e) {
//                System.out.println("Function.getUrl(String url):" + e);
//            }
//
//            return result;
//        }
//
//        /********************************************************
//         * 获取一个目录下的不存在的文件名，全部采用数字 path:路径 ext:扩展名 len:文件名长度 return:返回不带路径的文件名（带扩张名）
//         */
//        public String getFileName(String path, String ext, int len) {
//            String fileName = "";
//            while (true) {
//                fileName = getRandom(len) + "." + ext;
//                File file = new File(path + fileName);
//                if (!file.exists()) {
//                    break;
//                }
//            }
//            return fileName;
//        }
//
//        /**************************************************
//         * 删除一个文件 fileName:文件的全路径
//         */
//        public boolean delFile(String fileName) {
//            boolean result = true;
//            File file = new File(fileName);
//            if (file.exists()) {
//                result = file.delete();
//            }
//            return result;
//        }
//
//        /*********************************************************************
//         * 文件换名
//         *
//         */
//        public boolean rename(String oldFileName, String newFileName) {
//            try {
//                File file = new File(oldFileName);
//                return file.renameTo(new File(newFileName));
//            } catch (Exception e) {
//            }
//            return false;
//        }
//
//        /*************************************************
//         * 保存一个文件
//         */
//        public boolean saveFile(InputStream input, String fileName) {
//            int bytesum = 0;
//            int byteread = 0;
//            boolean result = true;
//            try {
//                FileOutputStream fs = new FileOutputStream(fileName);
//                byte[] buffer = new byte[1444];
//                int length;
//                while ((byteread = input.read(buffer)) != -1) {
//                    bytesum += byteread;
//                    fs.write(buffer, 0, byteread);
//                }
//                input.close();
//            } catch (Exception e) {
//                System.out.println("function.saveFile(InputStream," + fileName + "):" + e);
//                result = false;
//            }
//            return result;
//        }
//
//        /*******************************************************************
//         * 获取start年到现在时间的所有年份
//         *
//         */
//
//        public String[] getYear(int start) {
//            int end = Integer.parseInt(getCurrentYear());
//            if (start > end) {
//                return new String[0];
//            }
//            String[] result = new String[end - start + 1];
//            int temp = start;
//            for (int i = 0; i < result.length; i++) {
//                result[result.length - i - 1] = temp + "";
//                temp++;
//            }
//            return result;
//        }
//
//        /******************************************************************
//         *
//         * 获取年月，格式200308
//         *
//         */
//        public String getYearMonth(String date) {
//            if (date == null || date.length() < 10) {
//                return "";
//            }
//            String result = date.substring(0, 7);
//            result = replace(result, "-", "");
//            return result;
//        }
//
//        /*******************************************************************
//         *
//         * 判断是否是手机号
//         *
//         */
//        public boolean isMobile(String mobile) {
//            if (mobile == null || mobile.length() < 11) {
//                return false;
//            }
//            // 其他判断.....
//            return true;
//        }
//
//        /********************************************************************
//         *
//         * 将一条长消息切割成几条短消息
//         *
//         */
//        public static String[] splitShortMessage(String content) {
//            return splitShortMessage(content, 70);
//        }
//
//        public static String[] splitShortMessage(String content, int size) {
//            if (content == null || "".equals(content)) {
//                return new String[0];
//            }
//            if (size <= 0) {
//                size = 45;
//            }
//            int len = content.length();
//            String[] result = new String[1];
//            if (len <= size) {
//                result[0] = content;
//                return result;
//            }
//            // int num=(len+size-1)/size;
//
//            /*
//             * int num = (len + size - 6) / (size - 5); //分割时前面加上（2-1、2-2）之类标识； result = new
//             * String[num]; size = size - 5;
//             */
//            int num = (len + size - 4) / (size - 3); // 分割时前面加上（2-1、2-2）之类标识；
//            result = new String[num];
//            size = size - 3;
//
//            for (int i = 0; i < len; i += size) {
//                int no = i / size + 1;
//                if (i < len - size) {
//                    result[i / size] = no + "/" + num + content.substring(i, i + size);
//                } else {
//                    result[i / size] = no + "/" + num + content.substring(i, len);
//                }
//            }
//
//            return result;
//        }
//
//        /*********************************************************************
//         *
//         * 获取一个年份时间的段 start:起始时间段 end：结束时间段
//         *
//         */
//        /*
//         * public String[] getYear(int start) { int
//         * end=Integer.parseInt(getCurrentYear()); if(start>end) return new String[0];
//         * String[] result=new String[end-start+1]; int temp=start; for(int
//         * i=0;i<result.length;i++) { result[i]=temp+""; temp++; } return result; }
//         */
//        /*************************************************************************
//         *
//         * 获取一个没有重复的随机数组
//         *
//         * size:对象的大小 len:多少个数据数
//         *
//         */
//        public Vector getRandom(Vector size, int num) {
//            if (size.size() == 0) {
//                return new Vector();
//            }
//            Vector result = new Vector();
//            Random r = new Random();
//            for (int i = 0; i < num; i++) {
//                if (size.size() == 0) {
//                    break;
//                }
//                int index = r.nextInt(size.size());
//                // System.out.println("size="+size.size()+" random="+index);
//                result.add(size.get(index));
//                size.remove(index);
//                if (size.size() == 1 && i < num - 1) {
//                    result.add(size.get(0));
//                    break;
//                }
//            }
//            return result;
//        }
//
//        /********************************************************************************************
//         *
//         * 设置字符格式转换
//         *
//         *********************************************************************************************/
//
//        public String setToDB(String str) {
//            if (str == null) {
//                return "";
//            }
//            return codeConver(str, "ISO8859_1", "GBK");
//        }
//
//        public static String getFromDB(String str) {
//            if (str == null) {
//                return "";
//            }
//            // return codeConver(str,"GB2312","ISO8859_1");
//            return str;
//            // return codeConver(str,"ISO8859_1","GB2312");
//        }
//
//
//        public static String retCodeNew() {
//
//            Map<String,String> params = new HashMap<String, String>();
//            params.put("time",System.currentTimeMillis()+"");
//            params.put("noncestr",Demo.getNonceStr());
//            try {
//                return JiMiUtil.createMD5Sign(params, "jmbj");
//            } catch (Exception e) {
//                e.printStackTrace();
//                return getCurrentTime("yyyyMMddHHmmss") + getRandom(6);
//            }
//        }
//
//        public static String retCode() {
//            //return getCurrentTime("yyyyMMddHHmmss") + getRandom(2);
//            return retCodeNew();
//        }
//
//        /*******************************************************************************
//         *
//         * 整型数据排序,大到小 type: big--大到小 small－－小到大
//         *********************************************************************************/
//        public int[] sort(int[] data, String type) {
//            boolean blnChange = true;
//            while (blnChange) {
//                blnChange = false;
//                for (int i = 0; i < data.length - 1; i++) {
//                    if ("big".equals(type)) {
//                        if (data[i + 1] > data[i]) {
//                            int temp = data[i + 1];
//                            data[i + 1] = data[i];
//                            data[i] = temp;
//                            blnChange = true;
//                        }
//                    }
//                    if ("small".equals(type)) {
//                        if (data[i + 1] < data[i]) {
//                            int temp = data[i + 1];
//                            data[i + 1] = data[i];
//                            data[i] = temp;
//                            blnChange = true;
//                        }
//                    }
//                }
//            }
//            return data;
//        }
//
//        /******************************************************************************
//         *
//         * 获取一个随机数的字符串
//         *
//         ********************************************************************************/
//        public static String getRandom(int len) {
//            String max = "";
//            Random r = new Random();
//            String result = "";
//            for (int i = 0; i < len; i++) {
//                result += r.nextInt(9);
//            }
//            return result;
//        }
//
//        /*************************************************************************************
//         *
//         * 获取时间格式： 31/12 12:20
//         *
//         */
//        public static String getFormatDate(String date) {
//            try {
//                return date.substring(8, 10) + "/" + date.substring(5, 7) + " " + date.substring(11, 16);
//            } catch (Exception e) {
//            }
//            return "";
//        }
//
//        /*******************************************************************************
//         *
//         * 将分钟转为时分的格式 19:00
//         *
//         *********************************************************************************/
//        public String getTime(String t) {
//            if (!isInt(t)) {
//                return "00:00";
//            }
//            int i = Integer.parseInt(t);
//            String[] s = new String[2];
//            s[0] = Integer.toString(i / 60);
//            int j = i % 60;
//            if (j < 10) {
//                s[1] = "0" + j;
//            } else {
//                s[1] = j + "";
//            }
//            return s[0] + ":" + s[1];
//        }
//
//        /***************************************************************************
//         *
//         * 获取数值的文字表示法
//         *
//         *****************************************************************************/
//        public String getSize(int size) {
//            String strSize = "0Byte";
//            if (size > 1024 * 1024 * 2) {
//                strSize = Integer.toString(size / 1024 / 1024) + "M";
//            } else if (size > 1024 * 2) {
//                strSize = Integer.toString(size / 1024) + "K";
//            } else {
//                strSize = size + "Byte";
//            }
//            return strSize;
//        }
//
//        /*********************************************************************
//         *
//         * 将日期（格式：2002－08－08）删除“－”，后转化为数值（20020808）
//         *
//         ***********************************************************************/
//        public int getDateValue(String day) {
//            return Integer.parseInt(day.substring(0, 4) + day.substring(5, 7) + day.substring(8, 10));
//        }
//
//        /*********************************************************************
//         *
//         * 计算一个日期加上几个月后的值
//         *
//         ************************************************************************/
//        public String getNextDate(String start, int num) {
//            String nextDay = "";
//            int intYear = Integer.parseInt(start.substring(0, 4));
//            int intMonth = Integer.parseInt(start.substring(5, 7));
//            String strDay = start.substring(8, 10);
//            intMonth += num;
//            intYear += (intMonth - 1) / 12;
//            intMonth = (intMonth - 1) % 12 + 1;
//            nextDay = intYear + "-" + intMonth + "-" + strDay;
//            if (intMonth < 10) {
//                nextDay = intYear + "-0" + intMonth + "-" + strDay;
//            }
//            return nextDay;
//        }
//
//        /*****************************************************************
//         *
//         * 判断输入日期的准确性，并以一定的格式输出 mode: "CN" -- xx年xx月xx号 "EN" -- xx-xx-xx
//         *****************************************************************/
//        public String getNoteDate(String year, String month, String day, String mode) {
//            String strResult = "";
//            // check year
//            if (!isInt(year) || year.length() != 4) {
//                year = getCurrentYear();
//                // check month
//            }
//            if (!isInt(month) || month.length() > 2 || Integer.parseInt(month) > 12) {
//                month = getCurrentMonth();
//                // check day
//            }
//            if (!isInt(day) || day.length() > 2 || Integer.parseInt(day) > 31) {
//                month = getCurrentDay();
//            }
//            if ("CN".equals(mode)) {
//                strResult = year + "年" + Integer.parseInt(month) + "月" + Integer.parseInt(day) + "日";
//            } else { // "EN" mode
//                if (month.length() == 1) {
//                    month = "0" + month;
//                }
//                if (day.length() == 1) {
//                    day = "0" + day;
//                }
//                strResult = year + "-" + month + "-" + day;
//            }
//            return strResult;
//        }
//
//        /**************************************************************
//         *
//         * 获取中文的星期标识格式
//         *
//         ***************************************************************/
//        public String getWeekDay(String id) {
//            String result = "";
//            if ("0".equals(id)) {
//                result = "星期天";
//            }
//            if ("1".equals(id)) {
//                result = "星期一";
//            }
//            if ("2".equals(id)) {
//                result = "星期二";
//            }
//            if ("3".equals(id)) {
//                result = "星期三";
//            }
//            if ("4".equals(id)) {
//                result = "星期四";
//            }
//            if ("5".equals(id)) {
//                result = "星期五";
//            }
//            if ("6".equals(id)) {
//                result = "星期六";
//            }
//            return result;
//        }
//
//        public static String getWeekDays(int id) {
//            String result = "";
//            if (7 == id) {
//                result = "周日";
//            }
//            if (1 == id) {
//                result = "周一";
//            }
//            if (2 == id) {
//                result = "周二";
//            }
//            if (3 == id) {
//                result = "周三";
//            }
//            if (4 == id) {
//                result = "周四";
//            }
//            if (5 == id) {
//                result = "周五";
//            }
//            if (6 == id) {
//                result = "周六";
//            }
//            return result;
//        }
//
//        /***************************************************************
//         * 获取一个时段的所有日期，包括start和end start格式：2002-08-08 end格式：2002-08-08 返回值：
//         * 【】【0】：日期，格式为：2002-08-08 【】【1】：星期几，星期日：0 星期六：6
//         *****************************************************************/
//        public String[][] getSomeDate(String start, String end) {
//            // 数据合法性检测
//            if (!isDate(start)) {
//                return new String[0][0];
//            }
//            if (!isDate(end)) {
//                return new String[0][0];
//            }
//            String end1 = replace(end, "-", "");
//            String start1 = replace(start, "-", "");
//            if (Integer.parseInt(end1) < Integer.parseInt(start1)) {
//                return new String[0][0];
//            }
//            // 时段统计
//            int i = 0;
//            String[] temp;
//            for (i = 0;;) {
//                i++;
//                temp = getDate(start, i);
//                start1 = replace(temp[0], "-", "");
//                if (Integer.parseInt(end1) <= Integer.parseInt(start1)) {
//                    break;
//                }
//                // if(temp[0].equals(end)) break;
//            }
//            String[][] result = new String[i + 1][2];
//            for (i = 0;;) {
//
//                temp = getDate(start, i);
//                result[i] = temp;
//                i++;
//                start1 = replace(temp[0], "-", "");
//                if (Integer.parseInt(end1) <= Integer.parseInt(start1)) {
//                    break;
//                }
//
//                // if(temp[0].equals(end)) break;
//            }
//            return result;
//        }
//
//        /**************************************************************
//         *
//         * 判断数据是否是合法的日期格式：2002-08-08
//         *
//         *****************************************************************/
//        public static boolean isDate(String date) {
//            if (date == null || date.length() != 10) {
//                return false;
//            }
//            String date1 = replace(date, "-", "");
//            if (!isInt(date1) || date1.length() != 8) {
//                return false;
//            }
//            return true;
//        }
//
//        /***************************************************************
//         *
//         * 计算日历，计算某个时间加day天后的日期
//         *
//         * result[0]：日期，格式：2002-02-02 result[1]：本月的1号是星期几 ( 星期日：0 星期六：6 )
//         *
//         ***************************************************************/
//        public String[] getDate(String start, int day) {
//            String[] result = { "", "" };
//            // 数据合法性检测
//            if (!isDate(start)) {
//                start = getCurrentDate();
//            }
//            int intYear = Integer.parseInt(start.substring(0, 4));
//            int intMonth = Integer.parseInt(start.substring(5, 7));
//            int intDay = Integer.parseInt(start.substring(8, 10));
//            GregorianCalendar cal = new GregorianCalendar(intYear, intMonth - 1, intDay);
//            cal.add(Calendar.DAY_OF_MONTH, day);
//            intYear = cal.get(Calendar.YEAR);
//            intMonth = cal.get(Calendar.MONTH) + 1;
//            intDay = cal.get(Calendar.DAY_OF_MONTH);
//            result[0] = intYear + "-";
//            if (intMonth > 9) {
//                result[0] += intMonth + "-";
//            } else {
//                result[0] += "0" + intMonth + "-";
//            }
//            if (intDay > 9) {
//                result[0] += intDay + "";
//            } else {
//                result[0] += "0" + intDay + "";
//            }
//            if (Calendar.MONDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[1] = "1";
//            }
//            if (Calendar.TUESDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[1] = "2";
//            }
//            if (Calendar.WEDNESDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[1] = "3";
//            }
//            if (Calendar.THURSDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[1] = "4";
//            }
//            if (Calendar.FRIDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[1] = "5";
//            }
//            if (Calendar.SATURDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[1] = "6";
//            }
//            if (Calendar.SUNDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[1] = "0";
//            }
//            return result;
//        }
//
//        public static String getDate(int pDays, String pFormat) {
//            if ("".equals(pFormat)) {
//                pFormat = "yyyy-MM-dd";
//            }
//            java.text.SimpleDateFormat DateFormat = new java.text.SimpleDateFormat(pFormat);
//            java.util.Date date = new java.util.Date();
//            long intdate = (long) date.getTime() + (long) (1000 * 60 * 60 * 24) * pDays;
//            java.util.Date redate = new java.util.Date(intdate);
//
//            return DateFormat.format(redate);
//        }
//
//        /***************************************************************
//         *
//         * 计算日历，获取本月的天数、本月的1号是星期几
//         *
//         * result[0]：年份 2002 2003 result[1]：月份 1 2 3 result[2]：本月的天数 result[3]：本月的1号是星期几
//         * ( 星期日：0 星期六：6 )
//         *
//         ***************************************************************/
//        public String[] calendar(String year, String month) {
//            // year : 2002 2003 ...
//            // month: 1 2 3 ....
//            String[] result = new String[4];
//            if (year == null || year.length() != 4 || !isInt(year)) {
//                year = getCurrentYear();
//            }
//            if (month == null || month.length() > 2 || !isInt(month)) {
//                month = getCurrentMonth();
//            }
//            GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);
//            result[0] = year;
//            result[1] = month;
//            result[2] = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + "";
//            if (Calendar.MONDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[3] = "1";
//            }
//            if (Calendar.TUESDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[3] = "2";
//            }
//            if (Calendar.WEDNESDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[3] = "3";
//            }
//            if (Calendar.THURSDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[3] = "4";
//            }
//            if (Calendar.FRIDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[3] = "5";
//            }
//            if (Calendar.SATURDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[3] = "6";
//            }
//            if (Calendar.SUNDAY == cal.get(Calendar.DAY_OF_WEEK)) {
//                result[3] = "0";
//            }
//            return result;
//        }
//
//        public boolean runCommand(String strCommand) {
//            try {
//                Runtime rt = Runtime.getRuntime();
//                Process p = null;
//                // System.out.println(strCommand);
//                p = rt.exec(strCommand);
//                java.io.InputStream in = p.getInputStream();
//                java.io.BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                String input = reader.readLine();
//                if (input == null) {
//                    return true;
//                } else {
//                    while (input != null) {
//                        System.out.println("interface error:" + input);
//                        input = reader.readLine();
//                    }
//
//                    return false;
//                }
//            } catch (IOException ex) {
//                System.out.println(ex);
//            }
//            return false;
//        }
//
//        public String cmd(String strCommand) {
//            String input = null;
//            try {
//                Runtime rt = Runtime.getRuntime();
//                Process p = null;
//                // System.out.println(strCommand);
//                p = rt.exec(strCommand);
//                java.io.InputStream in = p.getInputStream();
//                java.io.BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                input = reader.readLine();
//                // p.waitFor();
//                // Thread.sleep(20000);
//                // while(input==null||input.equals(""))
//                // input=reader.readLine();
//                // System.out.println("interface error:"+input);
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
//            return input;
//        }
//
//        public String command(String strCommand) {
//            String input = "";
//            try {
//                Runtime rt = Runtime.getRuntime();
//                Process p = null;
//                // System.out.println(strCommand);
//                p = rt.exec(strCommand);
//                java.io.InputStream in = p.getInputStream();
//                java.io.BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                input = reader.readLine();
//                System.out.println("interface error:" + input);
//                while (input != null) {
//                    System.out.println("interface error:" + input);
//                    input += reader.readLine() + "";
//                }
//            } catch (IOException ex) {
//                System.out.println(ex);
//            }
//            return input;
//        }
//
//        public void log(String s) {
//            try {
//                FileOutputStream out = new FileOutputStream("c:\\debug.txt", true);
//                out.write((s + "\r").getBytes("ISO8859_1"));
//                out.close();
//            } catch (IOException e) {
//            }
//        }
//
//        public boolean writeFile(String filename, String content, boolean append) {
//            boolean blnResult = true;
//            try {
//                FileOutputStream out = new FileOutputStream(filename, append);
//                out.write((content + "\r").getBytes());
//                out.close();
//            } catch (IOException e) {
//                blnResult = false;
//            }
//            return blnResult;
//        }
//
//        // 将字符串由StandardFrom编码格式转换为StandardTo编码的格式
//        public String codeConver(String Input, String StandardFrom, String StandardTo) {
//            byte[] bytes;
//            String conResult = new String();
//            try {
//                bytes = Input.getBytes(StandardFrom);
//                conResult = new String(bytes, StandardTo);
//            }
//
//            catch (Exception e) {
//                // debugging begins here
//                System.out.println(e);
//                // debugging ends here
//                return ("null");
//            }
//            return conResult;
//        }
//
//        // 将字符串由StandardFrom编码格式转换无编码格式的字符串
//        public String clearConver(String Input, String StandardFrom) {
//            byte[] bytes;
//            String conResult = new String();
//            try {
//                bytes = Input.getBytes(StandardFrom);
//                conResult = new String(bytes);
//            }
//
//            catch (Exception e) {
//                // debugging begins here
//                System.out.println(e);
//                // debugging ends here
//                return ("null");
//            }
//            return conResult;
//        }
//
//        // 将无编码格式的字符串转换为StandardFrom编码格式字符串
//        public String codeConver(String Input, String StandardTo) {
//            byte[] bytes;
//            String conResult = new String();
//            try {
//                bytes = Input.getBytes();
//                conResult = new String(bytes, StandardTo);
//            }
//
//            catch (Exception e) {
//                // debugging begins here
//                System.out.println(e);
//                // debugging ends here
//                return ("null");
//            }
//            return conResult;
//        }
//
//        public String getUrlIp(String url) {
//            InetAddress myServer = null;
//            try {
//                myServer = InetAddress.getByName(url);
//            } catch (UnknownHostException e) {
//                System.out.println("function. getUrlIp:" + e);
//            }
//            return myServer.getHostAddress();
//        }
//
//        public static String getDate(java.util.Date d) {
//            GregorianCalendar g = new GregorianCalendar();
//            g.setTime(d);
//            String date = "";
//            date = g.get(Calendar.YEAR) + "-" + Integer.toString(g.get(Calendar.MONTH) + 1) + "-" + g.get(Calendar.DATE)
//                    + " " + g.get(Calendar.HOUR_OF_DAY) + ":" + g.get(Calendar.MINUTE);
//            return date;
//        }
//
//        public String[] split(String string, String splitChar) {
//            String[] s = null;
//            if (string == null || string.equals("")) {
//                s = new String[0];
//                return s;
//            }
//            String temp = string;
//            int i = 0;
//            for (i = 0; temp.indexOf(splitChar) != -1; i++) {
//                temp = temp.substring(temp.indexOf(splitChar) + 1, temp.length());
//            }
//            s = new String[i + 1];
//            if (s.length == 1) {
//                s[0] = temp;
//            } else {
//                for (i = 0; true; i++) {
//                    s[i] = string.substring(0, string.indexOf(splitChar));
//                    string = string.substring(string.indexOf(splitChar) + 1, string.length());
//                    if (string.indexOf(splitChar) == -1) {
//                        s[i + 1] = string.substring(0);
//                        break;
//                    }
//                }
//            }
//            return s;
//        }
//
//        public String[] getPageUrl(String url1, String num, int total) {
//            // num should be from big to small
//            // url[0] -- next
//            // url[1] -- previous
//            String[] url = new String[2];
//            int intNum = 0;
//            url[0] = "#";
//            url[1] = "#";
//            if (isInt(num)) {
//                intNum = Integer.parseInt(num);
//            }
//            if (total > intNum) {
//                url[1] = url1 + Integer.toString(intNum + 1);
//            }
//            if (intNum > 1) {
//                url[0] = url1 + Integer.toString(intNum - 1);
//            }
//            return url;
//        }
//
//        public String[] getPageUrl(String url, String page, int total, int pagesize) {
//            // 0:first page
//            // 1:previous page
//            // 2:next page
//            // 3:last page
//            // 4:current page
//            // 5:total pages
//            // 6:total records
//            String[] s = new String[7];
//            int intPage = 1; // current page;
//            int intTotalPage = (total + pagesize - 1) / pagesize;
//            int temp = 0;
//            if (isInt(page)) {
//                intPage = Integer.parseInt(page);
//            }
//            if (intPage > intTotalPage) {
//                intPage = intTotalPage;
//            }
//            s[0] = url + "1";
//            if (intPage == 1) {
//                s[0] = "#";
//            }
//            temp = 1;
//            if (intPage > 2) {
//                temp = intPage - 1;
//            }
//            s[1] = url + temp;
//            if (temp == intPage) {
//                s[1] = "#";
//            }
//            temp = intTotalPage;
//            if (intPage < intTotalPage) {
//                temp = intPage + 1;
//            }
//            s[2] = url + temp;
//            if (temp == intPage) {
//                s[2] = "#";
//            }
//            s[3] = url + intTotalPage;
//            if (intTotalPage == intPage) {
//                s[3] = "#";
//            }
//            s[4] = "" + intPage;
//            s[5] = "" + intTotalPage;
//            s[6] = total + "";
//            return s;
//        }
//
//        public int getPageStart(String page, int PageSize) {
//            int intPage = 1;
//            if (isInt(page)) {
//                intPage = Integer.parseInt(page);
//            }
//            if (intPage < 1) {
//                intPage = 1;
//            }
//            return (intPage - 1) * PageSize;
//        }
//
//        public String ChangeSize(int size) {
//            String strSize = "";
//            if (size < 1024) {
//                strSize = size + "Byte";
//            }
//            if (size > 1024) {
//                strSize = Integer.toString((size + 512) / 1024) + "KB";
//            }
//            return strSize;
//        }
//
//        public static String CheckStringForHtml(String strString) {
//
//            String strResult = "";
//            int intI = 0;
//            if (strString == null) {
//                strString = "";
//            }
//            for (int i = 0; i < strString.length(); i++) {
//
//                switch (strString.charAt(i)) {
//                    // case '\r':
//                    // result=result+"<br>";
//                    case '\r':
//                        intI = 0;
//                        strResult = strResult + "<br>";
//                        break;
//                    // break;
//                    case ' ':
//                        if (intI == 2) {
//                            strResult = strResult + "&nbsp;";
//                            intI = 2;
//                        }
//                        if (intI == 1) {
//                            strResult = strResult + "&nbsp;&nbsp;";
//                            intI = 2;
//                        }
//                        if (intI == 0) {
//                            strResult = strResult + " ";
//                            intI = 1;
//                        }
//
//                        /*
//                         * if(intI==1) { strResult=strResult+"&nbsp;"; intI=2; } else {
//                         * strResult=strResult+" "; intI=1; }
//                         */
//                        break;
//                    case '\"':
//                        intI = 0;
//                        strResult = strResult + "&quot;";
//                        break;
//                    case '<':
//                        intI = 0;
//                        strResult = strResult + "&lt;";
//                        break;
//                    case '>':
//                        intI = 0;
//                        strResult = strResult + "&gt;";
//                        break;
//                    default:
//                        intI = 0;
//                        strResult = strResult + strString.charAt(i);
//                }
//            }
//            return strResult;
//        }
//
//        // 邮件的内容可能是HTML的格式，所以不能屏蔽html的标签，只能处理一些java的换行内容
//        public String CheckContentForHtml(String strString) {
//
//            String strResult = "";
//            int intI = 0;
//            if (strString == null) {
//                strString = "";
//            }
//            for (int i = 0; i < strString.length(); i++) {
//
//                switch (strString.charAt(i)) {
//                    // case '\r':
//                    // result=result+"<br>";
//                    case '\r':
//                        intI = 0;
//                        strResult = strResult + "<br>";
//                        break;
//                    // break;
//                    case ' ':
//                        if (intI == 2) {
//                            strResult = strResult + "&nbsp;";
//                            intI = 2;
//                        }
//                        if (intI == 1) {
//                            strResult = strResult + "&nbsp;&nbsp;";
//                            intI = 2;
//                        }
//                        if (intI == 0) {
//                            strResult = strResult + " ";
//                            intI = 1;
//                        }
//
//                        /*
//                         * if(intI==1) { strResult=strResult+"&nbsp;"; intI=2; } else {
//                         * strResult=strResult+" "; intI=1; }
//                         */
//                        break;
//                    default:
//                        intI = 0;
//                        strResult = strResult + strString.charAt(i);
//                }
//            }
//            return strResult;
//        }
//
//        public static String replace(String strSource, String strFrom, String strTo) {
//            if (strSource == null) {
//                return "";
//            }
//            String strDest = "";
//            int intFromLen = strFrom.length();
//            int intPos;
//
//            while ((intPos = strSource.indexOf(strFrom)) != -1) {
//                strDest = strDest + strSource.substring(0, intPos);
//                strDest = strDest + strTo;
//                strSource = strSource.substring(intPos + intFromLen);
//            }
//            strDest = strDest + strSource;
//            return strDest;
//            // return other.ChangeToISO8859(strDest);
//        }
//
//        public static String CheckStringForDB(String strString) {
//
//            String strResult = "";
//            if (strString == null) {
//                strString = "";
//
//                // strString=setToDB(strString);
//
//            }
//            for (int i = 0; i < strString.length(); i++) {
//                switch (strString.charAt(i)) {
//                    case '\'':
//                        strResult = strResult + "''";
//                        break;
//                    case '\\':
//                        strResult = strResult + "\\";
//                    default:
//                        strResult = strResult + strString.charAt(i);
//                }
//            }
//            return strResult;
//        }
//
//        public static boolean isInt(String str) {
//            if (str == null || str.length() == 0) {
//                return false;
//            }
//            str = str.trim();
//            try {
//                Integer int_num = new Integer(0);
//                int int_out = int_num.parseInt(str);
//            } catch (NumberFormatException e) {
//                return false;
//            }
//            return true;
//        }
//
//        public boolean isHexInt(String str) {
//            if (str == null || str.length() == 0) {
//                return false;
//            }
//            str = str.trim();
//            try {
//                Integer int_num = new Integer(0);
//                int int_out = int_num.parseInt(str, 16);
//            } catch (NumberFormatException e) {
//                return false;
//            }
//            return true;
//        }
//
//        /*
//         * public String getTomorrow() { java.util.Date date=ComputeDate(new
//         * java.util.Date(),Calendar.DAY_OF_MONTH,1); GregorianCalendar todaysdate = new
//         * GregorianCalendar(); todaysdate.setGregorianChange(date); int year =
//         * todaysdate.get(Calendar.YEAR); int month = todaysdate.get(Calendar.MONTH) +
//         * 1; int day = todaysdate.get(Calendar.DAY_OF_MONTH); String strYear =
//         * Integer.toString(year); String strMonth = Integer.toString(month); String
//         * strDay = Integer.toString(day); if (day < 10) strDay = "0" + strDay; if
//         * (month < 10) strMonth = "0" + strMonth; return strYear + "-" + strMonth + "-"
//         * + strDay; }
//         */
//
//        public String getCurrentDate() {
//            GregorianCalendar todaysdate = new GregorianCalendar();
//            int year = todaysdate.get(Calendar.YEAR);
//            int month = todaysdate.get(Calendar.MONTH) + 1;
//            int day = todaysdate.get(Calendar.DAY_OF_MONTH);
//            String strYear = Integer.toString(year);
//            String strMonth = Integer.toString(month);
//            String strDay = Integer.toString(day);
//            if (day < 10) {
//                strDay = "0" + strDay;
//            }
//            if (month < 10) {
//                strMonth = "0" + strMonth;
//            }
//            return strYear + "-" + strMonth + "-" + strDay;
//        }
//
//        /***************************************************
//         * 格式：20040909
//         *
//         */
//
//        public static String getToday() {
//            GregorianCalendar todaysdate = new GregorianCalendar();
//            int year = todaysdate.get(Calendar.YEAR);
//            int month = todaysdate.get(Calendar.MONTH) + 1;
//            int day = todaysdate.get(Calendar.DAY_OF_MONTH);
//            String strYear = Integer.toString(year);
//            String strMonth = Integer.toString(month);
//            String strDay = Integer.toString(day);
//            if (day < 10) {
//                strDay = "0" + strDay;
//            }
//            if (month < 10) {
//                strMonth = "0" + strMonth;
//            }
//            return strYear + strMonth + strDay;
//        }
//
//        public String getCurrentYear() {
//            GregorianCalendar todaysdate = new GregorianCalendar();
//            int year = todaysdate.get(Calendar.YEAR);
//            int month = todaysdate.get(Calendar.MONTH) + 1;
//            int day = todaysdate.get(Calendar.DAY_OF_MONTH);
//            String strYear = Integer.toString(year);
//            String strMonth = Integer.toString(month);
//            String strDay = Integer.toString(day);
//            if (day < 10) {
//                strDay = "0" + strDay;
//            }
//            if (month < 10) {
//                strMonth = "0" + strMonth;
//            }
//            return strYear;
//        }
//
//        public String getCurrentMonth() {
//            GregorianCalendar todaysdate = new GregorianCalendar();
//            int year = todaysdate.get(Calendar.YEAR);
//            int month = todaysdate.get(Calendar.MONTH) + 1;
//            int day = todaysdate.get(Calendar.DAY_OF_MONTH);
//            String strYear = Integer.toString(year);
//            String strMonth = Integer.toString(month);
//            String strDay = Integer.toString(day);
//            if (day < 10) {
//                strDay = "0" + strDay;
//            }
//            if (month < 10) {
//                strMonth = "0" + strMonth;
//            }
//            return strMonth;
//        }
//
//        public String getCurrentDay() {
//            GregorianCalendar todaysdate = new GregorianCalendar();
//            int year = todaysdate.get(Calendar.YEAR);
//            int month = todaysdate.get(Calendar.MONTH) + 1;
//            int day = todaysdate.get(Calendar.DAY_OF_MONTH);
//            String strYear = Integer.toString(year);
//            String strMonth = Integer.toString(month);
//            String strDay = Integer.toString(day);
//            if (day < 10) {
//                strDay = "0" + strDay;
//            }
//            if (month < 10) {
//                strMonth = "0" + strMonth;
//            }
//            return strDay;
//        }
//
//        /*********************
//         * Author:xuzengli Date:2004/04/02 Function：页面翻页功能，获得下一页的页码。 Parameters：
//         * CurrentPage:当前显示的页码。 PageCount:总页数。 PageType:翻页的类型，0－首页；1－上一页；2－下一页；3－尾页。
//         * Return：将要显示的页码。
//         *********************/
//        public int GetNextPage(int CurrentPage, int PageCount, String PageType) {
//            if (PageType == null) {
//
//            } else if ("0".equals(PageType)) {
//                CurrentPage = 0;
//            } else if ("1".equals(PageType)) {
//                if ((CurrentPage - 1) > 0) {
//                    // LastPage = CurrentPage;
//                    CurrentPage = CurrentPage - 1;
//                } else {
//                    CurrentPage = 0;
//                }
//            } else if ("2".equals(PageType)) {
//                if ((CurrentPage + 1) < PageCount) {
//                    CurrentPage = CurrentPage + 1;
//                    // LastPage = CurrentPage + RowCount;
//                } else {
//                    CurrentPage = PageCount - 1;
//                    // LastPage = superRecord.size();
//                }
//            } else if ("3".equals(PageType)) {
//                // LastPage = superRecord.size();
//                CurrentPage = PageCount - 1;
//                // superRecord.size() - (superRecord.size() % RowCount);
//            }
//            if (CurrentPage < 0) {
//                CurrentPage = 0;
//
//            }
//            return CurrentPage;
//        }
//
//        public static String getCurrentDatetime() {
//            GregorianCalendar todaysdate = new GregorianCalendar();
//            int year = todaysdate.get(Calendar.YEAR);
//            int month = todaysdate.get(Calendar.MONTH) + 1;
//            int day = todaysdate.get(Calendar.DAY_OF_MONTH);
//            int hour = todaysdate.get(Calendar.HOUR_OF_DAY);
//            int minute = todaysdate.get(Calendar.MINUTE);
//            int second = todaysdate.get(Calendar.SECOND);
//            String strYear = Integer.toString(year);
//            String strMonth = Integer.toString(month);
//            String strDay = Integer.toString(day);
//            String strHour = Integer.toString(hour);
//            String strMinute = Integer.toString(minute);
//            String strSecond = Integer.toString(second);
//            if (day < 10) {
//                strDay = "0" + strDay;
//            }
//            if (month < 10) {
//                strMonth = "0" + strMonth;
//            }
//            if (hour < 10) {
//                strHour = "0" + strHour;
//            }
//            if (minute < 10) {
//                strMinute = "0" + strMinute;
//            }
//            if (second < 10) {
//                strSecond = "0" + strSecond;
//            }
//
//            return strYear + "-" + strMonth + "-" + strDay + " " + strHour + ":" + strMinute + ":" + second;
//        }
//
//        public String ChangeToGB2312(String str) {
//            byte[] temp = null;
//            String strResult = null;
//            if (str == null) {
//                return "";
//            }
//            try {
//                temp = str.getBytes("GB2312");
//                strResult = new String(temp);
//            } catch (UnsupportedEncodingException e) {
//            }
//            return strResult;
//        }
//
//        public String ChangeToISO8859(String str) {
//            byte[] temp = null;
//            if (str == null) {
//                return "";
//            }
//            String strResult = null;
//            try {
//                temp = str.getBytes("ISO8859_1");
//                strResult = new String(temp);
//            } catch (UnsupportedEncodingException e) {
//            }
//            return strResult;
//        }
//
//        public String ChangeDateFormat(Calendar c) { // error
//            String date = "";
//            date = c.YEAR + "-" + c.MONTH + "-" + c.DATE + " " + c.HOUR + ":" + c.MINUTE;
//            return date;
//        }
//
//        /**
//         * 按照指定的格式输出描述日期的字符串
//         * <p>
//         * 示例：<br>
//         * 假设 theDate 为 2003-10-23 15:39:00.0<br>
//         * 假设 theFormatString 为 "yyyy.MM.dd"<br>
//         * 那么 结果为： "2003.10.23"
//         *
//         * </p>
//         *
//         * @param theDate
//         *            某个时间
//         * @param theFormatString
//         *            指定的格式；对于格式的描述参见 java.text.SimpleDateFormat；
//         * @return 按照格式描述日期的字符串
//         */
//        public static String formatDate(java.util.Date theDate, String theFormatString) {
//            if (theDate == null || theFormatString == null) {
//                return "";
//            }
//            String theDateString = "";
//            try {
//                SimpleDateFormat theDateFormater = new SimpleDateFormat(theFormatString);
//                theDateString = theDateFormater.format(theDate);
//            } catch (IllegalArgumentException theException) {
//                //
//            }
//            return theDateString;
//        }
//
//        /*********************************************************************
//         * yyyy-MM-dd 即：2004-05-12
//         *
//         */
//
//        public String getTomorrowDate(String format) {
//            return formatDate(getTomorrowDate(new java.util.Date()), format);
//        }
//
//        /**
//         * 根据基准时间计算其昨天的日期
//         *
//         * @param theBaseDate
//         *            基准时间；如果为 null，则表示当前时间
//         * @return
//         */
//        public java.util.Date getYesterdayDate(java.util.Date theBaseDate) {
//            return computeDate(theBaseDate, Calendar.DAY_OF_MONTH, -1); // “日期”减 1
//            // -> 昨天
//        }
//
//        /**
//         * 根据基准时间计算其明天的日期
//         *
//         * @param theBaseDate
//         *            基准时间；如果为 null，则表示当前时间
//         * @return
//         */
//        public java.util.Date getTomorrowDate(java.util.Date theBaseDate) {
//            return computeDate(theBaseDate, Calendar.DAY_OF_MONTH, +1); // “日期”加 1
//            // -> 明天
//        }
//
//        /**
//         * 根据基准时间计算其上个月的日期
//         *
//         * @param theBaseDate
//         *            基准时间；如果为 null，则表示当前时间
//         * @return
//         */
//        public java.util.Date getPreviousMonthDate(java.util.Date theBaseDate) {
//            return computeDate(theBaseDate, Calendar.MONTH, -1); // “月份”减 1 -> 上个月
//        }
//
//        /**
//         * 根据基准时间计算其下个月的日期
//         *
//         * @param theBaseDate
//         *            基准时间；如果为 null，则表示当前时间
//         * @return
//         */
//        public java.util.Date getNextMonthDate(java.util.Date theBaseDate) {
//            return computeDate(theBaseDate, Calendar.MONTH, +1); // “月份”加 1 -> 下个月
//        }
//
//        /**
//         * 统一的根据基准时间、日期项（公元/年/月/日/时/分/秒/毫秒）、偏移量来计算时间的方法
//         *
//         * @param theBaseDate
//         *            基准时间； 如果为 null，则表示当前时间
//         * @param CalendarItem
//         *            日期项； 公元/年/月/日/时/分/秒/毫秒 等等，必须为合法的 java.util.Calendar 的常量。
//         * @param Offset
//         *            偏移量
//         * @return 计算后的时间
//         */
//        public java.util.Date computeDate(java.util.Date theBaseDate, int CalendarItem, int Offset) {
//            GregorianCalendar theNewDate = new GregorianCalendar();
//            if (theBaseDate == null) {
//                theNewDate.setTime(new java.util.Date());
//            } else {
//                theNewDate.setTime(theBaseDate);
//            }
//            theNewDate.add(CalendarItem, Offset);
//            return theNewDate.getTime();
//        }
//
//        public boolean isAllInt(String str) { // 全部为数字
//            String Letters = "0123456789";
//            char CheckChar;
//            for (int i = 0; i < str.length(); i++) {
//                CheckChar = str.charAt(i);
//                if (Letters.indexOf(CheckChar) == -1) {
//                    return false;
//                }
//            }
//            return true;
//        }
//
//        /**
//         * 检测用户是否执行了二次刷新提交.
//         *
//         * @param session
//         * @param request
//         * @return boolean 第一次提交: 返回submitIntValue>0（大于0的数值） ; 二次提交: submitIntValue =-1;
//         */
//        public int getSubmitState(HttpServletRequest request, HttpSession session) {
//
//            int submitIntValue = -1;
//            String frowardAction = session.getAttribute("submitValue").toString(); // 第一次提交前的数值
//            // .
//            String submitValue = request.getParameter("submitValue") == null ? "" : request.getParameter("submitValue"); // 成功提交一次后的数值.
//
//            if (!submitValue.equals("") && isAllInt(submitValue)) {
//                int forward = getInt(frowardAction, -1);
//                int last = getInt(submitValue, -1);
//
//                if (last > forward) { // 表示：第一次提交。
//                    submitIntValue = last;
//                }
//            }
//            return submitIntValue;
//        }
//
//        public static String getStr(Object o) {
//            if (o == null) {
//                return "";
//            }
//            return o.toString();
//        }
//
//        /**********************************************************************
//         * 按字节分割，若有汉字处于被分割位置，则自动处理，分割短信带序号 byteLen：字节长度，不是字符长
//         */
//        public static String[] splitShotMessageByByteWithSeq(String content, int byteLen) {
//            try {
//                int len = byteLen; // 字节长度
//                String s = content;
//                if (s.length() <= len / 2 || s.getBytes().length <= len) { // 不用分割
//                    String[] tmp = new String[1];
//                    tmp[0] = s;
//                    return tmp;
//                }
//                char[] c = s.toCharArray();
//                // len = len - 5; //(1-2) 最多分9条
//                len = len - 3; // 1/2 最多分9条
//                int tmpLen = 0;
//                java.util.Vector vec = new java.util.Vector();
//                int cutStart = 0;
//                for (int i = 0; i < c.length; i++) {
//                    if (c[i] > 128) {
//                        tmpLen += 2;
//                    } else {
//                        tmpLen += 1;
//                    }
//                    if (tmpLen == len) { // 正好分割
//                        vec.add(s.substring(cutStart, i + 1));
//                        cutStart = i + 1;
//                        tmpLen = 0;
//                    } else if (tmpLen > len) { // 最后一字符为双字节
//                        vec.add(s.substring(cutStart, i));
//                        cutStart = i;
//                        tmpLen = 0;
//                        i = i - 1;
//                    } else if (i == c.length - 1) { // 最后一条短信
//                        vec.add(s.substring(cutStart, i + 1));
//                    }
//                }
//                String[] result = new String[vec.size()];
//                for (int i = 0; i < vec.size(); i++) {
//                    int seq = i + 1;
//                    // result[i] = "(" + seq + "-" + vec.size() + ")" + (String)
//                    // vec.get(i);
//                    result[i] = seq + "/" + vec.size() + (String) vec.get(i);
//                }
//
//                return result;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return new String[0];
//        }
//
//        /**********************************************************************
//         * 按字节分割，若有汉字处于被分割位置，则自动处理,前面不添加序号 byteLen：字节长度，不是字符长
//         */
//        public static String[] splitShotMessageByByteNoSeq(String content, int byteLen) {
//            try {
//                int len = byteLen; // 字节长度
//                String s = content;
//                if (s.length() <= len / 2 || s.getBytes().length <= len) { // 不用分割
//                    s = s;
//                }
//                char[] c = s.toCharArray();
//                int tmpLen = 0;
//                java.util.Vector vec = new java.util.Vector();
//                int cutStart = 0;
//                for (int i = 0; i < c.length; i++) {
//                    if (c[i] > 128) {
//                        tmpLen += 2;
//                    } else {
//                        tmpLen += 1;
//                    }
//                    if (tmpLen == len) { // 正好分割
//                        vec.add(s.substring(cutStart, i + 1));
//                        cutStart = i + 1;
//                        tmpLen = 0;
//                    } else if (tmpLen > len) { // 最后一字符为双字节
//                        vec.add(s.substring(cutStart, i));
//                        cutStart = i;
//                        tmpLen = 0;
//                        i = i - 1;
//                    } else if (i == c.length - 1) { // 最后一条短信
//                        vec.add(s.substring(cutStart, i + 1));
//                    }
//                }
//                String[] result = new String[vec.size()];
//                for (int i = 0; i < vec.size(); i++) {
//                    result[i] = (String) vec.get(i);
//                }
//                return result;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return new String[0];
//        }
//
//        // 根据起始号码生成号段
//        public static List makeCodeSeg(String startCode, String endCode) {
//            if (startCode.length() != endCode.length()) {// 起始号码长度不对
//                return null;
//            }
//            String headCode = null;
//            int headCodeLen = -1;
//            for (int i = 0; i < startCode.length() - 1; i++) {
//                if (startCode.charAt(i) == endCode.charAt(i)) {
//                    headCodeLen = i;
//                    headCode = startCode.substring(0, headCodeLen);
//                    break;
//                }
//            }
//            if (headCodeLen == -1) {
//                return null;
//            }
//            int startNum = Integer.parseInt(startCode.substring(headCodeLen));
//            int endNum = Integer.parseInt(endCode.substring(headCodeLen));
//            List result = new ArrayList();
//            for (int i = startNum; i <= endNum; i++) {
//                String tmpCode = Integer.toString(i);
//                tmpCode += headCode;
//                // 长度的格式化
//                for (int j = tmpCode.length(); j < (startCode.length() - j); j++) {
//                    tmpCode = "0" + tmpCode;
//                }
//                result.add(tmpCode);
//            }
//            return result;
//        }
//
//        public static String getDnsFromUrl(String url) {
//            String Dns = "";
//            if (url.substring(0, 5).equals("http:")) {
//                String head = url.substring(7);
//                String[] sp = head.split("/");
//                String[] sp2 = sp[0].split(":");
//                Dns = sp2[0];
//            } else if (url.substring(0, 6).equals("https:")) {
//                String head = url.substring(8);
//                String[] sp = head.split("/");
//                String[] sp2 = sp[0].split(":");
//                Dns = sp2[0];
//            }
//            return Dns;
//        }
//
//        public static String getNMonthBeforeCurrentMonth(int n) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.MONTH, -n);
//            return formater.format(cal.getTime());
//        }
//
//        public static Date parseDate(String date, String format) {
//            try {
//                SimpleDateFormat formater = new SimpleDateFormat(format == null ? "yyyy-MM-dd HH:mm" : format);
//                return formater.parse(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        public static String getMonthBeforeCurrentMonth(int n) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.MONTH, -n);
//            return formater.format(cal.getTime());
//        }
//
//        public static String getMonthBeforeCurrentMonthOther(int n) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.MONTH, -n);
//            return formater.format(cal.getTime());
//        }
//
//        public static String getMonthBeforeCurrentMonthYM(int n) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.MONTH, -n);
//            return formater.format(cal.getTime());
//        }
//
//        public static String getDateBefore(int day, String format) {
//            SimpleDateFormat formater = new SimpleDateFormat(format);
//            Calendar now = Calendar.getInstance();
//            now.setTime(new Date());
//            now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
//            return formater.format(now.getTime());
//        }
//
//        public static Date getDateBefore(Date d, int day) {
//            Calendar now = Calendar.getInstance();
//            now.setTime(d);
//            now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
//            return now.getTime();
//        }
//
//        /**
//         * 得到几天前的时间
//         *
//         * @param d
//         * @param day
//         * @return
//         */
//        public static String getDateBefore(int day) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Calendar now = Calendar.getInstance();
//            now.setTime(new Date());
//            now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
//            return formater.format(now.getTime());
//        }
//
//        /**
//         * 判断某个日期和当前时间是否操作diffDay。
//         *
//         * @param dateTime
//         * @param diffDay
//         * @return
//         */
//        public static boolean diffNowOfDay(Date dateTime, double diffDay) {
//            if (dateTime == null)
//                return false;
//
//            Date startTime = new Date();
//            BigDecimal diff = new BigDecimal(startTime.getTime() - dateTime.getTime());
//            double result = diff.divide(new BigDecimal(24 * 3600 * 1000), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            // System.out.println("时间差：" + result);
//            return result >= diffDay;
//        }
//
//        public static String[] getTokenizer(String string, String format) {
//            StringTokenizer st = new StringTokenizer(string, format);
//            int i = st.countTokens();
//            String[] a = new String[i];
//            int k = 0;
//            while (st.hasMoreTokens()) {
//                a[k] = st.nextToken();
//                k++;
//            }
//            return a;
//        }
//
//        public static String getMonthBeforeCurrentMonths(int n) {
//            n = n - 1;
//            SimpleDateFormat formater = new SimpleDateFormat("MM");
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.MONTH, -n);
//            return formater.format(cal.getTime());
//        }
//
//        public static String getDayBeforeCurrentMonths(int n) {
//            n = n - 1;
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.MONTH, -n);
//            return formater.format(cal.getTime());
//        }
//
//        public static String getDayAfterCurrentMonths(int n) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.MONTH, n);
//            return formater.format(cal.getTime());
//        }
//
//        public static int getYears() {
//            GregorianCalendar todaysdate = new GregorianCalendar();
//            int year = todaysdate.get(Calendar.YEAR);
//            return year;
//        }
//
//        public static String firstMonthDate() {
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(new Date());
//            cal.set(Calendar.DAY_OF_MONTH, 1);
//            return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//        }
//
//        public static String DateFormatToString(Date date) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
//            return formater.format(date);
//        }
//
//        public static String DateFormatToString(Date date, String format) {
//            SimpleDateFormat formater = new SimpleDateFormat(format);
//            return formater.format(date);
//        }
//
//        public static String DateFormatToString(String str, Date date) {
//            SimpleDateFormat formater = new SimpleDateFormat(str);
//            return formater.format(date);
//        }
//
//        public static int count(String str, char ch) {
//            int count = 0;
//            for (int i = 0; i < str.length(); i++) {
//                count = (str.charAt(i) == ch) ? count + 1 : count;
//            }
//            return count;
//        }
//
//        /**
//         * 生成自动编码
//         *
//         * @param l
//         * @return
//         */
//        public static long generateOrderCode(long l) {
//            String str = new SimpleDateFormat("yyMMdd").format(new java.util.Date());
//            long m = Long.parseLong((str)) * 1000;
//            long ret = m + l;
//            return ret;
//        }
//
//        /**
//         * 生成自动编码
//         *
//         * @param l
//         * @return
//         */
//        public static long generateDirverOrderCode(long l) {
//            String str = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
//            long m = Long.parseLong((str)) * 100000;
//            long ret = m + l;
//            return ret;
//        }
//
//        /**
//         * 生成时间自动编码(年月日时分秒)
//         *
//         * @param l
//         * @return
//         */
//        public static long generateTimeCode() {
//            String str = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
//            long m = Long.parseLong((str));
//            return m;
//        }
//        /*
//         * public static void main(String[] args) { Function fun = new Function();
//         * System.out.println(fun.getCurrentDate()); }
//         */
//
//        /**
//         * 随机生成15位 0-9 a-z A-Z的随机码
//         */
//        public static String retRandom() {
//            char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
//                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
//                    'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
//                    'X', 'Y', 'Z' };
//            int len = codeSequence.length;
//            // 创建一个随机数生成器类
//            Random random = new Random();
//            StringBuffer sbf = new StringBuffer();
//            for (int i = 0; i < 7; i++) {
//                // 得到随机产生的验证码数字。
//                String strRand = String.valueOf(codeSequence[random.nextInt(len)]);
//                sbf.append(strRand);
//            }
//            return sbf.toString();
//        }
//
//        public static boolean checkDate(String date, String format) {
//            DateFormat df = new SimpleDateFormat(format);
//            Date d = null;
//            try {
//                d = df.parse(date);
//            } catch (Exception e) {
//                // 如果不能转换,肯定是错误格式
//                return false;
//            }
//            return true;
//        }
//
//        public static String dateDiffHourMin(String startTime, String endTime, String format) {
//            // 按照传入的格式生成一个simpledateformate对象
//            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//            long nh = 1000 * 60 * 60;// 一小时的毫秒数
//            long nm = 1000 * 60;// 一分钟的毫秒数
//            long ns = 1000;// 一秒钟的毫秒数
//            long diff;
//            String result = "";
//            try {
//                boolean isresult = false;
//                String[] startTimes = startTime.split(":");
//                String[] endTimes = endTime.split(":");
//                if (startTimes[0] != null && endTimes[0] != null) {
//                    int startHours = Integer.parseInt(startTimes[0]);
//                    int endHours = Integer.parseInt(endTimes[0]);
//                    if (startHours > endHours) {
//                        isresult = true;
//                    }
//                }
//
//                if (isresult) {
//                    // 获得两个时间的毫秒时间差异
//                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                    Calendar c = Calendar.getInstance();
//                    Date dTime = Function.parseDate(Function.getCurrentTime("yyyy-MM-dd"), "yyyy-MM-dd");
//                    c.setTime(dTime);
//                    c.add(Calendar.DATE, 1);
//                    String strTime = Function.DateFormatToString(c.getTime(), "yyyy-MM-dd");
//                    diff = sd.parse(strTime + " " + endTime).getTime()
//                            - sd.parse(Function.getCurrentTime("yyyy-MM-dd") + " " + startTime).getTime();
//                    long day = diff / nd;// 计算差多少天
//                    long hour = diff % nd / nh;// 计算差多少小时
//                    long min = diff % nd % nh / nm;// 计算差多少分钟
//                    long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//                    result = hour + "小时" + min + "分";
//                } else {
//                    SimpleDateFormat sd = new SimpleDateFormat(format);
//                    // 获得两个时间的毫秒时间差异
//                    diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
//                    // long day = diff / nd;// 计算差多少天
//                    long hour = diff % nd / nh;// 计算差多少小时
//                    long min = diff % nd % nh / nm;// 计算差多少分钟
//                    // long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//                    result = hour + "小时" + min + "分";
//                }
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//
//        public static int dateDiffTotalMin(String startTime, String endTime, String format) {
//            // 按照传入的格式生成一个simpledateformate对象
//            int totalMin = 0;
//            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//            long nh = 1000 * 60 * 60;// 一小时的毫秒数
//            long nm = 1000 * 60;// 一分钟的毫秒数
//            long ns = 1000;// 一秒钟的毫秒数
//            long diff;
//            String result = "";
//            try {
//                boolean isresult = false;
//                String[] startTimes = startTime.split(":");
//                String[] endTimes = endTime.split(":");
//                if (startTimes[0] != null && endTimes[0] != null) {
//                    int startHours = Integer.parseInt(startTimes[0]);
//                    int endHours = Integer.parseInt(endTimes[0]);
//                    if (startHours > endHours) {
//                        isresult = true;
//                    }
//                }
//
//                if (isresult) {
//                    // 获得两个时间的毫秒时间差异
//                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                    Calendar c = Calendar.getInstance();
//                    Date dTime = Function.parseDate(Function.getCurrentTime("yyyy-MM-dd"), "yyyy-MM-dd");
//                    c.setTime(dTime);
//                    c.add(Calendar.DATE, 1);
//                    String strTime = Function.DateFormatToString(c.getTime(), "yyyy-MM-dd");
//                    diff = sd.parse(strTime + " " + endTime).getTime()
//                            - sd.parse(Function.getCurrentTime("yyyy-MM-dd") + " " + startTime).getTime();
//                    long day = diff / nd;// 计算差多少天
//                    long hour = diff % nd / nh;// 计算差多少小时
//                    long min = diff % nd % nh / nm;// 计算差多少分钟
//                    long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//                    totalMin = (int) (hour * 60 + min);
//                } else {
//                    SimpleDateFormat sd = new SimpleDateFormat(format);
//                    // 获得两个时间的毫秒时间差异
//                    diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
//                    // long day = diff / nd;// 计算差多少天
//                    long hour = diff % nd / nh;// 计算差多少小时
//                    long min = diff % nd % nh / nm;// 计算差多少分钟
//                    // long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//                    totalMin = (int) (hour * 60 + min);
//                }
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            return totalMin;
//        }
//
//        /**
//         * 返回时间差
//         *
//         * @param nowDate
//         * @param frontDate
//         * @return
//         */
//        public static long deteDiffDay(String nowDate, String frontDate) {
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            Date now;
//            long day = 0;
//            try {
//                now = df.parse(nowDate);
//                Date date = df.parse(frontDate);
//                long l = date.getTime() - now.getTime();
//                day = l / (24 * 60 * 60 * 1000);
//            } catch (ParseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return day;
//        }
//
//        /**
//         * 返回时间差
//         *
//         * @param nowDate
//         * @param frontDate
//         * @return
//         */
//        public static DateBean deteDiffXc(String nowDate, String frontDate) {
//            DateBean bean = new DateBean();
//            try {
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date now = df.parse(nowDate);
//                Date date = df.parse(frontDate);
//                long l = now.getTime() - date.getTime();
//                long day = l / (24 * 60 * 60 * 1000);
//                long hour = (l / (60 * 60 * 1000) - day * 24);
//                long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
//                long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//
//                bean.setDay(day);
//                bean.setHour(hour);
//                bean.setMin(min);
//                // System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return bean;
//        }
//
//        /**
//         * 返回时间差分钟
//         *
//         * @param nowDate
//         * @param frontDate
//         * @return
//         */
//        public static int deteDiffXcMin(String frontDate) {
//            int totalMin = 0;
//            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//            long nh = 1000 * 60 * 60;// 一小时的毫秒数
//            long nm = 1000 * 60;// 一分钟的毫秒数
//            long ns = 1000;// 一秒钟的毫秒数
//            long diff;
//            try {
//                String nowDate = Function.getCurrentTime("yyyy-MM-dd HH:mm");
//                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                // 获得两个时间的毫秒时间差异
//                diff = sd.parse(nowDate).getTime() - sd.parse(frontDate).getTime();
//                long day = diff / nd;// 计算差多少天
//                long hour = diff % nd / nh;// 计算差多少小时
//                long min = diff % nd % nh / nm;// 计算差多少分钟
//                // long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//                totalMin = (int) (day * 24 * 60 + hour * 60 + min);
//                // System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return totalMin;
//        }
//
//        /**
//         * 返回时间差分钟
//         *
//         * @param nowDate
//         * @param frontDate
//         * @return
//         */
//        public static int deteDiffXcMin(String frontDate, String afterDate) {
//            int totalMin = 0;
//            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//            long nh = 1000 * 60 * 60;// 一小时的毫秒数
//            long nm = 1000 * 60;// 一分钟的毫秒数
//            long ns = 1000;// 一秒钟的毫秒数
//            long diff;
//            try {
//                // String nowDate=Function.getCurrentTime("yyyy-MM-dd HH:mm");
//                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                // 获得两个时间的毫秒时间差异
//                diff = sd.parse(frontDate).getTime() - sd.parse(afterDate).getTime();
//                long day = diff / nd;// 计算差多少天
//                long hour = diff % nd / nh;// 计算差多少小时
//                long min = diff % nd % nh / nm;// 计算差多少分钟
//                // long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//                totalMin = (int) (day * 24 * 60 + hour * 60 + min);
//                // System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return totalMin;
//        }
//
//        /**
//         * 返回时间差分钟
//         *
//         * @param nowDate
//         * @param frontDate
//         * @return
//         */
//        public static int deteAfterDiffXcMin(String afterDate) {
//            int totalMin = 0;
//            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//            long nh = 1000 * 60 * 60;// 一小时的毫秒数
//            long nm = 1000 * 60;// 一分钟的毫秒数
//            long ns = 1000;// 一秒钟的毫秒数
//            long diff;
//            try {
//                String nowDate = Function.getCurrentTime("yyyy-MM-dd HH:mm");
//                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                // 获得两个时间的毫秒时间差异
//                diff = sd.parse(afterDate).getTime() - sd.parse(nowDate).getTime();
//                long day = diff / nd;// 计算差多少天
//                long hour = diff % nd / nh;// 计算差多少小时
//                long min = diff % nd % nh / nm;// 计算差多少分钟
//                // long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//                totalMin = (int) (day * 24 * 60 + hour * 60 + min);
//                // System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return totalMin;
//        }
//        /**
//         * 日期差
//         * @param date1
//         * @param date2
//         * @return
//         */
//        public static int daysBetween(Date date1,Date date2)
//        {
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(date1);
//            long time1 = cal.getTimeInMillis();
//            cal.setTime(date2);
//            long time2 = cal.getTimeInMillis();
//            long between_days=(time2-time1)/(1000*3600*24);
//
//            return Integer.parseInt(String.valueOf(between_days));
//        }
//        /**
//         * 得到几天后的时间
//         *
//         * @param d
//         * @param day
//         * @return
//         */
//        public static String getDateAfter(int day) {
//            SimpleDateFormat formater = new SimpleDateFormat("MM-dd");
//            Calendar now = Calendar.getInstance();
//            now.setTime(new Date());
//            now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
//            return formater.format(now.getTime());
//        }
//
//        public static String getDateAfterYMD(int day) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
//            Calendar now = Calendar.getInstance();
//            now.setTime(new Date());
//            now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
//            return formater.format(now.getTime());
//        }
//
//        public static String getDateAfterYMDHM(int day) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            Calendar now = Calendar.getInstance();
//            now.setTime(new Date());
//            now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
//            return formater.format(now.getTime());
//        }
//
//        public static String getDateAfterYMD(String frontDate, int day) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
//            Calendar now = Calendar.getInstance();
//            try {
//                Date aa = null;
//                aa = formater.parse(frontDate);
//                now.setTime(aa);
//            } catch (ParseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
//            return formater.format(now.getTime());
//        }
//
//        public static String getDateAfterYMDHH(String frontDate, int day) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH");
//            Calendar now = Calendar.getInstance();
//            try {
//                Date aa = null;
//                aa = formater.parse(frontDate);
//                now.setTime(aa);
//            } catch (ParseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
//            return formater.format(now.getTime());
//        }
//
//        public static String dayForWeek(int day) throws Exception {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            Calendar c = Calendar.getInstance();
//            c.setTime(new Date());
//            c.set(Calendar.DATE, c.get(Calendar.DATE) + day);
//            int dayForWeek = 0;
//            if (c.get(Calendar.DAY_OF_WEEK) == 1) {
//                dayForWeek = 7;
//            } else {
//                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
//            }
//            return getWeekDays(dayForWeek);
//        }
//
//        public static String retDayChangeShwo(String reserveTime) {
//            if (StringUtil.isNotBlank(reserveTime) && reserveTime.length() > 10) {
//                String year = reserveTime.substring(0, 4);
//                String month = reserveTime.substring(5, 7);
//                String day = reserveTime.substring(8, 10);
//                String point = reserveTime.substring(11, 13);
//                String time = "";
//                if (month.compareTo("10") < 0) {
//                    month = reserveTime.substring(6, 7);
//                }
//
//                if (day.compareTo("10") < 0) {
//                    day = reserveTime.substring(9, 10);
//                }
//
//                if (point.compareTo("10") < 0) {
//                    point = reserveTime.substring(12, 13);
//                    time = "上午";
//                } else if (point.compareTo("12") <= 0) {
//                    time = "中午";
//                } else if (point.compareTo("18") <= 0) {
//                    point = String.valueOf(Integer.parseInt(point) - 12);
//                    time = "下午";
//                } else if (point.compareTo("24") <= 0) {
//                    point = String.valueOf(Integer.parseInt(point) - 12);
//                    time = "晚上";
//                }
//                reserveTime = year + "." + month + "." + day + " " + time + point + "点";
//            }
//            return reserveTime;
//        }
//
//        public static String retWxDate() {
//            Calendar cal = Calendar.getInstance();
//            int week = cal.get(Calendar.DAY_OF_WEEK);
//            String ret = "";
//            if (week == 1) {
//                ret = Function.getDateBefore(2, "yyyy-MM-dd");
//            } else if (week == 2) {
//                ret = Function.getDateBefore(3, "yyyy-MM-dd");
//            } else if (week == 3) {
//                ret = Function.getDateBefore(1, "yyyy-MM-dd");
//            } else if (week == 4) {
//                ret = Function.getDateBefore(2, "yyyy-MM-dd");
//            } else if (week == 5) {
//                ret = Function.getDateBefore(3, "yyyy-MM-dd");
//            } else if (week == 6) {
//                ret = Function.getDateBefore(4, "yyyy-MM-dd");
//            } else if (week == 7) {
//                ret = Function.getDateBefore(1, "yyyy-MM-dd");
//            }
//            return ret;
//        }
//
//        /***
//         * 判断 String 是否是 int
//         *
//         * @param input
//         * @return
//         */
//        public static boolean isInteger(String input) {
//            Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
//            return mer.find();
//        }
//
//        public static boolean checkDateWeek(String bDate) {
//            try {
//                DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//                Date bdate = format1.parse(bDate);
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(bdate);
//                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
//                        || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//                    return true;
//                }
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return false;
//        }
//
//        public static String lastWeekData(int weekDay) {
//            Calendar cal = Calendar.getInstance();
//            // n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
//            String sday = "";
//            String mday = "";
//            String smday = "";
//            try {
//                String today = dayForWeek(0);
//                if (today.equals("周日")) {
//                    cal.add(Calendar.DATE, -1);
//                } else {
//                    cal.add(Calendar.DATE, 0);
//                }
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                cal.add(Calendar.DATE, 0);
//            }
//
//            // cal.add(Calendar.DATE, 0);
//            // 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
//            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//            mday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//            cal.add(Calendar.DATE, -1);
//            cal.set(Calendar.DAY_OF_WEEK, weekDay);
//            sday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//
//            return sday;
//        }
//
//        public static String lastDateWeek() {
//            Calendar cal = Calendar.getInstance();
//            // n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
//            String sday = "";
//            String mday = "";
//            String smday = "";
//            try {
//                String today = dayForWeek(0);
//                if (today.equals("周日")) {
//                    cal.add(Calendar.DATE, -1);
//                } else {
//                    cal.add(Calendar.DATE, 0);
//                }
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                cal.add(Calendar.DATE, 0);
//            }
//
//            // cal.add(Calendar.DATE, 0);
//            // 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
//            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//            mday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//            cal.add(Calendar.DATE, -1);
//            cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
//            sday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//
//            smday = "'" + sday + "','" + mday + "'";
//            return smday;
//        }
//
//        public static String lastWeekMonth() {
//            String sday = "";
//            String mday = "";
//            String smday = "";
//            Calendar instance = Calendar.getInstance();
//            instance.add(Calendar.MONTH, 0);// 月份+1
//            instance.add(Calendar.DAY_OF_MONTH, 1 - instance.get(Calendar.DAY_OF_WEEK));// 根据月末最后一天是星期几，向前偏移至最近的周日
//            mday = DateFormatToString(instance.getTime());
//            sday = getDateAfterYMD(mday, -1);
//            smday = "'" + sday + "','" + mday + "'";
//            // System.out.println(smday);
//            return smday;
//
//        }
//
//        public static String getCurrentBfHours(String bDate) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            Calendar cal = Calendar.getInstance();
//            try {
//                Date bdate = formater.parse(bDate);
//                cal.setTime(bdate);
//                cal.add(Calendar.HOUR, -3);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return formater.format(cal.getTime());
//        }
//
//        public static String getCurrentBfHours(String bDate, int min) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            Calendar cal = Calendar.getInstance();
//            try {
//                Date bdate = formater.parse(bDate);
//                cal.setTime(bdate);
//                cal.add(Calendar.HOUR, -min);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return formater.format(cal.getTime());
//        }
//
//        public static String getCurrentBfMins(String bDate, int min) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            Calendar cal = Calendar.getInstance();
//            try {
//                Date bdate = formater.parse(bDate);
//                cal.setTime(bdate);
//                cal.add(Calendar.MINUTE, -min);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return formater.format(cal.getTime());
//        }
//
//        public static String getCurrentAfMins(String bDate, int min) {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            Calendar cal = Calendar.getInstance();
//            try {
//                Date bdate = formater.parse(bDate);
//                cal.setTime(bdate);
//                cal.add(Calendar.MINUTE, min);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return formater.format(cal.getTime());
//        }
//
//        public static long compareTime(String startTime, String endTime) throws ParseException {
//            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            Calendar start = Calendar.getInstance();
//            Calendar end = Calendar.getInstance();
//            start.setTime(formater.parse(startTime));
//            end.setTime(formater.parse(endTime));
//            long startTimeM = start.getTimeInMillis();
//            long endTimeM = end.getTimeInMillis();
//            return (endTimeM - startTimeM) / (1000 * 60);
//        }
//
//        /**
//         * 获取前几个小时的时间或者后几个小时的时间
//         *
//         * @param hour
//         * @return
//         */
//        public static String getBfHour(int hour) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
//            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
//        }
//
//        /**
//         * 获得某个月最大天数
//         *
//         * @param year 年份
//         * @param month 月份 (1-12)
//         * @return 某个月最大天数
//         */
//        public static int getMaxDayByYearMonth(int year, int month) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.YEAR, year - 1);
//            calendar.set(Calendar.MONTH, month);
//            return calendar.getActualMaximum(Calendar.DATE);
//        }
//
//        public static String loadJSON (String url) {
//            StringBuilder json = new StringBuilder();
//            try {
//                URL oracle = new URL(url);
//                URLConnection yc = oracle.openConnection();
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        yc.getInputStream()));
//                String inputLine = null;
//                while ( (inputLine = in.readLine()) != null) {
//                    json.append(inputLine);
//                }
//                in.close();
//            } catch (MalformedURLException e) {
//            } catch (IOException e) {
//            }
//            return json.toString();
//        }
//
//        public static String TimestampToString(java.sql.Timestamp ts) {
//            //java.sql.Timestamp ts = java.sql.Timestamp(System.currentTimeMillis());
//            String tsStr = "";
//            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            try {
//                tsStr = sdf.format(ts);
//                System.out.println(tsStr);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return tsStr;
//        }
//
//
//        public static long fromDateStringToLong(String inVal) {
//            Date date = null;
//            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            try {
//                date = inputFormat.parse(inVal);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return date.getTime()/1000;
//        }
//
//
//        // 测试
//        public static void main(String arg[]) {
//            try {
//                System.out.println(retCode());
//
////			System.out.println(fromDateStringToLong(Function.getCurrentTime()));
////			System.out.println(fromDateStringToLong("2019-05-01 17:24:31"));
////			System.out.println(fromDateStringToLong(Function.getCurrentTime())-fromDateStringToLong("2019-05-01 17:24:31"));
////			//System.out.println(TimestampToString(""));
//                /**String url = "http://huiyuan.kzhc.cn:8082/member/info/feferrals?openId=oBQ3qwlYhxbN6WklzST_jmkFyfdY";
//                 String result = loadJSON(url);
//                 JSONObject obj = new JSONObject().fromObject(result);
//                 ReturnData rd = (ReturnData)JSONObject.toBean(obj,ReturnData.class);
//                 System.out.println(rd.getCode());*/
//                // String json = loadJSON(url);
//                // System.out.println(json);
//                //String planServiceTimeStr="4-01 08:00-10:00";
//                //System.out.println(planServiceTimeStr.substring(2,9));
//                //OrderParamDayVo v=new  OrderParamDayVo();
//                //v.setDay01(v.getDay01()-1);
//                //v.setDay01(v.getDay01()-1);
//                //System.out.println(v.getDay01());
//                //System.out.println(getMaxDayByYearMonth(2019,04));
//                // System.out.println(Function.DateFormatToString(new Date()));
//                // System.out.println(checkDateWeek("2015-06-15"));
//                // System.out.println(getCurrentBfHours("2016-05-07 12:28"));
//                // Function.lastWeekMonth();
//                // String grapTime=Function.getCurrentBfMins(Function.getCurrentTime("yyyy-MM-dd
//                // HH:mm"),10);
//                // System.out.println(grapTime);
//                // System.out.println(Function.retCode());
//                // System.out.println(Function.getDateAfterYMDHH(Function.getCurrentTime("yyyy-MM-dd
//                // HH"), 1).compareTo("2016-09-28 17"));
//                // System.out.println(Function.getDateAfterYMDHH(Function.getCurrentTime("yyyy-MM-dd
//                // HH"), 1));
//                // String
//                // deliveryTime=Function.getCurrentBfMins(Function.getCurrentTime("yyyy-MM-dd
//                // HH:mm"),15);
//                // System.out.println(deliveryTime);
//                // System.out.println((float)1/(float)9);
//                // String
//                // deliveryTime=Function.getCurrentBfMins(Function.getCurrentTime("yyyy-MM-dd
//                // HH:mm"),5);
//                // SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd");
//                // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                // System.out.println(Function.compareTime("2016-11-06 12:20",
//                // dateFormat.format(new Date()))<=30);
//
//                // String
//                // lastLocationTime=Function.getCurrentBfMins(Function.getCurrentTime("yyyy-MM-dd
//                // HH:mm"),15);
//                // System.out.println("2016-10-15 14:34".compareTo(lastLocationTime)>0);
//
//                // String
//                // deliveryTime=Function.getCurrentBfMins(Function.getCurrentTime("yyyy-MM-dd
//                // HH:mm"),15);
//                /**
//                 * String changeMonth=Function.getCurrentTime("yyyy-MM"); String
//                 * oneMonth=Function.getMonthBeforeCurrentMonthYM(1); String
//                 * twoMonth=Function.getMonthBeforeCurrentMonthYM(2); String
//                 * threeMonth=Function.getMonthBeforeCurrentMonthYM(3);
//                 *
//                 * System.out.println(changeMonth); System.out.println(oneMonth);
//                 * System.out.println(twoMonth); System.out.println(threeMonth);
//                 */
//                // String
//                // deliveryTime=Function.getCurrentBfMins(Function.getCurrentTime("yyyy-MM-dd
//                // HH:mm"),15);
//                // System.out.println(deliveryTime);
//                // System.out.println(Function.getDateAfterYMDHH(Function.getCurrentTime("yyyy-MM-dd
//                // HH"), 1).compareTo("2016-11-14"+" 17"));
//                /**
//                 * System.out.println(getDateAfterYMD(5)); String
//                 * finshTime=Function.getCurrentBfMins(Function.getCurrentTime(), 30); String
//                 * endTime=Function.getBfHour(3); String startTime=Function.getBfHour(1);
//                 * System.out.println(finshTime); System.out.println(startTime);
//                 * System.out.println(endTime); String
//                 * grapTime=Function.getCurrentBfHours(Function.getCurrentTime("yyyy-MM-dd
//                 * HH:mm"),1); System.out.println(grapTime);
//                 * System.out.println(getCurrentAfMins(Function.getCurrentTime("yyyy-MM-dd
//                 * HH:mm"),60));
//                 */
//
//                //System.out.println(Function.lastDateWeek());
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//}
