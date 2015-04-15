package com.polytech4A.CSPS.core.model;

import java.util.ArrayList;

import static com.polytech4A.CSPS.core.util.Util.escToString;

/**
 * Image à positionner dans un pattern
 *
 * @author Laura
 */
public class Image {
    /**
     * nombre de fois que l'image est présente dans un pattern
     */
    private Long amount = 0L;

    /**
     * Permet de savoir si l'image est tourn�e
     */
    private boolean rotated = false;

    /**
     * Liste des positions pour chaque fois que l'image est pr�sente dans un
     * pattern (amount)
     */
    private ArrayList<Vector> positions = new ArrayList<>();

    /**
     * Taille de l'image
     */
    private Vector size;

    /**
     * nombre de fois que l'on veut imprimer l'image au total
     */
    private Long goal = -1L;

    public Image(Long _amount, boolean _rotated, ArrayList<Vector> _positions,
                 Vector _size, Long _goal) {
        amount = _amount == null ? 0L : _amount;
        rotated = _rotated;
        positions = _positions == null ? new ArrayList<>() : _positions;
        size = _size;
        goal = _goal;
    }

    public Image(Vector size, Long goal) {
        this(null, false, null, size, goal);
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public boolean isRotated() {
        return rotated;
    }

    public void setRotated(boolean rotated) {
        this.rotated = rotated;
    }

    public ArrayList<Vector> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Vector> positions) {
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

    public Long getArea() {
        return size.getX() * size.getY();
    }

    @Override
    public String toString() {
        StringBuilder stringPosition = new StringBuilder("Liste des positions pour chaque fois que l\'image est présente : \n");
        String detail = "IMAGE\n" +
                "Nombre d\'image dans le pattern : " + escToString(amount) + "\n"
                + "L\'image est tournée : " + rotated + "\n";

        if (positions == null) {
            stringPosition.append("<null>\n");
        } else if(positions.size() == 0) {
            stringPosition.append("<empty>\n");
        } else {
            for (Vector vec : positions) {
                stringPosition.append(escToString(vec));
                stringPosition.append('\n');
            }
        }

        detail = detail + stringPosition
                + "Taille de l\'image : " + size.toString() + "\n"
                + "Nombre d\'image à imprimer au total : " + escToString(goal) + "\n";

        return detail;

    }
}
