package sqlBuilder;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * 定义vo对应的数据库表名
 */
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.TYPE)
public @interface SqlTable {
	/**
	 * 对应的表名
	 */
	String value() default "";
}
