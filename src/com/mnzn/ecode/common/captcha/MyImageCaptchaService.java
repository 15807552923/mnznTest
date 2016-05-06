package com.mnzn.ecode.common.captcha;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * MyImageCaptchaService
 * 
 * @author cxy
 * 
 */
public interface MyImageCaptchaService extends ImageCaptchaService {
	public Boolean tryResponseForID(String ID, Object response)
			throws CaptchaServiceException;

	public Boolean tryResponseForID(String ID, Object response,
			boolean removeOnError) throws CaptchaServiceException;
}
