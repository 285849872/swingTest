package com.junxun.pdf.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zyb on 2018/5/17.
 */

import com.junxun.pdf.PDFGeneratorDto;

public interface PdfGenerator {
    /**
     * 生成Pdf文件到下载输出流
     * @param generator
     * @param request
     * @param response
     */
    public void generatePdf(@SuppressWarnings("rawtypes")PDFGeneratorDto generator, HttpServletRequest request, HttpServletResponse response);
}
