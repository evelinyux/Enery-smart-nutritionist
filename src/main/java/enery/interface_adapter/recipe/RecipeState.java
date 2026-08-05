package enery.interface_adapter.recipe;

public class RecipeState {
    private String id = "";
    private String recipeName = "";
    private String description = "";
    private String recipeUrl = "";

    // Pre-formatted strings for display
    private String calories = "";
    private String protein = "";
    private String carbs = "";
    private String fat = "";
    private String sugar = "";
    private String ingredients = ""; // Multi-line String
    private String instructions = "";

    private String recipeError = null;

    // Copy Constructor
    public RecipeState(RecipeState copy) {
        this.id = copy.id;
        this.recipeName = copy.recipeName;
        this.description = copy.description;
        this.recipeUrl = copy.recipeUrl;
        this.calories = copy.calories;
        this.protein = copy.protein;
        this.carbs = copy.carbs;
        this.fat = copy.fat;
        this.sugar = copy.sugar;
        this.ingredients = copy.ingredients;
        this.instructions = copy.instructions;
        this.recipeError = copy.recipeError;
    }

    public RecipeState() {
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRecipeName() { return recipeName; }
    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRecipeUrl() { return recipeUrl; }
    public void setRecipeUrl(String recipeUrl) { this.recipeUrl = recipeUrl; }

    public String getCalories() { return calories; }
    public void setCalories(String calories) { this.calories = calories; }

    public String getProtein() { return protein; }
    public void setProtein(String protein) { this.protein = protein; }

    public String getCarbs() { return carbs; }
    public void setCarbs(String carbs) { this.carbs = carbs; }

    public String getFat() { return fat; }
    public void setFat(String fat) { this.fat = fat; }

    public String getSugar() { return sugar; }
    public void setSugar(String sugar) { this.sugar = sugar; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getRecipeError() { return recipeError; }
    public void setRecipeError(String recipeError) { this.recipeError = recipeError; }
}