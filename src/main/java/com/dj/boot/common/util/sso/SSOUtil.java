package com.dj.boot.common.util.sso;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Vector;

public class SSOUtil {
	
	private final static Logger Log  = LoggerFactory.getLogger(SSOUtil.class);

	// Domino保存在本地的Cookie信息
	private final static String cookieName = "LtpaToken";
	// Domino提供的验证用户url(返回不含密码)
	private String authUrl;
	// Domino提供的验证用户url(返回含密码)
	private String authUrl2;
	// Domino提供的验证用户权限url(返回含密码)
	private String accessUrl;
	// Domino提供的验证失败后重定向页面url(返回含密码)
	private String redirectUrl;
	// 当前登录用户信息类
	private SSOUser user;
	


	/**
	 * 构造函数
	 */
	public SSOUtil(SSOUser user) {
		this.authUrl = null;
		this.authUrl2 = null;
		this.accessUrl = null;
		this.redirectUrl = null;
		this.user = user;
	}
	// 读取HTTP返回信息
	private void readData(InputStream ins, Vector<String> content)
			throws IOException {
		String linestr = null;

		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		while (true) {
			try {
				linestr = br.readLine();
			} catch (Exception e) {
				break;
			}
			if (linestr != null) {
				linestr = linestr.trim();
				if (linestr.length() > 0) {
					content.addElement(linestr);
				}
			} else {
				break;
			}
		}
	}

	// 读取本地Cookie信息，通过Domino进行用户认证
	public Vector<String> getResponseFromDomino(HttpServletRequest request,
                                                String url) throws Exception {

		// 取Domino保存在客户端本地的Cookie
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		// 初始化HTTP请求
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		Vector<String> content = new Vector<String>();
		// 把Domino的Cookie信息放到HTTP请求中
		for (int i = 0; i < cookies.length; i++) {
			String dominoCookieName = cookies[i].getName();
			if (cookieName.equalsIgnoreCase(dominoCookieName)) {
				String dominoCookieValue = cookies[i].getValue();
				String sCookie = dominoCookieName + "=" + dominoCookieValue;
				method.setRequestHeader("Cookie", sCookie);
				break;
			}
		}
		try {
			// 将Cookie通过HTTP发送到Domino服务器，请求进行用户验证
			Date begin = new Date();
			Log.debug("通过HTTP发送到Domino服务器，请求进行用户验证!开始");
			client.executeMethod(method);
			InputStream is = method.getResponseBodyAsStream();
			readData(is, content);
			Date end = new Date();
			Log.debug("通过HTTP发送到Domino服务器，请求进行用户验证!完成使用时间为："+(end.getTime()-begin.getTime())+"ms");
			method.releaseConnection();
		} catch (Exception e) {
			throw new Exception(
					"Error when communicating with Domino server.");
		}
		return content;
	}

	// 如果成功，从Domino服务器返回信息中取用户ID，否则返回false
	private boolean getUserIdfromDomino(HttpServletRequest request, String url)
			throws Exception {
		boolean isSucc = false;
		Vector<String> content = getResponseFromDomino(request, url);

		if (content == null) {
			return isSucc;
		}
		String aLine = (String) content.elementAt(0);
		if (aLine.length() != 0) {
			// 返回格式：userid=zhouxiao
			String[] strUser = aLine.split("=");
			if (strUser.length == 2) {
				// 初始化登录用户信息类（不含登录口令），并返回true
				if (strUser[0].equalsIgnoreCase("userid")) {
					user.setUserId(strUser[1]);
					user.setDominoToken(getCookieValue(request));
					user.setLogin(true);
					isSucc = true;
				}
			}
		}
		return isSucc;
	}

	// 如果成功，从Domino服务器返回信息中取用户ID/登录口令，否则返回false
	private boolean getUserfromDomino(HttpServletRequest request, String url)
			throws Exception {
		boolean isSucc = false;
		Vector<String> content = getResponseFromDomino(request, url);

		if (content == null) {
			return isSucc;
		}
		String aLine = (String) content.elementAt(0);
		if (aLine.length() != 0) {
			// 返回格式：userid=zhouxiao&password=123456
			String[] vContent = aLine.split("&");
			if (vContent.length == 2) {
				String[] strUser = vContent[0].split("=");
				if (strUser.length == 2) {
					if (strUser[0].equalsIgnoreCase("userid")) {
						// 初始化登录用户信息类（含登录口令），并返回true
						user.setUserId(strUser[1]);
						String[] strPasswd = vContent[1].split("=");
						if (strPasswd.length == 2) {
							if (strPasswd[0].equalsIgnoreCase("password")) {
								user.setEncryptedPwd(strPasswd[1]);
								user.setDominoToken(getCookieValue(request));
								user.setLogin(true);
								isSucc = true;
							}
						}
					}
				}
			}
		}
		return isSucc;
	}

