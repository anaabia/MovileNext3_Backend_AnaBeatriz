package com.ifood.nutritional.structure.util;

import java.text.Normalizer;

public class StringUtil {
	
	public static String removeAccents(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

}
