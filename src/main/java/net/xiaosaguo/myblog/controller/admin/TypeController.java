package net.xiaosaguo.myblog.controller.admin;

import net.xiaosaguo.myblog.pojo.entity.Type;
import net.xiaosaguo.myblog.service.TypeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * description: 分类 Controller
 *
 * @author xiaosaguo
 * @date 2020/04/26
 */
@Controller
@RequestMapping("/admin/type")
public class TypeController {

    @Resource
    private TypeService typeService;

    private static final String LIST_VIEW = "admin/type";
    private static final String REDIRECT_LIST_VIEW = "redirect:/admin/type";
    private static final String INPUT_VIEW = "admin/type-input";

    @GetMapping
    public String list(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", typeService.list(pageable));
        return LIST_VIEW;
    }

    @GetMapping("/save")
    public String saveView(Model model) {
        model.addAttribute("type", new Type());
        return INPUT_VIEW;
    }

    @PostMapping
    public String save(@Valid Type type, BindingResult bingingResult, RedirectAttributes attributes) {
        if (bingingResult.hasErrors()) {
            return INPUT_VIEW;
        }
        // 名称重复校验
        Type byName = typeService.getByName(type.getName());
        if (byName != null) {
            bingingResult.rejectValue("name", "nameError", "该名称已存在");
            return INPUT_VIEW;
        }
        // 保存
        typeService.save(type);
        attributes.addFlashAttribute("message", "新增成功");
        return REDIRECT_LIST_VIEW;
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.get(id));
        return INPUT_VIEW;
    }

    @PutMapping("/{id}")
    public String update(@Valid Type type, BindingResult bingingResult,
                         @PathVariable Long id,
                         RedirectAttributes attributes) {
        if (bingingResult.hasErrors()) {
            return INPUT_VIEW;
        }
        // 名称重复校验
        Type byName = typeService.getByName(type.getName());
        if (byName != null) {
            bingingResult.rejectValue("name", "nameError", "该名称已存在");
            return "admin/type-input";
        }
        // 更新
        typeService.update(id, type);
        attributes.addFlashAttribute("message", "更新成功");
        return REDIRECT_LIST_VIEW;
    }

    @GetMapping("/delete/{id}")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.delete(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST_VIEW;
    }
}
