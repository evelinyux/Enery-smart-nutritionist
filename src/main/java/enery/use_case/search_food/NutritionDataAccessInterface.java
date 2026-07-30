package enery.use_case.search_food;

import java.util.List;

import enery.entity.meal.FoodItem;

/**
 * Provides nutrition information for food queries.
 *
 * <p>This interface belongs to the use-case layer so interactors depend on an
 * application-owned abstraction rather than a specific external API.</p>
 *
 * <p>Any data source can implement this interface, including API Ninjas,
 * Edamam, a local database, or a fake implementation used in tests.</p>
 */
public interface NutritionDataAccessInterface {

    /**
     * Searches for nutrition information matching a natural-language query.
     *
     * @param query food description, optionally including quantity
     * @return matching food items
     */
    List<FoodItem> search(String query);
}
