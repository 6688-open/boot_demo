package com.dj.boot.aspect.util;

import com.dj.boot.common.util.json.JsonUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName ResponseUtils
 * @Description TODO
 * @Author wj
 * @Date 2019/12/20 16:34
 * @Version 1.0
 **/
public class ResponseUtils {

    public ResponseUtils() {
    }

    public static void responseError(HttpServletResponse httpServletResponse, int status, String message) {
        responseError(httpServletResponse, status, message, (Object)null);
    }

    public static void responseError(HttpServletResponse httpServletResponse, int status, String message, Object data) {
        httpServletResponse.setStatus(status);
        httpServletResponse.setContentType("application/json; charset=utf-8");

        try {
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            Throwable var5 = null;

            try {
                OperationResult result = new OperationResult();
                result.setErrorMessage(message);
                result.setData(data);
                String body = JsonUtils.serialize(result);
                byte[] bytes = body.getBytes("UTF-8");
                httpServletResponse.setContentLength(bytes.length);
                servletOutputStream.write(bytes);
            } catch (Throwable var17) {
                var5 = var17;
                throw var17;
            } finally {
                if (servletOutputStream != null) {
                    if (var5 != null) {
                        try {
                            servletOutputStream.close();
                        } catch (Throwable var16) {
                            var5.addSuppressed(var16);
                        }
                    } else {
                        servletOutputStream.close();
                    }
                }

            }
        } catch (Exception var19) {
            var19.printStackTrace();
        }

    }

    public static void unauthorizedResponse(HttpServletResponse httpServletResponse) {
        responseError(httpServletResponse, 401, "Please login first");
    }

    public static void noPermissionResponse(HttpServletResponse httpServletResponse, Object data) {
        responseError(httpServletResponse, 403, "Your account has no permission for your request url", data);
    }

    public static void noPermissionResponse(HttpServletResponse httpServletResponse) {
        noPermissionResponse(httpServletResponse, (Object)null);
    }

    public static void badRequestResponse(HttpServletResponse httpServletResponse, String message) {
        responseError(httpServletResponse, 400, message);
    }
}
