package com.nonamer777.madlevel7task1.repository.exception

import java.lang.Exception

data class ProfileRetrievalError(override val message: String): Exception(message)
