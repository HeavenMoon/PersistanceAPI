package fr.heavenmoon.persistanceapi.customs.factions;

public class CustomHome {

    private String name;
    private String worldName;
    private double X;
    private double Y;
    private double Z;
    private float Yaw;
    private float Pitch;

    public CustomHome(String name, String worldName, double X, double Y, Double Z, float Yaw, float Pitch) {
        this.name = name;
        this.worldName = worldName;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.Yaw = Yaw;
        this.Pitch = Pitch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public double getX() {
        return X;
    }

    public void setX(long x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(long y) {
        Y = y;
    }

    public double getZ() {
        return Z;
    }

    public void setZ(long z) {
        Z = z;
    }

    public float getYaw() {
        return Yaw;
    }

    public void setYaw(float yaw) {
        Yaw = yaw;
    }

    public float getPitch() {
        return Pitch;
    }

    public void setPitch(float pitch) {
        Pitch = pitch;
    }
    
}
