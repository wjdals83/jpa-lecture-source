package com.ohgiraffers.practicejpa02.menu.model.service;

import com.ohgiraffers.practicejpa02.menu.entity.Menu;
import com.ohgiraffers.practicejpa02.menu.model.dao.MenuRepository;
import com.ohgiraffers.practicejpa02.menu.model.dto.MenuDTO;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MenuService {

    private final MenuRepository repository;

    private final ModelMapper modelMapper;

    @Autowired
    public MenuService(MenuRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public MenuDTO findMenuByMenuCode(int menuCode) {

        Menu foundMenu = repository.findById(menuCode).orElseThrow(IllegalArgumentException::new);

        return modelMapper.map(foundMenu, MenuDTO.class);

    }
}
