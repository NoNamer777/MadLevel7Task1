package com.nonamer777.madlevel7task1.repository.exception

import java.lang.Exception

data class ProfileSaveError(override val message: String, override val cause: Throwable):
    Exception(message, cause)
