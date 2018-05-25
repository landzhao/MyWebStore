package com.cskaoyan.utils;

import com.cskaoyan.bean.Category;

import java.util.List;

public class PageHelper<T> {

    List<T> resultList;

    private int currentPageNumber;  //当前的访问的页码
    private int totalPageNumber; //总页码
    private int previousPageNumber;
    private int nextPageNumber;

    private int totalRecordNumber; //总记录数

    public int getTotalRecordNumber() {
        return totalRecordNumber;
    }

    public void setTotalRecordNumber(int totalRecordNumber) {
        this.totalRecordNumber = totalRecordNumber;
    }

    public PageHelper(int pageNumber, int totalRecordNumber, int RecordPerPage) {
          setCurrentPageNumber(pageNumber);

         /* totalPageNumber= totalRecordNumber%RecordPerPage==0?
                  totalRecordNumber%RecordPerPage:totalRecordNumber%RecordPerPage+1 ;*/

          totalPageNumber=(totalRecordNumber+RecordPerPage-1)/RecordPerPage;
          setTotalPageNumber(totalPageNumber );
        
        if (pageNumber+1>totalPageNumber)
               setNextPageNumber(pageNumber);
        else
              setNextPageNumber(pageNumber+1);

        if (pageNumber-1==0){
              setPreviousPageNumber(pageNumber);
        }
        else
        {
              setPreviousPageNumber(pageNumber-1);
        }  
    
    }

    public int getPreviousPageNumber() {
        return previousPageNumber;
    }

    public void setPreviousPageNumber(int previousPageNumber) {
        this.previousPageNumber = previousPageNumber;
    }

    public int getNextPageNumber() {
        return nextPageNumber;
    }

    public void setNextPageNumber(int nextPageNumber) {
        this.nextPageNumber = nextPageNumber;
    }

    @Override
    public String toString() {
        return "PageHelper{" +
                "resultList=" + resultList +
                ", currentPageNumber=" + currentPageNumber +
                ", totalPageNumber=" + totalPageNumber +
                '}';
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public int getTotalPageNumber() {
        return totalPageNumber;
    }

    public void setTotalPageNumber(int totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
    }
}
