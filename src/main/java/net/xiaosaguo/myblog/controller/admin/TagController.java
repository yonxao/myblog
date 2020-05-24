package net.xiaosaguo.myblog.controller.admin;

import net.xiaosaguo.myblog.pojo.entity.Tag;
import net.xiaosaguo.myblog.service.TagService;
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
 * description: Tag Controller
 *
 * @author xiaosaguo
 * @date 2020/04/30
 */
@Controller
@RequestMapping("/admin/tag")
public class TagController {

    @Resource
    private TagService tagService;

    private static final String LIST_VIEW = "admin/tag";
    private static final String REDIRECT_LIST_VIEW = "redirect:/admin/tag";
    private static final String INPUT_VIEW = "admin/tag-input";

    @GetMapping
    public String list(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", tagService.list(pageable));
        return LIST_VIEW;
    }

    @GetMapping("/save")
    public String saveView(Model model) {
        model.addAttribute("tag", new Tag());
        return INPUT_VIEW;
    }

    @PostMapping
    public String save(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return INPUT_VIEW;
        }
        // 名称重复校验
        Tag byName = tagService.getByName(tag.getName());
        if (byName != null) {
            bindingResult.rejectValue("name", "nameError", "该名称已存在");
            return INPUT_VIEW;
        }
        // 保存
        tagService.save(tag);
        attributes.addFlashAttribute("message", "新增成功");
        return REDIRECT_LIST_VIEW;
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.get(id));
        return INPUT_VIEW;
    }

    @PutMapping("/{id}")
    public String update(@Valid Tag tag, BindingResult bindingResult,
                         @PathVariable Long id,
                         RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return INPUT_VIEW;
        }
        // 名称重复校验
        Tag byName = tagService.getByName(tag.getName());
        if (byName != null) {
            bindingResult.rejectValue("name", "nameError", "该名称已存在");
            return INPUT_VIEW;
        }
        // 更新
        tagService.update(id, tag);
        attributes.addFlashAttribute("message", "更新成功");
        return REDIRECT_LIST_VIEW;
    }

    @GetMapping("/delete/{id}")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.delete(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST_VIEW;
    }
}
