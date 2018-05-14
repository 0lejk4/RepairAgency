package com.gelo.validation;

import org.apache.log4j.Logger;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Validated.
 * Classes should extend it in order to get ability to be validated.
 */
public abstract class Validated {
    private static final Logger logger = Logger.getLogger(Validated.class);
    private final List<Alert> errorList = new ArrayList<>();

    /**
     * Valid boolean.
     * Checks if class is valid using delegation to private isAllValid method
     *
     * @return the boolean
     */
    public boolean valid() {
        return isAllValid(this);
    }

    /**
     * Gets error list.
     *
     * @return the error list
     */
    public List<Alert> getErrorList() {
        return errorList;
    }

    /**
     * Add error.
     *
     * @param error the error
     */
    public void addError(Alert error) {
        this.errorList.add(error);
    }

    /**
     * Checks if source is valid and places alerts into error list for each invalid field
     *
     * @param source class that should be validated
     * @return if class was valid or not
     */
    private static boolean isAllValid(Validated source) {
        boolean valid = false;
        try {
            valid = true;
            //Uses Bean Introspector to get property descriptors to use them for validation
            for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(source.getClass()).getPropertyDescriptors()) {

                Method fieldGetter = propertyDescriptor.getReadMethod();
                //Should skip getClass
                if (!fieldGetter.getName().equals("getClass")) {
                    Valid annotation;
                    //No Valid annotation = no need to validate
                    if (fieldGetter.isAnnotationPresent(Valid.class)) {
                        annotation = fieldGetter.getAnnotation(Valid.class);
                    } else continue;

                    //Get value from getter
                    Object fieldAsObj = fieldGetter.invoke(source);

                    //Nullable? write default value and continue
                    if (fieldAsObj == null && fieldGetter.isAnnotationPresent(Nullable.class)) {
                        Nullable nullable = fieldGetter.getAnnotation(Nullable.class);
                        if (!nullable.value().equals("")) {
                            propertyDescriptor.getWriteMethod().invoke(source, nullable.value());
                        }
                        continue;
                    }

                    //Field is not marked Nullable but is null. Add alert about this and continue
                    if (fieldAsObj == null || fieldAsObj.toString().equals("")) {
                        valid = false;

                        source.addError(Alert.danger("required." + propertyDescriptor.getName()));
                        continue;
                    }

                    String field = fieldAsObj.toString();

                    //Get regexp from annotation and check if field matches it
                    boolean fieldValid = field.matches(getFromAnnotation(annotation));

                    //Add corresponding error if it is present
                    if (!fieldValid) {
                        source.addError(Alert.danger("invalid." + propertyDescriptor.getName()));
                    }

                    valid = valid && fieldValid;
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e);
        }
        return valid;
    }

    /**
     * Gets regexp from Valid annotation and places parameters in it if they are present
     *
     * @param valid annotation
     * @return regexp
     */
    private static String getFromAnnotation(Valid valid) {
        return Arrays.equals(valid.params(), new String[]{""})
                ? valid.value()
                : String.format(valid.value(), (Object[]) valid.params());
    }

    /**
     * Gets regexp from specific field of source class
     *
     * @param fieldName name of field that contains Valid annotation
     * @param source class that contains field
     * @return regexp
     */
    private static String getRegExp(String fieldName, Class source) {
        try {
            return getFromAnnotation(new PropertyDescriptor(fieldName, source).getReadMethod().getAnnotation(Valid.class));
        } catch (IntrospectionException e) {
            logger.error(e);
        }
        return "";
    }

    /**
     * Is field valid boolean.
     *
     * @param fieldValue the field value
     * @param fieldName  the field name
     * @param source     the source
     * @return the boolean
     */
    static boolean isFieldValid(String fieldValue, String fieldName, Class source) {
        return fieldValue != null
                && fieldName != null
                && source != null
                && fieldValue.matches(getRegExp(fieldName, source));
    }
}
