package com.cskaoyan.dao.impl;

import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.bean.Product;
import com.cskaoyan.form.SearchCondition;
import com.cskaoyan.utils.MyC3PODataSouce;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
CREATE TABLE `product` (
  `pid` INT PRIMARY KEY AUTO_INCREMENT,
  `pname` VARCHAR(200),
  `estorePrice` DOUBLE(10, 2),
  `markPrice` DOUBLE(10, 2),
  `pnum` INT,
  `cid` INT,
  `imgUrl` VARCHAR(255),
  `description` VARCHAR(255),
  CONSTRAINT FK_product_cid FOREIGN KEY (cid) REFERENCES category (cid)
);
 */

public class ProductDaoImpl implements ProductDao {
    @Override
    public void minusProductCount(int pid, int pnum) throws SQLException {
       /* Connection connection = TransactionUtil.get();
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.update(connection, "update product set pnum = pnum - ? where pid = ?", pnum, pid);
 */   }

    @Override
    public List<Object> findProductByName(String name) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        String param = "%" + name + "%";
        return queryRunner.query("select pname from product where cid <> 7 and (pname like ? or description like ?) ",
                new ColumnListHandler(), param, param);
    }

    @Override
    public List<Product> findSimpleSearchProduct(SearchCondition condition, int limit, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        String pname = "%" + condition.getPname().trim() + "%";
        return queryRunner.query("select * from product where cid <> 7 and (pname like ? or description like ?) limit ? offset ?",
                new BeanListHandler<>(Product.class), pname, pname, limit, offset);
    }

    @Override
    public int findSimpleSearchProductCount(SearchCondition condition) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        String pname = "%" + condition.getPname().trim() + "%";
        Long query = (Long) queryRunner.query("select count(*) from product where cid <> 7 and (pname like ? or description like ?) ",
                new ScalarHandler(), pname, pname);
        return query.intValue();
    }

    @Override
    public List<Product> findProductByCid(String cid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        return queryRunner.query("select * from product where cid = ?",
                new BeanListHandler<>(Product.class), cid);
    }

    @Override
    public List<Product> findTopProducts() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        return queryRunner.query("select * from product where cid = 7",
                new BeanListHandler<>(Product.class));
    }

    @Override
    public List<Product> findHotProducts() throws SQLException {
        int offset = (int) (Math.random() * 30);
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        return queryRunner.query("select * from product where cid <> 7 order by estorePrice limit 6 offset ?",
                new BeanListHandler<>(Product.class), offset);
    }

    @Override
    public List<Product> findPartSearchProduct(SearchCondition condition, int limit, int offset) throws SQLException {
        String sql = "select * from product where true ";

        List<Object> param = new ArrayList<>();

        sql = getMultiConditionSql(condition, sql, param);

        // 加上分页
        sql += "limit ? offset ? ";
        param.add(limit);
        param.add(offset);

        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        return queryRunner.query(sql, new BeanListHandler<>(Product.class), param.toArray());
    }

    @Override
    public int findAllSearchProductCount(SearchCondition condition) throws SQLException {
        // 拼接 SQL 语句，加上 where true 可以避免判断 and
        String sql = "select count(*) from product where true ";

        // 拼接参数，可变参数类型
        List<Object> param = new ArrayList<>();

        sql = getMultiConditionSql(condition, sql, param);

        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        Long query = (Long) queryRunner.query(sql, new ScalarHandler(), param.toArray());
        return query.intValue();
    }

    private String getMultiConditionSql(SearchCondition condition, String sql, List<Object> param) {
        String pid = condition.getPid();
        if (null != pid && !pid.isEmpty()) {
            sql += "and pid = ? ";
            param.add(pid);
        }

        String pname = condition.getPname();
        if (null != pname && !pname.isEmpty()) {
            sql += "and pname like ? ";
            param.add("%" + pname.trim() + "%");
        }

        String cid = condition.getCid();
        if (null != cid && !cid.isEmpty()) {
            sql += "and cid = ? ";
            param.add(cid);
        }

        String minPrice = condition.getMinPrice();
        if (null != minPrice && !minPrice.isEmpty()) {
            sql += "and estorePrice > ? ";
            param.add(minPrice);
        }

        String maxPrice = condition.getMaxPrice();
        if (null != maxPrice && !maxPrice.isEmpty()) {
            sql += "and estorePrice < ? ";
            param.add(maxPrice);
        }
        return sql;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        int update = queryRunner.update(
                "update product set pname = ?, estorePrice = ?, markPrice = ?, pnum = ?, cid = ?, imgUrl = ?, description = ? where pid = ?",
                product.getPname(),
                product.getEstorePrice(),
                product.getMarkPrice(),
                product.getPnum(),
                product.getCid(),
                product.getImgUrl(),
                product.getDescription(),
                product.getPid());
        return 1 == update;
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        return queryRunner.query("select * from product where pid = ?", new BeanHandler<>(Product.class), pid);
    }

    @Override
    public String deleteProduct(String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        Object query = queryRunner.query("select imgUrl from product where pid = ?", new ScalarHandler(), pid);
        queryRunner.update("delete from product where pid = ?", pid);
        return query.toString();
    }

    @Override
    public List<Product> findPartProduct(int limit, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        return queryRunner.query("select * from product limit ? offset ?", new BeanListHandler<>(Product.class), limit, offset);
    }

    @Override
    public int findAllProductCount() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        Long query = (Long) queryRunner.query("select count(*) from product", new ScalarHandler());
        return query.intValue();
    }

    @Override
    public boolean isPidAvailable(int pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        Long query = (Long) queryRunner.query("select count(*) from product where pid = ?", new ScalarHandler(), pid);
        return 1L != query;
    }

    @Override
    public boolean saveProduct(Product product) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(MyC3PODataSouce.getDataSource());
        int update = queryRunner.update(
                "INSERT INTO product (pid, pname, estorePrice, markPrice, pnum, cid, imgUrl, description) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);",
                product.getPid(),
                product.getPname(),
                product.getEstorePrice(),
                product.getMarkPrice(),
                product.getPnum(),
                product.getCid(),
                product.getImgUrl(),
                product.getDescription());
        return 1 == update;
    }

}
