package xzy.com.Kering.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xzy.com.Kering.annotations.CopyField;

/**
 * @author ：xzy.ajiu
 */
public class BeanUtils {
    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * 拷贝一个对象的属性至另一个对象
     * 支持两个对象之间不同属性名称进行拷贝，使用注解{@link CopyField}
     * @param originBean 源对象 使用注解{@link CopyField#targetName()}
     * @param targetBean 目标对象 使用注解{@link CopyField#originName()}
     */
    public static void copyBean(Object originBean, Object targetBean) {
        Map<String, Object> originFieldKeyWithValueMap = new HashMap<>(16);
        PropertyDescriptor propertyDescriptor = null;
        generateOriginFieldWithValue(propertyDescriptor, originBean, originFieldKeyWithValueMap, originBean.getClass());
        settingTargetFieldWithValue(propertyDescriptor, targetBean, originFieldKeyWithValueMap, targetBean.getClass());

    }

    /**
     * 生成需要被拷贝的属性字典 属性-属性值
     * 递归取父类属性值
     *
     * @param propertyDescriptor         属性描述器，可以获取bean中的属性及方法
     * @param originBean                 待拷贝的bean
     * @param originFieldKeyWithValueMap 存放待拷贝的属性和属性值
     * @param beanClass                  待拷贝的class
     */
    private static void generateOriginFieldWithValue(PropertyDescriptor propertyDescriptor, Object originBean,
                                                     Map<String, Object> originFieldKeyWithValueMap,
                                                     Class<?> beanClass) {
        if (beanClass.getSuperclass() == null) {
            return;
        }
        Field[] originFieldList = beanClass.getDeclaredFields();
        for (Field field : originFieldList) {
            try {
                /*获取属性上的注解。如果不存在，使用属性名，如果存在使用注解名*/
                CopyField annotation = field.getAnnotation(CopyField.class);
                String targetName = "";
                if (annotation != null) {
                    targetName = annotation.targetName();
                } else {
                    targetName = field.getName();
                }
                propertyDescriptor = new PropertyDescriptor(field.getName(), beanClass);
                Method method = propertyDescriptor.getReadMethod();
                Object value = method.invoke(originBean);
                originFieldKeyWithValueMap.put(targetName, value);
            } catch (IntrospectionException e) {
                logger.error("【源对象】异常:" + field.getName() + "不存在对应的get方法，无法参与拷贝！");
            } catch (IllegalAccessException e) {
                logger.error("【源对象】异常:" + field.getName() + "的get方法执行失败！");
            } catch (InvocationTargetException e) {
                logger.error("【源对象】异常:" + field.getName() + "的get方法执行失败！");
            }
        }
    }

    /**
     * 设置目标对象属性
     *
     * @param propertyDescriptor         属性描述器，获取当前传入属性的（getter/setter）方法
     * @param targetBean                 目标容器bean
     * @param originFieldKeyWithValueMap 待拷贝的属性和属性值
     * @param beanClass                  待设置的class
     */
    private static void settingTargetFieldWithValue(PropertyDescriptor propertyDescriptor, Object targetBean,
                                                    Map<String, Object> originFieldKeyWithValueMap,
                                                    Class<?> beanClass) {
        if (beanClass.getSuperclass() == null) {
            return;
        }
        Field[] targetFieldList = beanClass.getDeclaredFields();
        for (Field field : targetFieldList) {
            try {
                CopyField annotation = field.getAnnotation(CopyField.class);
                String originName = "";
                if (annotation != null) {
                    originName = annotation.originName();
                } else {
                    originName = field.getName();
                }
                propertyDescriptor = new PropertyDescriptor(field.getName(), beanClass);
                Method method = propertyDescriptor.getWriteMethod();
                method.invoke(targetBean, originFieldKeyWithValueMap.get(originName));
            } catch (IntrospectionException e) {
                logger.error("【目标对象】异常:" + field.getName() + "不存在对应的set方法，无法参与拷贝！");
            } catch (IllegalAccessException e) {
                logger.error("【目标对象】异常:" + field.getName() + "的set方法执行失败！");
            } catch (InvocationTargetException e) {
                logger.error("【目标对象】异常:" + field.getName() + "的set方法执行失败！");
            }
        }
    }


    /**
     * 拷贝一个对象的属性至另一个对象
     * 支持两个对象之间不同属性名称进行拷贝，使用注解{@link CopyField}
     * @param originBean 源对象 使用注解{@link CopyField#targetName()}
     * @param targetBean 目标对象 使用注解{@link CopyField#originName()}
     * 支持两个对象之间不同类型属性进行拷贝，使用注解{@link CopyField}
     * @param originBean 源对象 使用注解{@link CopyField#targetType()}
     * @param targetBean 目标对象 使用注解{@link CopyField#originType()}
     */
    public static void copyBeanDiffType(Object originBean, Object targetBean) {
        Map<String, Object> originFieldKeyWithValueMap = new HashMap<>(16);
        PropertyDescriptor propertyDescriptor = null;
        generateOriginFieldWithType(propertyDescriptor, originBean, originFieldKeyWithValueMap, originBean.getClass());
        settingTargetFieldWithType(propertyDescriptor, targetBean, originFieldKeyWithValueMap, targetBean.getClass());

    }

    /**
     * 生成需要被拷贝的属性字典 属性-属性值
     * 递归取父类属性值
     *
     * @param propertyDescriptor         属性描述器，可以获取bean中的属性及方法
     * @param originBean                 待拷贝的bean
     * @param originFieldKeyWithValueMap 存放待拷贝的属性和属性值
     * @param beanClass                  待拷贝的class
     */
    private static void generateOriginFieldWithType(PropertyDescriptor propertyDescriptor, Object originBean,
                                                     Map<String, Object> originFieldKeyWithValueMap,
                                                     Class<?> beanClass) {
        if (beanClass.getSuperclass() == null) {
            return;
        }
        Field[] originFieldList = beanClass.getDeclaredFields();
        for (Field field : originFieldList) {
            try {
                /*获取属性上的注解。如果不存在，使用属性名，如果存在使用注解名*/
                CopyField annotation = field.getAnnotation(CopyField.class);
                String targetName = "";
                if (annotation != null) {
                    targetName = annotation.targetName();
                } else {
                    targetName = field.getName();
                }
                propertyDescriptor = new PropertyDescriptor(field.getName(), beanClass);
                Method method = propertyDescriptor.getReadMethod();
                Object value = method.invoke(originBean);
                originFieldKeyWithValueMap.put(targetName, value);
            } catch (IntrospectionException e) {
                logger.error("【源对象】异常:" + field.getName() + "不存在对应的get方法，无法参与拷贝！");
            } catch (IllegalAccessException e) {
                logger.error("【源对象】异常:" + field.getName() + "的get方法执行失败！");
            } catch (InvocationTargetException e) {
                logger.error("【源对象】异常:" + field.getName() + "的get方法执行失败！");
            }
        }
    }

    /**
     * 设置目标对象属性
     *
     * @param propertyDescriptor         属性描述器，获取当前传入属性的（getter/setter）方法
     * @param targetBean                 目标容器bean
     * @param originFieldKeyWithValueMap 待拷贝的属性和属性值
     * @param beanClass                  待设置的class
     */
    private static void settingTargetFieldWithType(PropertyDescriptor propertyDescriptor, Object targetBean,
                                                    Map<String, Object> originFieldKeyWithValueMap,
                                                    Class<?> beanClass) {
        if (beanClass.getSuperclass() == null) {
            return;
        }
        Field[] targetFieldList = beanClass.getDeclaredFields();
        for (Field field : targetFieldList) {
            try {
                CopyField annotation = field.getAnnotation(CopyField.class);
                String originName = "";
                if (annotation != null) {
                    originName = annotation.originName();
                } else {
                    originName = field.getName();
                }
                propertyDescriptor = new PropertyDescriptor(field.getName(), beanClass);
                Method method = propertyDescriptor.getWriteMethod();
                method.invoke(targetBean, originFieldKeyWithValueMap.get(originName));
            } catch (IntrospectionException e) {
                logger.error("【目标对象】异常:" + field.getName() + "不存在对应的set方法，无法参与拷贝！");
            } catch (IllegalAccessException e) {
                logger.error("【目标对象】异常:" + field.getName() + "的set方法执行失败！");
            } catch (InvocationTargetException e) {
                logger.error("【目标对象】异常:" + field.getName() + "的set方法执行失败！");
            }
        }
    }
}
