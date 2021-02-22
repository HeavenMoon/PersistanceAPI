package fr.heavenmoon.persistanceapi.customs.address;

import java.util.Map;
import java.util.UUID;

public class CustomAddress
{
    // Define attributs
    private UUID id;
    private String address;
    private Map<String, Integer> accounts;

    /**
     * Constructor
     *
     * @param id       The id off address
     * @param address  The address
     * @param accounts The number of connection
     */
    public CustomAddress(UUID id, String address, Map<String, Integer> accounts)
    {
        this.id = id;
        this.address = address;
        this.accounts = accounts;
    }

    // Getters
    public UUID getId() { return id; }
    public String getAddress() { return address; }
    public Map<String, Integer> getAccounts() { return accounts; }

    // Setters
    public void setId(UUID id) { this.id = id; }
    public void setAddress(String address) { this.address = address; }
    public void setAccounts(Map<String, Integer> accounts) { this.accounts = accounts; }
    public void addAccounts(String account)
    {
        Map<String, Integer> accounts = getAccounts();
        if (accounts.containsKey(account))
        {
            int i = accounts.get(account);
            accounts.put(account, i+1);
            setAccounts(accounts);
        }
        else
        {
            accounts.put(account, 1);
            setAccounts(accounts);
        }
    }

}
