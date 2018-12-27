package sec.project.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.config.SessionAttributes;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class AccountController {

    @Autowired
    private HttpSession session;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder encoder;

    @RequestMapping(value = "/create-account", method = RequestMethod.GET)
    public String loadFormCreateAccount() {
        resetSession();
        return "form-create-account";
    }

    @RequestMapping(value = "/create-account", method = RequestMethod.POST)
    @Transactional
    public String submitFormCreateAccount(@RequestParam String username, @RequestParam String password) {
        resetSession();
        Account newAccount = new Account(username, encoder.encode(password)); // Whatever the result, do it now, to avoid too quick response in case of account already existing
        Account account = accountRepository.findByUsername(username);
        if (account != null) {
            return "redirect:/sign-in";
        } else {
            // Create new account
            accountRepository.save(newAccount);
            return "redirect:/sign-in";
        }
    }

    @RequestMapping(value = "/sign-in", method = RequestMethod.GET)
    public String loadFormSignIn() {
        resetSession();
        return "form-sign-in";
    }

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    @Transactional
    public String submitFormSignIn(Model model, @RequestParam String username, @RequestParam String password) {
        resetSession();
        Account account = accountRepository.findByUsername(username);
        if (account != null && encoder.matches(password, account.getPassword())) {
            session.setAttribute(SessionAttributes.session_user, account);
            SessionAttributes.setUserInModel(session, model);
            return "events";
        } else {
            return "redirect:/sign-in";
        }
    }

    private void resetSession() {
        session.removeAttribute(SessionAttributes.session_user);
    }

}
