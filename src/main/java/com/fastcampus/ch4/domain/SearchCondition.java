package com.fastcampus.ch4.domain;

import org.springframework.web.util.UriComponentsBuilder;

import static java.lang.Math.*;
import static java.util.Objects.requireNonNullElse;

public class SearchCondition { //조건 검색
    //sql 맵퍼 작성후 없는부분 SearchCondition에서 추가
    private Integer page = 1; //기본값을 주는이유는 나중에 컨트롤러에서 값을 받을때  값이 안들어오면 기본값으로한다.
    private Integer pageSize = DEFAULT_PAGE_SIZE;
    private String  option = ""; //제목하고 내용을 검색할건지, 제목만 검색할 건지
    private String  keyword = ""; //boardList에서 keyword 옵션에 따라 ("", A, T, W) 선택하게 되어있어 빈열로 하였다.
//    private Integer  offset;

    public static final int MIN_PAGE_SIZE = 5;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 50;

    public SearchCondition(){}

    public SearchCondition(Integer page, Integer pageSize) { //옵션이나 키워드가 없는 상태로 들어오는 경우도 있기때문
        this(page, pageSize, "", "");
    }

    public SearchCondition(Integer page, Integer pageSize, String option, String keyword) {
        this.page = page;
        this.pageSize = pageSize;
        this.option = option;
        this.keyword = keyword;
    }

    //getQueryString()은 현재 페이지를 기반으로 링크를 생성할때 사용
    public String getQueryString() {
        return getQueryString(page);
    }

    //getQueryString(Integer page)은 다른페이지를 선택했을때, 해당 페이지번호를 기반으로 링크를 생성할때 사용
    public String getQueryString(Integer page) {
        // ?page=10&pageSize=10&option=A&keyword=title
        return UriComponentsBuilder.newInstance()
                .queryParam("page",     page)
                .queryParam("pageSize", pageSize)
                .queryParam("option",   option)
                .queryParam("keyword",  keyword)
                .build().toString();
    }
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE);

        // MIN_PAGE_SIZE <= pageSize <= MAX_PAGE_SIZE
        this.pageSize = max(MIN_PAGE_SIZE, min(this.pageSize, MAX_PAGE_SIZE));
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getOffset() {
        return (page-1)*pageSize;
    }

    @Override
    public String toString() {
        return "SearchCondition{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", option='" + option + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}