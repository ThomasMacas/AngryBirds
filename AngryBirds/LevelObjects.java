import java.util.ArrayList;

public class LevelObjects { 
    private Bird bird;
    private int numBirds;
    private ArrayList<Pig> pigs;
    private ArrayList<Structure> structures;

    // We re-use a bird so every time it dies we just add it back to the sligshot and represent #birds in the top left
    public LevelObjects(Bird bird, int numBirds) {
        this.bird = bird;
        this.pigs = new ArrayList<>();
        this.structures = new ArrayList<>();
        this.numBirds = numBirds;
    }

    public Bird getBird() {
        return bird;
    }

    public int getNumBirds() {
        return this.numBirds;
    }

    public void addPigs(Pig pig) {
        this.pigs.add(pig);
    }

    public ArrayList<Pig> getPigs() {
        ArrayList<Pig> pigsList = new ArrayList<>(this.pigs);
        return pigsList;
    }

    public void addStructures(Structure structure) {
        this.structures.add(structure);
    }

    public ArrayList<Structure> getStructures() {
        ArrayList<Structure> structuresList = new ArrayList<>(this.structures);
        return structuresList;
    }
    
}
