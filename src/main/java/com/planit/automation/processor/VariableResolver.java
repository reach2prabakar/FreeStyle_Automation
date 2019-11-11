package com.planit.automation.processor;

import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.planit.automation.library.Global;


public class VariableResolver {

	private Map<String, String> dynamicVariableMap;
	private StrSubstitutor substitutor;
	
	public VariableResolver(Map<String, String> dynamicVariableMap){
	
		this.dynamicVariableMap = dynamicVariableMap;
		
	}
	
	
	public VariableResolver(StrSubstitutor substitutor){
		
		this.substitutor = substitutor;
		
	}
	
	
	public String resolveDynamicVariables(String stringToBeResolved){
		
		
		if(stringToBeResolved!=null){
			if(!stringToBeResolved.isEmpty()){
				if (stringToBeResolved.matches(Global.varibleDifferentiator)){
					//testData = Global.variableNamesmap.get(testData);		
				}
				if(!dynamicVariableMap.isEmpty()){
					for (Map.Entry<String, String> entry : dynamicVariableMap.entrySet())
					{

						if(entry.getValue().toString().trim()!=null){
							String variableToReplace = entry.getKey().toString().trim();
							String replaceData = entry.getValue().toString().trim();
							if (variableToReplace.matches(Global.varibleDifferentiator)){
								stringToBeResolved = stringToBeResolved.replace(variableToReplace, replaceData);
								stringToBeResolved=stringToBeResolved.trim();
							}
						}
					}
				}
			}
		}
		
		return stringToBeResolved;
	}
	
	
	public String resolveObjectIndentifiers(String stringToBeResolved){
		
		if(stringToBeResolved != null && !stringToBeResolved.isEmpty()){
			stringToBeResolved = stringToBeResolved.trim();
			stringToBeResolved = substitutor.replace(stringToBeResolved);
		}
		
		return stringToBeResolved;
	}
	
	public String resolveContentIndentifiers(String stringToBeResolved){
		
		if(stringToBeResolved != null && !stringToBeResolved.isEmpty()){
			stringToBeResolved = stringToBeResolved.trim();
			stringToBeResolved = substitutor.replace(stringToBeResolved);
		}
		
		return stringToBeResolved;
	}
	
}
