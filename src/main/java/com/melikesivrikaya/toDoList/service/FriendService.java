package com.melikesivrikaya.toDoList.service;

import com.melikesivrikaya.toDoList.model.Friend;
import com.melikesivrikaya.toDoList.request.CreateFriendRequest;
import com.melikesivrikaya.toDoList.request.DeleteFriendByUserIdAndFriendIdRequest;
import com.melikesivrikaya.toDoList.request.IsUsersFriendRequest;
import com.melikesivrikaya.toDoList.request.UpdateFriendRequest;
import com.melikesivrikaya.toDoList.response.FriendStateResponce;

import java.util.List;
import java.util.Optional;

public interface FriendService {
    List<Friend> getFriends();
    Optional<Friend> getFriendById(Long id);
    Friend createFriend(CreateFriendRequest createFriendRequest);
    Friend updateFriend( UpdateFriendRequest updateFriend);
    void deleteFriend(Long id);
    void deleteFriendPair(Long id);

    List<Friend> getFriendsByUserId(Long userId);

    List<Friend> getFriendsByFriendsId(Long friendId);

    void deleteFriendByUserIdAndFriendId(DeleteFriendByUserIdAndFriendIdRequest deleteFriend);
    Friend getFriendByUserIdAndFriendId(Long userId , Long friendId);
    boolean isUserFriend(IsUsersFriendRequest isUsersFriendRequest);
    FriendStateResponce getFriendWithFriendState(IsUsersFriendRequest isUsersFriendRequest);
}
