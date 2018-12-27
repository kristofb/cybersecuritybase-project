/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.config;

import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import sec.project.domain.Account;
import sec.project.domain.Event;
import sec.project.domain.UserComment;
import sec.project.repository.AccountRepository;

/**
 *
 * @author kristof
 */
public class SessionAttributes {

    public static String session_user = "user";
    public static String model_username = "username";
    public static String model_events = "events";
    public static String model_registered_events = "reg_events";
    private static String model_usercomments = "my_comments";
    private static String model_other_comments = "other_comments";

    @Autowired
    private AccountRepository accountRepository;

    public static void setUserInModel(HttpSession session, Model model, List<Account> users) {
        Account my_account = (Account) session.getAttribute(SessionAttributes.session_user);
        if (my_account != null && my_account instanceof Account) {
            model.addAttribute(SessionAttributes.model_username, my_account.getUsername());
            model.addAttribute(SessionAttributes.model_usercomments, my_account.getComments());

            List<Event> registeredEvents = my_account.getRegisteredEvents();
            Vector<Long> ids = new Vector<Long>();
            for (Event evt : registeredEvents) {
                ids.addElement(evt.getId());
            }
            model.addAttribute(SessionAttributes.model_registered_events, ids);
        }

        // Set other comments
        Vector<UserComment> comments = new Vector<>();
        for (Account other_account : users) {
            //if (!other_account.equals(my_account)) 
            if (other_account.getComments()!=null && other_account.getComments().length()>0)
            {
                UserComment uc = new UserComment(other_account.getUsername(), other_account.getComments());
                comments.addElement(uc);
            }
        }
        model.addAttribute(model_other_comments, comments);
    }

    public static Account getSessionUser(HttpSession session) {
        return (Account) session.getAttribute(SessionAttributes.session_user);
    }
}
