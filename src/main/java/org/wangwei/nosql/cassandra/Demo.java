package org.wangwei.nosql.cassandra;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.cassandra.core.CassandraOperations;

public class Demo {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext ct = new ClassPathXmlApplicationContext("beans.xml");
        CassandraOperations cassandraOperations = ct.getBean("cqlTemplate", CassandraOperations.class);
        // PersonRepository repository = ct.getBean(PersonRepository.class);
        //
        // List<Users> persons = new ArrayList<Users>();
        // for (int i = 0; i < 10; i++) {
        // Users person = new Users();
        // person.setUserid(i);
        // person.setCreate_time(new Date());
        // Map<Date, String> todo = Maps.newConcurrentMap();
        // todo.put(new Date(), "say: " + i);
        // person.setTodo(todo);
        // person.setFirst_name(i + "");
        // Map<String, String> m = Maps.newConcurrentMap();
        // m.put(i + "", "zzz");
        // person.setKv(m);
        // persons.add(person);
        // Thread.currentThread().sleep(500);
        // }
        // repository.save(persons);
        //
        // ct.close();

        String cql = "select * from users where kv contains key '5'";
        List<Users> us = cassandraOperations.select(cql, Users.class);
        for (Users u : us) {
            System.out.println(u.getUserid());
        }

    }
}
