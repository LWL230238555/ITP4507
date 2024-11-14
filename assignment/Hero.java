public abstract class Hero {
    private String heroID;
    private String heroName;
    private int hp;
    private int damage;

    public Hero(String heroID, String heroName) {
        this.heroID = heroID;
        this.heroName = heroName;
        this.hp = 200;
    }

    public String getHeroID() {
        return heroID;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public abstract void callSkill();
    
    public abstract void showHeroStatus();
    
}
