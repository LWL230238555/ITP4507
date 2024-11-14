public class Warlock extends Hero{
    private int mp;
    
    public Warlock(String heroID, String heroName) {
        super(heroID, heroName);
        this.setHp(100);
        this.mp = 500;
        this.setDamage(200);
    }
    
    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    @Override
    public void callSkill() {
        if (mp > 0) mp -= 100;
        else mp = 0;
        setDamage(100);
    }

    @Override
    public void showHeroStatus() {
        System.out.println(getHeroID() + 
                ", "+getHeroName() + ", Warlock, Hp: "+getHp() + 
                ", Damage: "+getDamage() + ", Mp: "+mp);
    }    
}
