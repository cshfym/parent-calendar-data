package com.parentcalendar.services.exclusion

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes


/**
 * Annotate any attribute with @EntityExclude to exclude from Gson output rendering.
 */
class EntityExclusionStrategy implements ExclusionStrategy {

    public boolean shouldSkipField(FieldAttributes fa) {
        fa.getAnnotation(EntityExclude.class) != null
    }

    @Override
    public boolean shouldSkipClass(Class<?> type) {
        false
    }

}
