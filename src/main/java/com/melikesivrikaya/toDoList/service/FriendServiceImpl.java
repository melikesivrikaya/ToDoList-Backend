package com.melikesivrikaya.toDoList.service;

import com.melikesivrikaya.toDoList.model.Friend;
import com.melikesivrikaya.toDoList.model.FriendState;
import com.melikesivrikaya.toDoList.model.User;
import com.melikesivrikaya.toDoList.repository.FriendRepository;
import com.melikesivrikaya.toDoList.repository.UserRepository;
import com.melikesivrikaya.toDoList.request.CreateFriendRequest;
import com.melikesivrikaya.toDoList.request.DeleteFriendByUserIdAndFriendIdRequest;
import com.melikesivrikaya.toDoList.request.IsUsersFriendRequest;
import com.melikesivrikaya.toDoList.request.UpdateFriendRequest;
import com.melikesivrikaya.toDoList.response.FriendStateResponce;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService{
    private FriendRepository friendRepository;
    private UserRepository userRepository;
    @Override
    public List<Friend> getFriends() {
        return friendRepository.findAll();
    }
    @Override
    public Optional<Friend> getFriendById(Long id) {
        return friendRepository.findById(id);
    }

    @Override
    public Friend createFriend(CreateFriendRequest createFriendRequest) {
        User user = userRepository.findById(createFriendRequest.getUserId()).get();
        Friend friend = new Friend();
        friend.setFriendId(createFriendRequest.getFriendId());
        friend.setUserId(createFriendRequest.getUserId());
        friend.setName(user.getName());
        friend.setFriendState(FriendState.SENTED_REQUEST);
        User user1 = userRepository.findById(friend.getFriendId()).get();
        Friend userFriend = new Friend(user.getId(), friend.getFriendId(), user1.getName(), FriendState.REQUEST);
        friendRepository.save(userFriend);
        return friendRepository.save(friend);

    }
    @Override
    public Friend updateFriend( UpdateFriendRequest updateFriend) {
        Friend friend = friendRepository.findById(updateFriend.getId()).get();
        friend.setFriendState(updateFriend.getFriendState());
        Friend friendByRequest = friendRepository.findByUserIdAndFriendId(friend.getFriendId(),friend.getUserId());
        if (updateFriend.getFriendState() == FriendState.SUCCESS){
            friendByRequest.setFriendState(FriendState.SUCCESS);
        }
        friendRepository.save(friendByRequest);
        return friendRepository.save(friend);
    }

    @Override
    public void deleteFriendPair(Long id) {
        Friend friend = friendRepository.findById(id).get();
        friendRepository.deleteById(id);
        Friend friendByRequest = friendRepository.findByUserIdAndFriendId(friend.getFriendId(), friend.getUserId());
        friendRepository.deleteById(friendByRequest.getId());
    }
    @Override
    public void deleteFriend(Long id) {
        friendRepository.deleteById(id);
    }

    //Arkadaşımın arkadaşlarını bu fonksiyonla görebilirim
    @Override
    public List<Friend> getFriendsByUserId(Long userId) {
        return friendRepository.findAllByUserId(userId);
    }

    @Override
    public List<Friend> getFriendsByFriendsId(Long friendId) {
        return friendRepository.findAllByFriendId(friendId);
    }

    @Override
    public void deleteFriendByUserIdAndFriendId(DeleteFriendByUserIdAndFriendIdRequest deleteFriend) {
        Friend friend = friendRepository.findByUserIdAndFriendId(deleteFriend.getUserId(), deleteFriend.getFriendId());
        deleteFriendPair(friend.getId());
    }

    @Override
    public Friend getFriendByUserIdAndFriendId(Long userId, Long friendId) {
        return friendRepository.findByUserIdAndFriendId(userId,friendId);
    }
    @Override
    public boolean isUserFriend(IsUsersFriendRequest isUsersFriendRequest){
        Friend friend = friendRepository.findByUserIdAndFriendId(isUsersFriendRequest.getUserId() , isUsersFriendRequest.getFriendId());
        if(friend == null){
            return false;
        }
        else if(friend.getFriendState() != FriendState.SUCCESS){
            return false;
        }
       else {
           return true;
        }
    }
    @Override
    public FriendStateResponce getFriendWithFriendState(IsUsersFriendRequest isUsersFriendRequest){
        Friend friend = friendRepository.findByUserIdAndFriendId(isUsersFriendRequest.getUserId(), isUsersFriendRequest.getFriendId());
        User user = userRepository.findById(isUsersFriendRequest.getUserId()).get();
        //friend boşta direk userı gönder değilse responce
        if(friend == null){
            return new FriendStateResponce(isUsersFriendRequest.getFriendId(),user);
        }
        return new FriendStateResponce(friend,user);
    }
}
