package com.cross.commons.exception

import java.lang.RuntimeException

class EntityNotFoundException(value: String): RuntimeException("Entity not found by value $value")