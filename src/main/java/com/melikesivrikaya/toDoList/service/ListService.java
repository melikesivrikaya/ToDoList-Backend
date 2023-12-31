package com.melikesivrikaya.toDoList.service;

import com.melikesivrikaya.toDoList.request.UpdateListRequest;
import com.melikesivrikaya.toDoList.response.ListResponce;

import java.util.List;

public interface ListService {
    List<ListResponce> getLists();
    ListResponce getList(Long id);
    void deleteList(Long id);
    ListResponce createList(com.melikesivrikaya.toDoList.model.List list);
    ListResponce updateList(UpdateListRequest list);
    List<ListResponce> getListsByUserId(Long userId);

    List<ListResponce> getListByListTitleId(Long titleId);
}
