/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.config;

import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import sec.project.domain.Account;
import sec.project.domain.Event;

/**
 *
 * @author kristof
 */
public class SessionAttributes {

    public static String session_user = "user";
    public static String model_username = "username";
    public static String model_events = "events";
    public static String model_registered_events = "reg_events";

    public static void setUserInModel(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute(SessionAttributes.session_user);
        if (account != null && account instanceof Account) {
            model.addAttribute(SessionAttributes.model_username, account.getUsername());

            List<Event> registeredEvents = account.getRegisteredEvents();
            Vector<Long> ids = new Vector<Long>();
            for(Event evt:registeredEvents)
            {
                ids.addElement(evt.getId());
            }
            model.addAttribute(SessionAttributes.model_registered_events, ids);
        }
    }

    public static Account getSessionUser(HttpSession session) {
        return (Account) session.getAttribute(SessionAttributes.session_user);
    }
}
