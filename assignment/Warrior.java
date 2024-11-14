public class Warrior extends Hero{
    private int defencePoint;

    public Warrior(String heroID, String heroName) {
        super(heroID, heroName);
        this.defencePoint = 500;
        this.setHp(500);
        this.setDamage(0);
    }

    public int getDefencePoint() {
        return defencePoint;
    }

    public void setDefencePoint(int defencePoint) {
        this.defencePoint = defencePoint;
    }

    @Override
    public void callSkill() {
        if (defencePoint<0) defencePoint = 0;
        this.setDamage(defencePoint/2);
        defencePoint -= 100;
    }

    @Override
    public void showHeroStatus() {
        System.out.println(getHeroID() + 
                ", "+getHeroName() + ", Hp: "+getHp() + 
                ", Damage: "+getDamage() + ", Defence Point: "+defencePoint);
    }    
}
