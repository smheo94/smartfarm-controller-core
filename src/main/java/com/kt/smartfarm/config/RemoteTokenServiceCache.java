package com.kt.smartfarm.config;

import com.kt.cmmn.util.WebUtil;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RemoteTokenServiceCache {
    private static Map<Integer, RemoteTokenServiceCache> cacheObject = new HashMap<>();

    private  static int CACHETIMEMILLI = 30000;
    private int tokenServicesHash= 0;
    private String remoteAddr ="";
    private Long accessTime = 0L;
    public OAuth2Authentication auth;
    public RemoteTokenServiceCache(HttpServletRequest request, RemoteTokenServices tokenServices, OAuth2Authentication auth) {
        this.remoteAddr = WebUtil.remoteAddress(request);
        if(remoteAddr == null ) remoteAddr = "";
        this.tokenServicesHash = tokenServices.hashCode();
        this.auth = auth;
        this.accessTime = System.currentTimeMillis();
    }
    @Override
    public int hashCode() {
        return this.remoteAddr.hashCode() % tokenServicesHash;
    }
    public static void setCacheTime(int cacheTimeMilli) {
        CACHETIMEMILLI = cacheTimeMilli;
    }
    public static int hashCode(int remoteAddressHash, int tokenServicesHash) {
        return remoteAddressHash % tokenServicesHash;
    }
    public static RemoteTokenServiceCache getHash(HttpServletRequest request, RemoteTokenServices tokenServices) {
        String remoteAddr = WebUtil.remoteAddress(request);
        int tokenServicesHash = tokenServices.hashCode();
        RemoteTokenServiceCache remoteTokenServiceCache = cacheObject.get(hashCode(remoteAddr == null ? "".hashCode() : remoteAddr.hashCode(), tokenServicesHash));
        Long now = System.currentTimeMillis();
        if( remoteTokenServiceCache == null || remoteTokenServiceCache.accessTime + CACHETIMEMILLI < now ) {
            return null;
        }
        remoteTokenServiceCache.accessTime = now;
        return remoteTokenServiceCache;
    }
    public static void putHash(HttpServletRequest request, RemoteTokenServices tokenServices, OAuth2Authentication auth) {
        if( auth == null || !auth.isAuthenticated() )
            return;
        RemoteTokenServiceCache cache = new RemoteTokenServiceCache(request, tokenServices, auth);
        cacheObject.put((Integer)cache.hashCode(), cache);
    }


}
