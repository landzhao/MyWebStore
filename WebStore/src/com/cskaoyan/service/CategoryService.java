package com.cskaoyan.service;

import com.cskaoyan.bean.Category;
import com.cskaoyan.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {

      boolean addCategory(String cname) throws SQLException;

      List<Category> findAllCategory() throws SQLException;



      boolean updateCategory(Category category) throws SQLException;

      boolean deleteCategory(int cid) throws SQLException;


      void deleteCategories(String[] cids);

       Category getCategoryByCid(String cid) throws SQLException, Exception;

       PageHelper<Category> findCategoryByPage(String num) throws SQLException;
}
