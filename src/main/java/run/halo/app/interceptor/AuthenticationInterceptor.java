package run.halo.app.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import run.halo.app.cache.AbstractStringCacheStore;
import run.halo.app.model.support.HaloConst;
import run.halo.app.security.util.SecurityUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;


/**
 * @author: DiegoSun
 * @time: 2022/3/10 下午4:25
 * @description: 认证拦截
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AbstractStringCacheStore cacheStore;

    public AuthenticationInterceptor(AbstractStringCacheStore cacheStore){
        this.cacheStore = cacheStore;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String method = request.getMethod();
        if(method.equals("OPTIONS")){
            return true;
        }
        // 获取token
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length==0){
            request.setAttribute(HaloConst.AUTH_KEY, false);
            return true;
        }
        Optional<Cookie> optionalCookie = Arrays.stream(cookies).filter(item -> item.getName().equals("token")).findFirst();
        // 根据是否存在，进行不同的处理
        optionalCookie
            .ifPresentOrElse(cookie -> {
                String token = cookie.getValue();
                Optional<String> value = cacheStore.get(SecurityUtils.buildTokenAccessKey(token));
                request.setAttribute(HaloConst.AUTH_KEY, value.isPresent());
                }, () -> {
                request.setAttribute(HaloConst.AUTH_KEY, false);
            });
        return true;
    }
}
