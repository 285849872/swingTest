package com.junxun.pdf;

import java.util.Map;
import java.util.HashMap;

/**
 * PDF模版信息
 */
public class PDFGeneratorDto {
    private String tempPath;
    private String fontPath;
    private Map<String, Object> textFields = new HashMap<String, Object>();
    private Map<String, Object> qrcodeFields = new HashMap<String, Object>();
    private Map<String, PDFTableDto> tableFields = new HashMap<String, PDFTableDto>();

    private String fileName;
    private boolean onlineView = false;

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getFontPath() {
        return fontPath;
    }

    public void setFontPath(String fontPath) {
        this.fontPath = fontPath;
    }

    public Map<String, Object> getTextFields() {
        return textFields;
    }

    public void setTextFields(Map<String, Object> textFields) {
        this.textFields = textFields;
    }

    public Map<String, Object> getQrcodeFields() {
        return qrcodeFields;
    }

    public void setQrcodeFields(Map<String, Object> qrcodeFields) {
        this.qrcodeFields = qrcodeFields;
    }

    public Map<String, PDFTableDto> getTableFields() {
        return tableFields;
    }

    public void setTableFields(Map<String, PDFTableDto> tableFields) {
        this.tableFields = tableFields;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isOnlineView() {
        return onlineView;
    }

    public void setOnlineView(boolean onlineView) {
        this.onlineView = onlineView;
    }
}
