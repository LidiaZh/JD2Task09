package task09.dao;

import task09.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class EntityDaoImpl implements EntityDao {

    private EntityManager em;
    private final Class aClass;

    public EntityDaoImpl(Class aClass) {
        this.aClass = aClass;
    }

    @Override
    public void insert(Object object) {
        em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public <T> void delete(T id) {
        em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            em.remove(em.find(aClass, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("This element is absent in table");
        }
        em.close();
    }

    @Override
    public <T> void updateName(T id, Object object) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        Method method = aClass.getMethod("setName", new Class[]{String.class});
        Object p = aClass.getConstructor().newInstance();
        try {
            p = em.find(aClass, id);
            method.invoke(p, object);
            em.merge(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("This element is absent in table");
        }
        em.close();
    }

    @Override
    public <T> void updateCity(T id, Object object) throws
            NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        Method method = aClass.getMethod("setCity", new Class[]{String.class});
        Object p = aClass.getConstructor().newInstance();
        try {
            p = em.find(aClass, id);
            method.invoke(p, object);
            em.merge(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("This element is absent in table");
        }
        em.close();
    }

    @Override
    public void select() {
        em = HibernateUtil.getEntityManager();
        String queryString = "SELECT e FROM " + aClass.getSimpleName() + " e";
        Query query = em.createQuery(queryString);
        List list = query.getResultList();
        list.forEach(System.out::println);
        em.close();
    }
}
