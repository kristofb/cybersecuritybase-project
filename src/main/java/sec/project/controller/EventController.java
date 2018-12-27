package sec.project.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.config.SessionAttributes;
import sec.project.domain.Account;
import sec.project.domain.Event;
import sec.project.repository.AccountRepository;
import sec.project.repository.EventRepository;

@Controller
public class EventController {

    @Autowired
    private HttpSession session;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PostConstruct
    public void init() {
        // add some content to the event repository
        eventRepository.save(new Event("New year concert", "City mayor Hall", "1th January 2019"));
        eventRepository.save(new Event("Spring concert", "City mayor Hall", "25th april 2019"));
        eventRepository.save(new Event("Jazz concert", "Main Hall", "13th may 2019"));
        eventRepository.save(new Event("School concert", "School central teather", "27th june 2019"));
    }

    /**
     * Display the list of available events
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute(SessionAttributes.model_events, eventRepository.findAll());
        SessionAttributes.setUserInModel(session, model);
        return "events";
    }

    /**
     * Register to an event. If the user is not authenticated, it is redirected
     * to the authentication page
     *
     * @param authentication
     * @param eventId
     * @return
     */
    @RequestMapping(value = "/events/register/{eventId}", method = RequestMethod.POST)
    public String register(@PathVariable Long eventId) {
        Account account = SessionAttributes.getSessionUser(session);
        if (account == null) {
            return "redirect:/sign-in";
        }

        Event event = eventRepository.findOne(eventId);
        account.registerTo(event);
        return "redirect:/events";
    }

    /**
     * Unegister to an already registered event. If the user is not
     * authenticated, an error is raised
     *
     * @param authentication
     * @param eventId
     * @return
     */
    @RequestMapping(value = "/events/register/{eventId}", method = RequestMethod.DELETE)
    public String unregister(@PathVariable Long eventId) {
        Account account = SessionAttributes.getSessionUser(session);
        if (account == null) {
            return "redirect:/sign-in";
        }

        Event event = eventRepository.findOne(eventId);
        account.unregisterTo(event);
        return "redirect:/events";
    }

}
