package com.tracker.nlp.util;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * http 工具类 获取请求中的参数
 *
 * @author show
 * @date 14:23 2019/5/29
 */
public class HttpUtils {
    /**
     * 获取请求IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //"***.***.***.***".length() = 15
        if (StrUtil.isNotBlank(ip) && ip.length() > 15) {
            if (ip.indexOf(CharUtil.COMMA) > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        //处理获取多个ip地址情况 nginx多层代理会出现多个ip 第一个为真实ip地址
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }


}
