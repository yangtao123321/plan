package com.reyoung.tools;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.reyoung.model.Approve;
import com.reyoung.model.AttentDetail;
import com.reyoung.model.AttentPlan;
import com.reyoung.model.Flowinfos;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by yangtao on 2020-02-25.
 */
public class AttentTools {

    // 产生维修计划表标题
    public static Font setChineseFont(HttpServletRequest request) {
        BaseFont bf = null;
        Font fontChinese = null;
        try {

            String pat = request.getSession().getServletContext().getRealPath(File.separator+"font"+File.separator+"STFANGSO.TTF");

            bf = BaseFont.createFont(pat,
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            fontChinese = new Font(bf, 20);

            fontChinese.setStyle( Font.BOLD|Font.UNDERLINE);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontChinese;
    }

    //维修计划主题的字体
    public static Font setlvxinfont(HttpServletRequest request) {

        BaseFont bf = null;

        Font fontChinese = null;

        try {

            String pat = request.getSession().getServletContext().getRealPath(File.separator+"font"+File.separator+"STFANGSO.TTF");

            bf = BaseFont.createFont(pat,
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            fontChinese = new Font(bf, 14f);

            //fontChinese.setStyle(Font.BOLD);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fontChinese;

    }

    //滤芯计划的单元格样式表 标题
    public static PdfPCell createlvxintitlecell(String str,int row,int col,HttpServletRequest request) {

        PdfPCell cell1 = new PdfPCell(new Paragraph(str,setlvxinfont(request)));

        cell1.setUseAscender(true);
        cell1.setUseDescender(true);
        /*cell1.setVerticalAlignment(cell1.ALIGN_MIDDLE);*/
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell1.setBorderWidth(0.3f);
        cell1.setMinimumHeight(30);
        cell1.setColspan(row);
        cell1.setRowspan(col);

        return cell1;

    }

    //签字批准的栏
    public static PdfPCell createrepairesignaturetitle(String str,int row,int col,HttpServletRequest request) {

        PdfPCell cell1 = new PdfPCell(new Paragraph(str,setrepairesignaturetitle(request)));

        cell1.setUseAscender(true);
        cell1.setUseDescender(true);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell1.setBorderWidth(0.3f);
        cell1.setMinimumHeight(35);
        cell1.setColspan(row);
        cell1.setRowspan(col);

        return cell1;

    }

    //维修计划负责人签字栏
    public static PdfPCell createrepairesignaturecell(String str,int row,int col,HttpServletRequest request) {

        PdfPCell cell1 = new PdfPCell(new Paragraph(str,setlvxinfont(request)));

        cell1.setUseAscender(true);
        cell1.setUseDescender(true);
        /*cell1.setVerticalAlignment(cell1.ALIGN_MIDDLE);*/
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell1.setBorderWidth(0.3f);
        cell1.setMinimumHeight(25);
        cell1.setColspan(row);
        cell1.setRowspan(col);
        cell1.disableBorderSide(2);


        return cell1;


    }

    public static PdfPCell createrepairecontextitle(String str,int row,int col,HttpServletRequest request) {

        PdfPCell cell1 = new PdfPCell(new Paragraph(str,setlvxinfont(request)));

        //PdfPCell cell1 = new PdfPCell(paragraph);

        cell1.setLeading(6,1);

        cell1.setUseAscender(true);
        cell1.setUseDescender(true);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell1.setBorderWidth(0.3f);
        cell1.setMinimumHeight(230);
        cell1.setColspan(row);
        cell1.setRowspan(col);

        return cell1;

    }

    //维修计划施工项目现状内容栏
    public static PdfPCell createrepairecontext(String str,int row,int col,HttpServletRequest request) {

        PdfPCell cell1 = new PdfPCell(new Paragraph(str,setlvxinfont(request)));

        //PdfPCell cell1 = new PdfPCell(paragraph);

        cell1.setLeading(6,1);

        cell1.setUseAscender(true);
        cell1.setUseDescender(true);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell1.setBorderWidth(0.3f);
        cell1.setMinimumHeight(230);
        cell1.setColspan(row);
        cell1.setRowspan(col);

        return cell1;

    }

    //采购设备要求栏
    public static PdfPCell createrepairerequires(String str,int row,int col,HttpServletRequest request) {

        PdfPCell cell1 = new PdfPCell(new Paragraph(str,setlvxinfont(request)));

        cell1.setLeading(6,1);

        cell1.setUseAscender(true);
        cell1.setUseDescender(true);
        /*cell1.setVerticalAlignment(cell1.ALIGN_MIDDLE);*/
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell1.setBorderWidth(0.3f);
        cell1.setMinimumHeight(160);
        cell1.setColspan(row);
        cell1.setRowspan(col);

        return cell1;

    }

    //采购设备详情栏目
    public static PdfPCell createrepairerequiresdetail(String str,int row,int col,HttpServletRequest request) {

        PdfPCell cell1 = new PdfPCell(new Paragraph(str,setlvxinfont(request)));

        cell1.setUseAscender(true);
        cell1.setUseDescender(true);
        /*cell1.setVerticalAlignment(cell1.ALIGN_MIDDLE);*/
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell1.setBorderWidth(0.3f);
        cell1.setMinimumHeight(30);
        cell1.setColspan(row);
        cell1.setRowspan(col);

        return cell1;

    }

    //添加对设备的其他的要求的样式
    public static PdfPCell createrepairerequiresother(String str, int row,int col,HttpServletRequest request){

        PdfPCell cell1 = new PdfPCell(new Paragraph(str,setlvxinfont(request)));

        cell1.setUseAscender(true);
        cell1.setUseDescender(true);
        /*cell1.setVerticalAlignment(cell1.ALIGN_MIDDLE);*/
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell1.setBorderWidth(0.3f);
        cell1.setMinimumHeight(90);
        cell1.setColspan(row);
        cell1.setRowspan(col);

        return cell1;

    }

    //采购设备要求栏
    public static PdfPCell createrepairerequires1(String str,int row,int col,HttpServletRequest request) {

        PdfPCell cell1 = new PdfPCell(new Paragraph(str,setlvxinfont(request)));

        cell1.setLeading(6,1);

        cell1.setUseAscender(true);
        cell1.setUseDescender(true);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell1.setBorderWidth(0.3f);
        cell1.setMinimumHeight(50);
        cell1.setColspan(row);
        cell1.setRowspan(col);

        return cell1;

    }

    //添加对采购物品的要求  当采购明细不存在、采购要求为空时 高度适当的变小
    public static PdfPCell createrepairerequiresother1(String str, int row,int col,HttpServletRequest request) {

        PdfPCell cell1 = new PdfPCell(new Paragraph(str,setlvxinfont(request)));

        cell1.setUseAscender(true);
        cell1.setUseDescender(true);
        /*cell1.setVerticalAlignment(cell1.ALIGN_MIDDLE);*/
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell1.setBorderWidth(0.3f);
        cell1.setMinimumHeight(50);
        cell1.setColspan(row);
        cell1.setRowspan(col);

        return cell1;

    }

    //设置维修计划签字批准的
    public static Font setrepairesignaturetitle(HttpServletRequest request) {

        BaseFont bf = null;
        Font fontChinese = null;
        try {

            //STXINWEI.TTF  STKAITI.TTF 华文楷体   STZHONGS.TTF

            /*bf = BaseFont.createFont("C:/Windows/Fonts/STSONG.TTF",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);*/

            String pat = request.getSession().getServletContext().getRealPath(File.separator+"font"+File.separator+"STZHONGS.TTF");

            bf = BaseFont.createFont(pat,
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            fontChinese = new Font(bf, 14f);

            //fontChinese.setStyle(Font.BOLD);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fontChinese;


    }

    //负责产生天成图片的单元格对象
    public static PdfPCell createpdfcell(Image image,int row,int col) {

        PdfPCell cell1 = new PdfPCell(image);

        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell1.setBorderWidth(0.5f);
        cell1.setMinimumHeight(85);
        cell1.setColspan(row);
        cell1.setRowspan(col);
        cell1.disableBorderSide(1);


        return cell1;

    }

    //负责部门为本部门的签字审批
    public static String makereport(AttentPlan attentPlan,Flowinfos flowinfos, java.util.List<Approve> approves,HttpServletRequest request) {

        String zhangzong=null;

        String dunit=null;

        for (Approve a:approves) {

            if (a.getUser().getPosition().getPosid()==3) {

                File[] files= new File(request.getSession().getServletContext().getRealPath(a.getSignature())).getParentFile().listFiles();

                Random random=new Random();

                File files1=files[random.nextInt(files.length)];

                zhangzong=files1.getAbsolutePath();

            }else if (a.getUser().getPosition().getPosid()==2) {

                File[] files= new File(request.getSession().getServletContext().getRealPath(a.getSignature())).getParentFile().listFiles();

                Random random=new Random();

                File files1=files[random.nextInt(files.length)];

                dunit=files1.getAbsolutePath();

            }

        }

        String filename=null;

        try{

            Rectangle rectangle=new Rectangle(PageSize.A4);//设置A4纸

            Document d=new Document(rectangle,35,30,10,10);

            String s = request.getSession().getServletContext().getRealPath(File.separator+"makereports");

            filename="Incident:"+flowinfos.getFlowinfoid()+"-"+GetYear.getfilename()+".pdf";

            File file=new File(s,filename);

            PdfWriter pdfWriter = PdfWriter.getInstance(d, new FileOutputStream(file));

            d.open();

            Paragraph elements = new Paragraph(attentPlan.getAttenttitle(), setChineseFont(request));

            elements.setAlignment(Element.ALIGN_CENTER);

            Image chapter = Image.getInstance(request.getSession().getServletContext().getRealPath(flowinfos.getUser().getChapter()));

            chapter.scaleToFit(105,105);

            //设置绝对路径
            chapter.setAbsolutePosition(210,700);

            d.add(chapter);

            d.add(elements);

            //设置对文本进行绝对定位
            PdfContentByte cb = pdfWriter.getDirectContent();

            String pat = request.getSession().getServletContext().getRealPath(File.separator + "font" + File.separator + "simkai.ttf");

            BaseFont bf = BaseFont.createFont(pat,//字体设置为楷体
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.setTextMatrix(520, 810);//文本绝对定位的坐标
            cb.showText("No:"+flowinfos.getFlowinfoid());
            cb.endText();
            //设置对文本的定位

            //各单位的章

            Image img = Image.getInstance(zhangzong);

            Image a=Image.getInstance(dunit);

            img.scaleToFit(65, 65);

            a.scaleToFit(70,70);

            //设置绝对路径
            img.setAbsolutePosition(90,360);

            //创建表格对象
            PdfPTable datatable = new PdfPTable(6);

            datatable.setSpacingBefore(30);

            datatable.setTotalWidth(new float[] {50,50,50,90,30,30});

            datatable.setWidthPercentage(100);// 表格的宽度百分比

            datatable.getDefaultCell().setBorderWidth(0.1f);// 边框宽度

            // 设置表格的底色
            datatable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
            datatable.getDefaultCell().setHorizontalAlignment(Element.BODY);

            PdfPCell pCell = createlvxintitlecell("提报单位", 1, 1,request);

            datatable.addCell(pCell);

            datatable.addCell(createlvxintitlecell(flowinfos.getUser().getDepartment().getDeptname(),5,1,request));

            datatable.addCell(createlvxintitlecell("提报日期",1,1,request));

            datatable.addCell(createlvxintitlecell(GetYear.getformdate(flowinfos.getStartime()),2,1,request));

            datatable.addCell(createlvxintitlecell("提报人", 1, 1,request));

            datatable.addCell(createlvxintitlecell(flowinfos.getPerson(), 7, 1,request));

            datatable.addCell(createrepairecontextitle("提   报\r\n\r\n原   因", 1, 1,request));

            datatable.addCell(createrepairecontext(attentPlan.getBuyreson(), 5, 1,request));//将前后空格去掉后在前面加3个tab

            Integer res=0;

            if (attentPlan.getAttentDetails()==null||attentPlan.getAttentDetails().size()==0) {



            }else {

                res=attentPlan.getAttentDetails().size();

            }

            if (res>0) {

                res=res+2;

                //需要循环遍历需要采购的设备的详情列
                datatable.addCell(createrepairerequires("采   购\r\n要   求", 1, res,request));

                datatable.addCell(createrepairerequiresdetail("名称", 2, 1,request));
                datatable.addCell(createrepairerequiresdetail("规格", 2, 1,request));
                datatable.addCell(createrepairerequiresdetail("数量", 1, 1,request));

                for (AttentDetail detail:attentPlan.getAttentDetails()) {

                    datatable.addCell(createrepairerequiresdetail(detail.getAttentname(),2,1,request));
                    datatable.addCell(createrepairerequiresdetail(detail.getAttentspecial(),2,1,request));
                    datatable.addCell(createrepairerequiresdetail(detail.getAttentnum(),1,1,request));

                }

                datatable.addCell(createrepairerequiresother(attentPlan.getAttentrequire(), 5, 1,request));

            }else if (res==0) {

                res=res+1;

                if (attentPlan.getAttentrequire()==null||attentPlan.getAttentrequire().trim().equals("")) {


                    //需要循环遍历需要采购的设备的详情列
                    datatable.addCell(createrepairerequires1("要   求", 1, res, request));

                    datatable.addCell(createrepairerequiresother1(attentPlan.getAttentrequire(), 5, 1, request));



                }else {

                    //需要循环遍历需要采购的设备的详情列
                    datatable.addCell(createrepairerequires("要   求", 1, res,request));

                    datatable.addCell(createrepairerequiresother(attentPlan.getAttentrequire(), 5, 1,request));

                }

            }

            datatable.addCell(createrepairesignaturetitle("签  字  批  准", 6, 1,request));

            datatable.addCell(createrepairesignaturecell("单位负责人:", 3, 1,request));

            datatable.addCell(createrepairesignaturecell("部门经理:",3,1,request));

            datatable.addCell(createpdfcell(a,3,1));

            datatable.addCell(createpdfcell(img,3,1));

            d.add(datatable);

            d.close();

        }catch (Exception e) {



        }


        return filename;

    }

    //负责部门为本部门的签字审批
    public static String makereport1(AttentPlan attentPlan,Flowinfos flowinfos,List<Approve> approves,HttpServletRequest request) {

        String zhangzong=null;

        String dunit=null;

        for (Approve a:approves) {

            if (a.getUser().getPosition().getPosid()==3) {

                File[] files= new File(request.getSession().getServletContext().getRealPath(a.getSignature())).getParentFile().listFiles();

                Random random=new Random();

                File files1=files[random.nextInt(files.length)];

                zhangzong=files1.getAbsolutePath();

            }else if (a.getUser().getPosition().getPosid()==2) {

                File[] files= new File(request.getSession().getServletContext().getRealPath(a.getSignature())).getParentFile().listFiles();

                Random random=new Random();

                File files1=files[random.nextInt(files.length)];

                dunit=files1.getAbsolutePath();

            }

        }

        String filename=null;

        try{

            Rectangle rectangle=new Rectangle(PageSize.A4);//设置A4纸

            Document d=new Document(rectangle,35,30,10,10);

            String s = request.getSession().getServletContext().getRealPath(File.separator+"makereports");

            filename="Incident:"+flowinfos.getFlowinfoid()+"-"+GetYear.getfilename()+".pdf";

            File file=new File(s,filename);

            PdfWriter pdfWriter = PdfWriter.getInstance(d, new FileOutputStream(file));

            d.open();

            Paragraph elements = new Paragraph(attentPlan.getAttenttitle(), setChineseFont(request));

            elements.setAlignment(Element.ALIGN_CENTER);

            Image chapter = Image.getInstance(request.getSession().getServletContext().getRealPath(flowinfos.getUser().getChapter()));

            chapter.scaleToFit(105,105);

            //设置绝对路径
            chapter.setAbsolutePosition(210,700);

            d.add(chapter);

            d.add(elements);

            //设置对文本进行绝对定位
            PdfContentByte cb = pdfWriter.getDirectContent();

            String pat = request.getSession().getServletContext().getRealPath(File.separator + "font" + File.separator + "simkai.ttf");

            BaseFont bf = BaseFont.createFont(pat,//字体设置为楷体
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.setTextMatrix(520, 810);//文本绝对定位的坐标
            cb.showText("No:"+flowinfos.getFlowinfoid());
            cb.endText();
            //设置对文本的定位

            //各单位的章


            Image img = Image.getInstance(zhangzong);

            Image a=Image.getInstance(dunit);

            Image empty = Image.getInstance(request.getSession().getServletContext().getRealPath(File.separator+"picture"+File.separator+"signature"+File.separator+"empty.jpg"));

            img.scaleToFit(65, 65);

            a.scaleToFit(70,70);

            empty.scaleToFit(30,30);

            //设置绝对路径
            img.setAbsolutePosition(90,360);

            PdfPTable datatable = Getdatatable.attainpdfptable();

            PdfPCell pCell = createlvxintitlecell("提报单位", 1, 1,request);

            datatable.addCell(pCell);

            datatable.addCell(createlvxintitlecell(flowinfos.getUser().getDepartment().getDeptname(),5,1,request));

            datatable.addCell(createlvxintitlecell("提报日期",1,1,request));

            datatable.addCell(createlvxintitlecell(GetYear.getformdate(flowinfos.getStartime()),2,1,request));

            datatable.addCell(createlvxintitlecell("提报人", 1, 1,request));

            datatable.addCell(createlvxintitlecell(flowinfos.getPerson(), 7, 1,request));

            datatable.addCell(createrepairecontextitle("提   报\r\n\r\n原   因", 1, 1,request));

            datatable.addCell(createrepairecontext(attentPlan.getBuyreson(), 5, 1,request));//将前后空格去掉后在前面加3个tab

            Integer res=0;

            if (attentPlan.getAttentDetails()==null||attentPlan.getAttentDetails().size()==0) {

            }else {

                res=attentPlan.getAttentDetails().size();

            }

            if (res>0) {

                res=res+2;

                //需要循环遍历需要采购的设备的详情列
                datatable.addCell(createrepairerequires("采   购\r\n要   求", 1, res,request));

                datatable.addCell(createrepairerequiresdetail("名称", 2, 1,request));
                datatable.addCell(createrepairerequiresdetail("规格", 2, 1,request));
                datatable.addCell(createrepairerequiresdetail("数量", 1, 1,request));

                for (AttentDetail detail:attentPlan.getAttentDetails()) {

                    datatable.addCell(createrepairerequiresdetail(detail.getAttentname(),2,1,request));
                    datatable.addCell(createrepairerequiresdetail(detail.getAttentspecial(),2,1,request));
                    datatable.addCell(createrepairerequiresdetail(detail.getAttentnum(),1,1,request));

                }

                datatable.addCell(createrepairerequiresother(attentPlan.getAttentrequire(), 5, 1,request));

            }else if (res==0) {

                res=res+1;

                if (attentPlan.getAttentrequire()==null||attentPlan.getAttentrequire().trim().equals("")) {

                    //需要循环遍历需要采购的设备的详情列
                    datatable.addCell(createrepairerequires1("要   求", 1, res, request));

                    datatable.addCell(createrepairerequiresother1(attentPlan.getAttentrequire(), 5, 1,request));

                }else {

                    //需要循环遍历需要采购的设备的详情列
                    datatable.addCell(createrepairerequires("要   求", 1, res,request));

                    datatable.addCell(createrepairerequiresother(attentPlan.getAttentrequire(), 5, 1,request));

                }

            }

            datatable.addCell(createrepairesignaturetitle("签  字  批  准", 6, 1,request));

            PdfPTable datatable1 = Getdatatable.attainpdfptable1();

            datatable1.addCell(createrepairesignaturecell("单位负责人:", 2, 1,request));

            datatable1.addCell(createrepairesignaturecell("部门经理:",2,1,request));

            datatable1.addCell(createrepairesignaturecell("总裁",2,1,request));

            datatable1.addCell(createpdfcell(a,2,1));

            datatable1.addCell(createpdfcell(img,2,1));

            datatable1.addCell(createpdfcell(empty,2,1));

            d.add(datatable);

            d.add(datatable1);

            d.close();

        }catch (Exception e) {



        }


        return filename;

    }

}
