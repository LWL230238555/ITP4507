public class MementoWarlock extends MementoCallSkill {
    private int mp;
    private Warlock warlock;

    public MementoWarlock(Warlock warlock) {
        super(warlock);
        this.warlock = warlock;
        mp = warlock.getMp();
    }

    public void restore() {
        super.restore();
        warlock.setMp(mp);
    }
}
