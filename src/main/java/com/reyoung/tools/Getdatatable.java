package com.reyoung.tools;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * Created by yangtao on 2020-02-25.
 */
public class Getdatatable {

    public static PdfPTable attainpdfptable() {

        PdfPTable datatable=null;

        try {

        //创建表格对象
        datatable = new PdfPTable(6);

        datatable.setSpacingBefore(30);

            datatable.setTotalWidth(new float[] {50,50,50,90,30,30});
            datatable.setWidthPercentage(100);// 表格的宽度百分比

            datatable.getDefaultCell().setBorderWidth(0.1f);// 边框宽度

            // 设置表格的底色
            datatable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
            datatable.getDefaultCell().setHorizontalAlignment(Element.BODY);

        } catch (DocumentException e) {

        }finally {

            return datatable;

        }



    }

    public static PdfPTable attainpdfptable1() {

        PdfPTable datatable=null;

        try {

            //创建表格对象
            datatable = new PdfPTable(6);

            datatable.setSpacingBefore(0);

            datatable.setTotalWidth(new float[] {50,50,50,50,50,50});
            datatable.setWidthPercentage(100);// 表格的宽度百分比

            datatable.getDefaultCell().setBorderWidth(0.1f);// 边框宽度

            // 设置表格的底色
            datatable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
            datatable.getDefaultCell().setHorizontalAlignment(Element.BODY);

        } catch (DocumentException e) {

        }finally {

            return datatable;

        }



    }


}
