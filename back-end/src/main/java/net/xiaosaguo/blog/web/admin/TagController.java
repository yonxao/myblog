package net.xiaosaguo.blog.web.admin;

import net.xiaosaguo.blog.po.Tag;
import net.xiaosaguo.blog.service.TagService;
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
 * description: Tag标签web控制器
 *
 * @author xiaosaguo
 * @date 2020-04-30 15:39
 */
@Controller
@RequestMapping("/admin/tag")
public class TagController {

    @Resource
    private TagService tagService;

    @GetMapping
    public String list(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", tagService.list(pageable));
        return "admin/tag";
    }

    @GetMapping("/save")
    public String saveView(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tag-input";
    }

    @PostMapping
    public String save(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            return "admin/tag-input";
        }

        Tag byName = tagService.getByName(tag.getName());
        if (byName != null) {
            bindingResult.rejectValue("name", "nameError", "已存在此标签");
            return "admin/tag-input";
        }

        tagService.save(tag);
        // TODO 此处没考虑会新增失败的情况
        attributes.addFlashAttribute("message", "新增成功");
        return "redirect:/admin/tag";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.get(id));
        return "admin/tag-input";
    }

    @PutMapping("/{id}")
    public String update(@Valid Tag tag,
                         BindingResult bindingResult,
                         @PathVariable Long id,
                         RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "admin/tag-input";
        }

        // 名称重复校验
        Tag byName = tagService.getByName(tag.getName());
        if (byName != null) {
            bindingResult.rejectValue("name", "nameError", "该名称已存在");
            return "admin/tag-input";
        }

        // 更新
        tagService.update(id, tag);
        attributes.addFlashAttribute("message", "更新成功");
        return "redirect:/admin/tag";
    }

    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.delete(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tag";
    }

}
