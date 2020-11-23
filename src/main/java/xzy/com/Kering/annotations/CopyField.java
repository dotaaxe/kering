package xzy.com.Kering.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ：xzy.ajiu
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CopyField {
    /**
     * 在即将被拷贝的属性上面，设置目标属性名
     * @return
     */
    String targetName() default "";

    /**
     * 在即将拷贝至改属性上面，设置源属性名
     * @return
     */
    String originName() default "";

    String targetType() default "";

    String originType() default "";


}
