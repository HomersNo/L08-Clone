package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SocialUser;
@Component
@Transactional
public class SocialUserToStringConverter implements Converter<SocialUser, String> {

	@Override
	public String convert(SocialUser socialUser) {
		String result;

		if (socialUser == null)
			result = null;
		else
			result = String.valueOf(socialUser.getId());

		return result;
	}

}