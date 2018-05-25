package com.cskaoyan.test;

import com.cskaoyan.bean.Product;
import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.dao.impl.ProductDaoImpl;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class ProductTest {



    @Test
    public void testMSearch() throws SQLException {

        ProductDao dao = new ProductDaoImpl();


       /* List<Product> products = dao.findSimpleSearchProductCount(null, "%透气%", null, "0", "999");


        System.out.println("products"+products);*/
    }
}
