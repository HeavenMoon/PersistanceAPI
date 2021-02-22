package fr.heavenmoon.persistanceapi.customs.player;

import java.util.List;
import java.util.UUID;

public class CustomSanction
{
    // Define attributs
    private UUID sanctionId;
    private UUID playerUuid;
    private SanctionType type;
    private String reason;
    private List<String> evidences;
    private UUID punisherUuid;
    private long expirationTime;
    private boolean isRevoqued;
    private long creationTime;

    /**
     * Constructor
     *
     * @param sanctionId     The id of the sanction
     * @param playerUuid     The player uuid
     * @param type           The type of sanction
     * @param reason         The reason of the sanction
     * @param evidences      The proofs of the sanction
     * @param punisherUuid   The punisher
     * @param expirationTime The timestamp of expiration
     * @param isRevoqued     The revocation status
     * @param creationTime   The timestamp of creation
     */
    public CustomSanction(UUID sanctionId, UUID playerUuid, SanctionType type, String reason, List<String> evidences, UUID punisherUuid, long expirationTime, boolean isRevoqued, long creationTime)
    {
        this.sanctionId = sanctionId;
        this.playerUuid = playerUuid;
        this.type = type;
        this.reason = reason;
        this.evidences = evidences;
        this.punisherUuid = punisherUuid;
        this.expirationTime = expirationTime;
        this.isRevoqued = isRevoqued;
        this.creationTime = creationTime;
    }

    /**
     * Constructor without id
     *
     * @param playerUuid     The player uuid
     * @param type           The type of sanction
     * @param reason         The reason of the sanction
     * @param evidences      The proofs of the sanction
     * @param punisherUuid   The punisher
     * @param expirationTime The timestamp of expiration
     * @param isRevoqued     The revocation status
     * @param creationTime   The timestamp of creation
     */
    public CustomSanction(UUID playerUuid, SanctionType type, String reason, List<String> evidences, UUID punisherUuid, long expirationTime, boolean isRevoqued, long creationTime)
    {
        this.playerUuid = playerUuid;
        this.type = type;
        this.reason = reason;
        this.evidences = evidences;
        this.punisherUuid = punisherUuid;
        this.expirationTime = expirationTime;
        this.isRevoqued = isRevoqued;
        this.creationTime = creationTime;
    }

    public boolean isValid()
    {
        Long time = expirationTime-System.currentTimeMillis();
        if (time >= 0) return true;
        else return false;
    }

    // Getters
    public UUID getSanctionId() { return sanctionId; }
    public UUID getPlayerUuid() { return playerUuid; }
    public SanctionType getType() { return type; }
    public String getReason() { return reason; }
    public List<String> getEvidences() { return evidences; }
    public UUID getPunisherUuid() { return punisherUuid; }
    public long getExpirationTime() { return expirationTime; }
    public boolean isRevoqued() { return isRevoqued; }
    public long getCreationTime() { return creationTime; }

    // Setters
    public void setSanctionId(UUID sanctionId) { this.sanctionId = sanctionId; }
    public void setPlayerUuid(UUID playerUuid) { this.playerUuid = playerUuid; }
    public void setType(SanctionType type) { this.type = type; }
    public void setReason(String reason) { this.reason = reason; }
    public void setEvidences(List<String> evidences) { this.evidences = evidences; }
    public void setPunisherUuid(UUID punisherUuid) { this.punisherUuid = punisherUuid; }
    public void setExpirationTime(long expirationTime) { this.expirationTime = expirationTime; }
    public void setRevoqued(boolean revoqued) { isRevoqued = revoqued; }
    public void setCreationTime(long creationTime) { this.creationTime = creationTime; }
}


