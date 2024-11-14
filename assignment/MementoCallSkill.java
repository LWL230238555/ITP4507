public class MementoCallSkill {
    private Hero hero;
    private String name;
    private int hp;
    private int damage;

    public MementoCallSkill(Hero hero) {
        this.hero = hero;
        name = hero.getHeroName();
        hp = hero.getHp();
        damage = hero.getDamage();
    }

    public Hero getMyHero() {
        return hero;
    }

    public void restore() {
        hero.setHeroName(name);
        hero.setHp(hp);
        hero.setDamage(damage);
    }
}
