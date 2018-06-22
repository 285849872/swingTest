import com.itextpdf.text.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.junxun.pdf.PDFTableDto;
import com.junxun.pdf.PDFTemplateExport;
import com.jxsh.aes.AES256EncryptionUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.PrintPDF;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by zyb on 2018/5/11.
 */
public class MainGUI extends JFrame {
    private JButton button1;
    private JPanel panelContent;
    private JLabel content = new JLabel("content:");
    private JLabel lab;
    private JButton bt1;
    private JButton bt2;
    private JButton bt3;
    private JButton bt4;
    private JButton bt5;
    private JButton bt6;
    private JButton bt7;
    private JButton bt9;
    private JTextField conditionStart1;
    private JTextField conditionStart2;
    private JTextField conditionStart3;
    private JTextField conditionStart4;
    private JTextField conditionStart5;
    private JTextField conditionStart6;
    private JTextField conditionStart7;
    private JTextField conditionStart8;
    private JTextField conditionStart9;
    private JTextField conditionStart10;
    private JTextField conditionStart11;
    private JTextField conditionEnd2;
    private JTextField conditionEnd3;
    private JTextField conditionEnd4;
    private JTextField conditionEnd5;
    private JTextField conditionEnd6;
    private JTextField conditionEnd7;
    private JTextField conditionEnd8;
    private JTextField conditionEnd9;
    private JTextField conditionEnd11;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JPanel panel;
    private JTextField conditionStart12;
    private JTextField conditionEnd12;
    private JTextField conditionStart13;
    private JCheckBox credchCheckBox;
    private JCheckBox alloiCheckBox;
    private JButton bt10;
    private JButton bt11;
    private JTextField divide;
    private JTextField groupText;
    private static JButton loginBt1;//登陆按钮
    private static JLabel jl_1;//登录的版面
    private static JFrame jf_1;//登陆的框架
    private static JTextField jtext1;//用户名
    private static JPasswordField jtext2;//密码
    private static JLabel jl_admin;
    private static JLabel jl_password;
    JCO.Table header;
    JCO.Table ite;
    JCheckBox jc1;
    JCO.Table tb;
    //TableList tl;
    JTable jtbList;
    JFrame fList;
    private static StringBuffer name = new StringBuffer();//账号
    private static StringBuffer pass = new StringBuffer();//密码
    //定义主要的组件
    public MainGUI(final JFrame frame) {
        final ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);
        SimpleDateFormat df7 = new SimpleDateFormat("yyyyMMdd");
        final SimpleDateFormat df8 = new SimpleDateFormat("MM/dd/yyyy");
        final SimpleDateFormat df6 = new SimpleDateFormat("yyyyMMdd");
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 2);
        calendar.set(Calendar.DATE, 0);
        conditionEnd12.setText(df7.format(calendar.getTime()));
        conditionStart4.setEditable(false);
        conditionStart9.setEditable(false);
        conditionEnd4.setEditable(false);
        conditionEnd9.setEditable(false);
        bt3.setEnabled(false);
        bt9.setEnabled(false);

        //查询按钮
        button1.addActionListener(new ActionListener() {
            String[] tabName = null;
            Object[][] playerInfo = null;
            ArrayList<JCheckBox> cbl = new ArrayList<JCheckBox>();
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    FileReader file = new FileReader("login/loginPassword.txt");
                    BufferedReader br = new BufferedReader(file);
                    String str = null;
                    while ((str = br.readLine()) != null) {
                        String[] s = str.split(",");
                        JCO.Client mConnection = JCO.createClient(s[0],name.toString(),pass.toString(),s[1],s[2],s[3]);
                        mConnection.connect();

                        JCO.Repository  mRepository = new JCO.Repository("ARAsoft", mConnection);
                        IFunctionTemplate ft = mRepository.getFunctionTemplate("ZDNP01");
                        JCO.Function function = ft.getFunction();
                        JCO.ParameterList table = function.getTableParameterList();
                        table.setValue(setValue(table, "T_GSBER", conditionStart2, conditionEnd2), "T_GSBER");
                        table.setValue(setValue(table, "T_SWENR", conditionStart3, conditionEnd3), "T_SWENR");
                        table.setValue(setValue(table, "T_SGENR", conditionStart5, conditionEnd5), "T_SGENR");
                        table.setValue(setValue(table, "T_SMENR", conditionStart6, conditionEnd6), "T_SMENR");
                        table.setValue(setValue(table, "T_SNUNR", conditionStart7, conditionEnd7), "T_SNUNR");
                        table.setValue(setValue(table, "T_SMIVE", conditionStart8, conditionEnd8), "T_SMIVE");
                        table.setValue(setValue(table, "T_PARTNR", conditionStart11, conditionEnd11), "T_PARTNR");
                        table.setValue(setValue(table, "T_INVOICE", conditionStart4, conditionEnd4), "T_INVOICE");
                        table.setValue(setValue(table, "T_GJAHR", conditionStart9, conditionEnd9), "T_GJAHR");
                        JCO.ParameterList input = function.getImportParameterList();
                        input.setValue(conditionStart1.getText(),"I_BUKRS");
                        if(radioButton1.isSelected()){
                            input.setValue(radioButton1.getText(),"I_FLAG");
                        }else if(radioButton2.isSelected()){
                            input.setValue(radioButton2.getText(),"I_FLAG");
                        }
                        try{
                            if(conditionStart12.getText() != null && !"".equals(conditionStart12.getText())){
                                input.setValue(df6.format(df6.parse(conditionStart12.getText())),"I_DATE_F");
                            }
                            if(conditionEnd12.getText() != null && !"".equals(conditionEnd12.getText())){
                                input.setValue(df6.format(df6.parse(conditionEnd12.getText())),"I_DATE_T");
                            }

                        }catch (Exception x){
                            JOptionPane.showMessageDialog(null, "日期格式错误,请按照'yyyyMMdd'格式输入!", "错误提示", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        input.setValue(conditionStart13.getText(),"I_MAX");
                        if(radioButton1.isSelected()){
                            input.setValue("X","I_FLAG");
                        }else if(radioButton2.isSelected()){
                            input.setValue("","I_FLAG");
                        }
                        if(credchCheckBox.isSelected()){
                            input.setValue("X","I_CREDCH");
                        }
                        if(alloiCheckBox.isSelected()){
                            input.setValue("X","I_ALLOI");
                        }
                        input.setValue(groupText.getText(),"I_INVCLT");
                        input.setValue(divide.getText(),"I_INVPER");
                        //执行function
                        mConnection.execute(function);
                        //得到输出参数
                        JCO.ParameterList export = function.getExportParameterList();
                        tb = function.getTableParameterList().getTable("T_OUTPUT");
                        header = function.getTableParameterList().getTable("T_VIMIIH");
                        ite = function.getTableParameterList().getTable("T_VIMIIP");
                        JCO.Structure sc = function.getExportParameterList().getStructure("RETURN");
                        //export.getValue("RETURN");


                        fList = new JFrame();

                        cbl = new ArrayList<JCheckBox>();
                        if(radioButton1.isSelected()){
                            //表头
                            tabName = new String[]{"","Customer","Amount","Crcy","PM","ALT","Contract",
                                    "No","Due From","Due To","Name","Name 2","Post Code","City","Street"};
                            //表格内容
                            playerInfo = new Object[tb.getNumRows()][20];
                            System.out.println(cbl.size());
                            for (int i = 0; i < tb.getNumRows(); i++){
                                jc1 = new JCheckBox(String.valueOf(i + 1), false);
                                jc1.setText(String.valueOf(i + 1));
                                System.out.println(String.valueOf(i + 1));
                                cbl.add(jc1);
                                tb.setRow(i);
                                playerInfo[i][0] = jc1;
                                playerInfo[i][1] = tb.getString("FIELD1");
                                playerInfo[i][2] = tb.getString("FIELD2");
//                                if(!tb.getString("FIELD2").contains("-")){
//                                    playerInfo[i][2] = new BigDecimal(tb.getString("FIELD2"));
//                                }else{
//                                    playerInfo[i][2] = tb.getString("FIELD2");
//                                }
                                playerInfo[i][3] = tb.getString("FIELD3");
                                playerInfo[i][4] = tb.getString("FIELD4");
                                playerInfo[i][5] = tb.getString("FIELD5");
                                playerInfo[i][6] = tb.getString("FIELD6");
                                playerInfo[i][7] = tb.getString("FIELD7");
                                playerInfo[i][8] = tb.getString("FIELD8");
                                playerInfo[i][9] = tb.getString("FIELD9");
//                                if(tb.getString("FIELD8") != null && !"".equals(tb.getString("FIELD8"))){
//                                    playerInfo[i][8] = df8.parse(tb.getString("FIELD8"));
//                                }else {
//                                    playerInfo[i][8] = tb.getString("FIELD8");
//                                }
//                                if(tb.getString("FIELD9") != null && !"".equals(tb.getString("FIELD9"))){
//                                    playerInfo[i][9] = df8.parse(tb.getString("FIELD9"));
//                                }else {
//                                    playerInfo[i][9] = tb.getString("FIELD9");
//                                }

                                playerInfo[i][10] = tb.getString("FIELD10");
                                playerInfo[i][11] = tb.getString("FIELD11");
                                playerInfo[i][12] = tb.getString("FIELD12");
                                playerInfo[i][13] = tb.getString("FIELD13");
                                playerInfo[i][14] = tb.getString("FIELD14");

                            }
                        }else if(radioButton2.isSelected()){
                            //表头
                            tabName = new String[]{"","Invoice no","Year","LastEdited","Customer","Amount","Crcy",
                                    "PM","ALT","Contract","No","Due From","Due To","Name","Name 2","Post Code",
                                    "City","Street","Last chnge","Spool no."};
                            playerInfo = new Object[tb.getNumRows()][20];
                            for (int i = 0; i < tb.getNumRows(); i++){
                                tb.setRow(i);
                                jc1 = new JCheckBox(String.valueOf(i + 1), false);
                                jc1.setText(String.valueOf(i + 1));
                                System.out.println(String.valueOf(i + 1));
                                cbl.add(jc1);
                                playerInfo[i][0] = jc1;
                                playerInfo[i][1] = tb.getString("FIELD1");
                                playerInfo[i][2] = tb.getString("FIELD2");
                                playerInfo[i][3] = tb.getString("FIELD3");
                                playerInfo[i][4] = tb.getString("FIELD4");
                                playerInfo[i][5] = tb.getString("FIELD5");
//                                if(!tb.getString("FIELD5").contains("-")){
//                                    playerInfo[i][5] = new BigDecimal(tb.getString("FIELD5"));
//                                }else{
//                                    playerInfo[i][5] = tb.getString("FIELD5");
//                                }

                                playerInfo[i][6] = tb.getString("FIELD6");
                                playerInfo[i][7] = tb.getString("FIELD7");
                                playerInfo[i][8] = tb.getString("FIELD8");
                                playerInfo[i][9] = tb.getString("FIELD9");
                                playerInfo[i][10] = tb.getString("FIELD10");
                                playerInfo[i][11] = tb.getString("FIELD11");
                                playerInfo[i][12] = tb.getString("FIELD12");
//                                if(tb.getString("FIELD11") != null && !"".equals(tb.getString("FIELD11"))){
//                                    playerInfo[i][11] = df8.parse(tb.getString("FIELD11"));
//                                }else {
//                                    playerInfo[i][11] = tb.getString("FIELD11");
//                                }
//                                if(tb.getString("FIELD12") != null && !"".equals(tb.getString("FIELD12"))){
//                                    playerInfo[i][12] = df8.parse(tb.getString("FIELD12"));
//                                }else {
//                                    playerInfo[i][12] = tb.getString("FIELD12");
//                                }

                                playerInfo[i][13] = tb.getString("FIELD13");
                                playerInfo[i][14] = tb.getString("FIELD14");
                                playerInfo[i][15] = tb.getString("FIELD15");
                                playerInfo[i][16] = tb.getString("FIELD16");
                                playerInfo[i][17] = tb.getString("FIELD17");
                                playerInfo[i][18] = tb.getString("FIELD18");
                                playerInfo[i][19] = tb.getString("FIELD19");
                            }
                        }
                        System.out.println(cbl.size() + "-------循环结束");
                        for (int t = 0; t < cbl.size(); t++){
                            cbl.get(t).setSelected(false);
                        }

                        DefaultTableModel model = new DefaultTableModel();
                        model.setDataVector(playerInfo, tabName);

                        //tl = new TableList();
                        // tl.setData(playerInfo);
                        //tl.setHead(tabName);
                        jtbList = new JTable(model);
                        jtbList.getColumn("").setCellEditor(new CheckBoxEditor(new JCheckBox()));
                        jtbList.getColumn("").setCellRenderer(new CheckBoxRenderer());
                        TableColumn column = jtbList.getColumn("Amount");
                        DefaultTableCellRenderer render = new DefaultTableCellRenderer();//设置监听器
                        render.setHorizontalAlignment(SwingConstants.RIGHT);//居中对齐
                        column.setCellRenderer(render);
                        column = jtbList.getColumn("Name");
                        column.setPreferredWidth(200);
                        column = jtbList.getColumn("Street");
                        column.setPreferredWidth(200);
                        column = jtbList.getColumn("PM");
                        column.setPreferredWidth(40);
                        column = jtbList.getColumn("ALT");
                        column.setPreferredWidth(40);

                        RowSorter sorter = new TableRowSorter(model);
                        jtbList.setRowSorter(sorter);
                        //jtbList.getColumn("Amount").setCellRenderer(new DefaultTableCellRenderer().setHorizontalAlignment(););

                        //jtbList.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(jc1));
                        //TableColumn tc = jtbList.getColumnModel().getColumn(0);
                        //tc.setCellEditor(jtbList.getDefaultEditor(Boolean.class));
                        //tc.setCellRenderer(jtbList.getDefaultRenderer(Boolean.class));
                        JScrollPane scrollPane = new JScrollPane(jtbList);
                        JPanel contentPane = new JPanel( );
                        JButton b1 = new JButton("打印");
                        JButton b2 = new JButton("预览");
                        JButton b3 = new JButton("全部打印");
                        if(tb.getNumRows() > 0){
                            contentPane.add(b2);
                            contentPane.add(b1);
                            contentPane.add(b3);
                            fList.getContentPane().add(contentPane,BorderLayout.SOUTH);
                        }
                        ImageIcon icon = new ImageIcon("src/img/Log.PNG");
                        fList.setIconImage(icon.getImage());
                        fList.getContentPane().add(scrollPane, BorderLayout.CENTER);
                        fList.setTitle("查询结果");
                        fList.pack();
                        fList.setVisible(true);
                        fList.setLocationRelativeTo(null);//设置打印窗口居中
                        fList.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                        fList.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        fList.setVisible(true);
                        frame.setEnabled(false);//先将主界面设置为不可操作状态

                        //打印
                        b1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                printDailog(playerInfo, cbl, "2", tb);//打开打印页面,并设置为可见
                            }
                        });
                        //预览
                        b2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                saveToFile(playerInfo, cbl, "1", tb);//只保存不打印
                            }
                        });
                        //全部打印
                        b3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                printDailog(playerInfo, cbl, "3", tb);//打开打印页面,并设置为可见
                            }
                        });
                        // 设置此表视图的首选大小
                        //jtbList.setPreferredScrollableViewportSize(new Dimension(550, 100));
                        fList.addWindowListener(new WindowAdapter()  {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                frame.setEnabled(true);//将主界面设置为可操作状态

                                if (e.equals(fList)){
                                    System.exit(0);
                                    fList.setDefaultCloseOperation(EXIT_ON_CLOSE);
                                    fList.setVisible(true);
                                    fList.dispose();

                                }
                            }
                        });
                        //断开连接
                        mConnection.disconnect();
                    }
                    file.close();
                    br.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });
        bt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConditionDailog("2").setVisible(true);//打开多条件页面,并设置为可见
            }
        });
        bt3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConditionDailog("3").setVisible(true);//打开多条件页面,并设置为可见
            }
        });
        bt4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConditionDailog("4").setVisible(true);//打开多条件页面,并设置为可见
            }
        });
        bt5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConditionDailog("5").setVisible(true);//打开多条件页面,并设置为可见
            }
        });
        bt6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConditionDailog("6").setVisible(true);//打开多条件页面,并设置为可见
            }
        });
        bt7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConditionDailog("7").setVisible(true);//打开多条件页面,并设置为可见
            }
        });
        bt9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConditionDailog("8").setVisible(true);//打开多条件页面,并设置为可见
            }
        });
        bt10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConditionDailog("10").setVisible(true);//打开多条件页面,并设置为可见
            }
        });
        bt11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConditionDailog("11").setVisible(true);//打开多条件页面,并设置为可见
            }
        });
        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radioButton1.isSelected()){
                    conditionStart12.setEditable(true);
                    conditionEnd12.setEditable(true);
                    groupText.setEditable(true);
                    divide.setEditable(true);
                    conditionStart4.setEditable(false);
                    conditionStart9.setEditable(false);
                    conditionEnd4.setEditable(false);
                    conditionEnd9.setEditable(false);
                    bt3.setEnabled(false);
                    bt9.setEnabled(false);
                    credchCheckBox.setEnabled(true);
                    alloiCheckBox.setEnabled(true);
                }
            }
        });
        radioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radioButton2.isSelected()){
                    conditionStart12.setEditable(false);
                    groupText.setEditable(false);
                    divide.setEditable(false);
                    conditionEnd12.setEditable(false);
                    conditionStart4.setEditable(true);
                    conditionStart9.setEditable(true);
                    conditionEnd4.setEditable(true);
                    conditionEnd9.setEditable(true);
                    bt3.setEnabled(true);
                    bt9.setEnabled(true);
                    credchCheckBox.setEnabled(false);
                    alloiCheckBox.setEnabled(false);
                }
            }
        });
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    /**
     * 多个查询条件生成
     * @param table function的表
     * @param name 表明
     * @param jtf1 第一个输入框
     * @param jtf2 第二个输入框
     * @return
     */
    private JCO.Table setValue(JCO.ParameterList table, String name, JTextField jtf1, JTextField jtf2){
        JCO.Table table1 = table.getTable(name);
        //table.setValue(table1, name);

        if(jtf1.getText() != null && !"".equals(jtf1.getText())){


            String[] str = jtf1.getText().split(",");
            if(str.length > 1){
                for (int i = 0; i < str.length; i++){
                    table1.appendRow();
                    table1.setValue("I", "SIGN");
                    table1.setValue(str[i], "LOW");
                    table1.setValue("EQ", "OPTION");
                }
            }else{
                table1.appendRow();
                table1.setValue("I", "SIGN");
                table1.setValue(jtf1.getText(), "LOW");
                table1.setValue("BT", "OPTION");
                table1.setValue(jtf2.getText(), "HIGH");
            }
        }else if(jtf2.getText() != null && !"".equals(jtf2.getText())){
            table1.appendRow();
            table1.setValue("I", "SIGN");
            //table1.setValue(jtf.getText(), "LOW");
            table1.setValue("BT", "OPTION");
            table1.setValue(jtf2.getText(), "HIGH");
        }
        return table1;
    }

    /**
     *  打印
     * @param playerInfo
     * @param box
     * @param flag
     */
    public void printDailog(Object[][] playerInfo, ArrayList<JCheckBox> box, String flag, JCO.Table tb) {

        File[] newPdfPath = saveToFile( playerInfo, box, flag, tb);
        if(newPdfPath != null && newPdfPath.length > 0){
            try {
                for (int i = 0; i < newPdfPath.length; i++){
                    System.out.println("进入打印----" + newPdfPath[i].getPath());
                    //Runtime.getRuntime().exec("cmd.exe /C start acrord32 /P /h " + newPdfPath[i].getPath());
                    PrintPDF.main(new String[]{
                            "-silentPrint",
                            "-printerName",PrintServiceLookup.lookupDefaultPrintService().getName() ,//指定打印机名
                            "-orientation","auto",//打印方向，三种可选
                            "pdfFilePath",  newPdfPath[i].getPath()//打印PDF文档的路径
                    });
                    System.out.println("文件大小：" + newPdfPath[i].length());
                    newPdfPath[i].delete();
                }
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null,"打印成功！");
            /*
            JFileChooser fileChooser = new JFileChooser();//创建打印作业

            //选中需要打印的文件
            fileChooser.setSelectedFiles(newPdfPath);
            File[] file = fileChooser.getSelectedFiles();//获取选择的文件
            //构建打印请求属性集
            HashPrintRequestAttributeSet pas = new HashPrintRequestAttributeSet();
            //设置打印格式，因为未确定类型，所以选择autosense
            DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
            //DocFlavor.INPUT_STREAM.PDF;
            //查找所有的可用的打印服务
            PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pas);
            //定位默认的打印服务
            PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
            //显示打印对话框
            PrintService service = ServiceUI.printDialog(null, 200, 200, printService, defaultService, flavor, pas);

            if(service != null){
                try {
                    for (int i = 0; i < file.length; i++){
                        DocPrintJob job = service.createPrintJob();
                        FileInputStream fis = new FileInputStream(file[i]);
                        DocAttributeSet das = new HashDocAttributeSet();
                        Doc doc = new SimpleDoc(fis, flavor, das);
                        job.print(doc, pas);
                        file[i].delete();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        */}
    }


    /**
     * pdf生成并保存
     * @param playerInfo
     * @param box
     * @param flag
     * @return
     */
    private File[] saveToFile(Object[][] playerInfo, ArrayList<JCheckBox> box, String flag, JCO.Table tbNumber) {//该方法用于保存信息到文档
        File[] path = null;
        //File[] pathYuLan = null;
        try {
            String templatePath = "";
            List<String> strList = new ArrayList<String>();
            //获取需要打印的行
            for (int i = 0; i < box.size(); i++) {
                System.out.println(box.get(i).isSelected());
                if(!flag.equals("3")){
                    if (box.get(i).isSelected()) {
                        strList.add(box.get(i).getText());
                    }
                }else {
                    strList.add(box.get(i).getText());
                }
            }
            if (strList.size() > 0) {
                FileReader file = new FileReader("login/loginPassword.txt");
                BufferedReader br = new BufferedReader(file);
                String str = null;
                while ((str = br.readLine()) != null) {
                    String[] s = str.split(",");
                    JCO.Client mConnection = JCO.createClient(s[0], name.toString(), pass.toString(), s[1], s[2], s[3]);
                    mConnection.connect();
                    path = new File[strList.size()];
                    //pathYuLan = new File[strList.size()];
                    for (int i = 0; i < strList.size(); i++) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHHmmss");
                        //String pdfPath = "newPdf/bigPdf/";
                        String pdfPath = "newPdf/";
                        String newPdfPath = format.format(new Date());


                        JCO.Repository mRepository = new JCO.Repository("ARAsoft", mConnection);
                        IFunctionTemplate ft = mRepository.getFunctionTemplate("ZDNP02");
                        JCO.Function function = ft.getFunction();
                        String name = null;
                        JCO.Structure sc = function.getImportParameterList().getStructure("I_LIST");
                        String invoiceNumber = "";
                        if(radioButton1.isSelected()){
                            tbNumber.setRow(Integer.parseInt(strList.get(i)) - 1);
                            invoiceNumber = tbNumber.getString("FIELD15");
                        }
                        for (int n = 0; n < playerInfo[Integer.parseInt(strList.get(i)) - 1].length; n++) {
                            name = "FIELD" + String.valueOf(n + 1);
                            //sc.setValue(playerInfo[Integer.parseInt(strList.get(i)) - 1][n], name);
                            if ((n + 1) >= playerInfo[Integer.parseInt(strList.get(i)) - 1].length) {
                                sc.setValue(playerInfo[Integer.parseInt(strList.get(i)) - 1][n] != null &&
                                        !"".equals(playerInfo[Integer.parseInt(strList.get(i)) - 1][n]) ? playerInfo[Integer.parseInt(strList.get(i)) - 1][n].toString().trim() : "", name);
                            } else {
                                sc.setValue(playerInfo[Integer.parseInt(strList.get(i)) - 1][n + 1] != null &&
                                        !"".equals(playerInfo[Integer.parseInt(strList.get(i)) - 1][n + 1]) ? playerInfo[Integer.parseInt(strList.get(i)) - 1][n + 1].toString().trim() : "", name);
                            }
                            if((n +1) == playerInfo[Integer.parseInt(strList.get(i)) - 1].length
                                    && !"".equals(invoiceNumber)){
                                sc.setValue(invoiceNumber.trim(), "FIELD15");
                            }
                        }
                        JCO.ParameterList input = function.getImportParameterList();
                        if (radioButton1.isSelected()) {
                            input.setValue("X", "I_FLAG");
                        } else if (radioButton2.isSelected()) {
                            input.setValue("", "I_FLAG");
                        }
                        if (flag.equals("1")) {
                            input.setValue("X", "I_PRINTPRV");
                        } else {
                            input.setValue("", "I_PRINTPRV");
                        }

                        JCO.ParameterList tab = function.getTableParameterList();
                        tab.setValue(header, "T_VIMIIH");
                        tab.setValue(ite, "T_VIMIIP");
                        //执行function
                        mConnection.execute(function);

                        //得到输出参数
                        JCO.ParameterList export = function.getExportParameterList();
                        if(export.getValue("E_LAYOUT").equals("1")){//不带付款码的模板
                            templatePath = "pdfTemplate/template2.pdf";
                        }else if(export.getValue("E_LAYOUT").equals("2")){//带付款码的模板
                            templatePath = "pdfTemplate/template1.pdf";
                        }

                        JCO.Table tb = function.getTableParameterList().getTable("T_ITEM");
                        JCO.Structure stc = function.getExportParameterList().getStructure("E_HEAD");
                        Map<String, PDFTableDto> tableFields = tableFileds(tb, stc);
                        Map<String, Object> textFields = textFields(stc, flag);
                        Map<String, Object> qrCodeFields = qrCodeFields(stc);
                        //将需要打印的值传回sap
                        File outputFile = new File( pdfPath,stc.getValue("CUSTNO") + "-" + newPdfPath + ".pdf");

                        outputFile.createNewFile();

                        PDFTemplateExport te = new PDFTemplateExport();
                        te.setTemplatePdfPath(templatePath);
                        te.export(
                                new FileOutputStream(outputFile), textFields, qrCodeFields, tableFields);
                        //转换为png格式
                        //String imagePath = pdf2Image("newPdf/" + stc.getValue("CUSTNO") + "-" + newPdfPath + ".pdf", "newPdf/", 300);
                        //path[i] = new File(pdfPath + stc.getValue("CUSTNO") + "-" + newPdfPath + "/" + stc.getValue("CUSTNO") + "-" + newPdfPath + ".png");
                        //File filex = new File("newPdf/" + stc.getValue("CUSTNO") + "-" + newPdfPath + ".pdf");
                        //filex.createNewFile();
                        //pngToPdf(imagePath, "newPdf/" + stc.getValue("CUSTNO") + "-" + newPdfPath + ".pdf");
                        path[i] = outputFile;
                        //pathYuLan[i] = filex;
                        //new File(imagePath).delete();
                        //outputFile.delete();
                        //断开连接
                        mConnection.disconnect();
                    }
                }
                br.close();
                file.close();
                if(flag.equals("1")){
                    if(path.length > 0){
                        Desktop.getDesktop().open(path[0]);
                        /*for (int i = 0; i < pathYuLan.length; i++){
                            path[i].delete();
                        }*/
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return path;
    }

    /**
     * 表格内容
     * @param tb
     * @param export
     * @return
     */
    public Map<String, PDFTableDto> tableFileds(JCO.Table tb, JCO.Structure export){
        PDFTableDto tableDto = new PDFTableDto();
        tableDto.setColNames("摘要,金额,税率,税额");
        tableDto.setColFields("t1,t2,t3,t4");
        List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
        Map<String, Object> row;
        for (int i = 0; i < tb.getNumRows(); i++){
            row = new HashMap<String, Object>();
            tb.setRow(i);
            if(tb.getString("TEXT").startsWith("*")){
                row.put("t1",tb.getString("TEXT").substring(1, tb.getString("TEXT").length()));
            }else{
                row.put("t1",tb.getString("TEXT"));
            }


            row.put("t2",tb.getString("AMOUNT").trim());
            row.put("t3",tb.getString("TAXRATE"));
            row.put("t4",tb.getString("TAXAMOUNT").trim());
            dataList.add(row);
        }
        row = new HashMap<String, Object>();
        row.put("t1","合计（Sum）");
        row.put("t2",export.getString("AMOUNT_SUM").trim());
        row.put("t3","");
        row.put("t4",export.getString("TAXAMOUNT_SUM").trim());
        dataList.add(row);
        row = new HashMap<String, Object>();
        row.put("t1","价税合计（Total Sum）");
        row.put("t2","");
        row.put("t3","");
        row.put("t4",export.getString("TOTAL_SUM").trim());
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
    public Map<String, Object> qrCodeFields(JCO.Structure stc){
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

                String gName = stc.getValue("G_NAME").toString().trim().replace("&", "");
                //判断是否包含汉字
                if(gName.getBytes().length != gName.length()){
                    //判断是否超过25位&截取前25位
                    if(gName.length() > 25){
                        gName = stc.getValue("G_NAME").toString().trim().replace("&", "").substring(0, 25);
                    }
                }
                String qrCode = "corpid=" + s1 + "&amt=" + stc.getValue("TOTAL_SUM").toString().trim().replace(",", "") +
                        "&ref1=" + stc.getValue("BILLNO").toString().trim() + "&ref2="
                        + stc.getValue("CUSTNO").toString().trim() + "&ref3=" + stc.getValue("CONTNO").toString().trim() + "&ref4="
                        + gName;
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
    public Map<String, Object> textFields(JCO.Structure stc, String flag){
        Map<String, Object> textField = new HashMap<String, Object>();
        if(stc.getValue("BILLNO") != null && !"".equals(stc.getValue("BILLNO"))){
            textField.put("billNumber","帐单号: Bill No." + "" + stc.getValue("BILLNO"));
        }else{
            textField.put("billNumber","TEST PRINT");
        }

        textField.put("postcode",stc.getValue("PSTLZ"));
        textField.put("city",stc.getValue("ORT"));
        textField.put("detailSite",stc.getValue("STRAS"));
        textField.put("seven",stc.getValue("NAME"));
        textField.put("four",stc.getValue("NAME3"));
        textField.put("name",stc.getValue("NAME4"));
        textField.put("six",stc.getValue("RENTTEXT"));
        textField.put("printDate",stc.getValue("PRINTDATE"));
        textField.put("dueDate",stc.getValue("DUEDATE"));
        textField.put("client",stc.getValue("CUSTNO"));
        textField.put("contractNumber",stc.getValue("CONTNO"));
        textField.put("unitName",stc.getValue("G_NAME"));
        textField.put("identifyNumber",stc.getValue("NSCODE"));
        textField.put("sitePhone",stc.getValue("ADDRESS"));
        textField.put("accountName",stc.getValue("BANKNAME"));
        textField.put("account", stc.getValue("BANKCODE"));
        return textField;
    }
    //登录界面
    private static void login(){
        //Font font =new Font("黑体", Font.PLAIN, 20);//设置字体
        jf_1 = new JFrame("登陆界面");
        jf_1.setSize(400, 270);
        jf_1.setResizable(false);// 设置窗口不可拉伸

        jl_1 = new JLabel();

        jl_admin = new JLabel("用户名:");
        jl_admin.setBounds(80, 50, 60, 25);
        //jl_admin.setFont(font);

        jl_password = new JLabel("密码:");
        jl_password.setBounds(80, 90, 60, 25);
        //jl_password.setFont(font);

        loginBt1 = new JButton("登陆");         //更改成loginButton
        loginBt1.setBounds(150, 130, 80, 40);
        //loginBt1.setFont(font);

        //加入文本框
        jtext1=new JTextField();
        jtext1.setBounds(150, 50, 158, 25);
        //jtext1.setFont(font);

        jtext2=new JPasswordField();//密码输入框
        jtext2.setBounds(150, 90, 158, 25);
        //jtext2.setFont(font);

        jl_1.add(jtext1);
        jl_1.add(jtext2);

        jl_1.add(jl_admin);
        jl_1.add(jl_password);
        jl_1.add(loginBt1);

        jf_1.add(jl_1);
        jf_1.setVisible(true);
        jf_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf_1.setBackground(new Color(222,235,244));
        ImageIcon icon = new ImageIcon("src/img/Log.PNG");
        jf_1.setIconImage(icon.getImage());
        jf_1.setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        login();
        //登录监测
        ActionListener bt1_ls = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = new StringBuffer(jtext1.getText());//账号
                pass = new StringBuffer(String.valueOf(jtext2.getPassword()));//密码
                if(name == null || "".equals(name.toString()) || pass == null || "".equals(pass.toString())){
                    JOptionPane.showMessageDialog(null, "用户名或密码不能为空", "错误提示", JOptionPane.ERROR_MESSAGE);
                }else{
                    try{
                        FileReader file = new FileReader("login/loginPassword.txt");
                        BufferedReader br = new BufferedReader(file);
                        String str = null;
                        while ((str = br.readLine()) != null) {
                            String[] s = str.split(",");
                            JCO.Client mConnection = JCO.createClient(s[0], name.toString(), pass.toString(), s[1], s[2], s[3]);
                            mConnection.connect();
                            //连接成功后，跳转到查询界面
                            jf_1.dispose();
                            JFrame frame = new JFrame("Print Debit Note");
                            JPanel rootPane = new MainGUI(frame).panelContent;
                            ImageIcon icon = new ImageIcon("src/img/Log.PNG");
                            frame.setIconImage(icon.getImage());
                            frame.setContentPane(rootPane);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.pack();
                            frame.setSize(700, 600);
                            frame.setResizable(false);// 设置窗口不可拉伸
                            frame.setLocationRelativeTo(rootPane);// 居中
                            frame.setVisible(true);
                            //断开连接
                            mConnection.disconnect();
                        }
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
        loginBt1.addActionListener(bt1_ls);
    }
    class CheckBoxRenderer implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (value == null)
                return null;
            return (Component) value;
        }
    }

    /**
     * 表格体
     */
    @SuppressWarnings("serial")
    class CheckBoxEditor extends DefaultCellEditor implements ItemListener {
        private JCheckBox button;

        public CheckBoxEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (value == null)
                return null;
            button = (JCheckBox) value;
            button.addItemListener(this);
            return (Component) value;
        }

        public Object getCellEditorValue() {
            button.removeItemListener(this);
            return button;
        }

        public void itemStateChanged(ItemEvent e) {
            super.fireEditingStopped();
        }
    }
    //定义一个对话框窗口类
    class ConditionDailog extends JDialog {
        private JTextField text1,text2,text3,text4,text5;
        private JPanel jp;
        JScrollPane jsp = null;
        List<JTextField> jtfs = new ArrayList<JTextField>();//用于保存文本框

        public ConditionDailog(final String t) {
            super();
            this.setSize(300,320);
            this.setModal(true);//模态为真,如果不关闭这个对话框,那么无法点击其他窗口
            this.getContentPane().setLayout(null);//设置布局控制器
            this.setLocationRelativeTo(null);//设置打印窗口居中
            //jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            //jp = new JPanel();
            //GridLayout gridLayout = new GridLayout(0,2);//网格布局，不限制行，2列
            //jp.setLayout(gridLayout);
            for (int i = 0; i < 4; i++) {

                text1 = new JTextField();
                jtfs.add(text1);
                text1.setBounds(10,10 + i * 30,160,20);
                this.add(text1,null);

            }
            ImageIcon icon = new ImageIcon("src/img/Log.PNG");
            if(t != null){
                if(t.equals("2")){
                    this.setTitle("Business entity");
                }
                if(t.equals("3")){
                    this.setTitle("Invoice number");
                }
                if(t.equals("4")){
                    this.setTitle("Building");
                }
                if(t.equals("5")){
                    this.setTitle("Rental Unit");
                }
                if(t.equals("6")){
                    this.setTitle("Usage Type");
                }
                if(t.equals("7")){
                    this.setTitle("Lease-out");
                }
                if(t.equals("8")){
                    this.setTitle("Invoice Year");
                }
                if(t.equals("10")){
                    this.setTitle("Partner");
                }
                if(t.equals("11")){
                    this.setTitle("Logical business area");
                }
            }
            this.setIconImage(icon.getImage());
            JButton jb1 = new JButton("确定");
            jb1.setBounds(200,10,71,27);
            this.add(jb1);
            JButton jb2 = new JButton("添加");
            jb2.setBounds(200,40,71,27);
            this.add(jb2);
            //this.add(jsp,null);



            jb1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String str = "";
                    List<JTextField> list = new ArrayList<JTextField>();
                    int s = 0;
                    //去除空值
                    for (int i = 0; i < jtfs.size(); i++) {
                        if(!jtfs.get(i).getText().equals("")){//如果不为空就输出
                            list.add(jtfs.get(i));
                        }
                        if(jtfs.get(i).getText().contains(",")){
                            s++;
                        }
                    }
                    if(s > 0){
                        JOptionPane.showMessageDialog(null, "条件中不能包含','字符！", "错误提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    for (int i = 0; i < list.size(); i++) {
                        if(!list.get(i).getText().equals("")){//如果不为空就输出
                            if(i + 1 < list.size() && list.get(i).getText() != null && !"".equals(list.get(i).getText())){
                                str = str + list.get(i).getText() + ",";
                            }else {
                                str = str + list.get(i).getText();
                            }
                        }
                    }
                    if(t != null){
                        if(t.equals("1")){
                            conditionStart1.setText(str);
                        }
                        if(t.equals("2")){
                            conditionStart3.setText(str);
                        }
                        if(t.equals("3")){
                            conditionStart4.setText(str);
                        }
                        if(t.equals("4")){
                            conditionStart5.setText(str);
                        }
                        if(t.equals("5")){
                            conditionStart6.setText(str);
                        }
                        if(t.equals("6")){
                            conditionStart7.setText(str);
                        }
                        if(t.equals("7")){
                            conditionStart8.setText(str);
                        }
                        if(t.equals("8")){
                            conditionStart9.setText(str);
                        }
                        if(t.equals("9")){
                            conditionStart10.setText(str);
                        }
                        if(t.equals("10")){
                            conditionStart11.setText(str);
                        }
                        if(t.equals("11")){
                            conditionStart2.setText(str);
                        }
                    }

                    setVisible(false);// 本窗口隐藏
                    dispose();//释放资源
                }
            });
            jb2.addActionListener(new ActionListener() {//点击添加按钮添加一个文本框
                public void actionPerformed(ActionEvent e) {
                    text1 = new JTextField();
                    jtfs.add(text1);
                    text1.setBounds(10,10 + (jtfs.size() - 1) * 30,160,20);
                    ConditionDailog.this.add(text1,null);
                    ConditionDailog.this.getContentPane().validate();
                    ConditionDailog.this.getContentPane().repaint();
                }
            });

        }
    }
    /***
     * PDF文件转PNG图片，全部页数
     *
     * @param PdfFilePath pdf完整路径
     * @param imgFilePath 图片存放的文件夹
     * @param dpi dpi越大转换后越清晰，相对转换速度越慢
     * @return
     */
    public static String pdf2Image(String PdfFilePath, String dstImgFolder, int dpi) {
        File file = new File(PdfFilePath);
        PDDocument pdDocument;
        File dstFile = null;
        try {
            int dot = file.getName().lastIndexOf('.');
            String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
            pdDocument = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            /* dpi越大转换后越清晰，相对转换速度越慢 */
            PdfReader reader = new PdfReader(PdfFilePath);
            int pages = reader.getNumberOfPages();
            StringBuffer imgFilePath = null;

            for (int i = 0; i < pages; i++) {
                String imgFilePathPrefix = dstImgFolder + "/" + imagePDFName;
                imgFilePath = new StringBuffer();
                imgFilePath.append(imgFilePathPrefix);
                imgFilePath.append("_");
                imgFilePath.append(String.valueOf(i + 1));
                imgFilePath.append(".png");
                dstFile = new File(imgFilePath.toString());
                dstFile.createNewFile();
                BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                ImageIO.write(image, "png", dstFile);
            }
            reader.close();
            pdDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dstFile.getPath();
    }
    public void pngToPdf(String imagePath, String pdfPath){
        try{
            BufferedImage img = ImageIO.read(new File(imagePath));
            FileOutputStream fos = new FileOutputStream(pdfPath);
            Document doc = new Document(null, 0, 0, 0, 0);
            doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(imagePath);
            PdfWriter.getInstance(doc, fos);
            new File(imagePath).delete();
            doc.open();
            doc.add(image);
            doc.close();
            System.out.println("png转pdf转换成功！");
        }catch (Exception e) {
            e.getMessage();
        }
    }
}
