package com.kindergarten.kindergarten.common;

import com.kindergarten.kindergarten.model.entity.Child;
import com.kindergarten.kindergarten.model.entity.Parent;
import com.kindergarten.kindergarten.model.entity.Teacher;
import com.kindergarten.kindergarten.repositories.ParentRepository;
import com.kindergarten.kindergarten.repositories.TeacherRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrentUsers {

    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;

    public CurrentUsers(TeacherRepository teacherRepository, ParentRepository parentRepository) {
        this.teacherRepository = teacherRepository;
        this.parentRepository = parentRepository;
    }

    public Parent findCurrentParent(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName() ;
        return parentRepository.findByEmail(username).get(); //TODO return username
    }

    public  Teacher findCurrentTeacher(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return teacherRepository.getTeacherByEmail(username);
       //return new Teacher("poli.plinova@gmail.com", "123456789");
    }

    public  List<Child> findChildrenToTeacher(){
       return findCurrentTeacher().getChildren();
      //  return List.of(new Child("1","Test", "Testcho", 12, new Group("23"), Gender.GIRL, "gabriela.aygarova@gmail.com,gabriela_aygarova@abv.bg",  new Teacher("poli.plinova@gmail.com", "123456789")));
    }
}
