package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SocialUserRepository;
import domain.SocialUser;

@Component
@Transactional
public class StringToSocialUserConverter implements Converter<String, SocialUser> {

	@Autowired
	SocialUserRepository socialUserRepository;

	@Override
	public SocialUser convert(String text) {
		SocialUser result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = socialUserRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
