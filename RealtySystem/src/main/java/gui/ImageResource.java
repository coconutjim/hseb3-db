package gui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

/***
 * Represents an image res
 */
public class ImageResource {

    private ImageResource() {

    }

    private static String IMAGE_PATH = "C:\\Users\\Lev\\Desktop\\images\\";

    public static BufferedImage getImage(String url) {
        BufferedImage image = null;
        if (url != null) {
            String path = ImageResource.IMAGE_PATH + url;
            try {
                image = ImageIO.read(new File(path));
            }
            catch (IOException e) {
                // nothing
            }
        }
        return image;
    }

    public static String generateId() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            builder.append(c);
        }
        return builder.toString();
    }


    public static void copyFile(File source, String id) {
        File dest = new File(IMAGE_PATH + id);
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
        catch (IOException e) {
            // nothing to do
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            }
            catch (IOException ee) {
                // nothing to do
            }
        }
    }

    public static void deleteFile(String url) {
        File file = new File(IMAGE_PATH + url);
        file.delete();
    }
}
