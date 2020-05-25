package net.xiaosaguo.myblog.controller.admin;

import net.xiaosaguo.myblog.pojo.entity.User;
import net.xiaosaguo.myblog.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * description: 管理员登录 Controller
 *
 * @author xiaosaguo
 * @date 2020/04/25
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Resource
    MessageSource messageSource;
    @Resource
    private UserService userService;

    public static final String ADMIN_LOGIN_VIEW = "admin/login";
    public static final String REDIRECT_ADMIN_LOGIN_VIEW = "redirect:/admin";
    public static final String ADMIN_INDEX_VIEW = "redirect:/admin/index";

    @GetMapping
    public String loginView() {
        return ADMIN_LOGIN_VIEW;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.getByUsernameAndPassword(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return ADMIN_INDEX_VIEW;
        } else {
            // 返回与当前线程相关联的语言环境（如果有的话），否则返回系统默认语言环境。
            attributes.addFlashAttribute("message",
                    messageSource.getMessage("login.error", null, LocaleContextHolder.getLocale()));
            return REDIRECT_ADMIN_LOGIN_VIEW;
        }
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("img", "/images/login" + System.currentTimeMillis() % 2 + ".jpg");
        return "admin/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return REDIRECT_ADMIN_LOGIN_VIEW;
    }
}
