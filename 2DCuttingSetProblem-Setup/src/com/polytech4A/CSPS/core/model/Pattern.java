package com.polytech4A.CSPS.core.model;

import java.util.ArrayList;

import static com.polytech4A.CSPS.core.util.Util.escToString;

/**
 * Planche contenant des images
 *
 * @author Laura
 */
public class Pattern {

    /**
     * taille du pattern
     */
    private Vector size;

    /**
     * Nombre de fois qu'il faut imprimer le pattern
     */
    private Long amount;

    /**
     * Les images présente dans le pattern
     */
    private ArrayList<Image> listImg;

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

    @Override
    public String toString() {
        StringBuilder listImgString = new StringBuilder();
        String patternString = "PATTERN\n"
                + "Taille du pattern : " + escToString(size) + "\n"
                + "Nombre de fois qu\'il faut imprimer le pattern : " + escToString(amount) + "\n"
                + "Les images présente dans le pattern : \n";

        for (Image img : listImg) {
            listImgString.append(escToString(img));
            listImgString.append('\n');
        }
        patternString = patternString + listImgString.toString();

        return patternString;
    }
}
