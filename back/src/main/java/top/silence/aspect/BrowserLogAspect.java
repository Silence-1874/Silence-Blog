package top.silence.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.silence.dto.BlogDTO;
import top.silence.dto.Result;
import top.silence.service.BrowserService;
import top.silence.service.CategoryService;
import top.silence.service.TagService;

@Component
@Aspect
public class BrowserLogAspect {

    @Autowired
    private BrowserService browserService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    // 浏览“关于我”界面的记录
    @Pointcut("execution(public * top.silence.controller.AboutController.get())")
    public void about() {
    }

    @Around("about()")
    public Result aroundAbout(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行切入点
        Result result = (Result) joinPoint.proceed();

        // 添加浏览记录
        browserService.add("关于我");

        return result;
    }

    // 浏览“归档”界面的记录
    @Pointcut("execution(public * top.silence.controller.ArchivesController.archive())")
    public void archive() {
    }

    @Around("archive()")
    public Result aroundArchive(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行切入点
        Result result = (Result) joinPoint.proceed();

        // 添加浏览记录
        browserService.add("归档");

        return result;
    }

    // 浏览某篇博文的记录，在文章浏览量增加时执行
    @Pointcut("execution(public * top.silence.controller.BlogController.getDtoById(..))")
    public void blog() {
    }

    @Around("blog()")
    public Result aroundBlog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行切入点
        Result result = (Result) joinPoint.proceed();

        // 添加浏览记录
        BlogDTO blogDTO = (BlogDTO) result.getData();
        browserService.add("《" + blogDTO.getTitle() + "》");

        return result;
    }

    // 浏览某个分类下的文章的记录
    @Pointcut("execution(public * top.silence.controller.BlogController.page(..))")
    public void category() {
    }

    @Around("category()")
    public Result aroundCategory(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行切入点
        Result result = (Result) joinPoint.proceed();

        Long categoryId = (Long) joinPoint.getArgs()[2];
        if (categoryId == null) {
            return result;
        }

        // 添加浏览记录
        String categoryName = categoryService.getById(categoryId).getCategoryName();
        browserService.add("\"" + categoryName + "\"分类");

        return result;
    }

    // 浏览某个标签下的文章的记录
    @Pointcut("execution(public * top.silence.controller.BlogController.pageByTagId(..))")
    public void tag() {
    }

    @Around("tag()")
    public Result aroundTag(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行切入点
        Result result = (Result) joinPoint.proceed();

        Long tagId = (Long) joinPoint.getArgs()[2];

        // 添加浏览记录
        String tagName = tagService.getById(tagId).getTagName();
        browserService.add("\"" + tagName + "\"标签");

        return result;
    }

}
