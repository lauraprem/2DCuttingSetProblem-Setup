package com.polytech4A.CSPS.core.model;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Image à positionner dans un pattern
 *
 * @author Laura
 */
public class Image implements Comparable<Image> {

    /**
     * nombre de fois que l'image est présente dans un pattern
     */
    private Long id = 0L;

    /**
     * nombre de fois que l'image est présente dans un pattern
     */
    private Long amount = 0L;

    /**
     * Liste des positions pour chaque fois que l'image est pr�sente dans un
     * pattern (amount)
     */
    private ArrayList<Position> positions = new ArrayList<>();

    /**
     * Taille de l'image
     */
    private Vector size;
    public final static Comparator<Image> ImageNameComparator = new Comparator<Image>() {
        public int compare(Image img1, Image img2) {

            Long ImgArea1 = img1.getArea();
            Long ImgArea2 = img2.getArea();

            // ascending
            // order
            // return
            // ImgArea1.compareTo(ImgArea2);

            // descending
            // order
            return ImgArea2.compareTo(ImgArea1);
        }

    };
    /**
     * nombre de fois que l'on veut imprimer l'image au total
     */
    private Long goal = -1L;

    public Image(Long _id, Long _amount, ArrayList<Position> _positions, Vector _size, Long _goal) {
        if (_amount != null)
            amount = _amount;
        positions = _positions == null ? new ArrayList<>() : _positions;
        size = _size;
        goal = _goal;
        id = (_id == null ? 0L : _id);
    }

    public Image(Vector size, Long goal) {
        this(null, null, null, size, goal);
    }

    public Image(Long id, Vector size, Long goal) {
        this(id, null, null, size, goal);
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void incrementAmoutByOne() {
        this.amount++;
    }

    public void decrementAmoutByOne() {
        this.amount--;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Position> positions) {
        this.positions = positions;
    }

    public Vector getSize() {
        return size;
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    public Long getGoal() {
        return goal;
    }

    public void setGoal(Long goal) {
        this.goal = goal;
    }

    public Long getId() {
        return id;
    }

	/*
     * @Override public String toString() { StringBuilder stringPosition = new
	 * StringBuilder("positions : \n"); String detail = "IMAGE " + id + "\n" +
	 * "amount : " + escToString(amount) + "\n"; if (positions == null) {
	 * stringPosition.append("<null>\n"); } else if (positions.size() == 0) {
	 * stringPosition.append("<empty>\n"); } else { for (Vector vec : positions)
	 * { stringPosition.append(escToString(vec)); stringPosition.append('\n'); }
	 * }
	 * 
	 * detail = detail + stringPosition + "size : " + size.toString() + "\n" +
	 * "goal : " + escToString(goal) + "\n";
	 * 
	 * return detail; }
	 */

    public Long getArea() {
        return size.getX() * size.getY();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Image{");
        sb.append("id=").append(id);
        sb.append(", amount=").append(amount);
        if (positions != null) {
            if (positions.size() != 0)
                sb.append(", positions=").append(positions);
            else
                sb.append(", positions=").append("<empty>");
        }
        sb.append(", size=").append(size);
        sb.append(", goal=").append(goal);
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Image image = (Image) o;

        if (!id.equals(image.id))
            return false;
        if (!size.equals(image.size))
            return false;
        return goal.equals(image.goal);

    }

    @Override
    public int compareTo(Image o) {
        if (this.getArea() < o.getArea()) {
            return 0;
        }
        return 1;
    }

    @Override
    public Object clone() {
        Image cloned = new Image((Vector) size.clone(), goal);
        cloned.id = id;
        cloned.setAmount(amount);
        cloned.positions = new ArrayList<Position>();
        for (Position position : positions) {
            cloned.positions.add((Position) position.clone());
        }
        return cloned;
    }
}