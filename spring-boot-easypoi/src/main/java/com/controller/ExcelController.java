package com.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.pojo.SimpleObjectResult;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description:
 * @author: LinQin
 * @date: 2018/11/05
 */
@Controller
public class ExcelController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HttpServletResponse response;

    private static SimpleObjectResult getData() {
        SimpleObjectResult data = new SimpleObjectResult();
        List<String> list = new ArrayList<>();
        list.add("q1");
        list.add("q2");
        list.add("q3");
        list.add("q4");
        list.add("q5");
        list.add("q6");

        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("q1", Math.random());
            map.put("q2", Math.random());
            map.put("q3", Math.random());
            map.put("q4", Math.random());
            map.put("q5", Math.random());
            map.put("q6", Math.random());
            mapList.add(map);
        }

        data.setTotal(100L);
        data.setTook(0L);
        data.setHeaders(list);
        data.setLines(mapList);

        return data;
    }

    @ResponseBody
    @RequestMapping("/downLoadExcel")
    public void downLoadExcel() throws IOException {
        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
        List<Map<String, Object>> lines = getData().getLines();
        List<String> headers = getData().getHeaders();

        for (String item : headers) {
            ExcelExportEntity excelHead = new ExcelExportEntity(item, item);
            excelHead.setWidth(20);
            excelHead.setHeight(15);
            entity.add(excelHead);
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("测试", "测试"), entity,
                lines);

        String fileName = "测试文件.xls";
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("content-Type", "application/vnd.ms-excel");
        workbook.write(response.getOutputStream());

    }

    @RequestMapping("/transReport")
    public void transReport(String startDate, String endDate,
                            HttpServletResponse response, HttpSession session) throws Exception {
        List<Map<String, Object>> lines = getData().getLines();
        List<String> headers = getData().getHeaders();

        HSSFWorkbook wb = new HSSFWorkbook();
        /* 报表字体 */
        Font fontColumn = wb.createFont();
        fontColumn.setFontName("宋体");
        // 设置字体大小
        fontColumn.setFontHeightInPoints((short) 12);
        fontColumn.setBold(true);

        // 居中样式
        CellStyle styleColumn = wb.createCellStyle();
        styleColumn.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        styleColumn.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleColumn.setFont(fontColumn);

        // 两位小数点样式
        CellStyle formatStyle = wb.createCellStyle();
        formatStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 居右
        formatStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        DataFormat format = wb.createDataFormat();
        formatStyle.setDataFormat(format.getFormat("0.00"));

        String strName = "";

        int sheetNum = 1;// 当前的sheet索引;
        int recNum = 0;// 当前list的遍历的记录索引
        int sheetNnum = 50; // 设置一sheet的大小
        while (true) {
            int rowNum = 0;  //行
            // 报表头内容，占1行
            HSSFSheet ws = wb.createSheet("第"+sheetNum+"页");
            sheetNum++;
            HSSFRow rowColumn0 = ws.createRow(0); // 创建行
            rowColumn0.createCell(0).setCellValue("序号");
            rowColumn0.createCell(1).setCellValue("订单号");
            rowColumn0.createCell(2).setCellValue("交易类型");

            for (int i = 0; i <= 2; i++) {
                rowColumn0.getCell(i).setCellStyle(styleColumn);
            }

            int sheetRecNum = 0;  //当前sheet记录行数;
            for (int i = recNum; i < lines.size(); i++,recNum++, rowNum++, sheetRecNum++) {
                //单个SHEET数据行超过SHEET应达到数据限额，则停止写入
                if(sheetRecNum >= sheetNnum) break;
                Map<String, Object> map = lines.get(i);
                HSSFRow rowBody = ws.createRow(sheetRecNum + 1);
                rowBody.createCell(0).setCellValue(sheetRecNum + 1);
                rowBody.createCell(1).setCellValue("2112");
                rowBody.createCell(2).setCellValue("dfds");

                rowBody.getCell(0).setCellStyle(styleColumn);
            }

            HSSFRow rowEnd = ws.createRow(sheetRecNum + 1);
            rowEnd.createCell(3).setCellValue("合计");
            rowEnd.createCell(4).setCellValue(sheetRecNum+"行");
            rowEnd.getCell(3).setCellStyle(formatStyle);
            rowEnd.getCell(4).setCellStyle(formatStyle);

            // 设置列宽
            ws.setColumnWidth(0, 2500);
            ws.setColumnWidth(1, 6000);
            ws.setColumnWidth(2, 5500);
            if(recNum >= lines.size()) break;
        }
        //主动清空list集合，减少内存消耗。
        lines=null;
        String fileName = "账单明细报表" + startDate + "--" + endDate + ".xls";
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("GBK");
        response.setHeader("Content-disposition", "attachment; filename="
                + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        logger.info("【导出账单明细报表结束】beginDate=" + startDate + ",endDate="
                + endDate);
        wb.write(response.getOutputStream());

    }


    public static void main(String[] args) {
        FileOutputStream fileOut = null;
        BufferedImage bufferImg = null;
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            //加载图片
            bufferImg = ImageIO.read(new File("C:\\Users\\LinQin\\Desktop\\11.png"));
            ImageIO.write(bufferImg, "png", byteArrayOut);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet1 = wb.createSheet("sheet1");
            HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
            /**
             dx1 - the x coordinate within the first cell.//定义了图片在第一个cell内的偏移x坐标，既左上角所在cell的偏移x坐标，一般可设0
             dy1 - the y coordinate within the first cell.//定义了图片在第一个cell的偏移y坐标，既左上角所在cell的偏移y坐标，一般可设0
             dx2 - the x coordinate within the second cell.//定义了图片在第二个cell的偏移x坐标，既右下角所在cell的偏移x坐标，一般可设0
             dy2 - the y coordinate within the second cell.//定义了图片在第二个cell的偏移y坐标，既右下角所在cell的偏移y坐标，一般可设0
             col1 - the column (0 based) of the first cell.//第一个cell所在列，既图片左上角所在列
             row1 - the row (0 based) of the first cell.//图片左上角所在行
             col2 - the column (0 based) of the second cell.//图片右下角所在列
             row2 - the row (0 based) of the second cell.//图片右下角所在行
             */
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,(short) 2, 2, (short) 5, 8);
            //插入图片
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
            fileOut = new FileOutputStream("C:\\Users\\LinQin\\Desktop\\excel.xls");
            // 输出文件
            wb.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