	// 获取登录Domino服务器的cookie
	private String getCookieValue(HttpServletRequest request) {
		Cookie userCookie = null;

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				userCookie = cookies[i];
				if (cookieName.equals(userCookie.getName())) {
					return userCookie.getValue();
				}
			}
		}
		return null;
	}

	// 从session中查找并返回名为ssoUser的对象
	public static SSOUser getUserFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if (session.getAttribute("ssoUser") != null) {
			return (SSOUser) session.getAttribute("ssoUser");
		} else {
			return null;
		}
	}

	// 将用户信息对象保存到session里，名为ssoUser
	public static void SaveUserToSession(HttpServletRequest request,
                                         SSOUser user) {
		HttpSession session = request.getSession(true);
		session.setAttribute("ssoUser", user);
	}

	// 比较现有session中的用户与Domino的登录用户是否一致
	public boolean compareSSOUser(HttpServletRequest request)
			throws Exception {
		boolean isSameUser = false;
		String cookieValue;

		// 取cookie
		cookieValue = getCookieValue(request);
		if (cookieValue == null) {
			// 如果Domino未登录，则同步清除session中的登录用户
			request.getSession().removeAttribute("ssoUser");
		} else {
			// 比较session中的用户cookie是否与当前请求中获得的cookie相同，如相同则表示用户已经登录
			SSOUser tmpUser = getUserFromSession(request);
			if (tmpUser != null && tmpUser.isLogin()) {
				if (tmpUser.getDominoToken().equals(cookieValue)) {
					isSameUser = true;
				} else {
					// 如果cookie不一致，则同步清除session中的登录用户
					request.getSession().removeAttribute("ssoUser");
				}
			}
		}
		return isSameUser;
	}

	// 从Domino取带密码的用户信息，如果未登录则重定向到登录页面
	public SSOUser getSSOUserWithPwd(HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
		try {
			if (authUrl2 == null) {
				throw new Exception("SSOUtil.authUrl2 has not been set.");
			}
			// 从Domino取用户登录信息
			if (getUserfromDomino(request, authUrl2)) {
				SaveUserToSession(request, user);
				return user;
			} else {
				// 如未登录则重定向到登录页面
				if (redirectUrl == null) {
					throw new Exception(
							"SSOUtil.redirectURL has not been set.");
				}
				response.sendRedirect(redirectUrl);
				return null;
			}
		} catch (Exception e) {
			throw new Exception("SSO get user id failed: " + e.getMessage());
		}
	}

	// 从Domino取用户信息，如果未登录则重定向到登录页面
	public SSOUser getSSOUser(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
		try {
			if (authUrl == null) {
				throw new Exception("SSOUtil.authUrl has not been set.");
			}
			// 从Domino取用户登录信息
			if (getUserIdfromDomino(request, authUrl)) {
				SaveUserToSession(request, user);
				return user;
			} else {
				// 如未登录则重定向到登录页面
				if (redirectUrl == null) {
					throw new Exception(
							"SSOUtil.redirectURL has not been set.");
				}
				response.sendRedirect(redirectUrl);
				return null;
			}
		} catch (Exception e) {
			throw new Exception("SSO get user id failed: " + e.getMessage());
		}
	}

	// 判断当前用户是否有访问权限，只供搜索引擎调用
	/*
	 * 系统管理员:返回access=系统管理员 分公司搜索引擎管理员:返回分公司名称,如果是多个分公司管理员，用逗号格开
	 * 如access=国家电网公司,福建电力公 非搜索引擎管理员:返回空字符串，即access=
	 */
	public String isAccess(HttpServletRequest request) throws Exception {
		String isAccess = "";

		if (accessUrl == null) {
			throw new Exception("SSOUtil.accessUrl has not been set.");
		}

		Vector<String> content = getResponseFromDomino(request, accessUrl);
		if (content == null) {
			return "";
		}
		// 取返回信息的第一行
		String aLine = (String) content.elementAt(0);
		if (aLine.length() != 0) {
			String[] vContent = aLine.split("=");
			if (vContent.length == 2) {
				if (vContent[0].equalsIgnoreCase("access")) {
					if (vContent[1].equals("")) {
						isAccess = "";
					} else {
						Log.debug("================================");
						if(vContent[1].indexOf("/")!= -1){
							isAccess = vContent[1].split("/")[0];
						}else{
							isAccess = vContent[1];
						}
						Log.debug(isAccess);
						Log.debug("================================");
					}
				}
			}
		}
		return isAccess;
	}

	/**
	 * @return the authUrl
	 */
	public String getAuthUrl() {
		return authUrl;
	}

	/**
	 * @param authUrl
	 *            the authUrl to set
	 */
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	/**
	 * @return the accessUrl
	 */
	public String getAccessUrl() {
		return accessUrl;
	}

	/**
	 * @param accessUrl
	 *            the accessUrl to set
	 */
	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}

	/**
	 * @return the redirectURL
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * @param redirectUrl
	 *            the redirectUrl to set
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/**
	 * @return the authUrl2
	 */
	public String getAuthUrl2() {
		return authUrl2;
	}

	/**
	 * @param authUrl2
	 *            the authUrl2 to set
	 */
	public void setAuthUrl2(String authUrl2) {
		this.authUrl2 = authUrl2;
	}

}