package com.sky.jSimple.cache;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

import java.util.ArrayList;
import java.util.List;


public class MemcachedCacheManager implements ICacheManager {

    private MemCachedClient memcachedClient;

    public MemCachedClient getMemcachedClient() {
        if(memcachedClient==null)
        {
// server list and weights
            String[] servers =getServers().split(",");

            String[] sWeights= getWeights().split(",");

            Integer[] weights =new Integer[sWeights.length];
            for(int i=0;i<sWeights.length;i++)
            {
                weights[i]=Integer.parseInt(sWeights[i]);
            }


            // grab an instance of our connection pool
            SockIOPool pool = SockIOPool.getInstance();

            // set the servers and the weights
            pool.setServers( servers );
            pool.setWeights( weights );

            // set some basic pool settings
            // 5 initial, 5 min, and 250 max conns
            // and set the max idle time for a conn
            // to 6 hours
            pool.setInitConn( 5 );
            pool.setMinConn( 5 );
            pool.setMaxConn( 250 );
            pool.setMaxIdle( 1000 * 60 * 60 * 6 );

            // set the sleep for the maint thread
            // it will wake up every x seconds and
            // maintain the pool size
            pool.setMaintSleep( 30 );

            // set some TCP settings
            // disable nagle
            // set the read timeout to 3 secs
            // and don't set a connect timeout
            pool.setNagle( false );
            pool.setSocketTO( 3000 );
            pool.setSocketConnectTO( 0 );

            // initialize the connection pool
            pool.initialize();


            memcachedClient=new MemCachedClient();

        }
        return memcachedClient;
    }

    public void setMemcachedClient(MemCachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public synchronized void insert(String key, Object value) {
        insert(key, value, "");
    }

    public Object get(String key) {
        return getMemcachedClient().get(key);
    }

    public synchronized void remove(String key) {
        getMemcachedClient().delete(key);

    }

    public synchronized void removeScope(String scope) {

        Object o = getMemcachedClient().get(scope);

        if (o != null) {
            List<String> keys = (List<String>) o;

            for (String key : keys) {
                getMemcachedClient().delete(key);
            }
        }
        getMemcachedClient().delete(scope);

    }

    public synchronized void insert(String key, Object value, String scope) {
        getMemcachedClient().set(key, value);

        Object keys = getMemcachedClient().get(scope);
        if (keys == null) {
            keys = new ArrayList<String>();
        }

        ((List<String>) keys).add(key);

        getMemcachedClient().set(scope, keys);
    }


    private String servers;

    private String weights;

    public String getWeights() {
        return weights;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }


}
