package com.capfsd.mod.filter;

import com.capfsd.mod.util.JWTUtils;
import com.capfsd.mod.util.LogUtils;
import com.capfsd.mod.util.ResponseResult;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
@Component
public class TokenFilter extends ZuulFilter{

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder()
    {
     //   return PRE_DECORATION_FILTER_ORDER - 1;
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        String req1 = request.getRequestURI();

        // Getting authorization information from header
//        String token = request.getHeader("Authorization");
//        System.out.println("Token is " + token);

        if (request.getMethod().equals("OPTIONS")) {
            LogUtils.getInst(this).debug("Options request, need to pass immediately");
            return false;
        }

        if (antPathMatcher.match("/*/*/*login", request.getRequestURI()) ||
                antPathMatcher.match("/*/*login", request.getRequestURI()) ||
                antPathMatcher.match("/*login", request.getRequestURI()) ||
                antPathMatcher.match("/*/*/*token", request.getRequestURI()) ||
                antPathMatcher.match("/*/*token", request.getRequestURI()) ||
                antPathMatcher.match("/*token", request.getRequestURI()) ||
                antPathMatcher.match("/*/*/*register", request.getRequestURI()) ||
                antPathMatcher.match("/*/*register", request.getRequestURI()) ||
                antPathMatcher.match("/*register", request.getRequestURI()) ||
                antPathMatcher.match("/*activate", request.getRequestURI()) ||
                antPathMatcher.match("/*/*activate", request.getRequestURI()) ||
                antPathMatcher.match("/*/*/*activate/*", request.getRequestURI()) ||
                antPathMatcher.match("/*/activate/*", request.getRequestURI())
        )
        {
            LogUtils.getInst(this).debug("No need to filter." + req1);
            return false;
        }
        return true;
    }

  //  @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        // Getting authorization information from header
        String token = request.getHeader("Authorization");

        if(StringUtils.isEmpty(token) || StringUtils.isBlank(token))
        {
            System.out.println("Empty token");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            requestContext.setResponseBody("token is empty");
            return null;
        }
        String token_replace = token.replace("Bearer","");
        LogUtils.getInst(this).debug("Replaced as : " + token_replace);
        Claims claims  = null;
        try {
            boolean flag = JWTUtils.validToken(token_replace);
            if(!flag)
            {
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
                requestContext.setResponseBody("token is expired or invalid");
                return ResponseResult.fail("Token is expired or invalid.", null);
            }

        } catch (Exception e) {
            LogUtils.getInst(this).debug(e.getMessage());
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
            return null;
        }
        LogUtils.getInst(this).debug("Claims is : " + claims);
        LogUtils.getInst(this).debug("Captured token is: " + token);
        requestContext.setSendZuulResponse(true);
        requestContext.setResponseStatusCode(HttpStatus.OK.value());
        return null;
    }

}
