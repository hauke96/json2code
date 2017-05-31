package json2code.converter.interfaces;

import json2code.scheme.SchemeFile;

/**
 * The pattern applier actually knows how a class in the target language is structured. It therefore has to construct the target code.
 * 
 * @author hauke
 *
 */
public interface IPatternApplier
{
	String convert(SchemeFile schemeFile);
}
