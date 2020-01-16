package gui;

import domain.realty_obj.RealtyObject;

import java.awt.image.BufferedImage;

/***
 * For list
 */
public class ROWrapper {

    private BufferedImage image;
    private RealtyObject object;

    public ROWrapper(RealtyObject object) {
        if (object == null) {
            throw new IllegalArgumentException("ROWrapper: object is null!");
        }
        this.object = object;
        image = ImageResource.getImage(object.getImageUrl());
    }

    public BufferedImage getImage() {
        return image;
    }

    public RealtyObject getObject() {
        return object;
    }
}
