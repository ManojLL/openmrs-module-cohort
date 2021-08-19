/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.cohort.validator;

import org.openmrs.api.context.Context;
import org.openmrs.module.cohort.CohortRole;
import org.openmrs.module.cohort.api.CohortService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Qualifier("addCohortRoleValidator")
public class AddCohortRoleValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(CohortRole.class);
	}
	
	@Override
	public void validate(Object command, Errors errors) {
		CohortService cohortService = Context.getService(CohortService.class);
		
		CohortRole currentRole = (CohortRole) command;
		CohortRole role = cohortService.getCohortRoleByName(currentRole.getName());
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		
		if (role != null) {
			errors.rejectValue("name", "An entry with this name already exists");
		}
	}
}
