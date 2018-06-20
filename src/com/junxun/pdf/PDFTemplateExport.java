package com.junxun.pdf;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zyb on 2018/5/15.
 */
public class PDFTemplateExport {
    //private static final Logger logger = LoggerFactory.getLogger(PDFTemplateExport.class);

    //模板路径
    private String templatePdfPath;
    //字体名称
    private String fontName = "simsun.ttc,0";

    public void setTemplatePdfPath(String templatePdfPath) {
        this.templatePdfPath = templatePdfPath;
    }
    public String getTemplatePdfPath() {
        return templatePdfPath;
    }

    /**
     *根据模版导出PDF文档
     * @param os 输出流
     * @param textFields 文本字段
     * @param qrcodeFields 二维码字段
     * @param tableFields 表格字段
     * @throws Exception
     */
    public void export(OutputStream os, Map<String, Object> textFields, Map<String, Object> qrcodeFields, Map<String, PDFTableDto> tableFields) throws Exception {

        //读取模板
        PdfReader reader = new PdfReader(templatePdfPath);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper ps = new PdfStamper(reader, bos);

        //使用中文字体
        BaseFont bf = BaseFont.createFont(getFontPath(fontName), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
        fontList.add(bf);

        AcroFields s = ps.getAcroFields();
        s.setSubstitutionFonts(fontList);

        //遍历表单字段
        for (Map.Entry<String, Object> entity : textFields.entrySet()){
            String key = entity.getKey();
            Object value = entity.getValue();

            s.setFieldProperty(key, "textfont", bf, null);
            s.setField(key, getBlank(value));
        }

        //遍历二维码字段
        for (Map.Entry<String, Object> entity : qrcodeFields.entrySet()){
            String key = entity.getKey();
            Object value = entity.getValue();
            //获取属性类型
            if(value != null && s.getField(key) != null){
                //获取位置（左上右下）
                FieldPosition fieldPosition = s.getFieldPositions(key).get(0);
                //绘制二维码
                float width = fieldPosition.position.getRight() - fieldPosition.position.getLeft();
                BarcodeQRCode pdf417 = new BarcodeQRCode(value.toString(), (int)width, (int)width, null);
                //生成二维码图像
                Image image128 = pdf417.getImage();
                //绘制在第一页
                PdfContentByte cb = ps.getOverContent(1);
                //左边距（居中处理）
                float marginLeft = (fieldPosition.position.getRight() - fieldPosition.position.getLeft() - image128.getWidth())/2;
                //二维码位置
                image128.setAbsolutePosition(fieldPosition.position.getLeft() + marginLeft, fieldPosition.position.getBottom());
                //加入二维码
                cb.addImage(image128);
            }
        }
        //遍历表格字段
        Font keyFont = new Font(bf,10, Font.BOLD);//设置表头字体大小
        Font textFont = new Font(bf, 10, Font.NORMAL);//设置文本字体大小
        for(Map.Entry<String, PDFTableDto> entry : tableFields.entrySet()){
            String key = entry.getKey();
            PDFTableDto tableDto = entry.getValue();
            //获取属性类型
            if(tableDto != null && tableDto.getColFields() != null && s.getField(key) != null){
                //获取位置（左上右下）
                FieldPosition fieldPosition = s.getFieldPositions(key).get(0);
                float width = fieldPosition.position.getRight() - fieldPosition.position.getLeft();
                //创建表格
                String[] thread = tableDto.getColNames() != null ? tableDto.getColNames().split(",") : tableDto.getColNames().split(",");

                float[] widths = {0.70f, 0.2f, 0.1f, 0.2f};
                PdfPTable table = new PdfPTable(widths);
                try {
                    table.setTotalWidth(width);
                    table.setLockedWidth(true);
                    table.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.getDefaultCell().setBorder(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
                //创建表头
                for (String col : thread){
                    //table.addCell(createCell(col, keyFont, Element.ALIGN_CENTER,false));
                }
                //创建表体
                String[] fields = tableDto.getColFields().split(",");
                List<Map<String, Object>> dataList = tableDto.getDataList();
                if(dataList != null && dataList.size() > 0){
                    for (int i=0;i<dataList.size();i++){
                        Map<String, Object> row = dataList.get(i);
                        for (int j=0;j<fields.length;j++) {
                            if(dataList.size() - 2 == i || dataList.size() - 1 == i){
                                if(j == 0){
                                    table.addCell(createCell(row.get(fields[j]), keyFont, Element.ALIGN_CENTER, false, "2"));
                                }else{
                                    table.addCell(createCell(row.get(fields[j]), keyFont, Element.ALIGN_RIGHT, false, "2"));
                                }
                            }else{
                                if(j == 0){
                                    table.addCell(createCell(row.get(fields[j]), textFont, Element.ALIGN_LEFT, false, "1"));
                                }else{
                                    table.addCell(createCell(row.get(fields[j]), textFont, Element.ALIGN_RIGHT, false, "1"));
                                }
                            }
                        }
                    }
                }
                //插入文档
                PdfContentByte cb = ps.getOverContent(1);
                table.writeSelectedRows(0, -1, 0, -1, fieldPosition.position.getLeft(), fieldPosition.position.getTop(), cb);
            }
        }
        ps.setFormFlattening(true);
        ps.close();

        os.write(bos.toByteArray());
        os.flush();
        os.close();

        bos.close();
        reader.close();
    }

    /**
     * 创建单元格
     * @param value 显示内容
     * @param font 字体
     * @param align 对齐方式
     * @param boderFlag 是否有边框
     * @return
     */
    public static PdfPCell createCell(Object value, Font font, int align, boolean boderFlag, String str){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_BASELINE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(getBlank(value), font));
        cell.setMinimumHeight(5);
        if("2".equals(str)){
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        }
        if (!boderFlag)
        {
            cell.setBorder(0);
            //cell.setPaddingTop(15.0f);
            //cell.setPaddingBottom(8.0f);
        }
        return cell;
    }
    /**
     * 创建单元格
     * @param value 显示内容
     * @param font 字体
     * @return
     */
    public static PdfPCell createCell(Object value, Font font){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_BASELINE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPhrase(new Phrase(getBlank(value), font));
        return cell;
    }

    /**
     * 获取字体文件
     * @param value
     * @return
     */
    public static String getFontPath(Object fontName){
        String fontPath = "C:\\Windows\\Fonts\\" + fontName;

        //判断系统类型，加载字体文件
        java.util.Properties prop = System.getProperties();
        String osName = prop.getProperty("os.name").toLowerCase();
        if(osName.indexOf("linux") > -1){
            fontPath = "/usr/share/fonts/" + fontName;
        }
        return fontPath;
    }

    /**
     * 非空处理
     * @param value
     * @return
     */
    public static String getBlank(Object value){
        if(value != null){
            return value.toString();
        }
        return "";
    }
}
