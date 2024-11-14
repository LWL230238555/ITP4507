public class MementoWarrior extends MementoCallSkill {
    private int defencePoint;
    private Warrior warrior;

    public MementoWarrior(Warrior warrior) {
        super(warrior);
        this.warrior = warrior;
        defencePoint = warrior.getDefencePoint();
    }

    public void restore() {
        super.restore();
        warrior.setDefencePoint(defencePoint);
    }
}