package sg.edu.nus.workshop14.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import sg.edu.nus.workshop14.model.Contact;

//Repository annotation connects it to a database
//Different from ContactsRepo that's a Service
@Repository
public interface RedisRepo {
    public void save(final Contact contact);
    public Contact findById(final String contectId);
    public List<Contact> findAll(int startIndex);
    
}
