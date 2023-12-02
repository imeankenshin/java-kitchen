package models.foods;

import models.Furnace;
import models.Ingredient;
import models.Burnable;

public class Fish extends Ingredient implements Burnable {
    public Fish() {
        super("Fish", 2);
    }

    @Override
    public Ingredient burnable(Furnace furnace) {
        return new RoastedFish();
    }
}
