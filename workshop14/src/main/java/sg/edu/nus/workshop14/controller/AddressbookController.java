package sg.edu.nus.workshop14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.workshop14.model.Contact;
import sg.edu.nus.workshop14.service.ContactsRepo;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class AddressbookController{
    private Logger logger = Logger.getLogger(AddressbookController.class.getName());

    @Autowired
    ContactsRepo service;

    @GetMapping("/")
    public String contactForm(Model model){
        logger.log(Level.INFO,"Show contact form");
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @GetMapping("/getContact/{contactId}")
    public String getContact(Model model, @PathVariable(value = "contactId") String contactId){
        logger.log(Level.INFO, "Contact ID: " + contactId);
        Contact contact = service.findById(contactId);

        logger.log(Level.INFO, "Name: " + contact.getName());
        logger.log(Level.INFO, "Email: " + contact.getEmail());
        logger.log(Level.INFO, "Phone No: " + contact.getPhoneNum());

        model.addAttribute("contact", contact);
        return "showContact";
    }   

    @GetMapping("/contact")
    public String getAllContacts(Model model, @RequestParam(name = "startIndex") 
        String startIndex){
        List<Contact> resultFromSvc = service.findAll(Integer.parseInt(startIndex));
        model.addAttribute("contacts", resultFromSvc);
        return "listContact";
    }

    @PostMapping("/contact")
    //Model Attribute annotation tells app that the contact obj is from a model attribute
    public String contactSubmit(@ModelAttribute Contact contact, Model model){
        logger.log(Level.INFO, "ID: " + contact.getId());
        logger.log(Level.INFO, "Name: " + contact.getName());
        logger.log(Level.INFO, "Email: " + contact.getEmail());
        logger.log(Level.INFO, "Phone No: " + contact.getPhoneNum());

        service.save(contact);
        model.addAttribute("contact", contact);
        //httpResponse.setStatus(HttpStatus.CREATED.value());
        return "showContact";
    }
}