package fr.heavenmoon.persistanceapi.config;

public class DatabaseConfig
{
	private String base;
	
	private String host;
	
	private int port;
	
	private String addressUser;
	private String addressPassword;
	private String addressDatabase;
	
	private String logUser;
	private String logPassword;
	private String logDatabase;
	
	private String playerUser;
	private String playerPassword;
	private String playerDatabase;
	
	private String sanctionUser;
	private String sanctionPassword;
	private String sanctionDatabase;
	
	private String serverUser;
	private String serverPassword;
	private String serverDatabase;
	
	private String factionUser;
	private String factionPassword;
	private String factionDatabase;
	
	public DatabaseConfig(String base, String host, int port, String addressUser, String addressPassword, String addressDatabase,
			String logUser, String logPassword, String logDatabase, String playerUser, String playerPassword, String playerDatabase,
			String sanctionUser, String sanctionPassword, String sanctionDatabase, String serverUser, String serverPassword,
			String serverDatabase, String factionUser, String factionPassword, String factionDatabase)
	{
		this.base = base;
		this.host = host;
		this.port = port;
		this.addressUser = addressUser;
		this.addressPassword = addressPassword;
		this.addressDatabase = addressDatabase;
		this.logUser = logUser;
		this.logPassword = logPassword;
		this.logDatabase = logDatabase;
		this.playerUser = playerUser;
		this.playerPassword = playerPassword;
		this.playerDatabase = playerDatabase;
		this.sanctionUser = sanctionUser;
		this.sanctionPassword = sanctionPassword;
		this.sanctionDatabase = sanctionDatabase;
		this.serverUser = serverUser;
		this.serverPassword = serverPassword;
		this.serverDatabase = serverDatabase;
		this.factionUser = factionUser;
		this.factionPassword = factionPassword;
		this.factionDatabase = factionDatabase;
	}
	
	public String getBase()
	{
		return this.base;
	}
	
	public String getHost()
	{
		return this.host;
	}
	
	public int getPort()
	{
		return this.port;
	}
	public String getAddressUser() { return addressUser; }
	public String getAddressPassword() { return addressPassword; }
	public String getAddressDatabase() { return addressDatabase; }
	public String getLogUser() { return logUser; }
	public String getLogPassword() { return logPassword; }
	public String getLogDatabase() { return logDatabase; }
	public String getPlayerUser() { return playerUser; }
	public String getPlayerPassword() { return playerPassword; }
	public String getPlayerDatabase() { return playerDatabase; }
	public String getSanctionUser() { return sanctionUser; }
	public String getSanctionPassword() { return sanctionPassword; }
	public String getSanctionDatabase() { return sanctionDatabase; }
	public String getServerUser() { return serverUser; }
	public String getServerPassword() { return serverPassword; }
	public String getServerDatabase() { return serverDatabase; }
	public String getFactionUser() { return factionUser; }
	public String getFactionPassword() { return factionPassword; }
	public String getFactionDatabase() { return factionDatabase; }
	
	public boolean isValid()
	{
		return (this.base != null && !this.base.isEmpty() && this.host != null &&
				!this.host.isEmpty() && this.addressUser != null &&
				!this.addressUser.isEmpty() && this.addressPassword != null && !this.addressPassword.isEmpty() &&
				this.addressDatabase != null && !this.addressDatabase.isEmpty() && this.logUser != null &&
				!this.logUser.isEmpty() && this.logPassword != null && !this.logPassword.isEmpty() &&
				this.logDatabase != null && !this.logDatabase.isEmpty() && this.playerUser != null &&
				!this.playerUser.isEmpty() && this.playerPassword != null && !this.playerPassword.isEmpty() &&
				this.playerDatabase != null && !this.playerDatabase.isEmpty() && this.sanctionUser != null &&
				!this.sanctionUser.isEmpty() && this.sanctionPassword != null && !this.sanctionPassword.isEmpty() &&
				this.sanctionDatabase != null && !this.sanctionDatabase.isEmpty() && this.serverUser != null &&
				!this.serverUser.isEmpty() && this.serverPassword != null && !this.serverPassword.isEmpty() &&
				this.serverDatabase != null && !this.serverDatabase.isEmpty());
	}
}