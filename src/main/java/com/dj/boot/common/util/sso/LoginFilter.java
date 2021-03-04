package com.dj.boot.common.util.sso;

//import com.cisdi.gzw.bi.user.serviceImpl.AccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {
	FilterConfig config;
	String authUrl;
	String redirectBaseUrl;
	String accessUrl;
	String token;
//	private AccessService accessService;

//	public void setAccessService(AccessService accessService){
//		this.accessService = accessService;
//	}

	public void setFilterConfig(FilterConfig config) {
		this.config = config;
	}

	private static  final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	public FilterConfig getFilterConfig() {
		return config;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		try {
			BPEngineUser sgccUser = (BPEngineUser) SSOUtil.getUserFromSession(request);
			if(token.equals("true")){
				//TODO 正式环境注释去掉 start
				if (sgccUser == null) {
					sgccUser = new BPEngineUser();
				}
				SSOUtil ssoUtil = new SSOUtil(sgccUser);
				if (!sgccUser.isLogin() || !ssoUtil.compareSSOUser(request)) {
					// Domino验证用户链接
					Cookie[] cookies = request.getCookies();
					if(cookies == null){
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						return;
					}
					String domainName = null;
					//加入登录用户的单位信息:companyCode
					String companyId = null;

					for(int i = 0;i < cookies.length;i++){
						if(cookies[i].getName().equals("oahost")){
							domainName = cookies[i].getValue();
						}
						if(cookies[i].getName().equals("companyCode")){
							companyId = cookies[i].getValue();
						}
					}
					logger.debug("用户的单位编码:"+companyId);
					HttpSession session = request.getSession();
					session.setAttribute("oahost", domainName);
					session.setAttribute("userCompanyId", companyId);
					if(domainName != null){
						String authUrl = "http://"+domainName+this.authUrl;
						String redirectBaseUrl = "http://"+domainName+this.redirectBaseUrl;
						String accessUrl = "http://"+domainName + this.accessUrl;
						ssoUtil.setAuthUrl(authUrl);
						ssoUtil.setRedirectUrl(redirectBaseUrl + request.getRequestURL());
						ssoUtil.setAccessUrl(accessUrl);
					}else{
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						return;
					}
//				// 未登录用户重定向链接
//				ssoUtil.setRedirectUrl(this.redirectBaseUrl + request.getRequestURL());
					sgccUser = (BPEngineUser) ssoUtil.getSSOUser(request, response);
					/* 搜索引擎验证用户权限 BEGIN */
					if (sgccUser != null) {
						sgccUser.setAccessable(ssoUtil.isAccess(request).equals("")?false:true);
						sgccUser.setAdminCorper(ssoUtil.isAccess(request));
						SSOUtil.SaveUserToSession(request, sgccUser);
					} else {
						return;
					}
					/* 搜索引擎验证用户权限 END   */
				}
				//TODO 正式环境注释去掉 end
			}

			//用户权限检查
			//※可以取消，只控制菜单
//			if (accessService.checkAccessUrl(request.getRequestURI(),sgccUser.getUserId()) == false){
//				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//				return;
//			}
			chain.doFilter(req, res); // no chain parameter needed here
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
		this.accessUrl = filterConfig.getInitParameter("accessUrl");
		this.authUrl = filterConfig.getInitParameter("authUrl");
		this.redirectBaseUrl = filterConfig.getInitParameter("redirectBaseUrl");
		this.token = filterConfig.getInitParameter("token");
	}

	@Override
	public void destroy() {
	}

}
