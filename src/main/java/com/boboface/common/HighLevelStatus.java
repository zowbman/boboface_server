package com.boboface.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.zowbman.base.util.WebUtil;

import com.boboface.listener.SesstionListener;

/**
 * 
 * Title:HighLevelStatus
 * Description:高权状态类
 * @author    zwb
 * @date      2016年7月27日 下午11:15:25
 *
 */
public class HighLevelStatus {
	/**
	 * 获取高权状态
	 * @param request
	 * @param response
	 * @return
	 */
	public static Integer getStatus(HttpServletRequest request, HttpServletResponse response){
		Integer status = (Integer) request.getSession().getAttribute("whosyourdaddy");
		if(status == null){
			String sid = WebUtil.getCookieByName(request, "whosyourdaddyId");
			if(sid!=null){
				HttpSession session = SesstionListener.getSession(sid);
				if (session != null) {
					status =  (Integer) session.getAttribute("whosyourdaddy");
					if (status != null) {
						SesstionListener.removeSession(sid);
						request.getSession().setAttribute("whosyourdaddy", status);
						WebUtil.addCookie(response, "whosyourdaddyId", request.getSession().getId(), 30 * 60);
					}
				}
			}
		}
		return status == null ? 0 : status;
	}
}
