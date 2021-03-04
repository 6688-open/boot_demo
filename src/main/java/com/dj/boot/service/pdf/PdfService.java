package com.dj.boot.service.pdf;

import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.service.pdf
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-07-02-14-47
 */
public interface PdfService {
    /**
     * exportPdf
     * @param response
     */
    void exportPdf(HttpServletResponse response);
}
