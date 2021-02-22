package fr.heavenmoon.persistanceapi.managers.redis;

import fr.heavenmoon.persistanceapi.PersistanceManager;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedisPublisher
{
	
	private PersistanceManager persistanceManager;
	private String title;
	private List<String> arguments;
	
	public RedisPublisher(PersistanceManager persistanceManager, String title)
	{
		this.persistanceManager = persistanceManager;
		this.title = title;
		this.arguments = new ArrayList<>();
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
	
	public RedisPublisher setArguments(String... arguments)
	{
		this.arguments = Arrays.asList(arguments);
		return this;
	}
	
	public RedisPublisher addArgument(String argument)
	{
		List<String> arguments = getArguments();
		arguments.add(argument);
		setArguments(arguments);
		return this;
	}
	
	public void publish(RedisTarget target)
	{
		PubSubMessage pubSubMessage = new PubSubMessage(title);
		pubSubMessage.setArguments(this.arguments);
		RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
		RTopic<String> rTopic = redissonClient.getTopic(target.getTarget());
		rTopic.publish(pubSubMessage.toJson());
	}
}
