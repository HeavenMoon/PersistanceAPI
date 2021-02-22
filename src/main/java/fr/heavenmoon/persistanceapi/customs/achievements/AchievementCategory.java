package fr.heavenmoon.persistanceapi.customs.achievements;

public class AchievementCategory
{

    // Defines attributs
    private int categoryId;
    private String categoryName;
    private String categoryDescription;
    private String minecraftItemId;
    private String parentId;

    /**
     * Constructor
     *
     * @param categoryId          The id of the category != 0
     * @param categoryName        The name of the category
     * @param categoryDescription The description of the category
     * @param minecraftItemId     The minecraft time id (0 if none)
     * @param parentId            The id of the parent category (0 if not a subcategory)
     */
    public AchievementCategory(int categoryId, String categoryName, String categoryDescription, String minecraftItemId, String parentId)
    {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.minecraftItemId = minecraftItemId;
        this.parentId = parentId;
    }

    // Getters
    public int getCategoryId() { return categoryId; }

    public String getCategoryName() { return categoryName; }

    public String getCategoryDescription() { return categoryDescription; }

    public String getMinecraftItemId() { return minecraftItemId; }

    public String getParentId() { return parentId; }

    // Setters
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public void setCategoryDescription(String categoryDescription) { this.categoryDescription = categoryDescription; }

    public void setMinecraftItemId(String minecraftItemId) { this.minecraftItemId = minecraftItemId; }

    public void setParentId(String parentId) { this.parentId = parentId; }
}
