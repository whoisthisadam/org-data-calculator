package com.chumachenko.orgsinfo.service;

import com.chumachenko.orgsinfo.repository.organization.OrgRepoImpl;
import com.chumachenko.orgsinfo.repository.organization.OrgRepository;
import com.chumachenko.orgsinfo.repository.user.UserRepoImpl;
import com.chumachenko.orgsinfo.repository.user.UserRepository;
import commands.fromserver.ResponseFromServer;
import entities.Organization;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrgService {

    OrgRepository orgRepository=new OrgRepoImpl();

    public List<String>getListOfOrgsString(){
        try (
                OrgRepository orgRepository=new OrgRepoImpl();
                UserRepository userRepository=new UserRepoImpl()
        )
        {
            List<String>list=orgRepository.findAll(null, 0).stream().map(
                    x->x.getId().toString()+". "+x.getType().toString()+' '+x.getName()+'('+userRepository.findById(x.getUserId()).getEmail()+')'
            ).collect(Collectors.toList());
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseFromServer createOrganization(Organization organization){
        try {
            orgRepository.create(organization);
            return ResponseFromServer.SUCCESFULLY;
        }catch (Exception e){
            return ResponseFromServer.ERROR;
        }
    }

    public Long findNumberOfOrgsOfUser(Long id){
        return orgRepository.findNumberOfOrgsOfUser(id);
    }

    public List<Organization> findAllOrgsByUserId(Long userId){
        return orgRepository.findAllByUserId(userId);
    }

    public ResponseFromServer deleteOrganization(Long userId, String name){
        try{
            orgRepository.deleteByUserIdAndName(userId,name);
            return ResponseFromServer.SUCCESFULLY;
        }
        catch (Exception e){
            return ResponseFromServer.ERROR;
        }
    }

    public Organization findOrgByUserIdAndName(Long userId, String name){
        try{
            return orgRepository.findByUserIdAndName(userId,name).get();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Organization>findTopSortedByLiquidity(){
        List<Organization>organizations=orgRepository.findTopSortedByLiquidity();
        try(FileWriter fileWriter = new FileWriter("Server/top_10_otchet_liquidity.txt")) {
            for(Integer i=0;i<organizations.size();i++){
                fileWriter.write((i+1)+". "+
                        organizations.get(i).getName()
                        +'('+new UserRepoImpl()
                        .findById(organizations.get(i).getUserId()).getEmail()
                        +") Ликвидность:"+organizations.get(i).getLiquidity()+"\n");
            }
            System.out.println("Wrote top 10 liquidity to file");
        } catch (IOException e) {
            System.out.println("An error occurred while writing top 10 liquidity to file");
            e.printStackTrace();
        }
        return organizations;
    }

    public List<Organization>findTopSortedBySolvency(){
        List<Organization>organizations=orgRepository.findTopSortedBySolvency();
        try(FileWriter fileWriter = new FileWriter("Server/top_10_otchet_solvency.txt")) {
            for(Integer i=0;i<organizations.size();i++){
                fileWriter.write((i+1)+". "+
                        organizations.get(i).getName()
                        +'('+new UserRepoImpl()
                        .findById(organizations.get(i).getUserId()).getEmail()
                        +") Платежеспособность:"+organizations.get(i).getSolvency()+"\n");
            }
            System.out.println("Wrote top 10 solvency to file");
        } catch (IOException e) {
            System.out.println("An error occurred while writing top 10 liquidity to file");
            e.printStackTrace();
        }
        return organizations;
    }

    public Double calcAvgLiquidity(){
        return orgRepository.calculateAverageLiquidity();
    }

    public Double calcAvgSolvency(){
        return orgRepository.calculateAverageSolvency();
    }

    public ResponseFromServer checkIfThisUserHasThisOrg(String name, Long userId){
        Optional<Organization>organization=orgRepository.findByUserIdAndName(userId, name);
        if(organization.isEmpty())return ResponseFromServer.ORG_NOT_EXIST;
        else return ResponseFromServer.SUCCESFULLY;
    }

    public ResponseFromServer updateOrgName(String name, Long id){
        try{
            orgRepository.changeOrgName(name,id);
            return ResponseFromServer.SUCCESFULLY;
        }
        catch (RuntimeException e){
            e.printStackTrace();
            return ResponseFromServer.ERROR;
        }
    }
}
