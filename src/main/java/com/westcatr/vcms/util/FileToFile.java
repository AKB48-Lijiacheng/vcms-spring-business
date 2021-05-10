package com.westcatr.vcms.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.color.ColorSpace;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.sl.usermodel.TextRun;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import cn.hutool.core.io.FileUtil;

/**
 * 文件转换
 */
public class FileToFile {
    /**
     * 将多页pdf转化为多张图片
     *
     * @param pdfPath
     * @return
     * @throws IOException
     */
    public static List<String> pdfPathToImagePaths(String pdfPath, String pdfName) {
        File pdfFile = new File(pdfPath);
        PDDocument pdDocument;
        try {
            pdDocument = PDDocument.load(pdfFile);
            int pageCount = pdDocument.getNumberOfPages();
            PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
            List<String> imagePathList = new ArrayList<>();
            String fileParent = pdfFile.getParent();
            for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
                String imgPath = fileParent + File.separator + pdfName + "/" + pdfName + "-" + (pageIndex + 1) + ".png";
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 105, ImageType.RGB);
                ImageIO.write(image, "png", new File(imgPath));
                imagePathList.add(imgPath);

            }
            pdDocument.close();
            return imagePathList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ppt2003 文档的转换 后缀名为.ppt
     *
     * @param pptFile ppt文件
     * @param imgFile 图片将要保存的目录（不是文件）
     * @return
     */
    public static boolean doPPT2003toImage(String filePath, String imgPath, List<String> list) {
        try {
            String fileExtName = FileUtil.getName(filePath);
            String fileNameSave = fileExtName.substring(0, fileExtName.lastIndexOf("."));
            File imgFile = new File(imgPath);
            HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(filePath));

            Dimension pgsize = ppt.getPageSize();
            for (int i = 0; i < ppt.getSlides().size(); i++) {
                // 防止中文乱码
                for (HSLFShape shape : ppt.getSlides().get(i).getShapes()) {
                    if (shape instanceof HSLFTextShape) {
                        HSLFTextShape tsh = (HSLFTextShape) shape;
                        for (HSLFTextParagraph p : tsh) {
                            for (HSLFTextRun r : p) {
                                r.setFontFamily("宋体");
                            }
                        }
                    }
                }
                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                // clear the drawing area
                graphics.setPaint(Color.WHITE);
                graphics.fill(new java.awt.geom.Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                // render
                ppt.getSlides().get(i).draw(graphics);

                // 图片将要存放的路径
                String absolutePath = imgFile.getAbsolutePath() + "/" + fileNameSave + "-" + (i + 1) + ".png";
                File jpegFile = new File(absolutePath);
                // 图片路径存放
                list.add(fileNameSave + "-" + (i + 1) + ".png");

                // 如果图片存在，则不再生成
                if (jpegFile.exists()) {
                    continue;
                }
                // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                FileOutputStream out = new FileOutputStream(jpegFile);
                // 写入到图片中去
                ImageIO.write(img, "png", out);
                out.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return false;
    }

    /**
     * ppt2007文档的转换 后缀为.pptx
     *
     * @param pptFile PPT文件
     * @param imgFile 图片将要保存的路径目录（不是文件）
     * @param list    存放文件名的 list
     * @return
     */
    public static boolean doPPT2007toImage(String filePath, String imgPath, List<String> list) {
        FileInputStream is = null;
        try {
            String fileExtName = FileUtil.getName(filePath);
            String fileNameSave = fileExtName.substring(0, fileExtName.lastIndexOf("."));
            File imgFile = new File(imgPath);
            File pptFile = new File(filePath);
            is = new FileInputStream(pptFile);
            XMLSlideShow xmlSlideShow = new XMLSlideShow(is);
            is.close();
            // 获取大小
            Dimension pgsize = xmlSlideShow.getPageSize();
            // 获取幻灯片
            List<XSLFSlide> slides = xmlSlideShow.getSlides();
            for (int i = 0; i < slides.size(); i++) {
                // 解决乱码问题
                List<XSLFShape> shapes = slides.get(i).getShapes();
                for (XSLFShape shape : shapes) {

                    if (shape instanceof XSLFTextShape) {
                        XSLFTextShape sh = (XSLFTextShape) shape;
                        List<XSLFTextParagraph> textParagraphs = sh.getTextParagraphs();

                        for (XSLFTextParagraph xslfTextParagraph : textParagraphs) {
                            List<XSLFTextRun> textRuns = xslfTextParagraph.getTextRuns();
                            for (XSLFTextRun xslfTextRun : textRuns) {
                                xslfTextRun.setFontFamily("宋体");
                            }
                        }
                    }
                }
                int times=2;
                // 根据幻灯片大小生成图片
                BufferedImage img = new BufferedImage(pgsize.width*times, pgsize.height*times, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                graphics.setPaint(Color.WHITE);
                graphics.scale(times, times);
                graphics.fill(new java.awt.geom.Rectangle2D.Float(0, 0, pgsize.width*times, pgsize.height*times));

                // 最核心的代码
                slides.get(i).draw(graphics);

                // 图片将要存放的路径
                String absolutePath = imgFile.getAbsolutePath() + "/" + fileNameSave + "-" + (i + 1) + ".png";
                File jpegFile = new File(absolutePath);
                // 图片路径存放
                list.add(fileNameSave + "-" + (i + 1) + ".png");

                // 如果图片存在，则不再生成
                if (jpegFile.exists()) {
                    continue;
                }
                // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                FileOutputStream out = new FileOutputStream(jpegFile);
                // 写入到图片中去
                ImageIO.write(img, "png", out);
                out.close();
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 截取视频获得指定帧的图片
     *
     * @param video   源视频文件
     * @param picPath 截图存放路径
     */
    /* public static void getVideoPic(String videoPath, String picPath) {
        File video = new File(videoPath);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video);
        try {
            ff.start();
            // 截取中间帧图片(具体依实际情况而定)
            int i = 0;
            int length = ff.getLengthInFrames();
            int middleFrame = length / 2;
            Frame frame = null;
            while (i < length) {
                frame = ff.grabFrame();
                if ((i > middleFrame) && (frame.image != null)) {
                    break;
                }
                i++;
            }
            // 截取的帧图片
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage srcImage = converter.getBufferedImage(frame);
            int srcImageWidth = srcImage.getWidth();
            int srcImageHeight = srcImage.getHeight();

            // 对截图进行等比例缩放(缩略图)
            int width = 480;
            int height = (int) (((double) width / srcImageWidth) * srcImageHeight);
            BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            thumbnailImage.getGraphics().drawImage(
                    srcImage.getScaledInstance(width, height, java.awt.image.BufferedImage.SCALE_SMOOTH), 0, 0, null);

            File picFile = new File(picPath);
            ImageIO.write(thumbnailImage, "png", picFile);

            ff.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{

        }
    } */

    /* public static void main(String[] args) {
        String vpath = "/Users/liusheng/ceshi/ceshi2.mov";
        String savePath = "/Users/liusheng/ceshi/";
        getVideoPic(vpath, savePath);
    } */
}