package com.junxun.pdf.service.impl;


import com.itextpdf.xmp.impl.Base64;
import com.junxun.pdf.PDFGeneratorDto;
import com.junxun.pdf.PDFTableDto;
import com.junxun.pdf.PDFTemplateExport;
import com.junxun.pdf.service.PdfGenerator;
import com.sun.xml.internal.ws.util.StringUtils;
import com.sun.xml.internal.ws.util.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by zyb on 2018/5/17.
 */
public class PdfGeneratorDefaultImpl implements PdfGenerator{
    //定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PdfGeneratorDefaultImpl.class);
    /**
     * 生成Pdf文件到下载输出流
     * @param generator
     * @param request
     * @param response
     */
    @Override
    public void generatePdf(@SuppressWarnings("rawtypes") PDFGeneratorDto generator, HttpServletRequest request, HttpServletResponse response) {
        //super.generatePdf(generator, request, response);
        //如果data中没有数据，则返回错误
        if(generator == null){

        }
        OutputStream outputStream = null;
        PDFTemplateExport pdfExport = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
            //初始化输出流
            String fileName = format.format(new Date());
            outputStream = initOutputStream(request, response, fileName, generator.isOnlineView());
            //初始模板
            String fontPath = generator.getFontPath();
            String tempPath = generator.getTempPath();
            String rootDir = request.getSession().getServletContext().getRealPath("/");
            tempPath = rootDir + "assets" + File.separator + "pdf" + File.separator + tempPath;
            if(fontPath == null || "".equals(fontPath)){
                pdfExport = new PDFTemplateExport();
                pdfExport.setTemplatePdfPath(tempPath);
            }else{
                //pdfExport = new PDFTemplateExport(tempPath, fontPath);
                //pdfExport.setTemplatePdfPath(tempPath);

            }
            //写入数据
            Map<String, Object> textFields = generator.getTextFields();
            Map<String, Object> qrcodeFields = generator.getQrcodeFields();
            Map<String, PDFTableDto> tableFields = generator.getTableFields();
            pdfExport.export(outputStream, textFields, qrcodeFields, tableFields);
        }catch (Exception e){
            logger.warn(e.getMessage(),e);
        }
    }

    /**
     * 初始化输出流
     * @param request
     * @param response
     * @param fileName
     * @param isOnLine
     * @return
     * @throws UtilException
     */
    private OutputStream initOutputStream(HttpServletRequest request, HttpServletResponse response, String fileName, boolean isOnLine) throws UtilException{
        try{
            String enableFileName = "";
            String agent = (String) request.getHeader("USER-AGENT");
            if(agent != null && agent.indexOf("like Gecko") != -1){//IE11
                enableFileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            }else if (agent != null && agent.indexOf("MSIE") == -1) {// FF
                enableFileName = "=?UTF-8?B?" + (new String(Base64.encode(fileName.getBytes("UTF-8")))) + "?=";
            } else { // IE
                enableFileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            }
            // 输出文件流
            response.reset();
            if (isOnLine) { // 在线打开方式
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=" + enableFileName);
            } else { // 纯下载方式
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
            }
            return response.getOutputStream();
        }catch (Exception e){
            logger.warn("pdf 流初始化失败",e);
        }
        return null;
    }
}
