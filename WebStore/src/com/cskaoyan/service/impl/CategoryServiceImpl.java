package com.cskaoyan.service.impl;

import com.cskaoyan.bean.Category;
import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.dao.impl.CategoryDaoImpl;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.utils.PageHelper;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    CategoryDao categoryDao=new CategoryDaoImpl();

    private static  final int PAGE_COUNT=3;

    @Override
    public boolean addCategory(String cname) throws SQLException {

        return categoryDao.addCategory(cname);
    }

    @Override
    public List<Category> findAllCategory() throws SQLException {
        return categoryDao.findAllCategory();
    }




    @Override
    public boolean updateCategory(Category category) throws SQLException {
        // TODO Auto-generated method stub
        return categoryDao.updateCategory(category);
    }
    @Override
    public boolean deleteCategory(int cid) throws SQLException {
        // TODO Auto-generated method stub
        return categoryDao.deleteCategoryById(cid);
    }

    @Override
    public void deleteCategories(String[] cids) {
        // TODO Auto-generated method stub

        categoryDao.deleteCategoriesByIds(cids);
    }

    @Override
    public Category getCategoryByCid(String cid) throws SQLException, Exception {
        int intcid = Integer.parseInt(cid);

        return categoryDao.getCategoryByCid(intcid);
    }


    /**
     * 获取分页结果的业务层
     * @param num
     * @return
     */
    @Override
    public PageHelper<Category> findCategoryByPage(String num) throws SQLException {

        int pageNumber = 1;
        try {
            pageNumber = Integer.parseInt(num);

        }catch (Exception e){

            //如果用户传来的num字符串不是数字
            e.printStackTrace();
        }


        //查询总的记录数
        int totalNumber = categoryDao.findTotalNumber();
        PageHelper<Category> pageInfo = new PageHelper<Category>(pageNumber,totalNumber,PAGE_COUNT);
        pageInfo.setCurrentPageNum(pageNumber);


        //limit offset
        int limit = PAGE_COUNT;
        int offset=(pageNumber-1)*PAGE_COUNT;
        List<Category> categoryList =categoryDao.findPartCategory(limit,offset);
        pageInfo.setRecords(categoryList);


        return pageInfo;
    }

}
