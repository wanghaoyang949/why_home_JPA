package com.why.home.back_end.web;


import com.why.home.back_end.service.BlogService;
import com.why.home.back_end.service.TagService;
import com.why.home.back_end.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*---------------------------------------------------------------
              CommentController Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/9/7.
;
;  Function:
;                   博客列表页处理
----------------------------------------------------------------*/

/*@Controller表示该类是控制层 控制层定义返回首页的控制器 用于测试*/
@Controller
public class BlogClientController {

    /* 列表显示 需要注入BlogService */
    @Autowired
    private BlogService blogService;

    /* 分类显示 需要注入TypeService */
    @Autowired
    private TypeService typeService;

    /* 标签显示 需要注入TagService */
    @Autowired
    private TagService tagService;

    /*通过Get请求路径 返回博客列表页*/
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8 , sort = {"updateTime"} , direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        /* Model存储查询后的分页信息 从而输出给前端页面 进行数据渲染 */
        /* blogService.listBlog(pageable,blog)返回类似JSON的信息 */
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listBlogTop(8));
        model.addAttribute("page",blogService.listBlog(pageable));
        return "blogs";
    }

    /*通过Get请求路径 返回博客详情页*/
    @GetMapping("/blogs/{id}")
    public String blogsdetails(@PathVariable Long id, Model model) {
        /* 使用getBlogMTH：本可以用getBlog查询id得到对应blog对象，但此方法得到的String类型content文本属于MarkDown语法，需要转化为HTML才能在详情页完整显示，所以在BlogService中利用MarkDownUtils工具引入新的方法处理  */
        model.addAttribute("blog", blogService.getBlogMTH(id));
        return "blogdetails";
    }

    /* PostMapping 返回搜索结果页*/
    /*----- @RequestParam用于从前端拿到query值 -----*/
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8 , sort = {"updateTime"} , direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model) {
        /* "%"+query+"%"用于模糊查询 */
        model.addAttribute("page",blogService.listBlogSearch(pageable,"%"+query+"%"));
        /* 将查询字符串返回到页面上去 */
        model.addAttribute("query",query);
        return "search";
    }
}
