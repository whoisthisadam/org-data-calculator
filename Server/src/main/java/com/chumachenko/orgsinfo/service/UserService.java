package com.chumachenko.orgsinfo.service;

import com.chumachenko.orgsinfo.repository.user.UserRepoImpl;
import com.chumachenko.orgsinfo.repository.user.UserRepository;
import commands.fromserver.ResponseFromServer;
import entities.User;
import exception.NoSuchEntityException;
import exception.RecurringEmailException;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    UserRepository userRepository=new UserRepoImpl();

    public boolean isUserExists(String email){
        try(UserRepository userRepository=new UserRepoImpl()){
            userRepository.findByEmail(email);
            return true;
        } catch (NoSuchEntityException e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public ResponseFromServer registerUser(User user){
        try (UserRepository u=new UserRepoImpl()){
            u.create(user);
            return ResponseFromServer.SUCCESFULLY;
        } catch (RecurringEmailException e) {
            return ResponseFromServer.ERROR;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String>getListOfStringUsers(){
        List<User> users = new UserRepoImpl().findAll(null, 0);
        return users
                .stream()
                .map(x -> x.getId() + ". " + x.getEmail())
                .collect(Collectors.toList());
    }

    public User findById(Long id){
        try(UserRepository userRepository=new UserRepoImpl()) {
            User user=userRepository.findById(id);
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseFromServer updateNameAndEmail(Long id, String firstName, String lastName, String email){
        try {
            userRepository.updateNamesAndEmail(id, firstName, lastName, email);
            return ResponseFromServer.SUCCESFULLY;
        }
        catch (Exception e){
            return  ResponseFromServer.ERROR;
        }
    }

    public ResponseFromServer updatePassword(Long id, String password){
        try {
            userRepository.updatePassword(id, password);
            return ResponseFromServer.SUCCESFULLY;
        }catch (Exception e){
            return ResponseFromServer.ERROR;
        }
    }

}
