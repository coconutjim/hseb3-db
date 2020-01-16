package gui.client.main.bookmarks;

import domain.bookmark.Bookmark;
import domain.realty_obj.RealtyObject;
import gui.ImageResource;

import java.awt.image.BufferedImage;

/***
 * For list
 */
public class BMWrapper {

    private Bookmark bookmark;
    private BufferedImage image;

    public BMWrapper(Bookmark bookmark) {
        if (bookmark == null) {
            throw new IllegalArgumentException("BMWrapper: bookmark is null!");
        }
        this.bookmark = bookmark;
        RealtyObject realtyObject = bookmark.getRealtyObject();
        if (realtyObject != null) {
            image = ImageResource.getImage(realtyObject.getImageUrl());
        }
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    public BufferedImage getImage() {
        return image;
    }
}
