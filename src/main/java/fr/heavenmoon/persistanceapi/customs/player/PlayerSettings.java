package fr.heavenmoon.persistanceapi.customs.player;

public class PlayerSettings {

    /**
     * 0: nobody
     * 1: friends
     * 2: everyone
     */

    private int showPlayerInLobby;
    private int acceptPrivateMessage;
    private int acceptFriendsRequest;

    public PlayerSettings(int showPlayerInLobby, int acceptPrivateMessage, int acceptFriendsRequest)
    {
        this.showPlayerInLobby = showPlayerInLobby;
        this.acceptPrivateMessage = acceptPrivateMessage;
        this.acceptFriendsRequest = acceptFriendsRequest;
    }
    public PlayerSettings()
    {
        this.showPlayerInLobby = 2;
        this.acceptPrivateMessage = 2;
        this.acceptFriendsRequest = 2;
    }

    public int getShowPlayerInLobby() {
        return showPlayerInLobby;
    }

    public void setShowPlayerInLobby(int showPlayerInLobby) {
        this.showPlayerInLobby = showPlayerInLobby;
    }

    public int getAcceptPrivateMessage() {
        return acceptPrivateMessage;
    }

    public void setAcceptPrivateMessage(int acceptPrivateMessage) {
        this.acceptPrivateMessage = acceptPrivateMessage;
    }

    public int getAcceptFriendsRequest() {
        return acceptFriendsRequest;
    }

    public void setAcceptFriendsRequest(int acceptFriendsRequest) {
        this.acceptFriendsRequest = acceptFriendsRequest;
    }
}