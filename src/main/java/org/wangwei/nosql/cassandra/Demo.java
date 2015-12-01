package org.wangwei.nosql.cassandra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo {
    public static void main(String[] args) {
        ConfigurableApplicationContext ct = new ClassPathXmlApplicationContext("beans.xml");
        PersonRepository repository = ct.getBean(PersonRepository.class);

        List<Users> persons = new ArrayList<Users>();
        for (int i = 0; i < 10; i++) {
            Users person = new Users();
            person.setUserid(i);
            person.setCreate_time(new Date());
            persons.add(person);
        }
        repository.save(persons);
        ct.close();
    }
}
