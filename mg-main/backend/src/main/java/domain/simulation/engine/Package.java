package domain.simulation.engine;

import jsweet.lang.Erased;

@Erased
public class Package {

    public enum Color {
        BLUE,
        RED
    }

    private final int id;
    private final Color color;
    private String lastStage;

    public Package(int id, Color color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public String getLastStage() {
        return lastStage;
    }

    public void setLastStage(String lastStage) {
        this.lastStage = lastStage;
    }

    @Override
    public String toString() {
        return "Package{id=" + id + ", color=" + color + ", lastStage=" + lastStage + "}";
    }
}
