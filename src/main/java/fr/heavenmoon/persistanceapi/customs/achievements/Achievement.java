package fr.heavenmoon.persistanceapi.customs.achievements;

public class Achievement
{

    // Define attributs
    private int achievementId;
    private String achievementName;
    private String achievementDescription;
    private int categoryId;

    /**
     * Constructor
     * @param achievementId          The id of the achievment
     * @param achievementName        The name of the achievement
     * @param achievementDescription The description of the achievement
     * @param categoryId             The id of the category
     */
    public Achievement(int achievementId, String achievementName, String achievementDescription, int categoryId)
    {
        this.achievementId = achievementId;
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.categoryId = categoryId;
    }

    // Getters
    public int getAchievementId() { return achievementId; }
    public String getAchievementName() { return achievementName; }
    public String getAchievementDescription() { return achievementDescription; }
    public int getCategoryId() { return categoryId; }

    // Setters
    public void setAchievementId(int achievementId) { this.achievementId = achievementId; }
    public void setAchievementName(String achievementName) { this.achievementName = achievementName; }
    public void setAchievementDescription(String achievementDescription) { this.achievementDescription = achievementDescription; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
}
