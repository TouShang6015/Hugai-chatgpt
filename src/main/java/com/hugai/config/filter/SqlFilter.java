package com.hugai.config.filter;

import com.alibaba.fastjson2.JSON;
import com.org.bebas.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

public class SqlFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(SqlFilter.class);

    public SqlFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (isError(request)) {
            handleErrorRequest(httpResponse);
            return;
        }

        chain.doFilter(httpRequest, httpResponse);
    }

    private void handleErrorRequest(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(Result.fail("非法请求参数")));
    }

    public void destroy() {
    }

    private boolean isError(ServletRequest request) {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Iterator<String[]> values = httpRequest.getParameterMap().values().iterator();// 获取所有的表单参数
        Iterator<String[]> values2 = httpRequest.getParameterMap().values().iterator();// 获取所有的表单参数
        boolean isError = false;
        try {
            String regEx_sql = "select|update|and|or|delete|insert|trancate|char|chr|into|substr|ascii|declare|exec|count|master|drop|execute";
            // sql注入过滤
            while (values.hasNext()) {

                String[] valueArray = values.next();

                for (int i = 0; i < valueArray.length; i++) {
                    String value = valueArray[i];
                    // 分拆关键字

                    String[] inj_stra = regEx_sql.split("\\|");

                    for (int j = 0; j < inj_stra.length; j++) {
                        // 判断如果路径参数值中含有关键字则返回true,并且结束循环
                        if ("and".equals(inj_stra[j]) || "or".equals(inj_stra[j]) || "into".equals(inj_stra[j])) {
                            if (value.toLowerCase()
                                    .indexOf(" " + inj_stra[j] + " ") != -1) {
                                isError = true;
                                log.info(" SQL 关键字过滤异常1，参数值：" + value + ";链接：" + httpRequest.getServletPath());
                                break;
                            }
                        } else {
                            if (value.toLowerCase()
                                    .indexOf(" " + inj_stra[j] + " ") != -1
                                    || value.toLowerCase().indexOf(
                                    inj_stra[j] + " ") != -1) {
                                isError = true;
                                log.info(" SQL 关键字过滤异常2，参数值：" + value + ";链接：" + httpRequest.getServletPath());
                                break;
                            }
                        }
                    }
                    if (isError) {
                        break;
                    }
                }

                if (isError) {
                    break;
                }

            }

            String serletPath = httpRequest.getServletPath();
            String regEx_xss = "script|iframe|video";

            //新加验证，验证不加空格的
            String regEx_xss_noBlank = "expression(";

            // XSS漏洞过滤
            while (values2.hasNext()) {

                String[] valueArray = values2.next();

                for (int i = 0; i < valueArray.length; i++) {
                    String value = valueArray[i];

                    // 分拆关键字
                    String[] inj_stra = regEx_xss.split("\\|");
                    for (int j = 0; j < inj_stra.length; j++) {
                        // 判断如果路径参数值中含有关键字则返回true,并且结束循环
                        if (value.toLowerCase().indexOf("<" + inj_stra[j]) != -1
                                || value.toLowerCase().indexOf(inj_stra[j] + ">") != -1
                                || value.toLowerCase().indexOf(" " + inj_stra[j] + " ") != -1
                                || value.toLowerCase().indexOf(" " + inj_stra[j]) != -1
                                || value.toLowerCase().indexOf(inj_stra[j] + " ") != -1
                                || value.toLowerCase().indexOf("<" + inj_stra[j] + ">") != -1) {
                            log.info(" SQL 关键字过滤异常3，参数值：" + value + ";链接：" + serletPath);
                            isError = true;
                            break;
                        }
                    }

                    /*新加验证，有验证不加空格的start*/
                    // 分拆关键字
                    String[] inj_stra_noBlank = regEx_xss_noBlank.split("\\|");
                    for (int j = 0; j < inj_stra_noBlank.length; j++) {
                        if (value.toLowerCase().indexOf(inj_stra_noBlank[j]) != -1
                                || value.toLowerCase().indexOf("<" + inj_stra_noBlank[j]) != -1
                                || value.toLowerCase().indexOf(inj_stra_noBlank[j] + ">") != -1
                                || value.toLowerCase().indexOf(" " + inj_stra_noBlank[j] + " ") != -1
                                || value.toLowerCase().indexOf(" " + inj_stra_noBlank[j]) != -1
                                || value.toLowerCase().indexOf(inj_stra_noBlank[j] + " ") != -1) {
                            log.info(" SQL 关键字过滤异常3，参数值：" + value + ";链接：" + serletPath);
                            isError = true;
                            break;
                        }
                    }
                    /*新加验证，有验证不加空格的end*/

                    if (isError) {
                        break;
                    }

                }

                if (isError) {
                    break;
                }

            }

            // 如果是admin后台，则根据请求参数进行过滤
//				if(serletPath.startsWith("/admin/toIndex")){
//					String uri = httpRequest.getRequestURI();
//					String selMenu = httpRequest.getParameter("selMenu");
//					String admin_regEx = "script|iframe";
//					String[] inj_stra = admin_regEx.split("\\|");
//					for (int j = 0; j < inj_stra.length; j++) {
//						if (!StringUtils.isEmpty(uri) && uri.toLowerCase()
//								.indexOf(inj_stra[j] + ">") != -1) {
//							isError = true;
//							break;
//						}
//						if (!StringUtils.isEmpty(selMenu) && selMenu.toLowerCase()
//								.indexOf(inj_stra[j] + ">") != -1) {
//							isError = true;
//							break;
//						}
//					}
//				}
//			}

        } catch (Exception e) {
            log.error("error", e);
        }
        return isError;
    }

}
