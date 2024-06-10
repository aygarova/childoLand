//package com.kindergarten.kindergarten.services;
//
//import com.kindergarten.kindergarten.common.convertor.ModelConvertor;
//import com.kindergarten.kindergarten.enums.Role;
//import com.kindergarten.kindergarten.enums.State;
//import com.kindergarten.kindergarten.exceptions.ExceptionMessages;
//import com.kindergarten.kindergarten.exceptions.WrongActionException;
//import com.kindergarten.kindergarten.model.dto.ChildRegisterDto;
//import com.kindergarten.kindergarten.model.dto.ChildrenProfileDto;
//import com.kindergarten.kindergarten.model.entity.Child;
//import com.kindergarten.kindergarten.model.entity.Group;
//import com.kindergarten.kindergarten.model.entity.Teacher;
//import com.kindergarten.kindergarten.repositories.ChildRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class ChildrenServiceTest {
//
//    @Mock
//    private ChildRepository childRepository;
//
//    @Mock
//    private GroupService groupService;
//
//    @Mock
//    private ModelConvertor modelConvertor;
//
//    @Mock
//    private ExceptionMessages exceptionMessages;
//
//    @InjectMocks
//    private ChildrenService childrenService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testRegisterChild_Success() {
//        ChildRegisterDto childRegisterDto = new ChildRegisterDto("1234567890", "John", "Doe", 5, "groupA", "teacherA");
//        Group group = new Group("groupA", "teacherA");
//        Teacher teacher = new Teacher("teacherA", "John", "Smith");
//        Child child = new Child("1234567890", "John", "Doe", 5, group, teacher);
//        when(groupService.findGroup("groupA")).thenReturn(group);
//        when(groupService.findGroup("teacherA")).thenReturn(group);
//        when(modelConvertor.fromDtoToEntity(childRegisterDto, group, teacher)).thenReturn(child);
//
//        childrenService.registerChild(childRegisterDto);
//
//        verify(childRepository, times(1)).save(child);
//    }
//
//    @Test
//    void testRegisterChild_AlreadyExists() {
//        Teacher teacher = new Teacher("teacherA", "John", Role.TEACHER);
//        Group group = new Group("groupA", teacher);
//
//        ChildRegisterDto childRegisterDto = new ChildRegisterDto("1234567890", "John", "Doe", 5, "group", "teacherA", "email");
//       // Child child = new Child("1234567890", "John", "Doe", 5, group, teacher);
//        when(groupService.findGroup("groupA")).thenReturn(group);
//        when(groupService.findGroup("teacherA")).thenReturn(group);
//        when(modelConvertor.fromDtoToEntity(childRegisterDto, group, teacher)).thenReturn(child);
//        when(childRepository.findById("1234567890")).thenReturn(java.util.Optional.of(child));
//        when(exceptionMessages.getAlreadyExsistUser()).thenReturn("User already exists");
//
//        WrongActionException exception = assertThrows(WrongActionException.class, () -> childrenService.registerChild(childRegisterDto));
//        assertEquals("User already exists", exception.getMessage());
//    }