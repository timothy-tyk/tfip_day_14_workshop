package workshop.workshop14.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import workshop.workshop14.models.Contact;

@Qualifier("contactsRedis")
@Service // business Logic
public class ContactsRedis {
  // code inside is a mixture of service and repository

  private static final String CONTACT_ENTITY="contactlist";
  
  @Autowired
  RedisTemplate<String, Object> redisTemplate;

  public void save(final Contact contact){
    System.out.println("TRYING TO SAVE");
    redisTemplate.opsForList().leftPush(CONTACT_ENTITY, contact.getId());
    redisTemplate.opsForHash().put(CONTACT_ENTITY+"_Map", contact.getId(),contact);
    System.out.println("saving contact");
  }

  public Contact findById(final String contactId){
    Contact result = (Contact) redisTemplate.opsForHash().get(CONTACT_ENTITY+"_Map", contactId);
    return result;
  }

  public List<Contact> findAll(int startIndex){
    List<Object> fromContactList = redisTemplate.opsForList().range(CONTACT_ENTITY, startIndex, startIndex+9);
    List<Contact> ctcs =redisTemplate.opsForHash().multiGet(CONTACT_ENTITY+"_Map", fromContactList).stream().filter(Contact.class::isInstance).map(Contact.class::cast).toList(); //check if object is Contact class
    return ctcs;
  }

}

