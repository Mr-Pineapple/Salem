package club.mcmodding.salem.spells;

public enum SpellType {

    FIRE(0xe33617),
    EARTH(0x764626),
    WATER(0x009fff),
    AIR(0xeef8ff),
    MISCELLANEOUS(0xc3c3c3);

    private final int color;

    SpellType(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

}
