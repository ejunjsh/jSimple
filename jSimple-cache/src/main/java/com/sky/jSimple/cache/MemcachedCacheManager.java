package com.sky.jSimple.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class MemcachedCacheManager implements ICacheManager {
	
	private MemcachedClient memcachedClient;
	
	public synchronized void insert(String key, Object value) {
		insert(key, value, "");
	}

	public Object get(String key) {
		try {
			return  getMemcachedClient().get(key);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public synchronized void remove(String key) {
		try {
			getMemcachedClient().delete(key);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void removeScope(String scope) {
		try {
			List<String> keys=getMemcachedClient().get(scope);
			
			for (String key : keys) {
				getMemcachedClient().delete(key);
			}
			getMemcachedClient().delete(scope);
			
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void insert(String key, Object value, String scope) {
		try {
			getMemcachedClient().set(key, 0, value);
			
			List<String> keys=getMemcachedClient().get(scope);
			if(keys==null)
			{
				keys=new ArrayList<String>();
			}
			
			keys.add(key);
			
			getMemcachedClient().set(scope,  0, keys);
			
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String servers;
	
	
    private int connectionPoolSize;
    

	public MemcachedClient getMemcachedClient() {
		if(memcachedClient==null)
		{
			 MemcachedClientBuilder builder = new XMemcachedClientBuilder(  
		                AddrUtil.getAddresses(getServers()));  
		               builder.setFailureMode(true);    
		               builder.setCommandFactory(new BinaryCommandFactory());  
		                builder.setConnectionPoolSize(getConnectionPoolSize());    
		                try {
							setMemcachedClient(builder.build());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
		}
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public int getConnectionPoolSize() {
		return connectionPoolSize;
	}

	public void setConnectionPoolSize(int connectionPoolSize) {
		this.connectionPoolSize = connectionPoolSize;
	}
}
