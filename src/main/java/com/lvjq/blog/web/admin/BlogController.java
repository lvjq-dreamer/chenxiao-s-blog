package com.lvjq.blog.web.admin;

import com.lvjq.blog.pojo.Blog;
import com.lvjq.blog.pojo.BlogQuery;
import com.lvjq.blog.pojo.User;
import com.lvjq.blog.service.BlogService;
import com.lvjq.blog.service.CommentService;
import com.lvjq.blog.service.TagService;
import com.lvjq.blog.service.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, BlogQuery blog, Model model){

        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        log.info("后台请求博文的数据");
        return "admin/blogs";

    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){

        blog.setUser((User)session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b = blogService.saveBlog(blog);
        if(b == null) {
            log.info("后台修改/增加博文操作成功");
            attributes.addFlashAttribute("message","操作失败");
        } else {
            log.info("后台修改/增加博文操作失败");
            attributes.addFlashAttribute("message","操作成功");
        }

        return "redirect:/admin/blogs";

    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                                 Pageable pageable, BlogQuery blog, Model model){

        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        log.info("博文信息search");
        return "admin/blogs :: blogList";//局部刷新

    }

    @GetMapping("/blogs/input")
    public String input(Model model){

        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        log.info("后台准备新增博文");
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){

        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        log.info("后台准备修改id为" + id + "博文数据");
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){

        blogService.deleteBlog(id);
        log.info("后台删除博文操作成功");
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";

    }
}
