package net.xiaosaguo.blog.web.admin;

import net.xiaosaguo.blog.po.Type;
import net.xiaosaguo.blog.service.TypeService;
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
 * description: 分类控制器
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
@Controller
@RequestMapping("/admin/type")
public class TypeController {

    @Resource
    private TypeService typeService;

    @GetMapping
    public String list(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", typeService.list(pageable));
        return "admin/type";
    }

    @GetMapping("/save")
    public String saveView(Model model) {
        model.addAttribute("type", new Type());
        return "admin/type-input";
    }

    @PostMapping
    public String save(@Valid Type type, BindingResult bingingResult, RedirectAttributes attributes) {

        if (bingingResult.hasErrors()) {
            return "admin/type-input";
        }

        Type byName = typeService.getByName(type.getName());
        if (byName != null) {
            bingingResult.rejectValue("name", "nameError", "该分类名称已存在");
            return "admin/type-input";
        }

        Type t = typeService.save(type);
        if (t == null) {
            // 没保存成功
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            // 保存成功
            attributes.addFlashAttribute("message", "新增成功");
        }

        return "redirect:/admin/type";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.get(id));
        return "admin/type-input";
    }

    @PutMapping("/{id}")
    public String update(@Valid Type type,
                         BindingResult bingingResult,
                         @PathVariable Long id,
                         RedirectAttributes attributes) {
        if (bingingResult.hasErrors()) {
            return "admin/type-input";
        }

        // 名称重复校验
        Type byName = typeService.getByName(type.getName());
        if (byName != null) {
            bingingResult.rejectValue("name", "nameError", "该分类名称已存在");
            return "admin/type-input";
        }

        Type t = typeService.update(id, type);
        if (t == null) {
            // 没保存成功
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            // 保存成功
            attributes.addFlashAttribute("message", "更新成功");
        }

        return "redirect:/admin/type";
    }

    @GetMapping("/delete/{id}")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.delete(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/type";
    }


}
