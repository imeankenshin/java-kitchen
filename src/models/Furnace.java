package models;

public class Furnace {
    private boolean isLit;
    private int progress;
    private final int maxProgress = 10;
    private Ingredient ingredient;

    public Furnace() {
        this.isLit = false;
        this.progress = 0;
        this.ingredient = null;
    }

    public void setIngredient(Ingredient ingredient) {
        if (this.getStatus() != FurnanceStatus.EMPTY) {
            throw new IllegalStateException("Furnace is not empty");
        }
        if (ingredient == null) {
            throw new IllegalArgumentException("Ingredient cannot be null");
        }
        this.ingredient = ingredient;
    }

    public void increaseProgress(int progress) {
        if (this.getStatus() != FurnanceStatus.BURNING) {
            throw new IllegalStateException("Furnace is not burning");
        }
        if (progress < 0) {
            throw new IllegalArgumentException("Progress cannot be negative");
        }
        if (this.progress + progress > this.maxProgress) {
            throw new IllegalArgumentException("Progress cannot exceed max progress");
        }
        this.progress += progress;
    }

    public boolean isLit() {
        return isLit;
    }

    public int getProgress() {
        return progress;
    }

    public FurnanceStatus getStatus() {
        if (this.ingredient == null) {
            return FurnanceStatus.EMPTY;
        }
        if (this.progress == 0) {
            return FurnanceStatus.FULL;
        }
        if (this.progress < this.maxProgress) {
            return FurnanceStatus.BURNING;
        }
        return FurnanceStatus.BURNT;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    private void start() {
        if (this.getStatus() != FurnanceStatus.FULL) {
            throw new IllegalStateException("Furnace is not full");
        }
        if (!(this.ingredient instanceof Roastable roastable)) {
            throw new IllegalStateException("Ingredient is not roastable");
        }
        this.isLit = true;
        BurnTask burnTask = new BurnTask(this);
        burnTask.start();
        this.ingredient = roastable.roast(this);
    }

    public Ingredient pickIngredient() {
        if (this.ingredient == null) {
            throw new IllegalStateException("Furnace is empty");
        }
        if (this.getStatus() != FurnanceStatus.BURNT) {
            throw new IllegalStateException("Furnace is not done");
        }
        Ingredient result = this.ingredient.clone();
        this.ingredient = null;
        return result;
    }
}