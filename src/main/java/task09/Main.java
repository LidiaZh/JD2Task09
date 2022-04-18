package task09;

import task09.dao.EntityDaoImpl;
import task09.entity.Address;
import task09.entity.People;
import java.lang.reflect.InvocationTargetException;

/**
 * Class Main
 */
public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        People people1 = People.builder()
                .name("Mihail")
                .patronymic("Alexandrovich")
                .surname("Petrov")
                .build();

        People people2 = People.builder()
                .name("Vasiliy")
                .patronymic("Petrovich")
                .surname("Gribov")
                .build();

        People people3 = People.builder()
                .name("Maria")
                .patronymic("Ivanovna")
                .surname("Morozova")
                .build();

        Address address1 = Address.builder()
                .city("Minsk")
                .street("Lobonka")
                .house("9")
                .build();

        Address address2 = Address.builder()
                .city("Minsk")
                .street("Brovki")
                .house("15")
                .build();

        Address address3 = Address.builder()
                .city("Gomel")
                .street("Bogdanovicha")
                .house("25")
                .build();

        EntityDaoImpl daoPeople = new EntityDaoImpl(People.class);
        daoPeople.insert(people1);
        daoPeople.insert(people2);
        daoPeople.insert(people3);
        daoPeople.delete(2);
        daoPeople.updateName(1, "Valerii");
        daoPeople.select();

        EntityDaoImpl daoAddress = new EntityDaoImpl(Address.class);
        daoAddress.insert(address1);
        daoAddress.insert(address2);
        daoAddress.insert(address3);
        daoAddress.delete(3);
        daoAddress.updateCity(1, "Brest");
        daoAddress.select();
    }
}
