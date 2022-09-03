package com.mozylee.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 생성될 수 있는 위치
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {

}
