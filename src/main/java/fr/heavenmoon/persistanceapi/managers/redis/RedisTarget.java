package fr.heavenmoon.persistanceapi.managers.redis;

public class RedisTarget
{
	
	private String target;
	
	public RedisTarget(RedisTargetType type)
	{
		this.target = type.getName();
	}
	
	public RedisTarget(RedisTargetType type, String name)
	{
		
		this.target = type.getName() + ":" + name;
	}
	
	public String getTarget()
	{
		return target;
	}
	
	public void setTarget(String target)
	{
		this.target = target;
	}
	
	public enum RedisTargetType
	{
		SERVER("Server"),
		PROXY("Proxy");
		
		private String name;
		
		RedisTargetType(String name)
		{
			this.name = name;
		}
		
		public String getName()
		{
			return name;
		}
	}
	
}
