package com.fastcampus.ch4.domain;

import java.util.*;

public class BoardDto {
    private Integer bno; //게시글 번호
    private String  title; //게시글 제목
    private String  content; //게시글 내용
    private String  writer; //게시글 작성자
    private int     view_cnt; //게시글 조회수
    private int     comment_cnt; //게시글 댓글 갯수
    private Date    reg_date; //게시글 작성 날짜

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardDto boardDto = (BoardDto) o;
        return Objects.equals(bno, boardDto.bno) && Objects.equals(title, boardDto.title) && Objects.equals(content, boardDto.content) && Objects.equals(writer, boardDto.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bno, title, content, writer);
    }

    public BoardDto() { this("","",""); }
    public BoardDto(String title, String content, String writer){
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public Integer getBno() {
        return bno;
    }
    public void setBno(Integer bno) {
        this.bno = bno;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }
    public int getView_cnt() {
        return view_cnt;
    }
    public void setView_cnt(int view_cnt) {
        this.view_cnt = view_cnt;
    }
    public int getComment_cnt() {
        return comment_cnt;
    }
    public void setComment_cnt(int comment_cnt) {
        this.comment_cnt = comment_cnt;
    }
    public Date getReg_date() {
        return reg_date;
    }
    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {
        return "BoardDto{" +
                "bno=" + bno +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", view_cnt=" + view_cnt +
                ", comment_cnt=" + comment_cnt +
                ", reg_date=" + reg_date +
                '}';
    }
}