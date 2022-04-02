package sg.edu.nus.workshop14.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sg.edu.nus.workshop14.model.Contact;

@Service
//ContactsRepo is the implementation of all methods to access the Contacts database
//Different from RedisRepo that's a database interface that only declares the method with no implementation
public class ContactsRepo implements RedisRepo{
    private Logger logger = Logger.getLogger(ContactsRepo.class.getName());
    private static final String CONTACT_ENTITY = "contactList";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(final Contact contact){
        //contactlist is a list of all contact IDs
        //contactlist_map is a hash map of the IDs (keys) and the contact object (values)
        redisTemplate.opsForList().leftPush(CONTACT_ENTITY, contact.getId());
        redisTemplate.opsForHash().put(CONTACT_ENTITY + "_Map", contact.getId(), contact);
    }

    @Override
    public Contact findById(final String contactId){
        Contact result = (Contact)redisTemplate.opsForHash()
            .get(CONTACT_ENTITY + "_Map", contactId);
        return result;
    }

    @Override
    public List<Contact> findAll(int startIndex){
        List<Object> fromContactList = redisTemplate.opsForList()
            .range(CONTACT_ENTITY, startIndex, startIndex + 9);
        List<Contact> contacts = (List<Contact>) redisTemplate.opsForHash()
            .multiGet(CONTACT_ENTITY + "_Map", fromContactList).stream()
            .filter(Contact.class::isInstance).map(Contact.class::cast)
            .toList();
        return contacts;
    }
}
