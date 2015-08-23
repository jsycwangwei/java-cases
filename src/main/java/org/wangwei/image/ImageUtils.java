package org.wangwei.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

public class ImageUtils {

    public void resizeImg(String path, int width, int height) throws Exception {
        BufferedImage img = ImageIO.read(new File(path));
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        // check width and height
        if (imgWidth > width || imgHeight > height) {
            float widthRate = (float) imgWidth / (float) width;
            float heightRate = (float) imgHeight / (float) height;
            float rate = widthRate > heightRate ? widthRate : heightRate;
            rewriteImg(path, path, 0, 0, (int) (imgWidth / rate), (int) (imgHeight / rate), BufferedImage.TYPE_INT_RGB);
        }
    }

    private boolean rewriteImg(String origin, String dest, int x, int y, int width, int height, Integer scaleType) {
        try {
            // img's suffix
            String suffix = origin.substring(origin.lastIndexOf(".") + 1);
            // source image
            Image image = ImageIO.read(new File(origin));

            // get target
            BufferedImage tag = new BufferedImage(width, height, scaleType);
            Graphics g = tag.getGraphics();
            g.drawImage(image, x, y, width, height, null);
            g.dispose();

            // Graphics to ByteArrayOutputStream
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(tag, suffix, bos);

            // write file
            FileOutputStream fos = new FileOutputStream(dest);
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();

            return true;
        }
        catch (Exception e) {
            return false;
        }

    }

}
