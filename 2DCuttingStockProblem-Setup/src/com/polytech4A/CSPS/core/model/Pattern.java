package com.polytech4A.CSPS.core.model;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Planche contenant des images
 *
 * @author Laura
 */
public class Pattern implements Comparable<Pattern>, Cloneable {

    /**
     * taille du pattern
     */
    protected Vector size;
    public final static Comparator<Pattern> PatternNameComparator = new Comparator<Pattern>() {
        public int compare(Pattern p1, Pattern p2) {

            Long PattArea1 = p1.getArea();
            Long PattArea2 = p2.getArea();

            // ascending order
            return PattArea1.compareTo(PattArea2);

            // descending order
            // return PattArea2.compareTo(PattArea1);
        }

    };
    /**
     * Nombre de fois qu'il faut imprimer le pattern
     */
    protected Long amount = 0L;
    /**
     * Les images présente dans le pattern
     */
    protected ArrayList<Image> listImg;

    public Pattern(Vector size) {
        super();
        this.size = size;
    }

    public Pattern(Vector size, Long amount) {
        super();
        this.size = size;
        this.amount = amount;
    }

    public Pattern(Vector size, ArrayList<Image> listImg) {
        this.size = size;
        this.listImg = listImg;
    }

    public Vector getSize() {
        return size;
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public ArrayList<Image> getListImg() {
        return listImg;
    }

    public void setListImg(ArrayList<Image> listImg) {
        this.listImg = listImg;
    }

    public void addImg(Image img) {
        for (int i = 0; i < listImg.size(); i++) {
            if (listImg.get(i).getId().equals(img.getId())) {
                listImg.get(i).setAmount(listImg.get(i).getAmount() + 1);
            }
        }
    }

    public Long getArea() {
        return size.getX() * size.getY();
    }

	/*
     * @Override public String toString() { StringBuilder listImgString = new
	 * StringBuilder(); String patternString = "PATTERN\n" + "size : " +
	 * escToString(size) + "\n" + "amount : " + escToString(amount) + "\n" +
	 * "listImg : \n";
	 * 
	 * if (listImg != null) { for (Image img : listImg) { if (img != null &&
	 * img.getAmount() != 0) { listImgString.append(escToString(img));
	 * listImgString.append('\n'); } } } else {
	 * listImgString.append(escToString(listImg)); listImgString.append('\n'); }
	 * 
	 * patternString = patternString + listImgString.toString();
	 * 
	 * return patternString; }
	 */

    public Image getImage(Long id) {
        for (Image image : listImg)
            if (id.equals(image.getId()))
                return image;
        return null;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Pattern{");
        sb.append("size=").append(size);
        sb.append(", amount=").append(amount);
        if (listImg != null) {
            if (listImg.size() != 0) {
                sb.append(", listImg=[");
                listImg.forEach(i -> {
                    if (i.getAmount() != 0L)
                        sb.append(i.toString());
                });
                sb.append("]");
            } else
                sb.append(", listImg=").append("<empty>");
        }
        sb.append('}');
        return sb.toString();
    }

    public synchronized void setPattern(Pattern pattern) {
        this.amount = pattern.getAmount();
        this.size = pattern.getSize();
        this.listImg = new ArrayList<Image>();
        for (Image image : pattern.listImg) {
            this.listImg.add((Image) image.clone());
        }
    }

    public Object clone() {
        Pattern cloned = new Pattern((Vector) this.size.clone(), this.amount);
        cloned.listImg = new ArrayList<Image>();
        if (listImg != null) {
            for (int i = 0; i < listImg.size(); i++) {
                cloned.listImg.add((Image) listImg.get(i).clone());
            }
        }
        return cloned;
    }

    @Override
    public int compareTo(Pattern o) {
        if (this.getArea() < o.getArea()) {
            return 0;
        }
        return 1;
    }

	public void deleteImg(Long idImage) {
		for (int i = 0; i < listImg.size(); i++) {
            if (listImg.get(i).getId().equals(idImage)) {
            	if(listImg.get(i).getAmount()>0){
            		listImg.get(i).setAmount(listImg.get(i).getAmount() - 1);
            	}
            }
        }
	}
}
