package cn.sinjinsong.sample.security.endpoint;

import cn.sinjinsong.sample.exception.base.BaseRestException;
import cn.sinjinsong.sample.exception.domain.RestError;
import cn.sinjinsong.sample.exception.user.AccessDeniedException;
import cn.sinjinsong.sample.util.JsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by SinjinSong on 2017/5/9.
 */
@Component
public class RESTAccessDeniedHandler implements AccessDeniedHandler {
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BaseRestException exception = new AccessDeniedException(authentication.getAuthorities().toString());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(exception.getStatus().value());
        response.getWriter().append(JsonUtil.json(new RestError(exception.getStatus(),exception.getCode(),exception.getErrors(),exception.getMoreInfoURL())));
    }
}
