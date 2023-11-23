// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License along with this library; See http://www.gnu.org/licenses/.
// ============================================================================
package com.braintribe.utils.template.model;

import java.util.List;

import com.braintribe.utils.template.TemplateException;

public class Variable implements TemplateNode {
	private String variableName;
	
	public Variable(String variableName) {
		super();
		this.variableName = variableName;
	}

	@Override
	public void merge(StringBuilder builder, MergeContext context) throws TemplateException {
		if (context.isSourceMode()) {
			if (builder.length() > 0)
				builder.append('+');
		}
		
		builder.append(context.getVariableValue(variableName));
	}
	
	@Override
	public void collectVariables(List<Variable> collections) {
		collections.add(this);
	}
	
	public String getVariableName() {
		return variableName;
	}
}
