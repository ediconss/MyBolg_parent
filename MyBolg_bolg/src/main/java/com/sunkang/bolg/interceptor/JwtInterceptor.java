package  com.sunkang.bolg.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.sunkang.bolg.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header=request.getHeader("Authorization");
        System.out.println("进入JwtInterceptor");
        if(header!=null && !header.equals("") ){
            //如果有头信息就进行解析
            System.out.println("token:"+header);
            if(header.startsWith("Bearer")){
                String token=header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles=(String)claims.get("roles");
                    String username=(String)claims.getSubject();
                    if(roles!=null||roles.equals("admin")){
                        request.setAttribute("claims_admin",header);
                        request.setAttribute("claims_username",username);
                    }
                }catch (Exception e){
                    throw new RuntimeException("令牌有误");
                }
                //throw new RuntimeException("权限不足");
            }
        }
        return true;
    }
}
