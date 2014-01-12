package nl.topicus.heroku.wicket;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;

@SuppressWarnings("serial")
public class ClassModifier extends AttributeModifier {

	/**
	 * @param attribute
	 * @param replaceModel
	 */
	public ClassModifier(IModel<?> replaceModel) {
		super("class", replaceModel);
	}

	/**
	 * @param attribute
	 * @param value
	 */
	public ClassModifier(Serializable value) {
		super("class", value);
	}

}
