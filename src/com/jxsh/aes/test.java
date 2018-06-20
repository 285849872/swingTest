package com.jxsh.aes;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.junxun.pdf.PDFTableDto;
import com.junxun.pdf.PDFTemplateExport;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyb on 2018/6/12.
 */
public class test {
    public static void main(String[] args) {
        try{
            Runtime.getRuntime().exec("cmd.exe /C start acrord32 /P /h " + "newPdf/20180601161805.pdf");
            Runtime.getRuntime().exec("cmd.exe /C \"c:\\Program Files\\Ghostgum\\gsview\\gsprint\" -printer " + PrintServiceLookup.lookupDefaultPrintService().getName() + "newPdf/20180601161805.pdf");
            //return true;
            String templatePath = "pdfTemplate/template1.pdf";
            File file = new File("newPdf/20180601161805.pdf");
            file.createNewFile();
            PDFTemplateExport te = new PDFTemplateExport();
            te.setTemplatePdfPath(templatePath);
            te.export(
                    new FileOutputStream(file), textFields(), qrCodeFields(), tableFileds());
            /*PDDocument pdDocument = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(pdDocument);*/
        }catch(Exception e){
            e.printStackTrace();
            //return false;
        }
        //pngToPdf("D:/SwingTest1/newPdf/10004831-2018-06-07151444_1.png", "D:/SwingTest1/newPdf/1234.pdf");
    }
    /**
     * 表格内容
     * @param tb
     * @param export
     * @return
     */
    public static Map<String, PDFTableDto> tableFileds(){
        PDFTableDto tableDto = new PDFTableDto();
        tableDto.setColNames("摘要,金额,税率,税额");
        tableDto.setColFields("t1,t2,t3,t4");
        List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
        Map<String, Object> row = new HashMap<String, Object>();;
        /*for (int i = 0; i < tb.getNumRows(); i++){
            row = new HashMap<String, Object>();
            tb.setRow(i);
            if(tb.getString("TEXT").startsWith("*")){
                row.put("t1",tb.getString("TEXT").substring(1, tb.getString("TEXT").length()));
            }else{
                row.put("t1",tb.getString("TEXT"));
            }


        }*/
        row.put("t1","测试测试测试");
        row.put("t2","77.99");
        row.put("t3","999");
        row.put("t4","1000000.99");
        dataList.add(row);
        row = new HashMap<String, Object>();
        row.put("t1","合计（Sum）");
        row.put("t2","19999");
        row.put("t3","");
        row.put("t4","99999999");
        dataList.add(row);
        row = new HashMap<String, Object>();
        row.put("t1","价税合计（Total Sum）");
        row.put("t2","");
        row.put("t3","");
        row.put("t4","99999999");
        dataList.add(row);
        tableDto.setDataList(dataList);
        Map<String, PDFTableDto> tableFields = new HashMap<String, PDFTableDto>();
        tableFields.put("table", tableDto);
        return tableFields;
    }

    /**
     * 二维码
     * @param stc
     * @return
     */
    private static Map<String, Object> qrCodeFields(){
        Map<String, Object> qrCodeFields = new HashMap<String, Object>();
        try{
            FileReader fileReader = new FileReader("login/pid&key&url.txt");
            BufferedReader br = new BufferedReader(fileReader);
            String str1 = null;
            String[] s = null;
            while ((str1 = br.readLine()) != null) {
                s = str1.split(",");
            }
            fileReader.close();
            br.close();
            if(s != null && s.length == 3){
                String s1 = s[0];
                String url = "https://" + s[2] + "/s2bcommerce/wechat/init?corpid=" + s1 + "&encstr=";

                //String gName = stc.getValue("G_NAME").toString().trim().replace("&", "");
                //判断是否包含汉字
                /*if(gName.getBytes().length != gName.length()){
                    //判断是否超过25位&截取前25位
                    if(gName.length() > 25){
                        gName = stc.getValue("G_NAME").toString().trim().replace("&", "").substring(0, 25);
                    }
                }*/
                String qrCode = "YYYYYYYYYYYYYYYYYYYYYYYYYYYY";
                //进行AES256 和 base64加密
                AES256EncryptionUtil util = new AES256EncryptionUtil();
                String str = url + util.encrypt(qrCode, s[1]);
                qrCodeFields.put("qrCode",str);
            }else{
                JOptionPane.showMessageDialog(null, "pid&key&url文件读取失败", "错误提示", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return qrCodeFields;
    }

    /**
     * 单个值
     * @param stc
     * @param flag
     * @return
     */
    public static Map<String, Object> textFields(){
        Map<String, Object> textField = new HashMap<String, Object>();
        /*if(stc.getValue("BILLNO") != null && !"".equals(stc.getValue("BILLNO"))){
            textField.put("billNumber","帐单号: Bill No." + "" + stc.getValue("BILLNO"));
        }else{
            textField.put("billNumber","TEST PRINT");
        }*/
        textField.put("billNumber","TEST PRINT");
        textField.put("postcode","213124");
        textField.put("city","上海");
        textField.put("detailSite","Ehjuo,ooo大连");
        textField.put("seven","大连大连测试测试");
        textField.put("four","Ehjuo,ooo");
        textField.put("name","大连大连测试测试");
        textField.put("six","Ehjuo,ooo");
        textField.put("printDate","大连大连测试测试");
        textField.put("dueDate","大连大连测试测试");
        textField.put("client","大连大连测试测试");
        textField.put("contractNumber","大连大连测试测试");
        textField.put("unitName","Ehjuo,ooo");
        textField.put("identifyNumber","Ehjuo,ooo");
        textField.put("sitePhone","大连大连测试测试大连大连测试测试大连大连测试测试大连大连测试测试Ehjuo,ooo1234567");
        textField.put("accountName","Ehjuo,ooo");
        textField.put("account", "Ehjuo,ooo");
        return textField;
    }
    public static void pngToPdf(String imagePath, String pdfPath){
        try{
            new File("D:/SwingTest1/newPdf/", "1234.pdf").createNewFile();
            BufferedImage img = ImageIO.read(new File(imagePath));
            FileOutputStream fos = new FileOutputStream(pdfPath);
            Document doc = new Document(null, 0, 0, 0, 0);
            doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(imagePath);
            PdfWriter.getInstance(doc, fos);
            //new File(imagePath).delete();
            doc.open();
            //doc.add(image);
            doc.close();
            System.out.println("png转pdf转换成功！");
        }catch (Exception e) {
            e.getMessage();
        }
    }
}
