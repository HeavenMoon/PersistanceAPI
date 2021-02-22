package fr.heavenmoon.persistanceapi.managers.redis;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PubSubMessage
{
	
	private String title;
	private List<String> arguments;
	
	public PubSubMessage(String title, List<String> arguments)
	{
		this.title = title;
		this.arguments = arguments;
	}
	
	public PubSubMessage(String title)
	{
		this.title = title;
		this.arguments = new ArrayList<>();
	}
	
	public static PubSubMessage fromJson(String json)
	{
		return new Gson().fromJson(json, PubSubMessage.class);
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public List<String> getArguments()
	{
		return arguments;
	}
	
	public void setArguments(List<String> arguments)
	{
		this.arguments = arguments;
	}
	
	public PubSubMessage addArgument(String argument)
	{
		List<String> arguments = getArguments();
		arguments.add(argument);
		setArguments(arguments);
		return this;
	}
	
	public String toJson()
	{
		return new Gson().toJson(this);
	}
	
}
