package com.landtool.utils;




import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sendGet {
    public static String sendGet(String url) {
        // 定义一个字符串用来存储网页内容
        StringBuilder result = new StringBuilder();
        // 定义一个缓冲字符输入流
        BufferedReader in = null;
        try {
            // 将string转成url对象
            URL realUrl = new URL(url);
            // 初始化一个链接到那个url的连接
            URLConnection connection = realUrl.openConnection();
            // 开始实际的连接
            connection.connect();
            // 初始化 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            // 用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null) {
                // 遍历抓取到的每一行并将其存储到result里面
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String RegexString(String targetStr, String patternStr) {
        // 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容
        // 相当于埋好了陷阱匹配的地方就会掉下去
        Pattern pattern = Pattern.compile(patternStr);
        // 定义一个matcher用来做匹配
        Matcher matcher = pattern.matcher(targetStr);
        // 如果找到了
        if (matcher.find()) {
            // 打印出结果
            return matcher.group(0);
        }
        return "";
    }

    public static List tiqu6(String a, List b) {
        for (int i = 0; i < 6; i++) {
            String linshi = a.substring(a.indexOf("<td>"), a.indexOf("</td>")) + "</td>";
            a = a.substring(0, a.indexOf("<td>")) + a.substring(a.indexOf("</td>") + 5, a.length());
            b.add(linshi.replace("<td>", "").replace("</td>", ""));


        }
        return b;
    }

    public static List tiqu7(String a, List b) {
        for (int i = 0; i < 7; i++) {
            String linshi = a.substring(a.indexOf("<td>"), a.indexOf("</td>")) + "</td>";
            b.add(linshi.replace("<td>", "").replace("</td>", ""));

            a = a.substring(0, a.indexOf("<td>")) + a.substring(a.indexOf("</td>") + 5, a.length());


        }
        return b;
    }

    public static List tiqu5(String a, List b) {
        for (int i = 0; i < 5; i++) {
            String linshi = a.substring(a.indexOf("<td>"), a.indexOf("</td>")) + "</td>";
            b.add(linshi.replace("<td>", "").replace("</td>", ""));
            a = a.substring(0, a.indexOf("<td>")) + a.substring(a.indexOf("</td>") + 5, a.length());
        }
        return b;
    }

    public static void main(String[] args) {
        // 定义即将访问的链接
        String url = "http://g.hyyb.org/systems/HyybServices/City_jiangmen/ForecastXML_single.php?sea=上川岛旅游区";
        // 访问链接并获取页面内容
        String result = sendGet(url);
        String zhengzhe = "<table[^>]*>[\\s\\S]*</table>";
        String text = RegexString(result, zhengzhe).replace("\t", "").trim();
        System.out.println(text);

        List<String> list = new ArrayList<>();
        int qidian = 0;
        for (int i = 0; i < 5; i++) {
            String linshi = text.substring(text.indexOf("<tr>"), text.indexOf("</tr>")) + "</tr>";
            list.add(linshi);
            text = text.replace(linshi, "").trim();
        }
        String yubaoquyu = list.get(0).substring(list.get(0).indexOf("<tr><td colspan =\"5\">"), list.get(0).indexOf("</td></tr>")).replace("<tr><td colspan =\"5\">", "");

        //第二行
        List dierhang = sendGet.tiqu5(list.get(1), new ArrayList());
        //第三行 江门飞沙 三天的数据
        List disanhang = sendGet.tiqu5(list.get(2), new ArrayList());
        List disihang = sendGet.tiqu5(list.get(3), new ArrayList());
        List diwuhang = sendGet.tiqu5(list.get(4), new ArrayList());

        StringBuffer str = new StringBuffer();
        str.append("{ \"yubaoquyu\":");
        str.append("\"").append(yubaoquyu).append("\"").append(",\"yubaoshijian\":[{");
        str.append("\"").append(dierhang.get(0)).append("\"").append(":").append("\"").append(disanhang.get(0)).append("\"").append(",");
        str.append("\"" + dierhang.get(1) + "\"" + ":" + "\"" + disanhang.get(1) + "\"" + ",");
        str.append("\"" + dierhang.get(2) + "\"" + ":" + "\"" + disanhang.get(2) + "\"" + ",");
        str.append("\"" + dierhang.get(3) + "\"" + ":" + "\"" + disanhang.get(3) + "\"" + ",");
        str.append("\"" + dierhang.get(4) + "\"" + ":" + "\"" + disanhang.get(4) + "\"" + "},{");

        str.append("\"" + dierhang.get(0) + "\"" + ":" + "\"" + disihang.get(0) + "\"" + ",");
        str.append("\"" + dierhang.get(1) + "\"" + ":" + "\"" + disihang.get(1) + "\"" + ",");
        str.append("\"" + dierhang.get(2) + "\"" + ":" + "\"" + disihang.get(2) + "\"" + ",");
        str.append("\"" + dierhang.get(3) + "\"" + ":" + "\"" + disihang.get(3) + "\"" + ",");
        str.append("\"" + dierhang.get(4) + "\"" + ":" + "\"" + disihang.get(4) + "\"" + "},{");

        str.append("\"" + dierhang.get(0) + "\"" + ":" + "\"" + diwuhang.get(0) + "\"" + ",");
        str.append("\"" + dierhang.get(1) + "\"" + ":" + "\"" + diwuhang.get(1) + "\"" + ",");
        str.append("\"" + dierhang.get(2) + "\"" + ":" + "\"" + diwuhang.get(2) + "\"" + ",");
        str.append("\"" + dierhang.get(3) + "\"" + ":" + "\"" + diwuhang.get(3) + "\"" + ",");
        str.append("\"" + dierhang.get(4) + "\"" + ":" + "\"" + diwuhang.get(4) + "\"" + "}]}");
        System.out.print(str);
    }
}
