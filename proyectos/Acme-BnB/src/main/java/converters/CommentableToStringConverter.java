/* CommentableToStringConverter.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Commentable;

@Component
@Transactional
public class CommentableToStringConverter implements Converter<Commentable, String> {

	@Override
	public String convert(Commentable commentable) {
		String result;

		if (commentable == null)
			result = null;
		else
			result = String.valueOf(commentable.getId());

		return result;
	}

}
