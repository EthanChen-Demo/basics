package com.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * development processing descriptions.
 * 
 * uncompleted version.
 * 
 * @author bl05386
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Uncompleted {
     public String description() ;
}