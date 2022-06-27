package task09.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import task09.entity.Address;
import task09.entity.People;
import task09.util.HibernateUtil;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntityDaoImplTest extends Assert {
    public static final String NAME_TO_UPDATE = "Valeriya";
    public static final String CITY_TO_UPDATE = "Mogilev";
    private static EntityDaoImpl daoPeople;
    private static EntityDaoImpl daoAddress;
    private static EntityManager em;
    private static Address address1;
    private static Address address2;
    private static People people1;
    private static People people2;

    @Before
    public void setUp() {
        daoPeople = new EntityDaoImpl(People.class);
        daoAddress = new EntityDaoImpl(Address.class);
        em = HibernateUtil.getEntityManager();

        address1 = Address.builder()
                .city("Vitebsk")
                .street("Vishnevaya")
                .house("11")
                .build();

        address2 = Address.builder()
                .city("Grodno")
                .street("Berezovaya")
                .house("10")
                .build();

        people1 = People.builder()
                .name("Marina")
                .surname("Vasytina")
                .patronymic("Ivanovna")
                .build();

        people2 = People.builder()
                .name("Ivan")
                .surname("Petrov")
                .patronymic("Vasilievich")
                .build();
    }

    @Test
    public void a_testInsert() {
        daoPeople.insert(people2);
        People peopleFromDB = em.find(People.class, people2.getId());
        assertEquals("Id not equals", people2.getId(),
                peopleFromDB.getId());
        assertEquals("Name not equals", people2.getName(),
                peopleFromDB.getName());
        assertEquals("Surname not equals", people2.getSurname(),
                peopleFromDB.getSurname());
        assertEquals("Patronymic not equals", people2.getPatronymic(),
                peopleFromDB.getPatronymic());

        daoAddress.insert(address2);
        Address addressFromDB = em.find(Address.class, address2.getId());
        em.getTransaction().begin();
        assertEquals("Id not equals", address2.getId(), addressFromDB.getId());
        assertEquals("City not equals", address2.getCity(), addressFromDB.getCity());
        assertEquals("Street not equals", address2.getStreet(), addressFromDB.getStreet());
        assertEquals("House not equals", address2.getHouse(), addressFromDB.getHouse());
    }

    @Test
    public void b_testDelete() {
        daoPeople.delete(people2.getId());
        boolean checkDelete = false;

        try {
            em.find(People.class, people2.getId());
        } catch (Exception ex) {
            checkDelete = true;
        }
        assertTrue("People with id = " + people2.getId()
                + " wasn`t delete", checkDelete);

        daoAddress.delete(address2.getId());
        checkDelete = false;
        try {
            em.find(Address.class, address2.getId());
        } catch (Exception ex) {
            checkDelete = true;
        }
        assertTrue("Address with id = " + address2.getId()
                + " wasn`t delete", checkDelete);
    }

    @Test
    public void c_testUpdateName() throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        daoPeople.insert(people1);
        daoPeople.updateName(people1.getId(), NAME_TO_UPDATE);

        People peopleFromDB = em.find(People.class, people1.getId());
        assertEquals(peopleFromDB.getName(), NAME_TO_UPDATE);
        daoPeople.select();
    }

    @Test
    public void d_testUpdateCity() throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        daoAddress.insert(address1);
        daoAddress.updateCity(address1.getId(), CITY_TO_UPDATE);
        Address addressFromDB = em.find(Address.class, address1.getId());

        assertEquals("City with id = " + address1.getId() + " wasn`t update",
                addressFromDB.getCity(), CITY_TO_UPDATE);
    }

    @AfterClass
    public static void cleanUp() {
        em.close();
        HibernateUtil.close();
    }
}