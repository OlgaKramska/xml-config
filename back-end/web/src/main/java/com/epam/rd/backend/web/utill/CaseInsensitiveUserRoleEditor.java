package com.epam.rd.backend.web.utill;

import com.epam.rd.backend.core.model.UserRole;

import java.beans.PropertyEditorSupport;

public class CaseInsensitiveUserRoleEditor extends PropertyEditorSupport {
    @Override public void setAsText(final String roleName) {
        setValue(UserRole.of(roleName));
    }
}
