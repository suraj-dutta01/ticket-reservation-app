package org.jsp.reservationapi.service;
import static org.jsp.reservationapi.util.ApplicationConstants.ADMIN_VERIFY_LINK;
import static org.jsp.reservationapi.util.ApplicationConstants.USER_VERIFY_LINK;
import static org.jsp.reservationapi.util.ApplicationConstants.USER_RESETPASSWORD_LINK;
import static org.jsp.reservationapi.util.ApplicationConstants.ADMIN_RESETPASSWORD_LINK;

import org.jsp.reservationapi.dao.AdminDao;
import org.jsp.reservationapi.dao.UserDao;
import org.jsp.reservationapi.model.Admin;
import org.jsp.reservationapi.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
@Service
public class LinkGeneratorService {
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private UserDao userDao;

	public String getAdminActivationLink(Admin admin,HttpServletRequest request) {
		admin.setToken(RandomString.make(45));
		adminDao.saveAdmin(admin);
		String sateUrl=request.getRequestURL().toString();
		return sateUrl.replace(request.getServletPath(), ADMIN_VERIFY_LINK+admin.getToken());
	}
	public String getUserActivationLink(User user,HttpServletRequest request) {
		user.setToken(RandomString.make(45));
		userDao.saveUser(user);
		String setUrl=request.getRequestURL().toString();
		return setUrl.replace(request.getServletPath(), USER_VERIFY_LINK+user.getToken());
	}
	public String getAdminResetPasswordLink(Admin admin,HttpServletRequest request) {
		admin.setToken(RandomString.make(45));
		adminDao.saveAdmin(admin);
		String sateUrl=request.getRequestURL().toString();
		return sateUrl.replace(request.getServletPath(), ADMIN_RESETPASSWORD_LINK+admin.getToken());
	}
	public String getUserResetPasswordLink(User user,HttpServletRequest request) {
		user.setToken(RandomString.make(45));
		userDao.saveUser(user);
		String setUrl=request.getRequestURL().toString();
		return setUrl.replace(request.getServletPath(), USER_RESETPASSWORD_LINK+user.getToken());
	}
}
