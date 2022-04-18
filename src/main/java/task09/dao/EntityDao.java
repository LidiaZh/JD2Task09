package task09.dao;

import java.lang.reflect.InvocationTargetException;

public interface EntityDao {

    void insert(Object object);

    <T> void delete(T id);

    <T> void updateName(T id, Object object)
            throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException;

    <T> void updateCity(T id, Object object)
            throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException;

    void select();
}
