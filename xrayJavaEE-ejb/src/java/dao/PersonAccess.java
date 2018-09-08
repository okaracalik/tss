/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.AssistantEntity;
import entity.EmployeeEntity;
import entity.PersonEntity;
import entity.RoleEntity;
import entity.SecretaryEntity;
import entity.SupervisorEntity;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author apple
 */

@Stateless
@LocalBean
public class PersonAccess extends NamedEntityAccess<PersonEntity>  {

    public PersonAccess() {
        super(PersonEntity.class);
    }

    public PersonEntity createPerson(String name) {
        PersonEntity pe = PersonEntity.newInstance();
        pe.setName(name);       
        em.persist(pe);
        return pe;
    }
    
    public RoleEntity createRole(PersonEntity person,String rolename){
        RoleEntity re=RoleEntity.newInstance();
        re.setName(rolename);
        re.setRtype(rolename);
        re.setPerson(person);
        em.persist(re);
        person.getRoles().add(re);
        /*switch(rolename){
            case "employee":
                EmployeeEntity ee=EmployeeEntity.newInstance();
                re.setrtype(role)
                break;
            case "assistant":
                AssistantEntity ae=AssistantEntity.newInstance();
                re=ae;
                break;
            case "supervisor":
                SupervisorEntity se=SupervisorEntity.newInstance();
                re=se;
                break;
            case "Secretary":
                SecretaryEntity see=SecretaryEntity.newInstance();
                re=see;
                break;
            
        }*/
        
        return re;
        
        
    }
    public List<String> getRoleNames(long personId){
        return em.createNamedQuery("PersonEntity.getRoleNames",String.class)
                .setParameter("personId",personId).getResultList();
    }
    public List<PersonEntity> getPersonList(){
        return em.createNamedQuery("PersonEntity.getPersonList",PersonEntity.class).getResultList();
        
    }
    
    public PersonEntity getPerosnByUuid(String uuid){
        return getNamedEntityByUuid(uuid);
        
    }
  

//    public int getPersonCount() {
//       long count = em.createNamedQuery("PersonEntity.getPersonCount", Long.class)
//                .getSingleResult();
//        return (int) count;
//    }
}
