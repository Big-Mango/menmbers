package com.haffee.menmbers.service.impl;

import com.haffee.menmbers.entity.Card;
import com.haffee.menmbers.entity.Person;
import com.haffee.menmbers.entity.User;
import com.haffee.menmbers.repository.CardRepository;
import com.haffee.menmbers.repository.PersonRepository;
import com.haffee.menmbers.repository.UserRepository;
import com.haffee.menmbers.service.PersonService;
import com.haffee.menmbers.utils.CopyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * create by jacktong
 * date 2018/8/4 下午4:37
 * 个人公众号相关功能
 **/

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CardRepository cardRepository;

    /**
     * 查询个人信息
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public User findOneUser(String id) {
        Optional<User> o = userRepository.findById(Long.valueOf(id));
        if(o.isPresent()){
            User u = o.get();
            Optional<Person> o_p = personRepository.findById(u.getPersonId());
            if(o_p.isPresent()){
                u.setPerson(o_p.get());
            }
            Optional<Card> o_c = cardRepository.findById(u.getCardId());
            if(o_c.isPresent()){
                u.setCard(o_c.get());
            }
            return u;
        }else{
            return null;
        }

    }

    /**
     * 更新个人信息
     * @param user
     * @param person
     * @throws Exception
     */
    @Override
    public void updateUserInfo(User user, Person person){
        //更新用户表
        Optional<User> o = userRepository.findById(new Long((long)user.getId()));
        if(o.isPresent()){
            User u_db = o.get();
            CopyProperties.copyPropertiesIgnoreNull(user,u_db);
            userRepository.save(u_db);
        }
        //更新个人信息表
        Optional<Person> o_p =  personRepository.findById(person.getId());
        if(o_p.isPresent()){
            Person p = o_p.get();
            CopyProperties.copyPropertiesIgnoreNull(person,p);
            personRepository.save(p);
        }



    }
}
