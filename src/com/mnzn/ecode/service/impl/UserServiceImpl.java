package com.mnzn.ecode.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.core.entity.UserRole;
import com.mnzn.ecode.repository.UserDao;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;
import com.mnzn.ecode.service.UserRoleService;
import com.mnzn.ecode.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private static final int SALT_SIZE = 8;
	public static final int HASH_INTERATIONS = 1024;
	
	@Autowired
	private UserDao userDao;
	
	
	@Override
	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}

	@Override
	public Page<User> getUserList(Long localId, Map<String, Object> searchParams, int pageNumber, int pageSize,String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<User> spec = buildSpecification(localId, searchParams);
		return userDao.findAll(spec, pageRequest);
	}

	@Override
	public PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		// TODO Auto-generated method stub
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "localid");
		} else if ("name".equals(sortType)) {
			sort = new Sort(Direction.ASC, "name");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	@Override
	public Specification<User> buildSpecification(Long userId, Map<String, Object> searchParams) {
		// TODO Auto-generated method stub
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.localid", new SearchFilter("user.localid", Operator.EQ, userId));
		Specification<User> spec = DynamicSpecifications.bySearchFilter(filters.values(), User.class);
		return spec;
	}

	@Override
	public List<User> getDealerManaList(ShiroUser user) {
		// TODO Auto-generated method stub
		return userDao.getDealerManaList(user);
	}

	@Override
	public List<User> getMyDealerList(String userType,Long userId) {
		// TODO Auto-generated method stub
		return userDao.getMyDealerList(userType,userId);
		
	}
	
	
	public User findOne(Long localid){
		return userDao.findOne(localid);
	}

	@Override
	public List<User> findAllUserByPage(Map<String, String> map) {
		// TODO Auto-generated method stub
		return userDao.findAllUserByPage(map);
	}


	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return userDao.update(user);
	}

	@Override
	public User findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	private UserRoleService userRoleService;

	@Autowired
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	@Override
	public User update(User user, Integer[] roleIds, Integer siteId) {
		user.applyDefaultValue();
		user = userDao.save(user);
		//保存角色权限
		userRoleService.update(user, roleIds, siteId);
		
		/*if (user.isSuper()) {
			
		} else {
			// 不是管理员则删除所有角色
			userRoleService.deleteByUserId(user.getLocalid());
		}*/
		
		return user;
	}

	@Transactional
	public void updatePassword(User user, String rawPassword) {
		entryptPassword(user,rawPassword);
		userDao.save(user);
	}
	
	
	private void entryptPassword(User user,String rawPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(rawPassword.getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	@Override
	public String totalUser(Long id,String userType) {
		// TODO Auto-generated method stub
		return userDao.totalUser(id,userType);
	}

	@Override
	public String todayAdd(Long id, String userType) {
		// TODO Auto-generated method stub
		return userDao.todayAdd(id,userType);
	}

	@Override
	public boolean mobileExist(String mobile,String localId) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(localId)){
			return userDao.countByMobile(mobile) > 0L;
		}else{
			Long l = Long.valueOf(localId);
			return userDao.countByMobile(mobile,l) > 0L;
		}
		
	}
	
	
	@Override
	public String totalMoney(Long id, String userType) {
		// TODO Auto-generated method stub
		return userDao.totalMoney(id,userType);
	}

	@Override
	public String todayMoney(Long id, String userType) {
		// TODO Auto-generated method stub
		return userDao.todayMoney(id,userType);
	}

	
	
}
