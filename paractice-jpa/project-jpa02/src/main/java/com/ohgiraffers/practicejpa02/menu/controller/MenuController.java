package com.ohgiraffers.practicejpa02.menu.controller;

import com.ohgiraffers.practicejpa02.common.Pagenation;
import com.ohgiraffers.practicejpa02.common.PagingButton;
import com.ohgiraffers.practicejpa02.menu.model.dto.MenuDTO;
import com.ohgiraffers.practicejpa02.menu.model.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j      // 자동 로깅
@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuService service;

    @Autowired
    public MenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping("{menuCode}")
    public String findMenuByCode(@PathVariable int menuCode, Model model) {

        MenuDTO resultMenu = service.findMenuByMenuCode(menuCode);

        model.addAttribute("menu", resultMenu);

        return "menu/detail";

    }

    @GetMapping("/list")
    public String findAllMenus(Model model, @PageableDefault Pageable pageable) {

        Page<MenuDTO> menuList = service.findMenuList(pageable);

        PagingButton paging = Pagenation.getPaginButtonInfo(menuList);

        model.addAttribute("menuList", menuList);
        model.addAttribute("paging", paging);

        return "menu/list";

    }

}
