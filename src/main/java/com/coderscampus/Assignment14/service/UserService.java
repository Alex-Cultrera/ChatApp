package com.coderscampus.Assignment14.service;

import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final ChannelService channelService;
    private final MessageService messageService;

    public UserService(UserRepository userRepo, ChannelService channelService, MessageService messageService) {
        this.userRepo = userRepo;
        this.channelService = channelService;
        this.messageService = messageService;
    }

//    public Set<User> findAll() {
//        return userRepo.findAllUsersWithChannelsAndMessages();
//    }

    public User findById(Long userId) {
        Optional<User> userOpt = userRepo.findById(userId);
        return userOpt.orElse(new User());
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public void delete(Long userId) {
        userRepo.deleteById(userId);
    }

    public void update(User existingUser, User user) {
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setName(user.getName());

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(user.getPassword());
            }
//
//            Address address = existingUser.getAddress();
//            if (address != null) {
//                address.setAddressLine1(user.getAddress().getAddressLine1());
//                address.setAddressLine2(user.getAddress().getAddressLine2());
//                address.setCity(user.getAddress().getCity());
//                address.setRegion(user.getAddress().getRegion());
//                address.setCountry(user.getAddress().getCountry());
//                address.setZipCode(user.getAddress().getZipCode());
//
//                messageService.saveAddress(address);
//            }
            user = existingUser;
            save(user);
        }
    }


}
