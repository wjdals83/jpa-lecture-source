package com.ohgiraffers.springdatajpa.menu.model.service;

import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.model.dao.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.model.dao.MenuRepository;
import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository repository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public MenuService(MenuRepository repository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    /* 1. findById() */
    public MenuDTO findMenuByMenuCode(int menuCode) {

        Menu foundMenu = repository.findById(menuCode).orElseThrow(IllegalArgumentException::new);

        return modelMapper.map(foundMenu, MenuDTO.class);
    }

    /* 페이징 처리 하지 않은 메뉴 전체 조회 */
    public List<MenuDTO> findMenuList() {

        List<Menu> menuList = repository.findAll(Sort.by("menuCode").descending());

        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());

    }

    /* 페이징 처리를 한 메뉴 전체 조회 */
    public Page<MenuDTO> findMenuList(Pageable pageable) {

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                                  pageable.getPageSize(),
                                  Sort.by("menuCode").descending());

        Page<Menu> menuList = repository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));

    }

    /* Query 메소드를 사용해서 조회하기 */
    public List<MenuDTO> findByMenuPrice(int menuPrice) {

//        List<Menu> menuList = repository.findByMenuPriceGreaterThan(menuPrice);
//        List<Menu> menuList = repository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);
        List<Menu> menuList = repository.findByMenuPriceGreaterThan(menuPrice, Sort.by("menuPrice").descending());

        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());
    }

    /* 5. @Query : JPQL Native Query */
    public List<CategoryDTO> findAllCategory() {

        List<Category> categoryList = categoryRepository.findAllCategory();

        categoryList.forEach(System.out::println);

        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    // save() 등록 관련 메소드
    @Transactional
    public void registNewMenu(MenuDTO menuDTO) {

        repository.save(modelMapper.map(menuDTO, Menu.class));

    }

    @Transactional
    public void modifyMenu(MenuDTO modifyMenu) {

        Menu foundMenu = repository.findById(modifyMenu.getMenuCode()).orElseThrow(IllegalArgumentException::new);

        /* 1. setter 사용해서 수정해보기 - setter 사용은 지양한다. */
//        foundMenu.setMenuName(modifyMenu.getMenuName());

        /* 2. @Builder */
//        foundMenu = foundMenu.toBuilder().menuName(modifyMenu.getMenuName()).build();
//        repository.save(foundMenu);

        /* 3. Entity 클래스 내부에서 builder 패턴을 사용해서 구현 */
        foundMenu = foundMenu.menuName(modifyMenu.getMenuName()).builder();
        repository.save(foundMenu);

    }

    @Transactional
    public void deleteMenu(int menuCode) {

        repository.deleteById(menuCode);

    }
}
