package workshop.workshop14.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import workshop.workshop14.models.Contact;
import workshop.workshop14.service.ContactsRedis;

@Controller
public class AddressBookController {
  @Autowired
  private ContactsRedis ctcRedisSvc;

  @GetMapping(path = "/")
  public String contactForm(Model model){
    model.addAttribute("contact", new Contact());
    return "contact";
  }

  @PostMapping("/contact")
  // Sequence of Contact, BindingResult, Model will affect whether error will be shown
  public String saveContact(@Valid Contact contact,BindingResult bindingResult, Model model, HttpServletResponse response){
    if(bindingResult.hasErrors()){
      model.addAttribute("contact", contact);
      return "contact";
    }
    ctcRedisSvc.save(contact);    
    model.addAttribute("contact", contact);
    response.setStatus(HttpServletResponse.SC_CREATED);    
    return "showContact";
  }

  @GetMapping("/contact")
  public String getAllContacts(Model model, @RequestParam(name = "startIndex") Integer startIndex){
    List<Contact> result = ctcRedisSvc.findAll(startIndex);
    model.addAttribute("contacts", result);
    return "listContact";
  }

  @GetMapping("/contact/{contactId}")
  public String getContactInfoById(Model model, @PathVariable(value = "contactId") String contactId){
    Contact contact = ctcRedisSvc.findById(contactId);
    model.addAttribute("contact", contact);
    return "showContact";
  }
}
