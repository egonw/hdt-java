/**
 * File: $HeadURL: https://hdt-java.googlecode.com/svn/trunk/hdt-java/src/org/rdfhdt/hdt/dictionary/DictionaryFactory.java $
 * Revision: $Rev: 191 $
 * Last modified: $Date: 2013-03-03 11:41:43 +0000 (dom, 03 mar 2013) $
 * Last modified by: $Author: mario.arias $
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Contacting the authors:
 *   Mario Arias:               mario.arias@deri.org
 *   Javier D. Fernandez:       jfergar@infor.uva.es
 *   Miguel A. Martinez-Prieto: migumar2@infor.uva.es
 *   Alejandro Andres:          fuzzy.alej@gmail.com
 */

package org.rdfhdt.hdt.dictionary;

import org.rdfhdt.hdt.dictionary.impl.FourSectionDictionary;
import org.rdfhdt.hdt.dictionary.impl.HashDictionary;
import org.rdfhdt.hdt.exceptions.IllegalFormatException;
import org.rdfhdt.hdt.hdt.HDTFactory;
import org.rdfhdt.hdt.hdt.HDTVocabulary;
import org.rdfhdt.hdt.options.ControlInfo;
import org.rdfhdt.hdt.options.HDTOptions;
import org.rdfhdt.hdt.options.HDTSpecification;

/**
 * Factory that creates Dictionary objects
 * 
 */
public class DictionaryFactory {

	public static final String MOD_DICT_IMPL_HASH = "hash";


	/**
	 * Creates a default dictionary (HashDictionary)
	 * 
	 * @return Dictionary
	 */
	public static Dictionary createDefaultDictionary()
			throws IllegalArgumentException {
		return new FourSectionDictionary(new HDTSpecification());
	}
	
	/**
	 * Creates a default dictionary (HashDictionary)
	 * 
	 * @return Dictionary
	 */
	public static TempDictionary createTempDictionary(HDTOptions spec) {
		String dictImpl = spec.get("tempDictionary.impl");
		
		// Implementations available in the Core
		if(dictImpl==null || "".equals(dictImpl) || MOD_DICT_IMPL_HASH.equals(dictImpl)) {
			return new HashDictionary(spec);
		}
		
		// Implementations available in the HDT-Disk module.
		return HDTFactory.getTempFactory().getDictionary(spec);
	}
	
	public static DictionaryPrivate createDictionary(HDTOptions spec) {
		String name = spec.get("dictionary.type");
		if(name==null || HDTVocabulary.DICTIONARY_TYPE_FOUR_SECTION.equals(name)) {
			return new FourSectionDictionary(spec);
		}
		throw new IllegalFormatException("Implementation of ditionary not found for "+name);
	}
	
	public static DictionaryPrivate createDictionary(ControlInfo ci) {
		String name = ci.getFormat();
		if(HDTVocabulary.DICTIONARY_TYPE_FOUR_SECTION.equals(name)) {
			return new FourSectionDictionary(new HDTSpecification());
		}
		throw new IllegalFormatException("Implementation of ditionary not found for "+name);
	}
}
