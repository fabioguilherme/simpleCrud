package com.portofolio.demo.domain.user;

import com.portofolio.demo.shared.errors.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UserDomainServiceImpl implements UserDomainService {
    @Override
    public User createUser(String name, String email) {
        return User.Builder.with().name(name).email(email).build();
    }

    @Override
    public void updateEmail(User user, String email) {

        if (user == null || email == null) {
            throw new BusinessException("User and email can not be null", null);
        }

        user.updateEmail(email);
    }
}
