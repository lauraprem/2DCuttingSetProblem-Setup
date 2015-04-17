package com.polytech4A.CSPS.core.model;

/**
 * @author Alexandre
 *         16/04/2015
 */
public class Position extends Vector {
    /**
     * TRUE si l'image est tournée
     */
    private Boolean rotated = Boolean.FALSE;

    public Position(Long x, Long y) {
        super(x, y);
    }

    public Position(Double w, Double h) {
        super(w, h);
    }

    public Position(Long x, Long y, Boolean rotated) {
        super(x, y);
        this.rotated = rotated;
    }

    public Position(Double w, Double h, Boolean rotated) {
        super(w, h);
        this.rotated = rotated;
    }

    public Boolean isRotated() {
        return rotated;
    }

    public void setRotated(Boolean rotated) {
        this.rotated = rotated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Position position = (Position) o;

        return !(rotated != null ? !rotated.equals(position.rotated) : position.rotated != null);

    }
}
