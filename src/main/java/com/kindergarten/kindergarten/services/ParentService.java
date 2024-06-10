package com.kindergarten.kindergarten.services;

import com.kindergarten.kindergarten.common.convertor.ModelConvertor;
import com.kindergarten.kindergarten.common.security.UserDetailServiceImpl;
import com.kindergarten.kindergarten.exceptions.ExceptionMessages;
import com.kindergarten.kindergarten.exceptions.WrongActionException;
import com.kindergarten.kindergarten.model.dto.ParentRegistrationDto;
import com.kindergarten.kindergarten.model.dto.UserLoginDto;
import com.kindergarten.kindergarten.model.entity.Child;
import com.kindergarten.kindergarten.model.entity.Parent;
import com.kindergarten.kindergarten.repositories.ParentRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
public class ParentService {

    private final ParentRepository parentRepository;
    private final ChildrenService childrenService;
    private final ModelConvertor modelConvertor;
    private final UserDetailServiceImpl userDetailService;

    private final PasswordEncoder passwordEncoder;
    public final ExceptionMessages exceptionMessages;

    public ParentService(ParentRepository parentRepository, ChildrenService childrenService, ModelConvertor modelConvertor, UserDetailServiceImpl userDetailService, PasswordEncoder passwordEncoder, ExceptionMessages exceptionMessages) {
        this.parentRepository = parentRepository;
        this.childrenService = childrenService;
        this.modelConvertor = modelConvertor;
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
        this.exceptionMessages = exceptionMessages;
    }

    public void registerUser(ParentRegistrationDto parentRegistrationDto) {
        Parent parent = modelConvertor.fromDtoToEntity(parentRegistrationDto, passwordEncoder);
        Parent usersWithThisEmail = parentRepository.findByEmail(parentRegistrationDto.getEmail()).orElse(null);
        if (!Objects.isNull(usersWithThisEmail)){
            throw new WrongActionException(exceptionMessages.getAlreadyExsistUser());
        }

        parentRepository.save(saveChildrenForThisParent(parent));

    }

    public boolean saveNewChild(String childEGN, String email) {
        Parent parent = parentRepository.findByEmail(email).orElse(null);
        List<String> children = Arrays.stream(parent.getChildEGN().split(",")).toList();

        if (checkParentHaveCorrectChildEGN(childEGN, email)){
            children.add(childEGN);
            parent.setChildEGN(String.join(",", children));
            parentRepository.save(parent);
            return true;
        }
        return false;
    }

    private Parent saveChildrenForThisParent(Parent parent) {
        List<String> childEGN = new ArrayList<>();
        List<String> childEGNList = Arrays.stream(parent.getChildEGN().split(",")).toList();

        System.out.println("Child EGN List: " + childEGNList);

        for (String children : childEGNList) {
            System.out.println("Checking: " + children + " for parent email: " + parent.getEmail());
            if (checkParentHaveCorrectChildEGN(children, parent.getEmail())) {
                childEGN.add(children);
                System.out.println("time");
            }
        }

        if (childEGN.isEmpty()){
            throw new WrongActionException(exceptionMessages.getEGNForOtherChild());
        }

        parent.setChildEGN(String.join(",", childEGN));
        return parent;
    }

    private boolean checkParentHaveCorrectChildEGN(String childEGN, String parentEmail) {
        return childrenService.isParentOfThisChild(childEGN, parentEmail);
    }

    public List<Child> getChildrenByParentEmail(String email) {
        return childrenService.parents(parentRepository.findByEmail(email).get());
    }

    public boolean loginUser(UserLoginDto userDto) {
        Parent loginParent = new Parent(userDto.getEmail(), userDto.getPassword());

        UserDetails userDetails =
                userDetailService.loadUserByUsername(loginParent.getEmail());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        if (userDetails == null || (!encoder.matches(loginParent.getPassword(),userDetails.getPassword()))){
            return false;
        }

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);

        return true;
    }

    public Parent findParentByEmail(String email){
        return parentRepository.findByEmail(email).get();
    }

    public void updateEmailPreferences(String parentEmail, boolean emailNotifications) {
        Parent parent = parentRepository.findByEmail(parentEmail).orElse(null);
        if (parent != null) {
            parent.setActive(emailNotifications);
            parentRepository.save(parent);
        }
    }
}
