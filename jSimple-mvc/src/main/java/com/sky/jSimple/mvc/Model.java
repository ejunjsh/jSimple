package com.sky.jSimple.mvc;

import com.sky.jSimple.utils.CastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shaojunjie on 2015/1/13.
 */
public class Model {
    private Map<String, Object> map;

    public Model() {
        this.map = new HashMap<String, Object>();
    }

    public Model(Map map) {
        this.map = map;
    }

    public <T> T get(String key, Class<T> cls) {
        return (T) CastUtil.castPirmitiveObject(cls, map.get(key) == null ? null : map.get(key).toString());
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
