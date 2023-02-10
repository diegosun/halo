package run.halo.app.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import run.halo.app.model.support.HaloConst;
import java.util.Objects;
import java.util.Optional;

public class AuthUtil {
    public static Boolean getAuth(){
        Object authObj = ((ServletRequestAttributes) Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes())).getRequest().getAttribute(
            HaloConst.AUTH_KEY);
        return (Boolean) Optional.ofNullable(authObj).orElse(false);
    }

}
