package net.xiaosaguo.myblog.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * description: 自定义的 Bean 工具类
 *
 * @author xiaosaguo
 * @date 2020/05/15
 */
public class MyBeanUtils {

    /**
     * description: 将对象中值为 null 的字段的名称以 string[] 返回
     *
     * @param o 要判断的实例
     * @return property 值为 null 的 propertyName 数组
     * @author xiaosaguo
     * @date 2020/05/15 11:31
     */
    public static String[] getNullPropertyNames(Object o) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(o);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNameList = new ArrayList<>();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            if (Objects.isNull(beanWrapper.getPropertyValue(propertyName))) {
                nullPropertyNameList.add(propertyName);
            }
        }
        return nullPropertyNameList.toArray(new String[0]);
    }

}
